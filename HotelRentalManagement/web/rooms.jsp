<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Room" %>
<%@ page import="dal.RoomDAO" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Phòng</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <!-- Bootstrap 5 -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

        <!-- Bootstrap Icons -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">

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
    <style>
        .sidebar {
            .sidebar .sidebar-wrap {
                padding: 25px;
                background-color: #f8f9fa; /* tùy chỉnh màu nếu muốn */
            }
        </style>
        <body>
            <%@include file="menu.jsp" %>

            <div class="hero-wrap" style="background-image: url('images/bg_1.jpg');">
                <div class="overlay"></div>
                <div class="container">
                    <div class="row no-gutters slider-text d-flex align-itemd-end justify-content-center">
                        <div class="col-md-9 ftco-animate text-center d-flex align-items-end justify-content-center">
                            <div class="text">
                                <p class="breadcrumbs mb-2"><span class="mr-2"><a href="index.jsp">Home</a></span> <span>About</span></p>
                                <h1 class="mb-4 bread">Rooms</h1>
                                <c:if test="${empty param.checkin or empty param.checkout}">
                                    <div class="alert alert-danger mt-3" role="alert" style="font-size: 16px;">
                                        <i class="bi bi-exclamation-triangle-fill"></i> Vui lòng nhập yêu cầu của bạn vào bộ lọc để hệ thống có thể cung cấp phòng phù hợp cho bạn!
                                    </div>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>
            </div>


            <section class="ftco-section bg-light">
                <div class="container">
                    <div class="row">
                        <div class="col-lg-9">
                            <div id="roomContainer" class="row">
                                <c:if test="${empty param.checkin or empty param.checkout}">
                                    <div class="alert alert-success mt-3" role="alert" style="font-size: 16px;">
                                        <p style="text-align: center;">Các loại phòng trong hệ thống khách sạn của chúng tôi.</p>
                                    </div>
                                </c:if>
                                <c:forEach var="room" items="${rooms}">
                                    <div class="col-sm col-md-6 col-lg-4 ftco-animate">
                                        <div  class="room">
                                            <c:choose>
                                                <c:when test="${not empty param.checkin and not empty param.checkout}">
                                                    <a href="roomDetail?roomId=${room.roomId}&checkin=${param.checkin}&checkout=${param.checkout}" 
                                                       class="img d-flex justify-content-center align-items-center"
                                                       style="background-image: url(${room.imageUrls[0] != null ? room.imageUrls[0] : 'photo/1.jpg'});">
                                                        <div class="icon d-flex justify-content-center align-items-center">
                                                            <span class="icon-search2"></span>
                                                        </div>
                                                    </a>
                                                </c:when>
                                                <c:otherwise>
                                                    <div class="img d-flex justify-content-center align-items-center"
                                                         style="background-image: url(${room.imageUrls[0] != null ? room.imageUrls[0] : 'photo/1.jpg'});
                                                         filter: grayscale(70%);
                                                         cursor: not-allowed;">
                                                        <div class="icon d-flex justify-content-center align-items-center">
                                                            <span class="icon-lock"></span>
                                                        </div>
                                                    </div>
                                                </c:otherwise>
                                            </c:choose>                 
                                            <div class="icon d-flex justify-content-center align-items-center">
                                                <span class="icon-search2"></span>
                                            </div>
                                            </a>
                                            <div class="text p-3 text-center">
                                                <h3 class="mb-3"><a href="roomDetail?roomId=${room.roomId}">${room.roomType.typeName}</a></h3>
                                                <p>
                                                    <span class="price mr-2">
                                                        <fmt:formatNumber value="${room.roomType.basePrice}" type="number" groupingUsed="true"/>vnd
                                                    </span> 
                                                <div class="per">per night</div>
                                                </p>
                                                <ul class="list">
                                                    <li><span>Max:</span> ${room.capacity} </li>
                                                    <li><span>Size:</span> ${room.size}</li>
                                                    <li><span>Bed:</span> ${room.bed}</li>
                                                </ul>
                                                <hr>
                                                <p class="pt-1">
                                                    <c:choose>
                                                        <c:when test="${not empty param.checkin and not empty param.checkout}">
                                                            <a href="roomDetail?roomId=${room.roomId}&checkin=${param.checkin}&checkout=${param.checkout}" class="btn-custom">
                                                                Book Now <span class="icon-long-arrow-right"></span>
                                                            </a>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <button type="button" class="btn btn-secondary" disabled>
                                                                Không khả dụng
                                                            </button>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </p>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>

                            </div>
                            <div class="row mt-4">
                                <div class="col text-center">
                                    <div class="block-27">
                                        <ul>
                                            <c:if test="${pageIndex > 1}">
                                                <li><a href="listRoomBooking?page=${pageIndex - 1}">&lt;</a></li>
                                                </c:if>

                                            <c:forEach begin="1" end="${totalPages}" var="i">
                                                <li class="${i == pageIndex ? 'active' : ''}">
                                                    <a href="listRoomBooking?page=${i}">${i}</a>
                                                </li>
                                            </c:forEach>

                                            <c:if test="${pageIndex < totalPages}">
                                                <li><a href="listRoomBooking?page=${pageIndex + 1}">&gt;</a></li>
                                                </c:if>
                                        </ul>
                                    </div>
                                </div>
                            </div>

                        </div>
                        <div class="col-lg-3 sidebar">
                            <div class="sidebar-wrap bg-light ftco-animate">
                                <h3 class="heading mb-4">Bộ lọc tìm kiếm</h3>
                                <form action="listRoomBooking" method="get">
                                    <div class="fields">

                                        <div class="form-group">
                                            <input type="date" name="checkin"  id="check-in" class="form-control" value="${param.checkin}">
                                        </div>

                                        <div class="form-group">
                                            <input type="date" name="checkout" id="check-out" class="form-control" value="${param.checkout}">
                                        </div>

                                        <!-- Room Type -->
                                        <div class="form-group">
                                            <div class="select-wrap one-third">
                                                <div class="icon"><span class="ion-ios-arrow-down"></span></div>
                                                <select name="roomType" class="form-control">
                                                    <option value="">Room Type</option>
                                                    <c:forEach var="type" items="${roomTypeList}">
                                                        <option value="${type.typeName}" <c:if test="${param.roomType eq type.typeName}">selected</c:if>>
                                                            ${type.typeName}
                                                        </option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>

                                        <!-- Total Guests (manual input) -->
                                        <div class="form-group">
                                            <input type="text" id="guests" name="guests" class="form-control" value="${param.guests}">
                                        </div>
                                        <div class="form-group">
                                            <input type="number" name="bed" class="form-control" placeholder="Số giường" value="${param.bed}">
                                        </div>

                                        <!-- Price Range -->
                                        <div class="form-group">
                                            <label>Khoảng giá (VNĐ)</label>
                                            <div class="range-slider">
                                                <span>
                                                    <input type="number" name="minPrice" id="minInput"
                                                           value="${param.minPrice != null ? param.minPrice : 0}" 
                                                           min="0" max="5000000" step="100000" /> -
                                                    <input type="number" name="maxPrice" id="maxInput"
                                                           value="${param.maxPrice != null ? param.maxPrice : 2500000}" 
                                                           min="0" max="5000000" step="100000" />
                                                </span>
                                                <input id="minRange" 
                                                       value="${param.minPrice != null ? param.minPrice : 0}" 
                                                       min="0" max="5000000" step="100000" type="range" 
                                                       oninput="minInput.value=this.value">
                                                <input id="maxRange" 
                                                       value="${param.maxPrice != null ? param.maxPrice : 2500000}" 
                                                       min="0" max="5000000" step="100000" type="range" 
                                                       oninput="maxInput.value=this.value">
                                            </div>

                                        </div>
                                        <!-- Submit -->
                                        <div class="form-group">
                                            <input type="submit" value="Search" class="btn btn-primary py-3 px-5">
                                        </div>

                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
            <section class="instagram pt-5">
                <div class="container-fluid">
                    <div class="row no-gutters justify-content-center pb-5">
                        <div class="col-md-7 text-center heading-section ftco-animate">
                            <h2><span>Instagram</span></h2>
                        </div>
                    </div>
                    <div class="row no-gutters">
                        <div class="col-sm-12 col-md ftco-animate">
                            <a href="images/insta-1.jpg" class="insta-img image-popup" style="background-image: url(images/insta-1.jpg);">
                                <div class="icon d-flex justify-content-center">
                                    <span class="icon-instagram align-self-center"></span>
                                </div>
                            </a>
                        </div>
                        <div class="col-sm-12 col-md ftco-animate">
                            <a href="images/insta-2.jpg" class="insta-img image-popup" style="background-image: url(images/insta-2.jpg);">
                                <div class="icon d-flex justify-content-center">
                                    <span class="icon-instagram align-self-center"></span>
                                </div>
                            </a>
                        </div>
                        <div class="col-sm-12 col-md ftco-animate">
                            <a href="images/insta-3.jpg" class="insta-img image-popup" style="background-image: url(images/insta-3.jpg);">
                                <div class="icon d-flex justify-content-center">
                                    <span class="icon-instagram align-self-center"></span>
                                </div>
                            </a>
                        </div>
                        <div class="col-sm-12 col-md ftco-animate">
                            <a href="images/insta-4.jpg" class="insta-img image-popup" style="background-image: url(images/insta-4.jpg);">
                                <div class="icon d-flex justify-content-center">
                                    <span class="icon-instagram align-self-center"></span>
                                </div>
                            </a>
                        </div>
                        <div class="col-sm-12 col-md ftco-animate">
                            <a href="images/insta-5.jpg" class="insta-img image-popup" style="background-image: url(images/insta-5.jpg);">
                                <div class="icon d-flex justify-content-center">
                                    <span class="icon-instagram align-self-center"></span>
                                </div>
                            </a>
                        </div>
                    </div>
                </div>
            </section>

            <%@include file="footer.jsp" %>

            <script>
                const minInput = document.getElementById('minInput');
                const maxInput = document.getElementById('maxInput');
                const minRange = document.getElementById('minRange');
                const maxRange = document.getElementById('maxRange');

                // Khi kéo thanh trượt, cập nhật ô nhập số
                minRange.addEventListener('input', function () {
                    let min = parseInt(minRange.value);
                    let max = parseInt(maxRange.value);
                    if (min > max)
                        minRange.value = max; // Giới hạn không vượt max
                    minInput.value = minRange.value;
                });

                maxRange.addEventListener('input', function () {
                    let min = parseInt(minRange.value);
                    let max = parseInt(maxRange.value);
                    if (max < min)
                        maxRange.value = min; // Giới hạn không nhỏ hơn min
                    maxInput.value = maxRange.value;
                });

                // Khi chỉnh số, cập nhật thanh trượt
                minInput.addEventListener('input', function () {
                    if (parseInt(minInput.value) <= parseInt(maxInput.value)) {
                        minRange.value = minInput.value;
                    }
                });

                maxInput.addEventListener('input', function () {
                    if (parseInt(maxInput.value) >= parseInt(minInput.value)) {
                        maxRange.value = maxInput.value;
                    }
                });
            </script>



            <div id="paginationControls" style="text-align:center;
                 margin-top: 20px;"></div>



            <!-- loader -->
            <div id="ftco-loader" class="show fullscreen"><svg class="circular" width="48px" height="48px"><circle class="path-bg" cx="24" cy="24" r="22" fill="none" stroke-width="4" stroke="#eeeeee"/><circle class="path" cx="24" cy="24" r="22" fill="none" stroke-width="4" stroke-miterlimit="10" stroke="#F96D00"/></svg></div>

            <script>

                const today = new Date().toISOString().split("T")[0];

                document.getElementById("check-in").setAttribute("min", today);
                document.getElementById("check-out").setAttribute("min", today);

                document.getElementById("check-in").addEventListener("change", function () {
                    const checkinDate = this.value;
                    const checkoutInput = document.getElementById("check-out");

                    const nextDay = new Date(checkinDate);
                    nextDay.setDate(nextDay.getDate() + 1);
                    const minCheckout = nextDay.toISOString().split("T")[0];
                    checkoutInput.setAttribute("min", minCheckout);

                    if (checkoutInput.value && checkoutInput.value <= checkinDate) {
                        checkoutInput.value = "";
                    }
                });


                document.addEventListener("DOMContentLoaded", function () {
                    const toastEl = document.getElementById('successToast');
                    if (toastEl) {
                        const toast = new bootstrap.Toast(toastEl, {
                            autohide: true,
                            delay: 3000
                        });
                        toast.show();
                    }
                });
            </script>
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
