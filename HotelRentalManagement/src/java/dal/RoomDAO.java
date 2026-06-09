/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import model.Room;
import model.RoomType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import model.RoomAmenity;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import model.FeedbackRoom;

/**
 *
 * @author admin
 */
public class RoomDAO extends DBcontext {

    public static List<Room> getAllRooms() {
        List<Room> rooms = new ArrayList<>();
        Connection conn = new dal.DBcontext().c;
        if (conn == null) {
            return rooms;
        }

        String sql = "SELECT r.Room_Id, r.Room_Number, r.Room_Type_Id, r.Booked, r.Capacity, r.Size, r.Bed, "
                + "r.Created_At, r.Status, r.Updated_At, "
                + "rt.Type_Name, rt.Description, rt.Base_Price "
                + "FROM Rooms r "
                + "JOIN RoomType rt ON r.Room_Type_Id = rt.Room_Type_Id";

        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Room room = extractRoomFromResultSet(rs);
                rooms.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rooms;
    }

    private static Room extractRoomFromResultSet(ResultSet rs) throws SQLException {
        Room room = new Room();

        room.setRoomId(rs.getInt("Room_Id"));
        room.setRoomNumber(rs.getInt("Room_Number"));
        room.setBooked(rs.getBoolean("Booked"));
        room.setCapacity(rs.getString("Capacity"));
        room.setSize(rs.getString("Size"));
        room.setBed(rs.getInt("Bed"));
        room.setCreatedAt(rs.getTimestamp("Created_At"));
        room.setStatus(rs.getString("Status"));
        room.setUpdatedAt(rs.getTimestamp("Updated_At"));

        RoomType roomType = new RoomType();
        roomType.setRoomTypeId(rs.getInt("Room_Type_Id"));
        roomType.setTypeName(rs.getString("Type_Name"));
        roomType.setDescription(rs.getString("Description"));
        roomType.setBasePrice(rs.getDouble("Base_Price"));

        room.setRoomType(roomType);

        return room;
    }

    public static boolean createRoom(Room room) {
        Connection conn = new dal.DBcontext().c;
        if (conn == null) {
            return false;
        }

        String sql = "INSERT INTO Rooms (Room_Number, Room_Type_Id, Booked, Capacity, Size, Bed, Status, Created_At, Updated_At) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String insertImageSql = "INSERT INTO RoomImages (Room_Id, Image_Url) VALUES (?, ?)";

        try {
            conn.setAutoCommit(false);

            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, room.getRoomNumber());
            stmt.setInt(2, room.getRoomType().getRoomTypeId());
            stmt.setBoolean(3, room.isBooked());

            stmt.setString(4, room.getCapacity());
            stmt.setString(5, room.getSize());
            stmt.setInt(6, room.getBed());
            stmt.setString(7, room.getStatus());

            Timestamp now = new Timestamp(System.currentTimeMillis());
            stmt.setTimestamp(8, now);
            stmt.setTimestamp(9, now);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                conn.rollback();
                return false;
            }

