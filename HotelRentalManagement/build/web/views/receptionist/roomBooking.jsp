<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Phòng</title>
        <link rel="preconnect" href="https://fonts.googleapis.com" />
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
        <link
            href="https://fonts.googleapis.com/css2?family=Lato:wght@400;700&display=swap"
            rel="stylesheet"
            />
        <link

            rel="stylesheet"
            href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css"
            />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" />

        <style>
            :root {
                --sidebar-bg: #4c3c6f;
                --main-bg: #f8f9fa;
                --text-light: #ffffff;
                --text-dark: #333333;
                --border-color: #e9ecef;
                --primary-blue: #007bff;
                --accent-green: #10b981;
                --accent-purple: #8b5cf6;
            }

            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
            }

            body {
                font-family: 'Lato', sans-serif;
                background-color: var(--main-bg);
                color: var(--text-dark);
            }

            .top-navbar {
                display: flex;
                justify-content: space-between;
                align-items: center;
                padding: 1rem 2.5rem;
                background-color: #fff;
                border-bottom: 1px solid var(--border-color);
            }

            .logo {
                font-size: 1.5rem;
                font-weight: 700;
            }

            .logo-heart {
                color: var(--primary-blue);
            }

            .nav-links a {
                text-decoration: none;
                color: #495057;
                margin-left: 1.5rem;
                font-weight: 500;
                transition: color 0.2s ease;
            }

            .nav-links a:hover {
                color: var(--primary-blue);
            }

            .dashboard-container {
                display: flex;
                height: calc(100vh - 65px);
            }

            .sidebar {
                width: 260px;
                background-color: var(--sidebar-bg);
                color: var(--text-light);
                display: flex;
                flex-direction: column;
                padding: 20px;
                overflow-y: auto;
            }

            .sidebar-header h2 {
                text-align: center;
                margin-bottom: 25px;
            }

            .search-bar {
                position: relative;
                margin-bottom: 25px;
            }

            .search-bar i {
                position: absolute;
                left: 15px;
                top: 50%;
                transform: translateY(-50%);
                color: #9e9cb1;
            }

            .search-bar input {
                width: 100%;
                padding: 10px 10px 10px 40px;
                border-radius: 8px;
                border: none;
                background-color: #5e4a87;
                color: var(--text-light);
            }

            .sidebar-nav ul {
                list-style: none;
            }

            .sidebar-nav li {
                margin-bottom: 5px;
            }

            .sidebar-nav a {
                display: flex;
                align-items: center;
                padding: 12px 15px;
                text-decoration: none;
                color: #c5c1d6;
                border-radius: 8px;
                transition: background-color 0.3s;
            }

            .sidebar-nav a i {
                width: 20px;
                margin-right: 15px;
            }

            .sidebar-nav a:hover {
                background-color: #5e4a87;
            }

            .sidebar-nav li.active a {
                background-color: #5e4a87;
                color: var(--text-light);
                font-weight: bold;
            }

            .sidebar-footer {
                margin-top: auto;
            }

            .user-profile {
                display: flex;
                align-items: center;
                padding: 10px;
                background-color: #5e4a87;
                border-radius: 8px;
            }

            .user-profile img {
                border-radius: 50%;
                margin-right: 10px;
            }

            /* --- MAIN CONTENT --- */
            .main-content {
                flex: 1;
                padding: 20px 30px;
                overflow-y: auto;
            }

            /* --- KHUNG TÌM KIẾM --- */
            .booking-section {
                padding: 20px;
                background-color: #fff;
                border-radius: 8px;
                margin-bottom: 20px;
                border: 1px solid var(--border-color);
            }

            .booking-section h1 {
                font-size: 1.75rem;
                margin-bottom: 1.5rem;
                font-weight: 700;
            }

            .search-form {
                display: flex;
                flex-wrap: wrap;
                gap: 1.5rem;
                align-items: flex-end;
            }

            .form-group {
                display: flex;
                flex-direction: column;
                flex: 1;
                min-width: 180px;
            }

            .form-group label {
                font-size: 0.9rem;
                color: #6c757d;
                margin-bottom: 0.5rem;
                font-weight: 500;
            }

            .form-group select,
            .form-group input,
            .form-group .btn-search {
                padding: 0.8rem;
                border: 1px solid #ced4da;
                border-radius: 6px;
                font-size: 1rem;
            }

            .btn-search {
                background-color: var(--primary-blue);
                color: white;
                font-weight: bold;
                cursor: pointer;
                border-color: var(--primary-blue);
            }

            .btn-search:hover {
                background-color: #0056b3;
            }

            /* --- DANH SÁCH KHÁCH SẠN --- */
            .hotel-list-section {
                display: grid;
                grid-template-columns: repeat(auto-fill, minmax(260px, 1fr));
                gap: 1.5rem;
            }

            .hotel-card {
                background-color: #fff;
                border-radius: 8px;
                box-shadow: 0 4px 6px rgba(0,0,0,0.05);
                overflow: hidden;
                transition: transform 0.3s ease, box-shadow 0.3s ease;
                opacity: 0;
                animation: fadeIn 0.6s ease-out forwards;
            }

            @keyframes fadeIn {
                from {
                    opacity: 0;
                    transform: translateY(20px);
                }
                to {
                    opacity: 1;
                    transform: translateY(0);
                }
            }

            .hotel-card:hover {
                transform: translateY(-5px);
                box-shadow: 0 8px 20px rgba(0,0,0,0.1);
            }

            .hotel-card img {
                width: 100%;
                height: 160px;
                object-fit: cover;
                display: block;
            }

            .hotel-info {
                padding: 1rem;
            }

            .hotel-info h3 {
                margin-top: 0;
                margin-bottom: 0.25rem;
                font-size: 1.25rem;
            }
            .hotel-location, .hotel-price {
                color: #6c757d;
                margin-bottom: 0.5rem;
                font-size: 0.9rem;
            }
            .hotel-rating {
                color: #ffc107;
                margin-bottom: 1rem;
            }
            .btn-view-more {
                width: 100%;
                padding: 0.75rem;
                border: 1px solid var(--primary-blue);
                background-color: white;
                color: var(--primary-blue);
                border-radius: 5px;
                cursor: pointer;
                font-weight: bold;
            }
            .btn-view-more:hover {
                background-color: var(--primary-blue);
                color: white;
            }

        </style>
        <style>
            ul.pagination {
                display: flex;
                justify-content: center;
                list-style: none;
                padding-left: 0;
                margin-top: 30px;
            }

            ul.pagination li {
                margin: 0 5px;
            }

            ul.pagination li a {
                display: block;
                padding: 8px 14px;
                background-color: #f1f1f1;
                color: #007bff;
                border: 1px solid #ddd;
                border-radius: 5px;
                text-decoration: none;
                transition: all 0.2s ease;
            }

            ul.pagination li a:hover {
                background-color: #007bff;
                color: white;
            }

            ul.pagination li.active a {
                background-color: #007bff;
                color: white;
                font-weight: bold;
                border-color: #007bff;
            }
        </style>

    </head>
    <body>

        <div class="dashboard-container">

            <%@include file="menu-receptionist.jsp" %>

            <main class="main-content">
                <section class="booking-section">
                    <h1>Tìm kiếm & Đặt phòng</h1>
                    <form action="searchRoom" method="get" class="search-form">
                        <div class="form-group">
                            <label for="check-in">Ngày nhận phòng</label>
                            <input type="date" id="check-in" name="checkin" value="${param.checkin}" required>
                        </div>
                        <div class="form-group">
                            <label for="check-out">Ngày trả phòng</label>
                            <input type="date" id="check-out" name="checkout" value="${param.checkout}" required>
                        </div>
                        <div class="form-group">
                            <label for="guests">Số khách</label>
                            <input type="text" id="guests" name="guests" value="${param.guests}" required>
                        </div>
                        <div class="form-group">
                            <label for="roomType">Loại phòng</label>
                            <select name="roomType" id="room-type" required>
                                <option value="">-- Tất cả --</option>
                                <c:forEach var="type" items="${roomTypes}">
                                    <option value="${type.typeName}" ${param.roomType == type.typeName ? 'selected' : ''} >
                                        ${type.typeName}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="form-group">
                            <label for="bed">Số giường</label>
                            <input type="number" id="bed" name="bed" min="1" value="${param.bed}" required>
                        </div>

                        <div class="form-group">
                            <label for="maxPrice">Giá tối thiểu</label>
                            <input type="number" id="minPrice" name="minPrice" min="0" value="${param.minPrice}" required>
                        </div>

                        <div class="form-group">
                            <label for="maxPrice">Giá tối đa</label>
                            <input type="number" id="maxPrice" name="maxPrice" min="0" value="${param.maxPrice}" required>
                        </div>

                        <div class="form-group">
                            <button type="submit" class="btn-search">Tìm kiếm</button>
                        </div>
                    </form>

                </section>

                <section id="hotel-list" class="hotel-list-section">
                    <c:forEach var="room" items="${rooms}">
                        <div class="hotel-card">
                            <c:choose>
                                <c:when test="${not empty room.imageUrls}">
                                    <img src="${room.imageUrls[0]}" alt="Ảnh phòng">
                                </c:when>
                                <c:otherwise>
                                    <img src="photo/1.jpg" alt="Ảnh mặc định">
                                </c:otherwise>
                            </c:choose>

                            <div class="hotel-info">
                                <h3>${room.roomType.typeName} - Phòng ${room.roomNumber}</h3>
                                <div class="hotel-location">Sức chứa: ${room.capacity}</div>
                                <div class="hotel-location">Số giường: ${room.bed}</div>

                                <c:if test="${room.bookedInRange}">
                                    <div class="room-status">
                                        <span style="color: #006D77;">
                                            <div> Từ:
                                                <fmt:formatDate value="${room.bookingCheckinDate}" pattern="dd/MM/yyyy HH:mm" />
                                            </div>  <div>  đến
                                                <fmt:formatDate value="${room.bookingCheckoutDate}" pattern="dd/MM/yyyy HH:mm" />
                                            </div>
                                        </span>
                                    </div>
                                </c:if>
                                <c:if test="${room.daysLeft != null and room.bookedInRange}">
                                    <div class="room-status">
                                        <span style="color:red;">Còn lại: <b>${room.daysLeft}</b> ngày đến ngày trả phòng</span>
                                    </div>
                                </c:if>


                                <div class="hotel-price">
                                    <b><fmt:formatNumber value="${room.roomType.basePrice}" type="number" groupingUsed="true"/></b> vnd/đêm
                                </div>
                                <c:if test="${not empty param.checkin and not empty param.checkout and not empty param.guests and not room.bookedInRange}">
                                    <form action="bookOffline" method="get">
                                        <input type="hidden" name="roomId" value="${room.roomId}">
                                        <input type="hidden" name="roomNumber" value="${room.roomNumber}">
                                        <input type="hidden" name="checkin" value="${param.checkin}">
                                        <input type="hidden" name="checkout" value="${param.checkout}">
                                        <button type="submit" class="btn-view-more">Đặt ngay</button>
                                    </form>
                                </c:if>


                            </div>
                        </div>
                    </c:forEach>
                </section>

                <ul class="pagination">

                    <c:if test="${pageIndex > 1}">
                        <li><a href="searchRoom?page=${pageIndex - 1}">&laquo;</a></li>
                        </c:if>


                    <c:forEach begin="1" end="${totalPages}" var="i">
                        <li class="${i == pageIndex ? 'active' : ''}">
                            <a href="searchRoom?page=${i}">${i}</a>
                        </li>
                    </c:forEach>

                    <c:if test="${pageIndex < totalPages}">
                        <li><a href="searchRoom?page=${pageIndex + 1}">&raquo;</a></li>
                        </c:if>
                </ul>


            </main>
        </div>
        <c:if test="${not empty sessionScope.showSuccessModal}">
            <div class="position-fixed top-0 end-0 p-3" style="z-index: 1055;">
                <div class="toast show align-items-center text-bg-success border-0"
                     role="alert" aria-live="assertive" aria-atomic="true" id="successToast"
                     data-bs-autohide="true" data-bs-delay="3000">
                    <div class="d-flex">
                        <div class="toast-body">
                            <i class="bi bi-check-circle-fill me-2"></i> ${sessionScope.showSuccessModal}
                        </div>
                        <button type="button" class="btn-close btn-close-white me-2 m-auto"
                                data-bs-dismiss="toast" aria-label="Đóng"></button>
                    </div>
                </div>
            </div>

            <%
                session.removeAttribute("showSuccessModal");
            %>
        </c:if>


        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>


        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
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

    </body>
</html>