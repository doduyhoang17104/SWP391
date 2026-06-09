<%-- 
    Document   : verify-otp
    Created on : May 22, 2025, 10:15:54 AM
    Author     : ddhoang
--%>

<%-- 
    Document   : forgot-password
    Created on : May 22, 2025, 10:07:48 AM
    Author     : ddhoang
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*, javax.mail.*, javax.mail.internet.*" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Quên mật khẩu</title>
        <style>
            body {
                font-family: Arial;
                background: #f0f0f0;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
            }
            .box {
                background: white;
                padding: 30px;
                border-radius: 10px;
                box-shadow: 0 0 10px #ccc;
                width: 300px;
            }
            input, button {
                width: 100%;
                padding: 10px;
                margin: 10px 0;
                border-radius: 5px;
                border: 1px solid #ccc;
            }
            button {
                background: #0073e6;
                color: white;
                border: none;
            }
        </style>
    </head>
    <body>
        <link href="css/login.css" rel="stylesheet" type="text/css"/>
        <div class="box">
            <h2>Nhập mã OTP</h2>
            <form method="post" action="verifyOTP">
                <label>OTP:</label>
                <input type="text" name="otp" required>
                <p style="color: red">${requestScope.msg}</p>
                <button type="submit">Khôi phục mật khẩu</button>
            </form>
            <form action="forgotpassword" method="POST">
                <input type="hidden" name="action" value="resend">
                <button type="submit" action>Gửi lại mã OTP</button>
            </form>
            <p class="switch-text">
                Đã có tài khoản? <a href="login">Đăng nhập</a><br>
                Chưa có tài khoản? <a href="register">Đăng ký</a>

            </p>
        </div>

    </body>
</html>
