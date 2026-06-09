<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>Loại phòng</title>
        <link href="https://cdn.jsdelivr.net/npm/simple-datatables@latest/dist/style.css" rel="stylesheet" />
        <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet" />
        <link href="css/style-room.css" rel="stylesheet" type="text/css"/>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/js/all.min.js" crossorigin="anonymous"></script>
        <style>
            .block-27 ul {
                padding: 0;
                margin: 0 auto;
                display: inline-block;
                list-style: none;
            }

            .block-27 ul li {
                display: inline-block;
                margin: 0 4px;
            }

            .block-27 ul li a {
                display: block;
                padding: 8px 14px;
                border: 1px solid #ccc;
                background: #fff;
                color: #333;
                text-decoration: none;
                border-radius: 4px;
                transition: all 0.2s ease-in-out;
            }

            .block-27 ul li a:hover {
                background: #007bff;
                color: white;
                border-color: #007bff;
            }

            .block-27 ul li.active a {
                background: #007bff;
                color: #fff;
                border-color: #007bff;
                pointer-events: none;
            }

        </style>
    </head>
    <body class="sb-nav-fixed">
        <nav class="sb-topnav navbar navbar-expand navbar-dark bg-dark">
            <%@ include file="header.jsp" %>  
        </nav>

        <div id="layoutSidenav">
            <%@ include file="menu-manager.jsp" %>  

            <div id="layoutSidenav_content">
                <main>
                    <div class="container-fluid px-4">
                        <h1 class="mt-4">Danh sách loại phòng</h1>
                        <div class="card mb-4">
                            <div class="card-body">
                                Dưới đây là các loại phòng hiện có trong hệ thống khách sạn.
                            </div>
                        </div>
                        <div class="card mb-4">
                            <div class="card-header">
                                <i class="fas fa-bed me-1"></i>
                                Room Types
                            </div>
                            <div class="card-body">
                                <a href="createRoomType" class="btn btn-success mb-3">+ Thêm loại phòng</a>
                                <c:if test="${not empty error}">
                                    <div class="alert alert-danger mt-2">${error}</div>
                                </c:if>
                                <table class="table table-bordered">
                                    <thead>
                                        <tr>
                                            <th>Tên loại phòng</th>
                                            <th>Giá cơ bản (VND)</th>
                                            <th>Mô tả</th>
                                            <th>Phòng</th>
                                            <th>Tiện nghi</th> <!-- Thêm cột này -->
                                            <th>Hành động</th>
                                        </tr>
                                    </thead>

                                    <tbody>
                                        <c:forEach var="type" items="${roomTypes}">
                                            <tr>
                                                <td>${type.typeName}</td>
                                                <td>
                                                    <fmt:formatNumber value="${type.basePrice}" type="number" groupingUsed="true"/>
                                                </td>
                                                <td>${type.description}</td>
                                                <td>
                                                    <c:forEach var="room" items="${type.rooms}" varStatus="roomStatus">
                                                        ${room.roomNumber}<c:if test="${!roomStatus.last}">, </c:if>
                                                    </c:forEach>
                                                </td>
                                                <td>
                                                    <div>
                                                        <c:forEach var="amenity" items="${type.amenities}" varStatus="loop">
                                                            <div>${amenity}</div>
                                                            <c:if test="${!loop.last}"> </c:if>
                                                        </c:forEach>
                                                    </div>

                                                    <div class="mt-2 d-flex gap-1 flex-wrap">
                                                        <a href="createRoomAmenity?roomTypeId=${type.roomTypeId}"
                                                           class="btn btn-outline-secondary btn-sm"
                                                           style="font-size: 0.7rem; padding: 1px 5px;">
                                                            ➕
                                                        </a>
                                                        <a href="editAmenityList?roomTypeId=${type.roomTypeId}"
                                                           class="btn btn-outline-secondary btn-sm"
                                                           style="font-size: 0.7rem; padding: 1px 5px;">
                                                            ✏️
                                                        </a>

                                                    </div>
                                                </td>

                                                <td>
                                        <center>
                                            <c:set var="roomListStr" value="" />
                                            <c:forEach var="room" items="${type.rooms}" varStatus="status">
                                                <c:set var="roomListStr" value="${roomListStr}${room.roomNumber}${!status.last ? ', ' : ''}" />
                                            </c:forEach>

                                            <a href="editRoomType?id=${type.roomTypeId}"
                                               class="btn btn-warning btn-sm"
                                               onclick="return confirm('Bạn có muốn sửa loại phòng của các phòng: ${roomListStr}?');">
                                                Sửa
                                            </a>

                                            <a href="deleteRoomType?id=${type.roomTypeId}"
                                               class="btn btn-danger btn-sm"
                                               onclick="return confirm('Bạn có chắc chắn muốn xóa?');">
                                                Xóa
                                            </a>
                                        </center>
                                        </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>

                                </table>
                            </div>
                        </div>
                    </div>

                </main>
            </div>
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
            <script src="${pageContext.request.contextPath}/js/scripts.js"></script>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js" crossorigin="anonymous"></script>
            <script src="assets/demo/chart-area-demo.js"></script>
            <script src="assets/demo/chart-bar-demo.js"></script>

    </body>
</html>
