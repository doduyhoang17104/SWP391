<%-- 
   Document   : index
   Created on : May 21, 2025, 1:18:55 PM
   Author     : ddhoang
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Trang chủ</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">


        <link href="https://fonts.googleapis.com/css?family=Poppins:200,300,400,500,600,700" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css?family=Playfair+Display:400,400i,700,700i" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/nouislider@15.7.1/dist/nouislider.min.css">

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
        <link href="css/loginsuccess.css" rel="stylesheet" type="text/css"/>

        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

    </head>
    <style>
        .message-container {
            position: fixed;
            top: 20px;
            right: 20px;
            z-index: 9999;
        }

        .flash-message {
            position: relative;
            background-color: #28a745;
            color: white;
            padding: 15px 40px 15px 20px;
            border-radius: 8px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
            font-weight: bold;
            transition: opacity 0.5s ease;
            max-width: 350px;         /* ✅ giới hạn chiều ngang */
            word-wrap: break-word;    /* ✅ xuống dòng nếu dài */
            white-space: normal;      /* ✅ đảm bảo xuống dòng */
        }

        .close-btn {
            position: absolute;
            top: 5px;
            right: 10px;
            background: transparent;
            border: none;
            font-size: 20px;
            color: white;
            cursor: pointer;
        }
        .thumbnail {
            width: 84%;
            aspect-ratio: 4 / 3; /* hoặc 16 / 9 nếu muốn rộng hơn */
            object-fit: cover;
            border-radius: 10px;
            display: block;
        }
        .booking-form {
            background-color: #f8f9fa;
            border-radius: 15px;
            padding: 30px;
            box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
            margin-top: -152px;
        }

        .booking-form label {
            font-weight: 600;
            margin-bottom: 5px;
            display: block;
        }
        .booking-form .form-group {
            background-color: white;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
            padding: 20px;
        }
        .booking-form input[type="date"],
        .booking-form input[type="number"],
        .booking-form .btn-primary {
            border-radius: 10px;
            font-size: 18px;
            font-weight: bold;
            padding: 12px 0;
            background: linear-gradient(to right, #0066cc, #0099ff);
            border: none;
            transition: background 0.3s ease;
        }

        .booking-form .btn-primary:hover {
            background: linear-gradient(to right, #005bb5, #008ae6);
        }

        .booking-form .row .col-md-3,
        .booking-form .row .col-md-6 {
            margin-bottom: 20px;
        }

    </style>
    <body>

        <%@include file="menu.jsp" %>
        <div class="message-container">
            <c:if test="${not empty sessionScope.message}">
                <div class="flash-message" id="flashMessage">
                    ${sessionScope.message}
                    <button class="close-btn" onclick="closeFlash()">×</button>
                </div>
                <c:remove var="message" scope="session" />
            </c:if>
        </div>
        <section class="home-slider owl-carousel">
            <div class="slider-item" style="background-image:url(images/bg_1.jpg);">
                <div class="overlay"></div>
                <div class="container">
                    <div class="row no-gutters slider-text align-items-center justify-content-center">
                        <div class="col-md-12 ftco-animate text-center">
                            <div class="text mb-5 pb-3">
                                <h1 class="mb-3">Chào mừng bạn đến với hệ thống của chúng tôi</h1>
                                <h2>Khách sạn</h2>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="slider-item" style="background-image:url(images/bg_2.jpg);">
                <div class="overlay"></div>
                <div class="container">
                    <div class="row no-gutters slider-text align-items-center justify-content-center">
                        <div class="col-md-12 ftco-animate text-center">
                            <div class="text mb-5 pb-3">
                                <h1 class="mb-3">Trải Nghiệm Nghỉ Dưỡng Sang Trọng</h1>
                                <h2>Hãy Đồng Hành Cùng Chúng Tôi</h2>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <section class="ftco-booking" ">

            <div class="container">
                <div class="row">
                    <div class="col-lg-12">
                        <form action="listRoomBooking" method="get" class="booking-form">
                            <div class="row">
                                <div class="col-md-3 d-flex">
                                    <div class="form-group p-4 align-self-stretch d-flex align-items-end">
                                        <div class="wrap w-100">
                                            <label>Check-in Date</label>
                                            <input type="date"  id="check-in" name="checkin" class="form-control" value="${param.checkin}" required>
                                        </div>
                                    </div>
                                </div>

                                <div class="col-md-3 d-flex">
                                    <div class="form-group p-4 align-self-stretch d-flex align-items-end">
                                        <div class="wrap w-100">
                                            <label>Check-out Date</label>
                                            <input type="date"  id="check-out" name="checkout" class="form-control" value="${param.checkout}" required>
                                        </div>
                                    </div>
                                </div>

                                <div class="col-md-3 d-flex">
                                    <div class="form-group p-4 align-self-stretch d-flex align-items-end">
                                        <div class="wrap w-100">
                                            <label>Room Type</label>
                                            <select name="roomType" class="form-control" required>
                                                <option value="">All Types</option>
                                                <c:forEach var="type" items="${roomTypeList}">
                                                    <option value="${type.typeName}" <c:if test="${param.roomType eq type.typeName}">selected</c:if>>
                                                        ${type.typeName}
                                                    </option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                </div>

                                <div class="col-md-3 d-flex">
                                    <div class="form-group p-4 align-self-stretch d-flex align-items-end">
                                        <div class="wrap w-100">
                                            <label>Guests</label>
                                            <input type="number" name="guests" class="form-control" value="${param.guests}" min="1" max="10" required>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="row mt-4">

                                <div class="col-md-3 d-flex">
                                    <div class="form-group p-4 align-self-stretch d-flex align-items-end">
                                        <div class="wrap w-100">
                                            <label>Beds</label>
                                            <input type="number" name="bed" class="form-control" value="${param.bed}" min="1" max="5" required>
                                        </div>
                                    </div>
                                </div>

                                <div class="col-md-6 d-flex">
                                    <div class="form-group p-4 align-self-stretch d-flex align-items-end w-100">
                                        <div class="wrap w-100">
                                            <label>Price Range (VNĐ)</label>
                                            <div id="slider-range"></div>
                                            <div class="d-flex justify-content-between mt-2">
                                                <span id="price-min-text"></span>
                                                <span id="price-max-text"></span>
                                            </div>

                                            <!-- Hidden input -->
                                            <input type="hidden" name="minPrice" id="minPriceInput">
                                            <input type="hidden" name="maxPrice" id="maxPriceInput">
                                        </div>
                                    </div>
                                </div>



                                <div class="col-md-3 d-flex">
                                    <div class="form-group d-flex align-self-stretch align-items-end w-100 p-4">
                                        <input type="submit" value="Search" class="btn btn-primary py-3 px-4 w-100">
                                    </div>
                                </div>

                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </section>


<!--        <section class="ftco-section ftc-no-pb ftc-no-pt mt-5">
            <div class="container">
                <div class="row">
                    <div class="col-md-5 p-md-5 img img-2 d-flex justify-content-center align-items-center" style="background-image: url(images/bg_2.jpg);">
                        <a href="https://vimeo.com/45830194" class="icon popup-vimeo d-flex justify-content-center align-items-center">
                            <span class="icon-play"></span>
                        </a>
                    </div>
                    <div class="col-md-7 py-5 wrap-about pb-md-5 ftco-animate">
                        <div class="heading-section heading-section-wo-line pt-md-5 pl-md-5 mb-5">
                            <div class="ml-md-0">
                                <span class="subheading">Welcome to Royal Hotel</span>
                                <h2 class="mb-4">Welcome To Our Hotel</h2>
                            </div>
                        </div>
                        <div class="pb-md-5">
                            
                            <ul class="ftco-social d-flex">
                                <li class="ftco-animate"><a href="#"><span class="icon-twitter"></span></a></li>
                                <li class="ftco-animate"><a href="#"><span class="icon-facebook"></span></a></li>
                                <li class="ftco-animate"><a href="#"><span class="icon-google-plus"></span></a></li>
                                <li class="ftco-animate"><a href="#"><span class="icon-instagram"></span></a></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </section>-->

        <section class="ftco-section">
            <div class="container">
                <div class="row d-flex">
                    <div class="col-md-3 d-flex align-self-stretch ftco-animate">
                        <div class="media block-6 services py-4 d-block text-center">
                            <div class="d-flex justify-content-center">
                                <div class="icon d-flex align-items-center justify-content-center">
                                    <span class="flaticon-reception-bell"></span>
                                </div>
                            </div>
                            <div class="media-body p-2 mt-2">
                                <h3 class="heading mb-3">25/7 Front Desk</h3>
                                <p>A small river named Duden flows by their place and supplies.</p>
                            </div>
                        </div>      
                    </div>
                    <div class="col-md-3 d-flex align-self-stretch ftco-animate">
                        <div class="media block-6 services py-4 d-block text-center">
                            <div class="d-flex justify-content-center">
                                <div class="icon d-flex align-items-center justify-content-center">
                                    <span class="flaticon-serving-dish"></span>
                                </div>
                            </div>
                            <div class="media-body p-2 mt-2">
                                <h3 class="heading mb-3">Restaurant Bar</h3>
                                <p>A small river named Duden flows by their place and supplies.</p>
                            </div>
                        </div>    
                    </div>
                    <div class="col-md-3 d-flex align-sel Searchf-stretch ftco-animate">
                        <div class="media block-6 services py-4 d-block text-center">
                            <div class="d-flex justify-content-center">
                                <div class="icon d-flex align-items-center justify-content-center">
                                    <span class="flaticon-car"></span>
                                </div>
                            </div>
                            <div class="media-body p-2 mt-2">
                                <h3 class="heading mb-3">Transfer Services</h3>
                                <p>A small river named Duden flows by their place and supplies.</p>
                            </div>
                        </div>      
                    </div>
                    <div class="col-md-3 d-flex align-self-stretch ftco-animate">
                        <div class="media block-6 services py-4 d-block text-center">
                            <div class="d-flex justify-content-center">
                                <div class="icon d-flex align-items-center justify-content-center">
                                    <span class="flaticon-spa"></span>
                                </div>
                            </div>
                            <div class="media-body p-2 mt-2">
                                <h3 class="heading mb-3">Spa Suites</h3>
                                <p>A small river named Duden flows by their place and supplies.</p>
                            </div>
                        </div>      
                    </div>
                </div>
            </div>
        </section>
        <section class="ftco-section ftco-counter img" id="section-counter" style="background-image: url(images/bg_1.jpg);">
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-md-10">
                        <div class="row">
                            <div class="col-md-3 d-flex justify-content-center counter-wrap ftco-animate">
                                <div class="block-18 text-center">
                                    <div class="text">
                                        <strong class="number" data-number="50000">0</strong>
                                        <span>Happy Guests</span>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-3 d-flex justify-content-center counter-wrap ftco-animate">
                                <div class="block-18 text-center">
                                    <div class="text">
                                        <strong class="number" data-number="3000">0</strong>
                                        <span>Rooms</span>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-3 d-flex justify-content-center counter-wrap ftco-animate">
                                <div class="block-18 text-center">
                                    <div class="text">
                                        <strong class="number" data-number="1000">0</strong>
                                        <span>Staffs</span>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-3 d-flex justify-content-center counter-wrap ftco-animate">
                                <div class="block-18 text-center">
                                    <div class="text">
                                        <strong class="number" data-number="100">0</strong>
                                        <span>Destination</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>  
        <section class="ftco-section">
            <div class="container">
                <div class="row justify-content-center mb-5 pb-3">
                    <div class="col-md-7 heading-section text-center ftco-animate">
                        <h2>Bài viết mới</h2>
                    </div>
                </div>
                <div class="row d-flex">
                    <c:forEach items="${newBlog}" var="c">
                        <div class="col-md-4 d-flex ftco-animate">
                            <div class="blog-entry align-self-stretch">
                                <a href="blogdetail?id=${c.postId}" class="block-20" style="background-image:;">
                                    <img src="${c.image}" alt="alt" class="thumbnail"/>
                                </a>
                                <div class="text mt-3 d-block">
                                    <h3 class="heading mt-3"><a href="#">${c.title}</a></h3>
                                    <div class="meta mb-3">
                                        <div><a href="#">${c.createdAt}</a></div>
                                        <div><a href="#">${c.authorName}</a></div>
                                        <div><a href="#" class="meta-chat"><span class="icon-chat"></span> ${c.noComment}</a></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </section>

        <section class="instagram">
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

            <jsp:include page="views/user/chatbot.jsp" />


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



        <div id="successModal" class="modal-overlay hidden">
            <div class="modal-box">
                <div class="checkmark-circle">
                    <div class="background"></div>
                    <div class="checkmark draw"></div>
                </div>
                <h2>🎉 Đăng nhập thành công!</h2>
                <button onclick="closeModal()">Đóng</button>
            </div>
        </div>

        <script>
            function closeModal() {
                document.getElementById("successModal").classList.add("hidden");

                const url = new URL(window.location);
                url.searchParams.delete('loginSuccess');
                window.history.replaceState({}, document.title, url);
            }

            window.addEventListener("DOMContentLoaded", () => {
                const params = new URLSearchParams(window.location.search);
                if (params.get("loginSuccess") === "true") {
                    document.getElementById("successModal").classList.remove("hidden");

                    setTimeout(closeModal, 3000);
                }
            });
            function closeFlash() {
                const flash = document.getElementById("flashMessage");
                if (flash) {
                    flash.style.opacity = 0;
                    setTimeout(() => flash.style.display = "none", 500);
                }
            }

            setTimeout(closeFlash, 3000);
        </script>
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
        <script>
            const minPrice = document.getElementById("minPrice");
            const maxPrice = document.getElementById("maxPrice");
            const minPriceValue = document.getElementById("minPriceValue");
            const maxPriceValue = document.getElementById("maxPriceValue");

            function formatCurrency(value) {
                return new Intl.NumberFormat('vi-VN').format(value);
            }

            minPrice.addEventListener("input", function () {
                if (parseInt(minPrice.value) > parseInt(maxPrice.value)) {
                    maxPrice.value = minPrice.value;
                    maxPriceValue.innerText = formatCurrency(maxPrice.value);
                }
                minPriceValue.innerText = formatCurrency(minPrice.value);
            });

            maxPrice.addEventListener("input", function () {
                if (parseInt(maxPrice.value) < parseInt(minPrice.value)) {
                    minPrice.value = maxPrice.value;
                    minPriceValue.innerText = formatCurrency(minPrice.value);
                }
                maxPriceValue.innerText = formatCurrency(maxPrice.value);
            });
        </script>
        <script src="https://cdn.jsdelivr.net/npm/nouislider@15.7.1/dist/nouislider.min.js"></script>
        <script>
            const slider = document.getElementById('slider-range');

            const minVal = ${param.minPrice != null ? param.minPrice : 100000};
            const maxVal = ${param.maxPrice != null ? param.maxPrice : 2500000};

            noUiSlider.create(slider, {
                start: [minVal, maxVal],
                connect: true,
                range: {
                    'min': 0,
                    'max': 5000000
                },
                step: 100000,
                format: {
                    to: value => Math.round(value),
                    from: value => Number(value)
                }
            });

            const minText = document.getElementById('price-min-text');
            const maxText = document.getElementById('price-max-text');
            const minInput = document.getElementById('minPriceInput');
            const maxInput = document.getElementById('maxPriceInput');

            slider.noUiSlider.on('update', (values) => {
                const min = parseInt(values[0]);
                const max = parseInt(values[1]);

                minText.innerText = min.toLocaleString('vi-VN') + " VNĐ";
                maxText.innerText = max.toLocaleString('vi-VN') + " VNĐ";

                minInput.value = min;
                maxInput.value = max;
            });
        </script>

    </body>


</html>