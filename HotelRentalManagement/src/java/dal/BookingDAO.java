/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Statement;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import model.Booking;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import model.InvoiceSummary;
import model.Payment;
import model.RoomBookingInfo;

/**
 *
 * @author ddhoang
 */
public class BookingDAO extends DBcontext {

    public List<Booking> getCompletedBookingsByUserIdWithPaging(int userId, int offset, int pageSize) {
        Map<Integer, Booking> bookingMap = new LinkedHashMap<>();

        String sql = "SELECT \n"
                + "    b.Booking_Id, \n"
                + "    r.Room_Number, \n"
                + "    b.CheckIn_Date, \n"
                + "    b.Room_Id, \n"
                + "    rt.Type_Name AS RoomTypeName, \n"
                + "    b.CheckOut_Date, \n"
                + "    b.Special_Requests, \n"
                + "    b.Promotion_Id, \n"
                + "    b.Total_Amount, \n"
                + "    bs.Quantity, \n"
                + "    s.Service_Name \n"
                + "FROM (\n"
                + "    SELECT Booking_Id \n"
                + "    FROM Booking \n"
                + "    WHERE Customer_Id = ? AND Status = 'completed' \n"
                + "    ORDER BY Booking_Id DESC \n"
                + "    OFFSET ? ROWS FETCH NEXT ? ROWS ONLY\n"
                + ") sub \n"
                + "JOIN Booking b ON sub.Booking_Id = b.Booking_Id \n"
                + "LEFT JOIN Rooms r ON b.Room_Id = r.Room_Id \n"
                + "JOIN RoomType rt ON r.Room_Type_Id = rt.Room_Type_Id \n"
                + "LEFT JOIN BookingService bs ON b.Booking_Id = bs.Booking_Id \n"
                + "LEFT JOIN Services s ON bs.Service_Id = s.Service_Id";

        try (PreparedStatement st = c.prepareStatement(sql)) {
            st.setInt(1, userId);
            st.setInt(2, offset);
            st.setInt(3, pageSize);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    int bookingId = rs.getInt("Booking_Id");
                    Booking booking = bookingMap.get(bookingId);

                    if (booking == null) {
                        booking = new Booking();
                        booking.setBookingId(bookingId);
                        booking.setRoomId(rs.getInt("Room_Id"));
                        booking.setCheckIn(rs.getDate("CheckIn_Date"));
                        booking.setCheckOut(rs.getDate("CheckOut_Date"));
                        booking.setRoomNumber(rs.getInt("Room_Number"));
                        booking.setRoomTypeName(rs.getString("RoomTypeName"));
                        booking.setRequest(rs.getString("Special_Requests"));
                        booking.setPromotionId(rs.getInt("Promotion_Id"));
                        booking.setTotalAmount(rs.getDouble("Total_Amount"));
                        booking.setServiceNames(new ArrayList<>());
                        booking.setServiceQuantities(new ArrayList<>());

                        bookingMap.put(bookingId, booking);
                    }

                    String serviceName = rs.getString("Service_Name");
                    int quantity = rs.getInt("Quantity");

                    if (serviceName != null) {
                        booking.getServiceNames().add(serviceName);
                        booking.getServiceQuantities().add(quantity);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>(bookingMap.values());
    }

    public int countCompletedBookingsByCustomer(int userId) {
        String sql = "SELECT COUNT(*) FROM Booking WHERE Customer_Id = ? AND Status = 'completed'";

        try (PreparedStatement st = c.prepareStatement(sql)) {
            st.setInt(1, userId);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public Map<String, Integer> getBookingStatusCounts() {
        Map<String, Integer> statusCounts = new LinkedHashMap<>();

        String sql = "SELECT [Status], COUNT(*) AS total FROM Booking GROUP BY [Status]";

        try {
            PreparedStatement st = c.prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                String status = rs.getString("Status");
                int total = rs.getInt("total");
                statusCounts.put(status, total);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return statusCounts;
    }

    public List<Booking> getApproveBookingWithPaging(int offset, int pageSize) {
        Map<Integer, Booking> bookingMap = new LinkedHashMap<>();

        String sql = "SELECT \n"
                + "    b.Booking_Id, \n"
                + "    r.Room_Number, \n"
                + "    b.CheckIn_Date, \n"
                + "    b.Room_Id, \n"
                + "    rt.Type_Name AS RoomTypeName, \n"
                + "    b.CheckOut_Date, \n"
                + "    b.Special_Requests, \n"
                + "    b.Promotion_Id, \n"
                + "    b.Total_Amount, \n"
                + "    bs.Quantity, \n"
                + "    s.Service_Name, \n"
                + "    u.Full_Name, \n"
                + "    i.Deposit_Amount \n"
                + "FROM (\n"
                + "    SELECT Booking_Id \n"
                + "    FROM Booking \n"
                + "    WHERE Status = 'pending' \n"
                + "    ORDER BY Booking_Id DESC \n"
                + "    OFFSET ? ROWS FETCH NEXT ? ROWS ONLY\n"
                + ") sub \n"
                + "JOIN Booking b ON sub.Booking_Id = b.Booking_Id \n"
                + "LEFT JOIN Rooms r ON b.Room_Id = r.Room_Id \n"
                + "JOIN RoomType rt ON r.Room_Type_Id = rt.Room_Type_Id \n"
                + "LEFT JOIN BookingService bs ON b.Booking_Id = bs.Booking_Id \n"
                + "LEFT JOIN Services s ON bs.Service_Id = s.Service_Id \n"
                + "JOIN [User] u ON b.Customer_Id = u.User_Id \n"
                + "LEFT JOIN Invoice i ON b.Booking_Id = i.Booking_Id";

        try (PreparedStatement st = c.prepareStatement(sql)) {
            st.setInt(1, offset);
            st.setInt(2, pageSize);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    int bookingId = rs.getInt("Booking_Id");
                    Booking booking = bookingMap.get(bookingId);

                    if (booking == null) {
                        booking = new Booking();
                        booking.setBookingId(bookingId);
                        booking.setRoomId(rs.getInt("Room_Id"));
                        booking.setCheckIn(rs.getDate("CheckIn_Date"));
                        booking.setCheckOut(rs.getDate("CheckOut_Date"));
                        booking.setRoomNumber(rs.getInt("Room_Number"));
                        booking.setRoomTypeName(rs.getString("RoomTypeName"));
                        booking.setRequest(rs.getString("Special_Requests"));
                        booking.setPromotionId(rs.getInt("Promotion_Id"));
                        booking.setTotalAmount(rs.getDouble("Total_Amount"));
                        booking.setUserName(rs.getString("Full_Name"));
                        booking.setDepositAmount(rs.getDouble("Deposit_Amount"));
                        booking.setServiceNames(new ArrayList<>());
                        booking.setServiceQuantities(new ArrayList<>());
                        bookingMap.put(bookingId, booking);
                    }

                    String serviceName = rs.getString("Service_Name");
                    int quantity = rs.getInt("Quantity");

                    if (serviceName != null) {
                        booking.getServiceNames().add(serviceName);
                        booking.getServiceQuantities().add(quantity);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>(bookingMap.values());
    }

    public int countApproveBooking() {
        String sql = "SELECT COUNT(*) FROM Booking WHERE Status = 'pending'";

        try (PreparedStatement st = c.prepareStatement(sql)) {
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public List<Booking> searchApproveBookingByUserName(String keyword) {
        Map<Integer, Booking> bookingMap = new LinkedHashMap<>();

        String sql = "SELECT \n"
                + "    b.Booking_Id, \n"
                + "    r.Room_Number, \n"
                + "    b.CheckIn_Date, \n"
                + "    b.Room_Id, \n"
                + "    b.CheckOut_Date, \n"
                + "    b.Special_Requests, \n"
                + "    b.Promotion_Id, \n"
                + "    b.Total_Amount, \n"
                + "    bs.Quantity, \n"
                + "    s.Service_Name, \n"
                + "    u.Full_Name \n"
                + "FROM Booking b \n"
                + "LEFT JOIN Rooms r ON b.Room_Id = r.Room_Id \n"
                + "LEFT JOIN BookingService bs ON b.Booking_Id = bs.Booking_Id \n"
                + "LEFT JOIN Services s ON bs.Service_Id = s.Service_Id \n"
                + "JOIN [User] u ON b.Customer_Id = u.User_Id \n"
                + "WHERE b.Status = 'pending' AND u.Full_Name LIKE ? \n"
                + "ORDER BY b.Booking_Id DESC;";

        try {
            PreparedStatement st = c.prepareStatement(sql);
            st.setString(1, "%" + keyword + "%");
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                int bookingId = rs.getInt("Booking_Id");
                Booking booking = bookingMap.get(bookingId);

                if (booking == null) {
                    booking = new Booking();
                    booking.setBookingId(bookingId);
                    booking.setRoomId(rs.getInt("Room_Id"));
                    booking.setCheckIn(rs.getDate("CheckIn_Date"));
                    booking.setCheckOut(rs.getDate("CheckOut_Date"));
                    booking.setRoomNumber(rs.getInt("Room_Number"));
                    booking.setRequest(rs.getString("Special_Requests"));
                    booking.setPromotionId(rs.getInt("Promotion_Id"));
                    booking.setTotalAmount(rs.getDouble("Total_Amount"));
                    booking.setUserName(rs.getString("Full_Name"));

                    bookingMap.put(bookingId, booking);
                }

                String serviceName = rs.getString("Service_Name");
                int quantity = rs.getInt("Quantity");

                if (serviceName != null) {
                    booking.getServiceNames().add(serviceName);
                    booking.getServiceQuantities().add(quantity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>(bookingMap.values());
    }

    public boolean updateStatusBookingByBookingId(int bookingId, String status, String rejectReason) {
        String sql;
        if ("reject".equalsIgnoreCase(status)) {
            sql = "UPDATE Booking SET Status = ?, Reject_Reason = ? WHERE Booking_Id = ?";
        } else {
            sql = "UPDATE Booking SET Status = ?, Reject_Reason = NULL WHERE Booking_Id = ?";
        }

        try (PreparedStatement st = c.prepareStatement(sql)) {
            st.setString(1, status);
            if ("reject".equalsIgnoreCase(status)) {
                st.setString(2, rejectReason);
                st.setInt(3, bookingId);
            } else {
                st.setInt(2, bookingId);
            }

            return st.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Booking> searchBookingByUserId(int userId, Date fromDate, Date toDate,
            String roomType, Double priceFrom, Double priceTo) {
        Map<Integer, Booking> bookingMap = new LinkedHashMap<>();

        StringBuilder sql = new StringBuilder(
                "SELECT b.Booking_Id, r.Room_Number, b.CheckIn_Date, b.Room_Id, "
                + "rt.Type_Name AS RoomTypeName, b.CheckOut_Date, b.Special_Requests, "
                + "b.Promotion_Id, b.Total_Amount, bs.Quantity, s.Service_Name "
                + "FROM Booking b "
                + "LEFT JOIN Rooms r ON b.Room_Id = r.Room_Id "
                + "JOIN RoomType rt ON r.Room_Type_Id = rt.Room_Type_Id "
                + // ✅ sửa thành JOIN
                "LEFT JOIN BookingService bs ON b.Booking_Id = bs.Booking_Id "
                + "LEFT JOIN Services s ON bs.Service_Id = s.Service_Id "
                + "WHERE b.Customer_Id = ? AND b.Status = 'completed' " // ✅ giống hàm cũ
        );

        List<Object> params = new ArrayList<>();
        params.add(userId);

        if (fromDate != null && toDate != null) {
            sql.append("AND b.CheckIn_Date >= ? AND b.CheckOut_Date <= ? ");
            params.add(fromDate);
            params.add(toDate);
        }

        if (roomType != null && !roomType.isEmpty()) {
            sql.append("AND rt.Type_Name = ? ");
            params.add(roomType);
        }

        if (priceFrom != null && priceTo != null) {
            sql.append("AND b.Total_Amount BETWEEN ? AND ? ");
            params.add(priceFrom);
            params.add(priceTo);
        }

        sql.append("ORDER BY b.Booking_Id");

        try (PreparedStatement st = c.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                Object param = params.get(i);
                if (param instanceof java.util.Date) {
                    st.setDate(i + 1, new java.sql.Date(((java.util.Date) param).getTime()));
                } else if (param instanceof Double) {
                    st.setDouble(i + 1, (Double) param);
                } else {
                    st.setObject(i + 1, param);
                }
            }

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                int bookingId = rs.getInt("Booking_Id");
                Booking booking = bookingMap.get(bookingId);

                if (booking == null) {
                    booking = new Booking();
                    booking.setBookingId(bookingId);
                    booking.setRoomId(rs.getInt("Room_Id"));
                    booking.setRoomNumber(rs.getInt("Room_Number"));
                    booking.setRoomTypeName(rs.getString("RoomTypeName")); // ✅ đúng alias
                    booking.setCheckIn(rs.getDate("CheckIn_Date"));
                    booking.setCheckOut(rs.getDate("CheckOut_Date"));
                    booking.setRequest(rs.getString("Special_Requests"));
                    booking.setPromotionId(rs.getInt("Promotion_Id"));
                    booking.setTotalAmount(rs.getDouble("Total_Amount"));
                    bookingMap.put(bookingId, booking);
                }

                String serviceName = rs.getString("Service_Name");
                int quantity = rs.getInt("Quantity");

                if (serviceName != null) {
                    booking.getServiceNames().add(serviceName);
                    booking.getServiceQuantities().add(quantity);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>(bookingMap.values());
    }

    public List<Booking> getBookingsByUserIdWithPaging(int userId, int offset, int pageSize) {
        Map<Integer, Booking> bookingMap = new LinkedHashMap<>();

        String sql = "SELECT \n"
                + "    b.Booking_Id, \n"
                + "    b.Room_Id, \n"
                + "    r.Room_Number, \n"
                + "    rt.Type_Name AS RoomTypeName, \n"
                + "    b.CheckIn_Date, \n"
                + "    b.CheckOut_Date, \n"
                + "    b.Special_Requests, \n"
                + "    b.Promotion_Id, \n"
                + "    b.Total_Amount, \n"
                + "    b.Status, \n"
                + "    b.Reject_Reason, \n"
                + "    bs.Quantity, \n"
                + "    s.Service_Name, \n"
                + "    i.Deposit_Amount \n"
                + "FROM (\n"
                + "    SELECT Booking_Id \n"
                + "    FROM Booking \n"
                + "    WHERE Customer_Id = ? AND Status != 'completed' AND Status != 'cancel'\n"
                + "    ORDER BY Booking_Id DESC \n"
                + "    OFFSET ? ROWS FETCH NEXT ? ROWS ONLY\n"
                + ") AS sub \n"
                + "JOIN Booking b ON sub.Booking_Id = b.Booking_Id \n"
                + "JOIN Rooms r ON b.Room_Id = r.Room_Id \n"
                + "JOIN RoomType rt ON r.Room_Type_Id = rt.Room_Type_Id \n"
                + "LEFT JOIN BookingService bs ON b.Booking_Id = bs.Booking_Id \n"
                + "LEFT JOIN Services s ON bs.Service_Id = s.Service_Id \n"
                + "LEFT JOIN Invoice i ON b.Booking_Id = i.Booking_Id";

        try (PreparedStatement st = c.prepareStatement(sql)) {
            st.setInt(1, userId);
            st.setInt(2, offset);
            st.setInt(3, pageSize);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    int bookingId = rs.getInt("Booking_Id");
                    Booking booking = bookingMap.get(bookingId);
                    System.out.println("bookingId :" + bookingId);
                    if (booking == null) {
                        booking = new Booking();
                        booking.setBookingId(bookingId);
                        booking.setRoomId(rs.getInt("Room_Id"));
                        booking.setRoomNumber(rs.getInt("Room_Number"));
                        booking.setRoomTypeName(rs.getString("RoomTypeName"));
                        booking.setCheckIn(rs.getTimestamp("CheckIn_Date"));
                        booking.setCheckOut(rs.getTimestamp("CheckOut_Date"));
                        booking.setRequest(rs.getString("Special_Requests"));
                        booking.setPromotionId(rs.getInt("Promotion_Id"));
                        booking.setTotalAmount(rs.getDouble("Total_Amount"));
                        booking.setStatus(rs.getString("Status"));
                        booking.setNote(rs.getString("Reject_Reason"));
                        booking.setDepositAmount(rs.getDouble("Deposit_Amount"));
                        booking.setServiceNames(new ArrayList<>());
                        booking.setServiceQuantities(new ArrayList<>());
                        bookingMap.put(bookingId, booking);
                    }

                    String serviceName = rs.getString("Service_Name");
                    int quantity = rs.getInt("Quantity");

                    if (serviceName != null) {
                        booking.getServiceNames().add(serviceName);
                        booking.getServiceQuantities().add(quantity);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>(bookingMap.values());
    }

    public Booking getBookingById(int bookingId) {
        Booking booking = null;
        String sql = "SELECT \n"
                + "    b.Booking_Id, \n"
                + "    b.Room_Id, \n"
                + "    r.Room_Number, \n"
                + "    rt.Type_Name AS RoomTypeName, \n"
                + "    b.CheckIn_Date, \n"
                + "    b.CheckOut_Date, \n"
                + "    b.Special_Requests, \n"
                + "    b.Promotion_Id, \n"
                + "    b.Total_Amount, \n"
                + "    b.Status, \n"
                + "    b.Reject_Reason, \n"
                + "    bs.Quantity, \n"
                + "    s.Service_Name \n"
                + "FROM Booking b \n"
                + "JOIN Rooms r ON b.Room_Id = r.Room_Id \n"
                + "JOIN RoomType rt ON r.Room_Type_Id = rt.Room_Type_Id \n"
                + "LEFT JOIN BookingService bs ON b.Booking_Id = bs.Booking_Id \n"
                + "LEFT JOIN Services s ON bs.Service_Id = s.Service_Id \n"
                + "WHERE b.Booking_Id = ?";

        try (PreparedStatement st = c.prepareStatement(sql)) {
            st.setInt(1, bookingId);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                if (booking == null) {
                    booking = new Booking();
                    booking.setBookingId(rs.getInt("Booking_Id"));
                    booking.setRoomId(rs.getInt("Room_Id"));
                    booking.setRoomNumber(rs.getInt("Room_Number"));
                    booking.setRoomTypeName(rs.getString("RoomTypeName"));
                    booking.setCheckIn(rs.getDate("CheckIn_Date"));
                    booking.setCheckOut(rs.getDate("CheckOut_Date"));
                    booking.setRequest(rs.getString("Special_Requests"));
                    booking.setPromotionId(rs.getInt("Promotion_Id"));
                    booking.setTotalAmount(rs.getDouble("Total_Amount"));
                    booking.setStatus(rs.getString("Status"));
                    booking.setNote(rs.getString("Reject_Reason"));
                    // Khởi tạo danh sách nếu chưa có
                    booking.setServiceNames(new ArrayList<>());
                    booking.setServiceQuantities(new ArrayList<>());
                }

                String serviceName = rs.getString("Service_Name");
                int quantity = rs.getInt("Quantity");

                if (serviceName != null) {
                    booking.getServiceNames().add(serviceName);
                    booking.getServiceQuantities().add(quantity);
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi SQL trong getBookingById, bookingId: " + bookingId + ", message: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Lỗi hệ thống trong getBookingById, bookingId: " + bookingId + ", message: " + e.getMessage());
            e.printStackTrace();
        }

        return booking;
    }

    public static void main(String[] args) {
        int bookingId = 100; // thay bằng ID tồn tại trong DB
        BookingDAO dao = new BookingDAO();
        Booking booking = dao.getBookingById(bookingId);

        if (booking == null) {
            System.out.println("❌ Không tìm thấy booking với ID = " + bookingId);
            return;
        }

        System.out.println("✅ Thông tin Booking:");
        System.out.println("Booking ID: " + booking.getBookingId());
        System.out.println("Room ID: " + booking.getRoomId());
        System.out.println("Room Number: " + booking.getRoomNumber());
        System.out.println("Loại phòng: " + booking.getRoomTypeName());
        System.out.println("Check-in: " + booking.getCheckIn());
        System.out.println("Check-out: " + booking.getCheckOut());
        System.out.println("Yêu cầu đặc biệt: " + booking.getRequest());
        System.out.println("Promotion ID: " + booking.getPromotionId());
        System.out.println("Tổng tiền: " + booking.getTotalAmount());
        System.out.println("Trạng thái: " + booking.getStatus());
        System.out.println("Ghi chú từ chối: " + booking.getNote());

        System.out.println("\n📦 Dịch vụ đã chọn:");
        if (booking.getServiceNames().isEmpty()) {
            System.out.println("Không có dịch vụ.");
        } else {
            for (int i = 0; i < booking.getServiceNames().size(); i++) {
                System.out.println("- " + booking.getServiceNames().get(i)
                        + " (x" + booking.getServiceQuantities().get(i) + ")");
            }
        }
    }

    public List<Booking> searchBookingsByFilters(int userId, String status, String roomType,
            Double priceFrom, Double priceTo) {
        Map<Integer, Booking> bookingMap = new LinkedHashMap<>();

        StringBuilder sql = new StringBuilder(
                "SELECT b.Booking_Id, b.Room_Id, r.Room_Number, rt.Type_Name AS RoomTypeName, "
                + "b.CheckIn_Date, b.CheckOut_Date, b.Special_Requests, b.Promotion_Id, "
                + "b.Total_Amount, b.Status, b.Reject_Reason, bs.Quantity, s.Service_Name, "
                + "i.Deposit_Amount "
                + "FROM Booking b "
                + "JOIN Rooms r ON b.Room_Id = r.Room_Id "
                + "JOIN RoomType rt ON r.Room_Type_Id = rt.Room_Type_Id "
                + "LEFT JOIN BookingService bs ON b.Booking_Id = bs.Booking_Id "
                + "LEFT JOIN Services s ON bs.Service_Id = s.Service_Id "
                + "LEFT JOIN Invoice i ON b.Booking_Id = i.Booking_Id "
                + "WHERE b.Customer_Id = ? AND b.Status != 'completed' "
        );

        List<Object> params = new ArrayList<>();
        params.add(userId);

        if (status != null && !status.trim().isEmpty()) {
            sql.append("AND b.Status = ? ");
            params.add(status.trim());
        }

        if (roomType != null && !roomType.isEmpty()) {
            sql.append("AND rt.Type_Name = ? ");
            params.add(roomType);
        }

        if (priceFrom != null && priceTo != null) {
            sql.append("AND b.Total_Amount BETWEEN ? AND ? ");
            params.add(priceFrom);
            params.add(priceTo);
        }

        sql.append("ORDER BY b.Booking_Id DESC");

        try (PreparedStatement st = c.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                Object param = params.get(i);
                if (param instanceof Double) {
                    st.setDouble(i + 1, (Double) param);
                } else {
                    st.setObject(i + 1, param);
                }
            }

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int bookingId = rs.getInt("Booking_Id");
                Booking booking = bookingMap.get(bookingId);

                if (booking == null) {
                    booking = new Booking();
                    booking.setBookingId(bookingId);
                    booking.setRoomId(rs.getInt("Room_Id"));
                    booking.setRoomNumber(rs.getInt("Room_Number"));
                    booking.setRoomTypeName(rs.getString("RoomTypeName"));
                    booking.setCheckIn(rs.getDate("CheckIn_Date"));
                    booking.setCheckOut(rs.getDate("CheckOut_Date"));
                    booking.setRequest(rs.getString("Special_Requests"));
                    booking.setPromotionId(rs.getInt("Promotion_Id"));
                    booking.setTotalAmount(rs.getDouble("Total_Amount"));
                    booking.setStatus(rs.getString("Status"));
                    booking.setNote(rs.getString("Reject_Reason"));
                    booking.setDepositAmount(rs.getDouble("Deposit_Amount")); // <-- THÊM DÒNG NÀY
                    booking.setServiceNames(new ArrayList<>());
                    booking.setServiceQuantities(new ArrayList<>());

                    bookingMap.put(bookingId, booking);
                }

                String serviceName = rs.getString("Service_Name");
                int quantity = rs.getInt("Quantity");

                if (serviceName != null) {
                    booking.getServiceNames().add(serviceName);
                    booking.getServiceQuantities().add(quantity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>(bookingMap.values());
    }

    public boolean deleteBooking(int bookingId) {
        String sql = "DELETE FROM Booking WHERE Booking_Id = ?";

        try (PreparedStatement st = c.prepareStatement(sql)) {
            st.setInt(1, bookingId);
            int affectedRows = st.executeUpdate();
            return affectedRows > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<RoomBookingInfo> getRoomBookingSchedule() {
        List<RoomBookingInfo> list = new ArrayList<>();
        String sql = """
    SELECT 
        r.Room_Id,
        r.Room_Number,
        rt.Type_Name AS Room_Type_Name,
        b.Booking_Id,
        b.CheckIn_Date,
        b.CheckOut_Date,
        b.Reservation_Date,
        b.Total_Amount,
        b.Status,

        ISNULL(u.Full_Name, ob.Full_Name) AS Customer_Name,
        ISNULL(u.Phone_Number, ob.Phone_Number) AS Customer_Phone,
        u.Email AS Customer_Email,
        ob.Identity_Card AS Customer_Identity_Card

    FROM Booking b
    JOIN Rooms r ON r.Room_Id = b.Room_Id
    JOIN RoomType rt ON r.Room_Type_Id = rt.Room_Type_Id

    LEFT JOIN [User] u ON b.Customer_Id = u.User_Id AND u.Role_Id = 1
    LEFT JOIN OfflineBookingUser ob ON b.Booking_Id = ob.Booking_Id

    WHERE b.Status IN ('approve', 'checkin', 'pending')
    ORDER BY r.Room_Number, b.CheckIn_Date
""";
        try (Connection conn = new DBcontext().c; PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                RoomBookingInfo info = new RoomBookingInfo();

                info.setRoomId(rs.getInt("Room_Id"));
                info.setRoomNumber(rs.getString("Room_Number"));
                info.setRoomTypeName(rs.getString("Room_Type_Name"));
                info.setCheckInDate(rs.getTimestamp("CheckIn_Date").toLocalDateTime());
                info.setCheckOutDate(rs.getTimestamp("CheckOut_Date").toLocalDateTime());

                info.setCustomerName(rs.getString("Customer_Name"));
                info.setCustomerPhone(rs.getString("Customer_Phone"));
                info.setCustomerEmail(rs.getString("Customer_Email"));
                info.setCustomerIdentityCard(rs.getString("Customer_Identity_Card"));

                list.add(info);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public void addBookingService(int bookingId, int serviceId, int quantity) throws SQLException {
        String sql = "INSERT INTO BookingService (Booking_Id, Service_Id, Quantity) VALUES (?, ?, ?)";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, bookingId);
            ps.setInt(2, serviceId);
            ps.setInt(3, quantity); // mặc định là 1 nếu không chọn số lượng
            ps.executeUpdate();
        }
    }

    public int createBooking(int customerId, int roomId, Timestamp checkin, Timestamp checkout,
            String specialRequest, BigDecimal totalAmount, Integer promotionId) throws SQLException {
        String sql = "INSERT INTO Booking (Customer_Id, Room_Id, Reservation_Date, CheckIn_Date, CheckOut_Date, "
                + "Special_Requests, Total_Amount, Promotion_Id) "
                + "OUTPUT INSERTED.Booking_Id VALUES (?, ?, GETDATE(), ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            ps.setInt(2, roomId);
            ps.setTimestamp(3, checkin);
            ps.setTimestamp(4, checkout);
            ps.setString(5, specialRequest);
            ps.setBigDecimal(6, totalAmount);
            if (promotionId != null) {
                ps.setInt(7, promotionId);
            } else {
                ps.setNull(7, java.sql.Types.INTEGER);
            }

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("Booking_Id");
            }
        }
        return -1;
    }

    public static RoomBookingInfo getCurrentBookingForRoom(int roomId) {
        RoomBookingInfo info = null;

        String sql = "SELECT TOP 1 b.*, u.Full_Name AS CustomerName, r.Room_Number, rt.Type_Name "
                + "FROM Booking b "
                + "JOIN [User] u ON b.Customer_Id = u.User_Id "
                + "JOIN Rooms r ON b.Room_Id = r.Room_Id "
                + "JOIN RoomType rt ON r.Room_Type_Id = rt.Room_Type_Id "
                + "WHERE b.Room_Id = ? "
                + "AND b.Status IN ('approve', 'checkin','pending') "
                + "AND b.CheckOut_Date >= CAST(GETDATE() AS DATE) "
                + "ORDER BY b.CheckOut_Date ASC";

        try {
            DBcontext db = new DBcontext();
            Connection c = db.c;

            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, roomId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                info = new RoomBookingInfo();
                info.setRoomId(rs.getInt("Room_Id"));
                info.setRoomNumber(String.valueOf(rs.getInt("Room_Number")));
                info.setRoomTypeName(rs.getString("Type_Name"));
                info.setCustomerName(rs.getString("CustomerName"));
                info.setCheckInDate(rs.getTimestamp("CheckIn_Date").toLocalDateTime());
                info.setCheckOutDate(rs.getTimestamp("CheckOut_Date").toLocalDateTime());
            }

            rs.close();
            ps.close();
            c.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return info;
    }

    public static boolean isRoomBookedInRange(int roomId, LocalDate checkin, LocalDate checkout) {
        String sql = """
        SELECT 1
        FROM Booking
        WHERE Room_Id = ?
          AND Status IN ('confirmed', 'checkin', 'approve')
          AND NOT (
              ? <= CheckIn_Date OR ? >= CheckOut_Date
          )
    """;

        try (Connection conn = new DBcontext().c; PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, roomId);
            ps.setDate(2, java.sql.Date.valueOf(checkout));
            ps.setDate(3, java.sql.Date.valueOf(checkin));

            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean isRoomBookedInRangenew(int roomId, LocalDateTime checkin, LocalDateTime checkout) {
        String sql = """
        SELECT 1 FROM Booking
        WHERE Room_Id = ?
          AND Status IN ('checkin', 'approve', 'confirmed')
          AND NOT (
               ? >= CheckOut_Date OR ? <= CheckIn_Date
          )
    """;

        try (Connection conn = new DBcontext().c; PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, roomId);
            ps.setTimestamp(2, Timestamp.valueOf(checkin));
            ps.setTimestamp(3, Timestamp.valueOf(checkout));
            ResultSet rs = ps.executeQuery();
            return rs.next(); // Có trùng → đang bị đặt rồi
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public int createBooking(int customerId, int roomId, Timestamp checkin, Timestamp checkout,
            String specialRequests, BigDecimal totalAmount) throws SQLException {

        String sql = "INSERT INTO Booking (Customer_Id, Room_Id, Reservation_Date, CheckIn_Date, CheckOut_Date, "
                + "Special_Requests, Status, Promotion_Id, Total_Amount) "
                + "VALUES (?, ?, GETDATE(), ?, ?, ?, 'checkin', NULL, ?)";

        try (PreparedStatement ps = c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, customerId);
            ps.setInt(2, roomId);
            ps.setTimestamp(3, checkin);
            ps.setTimestamp(4, checkout);
            ps.setString(5, specialRequests);
            ps.setBigDecimal(6, totalAmount);

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }
        }

        return -1;
    }

    public int insertBookingOnline(int customerId, int roomId, Date checkInDate, Date checkOutDate, String specialRequests, Integer promotionId, int totalAmount) {
        String sql = "INSERT INTO Booking (Customer_Id, Room_Id, Reservation_Date, CheckIn_Date, CheckOut_Date, "
                + "Special_Requests, Promotion_Id, Total_Amount, Status) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement st = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setInt(1, customerId);
            st.setInt(2, roomId);
            st.setTimestamp(3, new java.sql.Timestamp(System.currentTimeMillis()));

            // Thiết lập giờ mặc định
            Calendar checkInCal = Calendar.getInstance();
            checkInCal.setTime(checkInDate);
            checkInCal.set(Calendar.HOUR_OF_DAY, 14);
            checkInCal.set(Calendar.MINUTE, 0);
            checkInCal.set(Calendar.SECOND, 0);
            checkInCal.set(Calendar.MILLISECOND, 0);

            Calendar checkOutCal = Calendar.getInstance();
            checkOutCal.setTime(checkOutDate);
            checkOutCal.set(Calendar.HOUR_OF_DAY, 12);
            checkOutCal.set(Calendar.MINUTE, 0);
            checkOutCal.set(Calendar.SECOND, 0);
            checkOutCal.set(Calendar.MILLISECOND, 0);

            st.setTimestamp(4, new java.sql.Timestamp(checkInCal.getTimeInMillis()));
            st.setTimestamp(5, new java.sql.Timestamp(checkOutCal.getTimeInMillis()));

            if (specialRequests != null) {
                st.setString(6, specialRequests);
            } else {
                st.setNull(6, java.sql.Types.VARCHAR);
            }

            if (promotionId != null) {
                st.setInt(7, promotionId);
            } else {
                st.setNull(7, java.sql.Types.INTEGER);
            }

            st.setDouble(8, totalAmount);
            st.setString(9, "pending");

            int result = st.executeUpdate();
            if (result > 0) {
                try (ResultSet rs = st.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1); // ✅ Booking_Id vừa được tạo
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1; // ❌ Insert thất bại
    }

    public void insertBookingService(int bookingId, int serviceId) {
        String sql = "INSERT INTO BookingService (Booking_Id, Service_Id, Quantity) VALUES (?, ?, ?)";

        try (PreparedStatement st = c.prepareStatement(sql)) {
            st.setInt(1, bookingId);
            st.setInt(2, serviceId);
            st.setInt(3, 1); // hoặc số lượng thật nếu có
            st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int countBookingsByCustomer(int customerId) {
        String sql = "SELECT COUNT(DISTINCT Booking_Id) FROM Booking WHERE Customer_Id = ? AND Status != 'completed' AND Status != 'cancel'";

        try (Connection conn = new DBcontext().c; PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, customerId);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public boolean existsBookingId(int bookingId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Booking WHERE Booking_Id = ?";
        DBcontext db = new DBcontext(); // ✅ Không thay đổi gì trong DBcontext
        try (Connection conn = db.c; PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, bookingId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    public Integer getActiveBookingId() {
        String sql = "SELECT TOP 1 Booking_Id FROM Booking WHERE Status = 'checkin'";
        try (PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("Booking_Id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
//
//    public List<Booking> getCurrentApprovedBookings() {
//        List<Booking> list = new ArrayList<>();
//
//        String sql = "SELECT b.*, r.Room_Number, u.Full_Name "
//                + "FROM Booking b "
//                + "JOIN Rooms r ON b.Room_Id = r.Room_Id "
//                + "JOIN [User] u ON b.Customer_Id = u.User_Id "
//                + "WHERE b.Status = 'approve' "
//                + "AND CAST(b.CheckIn_Date AS DATE) <= CAST(GETDATE() AS DATE) "
//                + "AND CAST(b.CheckOut_Date AS DATE) > CAST(GETDATE() AS DATE)";
//
//        try (Connection conn = new DBcontext().c; PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
//
//            while (rs.next()) {
//                Booking b = new Booking();
//                b.setBookingId(rs.getInt("Booking_Id"));
//                b.setRoomId(rs.getInt("Room_Id"));
//                b.setUserId(rs.getInt("Customer_Id"));
//                b.setPromotionId(rs.getInt("Promotion_Id"));
//                b.setRoomNumber(rs.getInt("Room_Number")); // Lấy số phòng thật
//                b.setRequest(rs.getString("Special_Requests"));
//                b.setStatus(rs.getString("Status"));
//                b.setNote(rs.getString("Reject_Reason"));
//                b.setTotalAmount(rs.getDouble("Total_Amount"));
//                b.setCheckIn(rs.getDate("CheckIn_Date"));
//                b.setCheckOut(rs.getDate("CheckOut_Date"));
//                b.setReservationDate(rs.getDate("Reservation_Date"));
//                b.setUserName(rs.getString("Full_Name")); // Tên khách hàng
//                list.add(b);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return list;
//    }

    public List<Booking> getCurrentApprovedBookings(LocalDate date) {
        List<Booking> list = new ArrayList<>();

        String sql = "SELECT b.*, r.Room_Number, u.Full_Name "
                + "FROM Booking b "
                + "JOIN Rooms r ON b.Room_Id = r.Room_Id "
                + "JOIN [User] u ON b.Customer_Id = u.User_Id "
                + "WHERE b.Status = 'approve' "
                + "AND CAST(b.CheckIn_Date AS DATE) <= ? "
                + "AND CAST(b.CheckOut_Date AS DATE) >= ?";

        try (Connection conn = new DBcontext().c; PreparedStatement ps = conn.prepareStatement(sql)) {

            // Chuyển LocalDate thành java.sql.Date và gán vào PreparedStatement
            java.sql.Date sqlDate = java.sql.Date.valueOf(date);
            ps.setDate(1, sqlDate);
            ps.setDate(2, sqlDate);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Booking b = new Booking();
                    b.setBookingId(rs.getInt("Booking_Id"));
                    b.setRoomId(rs.getInt("Room_Id"));
                    b.setUserId(rs.getInt("Customer_Id"));
                    b.setPromotionId(rs.getInt("Promotion_Id"));
                    b.setRoomNumber(rs.getInt("Room_Number"));
                    b.setRequest(rs.getString("Special_Requests"));
                    b.setStatus(rs.getString("Status"));
                    b.setNote(rs.getString("Reject_Reason"));
                    b.setTotalAmount(rs.getDouble("Total_Amount"));
                    b.setCheckIn(rs.getDate("CheckIn_Date"));
                    b.setCheckOut(rs.getDate("CheckOut_Date"));
                    b.setReservationDate(rs.getDate("Reservation_Date"));
                    b.setUserName(rs.getString("Full_Name"));
                    list.add(b);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<Booking> getCurrentCheckinBookings(LocalDate date) {
        List<Booking> list = new ArrayList<>();

        String sql = "SELECT "
                + "b.Booking_Id, b.Room_Id, b.Reservation_Date, b.CheckIn_Date, b.CheckOut_Date, "
                + "b.Total_Amount, b.Status, u.Role_Id, "
                + "r.Room_Number, "
                + "CASE WHEN u.Role_Id = 1 THEN u.Full_Name WHEN u.Role_Id = 4 THEN ob.Full_Name ELSE NULL END AS Customer_Name, "
                + "CASE WHEN u.Role_Id = 1 THEN u.Phone_Number WHEN u.Role_Id = 4 THEN ob.Phone_Number ELSE NULL END AS Customer_Phone, "
                + "CASE WHEN u.Role_Id = 1 THEN u.Email ELSE NULL END AS Customer_Email, "
                + "CASE WHEN u.Role_Id = 4 THEN ob.Identity_Card ELSE NULL END AS Customer_Identity_Card "
                + "FROM Booking b "
                + "JOIN Rooms r ON b.Room_Id = r.Room_Id "
                + "LEFT JOIN [User] u ON b.Customer_Id = u.User_Id "
                + "LEFT JOIN OfflineBookingUser ob ON b.Booking_Id = ob.Booking_Id "
                + "WHERE b.Status = 'checkin' "
                + "AND CAST(b.CheckIn_Date AS DATE) <= ? "
                + "AND CAST(b.CheckOut_Date AS DATE) >= ? "
                + "AND (u.Role_Id = 1 OR u.Role_Id = 4)";

        try (Connection conn = new DBcontext().c; PreparedStatement ps = conn.prepareStatement(sql)) {

            java.sql.Date sqlDate = java.sql.Date.valueOf(date);
            ps.setDate(1, sqlDate);
            ps.setDate(2, sqlDate);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Booking b = new Booking();
                    b.setBookingId(rs.getInt("Booking_Id"));
                    b.setRoomId(rs.getInt("Room_Id"));
                    b.setRoomNumber(rs.getInt("Room_Number"));
                    b.setReservationDate(rs.getDate("Reservation_Date"));
                    b.setCheckIn(rs.getDate("CheckIn_Date"));
                    b.setCheckOut(rs.getDate("CheckOut_Date"));
                    b.setTotalAmount(rs.getDouble("Total_Amount"));
                    b.setStatus(rs.getString("Status"));
                    b.setUserName(rs.getString("Customer_Name"));
                    b.setUserPhone(rs.getString("Customer_Phone"));
                    b.setUserEmail(rs.getString("Customer_Email"));
                    b.setUserIdentity(rs.getString("Customer_Identity_Card"));
                    b.setRoleId(rs.getInt("Role_Id"));
                    list.add(b);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean updateBookingStatus(int bookingId, String status) {
        String sql = "UPDATE Booking SET Status = ? WHERE Booking_Id = ?";
        try (Connection conn = new DBcontext().c; PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, bookingId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public InvoiceSummary getInvoiceSummaryByBookingId(int bookingId) {
        String sql = """
        SELECT 
            b.Total_Amount, 
            i.Deposit_Amount,
            (b.Total_Amount - i.Deposit_Amount) AS Remaining_Amount,
            b.CheckIn_Date,
            b.CheckOut_Date,
            b.Special_Requests,
            r.Room_Number,
            rt.Room_Type_Name
        FROM Booking b
        JOIN Invoice i ON b.Booking_Id = i.Booking_Id
        JOIN Rooms r ON b.Room_Id = r.Room_Id
        JOIN RoomType rt ON r.Room_Type_Id = rt.Room_Type_Id
        WHERE b.Booking_Id = ?
    """;

        try (Connection conn = new DBcontext().c; PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, bookingId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                InvoiceSummary summary = new InvoiceSummary();
                summary.setTotalAmount(rs.getDouble("Total_Amount"));
                summary.setDepositAmount(rs.getDouble("Deposit_Amount"));
                summary.setRemainingAmount(rs.getDouble("Remaining_Amount"));
                summary.setCheckIn(rs.getDate("CheckIn_Date"));
                summary.setCheckOut(rs.getDate("CheckOut_Date"));
                summary.setSpecialRequests(rs.getString("Special_Requests"));
                summary.setRoomNumber(rs.getInt("Room_Number"));
                summary.setRoomTypeName(rs.getString("Room_Type_Name"));
                return summary;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Payment> getAllPayments() {
        List<Payment> list = new ArrayList<>();
        String sql = "SELECT Payment_Id, Payment_Type FROM Payment";
        try (PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Payment p = new Payment();
                p.setPaymentId(rs.getInt("Payment_Id"));
                p.setPaymentType(rs.getString("Payment_Type"));
                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int calculateNumberOfNights(Date checkIn, Date checkOut) {
        if (checkIn == null || checkOut == null) {
            return 0;
        }

        LocalDate start = checkIn.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate end = checkOut.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        long nights = ChronoUnit.DAYS.between(start, end);
        return (int) nights;
    }

    public int getLatestBookingId() {
        int latestId = -1;
        String sql = "SELECT TOP 1 [Booking_Id] FROM Booking ORDER BY [Booking_Id] DESC";

        try {
            PreparedStatement st = c.prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                latestId = rs.getInt("Booking_Id");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return latestId;
    }

    public Booking getBookingUserInfoByBookingId(int bookingId) {
        Booking booking = null;


    String sql = "SELECT "
            + "b.Booking_Id, b.Room_Id, b.Reservation_Date, b.CheckIn_Date, b.CheckOut_Date, "
            + "b.Total_Amount, b.Status, u.Role_Id, "
            + "r.Room_Number, rt.Type_Name, "
            + "CASE WHEN u.Role_Id = 1 THEN u.Full_Name WHEN u.Role_Id = 4 THEN ob.Full_Name ELSE NULL END AS Customer_Name, "
            + "CASE WHEN u.Role_Id = 1 THEN u.Phone_Number WHEN u.Role_Id = 4 THEN ob.Phone_Number ELSE NULL END AS Customer_Phone, "
            + "CASE WHEN u.Role_Id = 1 THEN u.Email ELSE NULL END AS Customer_Email, "
            + "CASE WHEN u.Role_Id = 4 THEN ob.Identity_Card ELSE NULL END AS Customer_Identity_Card "
            + "FROM Booking b "
            + "JOIN Rooms r ON b.Room_Id = r.Room_Id "
            + "JOIN RoomType rt ON r.Room_Type_Id = rt.Room_Type_Id "
            + "LEFT JOIN [User] u ON b.Customer_Id = u.User_Id "
            + "LEFT JOIN OfflineBookingUser ob ON b.Booking_Id = ob.Booking_Id "
            + "WHERE b.Booking_Id = ? "
            + "AND (u.Role_Id = 1 OR u.Role_Id = 4)";

        try (Connection conn = new DBcontext().c; PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, bookingId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    booking = new Booking();
                    booking.setBookingId(rs.getInt("Booking_Id"));
                    booking.setRoomId(rs.getInt("Room_Id"));
                    booking.setRoomNumber(rs.getInt("Room_Number"));
                    booking.setRoomTypeName(rs.getString("Type_Name"));
                    booking.setReservationDate(rs.getDate("Reservation_Date"));
                    booking.setCheckIn(rs.getDate("CheckIn_Date"));
                    booking.setCheckOut(rs.getDate("CheckOut_Date"));
                    booking.setTotalAmount(rs.getDouble("Total_Amount"));
                    booking.setStatus(rs.getString("Status"));
                    booking.setUserName(rs.getString("Customer_Name"));
                    booking.setUserPhone(rs.getString("Customer_Phone"));
                    booking.setUserEmail(rs.getString("Customer_Email"));
                    booking.setUserIdentity(rs.getString("Customer_Identity_Card"));
                    booking.setRoleId(rs.getInt("Role_Id"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return booking;
    }

    public void updateTotalAmountByBookingId(int bookingId, double totalAmount) {
        String sql = "UPDATE Booking SET Total_Amount = ? WHERE Booking_Id = ?";

        try (PreparedStatement st = c.prepareStatement(sql)) {
            st.setDouble(1, totalAmount);
            st.setInt(2, bookingId);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating total amount for Booking ID " + bookingId, e);
        }
    }

}
