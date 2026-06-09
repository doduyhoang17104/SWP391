<%-- 
    Document   : blog-single
    Created on : May 21, 2025, 1:22:15 PM
    Author     : ddhoang
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        .blog-single-title {
            font-size: 36px;
        }

        .blog-single-content {
            font-size: 20px;
        }

        .sidebar-box p,
        .sidebar-box li,
        .sidebar-box h3 {
            font-size: 18px;
        }

        .comment-body p,
        .comment-body h3,
        .comment-form-wrap label,
        .comment-form-wrap input,
        .comment-form-wrap textarea {
            font-size: 17px;
        }

        .thumbnail {
            width: 84%;
            aspect-ratio: 4 / 3; /* hoặc 16 / 9 nếu muốn rộng hơn */
            object-fit: cover;
            border-radius: 10px;
            display: block;
        }
        .comment-avatar {
            flex-shrink: 0;
        }

        .avatar-img {
            width: 60px;
            height: 60px;
            border-radius: 50%;
            object-fit: cover;
        }
        .like-btn {
            display: inline-block;
            color: gray; /* Chưa like: màu xám */
            cursor: pointer;
            font-size: 16px;
            margin-top: 5px;
            transition: color 0.3s;
        }

        .like-btn.liked {
            color: blue; /* Đã like: màu xanh lá cây */
        }

        .like-btn i {
            margin-right: 6px;
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
                            <p class="breadcrumbs mb-2" data-scrollax="properties: { translateY: '30%', opacity: 1.6 }"><span class="mr-2"><a href="index.jsp">Home</a></span> <span class="mr-2"><a href="blog.jsp">Blog</a></span> <span>Blog Single</span></p>
                            <h1 class="mb-4 bread">Blog Single</h1>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <section class="ftco-section ftco-degree-bg">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-9 ftco-animate order-md-last">
                        <h2 class="mb-3 blog-single-title">${postDetail.title}</h2>

                        <p>
                            <img src="${postDetail.image}" alt="" class="thumbnail">
                        </p>

                        <p class="blog-single-content">${postDetail.content}</p>

                        <div class="tag-widget post-tag-container mb-5 mt-5">
                            <div class="tagcloud">
                                <a href="#" class="tag-cloud-link">Life</a>
                                <a href="#" class="tag-cloud-link">Sport</a>
                                <a href="#" class="tag-cloud-link">Tech</a>
                                <a href="#" class="tag-cloud-link">Travel</a>
                            </div>
                        </div>
                        <h3>Tác giả bài viết</h3>
                        <div class="about-author d-flex p-4 bg-light">
                            <div class="comment-avatar mr-3">
                                <c:choose>
                                    <c:when test="${not empty postDetail.authorImage}">
                                        <img src="images/avatar/${postDetail.authorImage}" alt="Avatar" class="avatar-img">
                                    </c:when>
                                    <c:otherwise>
                                        <img src="https://dongvat.edu.vn/upload/2025/03/anh-dai-dien-facebook-mac-dinh-005.webp" alt="Avatar" class="avatar-img">
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="desc align-self-md-center"> 
                                <h3>${postDetail.authorName}</h3>
                                <p>Chúng tôi chấp nhận, tuy nhiên, rằng có những nhu cầu thiết yếu và những điều khiến chúng ta hài lòng một cách dễ chịu; chúng là những thứ đáng được đặt đúng chỗ, là sự thật, là lỗi lầm do hiểu lầm khôn ngoan, là sự giống nhau về hiểu biết, là sáng tạo, là những kẻ chạy trốn của tình yêu không bao giờ có.</p>
                            </div>
                        </div>


                        <div class="pt-5 mt-5">
                            <c:choose>
                                <c:when test="${count == 0}">
                                    <h3 class="mb-5" style="color: red">Hãy là người đầu tiên bình luận bài viết này</h3>
                                </c:when>
                                <c:otherwise>
                                    <h3 class="mb-5">${count} Comments</h3>
                                </c:otherwise>
                            </c:choose>
                            <ul class="comment-list">
                                <c:forEach items="${listComment}" var="c">
                                    <li class="comment d-flex mb-4">
                                        <div class="comment-avatar mr-3">
                                            <c:choose>
                                                <c:when test="${not empty c.userImage}">
                                                    <img src="images/avatar/${c.userImage}" alt="Avatar" class="avatar-img">
                                                </c:when>
                                                <c:otherwise>
                                                    <img src="https://dongvat.edu.vn/upload/2025/03/anh-dai-dien-facebook-mac-dinh-005.webp" alt="Avatar" class="avatar-img">
                                                </c:otherwise>
                                            </c:choose>
                                        </div>

                                        <div class="comment-body w-100 position-relative">
                                            <h3>${c.authorName}</h3>
                                            <div class="meta">${c.createdAt}</div>
                                            <p>${c.content}</p>
                                            <p class="like-btn"><i class="icon-thumbs-up"></i> Thích</p>
                                            <p><a href="#" class="reply">Reply</a></p>
                                            <!-- Nút xoá (chỉ hiện với quản lý) -->
                                            <c:if test="${sessionScope.user != null && sessionScope.user.getRoleid() == 3}">
                                                <form action="deletecomment" method="post" class="position-absolute" style="top: 0; right: 0;">
                                                    <input type="hidden" name="commentId" value="${c.commentId}" />
                                                    <input type="hidden" name="postId" value="${postDetail.postId}" />
                                                    <button 
                                                        type="submit" 
                                                        style="border: none; background: none; color: red; font-weight: bold;"
                                                        onclick="return confirm('Bạn có chắc chắn muốn xóa bình luận này không?');"
                                                        >
                                                        Xóa bình luận
                                                    </button>
                                                </form>
                                            </c:if>

                                        </div>
                                    </li>
                                </c:forEach>




                            </ul>
                            <!-- END comment-list -->

                            <div class="comment-form-wrap pt-5">
                                <h3 class="mb-5">Bình luận của bạn</h3>
                                <form action="addcomment" method="POST" class="p-5 bg-light">
                                    <!-- Truyền postId ẩn -->
                                    <input type="hidden" name="postId" value="${postDetail.postId}" />
                                    <div class="form-group">
                                        <label for="message">Bình luận</label>
                                        <textarea name="message" id="message" cols="30" rows="10" class="form-control"></textarea>
                                    </div>
                                    <div class="form-group">
                                        <input type="submit" value="Post Comment" class="btn py-3 px-4 btn-primary">
                                    </div>
                                </form>
                            </div>
                        </div>

                    </div> <!-- .col-md-8 -->
                    <div class="col-lg-3 sidebar ftco-animate">
                        <div class="sidebar-box text-center mb-4">
                            <a href="viewblog" class="btn btn-primary btn-lg px-5 py-3">← Quay lại blog</a>

                        </div>


                        <div class="sidebar-box ftco-animate">
                            <h3>Bài viết mới </h3>
                            <c:forEach items="${newBlog}" var="c">
                                <div class="block-21 mb-4 d-flex">
                                    <a href="blogdetail?id=${c.postId}" class="blog-img mr-4" style="background-image:">
                                        <img src="${c.image}" alt="alt" class="thumbnail"/>
                                    </a>
                                    <div class="text">
                                        <h3 class="heading"><a href="#">${c.title}</a></h3>
                                        <div class="meta">
                                            <div><a href="#"><span class="icon-calendar"></span> ${c.createdAt}</a></div>
                                            <div><a href="#"><span class="icon-person"></span> ${c.authorName}</a></div>
                                            <div><a href="#"><span class="icon-chat"></span> ${c.noComment}</a></div>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>

                        </div>

                        <div class="sidebar-box ftco-animate">
                            <h3>Tag Cloud</h3>
                            <div class="tagcloud">
                                <a href="#" class="tag-cloud-link">dish</a>
                                <a href="#" class="tag-cloud-link">menu</a>
                                <a href="#" class="tag-cloud-link">food</a>
                                <a href="#" class="tag-cloud-link">sweet</a>
                                <a href="#" class="tag-cloud-link">tasty</a>
                                <a href="#" class="tag-cloud-link">delicious</a>
                                <a href="#" class="tag-cloud-link">desserts</a>
                                <a href="#" class="tag-cloud-link">drinks</a>
                            </div>
                        </div>

                        <div class="sidebar-box ftco-animate ">
                            <h3>Giới thiệu</h3>
                            <p>Chào mừng quý khách đến với khách sạn của chúng tôi nơi mang đến sự thoải mái, tiện nghi và dịch vụ chuyên nghiệp. Với không gian sang trọng, đội ngũ nhân viên thân thiện và vị trí thuận lợi, chúng tôi cam kết sẽ mang lại cho quý khách một trải nghiệm lưu trú tuyệt vời và đáng nhớ.</p>
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
        <script>
                document.addEventListener("DOMContentLoaded", () => {
                    const likeButtons = document.querySelectorAll(".like-btn");

                    likeButtons.forEach(btn => {
                        btn.addEventListener("click", () => {
                            btn.classList.toggle("liked");
                        });
                    });
                });
        </script>

    </body>
</html>
