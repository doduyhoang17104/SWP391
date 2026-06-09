<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Chatbot</title>
        <style>
            #chatContainer {
                font-family: Arial, sans-serif;
                font-size: 16px;
                position: fixed;
                bottom: 80px;
                right: 20px;
                width: 420px;
                height: 500px;
                display: none;
                z-index: 1000;
            }
            #chatContainer * {
                box-sizing: border-box;
            }
            #chatContainer .card {
                height: 100%;
                background: #fff;
                border: 1px solid #ccc;
                display: flex;
                flex-direction: column;
            }

            #chatContainer .card-header {
                background-color: #0d6efd;
                color: white;
                padding: 10px 15px;
                font-weight: bold;
                display: flex;
                justify-content: space-between;
                align-items: center;
            }

            #chatContainer .card-body {
                flex: 1;
                overflow-y: auto;
                padding: 15px;
            }

            #chatContainer .card-footer {
                padding: 10px;
                border-top: 1px solid #ccc;
            }

            .btn-close {
                background: none;
                border: none;
                font-size: 1.2rem;
                color: white;
                cursor: pointer;
            }

            .chat-message {
                max-width: 80%;
                padding: 8px 12px;
                border-radius: 12px;
                margin-bottom: 10px;
                display: inline-block;
                clear: both;
                word-wrap: break-word;
                white-space: pre-wrap;
            }

            .chat-user {
                background-color: #d1e7dd;
                float: right;
                text-align: right;
            }

            .chat-bot {
                background-color: #f1f1f1;
                float: left;
                text-align: left;
            }

            #suggestedQuestions {
                padding: 10px;
                background: #f8f9fa;
                border-top: 1px solid #ccc;
            }

            #suggestedQuestions button {
                margin: 4px 4px 0 0;
                padding: 5px 10px;
                font-size: 14px;
                cursor: pointer;
            }

            #userMessage {
                width: 100%;
                padding: 8px;
                font-size: 15px;
            }

            .send-btn {
                padding: 8px 14px;
                background-color: #0d6efd;
                color: white;
                border: none;
                cursor: pointer;
            }

            /* Scrollbar styling */
            #chatBox::-webkit-scrollbar {
                width: 6px;
            }

            #chatBox::-webkit-scrollbar-thumb {
                background-color: #bbb;
                border-radius: 4px;
            }
            #suggestedQuestions button {
                margin: 4px 4px 0 0;
                padding: 6px 12px;
                font-size: 14px;
                cursor: pointer;
                background-color: #fff;
                border: 1px solid #ccc;
                border-radius: 20px; /* <== BO TRÒN MỀM MẠI */
                transition: background-color 0.2s ease;
            }

            #suggestedQuestions button:hover {
                background-color: #e9f0ff;
            }

        </style>
    </head>
    <body>

        <!-- Chatbot Container -->
        <div id="chatContainer" style="position: fixed; top: 70px; right: 20px; width: 420px; height: calc(100vh - 90px); display: none; z-index: 1000;">

            <div class="card">
                <div class="card-header">
                    Trợ lý khách sạn
                    <button onclick="closeChat()" class="btn-close">&times;</button>
                </div>

                <div class="card-body" id="chatBox">
                    <!-- Tin nhắn sẽ hiển thị ở đây -->
                </div>

                <div id="suggestedQuestions">
                    <small style="color:#555;">Khách hàng hay hỏi:</small><br>
                    <button onclick="sendSuggested('Có phòng trống vào cuối tuần không?')">Có phòng trống cuối tuần?</button>
                    <button onclick="sendSuggested('Giá phòng VIP là bao nhiêu?')">Giá phòng?</button>
                    <button onclick="sendSuggested('Tôi muốn đặt phòng đôi.')">Đặt phòng đôi</button>
                    <button onclick="sendSuggested('Chính sách hủy phòng như thế nào?')">Chính sách hủy?</button>
                </div>

                <div class="card-footer">
                    <div style="display: flex; gap: 5px;">
                        <input type="text" id="userMessage" placeholder="Nhập tin nhắn...">
                        <button class="send-btn" onclick="sendMessage()">Gửi</button>
                    </div>
                </div>
            </div>
        </div>

        <!-- Nút bật chat -->
        <div onclick="toggleChat()" style="position: fixed; bottom: 20px; right: 20px; z-index: 999; cursor: pointer; font-size: 2rem; color: #0d6efd;">
            <i class="fas fa-robot"></i>
        </div>

        <!-- JavaScript xử lý -->
        <script>
            function sendMessage() {
                const input = document.getElementById("userMessage");
                const message = input.value.trim();
                if (!message)
                    return;

                appendUserMessage(message);
                input.value = "";
                sendToServer(message);
            }

            function appendUserMessage(message) {
                const chatBox = document.getElementById("chatBox");
                const userDiv = document.createElement("div");
                userDiv.className = "chat-message chat-user";
                userDiv.innerHTML = sanitizeHTML(message);
                chatBox.appendChild(userDiv);
                scrollToBottom(chatBox);
            }

            function appendBotMessage(reply) {
                const chatBox = document.getElementById("chatBox");
                const botDiv = document.createElement("div");
                botDiv.className = "chat-message chat-bot";
                botDiv.innerHTML = sanitizeHTML(reply);
                chatBox.appendChild(botDiv);
                scrollToBottom(chatBox);
            }

            function scrollToBottom(element) {
                element.scrollTop = element.scrollHeight;
            }

            function sanitizeHTML(str) {
                const temp = document.createElement('div');
                temp.textContent = str;
                return temp.innerHTML;
            }

            function sendToServer(message) {
                fetch("chatbot", {
                    method: "POST",
                    headers: {"Content-Type": "application/x-www-form-urlencoded"},
                    body: "message=" + encodeURIComponent(message)
                })
                        .then(response => response.json())
                        .then(data => {
                            if (data.reply) {
                                appendBotMessage(data.reply);
                            } else {
                                appendBotMessage("❗ Không có phản hồi từ AI.");
                            }
                        })
                        .catch(error => {
                            console.error("Lỗi:", error);
                            appendBotMessage("⚠️ Lỗi kết nối máy chủ.");
                        });
            }

            function sendSuggested(text) {
                document.getElementById("userMessage").value = text;
                sendMessage();
            }

            let hasWelcomed = false;

            function toggleChat() {
                const chat = document.getElementById("chatContainer");

                if (chat.style.display === "none") {
                    chat.style.display = "block";

                    // Gửi lời chào nếu lần đầu mở
                    if (!hasWelcomed) {
                        appendBotMessage("👋 Chào mừng bạn đến với khách sạn của chúng tôi. Tôi có thể giúp gì cho bạn?");
                        hasWelcomed = true;
                    }
                } else {
                    chat.style.display = "none";
                }
            }


            function closeChat() {
                document.getElementById("chatContainer").style.display = "none";
            }

            window.onload = function () {
                document.getElementById("userMessage").addEventListener("keypress", function (event) {
                    if (event.key === "Enter") {
                        event.preventDefault();
                        sendMessage();
                    }
                });
            };
        </script>

    </body>
</html>
