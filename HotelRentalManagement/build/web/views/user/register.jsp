<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Đăng ký</title>
        <link rel="stylesheet" href="style.css">
        <link href="css/login.css" rel="stylesheet" type="text/css"/>
        <link href="css/logingg.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <div class="container">
            <div class="form-box">
                <h2>Đăng ký</h2>
                <form action="register" method="POST">
                    <div class="input-group">
                        <input type="text" name="name" placeholder="Họ và tên" required>
                    </div>
                    <div class="input-group">
                        <input type="text" name="phone" placeholder="Số điện thoại" required ">
                    </div>
                    <div class="input-group">
                        <input type="text" name="address" placeholder="Địa chỉ" required>
                    </div>
                    <div class="input-group">
                        <input type="email" name="gmail" placeholder="Gmail" required>
                    </div>
                    <div class="input-group">
                        <input type="text" name="username" placeholder="Tên đăng nhập" required>
                    </div>
                    <div class="input-group">
                        <input type="password" name="password" placeholder="Mật khẩu" required>
                    </div>
                    <div class="input-group">
                        <input type="password" name="repassword" placeholder="Xác nhận mật khẩu" required>
                    </div>
                    <p style="color: red">${requestScope.msg}</p>
                    <div class="input-group">
                        <button type="submit" class="btn">Đăng ký</button>

                    </div>
                    <div class="form-row">
                        <div class="back-left">
                            <a href="login">← Quay lại</a>
                        </div>
                        <div class="forgot-password">
                            <a href="forgotpassword">Quên mật khẩu?</a>
                        </div>
                    </div>

                    <a href="https://accounts.google.com/o/oauth2/v2/auth?scope=openid%20email%20profile&redirect_uri=http://localhost:8080/HotelRentalManagement/loginGoogle&response_type=code&client_id=775010914231-131te3qs2e01dqjtngo8a4k3ld224qen.apps.googleusercontent.com&access_type=offline&include_granted_scopes=true">
                        <div class="google-btn">
                            <img src="https://www.svgrepo.com/show/475656/google-color.svg" alt="Google">
                            <span>Đăng nhập bằng Google</span>
                        </div>
                    </a>
                    <div class="switch-text">
                        Đã có tài khoản? <a href="login" >Đăng nhập</a>
                    </div>
                </form>
            </div>
        </div>
    </body>
</html>
