package controller.user;

import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import model.ChatMessageHandler;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;

import java.net.HttpURLConnection;
import java.net.URL;

public class ChatBotServlet extends HttpServlet {

    private static final String API_KEY = "AIzaSyCB1bbOYcKxFr45cvKLvZz2L1n2KBY83ao";
    private static final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + API_KEY;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        PrintWriter out = response.getWriter();
        try {
            String userMessage = request.getParameter("message");
            if (userMessage == null || userMessage.trim().isEmpty()) {
                sendJsonError(response, out, 400, "Message cannot be empty");
                return;
            }

            // Gọi xử lý nội dung nội bộ (database, rule-based...)
            JsonObject replyJson = ChatMessageHandler.handleMessage(userMessage);
            if (replyJson.has("reply")) {
                out.write(replyJson.toString());
                return; // Xử lý xong, không gọi Gemini nữa
            }

            // Nếu không xử lý được => gọi Gemini API
            JsonObject successResponse = callGeminiAPI(userMessage);
            out.write(successResponse.toString());

        } catch (Exception e) {
            e.printStackTrace();
            sendJsonError(response, out, 500, "Internal server error: " + e.getMessage());
        }
    }

    private JsonObject callGeminiAPI(String message) throws IOException {
        JsonObject part = new JsonObject();
        part.addProperty("text", message);

        JsonArray parts = new JsonArray();
        parts.add(part);

        JsonObject content = new JsonObject();
        content.add("parts", parts);

        JsonArray contents = new JsonArray();
        contents.add(content);

        JsonObject body = new JsonObject();
        body.add("contents", contents);

        // Optional safety setting
        JsonObject safety = new JsonObject();
        safety.addProperty("category", "HARM_CATEGORY_HARASSMENT");
        safety.addProperty("threshold", "BLOCK_MEDIUM_AND_ABOVE");
        JsonArray safetySettings = new JsonArray();
        safetySettings.add(safety);
        body.add("safety_settings", safetySettings);

        URL url = new URL(API_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            os.write(body.toString().getBytes("UTF-8"));
        }

        int responseCode = conn.getResponseCode();
        InputStream stream = (responseCode == HttpURLConnection.HTTP_OK)
                ? conn.getInputStream()
                : conn.getErrorStream();

        StringBuilder responseText = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(stream, "UTF-8"))) {
            String line;
            while ((line = br.readLine()) != null) {
                responseText.append(line.trim());
            }
        }

        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new IOException("Gemini API error: " + responseText);
        }

        JsonObject jsonObj = JsonParser.parseString(responseText.toString()).getAsJsonObject();
        JsonObject candidate = jsonObj.getAsJsonArray("candidates").get(0).getAsJsonObject();
        JsonObject contentObj = candidate.getAsJsonObject("content");
        String reply = contentObj.getAsJsonArray("parts").get(0).getAsJsonObject().get("text").getAsString();

        JsonObject successResponse = new JsonObject();
        successResponse.addProperty("reply", reply);
        return successResponse;
    }

    private void sendJsonError(HttpServletResponse response, PrintWriter out, int status, String message) {
        response.setStatus(status);
        JsonObject error = new JsonObject();
        error.addProperty("error", message);
        out.write(error.toString());
    }
}
