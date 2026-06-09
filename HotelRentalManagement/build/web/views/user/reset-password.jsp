<%-- 
    Document   : reset-password
    Created on : May 22, 2025, 10:25:09 AM
    Author     : ddhoang
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" %>


<!DOCTYPE html>
<link href="css/login.css" rel="stylesheet" type="text/css"/>
<html>
    <head>
        <title>Đặt lại mật khẩu</title>

    </head>
    <body>
        <div class="box">
            <h2>Đặt lại mật khẩu</h2>
            <form method="post" action="resetpassword">

                <input type="password" name="password" placeholder="Mật khẩu mới" required>
                <input type="password" name="confirmPassword" placeholder="Xác nhận mật khẩu" required>
                <p style="color: red">${requestScope.msg}</p>
                <button type="submit">Cập nhật mật khẩu</button>
            </form>
            <p class="switch-text">
                Đã có tài khoản? <a href="login">Đăng nhập</a><br>
                Chưa có tài khoản? <a href="register">Đăng ký</a>
            </p>
        </div>

    </body>
</html>
