<%-- 
    Document   : restaurant
    Created on : May 21, 2025, 1:21:05 PM
    Author     : ddhoang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Đồ ăn</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <link href="https://fonts.googleapis.com/css?family=Poppins:200,300,400,500,600,700" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css?family=Playfair+Display:400,400i,700,700i" rel="stylesheet">

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
        <link rel="stylesheet" href="css/style.css">
    </head>
    <body>
        <%@include file="menu.jsp" %>

        <div class="hero-wrap" style="background-image: url('images/bg_1.jpg');">
            <div class="overlay"></div>
            <div class="container">
                <div class="row no-gutters slider-text d-flex align-itemd-end justify-content-center">
                    <div class="col-md-9 ftco-animate text-center d-flex align-items-end justify-content-center">
                        <div class="text">
                            <p class="breadcrumbs mb-2"><span class="mr-2"><a href="index.jsp">Home</a></span> <span>Restaurants</span></p>
                            <h1 class="mb-4 bread">Ẩm thực</h1>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <section class="ftco-section">
            <div class="container">
                <div class="row justify-content-center mb-5 pb-3">
                    <div class="col-md-7 heading-section text-center ftco-animate">
                        <h2>Our Menu</h2>
                    </div>
                </div>

                <!-- Search Bar -->
                <form class="row g-3 mb-4" method="get" action="restaurant">
                    <!-- Nút Trở về tất cả các món ăn (hiện khi đang có bộ lọc) -->
                    <c:if test="${not empty param.keyword or not empty param.category or not empty param.priceFrom or not empty param.priceTo}">
                        <div class="col-md-12 mb-3 text-end">
                            <a href="restaurant" class="btn btn-secondary">🔄 Hiển thị tất cả món ăn</a>
                        </div>
                    </c:if>

                    <!-- Tên món -->
                    <div class="col-md-4">
                        <input type="text" name="keyword" class="form-control" placeholder="🔍 Tìm tên món..."
                               value="<%= request.getParameter("keyword") != null ? request.getParameter("keyword") : "" %>">
                    </div>

                    <!-- Danh mục -->
                    <div class="col-md-3">
                        <select class="form-control" name="category">
                            <option value="">-- Tất cả danh mục --</option>
                            <option value="Đồ ăn" <%= "Đồ ăn".equals(request.getParameter("category")) ? "selected" : "" %>>Đồ ăn</option>
                            <option value="Đồ uống" <%= "Đồ uống".equals(request.getParameter("category")) ? "selected" : "" %>>Đồ uống</option>
                        </select>
                    </div>

                    <!-- Giá từ -->
                    <div class="col-md-2">
                        <input type="number" name="priceFrom" class="form-control" placeholder="Giá từ..."
                               value="<%= request.getParameter("priceFrom") != null ? request.getParameter("priceFrom") : "" %>">
                    </div>

                    <!-- Giá đến -->
                    <div class="col-md-2">
                        <input type="number" name="priceTo" class="form-control" placeholder="Giá đến..."
                               value="<%= request.getParameter("priceTo") != null ? request.getParameter("priceTo") : "" %>">
                    </div>

                    <!-- Nút tìm -->
                    <div class="col-md-1 d-grid">
                        <button type="submit" class="btn btn-outline-primary">Tìm</button>
                    </div>
                </form>

                <!-- Grid of food items -->
                <div class="row">
                    <c:forEach var="food" items="${foodList}">
                        <div class="col-md-3 mb-4">
                            <div class="border rounded text-center shadow-sm p-2 h-100 position-relative">
                                <div style="height: 180px; overflow: hidden;">
                                    <a href="viewfood?id=${food.id}">
                                        <img src="${food.image}" class="img-fluid" style="height: 100%; object-fit: cover;" />
                                    </a>
                                </div>
                                <div class="pt-2">
                                    <p class="mb-1 fw-bold">
                                        <a href="viewfood?id=${food.id}" class="text-dark text-decoration-none">${food.name}</a>
                                    </p>
                                    <div>
                                        <span class="fw-bold text-danger d-block">
                                            <fmt:formatNumber value="${food.price}" type="number" pattern="#,##0.000"/> VNĐ
                                        </span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <!-- Nút Xem thêm -->
                <div class="col-md-12 text-center mt-4">
                    <a href="listfood" class="btn btn-outline-primary btn-lg">🍽️ Xem thêm món ăn</a>
                </div>



             
            </div>
        </section>

        <%@include file="footer.jsp" %>




        <!-- loader -->
        <div id="ftco-loader" class="show fullscreen"><svg class="circular" width="48px" height="48px"><circle class="path-bg" cx="24" cy="24" r="22" fill="none" stroke-width="4" stroke="#eeeeee"/><circle class="path" cx="24" cy="24" r="22" fill="none" stroke-width="4" stroke-miterlimit="10" stroke="#F96D00"/></svg></div>


        <script src="js/jquery.min.js"></script>
        <script src="js/jquery-migrate-3.0.1.min.js"></script>
        <script src="js/popper.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/jquery.easing.1.3.js"></script>
        <script src="js/jquery.waypoints.min.js"></script>
        <script src="js/jquery.stellar.min.js"></script>
        <script src="js/owl.carousel.min.js"></script>
        <script src="js/jquery.magnific-popup.min.js"></script>
        <script src="js/aos.js"></script>
        <script src="js/jquery.animateNumber.min.js"></script>
        <script src="js/bootstrap-datepicker.js"></script>
        <script src="js/jquery.timepicker.min.js"></script>
        <script src="js/scrollax.min.js"></script>
        <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBVWaKrjvy3MaE7SQ74_uJiULgl1JY0H2s&sensor=false"></script>
        <script src="js/google-map.js"></script>
        <script src="js/main.js"></script>

    </body>
</html>
