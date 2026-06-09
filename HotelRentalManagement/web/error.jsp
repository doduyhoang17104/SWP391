<%-- 
    Document   : error
    Created on : Jul 12, 2025, 12:28:28 PM
    Author     : ddhoang
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->

        <title>404 HTML Tempalte by Colorlib</title>

        <!-- Google font -->
        <link href="https://fonts.googleapis.com/css?family=Montserrat:400,700,900" rel="stylesheet">

        <!-- Custom stlylesheet -->
        <link href="css/404.css" rel="stylesheet" type="text/css"/>

        <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
                  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
                  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
                <![endif]-->

    </head>
    <body>

        <div id="notfound">
            <div class="notfound">
                <div class="notfound-404">
                    <h1>Oops!</h1>
                </div>
                <h2>404 - Trang không tồn tại</h2>
                <p>Rất tiếc! Trang bạn yêu cầu có thể đã bị gỡ bỏ, thay đổi đường dẫn hoặc hiện không khả dụng.</p>

                <c:choose>
                    <c:when test="${not empty sessionScope.user && sessionScope.user.roleid == 1}">
                        <a href="home">Quay về trang chủ</a>
                    </c:when>
                    <c:when test="${sessionScope.user.roleid == 2}">
                        <a href="listuser">Quay về trang quản trị</a>
                    </c:when>
                    <c:when test="${sessionScope.user.roleid == 3}">
                        <a href="chart">Quay về trang quản lý</a>
                    </c:when>
                    <c:when test="${sessionScope.user.roleid == 4}">
                        <a href="listBooking">Quay về trang lễ tân</a>
                    </c:when>
                    <c:otherwise>
                        <a href="home">Quay về trang chủ</a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

    </body>

</html>
