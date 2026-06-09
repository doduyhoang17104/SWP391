package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ChatBotDAO extends DBcontext {

    public List<String> getAvailableWeekendRooms() {
        List<String> availableRooms = new ArrayList<>();

        String sql = "SELECT r.Room_Number\n"
                + "FROM Rooms r\n"
                + "LEFT JOIN Booking b ON r.Room_Id = b.Room_Id\n"
                + "WHERE r.Status = 'available'\n"
                + "AND r.Room_Id NOT IN (\n"
                + "    SELECT Room_Id\n"
                + "    FROM Booking\n"
                + "    WHERE \n"
                + "        DATENAME(WEEKDAY, CheckIn_Date) IN ('Saturday', 'Sunday')\n"
                + "        OR DATENAME(WEEKDAY, CheckOut_Date) IN ('Saturday', 'Sunday')\n"
                + ");";

        try (PreparedStatement stmt = c.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                availableRooms.add(rs.getString("Room_Number"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return availableRooms;
    }

    public List<String> getAllRoomTypesWithPrices() {
        List<String> roomPrices = new ArrayList<>();
        String sql = "SELECT Type_Name, Base_Price FROM RoomType";

        try (PreparedStatement stmt = c.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String type = rs.getString("Type_Name");
                String price = rs.getString("Base_Price");

                roomPrices.add(type + ": " + price + " VND/ 1 đêm");

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return roomPrices;
    }

    public String getServicesGroupedByCategory() {
        String sql = "SELECT sc.Service_Category_Name, s.Service_Name, s.Price "
                + "FROM Services s "
                + "LEFT JOIN ServiceCategory sc ON s.Service_Category_Id = sc.Service_Category_Id "
                + "ORDER BY sc.Service_Category_Name, s.Service_Name";

        Map<String, List<String>> categoryMap = new LinkedHashMap<>();

        try (PreparedStatement stmt = c.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String category = rs.getString("Service_Category_Name");
                String name = rs.getString("Service_Name");
                String price = rs.getString("Price");

                String serviceLine = "  • " + name + " – " + price + " VND";

                categoryMap.computeIfAbsent(category, k -> new ArrayList<>()).add(serviceLine);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Ghép kết quả lại thành chuỗi phản hồi
        StringBuilder response = new StringBuilder("🛎️ Danh sách dịch vụ:\n");
        for (Map.Entry<String, List<String>> entry : categoryMap.entrySet()) {
            response.append("\n").append("🔹 ").append(entry.getKey()).append(":\n");

            for (String s : entry.getValue()) {
                response.append(s).append("\n");
            }
        }

        return response.toString();
    }

    public String getRoomPriceByTypeName(String typeNameInput) {
        String sql = "SELECT Base_Price FROM RoomType WHERE LOWER(Type_Name) = ?";

        try (PreparedStatement stmt = c.prepareStatement(sql)) {
            stmt.setString(1, typeNameInput.toLowerCase());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("Base_Price") + " VND";
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args) {

        ChatBotDAO dao = new ChatBotDAO(); // Đảm bảo class này kế thừa DBcontext và có sẵn Connection

        List<String> weekendRooms = dao.getAvailableWeekendRooms();

        if (weekendRooms.isEmpty()) {
            System.out.println("❌ Không có phòng nào trống vào cuối tuần.");
        } else {
            System.out.println("✅ Danh sách phòng trống vào cuối tuần:");
            for (String roomNumber : weekendRooms) {
                System.out.println(" - Phòng: " + roomNumber);
            }
        }
    }

}
