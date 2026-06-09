<%-- 
    Document   : contact
    Created on : May 21, 2025, 1:22:51 PM
    Author     : ddhoang
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Liên hệ</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">


        <link rel="stylesheet" href="css/style.css">
    </head>
    <body>
        <%@include file="menu.jsp" %>
        <c:if test="${not empty sessionScope.success}">
            <div class="position-fixed p-3" style="top: 1rem; right: 1rem; z-index: 1080;">
                <div class="toast align-items-center text-white bg-success border-0 show"
                     role="alert"
                     aria-live="assertive"
                     aria-atomic="true"
                     data-bs-autohide="true"
                     style="max-width: 350px; word-wrap: break-word; white-space: normal; box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);">
                    <div class="d-flex">
                        <div class="toast-body text-start">
                            <i class="bi bi-check-circle-fill me-2"></i>
                            ${sessionScope.success}
                        </div>
                        <button type="button"
                                class="btn text-white border-0 bg-transparent me-2 m-auto fs-5"
                                data-bs-dismiss="toast"
                                aria-label="Đóng">
                            <i class="bi bi-x-lg"></i>
                        </button>
                    </div>
                </div>
            </div>
            <c:remove var="success" scope="session" />
        </c:if>



        <div class="hero-wrap" style="background-image: url('images/bg_1.jpg');">
            <div class="overlay"></div>
            <div class="container">
                <div class="row no-gutters slider-text d-flex align-itemd-end justify-content-center">
                    <div class="col-md-9 ftco-animate text-center d-flex align-items-end justify-content-center">
                        <div class="text">
                            <p class="breadcrumbs mb-2"><span class="mr-2"><a href="index.jsp">Home</a></span> <span>Contact</span></p>
                            <h1 class="mb-4 bread">Contact Us</h1>
                        </div>
                    </div>
                </div>
            </div>
        </div>


        <section class="ftco-section contact-section bg-light">
            <div class="container">
                <div class="row d-flex mb-5 contact-info">
                    <div class="col-md-12 mb-4">
                        <h2 class="h3">Thông tin liên hệ</h2>
                    </div>
                    <div class="w-100"></div>
                    <div class="col-md-3 d-flex">
                        <div class="info bg-white p-4">
                            <span>Địa chỉ:</span><br>
                            <p><a>Trường Đại học FPT Hà Nội
                                Khu Công Nghệ Cao Hòa Lạc, km 29, Đại lộ, Thăng Long, Hà Nội</a></p>
                        </div>
                    </div>
                    <div class="col-md-3 d-flex">
                        <div class="info bg-white p-4">
                            <span>Số điện thoại:</span> 
                            <p><a href="tel://1234567920">0394855685</a><p/>
                        </div>
                    </div>
                    <div class="col-md-3 d-flex">
                        <div class="info bg-white p-4">
                            <span>Email:</span> 
                            <p><a href="mailto:hoangdo171004@gmail.com">hoangdo171004@gmail.com</a></p>
                        </div>
                    </div>
                    <div class="col-md-3 d-flex">
                        <div class="info bg-white p-4">
                            <span>Website:</span> 
                            <p><a href="#">HotelRentalManagement</a></p>
                        </div>
                    </div>
                </div>
                <div class="row block-9">
                    <div class="col-md-6 order-md-last d-flex">
                        <form action="contact" method="POST" class="bg-white p-5 contact-form">
                            <div class="form-group">
                                <input type="text" name="name" class="form-control" placeholder="Tên của bạn">
                            </div>
                            <div class="form-group">
                                <input type="text" name="email" class="form-control" placeholder="Email của bạn">
                            </div>
                            <div class="form-group">
                                <input type="text" name="title" class="form-control" placeholder="Chủ đề">
                            </div>
                            <div class="form-group">
                                <textarea name="content" id="" cols="30" rows="7" class="form-control" placeholder="Tin nhắn"></textarea>
                            </div>
                            <div class="form-group">
                                <input type="submit" value="Gửi tin nhắn" class="btn btn-primary py-3 px-5">
                            </div>
                        </form>

                    </div>

                    <div class="col-md-6 d-flex">
                        <iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d2036.8328988567714!2d105.52386053874251!3d21.012893195158117!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x3135abc60e7d3f19%3A0x2be9d7d0b5abcbf4!2zVHLGsOG7nW5nIMSQ4bqhaSBo4buNYyBGUFQgSMOgIE7hu5lp!5e1!3m2!1svi!2s!4v1749046078148!5m2!1svi!2s" width="600" height="650" style="border:0;" allowfullscreen="" loading="lazy" referrerpolicy="no-referrer-when-downgrade"></iframe>
                    </div>
                </div>
            </div>
        </section>



        <%@include file="footer.jsp" %>



        <!-- loader -->
        <div id="ftco-loader" class="show fullscreen"><svg class="circular" width="48px" height="48px"><circle class="path-bg" cx="24" cy="24" r="22" fill="none" stroke-width="4" stroke="#eeeeee"/><circle class="path" cx="24" cy="24" r="22" fill="none" stroke-width="4" stroke-miterlimit="10" stroke="#F96D00"/></svg></div>


        <script src="js/jquery.min.js"></script>
        <script src="js/jquery-migrate-3.0.1.min.js"></script>
        <script src="js/popper.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
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
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">

    </body>
    <script>
        // Tự động ẩn thông báo sau 4 giây
        window.addEventListener('DOMContentLoaded', () => {
            const toastElList = [].slice.call(document.querySelectorAll('.toast'));
            toastElList.map(function (toastEl) {
                const toast = new bootstrap.Toast(toastEl, {delay: 3000});
                toast.show();
            });
        });
        let selectedForm = null;

        document.querySelectorAll(".show-confirm-modal").forEach(btn => {
            btn.addEventListener("click", function () {
                selectedForm = this.closest("form");
            });
        });

        document.getElementById("confirmCancelBtn").addEventListener("click", function () {
            if (selectedForm) {
                selectedForm.submit();
            }
        });
    </script>


</html>
