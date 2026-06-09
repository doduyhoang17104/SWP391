<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Room Details</title>
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <!-- Bootstrap 5 CDN -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

        <style>
            .room-img {
                background-size: cover;
                background-position: center;
                height: 300px;
                border-radius: 10px;
            }

            .thumbnail {
                width: 100%;
                aspect-ratio: 4 / 3;
                object-fit: cover;
                border-radius: 10px;
                display: block;
            }

            .hero-wrap {
                background-image: url('images/bg_1.jpg');
                background-size: cover;
                background-position: center;
                position: relative;
                padding: 100px 0;
                color: white;
            }

            .hero-wrap::before {
                content: "";
                position: absolute;
                top: 0;
                left: 0;
                right: 0;
                bottom: 0;
                background-color: rgba(0, 0, 0, 0.4);
            }

            .hero-content {
                position: relative;
                z-index: 1;
            }
        </style>
    </head>

    <body>

        <%@include file="menu.jsp" %>

        <div class="hero-wrap" style="background-image: url('images/bg_1.jpg');">
            <div class="overlay"></div>
            <div class="container">
                <div class="row no-gutters slider-text d-flex align-itemd-end justify-content-center">
                    <div class="col-md-9 ftco-animate text-center d-flex align-items-end justify-content-center">
                        <div class="text">
                            <p class="breadcrumbs mb-2" data-scrollax="properties: { translateY: '30%', opacity: 1.6 }"><span class="mr-2"><a href="index.jsp">Home</a></span> <span class="mr-2"><a href="rooms.jsp">Room</a></span> <span>Room Single</span></p>
                            <h1 class="mb-4 bread">Room Single</h1>
                        </div>
                    </div>
                </div>
            </div>
        </div>


        <section class="ftco-section">
            <div class="container">
                <div class="row">
                    <div class="col-lg-8">
                        <div class="row">
                            <div class="col-md-12 ftco-animate">
                                <h2 class="mb-4">${room.roomType.typeName}</h2>

                                <div class="single-slider owl-carousel">
                                    <c:forEach var="img" items="${room.imageUrls}">
                                        <div class="item">
                                            <div class="room-img" style="background-image: url('${img}');"></div>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>

                            <div class="col-md-12 room-single mt-4 mb-5 ftco-animate">
                                <p>${room.roomType.description}</p>

                                <div class="d-md-flex mt-5 mb-5">
                                    <ul class="list">
                                        <li><span>Max:</span> ${room.capacity} </li>
                                        <li><span>Size:</span> ${room.size}</li>
                                    </ul>
                                    <ul class="list ml-md-5">
                                        <li><span>View:</span> Sea View</li> 
                                        <li><span>Bed:</span> ${room.bed}</li>
                                    </ul>
                                </div>
                                <h3 class="text-warning ftco-animate">
                                    ★ <fmt:formatNumber value="${avgRating}" type="number" maxFractionDigits="1"/> / 5
                                </h3>
                                <form action="checkout" method="get">
                                    <input type="hidden" name="checkin" value="${checkIn}">
                                    <input type="hidden" name="checkout" value="${checkOut}">
                                    <input type="hidden" name="roomId" value="${room.roomId}" />
                                    <button type="submit" class="btn btn-primary">Đặt phòng ngay</button>
                                </form>
                                <h4 class="mt-5 mb-3">Đánh giá của khách hàng</h4>

                                <c:forEach items="${feedback}" var="c">
                                    <div class="border rounded p-4 shadow-sm mb-4 bg-light">
                                        <div class="d-flex justify-content-between align-items-center mb-2">
                                            <div>
                                                <strong class="text-dark">${c.authorName}</strong>
                                                <small class="text-muted d-block">${c.createAt}</small>
                                            </div>
                                            <div class="text-warning fs-5">
                                                <c:forEach begin="1" end="5" var="i">
                                                    <c:choose>
                                                        <c:when test="${i <= c.rating}">★</c:when>
                                                        <c:otherwise>☆</c:otherwise>
                                                    </c:choose>
                                                </c:forEach>
                                            </div>
                                        </div>
                                        <p class="mb-0 text-dark">${c.content}</p>
                                    </div>
                                </c:forEach>

                            </div>


                        </div>
                    </div> 
                    <div class="col-lg-4 sidebar ftco-animate">

                        <div class="sidebar-box ftco-animate">
                            <h3>Bài viết mới</h3>
                            <c:forEach var="c" items="${newBlog}">
                                <div class="block-21 mb-4 d-flex">
                                    <a href="blogdetail?id=${c.postId}" class="blog-img mr-4" style="background-image:">
                                        <img src="${c.image}" alt="alt" class="thumbnail"/>
                                    </a>                                    <div class="text">
                                        <h3 class="heading"><a href="#">${c.title}</a></h3>
                                        <div class="meta">
                                            <div><a href="#"><span class="icon-calendar"></span>${c.createdAt}</</a></div>
                                            <div><a href="#"><span class="icon-person"></span> ${c.authorName}</a></div>
                                            <div><a href="#"><span class="icon-chat"></span>${c.noComment}</a></div>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>

                        </div>
                    </div>
                </div>
            </div>
        </section> <!-- .section -->

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