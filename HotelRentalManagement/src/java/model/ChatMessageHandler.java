/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ddhoang
 */
import com.google.gson.JsonObject;
import dal.ChatBotDAO;

import java.util.List;

public class ChatMessageHandler {

    public static JsonObject handleMessage(String userMessage) {
        JsonObject response = new JsonObject();
        ChatBotDAO dao = new ChatBotDAO();
        String lowerMsg = userMessage.toLowerCase();

        if (lowerMsg.contains("cuối tuần") && lowerMsg.contains("phòng trống")) {
            List<String> availableRooms = dao.getAvailableWeekendRooms();

            if (availableRooms.isEmpty()) {
                response.addProperty("reply", "❌ Rất tiếc, không còn phòng trống vào cuối tuần.");
            } else {
                StringBuilder reply = new StringBuilder("✅ Hiện tại còn " + availableRooms.size() + " phòng trống vào cuối tuần:\n");
                response.addProperty("reply", reply.toString());
            }
        } else if (lowerMsg.contains("giá")) {
            List<String> roomPrices = dao.getAllRoomTypesWithPrices();
            if (roomPrices.isEmpty()) {
                response.addProperty("reply", "❌ Rất tiếc, chưa có thông tin về giá các loại phòng.");
            } else {
                StringBuilder reply = new StringBuilder("💰 Giá các loại phòng hiện tại:\n");
                for (String info : roomPrices) {
                    reply.append(" • ").append(info).append("\n");
                }
                response.addProperty("reply", reply.toString());
            }
        } else if (lowerMsg.contains("dịch vụ")) {
            String reply = dao.getServicesGroupedByCategory();
            response.addProperty("reply", reply);
            return response;
        } else if (lowerMsg.contains("giờ") || lowerMsg.contains("thời gian")&& (lowerMsg.contains("checkin") || lowerMsg.contains("check-in") || lowerMsg.contains("nhận phòng"))) {
            String reply = "🕑 Giờ nhận phòng (check-in) mặc định là **14:00**.\n"
                    + "🕛 Giờ trả phòng (check-out) mặc định là **12:00**.";
            response.addProperty("reply", reply);
            return response;
        }

        return response;
    }
}
