<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.User"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Royal Hotel</title>
        <meta charset="UTF-8">
        <!-- Fonts & Icons -->
        <link href="https://fonts.googleapis.com/css?family=Poppins:200,300,400,500,600,700" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css?family=Playfair+Display:400,400i,700,700i" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />

        <!-- Bootstrap & Base Styles -->
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <link rel="stylesheet" href="css/style.css">
        <link rel="stylesheet" href="css/menu.css">

        <!-- Animations, Icons, Plugins -->
        <link rel="stylesheet" href="css/open-iconic-bootstrap.min.css">
        <link rel="stylesheet" href="css/animate.css">
        <link rel="stylesheet" href="css/owl.carousel.min.css">
        <link rel="stylesheet" href="css/owl.theme.default.min.css">
        <link rel="stylesheet" href="css/magnific-popup.css">
        <link rel="stylesheet" href="css/aos.css">
        <link rel="stylesheet" href="css/ionicons.min.css">
        <link rel="stylesheet" href="css/bootstrap-datepicker.css">
        <link rel="stylesheet" href="css/jquery.timepicker.css">
        <link rel="stylesheet" href="css/flaticon.css">
        <link rel="stylesheet" href="css/icomoon.css">
        <style>
            .navbar-light .navbar-nav .nav-link {
                color: #ff6600 !important; /* hoặc #333 nếu bạn muốn tối hơn */
                font-weight: 500;
                transition: 0.3s;
            }
            .navbar-light .navbar-nav .nav-link:hover,
            .navbar-light .navbar-nav .nav-link.active {
                color: #fff !important; /* Màu khi hover hoặc đang active */
                background-color: #ff6600;
                border-radius: 8px;
                padding: 6px 12px;
            }
            .navbar-light .navbar-nav .nav-link {
                text-shadow: 1px 1px 2px rgba(0,0,0,0.3);
            }


            .user-icon {
                display: flex;
                align-items: center;
                justify-content: center;
                width: 48px;        /* tăng từ 36px lên 48px */
                height: 48px;
                border: 3px solid orange; /* dày hơn để cân đối */
                border-radius: 50%;
                font-size: 22px;    /* to hơn để dễ thấy */
                color: orange;
                position: relative;
                transition: 0.3s;
            }


            .user-icon:hover {
                background-color: #fff2e6;
                color: #ff6600;
            }
            .status-dot {
                width: 10px;
                height: 10px;
                border-radius: 50%;
                position: absolute;
                top: 6px;
                right: 6px;
            }


            .dot-green {
                background-color: #28a745;
            }

            .dot-red {
                background-color: #dc3545;
            }
        </style>
    </head>

    <%
        String currentPage = request.getRequestURI();
        currentPage = currentPage.substring(currentPage.lastIndexOf("/") + 1);
        User user = (User) session.getAttribute("user");
    %>

    <body>
        <nav class="navbar navbar-expand-lg navbar-dark ftco_navbar bg-dark ftco-navbar-light" id="ftco-navbar">
            <div class="container d-flex justify-content-between align-items-center">
                <!-- Logo trái -->
                <a class="navbar-brand mr-auto" href="home">ROYAL HOTEL</a>

                <!-- Menu giữa -->
                <div class="collapse navbar-collapse justify-content-center" id="ftco-nav">
                    <ul class="navbar-nav mx-auto">
                        <li class="nav-item <%= "home.jsp".equals(currentPage) ? "active" : "" %>">
                            <a href="home" class="nav-link">Trang chủ</a>
                        </li>
                        <li class="nav-item <%= "rooms.jsp".equals(currentPage) ? "active" : "" %>">
                            <a href="listRoomBooking" class="nav-link">Phòng</a>
                        </li>
                        <li class="nav-item <%= "restaurant.jsp".equals(currentPage) ? "active" : "" %>">
                            <a href="restaurant" class="nav-link">Đồ ăn</a>
                        </li>
                        <li class="nav-item <%= "about.jsp".equals(currentPage) ? "active" : "" %>">
                            <a href="serviceservlet" class="nav-link">Giới thiệu</a>
                        </li>
                        <li class="nav-item <%= "blog.jsp".equals(currentPage) ? "active" : "" %>">
                            <a href="viewblog" class="nav-link">Bài viết</a>
                        </li>
                        <li class="nav-item <%= "contact.jsp".equals(currentPage) ? "active" : "" %>">
                            <a href="contact" class="nav-link">Liên hệ</a>
                        </li>

                        <!-- Vai trò user -->
                        <c:if test="${sessionScope.user != null && sessionScope.user.roleid == 1}">
                            <li class="nav-item <%= "feedback.jsp".equals(currentPage) ? "active" : "" %>">
                                <a href="feedback.jsp" class="nav-link">Phản hồi</a>
                            </li>
                        </c:if>

                        <!-- Vai trò quản lý -->
                        <c:if test="${sessionScope.user != null && sessionScope.user.roleid == 3}">
                            <li class="nav-item <%= "listRoom".equals(currentPage) ? "active" : "" %>">
                                <a href="listRoom" class="nav-link">Quản lý</a>
                            </li>
                        </c:if>
                        <c:if test="${sessionScope.user != null && sessionScope.user.roleid == 4}">
                            <li class="nav-item <%= "listBooking".equals(currentPage) ? "active" : "" %>">
                                <a href="listBooking" class="nav-link">Quản lý</a>
                            </li>
                        </c:if>
                    </ul>
                </div>

                <!-- Icon người dùng bên phải -->
                <ul class="navbar-nav ml-auto d-flex flex-row">
                    <li class="nav-item mx-1">
                        <a href="<%= (user != null) ? "updateprofile" : "login" %>" class="user-icon">
                            <i class="fa fa-user"></i>
                            <span class="status-dot <%= (user != null) ? "dot-green" : "dot-red" %>"></span>
                        </a>
                    </li>
                </ul>
            </div>
        </nav>
    </body>
</html>
