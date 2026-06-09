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
        <link href="css/login.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>

        <div class="box">
            <h2>Khôi phục mật khẩu</h2>
            <form method="post" action="forgotpassword">
                <label>Email</label>
                <input type="email" name="email" placeholder="Email" required>
                <p style="color: red">${requestScope.msg}</p>
                <button type="submit">Nhận mã OTP</button>
            </form>
            <p class="switch-text">
                Đã có tài khoản? <a href="login">Đăng nhập</a><br>
                Chưa có tài khoản? <a href="register">Đăng ký</a>
                
            </p>
        </div>
        <script>
            const urlParams = new URLSearchParams(window.location.search);
            const message = urlParams.get('message');
            if (message) {
                alert(decodeURIComponent(message));
            }
        </script>

    </body>
</html>
