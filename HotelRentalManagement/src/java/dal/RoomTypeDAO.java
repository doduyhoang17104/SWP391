/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import model.Room;
import model.RoomType;

/**
 *
 * @author admin
 */
public class RoomTypeDAO {

    public static List<RoomType> getAllRoomTypesWithRooms() {
        Map<Integer, RoomType> roomTypeMap = new LinkedHashMap<>();

        String sql = "SELECT rt.Room_Type_Id, rt.Type_Name, rt.Description, rt.Base_Price, "
                + "r.Room_Id, r.Room_Number "
                + "FROM RoomType rt "
                + "LEFT JOIN Rooms r ON rt.Room_Type_Id = r.Room_Type_Id "
                + "ORDER BY rt.Room_Type_Id";

        try (Connection conn = new dal.DBcontext().c; PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int roomTypeId = rs.getInt("Room_Type_Id");

                RoomType rt = roomTypeMap.get(roomTypeId);
                if (rt == null) {
                    rt = new RoomType();
                    rt.setRoomTypeId(roomTypeId);
                    rt.setTypeName(rs.getString("Type_Name"));
                    rt.setDescription(rs.getString("Description"));
                    rt.setBasePrice(rs.getDouble("Base_Price"));
                    roomTypeMap.put(roomTypeId, rt);
                }

                int roomId = rs.getInt("Room_Id");
                if (roomId != 0) {
                    Room room = new Room();
                    room.setRoomId(roomId);
                    room.setRoomNumber(rs.getInt("Room_Number"));
                    rt.getRooms().add(room);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>(roomTypeMap.values());
    }

    public static boolean addRoomType(RoomType roomType) {
        String sql = "INSERT INTO RoomType (Type_Name, Description, Base_Price) VALUES (?, ?, ?)";
        try (Connection conn = new dal.DBcontext().c; PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, roomType.getTypeName());
            ps.setString(2, roomType.getDescription());
            ps.setDouble(3, roomType.getBasePrice());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isTypeNameExists(String typeName) {
        String sql = "SELECT 1 FROM RoomType WHERE Type_Name = ?";
        try (Connection conn = new dal.DBcontext().c; PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, typeName);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean updateRoomType(RoomType roomType) {
        String sql = "UPDATE RoomType SET Type_Name = ?, Description = ?, Base_Price = ? WHERE Room_Type_Id = ?";
        try (Connection conn = new dal.DBcontext().c; PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, roomType.getTypeName());
            ps.setString(2, roomType.getDescription());
            ps.setDouble(3, roomType.getBasePrice());
            ps.setInt(4, roomType.getRoomTypeId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<String> getAmenitiesByRoomTypeId(int roomTypeId) {
    List<String> amenities = new ArrayList<>();
    String sql = "SELECT Amenity_Name FROM RoomAmenity WHERE Room_Type_Id = ?";
    try (Connection conn = new dal.DBcontext().c;
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, roomTypeId);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                amenities.add(rs.getString("Amenity_Name"));
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return amenities;
}

    public static RoomType getRoomTypeById(int id) {
        String sql = "SELECT Room_Type_Id, Type_Name, Description, Base_Price FROM RoomType WHERE Room_Type_Id = ?";
        try (Connection conn = new dal.DBcontext().c; PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    RoomType rt = new RoomType();
                    rt.setRoomTypeId(rs.getInt("Room_Type_Id"));
                    rt.setTypeName(rs.getString("Type_Name"));
                    rt.setDescription(rs.getString("Description"));
                    rt.setBasePrice(rs.getDouble("Base_Price"));
                    return rt;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean deleteRoomType(int roomTypeId) {
        String checkSql = "SELECT COUNT(*) FROM Rooms WHERE Room_Type_Id = ?";
        String deleteSql = "DELETE FROM RoomType WHERE Room_Type_Id = ?";

        try (Connection conn = new dal.DBcontext().c; PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {

            checkStmt.setInt(1, roomTypeId);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return false;
            }
            try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
                deleteStmt.setInt(1, roomTypeId);
                return deleteStmt.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static List<RoomType> getAllRoomTypesWithRoomsAndAmenities() {
        Map<Integer, RoomType> roomTypeMap = new LinkedHashMap<>();

        String sql = "SELECT rt.Room_Type_Id, rt.Type_Name, rt.Description, rt.Base_Price, "
                + "r.Room_Id, r.Room_Number "
                + "FROM RoomType rt "
                + "LEFT JOIN Rooms r ON rt.Room_Type_Id = r.Room_Type_Id "
                + "ORDER BY rt.Room_Type_Id";

        try (Connection conn = new dal.DBcontext().c; PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int roomTypeId = rs.getInt("Room_Type_Id");

                RoomType rt = roomTypeMap.get(roomTypeId);
                if (rt == null) {
                    rt = new RoomType();
                    rt.setRoomTypeId(roomTypeId);
                    rt.setTypeName(rs.getString("Type_Name"));
                    rt.setDescription(rs.getString("Description"));
                    rt.setBasePrice(rs.getDouble("Base_Price"));
                    rt.setRooms(new ArrayList<>());
                    rt.setAmenities(getAmenitiesByRoomType(conn, roomTypeId)); // gọi riêng trong kết nối

                    roomTypeMap.put(roomTypeId, rt);
                }

                int roomId = rs.getInt("Room_Id");
                if (roomId != 0) {
                    Room room = new Room();
                    room.setRoomId(roomId);
                    room.setRoomNumber(rs.getInt("Room_Number"));
                    rt.getRooms().add(room);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>(roomTypeMap.values());
    }

    private static List<String> getAmenitiesByRoomType(Connection conn, int roomTypeId) {
        List<String> amenities = new ArrayList<>();
        String sql = "SELECT Amenity_Name FROM RoomAmenity WHERE Room_Type_Id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, roomTypeId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    amenities.add(rs.getString("Amenity_Name"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return amenities;
    }

    public static boolean addAmenity(int roomTypeId, String amenityName) {
        String sql = "INSERT INTO RoomAmenity (Room_Type_Id, Amenity_Name) VALUES (?, ?)";

        try (Connection conn = new DBcontext().c; PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, roomTypeId);
            ps.setString(2, amenityName);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<Room> getRoomsByRoomTypeId(int roomTypeId) {
    List<Room> rooms = new ArrayList<>();
    String sql = "SELECT Room_Id, Room_Number FROM Rooms WHERE Room_Type_Id = ?";

    try (Connection conn = new dal.DBcontext().c;
         PreparedStatement ps = conn.prepareStatement(sql)) {
        
        ps.setInt(1, roomTypeId);
        
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Room room = new Room();
                room.setRoomId(rs.getInt("Room_Id"));
                room.setRoomNumber(rs.getInt("Room_Number"));
                rooms.add(room);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace(); 
    }

    return rooms;
}

}
