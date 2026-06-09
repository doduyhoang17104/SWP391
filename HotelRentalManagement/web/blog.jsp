<%-- 
    Document   : blog
    Created on : May 21, 2025, 1:21:59 PM
    Author     : ddhoang
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Bài viết</title>
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
    <style>
        .thumbnail {
            width: 100%;
            aspect-ratio: 4 / 3; 
            object-fit: cover;
            border-radius: 10px;
            height: auto;
            display: block;
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
                            <p class="breadcrumbs mb-2"><span class="mr-2"><a href="index.jsp">Home</a></span> <span>Blog</span></p>
                            <h1 class="mb-4 bread">Blog</h1>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <section class="ftco-section">
            <div class="container-fluid px-5">

                <div class="row">
                    <!-- Cột bên trái: Bộ lọc -->
                    <div class="col-lg-2 col-md-3">

                        <div class="filter-box p-3 bg-white shadow rounded">
                            <h5 class="mb-3">Bộ lọc tìm kiếm</h5>
                            <form action="searchpost" method="POST">
                                <div class="form-group mb-3">
                                    <label for="keyword">Từ khóa</label>
                                    <input type="text" id="keyword" name="key" class="form-control" placeholder="Nhập tiêu đề...">
                                </div>
                                <div class="form-group mb-3">
                                    <label for="author">Tác giả</label>
                                    <input type="text" id="author" name="author" class="form-control" placeholder="Nhập tên tác giả...">
                                </div>
                                <div class="form-group mb-3">
                                    <label for="date">Ngày đăng</label>
                                    <input type="date" id="date" name="date" class="form-control">
                                </div>

                                <button class="btn btn-primary w-100 mb-2" type="submit">Lọc bài viết</button>

                                <!-- Nút xem tất cả -->
                                <a href="viewblog" class="btn btn-outline-secondary w-100">Tất cả bài viết</a>
                            </form>
                        </div>

                    </div>

                    <!-- Cột bên phải: Danh sách blog -->
                    <div class="col-lg-10 col-md-9">

                        <div class="row">
                            <c:if test="${empty posts}">
                                <div class="col-12 text-center mt-4">
                                    <p class="text-danger font-weight-bold display-4">Không có bài viết để hiển thị.</p>
                                </div>
                            </c:if>


                            <c:forEach items="${posts}" var="c">
                                <div class="col-md-3 d-flex ftco-animate mb-4">
                                    <div class="blog-entry align-self-stretch">
                                        <a href="blogdetail?id=${c.postId}" class="block-20">
                                            <img src="${c.image}" alt="Ảnh" class="thumbnail" />
                                        </a>
                                        <div class="text mt-3 d-block">
                                            <h3 class="heading mt-3"><a href="#">${c.title}</a></h3>
                                            <div class="meta mb-3">
                                                <div><a href="#">${c.createdAt}</a></div><br>
                                                <div><a href="#">${c.authorName}</a></div>
                                                <div><a href="#" class="meta-chat"><span class="icon-chat"></span> ${c.noComment}</a></div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>

                        <!-- Phân trang -->
                        <div class="row mt-4">
                            <div class="col text-center">
                                <div class="block-27">
                                    <ul>
                                        <c:if test="${tag > 1}">
                                            <li><a href="viewblog?index=${tag - 1}">&lt;</a></li>
                                            </c:if>
                                            <c:forEach begin="1" end="${endPage}" var="c">
                                            <li class="${tag == c ? 'active' : ''}"><a href="viewblog?index=${c}">${c}</a></li>
                                            </c:forEach>
                                            <c:if test="${tag < endPage}">
                                            <li><a href="viewblog?index=${tag + 1}">&gt;</a></li>
                                            </c:if>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div> <!-- end col-md-9 -->
                </div> <!-- end row -->
            </div> <!-- end container -->
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
