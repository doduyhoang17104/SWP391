<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<style>
    .sidebar {
        width: 250px;
        height: 100vh;
        position: fixed;
        top: 0;
        left: 0;
        background-color: #1f1f1f;
        color: #fff;
        display: flex;
        flex-direction: column;
        box-shadow: 2px 0 5px rgba(0, 0, 0, 0.15);
        z-index: 1000;
        padding-top: 20px;
    }

    .company-logo {
        text-align: center;
        font-size: 1.7rem;
        font-weight: bold;
        color: #fff;
        margin-bottom: 25px;
    }

    .company-logo .icon {
        color: #8A2BE2;
        margin-right: 10px;
    }

    .section-title {
        padding: 0 20px;
        font-size: 0.8rem;
        color: #adb5bd;
        text-transform: uppercase;
        letter-spacing: 1px;
        margin-bottom: 15px;
    }

    .nav {
        padding-left: 0;
        list-style: none;
        margin-bottom: 0;
    }

    .nav-item {
        width: 100%;
    }

    .nav-link {
        display: flex;
        align-items: center;
        padding: 12px 20px;
        color: #cfd8dc;
        text-decoration: none;
        transition: all 0.3s ease;
        font-size: 0.95rem;
    }

    .nav-link i {
        margin-right: 12px;
        font-size: 1.1rem;
        color: #adb5bd;
    }

    .nav-link:hover {
        background-color: #333;
        color: #fff;
    }

    .nav-link.active {
        background-color: #8A2BE2;
        color: #fff;
    }

    .nav-link.active i {
        color: #fff;
    }

    .sidebar-footer {
        margin-top: auto;
        padding: 15px 20px;
        font-size: 0.75rem;
        color: #6c757d;
        border-top: 1px solid #2c2c2c;
    }
</style>

<%
    String currentPath = request.getRequestURI();
%>

<div class="sidebar">
    <div class="company-logo">
        <i class="fas fa-hotel icon"></i> HOTEL
    </div>
    <div class="section-title">Quản lí khách sạn</div>
    <ul class="nav flex-column">
        <li class="nav-item">
            <a class="nav-link <%= currentPath.contains("/user-list.jsp") ? "active" : "" %>" href="${pageContext.request.contextPath}/listuser">
                <i class="fas fa-user-gear"></i> Quản lý người dùng
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link <%= currentPath.contains("/add-user.jsp") ? "active" : "" %>" href="${pageContext.request.contextPath}/adduser">
                <i class="fas fa-user-plus"></i> Thêm người dùng
            </a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/logout">
                <i class="fas fa-sign-out-alt"></i> Đăng xuất
            </a>
        </li>
    </ul>
    <div class="sidebar-footer">
        Admin Sales Management System 2025 © Group 10
    </div>
</div>
