<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="java.time.LocalDate, java.time.LocalDateTime, java.time.temporal.ChronoUnit" %>

<style>
    /* --- CÀI ĐẶT CHUNG & BIẾN --- */
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

    /* --- SIDEBAR --- */
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

    /* --- MAIN CONTENT --- */
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

    /* --- STATS SECTION --- */
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

    /* --- GANTT CHART SECTION --- */
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

    /* Different booking colors */
    .booking-bar.blue {
        background-color: #3b82f6;
    }
    .booking-bar.green {
        background-color: #10b981;
    }
    .booking-bar.purple {
        background-color: #8b5cf6;
    }

    /* This is a helper to correctly align bookings */
    .gantt-grid > .booking-bar {
        grid-row-end: span 1;
    }

    .room-row {
        transition: max-height 0.3s ease-out, opacity 0.3s ease-out, padding 0.3s ease-out;
        overflow: hidden;
        max-height: 100px; /* Adjust this to be large enough to contain all room content when expanded */
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

    /* Add transition for grid-cell-filler */
    .grid-cell-filler {
        grid-column: 2 / -1;
        border-bottom: 1px solid var(--border-color);
        transition: max-height 0.3s ease-out, opacity 0.3s ease-out, padding 0.3s ease-out;
        overflow: hidden;
        max-height: 50px; /* A reasonable height for the filler when visible */
        opacity: 1;
    }

    .grid-cell-filler.hidden {
        max-height: 0;
        opacity: 0;
        padding-top: 0;
        padding-bottom: 0;
        border-bottom: none;
    }

    /* Ensure all grid cells within room-row maintain vertical borders */
    .room-row > div {
        border-right: 1px solid var(--border-color);
        height: 100%;
    }
    .room-row > div:last-child {
        border-right: none;
    }

    /* Cell filler needs bottom border if it directly follows a group label */
    .grid-cell-filler {
        grid-column: 2 / -1;
        border-bottom: 1px solid var(--border-color);
    }
    ul {
        list-style: none;
        padding-left: 0;
        margin: 0;
    }

    ul li {
        display: block;
        padding: 10px 15px;
        margin-bottom: 5px;
        border-radius: 5px;
        transition: background 0.3s;
    }

    ul li a {
        color: #333;
        text-decoration: none;
        display: block;
    }

    ul li.active {
        background-color: #007bff;
    }

    ul li.active a {
        color: white;
        font-weight: bold;
    }

    ul li:hover {
        background-color: #f0f0f0;
    }
    .sidebar-title a {
        color: #f8f9fa; /* màu trắng nhẹ */
        text-decoration: none; /* bỏ gạch chân */
        font-weight: bold;
    }
</style>


<aside class="sidebar">
    <div class="sidebar-header">
        <h2 class="sidebar-title">
            <a href="home">My Hotel</a>
        </h2>
        <div class="search-bar">
            <i class="fa-solid fa-magnifying-glass"></i>
            <input type="text" placeholder="Booking, Guest, et.." />
        </div>
    </div>


    <%
    String uri = request.getRequestURI().toLowerCase();
    %>
    <%

System.out.println("URI: " + uri); // Xem trong console/log Tomcat
    %>
    <nav class="sidebar-nav">
        <ul>
            <li class="<%= uri.equals("/hotelrentalmanagement/views/receptionist/listbooking.jsp") ? "active" : "" %>">
                <a href="listBooking"><i class="fa-solid fa-bell-concierge"></i> Quản lí</a>
            </li>
            <li class="<%= uri.equals("/hotelrentalmanagement/views/receptionist/roombooking.jsp") ? "active" : "" %>">
                <a href="searchRoom"><i class="fa-solid fa-hotel"></i>Đặt phòng</a>
            </li>
            <li class="<%= uri.contains("checkin") ? "active" : "" %>">
                <a href="checkinBooking"><i class="fa-solid fa-users"></i> Checkin</a>
            </li>
            <li class="<%= uri.equals("/hotelrentalmanagement/views/receptionist/checkout-booking.jsp") ? "active" : "" %>">
                <a href="checkoutBooking"><i class="fa-solid fa-door-open"></i>Phòng đã nhận</a>
            </li>
            <li>
                <a class="nav-link " href="logout">
                    <div class="sb-nav-link-icon"><i class="fas fa-sign-out-alt"></i></div>
                    Đăng xuất
                </a>
        </ul>
    </nav>

    <div class="sidebar-footer">
        <div class="user-profile">
            <c:if test="${not empty sessionScope.user}">
                <span>${sessionScope.user.name}</span>
            </c:if>

        </div>

        <div class="chat-support">
            <i class="fa-solid fa-headset"></i>
            <!--            <span>Chat with Support</span>-->
        </div>
    </div>
</aside>