            int roomId;
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    roomId = generatedKeys.getInt(1);
                } else {
                    conn.rollback();
                    return false;
                }
            }

            if (room.getImageUrls() != null && !room.getImageUrls().isEmpty()) {
                PreparedStatement imgStmt = conn.prepareStatement(insertImageSql);
                for (String url : room.getImageUrls()) {
                    imgStmt.setInt(1, roomId);
                    imgStmt.setString(2, url);
                    imgStmt.addBatch();
                }
                imgStmt.executeBatch();
            }

            conn.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean deleteRoom(int roomId) {
        Connection conn = new dal.DBcontext().c;
        try {
            String deleteImages = "DELETE FROM RoomImages WHERE Room_Id = ?";
            String deleteRoom = "DELETE FROM Rooms WHERE Room_Id = ?";

            PreparedStatement ps1 = conn.prepareStatement(deleteImages);
            ps1.setInt(1, roomId);
            ps1.executeUpdate();

            PreparedStatement ps2 = conn.prepareStatement(deleteRoom);
            ps2.setInt(1, roomId);
            int rowsAffected = ps2.executeUpdate();

            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isRoomNumberExists(int roomNumber) {
        Connection conn = new dal.DBcontext().c;
        if (conn == null) {
            return false;
        }

        String sql = "SELECT COUNT(*) FROM Rooms WHERE Room_Number = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, roomNumber);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static List<RoomType> getAllRoomTypes() {
        List<RoomType> roomTypes = new ArrayList<>();

        Connection conn = new dal.DBcontext().c;
        if (conn == null) {
            return roomTypes;
        }

        String sql = "SELECT Room_Type_Id, Type_Name, Description, Base_Price FROM RoomType";

        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                RoomType rt = extractRoomTypeFromResultSet(rs);
                roomTypes.add(rt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return roomTypes;
    }

    private static RoomType extractRoomTypeFromResultSet(ResultSet rs) throws SQLException {
        RoomType rt = new RoomType();
        rt.setRoomTypeId(rs.getInt("Room_Type_Id"));
        rt.setTypeName(rs.getString("Type_Name"));
        rt.setDescription(rs.getString("Description"));

        rt.setBasePrice(rs.getDouble("Base_Price"));
        return rt;
    }

    public static boolean saveRoomImage(int roomId, String imageUrl) {
        String sql = "INSERT INTO RoomImages (Room_Id, Image_Url) VALUES (?, ?)";

        Connection conn = new dal.DBcontext().c;
        if (conn == null) {
            return false;
        }

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, roomId);
            ps.setString(2, imageUrl);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Room viewRoom(int roomId) {
        System.out.println("viewRoom bắt đầu với roomId = " + roomId);
        Connection conn = new dal.DBcontext().c;
        if (conn == null) {
            System.out.println("Không kết nối được database!");
            return null;
        }

        Room room = null;

        try {
            String sqlRoom = "SELECT r.Room_Id, r.Room_Number, r.Booked, r.Capacity, r.Size, r.Bed, r.Status, r.Created_At, r.Updated_At, "
                    + "rt.Room_Type_Id, rt.Type_Name, rt.Description, rt.Base_Price "
                    + "FROM Rooms r JOIN RoomType rt ON r.Room_Type_Id = rt.Room_Type_Id "
                    + "WHERE r.Room_Id = ?";

            try (PreparedStatement psRoom = conn.prepareStatement(sqlRoom)) {
                psRoom.setInt(1, roomId);
                try (ResultSet rsRoom = psRoom.executeQuery()) {
                    if (rsRoom.next()) {
                        System.out.println("Tìm thấy phòng với id = " + roomId);
                        room = new Room();
                        room.setRoomId(rsRoom.getInt("Room_Id"));
                        room.setRoomNumber(rsRoom.getInt("Room_Number"));
                        room.setBooked(rsRoom.getBoolean("Booked"));
                        room.setCapacity(rsRoom.getString("Capacity"));
                        room.setSize(rsRoom.getString("Size"));
                        room.setBed(rsRoom.getInt("Bed"));

                        room.setStatus(rsRoom.getString("Status"));
                        room.setCreatedAt(rsRoom.getTimestamp("Created_At"));
                        room.setUpdatedAt(rsRoom.getTimestamp("Updated_At"));

                        RoomType roomType = new RoomType();
                        roomType.setRoomTypeId(rsRoom.getInt("Room_Type_Id"));
                        roomType.setTypeName(rsRoom.getString("Type_Name"));
                        roomType.setDescription(rsRoom.getString("Description"));
                        roomType.setBasePrice(rsRoom.getDouble("Base_Price"));
                        room.setRoomType(roomType);
                    } else {
                        System.out.println("Không tìm thấy phòng với id = " + roomId);
                        return null;
                    }
                }
            }

            String sqlImages = "SELECT Image_Url FROM RoomImages WHERE Room_Id = ?";
            try (PreparedStatement psImages = conn.prepareStatement(sqlImages)) {
                psImages.setInt(1, roomId);
                try (ResultSet rsImages = psImages.executeQuery()) {
                    List<String> images = new ArrayList<>();
                    while (rsImages.next()) {
                        images.add(rsImages.getString("Image_Url"));
                    }
                    room.setImageUrls(images);
                    System.out.println("Số hình ảnh: " + images.size());
                }
            }

            String sqlAmenities = "SELECT Room_Amenity_Id, Amenity_Name FROM RoomAmenity WHERE Room_Type_Id = ?";
            try (PreparedStatement psAmenities = conn.prepareStatement(sqlAmenities)) {
                psAmenities.setInt(1, room.getRoomType().getRoomTypeId());
                try (ResultSet rsAmenities = psAmenities.executeQuery()) {
                    List<RoomAmenity> amenities = new ArrayList<>();
                    while (rsAmenities.next()) {
                        RoomAmenity amenity = new RoomAmenity();
                        amenity.setRoomAmenityId(rsAmenities.getInt("Room_Amenity_Id"));
                        amenity.setAmenityName(rsAmenities.getString("Amenity_Name"));
                        amenity.setRoomType(room.getRoomType());
                        amenities.add(amenity);
                    }
                    room.setAmenities(amenities);
                    System.out.println("Số tiện ích: " + amenities.size());
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Lỗi khi truy vấn dữ liệu phòng: " + e.getMessage());
            return null;
        }

        return room;
    }

    public static boolean editRoom(int roomId, Room room) throws SQLException {
        Connection conn = new dal.DBcontext().c;
        if (conn == null) {
            return false;
        }

        String checkRoomExistsSql = "SELECT COUNT(*) FROM Rooms WHERE Room_Id = ?";
        String checkRoomNumberSql = "SELECT COUNT(*) FROM Rooms WHERE Room_Number = ? AND Room_Id <> ?";
        String updateRoomSql = "UPDATE Rooms SET Room_Number = ?, Room_Type_Id = ?, Booked = ?, Capacity = ?, Size = ?, Bed = ?, Status = ?, Updated_At = ? WHERE Room_Id = ?";
        String deleteImagesSql = "DELETE FROM RoomImages WHERE Room_Id = ?";
        String insertImageSql = "INSERT INTO RoomImages (Room_Id, Image_Url) VALUES (?, ?)";

        try {
            conn.setAutoCommit(false);

            try (PreparedStatement checkStmt = conn.prepareStatement(checkRoomExistsSql)) {
                checkStmt.setInt(1, roomId);
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) == 0) {
                        conn.rollback();
                        throw new SQLException("Phòng không tồn tại.");
                    }
                }
            }

            try (PreparedStatement checkNumberStmt = conn.prepareStatement(checkRoomNumberSql)) {
                checkNumberStmt.setInt(1, room.getRoomNumber());
                checkNumberStmt.setInt(2, roomId);
                try (ResultSet rs = checkNumberStmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        conn.rollback();
                        throw new SQLException("Số phòng đã tồn tại.");
                    }
                }
            }

            try (PreparedStatement ps = conn.prepareStatement(updateRoomSql)) {
                ps.setInt(1, room.getRoomNumber());
                ps.setInt(2, room.getRoomType().getRoomTypeId());
                ps.setBoolean(3, room.isBooked());
                ps.setString(4, room.getCapacity());
                ps.setString(5, room.getSize());
                ps.setInt(6, room.getBed());
                ps.setString(7, room.getStatus());
                ps.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
                ps.setInt(9, roomId);

                int affected = ps.executeUpdate();
                if (affected == 0) {
                    conn.rollback();
                    throw new SQLException("Không thể cập nhật phòng.");
                }
            }

            if (room.getImageUrls() != null) {
                // Xoá ảnh cũ
                try (PreparedStatement psDel = conn.prepareStatement(deleteImagesSql)) {
                    psDel.setInt(1, roomId);
                    psDel.executeUpdate();
                }

                if (!room.getImageUrls().isEmpty()) {
                    try (PreparedStatement psIns = conn.prepareStatement(insertImageSql)) {
                        for (String url : room.getImageUrls()) {
                            psIns.setInt(1, roomId);
                            psIns.setString(2, url);
                            psIns.addBatch();
                        }
                        psIns.executeBatch();
                    }
                }
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            throw e;
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<String> getRoomImageUrls(int roomId) {
        List<String> imageUrls = new ArrayList<>();
        Connection conn = new dal.DBcontext().c;

        if (conn == null) {
            return imageUrls;
        }

        String sql = "SELECT Image_Url FROM RoomImages WHERE Room_Id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, roomId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    imageUrls.add(rs.getString("Image_Url"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return imageUrls;
    }

    public static List<Room> getRoomsWithImageAndPriceByPage(int pageIndex, int pageSize) {
        List<Room> rooms = new ArrayList<>();
        Connection conn = new dal.DBcontext().c;
        if (conn == null) {
            return rooms;
        }

        String sql = "SELECT r.Room_Id, r.Room_Number, r.Room_Type_Id, r.Booked, r.Capacity, r.Size, r.Bed, "
                + "r.Created_At, r.Status, r.Updated_At, "
                + "rt.Type_Name, rt.Description, rt.Base_Price, "
                + "(SELECT TOP 1 Image_Url FROM RoomImages WHERE Room_Id = r.Room_Id ORDER BY Upload_At ASC) AS MainImageUrl "
                + "FROM Rooms r "
                + "JOIN RoomType rt ON r.Room_Type_Id = rt.Room_Type_Id "
                + "ORDER BY r.Room_Id "
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, (pageIndex - 1) * pageSize);
            ps.setInt(2, pageSize);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Room room = new Room();
                room.setRoomId(rs.getInt("Room_Id"));
                room.setRoomNumber(rs.getInt("Room_Number"));
                room.setBooked(rs.getBoolean("Booked"));
                room.setCapacity(rs.getString("Capacity"));
                room.setSize(rs.getString("Size"));
                room.setBed(rs.getInt("Bed"));
                room.setCreatedAt(rs.getTimestamp("Created_At"));
                room.setStatus(rs.getString("Status"));
                room.setUpdatedAt(rs.getTimestamp("Updated_At"));

                RoomType roomType = new RoomType();
                roomType.setRoomTypeId(rs.getInt("Room_Type_Id"));
                roomType.setTypeName(rs.getString("Type_Name"));
                roomType.setDescription(rs.getString("Description"));
                roomType.setBasePrice(rs.getDouble("Base_Price"));
                room.setRoomType(roomType);

                String mainImage = rs.getString("MainImageUrl");
                room.setImageUrls(mainImage != null ? Collections.singletonList(mainImage) : new ArrayList<>());

                rooms.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }

    public static int getTotalRoomTypeCount() {
        int count = 0;
        String sql = "SELECT COUNT(DISTINCT Room_Type_Id) FROM Rooms";
        try (Connection conn = new DBcontext().c; PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public static List<Room> getOneRoomPerTypeWithImageAndPrice(int pageIndex, int pageSize) {
        List<Room> rooms = new ArrayList<>();
        Connection conn = new dal.DBcontext().c;
        if (conn == null) {
            return rooms;
        }

        String sql = "WITH RoomWithRowNum AS ("
                + " SELECT r.Room_Id, r.Room_Number, r.Room_Type_Id, r.Booked, r.Capacity, r.Size, r.Bed, "
                + " r.Created_At, r.Status, r.Updated_At, "
                + " rt.Type_Name, rt.Description, rt.Base_Price, "
                + " (SELECT TOP 1 Image_Url FROM RoomImages WHERE Room_Id = r.Room_Id ORDER BY Upload_At ASC) AS MainImageUrl, "
                + " ROW_NUMBER() OVER (PARTITION BY r.Room_Type_Id ORDER BY r.Room_Id) AS rn "
                + " FROM Rooms r "
                + " JOIN RoomType rt ON r.Room_Type_Id = rt.Room_Type_Id "
                + ") "
                + "SELECT * FROM RoomWithRowNum "
                + "WHERE rn = 1 "
                + "ORDER BY Room_Type_Id "
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, (pageIndex - 1) * pageSize); // số dòng bỏ qua
            ps.setInt(2, pageSize); // số dòng lấy
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Room room = new Room();
                room.setRoomId(rs.getInt("Room_Id"));
                room.setRoomNumber(rs.getInt("Room_Number"));
                room.setBooked(rs.getBoolean("Booked"));
                room.setCapacity(rs.getString("Capacity"));
                room.setSize(rs.getString("Size"));
                room.setBed(rs.getInt("Bed"));
                room.setCreatedAt(rs.getTimestamp("Created_At"));
                room.setStatus(rs.getString("Status"));
                room.setUpdatedAt(rs.getTimestamp("Updated_At"));

                RoomType roomType = new RoomType();
                roomType.setRoomTypeId(rs.getInt("Room_Type_Id"));
                roomType.setTypeName(rs.getString("Type_Name"));
                roomType.setDescription(rs.getString("Description"));
                roomType.setBasePrice(rs.getDouble("Base_Price"));
                room.setRoomType(roomType);

                String mainImage = rs.getString("MainImageUrl");
                room.setImageUrls(mainImage != null ? Collections.singletonList(mainImage) : new ArrayList<>());

                rooms.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rooms;
    }

    public static int getTotalRoomCount() {
        int count = 0;
        Connection conn = new dal.DBcontext().c;
        if (conn == null) {
            return 0;
        }

        String sql = "SELECT COUNT(*) FROM Rooms";
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public static Room getRoomDetailById(int roomId) {
        Room room = null;
        Connection conn = new dal.DBcontext().c;
        if (conn == null) {
            return null;
        }

        String sql = "SELECT r.Room_Id, r.Room_Number, r.Room_Type_Id, r.Booked, r.Capacity, r.Size, r.Bed, "
                + "r.Created_At, r.Status, r.Updated_At, "
                + "rt.Type_Name, rt.Description, rt.Base_Price "
                + "FROM Rooms r "
                + "JOIN RoomType rt ON r.Room_Type_Id = rt.Room_Type_Id "
                + "WHERE r.Room_Id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, roomId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                room = new Room();
                room.setRoomId(rs.getInt("Room_Id"));
                room.setRoomNumber(rs.getInt("Room_Number"));
                room.setBooked(rs.getBoolean("Booked"));
                room.setCapacity(rs.getString("Capacity"));
                room.setSize(rs.getString("Size"));
                room.setBed(rs.getInt("Bed"));
                room.setCreatedAt(rs.getTimestamp("Created_At"));
                room.setStatus(rs.getString("Status"));
                room.setUpdatedAt(rs.getTimestamp("Updated_At"));

                RoomType rt = new RoomType();
                rt.setRoomTypeId(rs.getInt("Room_Type_Id"));
                rt.setTypeName(rs.getString("Type_Name"));
                rt.setDescription(rs.getString("Description"));
                rt.setBasePrice(rs.getDouble("Base_Price"));
                room.setRoomType(rt);
                room.setImageUrls(getRoomImagesByRoomId(roomId));
                room.setAmenities(getAmenitiesByRoomTypeId(rt.getRoomTypeId()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return room;
    }

    public static List<String> getRoomImagesByRoomId(int roomId) {
        List<String> images = new ArrayList<>();
        Connection conn = new dal.DBcontext().c;
        String sql = "SELECT Image_Url FROM RoomImages WHERE Room_Id = ? ORDER BY Upload_At ASC";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, roomId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                images.add(rs.getString("Image_Url"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return images;
    }

    public static List<RoomAmenity> getAmenitiesByRoomTypeId(int roomTypeId) {
        List<RoomAmenity> amenities = new ArrayList<>();
        Connection conn = new dal.DBcontext().c;
        String sql = "SELECT Room_Amenity_Id, Amenity_Name FROM RoomAmenity WHERE Room_Type_Id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, roomTypeId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                RoomAmenity ra = new RoomAmenity();
                ra.setRoomAmenityId(rs.getInt("Room_Amenity_Id"));
                ra.setAmenityName(rs.getString("Amenity_Name"));
                amenities.add(ra);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return amenities;
    }

    public static List<Room> getAvailableRooms() {
        return getAllRooms().stream()
                .filter(room -> !room.isBooked())
                .collect(Collectors.toList());
    }

    public List<Room> searchAvailableRooms(LocalDate checkin, LocalDate checkout, int guests, String roomType, int bed, double maxPrice) {
        List<Room> list = new ArrayList<>();

        String sql = """
   SELECT r.*, rt.Type_Name, rt.Base_Price
   FROM Rooms r
   JOIN RoomType rt ON r.Room_Type_Id = rt.Room_Type_Id
   WHERE rt.Base_Price <= ?
   AND r.Bed >= ?
   AND NOT EXISTS (
       SELECT 1 FROM Booking b
       WHERE b.Room_Id = r.Room_Id
         AND NOT (
             b.CheckOut_Date <= ? OR b.CheckIn_Date >= ?
         )
   )
""";

        if (roomType != null && !roomType.isEmpty()) {
            sql += " AND rt.Type_Name = ?";
        }

        sql += " ORDER BY rt.Base_Price ASC, r.Bed ASC";

        try (Connection conn = new DBcontext().c; PreparedStatement ps = conn.prepareStatement(sql)) {
            int i = 1;
            ps.setDouble(i++, maxPrice);
            ps.setInt(i++, bed);
            ps.setDate(i++, Date.valueOf(checkin));
            ps.setDate(i++, Date.valueOf(checkout));

            if (roomType != null && !roomType.isEmpty()) {
                ps.setString(i++, roomType);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String capacityRaw = rs.getString("Capacity");
                int roomCapacity = extractTotalGuests(capacityRaw);
                if (roomCapacity >= guests) {
                    Room r = new Room();
                    r.setRoomId(rs.getInt("Room_Id"));
                    r.setRoomNumber(rs.getInt("Room_Number"));
                    r.setBed(rs.getInt("Bed"));
                    r.setCapacity(capacityRaw);
                    r.setStatus(rs.getString("Status"));

                    RoomType rt = new RoomType();
                    rt.setTypeName(rs.getString("Type_Name"));
                    rt.setBasePrice(rs.getBigDecimal("Base_Price").doubleValue());
                    r.setRoomType(rt);

                    list.add(r);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public static int extractTotalGuests(String input) {
        int total = 0;
        Matcher matcher = Pattern.compile("\\d+").matcher(input);
        while (matcher.find()) {
            total += Integer.parseInt(matcher.group());
        }
        return total == 0 ? 1 : total;
    }

    public boolean insertRoomsFeedback(int roomId, int bookingId, int userId, int rating, String comment) {
        String sql = "INSERT INTO FeedbackRoom (Room_Id,Booking_Id, User_Id, Rating, Comment, Created_At) "
                + "VALUES (?, ?, ?,?, ?, GETDATE())";

        try (Connection conn = new DBcontext().c; PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, roomId);
            ps.setInt(2, bookingId);
            ps.setInt(3, userId);
            ps.setInt(4, rating);
            ps.setString(5, comment);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean hasFeedback(int userId, int bookingId) {
        String sql = "SELECT 1 FROM FeedbackRoom WHERE User_Id = ? AND Booking_Id = ?";
        try (Connection conn = new DBcontext().c; PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, bookingId);

            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<FeedbackRoom> getFeedbackRoomsWithPaging(int offset, int pageSize) {
        List<FeedbackRoom> feedbackList = new ArrayList<>();

        String sql = """
        SELECT 
            fr.Feedback_Room_Id AS feedbackId,
            fr.User_Id AS userId,
            fr.Room_Id AS roomId,
            fr.Rating AS rating,
            fr.Booking_Id AS bookingId,
            r.Room_Number AS roomNumber,
            fr.Comment AS content,
            u.Full_Name AS authorName,
            fr.Created_At AS createAt,
            b.CheckIn_Date AS checkIn,
            b.CheckOut_Date AS checkOut,
            fr.Updated_At
        FROM (
            SELECT Feedback_Room_Id
            FROM FeedbackRoom
            ORDER BY Created_At DESC
            OFFSET ? ROWS FETCH NEXT ? ROWS ONLY
        ) AS sub
        JOIN FeedbackRoom fr ON sub.Feedback_Room_Id = fr.Feedback_Room_Id
        JOIN [User] u ON fr.User_Id = u.User_Id
        JOIN Booking b ON fr.Booking_Id = b.Booking_Id
        JOIN Rooms r ON fr.Room_Id = r.Room_Id
    """;

        try (PreparedStatement st = c.prepareStatement(sql)) {
            st.setInt(1, offset);
            st.setInt(2, pageSize);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    FeedbackRoom f = new FeedbackRoom(
                            rs.getInt("feedbackId"),
                            rs.getInt("userId"),
                            rs.getInt("roomId"),
                            rs.getInt("rating"),
                            rs.getInt("bookingId"),
                            rs.getInt("roomNumber"),
                            rs.getString("content"),
                            rs.getString("authorName"),
                            rs.getDate("createAt"),
                            rs.getDate("checkIn"),
                            rs.getDate("checkOut"),
                            rs.getDate("Updated_At")
                    );
                    feedbackList.add(f);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return feedbackList;
    }

    public int countAllFeedbackRooms() {
        String sql = "SELECT COUNT(*) FROM FeedbackRoom";

        try (PreparedStatement st = c.prepareStatement(sql); ResultSet rs = st.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return 0;
    }

    public List<FeedbackRoom> getAllFeedbackRoomsSortedByRating(String sortDirection) {
        List<FeedbackRoom> feedbackList = new ArrayList<>();

        String order = "ASC";
        if ("desc".equalsIgnoreCase(sortDirection)) {
            order = "DESC";
        } else if (!"asc".equalsIgnoreCase(sortDirection)) {
            order = "ASC";
        }

        String sql = """
        SELECT 
            fr.Feedback_Room_Id AS feedbackId,
            fr.User_Id AS userId,
            fr.Room_Id AS roomId,
            fr.Rating AS rating,
            fr.Booking_Id AS bookingId,
            r.Room_Number AS roomNumber,
            fr.Comment AS content,
            u.Full_Name AS authorName,
            fr.Created_At AS createAt,
            b.CheckIn_Date AS checkIn,
            b.CheckOut_Date AS checkOut
        FROM 
            FeedbackRoom fr
        JOIN [User] u ON fr.User_Id = u.User_Id
        JOIN Booking b ON fr.Booking_Id = b.Booking_Id
        JOIN Rooms r ON fr.Room_Id = r.Room_Id
        ORDER BY fr.Rating """ + " " + order;

        try {
            PreparedStatement st = c.prepareStatement(sql);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    FeedbackRoom f = new FeedbackRoom(
                            rs.getInt("feedbackId"),
                            rs.getInt("userId"),
                            rs.getInt("roomId"),
                            rs.getInt("rating"),
                            rs.getInt("bookingId"),
                            rs.getInt("roomNumber"),
                            rs.getString("content"),
                            rs.getString("authorName"),
                            rs.getDate("createAt"),
                            rs.getDate("checkIn"),
                            rs.getDate("checkOut")
                    );
                    feedbackList.add(f);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return feedbackList;
    }

    public boolean updateFeedbackByBookingId(int bookingId, int rating, String comment) {
        String sql = "UPDATE FeedbackRoom SET Rating = ?, Comment = ?, Updated_At = GETDATE() WHERE Booking_Id = ?";
        try (Connection conn = new DBcontext().c; PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, rating);
            ps.setString(2, comment);
            ps.setInt(3, bookingId);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public FeedbackRoom getFeedbackRoomByBookingId(int bookingId) {
        String sql = """
        SELECT 
            fr.Feedback_Room_Id AS feedbackId,
            fr.User_Id AS userId,
            fr.Room_Id AS roomId,
            fr.Rating AS rating,
            fr.Booking_Id AS bookingId,
            r.Room_Number AS roomNumber,
            fr.Comment AS content,
            u.Full_Name AS authorName,
            fr.Created_At AS createAt,
            fr.Updated_At,
            b.CheckIn_Date AS checkIn,
            b.CheckOut_Date AS checkOut
        FROM 
            FeedbackRoom fr
        JOIN [User] u ON fr.User_Id = u.User_Id
        JOIN Booking b ON fr.Booking_Id = b.Booking_Id
        JOIN Rooms r ON fr.Room_Id = r.Room_Id
        WHERE fr.Booking_Id = ?
    """;

        try {
            PreparedStatement st = c.prepareStatement(sql);
            st.setInt(1, bookingId);

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return new FeedbackRoom(
                            rs.getInt("feedbackId"),
                            rs.getInt("userId"),
                            rs.getInt("roomId"),
                            rs.getInt("rating"),
                            rs.getInt("bookingId"),
                            rs.getInt("roomNumber"),
                            rs.getString("content"),
                            rs.getString("authorName"),
                            rs.getDate("createAt"),
                            rs.getDate("checkIn"),
                            rs.getDate("checkOut"),
                            rs.getDate("Updated_At")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public List<FeedbackRoom> getFeedbackByRoomId(int roomId) {
        List<FeedbackRoom> feedbackList = new ArrayList<>();

        String sql = """
        SELECT 
            fr.Feedback_Room_Id AS feedbackId,
            fr.User_Id AS userId,
            fr.Room_Id AS roomId,
            fr.Rating AS rating,
            fr.Booking_Id AS bookingId,
            r.Room_Number AS roomNumber,
            fr.Comment AS content,
            u.Full_Name AS authorName,
            fr.Created_At AS createAt,
            b.CheckIn_Date AS checkIn,
            b.CheckOut_Date AS checkOut,
            fr.Updated_At
        FROM FeedbackRoom fr
        JOIN [User] u ON fr.User_Id = u.User_Id
        JOIN Booking b ON fr.Booking_Id = b.Booking_Id
        JOIN Rooms r ON fr.Room_Id = r.Room_Id
        WHERE fr.Room_Id = ?
        ORDER BY fr.Created_At DESC
    """;

        try (PreparedStatement st = c.prepareStatement(sql)) {
            st.setInt(1, roomId);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    FeedbackRoom f = new FeedbackRoom(
                            rs.getInt("feedbackId"),
                            rs.getInt("userId"),
                            rs.getInt("roomId"),
                            rs.getInt("rating"),
                            rs.getInt("bookingId"),
                            rs.getInt("roomNumber"),
                            rs.getString("content"),
                            rs.getString("authorName"),
                            rs.getDate("createAt"),
                            rs.getDate("checkIn"),
                            rs.getDate("checkOut"),
                            rs.getDate("Updated_At")
                    );
                    feedbackList.add(f);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return feedbackList;
    }

    public boolean deleteByBookingId(int bookingId) {
        String sql = "DELETE FROM FeedbackRoom WHERE Booking_Id = ?";

        try (PreparedStatement st = c.prepareStatement(sql)) {
            st.setInt(1, bookingId);
            int rowsAffected = st.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<FeedbackRoom> searchFeedbackRooms(String room, String rating, String user, String date) {
        List<FeedbackRoom> feedbackList = new ArrayList<>();

        StringBuilder sql = new StringBuilder("""
        SELECT 
            fr.Feedback_Room_Id AS feedbackId,
            fr.User_Id AS userId,
            fr.Room_Id AS roomId,
            fr.Rating AS rating,
            fr.Booking_Id AS bookingId,
            r.Room_Number AS roomNumber,
            fr.Comment AS content,
            u.Full_Name AS authorName,
            fr.Created_At AS createAt,
            b.CheckIn_Date AS checkIn,
            b.CheckOut_Date AS checkOut
        FROM 
            FeedbackRoom fr
        JOIN [User] u ON fr.User_Id = u.User_Id
        JOIN Booking b ON fr.Booking_Id = b.Booking_Id
        JOIN Rooms r ON fr.Room_Id = r.Room_Id
        WHERE 1=1
    """);

        List<Object> params = new ArrayList<>();

        if (room != null && !room.isEmpty()) {
            sql.append(" AND r.Room_Number = ?");
            params.add(Integer.parseInt(room));
        }

        if (rating != null && !rating.isEmpty()) {
            sql.append(" AND fr.Rating = ?");
            params.add(Integer.parseInt(rating));
        }

        if (user != null && !user.trim().isEmpty()) {
            sql.append(" AND u.Full_Name LIKE ?");
            params.add("%" + user.trim() + "%");
        }
        if (date != null && !date.trim().isEmpty()) {
            sql.append(" AND CONVERT(DATE, fr.Created_At) = ?");
            params.add(Date.valueOf(date));
        }

        try (PreparedStatement st = c.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                st.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    FeedbackRoom f = new FeedbackRoom(
                            rs.getInt("feedbackId"),
                            rs.getInt("userId"),
                            rs.getInt("roomId"),
                            rs.getInt("rating"),
                            rs.getInt("bookingId"),
                            rs.getInt("roomNumber"),
                            rs.getString("content"),
                            rs.getString("authorName"),
                            rs.getDate("createAt"),
                            rs.getDate("checkIn"),
                            rs.getDate("checkOut")
                    );
                    feedbackList.add(f);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return feedbackList;
    }

    public static List<Room> searchAvailableRooms(LocalDate checkin, LocalDate checkout, int guests, String roomType, int bed, double minPrice, double maxPrice) {
        List<Room> list = new ArrayList<>();

        String sql = """
        SELECT r.*, rt.Type_Name, rt.Base_Price, rt.Description
        FROM Rooms r
        JOIN RoomType rt ON r.Room_Type_Id = rt.Room_Type_Id
        WHERE rt.Base_Price BETWEEN ? AND ?
          AND r.Bed >= ?
    """;

        if (roomType != null && !roomType.isEmpty()) {
            sql += " AND rt.Type_Name = ?";
        }

        sql += " ORDER BY rt.Base_Price ASC, r.Bed ASC";

        try (Connection conn = new DBcontext().c; PreparedStatement ps = conn.prepareStatement(sql)) {
            int i = 1;
            ps.setDouble(i++, minPrice);
            ps.setDouble(i++, maxPrice);
            ps.setInt(i++, bed);

            if (roomType != null && !roomType.isEmpty()) {
                ps.setString(i++, roomType);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String capacityRaw = rs.getString("Capacity");
                int roomCapacity = extractTotalGuests(capacityRaw);
                if (roomCapacity >= guests) {
                    Room r = new Room();
                    r.setRoomId(rs.getInt("Room_Id"));
                    r.setRoomNumber(rs.getInt("Room_Number"));
                    r.setBed(rs.getInt("Bed"));
                    r.setCapacity(capacityRaw);
                    r.setStatus(rs.getString("Status"));
                    r.setSize(rs.getString("Size"));
                    r.setCreatedAt(rs.getTimestamp("Created_At"));
                    r.setUpdatedAt(rs.getTimestamp("Updated_At"));

                    RoomType rt = new RoomType();
                    rt.setRoomTypeId(rs.getInt("Room_Type_Id"));
                    rt.setTypeName(rs.getString("Type_Name"));
                    rt.setDescription(rs.getString("Description"));
                    rt.setBasePrice(rs.getDouble("Base_Price"));

                    r.setRoomType(rt);
                    boolean isBooked = BookingDAO.isRoomBookedInRange(r.getRoomId(), checkin, checkout);
                    r.setBooked(isBooked);

                    list.add(r);
                }
            }
            list.sort(Comparator.comparing(Room::isBooked));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public static List<Room> searchRoomsWithPagination(String roomNumber, String roomType, Integer bed,
            String status, Boolean booked, int pageIndex, int pageSize) {
        List<Room> list = new ArrayList<>();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT r.*, rt.Type_Name, rt.Description, rt.Base_Price ")
                .append("FROM Rooms r JOIN RoomType rt ON r.Room_Type_Id = rt.Room_Type_Id WHERE 1=1 ");

        List<Object> params = new ArrayList<>();

        if (roomNumber != null && !roomNumber.isEmpty()) {
            sql.append("AND r.Room_Number LIKE ? ");
            params.add("%" + roomNumber + "%");
        }
        if (roomType != null && !roomType.isEmpty()) {
            sql.append("AND rt.Type_Name LIKE ? ");
            params.add("%" + roomType + "%");
        }
        if (bed != null) {
            sql.append("AND r.Bed = ? ");
            params.add(bed);
        }
        if (status != null && !status.isEmpty()) {
            sql.append("AND r.Status = ? ");
            params.add(status);
        }
        if (booked != null) {
            sql.append("AND r.Booked = ? ");
            params.add(booked);
        }

        sql.append("ORDER BY r.Room_Id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");
        params.add((pageIndex - 1) * pageSize);
        params.add(pageSize);

        try (Connection conn = new DBcontext().c; PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Room room = new Room();
                room.setRoomId(rs.getInt("Room_Id"));
                room.setRoomNumber(rs.getInt("Room_Number"));
                room.setCapacity(rs.getString("Capacity"));
                room.setSize(rs.getString("Size"));
                room.setBed(rs.getInt("Bed"));
                room.setBooked(rs.getBoolean("Booked"));
                room.setStatus(rs.getString("Status"));
                room.setCreatedAt(rs.getTimestamp("Created_At"));
                room.setUpdatedAt(rs.getTimestamp("Updated_At"));

                RoomType rt = new RoomType();
                rt.setRoomTypeId(rs.getInt("Room_Type_Id"));
                rt.setTypeName(rs.getString("Type_Name"));
                rt.setDescription(rs.getString("Description"));
                rt.setBasePrice(rs.getDouble("Base_Price"));
                room.setRoomType(rt);

                list.add(room);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static int countFilteredRooms(String roomNumber, String roomType, Integer bed,
            String status, Boolean booked) {
        int count = 0;

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(*) FROM Rooms r JOIN RoomType rt ON r.Room_Type_Id = rt.Room_Type_Id WHERE 1=1 ");
        List<Object> params = new ArrayList<>();

        if (roomNumber != null && !roomNumber.isEmpty()) {
            sql.append("AND r.Room_Number LIKE ? ");
            params.add("%" + roomNumber + "%");
        }
        if (roomType != null && !roomType.isEmpty()) {
            sql.append("AND rt.Type_Name LIKE ? ");
            params.add("%" + roomType + "%");
        }
        if (bed != null) {
            sql.append("AND r.Bed = ? ");
            params.add(bed);
        }
        if (status != null && !status.isEmpty()) {
            sql.append("AND r.Status = ? ");
            params.add(status);
        }
        if (booked != null) {
            sql.append("AND r.Booked = ? ");
            params.add(booked);
        }

        try (Connection conn = new DBcontext().c; PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    public static List<Room> searchOnlyAvailableRooms(LocalDate checkin, LocalDate checkout,
            int guests, String roomType, int bed,
            double minPrice, double maxPrice) {

        List<Room> list = new ArrayList<>();

        String sql = """
    SELECT r.*, rt.Type_Name, rt.Base_Price, rt.Description
    FROM Rooms r
    JOIN RoomType rt ON r.Room_Type_Id = rt.Room_Type_Id
    WHERE rt.Base_Price BETWEEN ? AND ?
      AND r.Bed >= ?
      AND r.Room_Id NOT IN (
          SELECT Room_Id FROM Booking
          WHERE NOT (CheckOut_Date <= ? OR CheckIn_Date >= ?)
            AND Status IN ( 'checkin', 'approve','pending')
      )
""";

        if (roomType != null && !roomType.isEmpty()) {
            sql += " AND rt.Type_Name = ?";
        }

        sql += " ORDER BY rt.Base_Price ASC, r.Bed ASC";

        try (Connection conn = new DBcontext().c; PreparedStatement ps = conn.prepareStatement(sql)) {

            int i = 1;
            ps.setDouble(i++, minPrice);
            ps.setDouble(i++, maxPrice);
            ps.setInt(i++, bed);

            LocalDateTime checkinDateTime = checkin.atTime(14, 0);
            LocalDateTime checkoutDateTime = checkout.atTime(12, 0);

            ps.setTimestamp(i++, Timestamp.valueOf(checkinDateTime));
            ps.setTimestamp(i++, Timestamp.valueOf(checkoutDateTime));

            if (roomType != null && !roomType.isEmpty()) {
                ps.setString(i++, roomType);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String capacityRaw = rs.getString("Capacity");
                int roomCapacity = extractTotalGuests(capacityRaw);

                if (roomCapacity >= guests) {
                    Room r = new Room();
                    r.setRoomId(rs.getInt("Room_Id"));
                    r.setRoomNumber(rs.getInt("Room_Number"));
                    r.setBed(rs.getInt("Bed"));
                    r.setCapacity(capacityRaw);
                    r.setStatus(rs.getString("Status"));
                    r.setSize(rs.getString("Size"));
                    r.setCreatedAt(rs.getTimestamp("Created_At"));
                    r.setUpdatedAt(rs.getTimestamp("Updated_At"));

                    RoomType rt = new RoomType();
                    rt.setRoomTypeId(rs.getInt("Room_Type_Id"));
                    rt.setTypeName(rs.getString("Type_Name"));
                    rt.setDescription(rs.getString("Description"));
                    rt.setBasePrice(rs.getDouble("Base_Price"));

                    r.setRoomType(rt);
                    list.add(r);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public static double getAverageRatingByRoomTypeId(int roomTypeId) {
        double avgRating = 0.0;
        String sql = """
        SELECT AVG(CAST(fr.Rating AS FLOAT)) AS avg_rating
        FROM FeedbackRoom fr
        JOIN Rooms r ON fr.Room_Id = r.Room_Id
        WHERE r.Room_Type_Id = ?
    """;

        try (Connection conn = new DBcontext().c; PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, roomTypeId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                avgRating = rs.getDouble("avg_rating");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return avgRating;
    }

    public static void main(String[] args) {

        LocalDate checkin = LocalDate.of(2025, 7, 7);
        LocalDate checkout = LocalDate.of(2025, 7, 8);

        int guests = 2;
        String roomType = "Phòng Cổ điển";
        int bed = 1;
        double minPrice = 0;
        double maxPrice = 10000000;

        System.out.println("🔍 Đang tìm phòng...");
        System.out.println("  ▶ Check-in: " + checkin.atTime(14, 0));
        System.out.println("  ▶ Check-out: " + checkout.atTime(12, 0));
        System.out.println("  ▶ Room type: " + roomType);
        System.out.println("  ▶ Guests: " + guests);

        List<Room> rooms = searchOnlyAvailableRooms(checkin, checkout, guests, roomType, bed, minPrice, maxPrice);

        System.out.println("✅ Tìm thấy " + rooms.size() + " phòng phù hợp:");
        for (Room r : rooms) {
            System.out.println("----------------------------------------------------");
            System.out.println("Phòng #: " + r.getRoomNumber());
            System.out.println("Loại: " + r.getRoomType().getTypeName());
            System.out.println("Giường: " + r.getBed());
            System.out.println("Giá: " + r.getRoomType().getBasePrice());
            System.out.println("Trạng thái: " + r.getStatus());
            System.out.println("ID phòng: " + r.getRoomId());
        }

        if (rooms.isEmpty()) {
            System.out.println("⚠️ Không có phòng trống trong thời gian này.");
        }
    }

}
