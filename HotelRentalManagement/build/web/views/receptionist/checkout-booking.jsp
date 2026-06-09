<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="java.time.LocalDate, java.time.LocalDateTime, java.time.temporal.ChronoUnit" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>CheckOut</title>
        <link rel="stylesheet" href="style.css" />
        <link rel="preconnect" href="https://fonts.googleapis.com" />
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
        <link
            href="https://fonts.googleapis.com/css2?family=Lato:wght@400;700&display=swap"
            rel="stylesheet"
            />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

        <link
            rel="stylesheet"
            href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css"
            />
        <style>
            :root {
                --sidebar-bg: #4c3c6f;
                --main-bg: #f8f9fa;
                --text-light: #ffffff;
                --text-dark: #333333;
                --border-color: #e9ecef;
                --primary-blue: #3b82f6;
                --accent-green: #10b981;
                --accent-purple: #8b5cf6;
                --weekend-bg: #fffbeb;
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

            .dashboard-container {
                display: flex;
                height: 100vh;
            }

            .sidebar {
                width: 260px;
                background-color: var(--sidebar-bg);
                color: var(--text-light);
                display: flex;
                flex-direction: column;
                padding: 20px;
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

            .search-bar input::placeholder {
                color: #9e9cb1;
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
                border-left: 3px solid var(--primary-blue);
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
                margin-bottom: 10px;
            }

            .user-profile img {
                border-radius: 50%;
                margin-right: 10px;
            }

            .chat-support {
                display: flex;
                align-items: center;
                padding: 10px;
                cursor: pointer;
            }

            .chat-support i {
                margin-right: 10px;
            }

            .main-content {
                flex: 1;
                padding: 20px 30px;
                overflow-y: auto;
            }

            .main-header {
                display: flex;
                justify-content: space-between;
                align-items: center;
                margin-bottom: 20px;
            }

            .header-title {
                display: flex;
                align-items: center;
                gap: 20px;
            }

            .header-title h1 {
                font-size: 28px;
            }

            .tabs button {
                padding: 8px 16px;
                border: none;
                background-color: transparent;
                cursor: pointer;
                font-size: 16px;
                color: #6c757d;
            }

            .tabs button.active {
                font-weight: bold;
                color: var(--primary-blue);
                border-bottom: 2px solid var(--primary-blue);
            }

            .header-controls {
                display: flex;
                align-items: center;
                gap: 15px;
            }

            .header-controls button {
                padding: 8px 12px;
                border: 1px solid #ced4da;
                border-radius: 6px;
                background-color: white;
                cursor: pointer;
            }

            .nav-arrows i {
                padding: 8px;
                cursor: pointer;
            }

            .stats {
                display: flex;
                gap: 20px;
                margin-bottom: 20px;
            }

            .stat-item {
                display: flex;
                align-items: center;
                gap: 15px;
                padding: 15px;
                background-color: white;
                border-radius: 8px;
                border: 1px solid var(--border-color);
                flex-grow: 1;
            }

            .stat-item i {
                font-size: 24px;
                color: #6c757d;
            }

            .stat-info h3 {
                font-size: 22px;
            }

            .stat-info p {
                font-size: 12px;
                color: #6c757d;
                text-transform: uppercase;
            }

            .gantt-chart {
                background-color: white;
                border: 1px solid var(--border-color);
                border-radius: 8px;
                overflow-x: auto;
            }

            .gantt-header {
                display: grid;
                grid-template-columns: 150px repeat(14, 1fr);
                border-bottom: 1px solid var(--border-color);
                background-color: #f8f9fa;
                font-weight: bold;
            }

            .gantt-header > div {
                padding: 15px 10px;
                text-align: center;
                border-right: 1px solid var(--border-color);
            }
            .gantt-header .room-header {
                text-align: left;
            }
            .gantt-header .weekend {
                background-color: var(--weekend-bg);
            }

            .gantt-grid {
                display: grid;
                grid-template-columns: 150px repeat(14, 1fr);
            }

            .room-group-label {
                grid-column: 1 / -1;
                padding: 10px;
                font-weight: bold;
                background-color: #f8f9fa;
                border-top: 1px solid var(--border-color);
                border-bottom: 1px solid var(--border-color);
                cursor: pointer;
                display: flex;
                align-items: center;
            }

            .room-group-label .collapse-icon {
                margin-right: 10px;
                transition: transform 0.3s ease;
                font-family: 'Font Awesome 6 Free';
                font-weight: 900;
            }

            .room-group-label.collapsed .collapse-icon {
                transform: rotate(90deg);
            }

            .room-name {
                padding: 15px 10px;
                border-right: 1px solid var(--border-color);
                grid-column: 1;
            }

            .booking-bar {
                margin: 5px 0;
                padding: 8px;
                border-radius: 4px;
                color: white;
                font-size: 14px;
                overflow: hidden;
                text-overflow: ellipsis;
                white-space: nowrap;
                cursor: pointer;
            }

            .booking-bar.blue {
                background-color: #3b82f6;
            }
            .booking-bar.green {
                background-color: #10b981;
            }
            .booking-bar.purple {
                background-color: #8b5cf6;
            }

            .gantt-grid > .booking-bar {
                grid-row-end: span 1;
            }

            .room-row {
                transition: max-height 0.3s ease-out, opacity 0.3s ease-out, padding 0.3s ease-out;
                overflow: hidden;
                max-height: 100px;
                opacity: 1;
                display: grid;
                grid-template-columns: subgrid;
                grid-column: 1 / -1;
                align-items: center;
                border-bottom: 1px solid var(--border-color);
            }

            .room-row.hidden {
                max-height: 0;
                opacity: 0;
                padding-top: 0;
                padding-bottom: 0;
                border-bottom: none;
            }

            .grid-cell-filler {
                grid-column: 2 / -1;
                border-bottom: 1px solid var(--border-color);
                transition: max-height 0.3s ease-out, opacity 0.3s ease-out, padding 0.3s ease-out;
                overflow: hidden;
                max-height: 50px;
                opacity: 1;
            }

            .grid-cell-filler.hidden {
                max-height: 0;
                opacity: 0;
                padding-top: 0;
                padding-bottom: 0;
                border-bottom: none;
            }

            .room-row > div {
                border-right: 1px solid var(--border-color);
                height: 100%;
            }
            .room-row > div:last-child {
                border-right: none;
            }

            .grid-cell-filler {
                grid-column: 2 / -1;
                border-bottom: 1px solid var(--border-color);
            }
        </style>
    </head>
    <body>
        <div class="dashboard-container">
            <%@include file="menu-receptionist.jsp" %>

            <main class="main-content">

                <div class="container">
                    <nav class="navbar navbar-expand-lg navbar-light bg-white py-3 mb-4 rounded shadow-sm">
                        <div class="container-fluid">
                            <a class="navbar-brand d-flex align-items-center" href="#">
                                <h4 class="mb-0 ms-2">Bookings</h4>
                            </a>
                            <form action="checkoutBooking" method="get" class="row g-3 mb-4">
                                <div class="col-auto">
                                    <input type="date" name="selectedDate" class="form-control"
                                           value="${selectedDate != null ? selectedDate : ''}"/>
                                </div>
                                <div class="col-auto">
                                    <button type="submit" class="btn btn-primary">Lọc theo ngày</button>
                                </div>
                            </form>
                        </div>
                    </nav>
                    <c:if test="${not empty sessionScope.showSuccessModal}">
                        <div style="position: fixed; top: 1rem; right: 1rem; z-index: 1055;">
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

                        <%-- Xóa khỏi session sau khi hiển thị --%>
                        <%
                            session.removeAttribute("showSuccessModal");
                        %>
                    </c:if>

                    <div class="row mt-4">
                        <c:choose>
                            <c:when test="${not empty todayBookings}">
                                <c:forEach var="b" items="${todayBookings}">
                                    <div class="col-md-6 mb-4">
                                        <div class="card shadow-sm h-100">
                                            <div class="card-body">
                                                <div class="d-flex justify-content-between align-items-start mb-3">
                                                    <h5 class="card-title text-success">Room: ${b.roomNumber}</h5>
                                                    <p class="text-muted small">
                                                        Loại khách:
                                                        <span class="fw-bold ${b.roleId == 1 ? 'text-primary' : 'text-warning'}">
                                                            ${b.roleId == 1 ? 'Online' : 'Offline'}
                                                        </span>
                                                    </p>

                                                </div>
                                                <p class="card-text text-muted mb-1">Customer Name</p>
                                                <h6 class="mb-2">${b.userName}</h6>
                                                <p class="card-text text-muted mb-1">Date</p>
                                                <h6 class="mb-3">
                                                    <fmt:formatDate value="${b.checkIn}" pattern="dd MMM yyyy"/> -
                                                    <fmt:formatDate value="${b.checkOut}" pattern="dd MMM yyyy"/>
                                                </h6>

                                                <div class="row row-cols-2 text-center g-2">
                                                    <!-- Check-in -->
                                                    <div class="col">
                                                        <div class="bg-light p-3 rounded h-100">
                                                            <i class="fas fa-sign-in-alt text-muted mb-2"></i>
                                                            <p class="mb-1 text-muted small">Status</p>
                                                            <p class="fw-bold mb-2 text-danger">Checked In</p>
                                                            <form action="totalAmout" method="get">
                                                                <input type="hidden" name="bookingId" value="${b.bookingId}" />
                                                                <input type="hidden" name="roomId" value="${b.roomId}" /> 
                                                                <input type="hidden" name="roomNumber" value="${b.roomNumber}" />
                                                                <input type="hidden" name="checkin" value="${b.checkIn}" />
                                                                <input type="hidden" name="checkout" value="${b.checkOut}" />
                                                                <button class="btn btn-sm btn-success w-100">Check Out</button>
                                                            </form>

                                                        </div>
                                                    </div>
                                                    <div class="col">
                                                        <div class="bg-light p-3 rounded h-100">
                                                            <i class="fas fa-concierge-bell text-muted mb-2"></i>
                                                            <p class="mb-1 text-muted small">Services</p>
                                                            <p class="fw-bold mb-2 text-primary">Available</p>
                                                            <form action="orderService" method="get">
                                                                <input type="hidden" name="bookingId" value="${b.bookingId}" />
                                                                <input type="hidden" name="roomId" value="${b.roomId}" />
                                                                <input type="hidden" name="roomNumber" value="${b.roomNumber}" />
                                                                <input type="hidden" name="checkin" value="${b.checkIn}" />
                                                                <input type="hidden" name="checkout" value="${b.checkOut}" />
                                                                <button class="btn btn-sm btn-primary w-100">Book Service</button>
                                                            </form>


                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </c:when>

                            <c:otherwise>
                                <div class="col-12 text-center mt-5">
                                    <div class="alert alert-info" role="alert">
                                        Hôm nay không có khách nào đến check-in.
                                    </div>
                                </div>
                            </c:otherwise>
                        </c:choose>

                    </div>
                </div>
            </main>



        </div>
        <div class="position-fixed top-0 end-0 p-3" style="z-index: 9999">
            <div id="serviceToast" class="toast align-items-center text-white bg-success border-0" role="alert" aria-live="assertive" aria-atomic="true">
                <div class="d-flex">
                    <div class="toast-body">
                        Dịch vụ đã được cập nhật thành công!
                    </div>
                    <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>
                </div>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

        <script>
            document.addEventListener("DOMContentLoaded", function () {
                const urlParams = new URLSearchParams(window.location.search);
                if (urlParams.get("serviceUpdated") === "true") {
                    const toastEl = document.getElementById("serviceToast");
                    const toast = new bootstrap.Toast(toastEl);
                    toast.show();
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
            document.addEventListener('DOMContentLoaded', () => {
                const roomGroupLabels = document.querySelectorAll('.room-group-label');

                roomGroupLabels.forEach((label) => {
                    label.addEventListener('click', () => {
                        const groupName = label.dataset.group;
                        const roomRows = document.querySelectorAll(`.room-row[data-group-item="${groupName}"]`);
                        const gridCellFiller = label.nextElementSibling;
                        const collapseIcon = label.querySelector('.collapse-icon');

                        const isCollapsed = label.classList.toggle('collapsed');

                        roomRows.forEach(row => {
                            row.classList.toggle('hidden', isCollapsed);
                        });

                        if (gridCellFiller?.classList.contains('grid-cell-filler')) {
                            gridCellFiller.classList.toggle('hidden', isCollapsed);
                        }

                        collapseIcon.classList.toggle('fa-chevron-down', !isCollapsed);
                        collapseIcon.classList.toggle('fa-chevron-right', isCollapsed);
                    });
                });

                const currentDateTimeSpan = document.getElementById('currentDateTime');
                function updateCurrentDateTime() {
                    const now = new Date();
                    currentDateTimeSpan.textContent = now.toLocaleString('en-US', {
                        weekday: 'short', day: 'numeric', month: 'short', year: 'numeric',
                        hour: '2-digit', minute: '2-digit', second: '2-digit', hour12: true
                    });
                }
                updateCurrentDateTime();
                setInterval(updateCurrentDateTime, 1000);
            });
        </script>
    </body>
</html>