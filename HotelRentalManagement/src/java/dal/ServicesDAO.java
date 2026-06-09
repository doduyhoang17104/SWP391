/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import model.Service.Service;
import model.ServiceCategory;

/**
 *
 * @author admin
 */
public class ServicesDAO extends DBcontext {

    public Map<ServiceCategory, List<Service>> getServiceMapByCategory() {
        Map<ServiceCategory, List<Service>> map = new LinkedHashMap<>();

        String sql = "SELECT sc.Service_Category_Id, sc.Service_Category_Name, "
                + "s.Service_Id, s.Service_Name, s.Description, s.Price "
                + "FROM ServiceCategory sc "
                + "LEFT JOIN Services s ON sc.Service_Category_Id = s.Service_Category_Id "
                + "ORDER BY sc.Service_Category_Name, s.Service_Name";

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int catId = rs.getInt("Service_Category_Id");
                String catName = rs.getString("Service_Category_Name");

                ServiceCategory category = new ServiceCategory(catId, catName);
                map.putIfAbsent(category, new ArrayList<>());

                int serviceId = rs.getInt("Service_Id");
                if (serviceId != 0) {
                    Service s = new Service(
                            serviceId,
                            rs.getString("Service_Name"),
                            rs.getString("Description"),
                            rs.getInt("Price"),
                            catId
                    );
                    map.get(category).add(s);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }

    public void addServiceToBooking(int bookingId, int serviceId, int quantity) {
        String sql = "INSERT INTO BookingService (Booking_Id, Service_Id, Quantity) VALUES (?, ?, ?)";

        try (PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, bookingId);
            ps.setInt(2, serviceId);
            ps.setInt(3, quantity);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public void updateTotalAmountIncludingRoom(int bookingId) {
        String sql = """
        UPDATE Booking
        SET Total_Amount = (
            (SELECT 
                DATEDIFF(DAY, b.CheckIn_Date, b.CheckOut_Date) * rt.Base_Price
             FROM Booking b
             JOIN Rooms r ON b.Room_Id = r.Room_Id
             JOIN RoomType rt ON r.Room_Type_Id = rt.Room_Type_Id
             WHERE b.Booking_Id = ?)
            +
            (SELECT ISNULL(SUM(bs.Quantity * s.Price), 0)
             FROM BookingService bs
             JOIN Services s ON bs.Service_Id = s.Service_Id
             WHERE bs.Booking_Id = ?)
        )
        WHERE Booking_Id = ?
        """;

        try (Connection conn = new DBcontext().c; PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, bookingId);
            ps.setInt(2, bookingId);
            ps.setInt(3, bookingId);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Integer> getSelectedServiceIdsForBooking(int bookingId) {
        List<Integer> list = new ArrayList<>();
        String sql = "SELECT Service_Id FROM BookingService WHERE Booking_Id = ?";
        try (Connection conn = new DBcontext().c; PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, bookingId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(rs.getInt("Service_Id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void updateBookingServices(int bookingId, int[] serviceIds, int[] quantities) {
        try (Connection conn = new DBcontext().c) {
            conn.setAutoCommit(false);

            String deleteSql = "DELETE FROM BookingService WHERE Booking_Id = ?";
            try (PreparedStatement ps = conn.prepareStatement(deleteSql)) {
                ps.setInt(1, bookingId);
                ps.executeUpdate();
            }

            String insertSql = "INSERT INTO BookingService (Booking_Id, Service_Id, Quantity) VALUES (?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
                for (int i = 0; i < serviceIds.length; i++) {
                    ps.setInt(1, bookingId);
                    ps.setInt(2, serviceIds[i]);
                    ps.setInt(3, quantities[i]);
                    ps.addBatch();
                }
                ps.executeBatch();
            }

            updateTotalAmountIncludingRoom(bookingId);

            conn.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteAllServicesFromBooking(int bookingId) {
        String sql = "DELETE FROM BookingService WHERE Booking_Id = ?";
        try (Connection conn = new DBcontext().c; PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, bookingId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<String, Integer> getSelectedServiceNamesWithQuantities(int bookingId) {
        Map<String, Integer> serviceMap = new LinkedHashMap<>();
        String sql = """
        SELECT s.Service_Name, bs.Quantity
        FROM BookingService bs
        JOIN Services s ON bs.Service_Id = s.Service_Id
        WHERE bs.Booking_Id = ?
    """;

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, bookingId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                serviceMap.put(rs.getString("Service_Name"), rs.getInt("Quantity"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serviceMap;
    }

}
