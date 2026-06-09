<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Room" %>
<%@ page import="dal.RoomDAO" %>
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
        <title>Phòng</title>
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

                    <% String message = (String) session.getAttribute("message");
                                                   String error = (String) session.getAttribute("error");
                                                   session.removeAttribute("message");
                                                   session.removeAttribute("error");
                    %>

                    <% if (message != null) { %>
                    <div class="alert alert-success"><%= message %></div>
                    <% } else if (error != null) { %>
                    <div class="alert alert-danger"><%= error %></div>
                    <% } %>
                    <div class="container-fluid px-4">
                        <h1 class="mt-4">Danh sách phòng</h1>
                        <a href="createRoom" class="btn btn-success mb-3">Thêm phòng mới</a> 
                        <form action="listRoom" method="get" class="row mb-3">
                            <div class="col-md-2">
                                <input type="text" name="roomNumber" class="form-control" placeholder="Số phòng" value="${param.roomNumber}">
                            </div>
                            <div class="col-md-2">
                          
                                    <select name="roomType" id="room-type" class="form-select">
                                        <option value="">-- Tất cả --</option>
                                        <c:forEach var="type" items="${roomTypes}">
                                            <option value="${type.typeName}" ${param.roomType == type.typeName ? 'selected' : ''}>
                                                ${type.typeName}
                                            </option>
                                        </c:forEach>
                                    </select>
                            

                            </div>
                            <div class="col-md-2">
                                <input type="number" name="bed" class="form-control" placeholder="Giường" value="${param.bed}">
                            </div>
                            <div class="col-md-2">
                                <select name="status" class="form-select">
                                    <option value="">-- Trạng thái --</option>
                                    <option value="available" ${param.status == 'available' ? 'selected' : ''}>Available</option>
                                    <option value="maintenance" ${param.status == 'maintenance' ? 'selected' : ''}>Maintenance</option>
                                </select>
                            </div>
                            <div class="col-md-2">
                                <select name="booked" class="form-select">
                                    <option value="">-- Booked --</option>
                                    <option value="true" ${param.booked == 'true' ? 'selected' : ''}>Yes</option>
                                    <option value="false" ${param.booked == 'false' ? 'selected' : ''}>No</option>
                                </select>
                            </div>
                            <div class="col-md-2">
                                <button type="submit" class="btn btn-primary w-100">Tìm</button>
                            </div>
                        </form>

                        <table id="roomTable" class="table table-striped table-bordered mt-3">
                            <thead class="table-dark">
                                <tr>
                                    <th>Room ID</th>
                                    <th>Room Number</th>
                                    <th>Room Type</th>
                                    <th>Capacity</th>
                                    <th>Size</th>
                                    <th>Bed</th>
                                    <th>Status</th>
                                    <th>Booked</th> <!-- Thêm cột Booked -->
                                    <th>Created At</th>
                                    <th>Updated At</th>
                                    <th style="width: 175px;">Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:choose>
                                    <c:when test="${not empty roomList}">
                                        <c:forEach var="r" items="${roomList}">
                                            <tr>
                                                <td>${r.roomId}</td>
                                                <td>${r.roomNumber}</td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${not empty r.roomType}">
                                                            ${r.roomType.typeName}
                                                        </c:when>
                                                        <c:otherwise>
                                                            N/A
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td>${r.capacity}</td>
                                                <td>${r.size}</td>
                                                <td>${r.bed}</td>
                                                <td>${r.status}</td>
                                                <td>${r.booked ? 'Yes' : 'No'}</td> <!-- Hiển thị thông tin Booked -->
                                                <td><fmt:formatDate value="${r.createdAt}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                                <td><fmt:formatDate value="${r.updatedAt}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                                <td>
                                                    <a href="editRoom?id=${r.roomId}" class="btn btn-primary btn-sm btn-edit-room">Sửa</a>
                                                    <a href="deleteRoom?id=${r.roomId}" class="btn btn-danger btn-sm" onclick="return confirm('Bạn có chắc chắn muốn xóa phòng này?');">Xóa</a>
                                                    <a href="viewRoom?id=${r.roomId}" class="btn btn-info btn-sm">Xem</a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <tr>
                                            <td colspan="11" class="text-center">Không có phòng nào</td>
                                        </tr>
                                    </c:otherwise>
                                </c:choose>
                            </tbody>

                        </table>
                        <div class="row mt-4">
                            <div class="col text-center">
                                <div class="block-27">
                                    <ul>
                                        <c:if test="${pageIndex > 1}">
                                            <li>
                                                <a href="listRoom?page=${pageIndex - 1}&roomNumber=${param.roomNumber}&roomType=${param.roomType}&bed=${param.bed}&status=${param.status}&booked=${param.booked}">&lt;</a>
                                            </li>
                                        </c:if>

                                        <c:forEach begin="1" end="${totalPages}" var="i">
                                            <li class="${i == pageIndex ? 'active' : ''}">
                                                <a href="listRoom?page=${i}&roomNumber=${param.roomNumber}&roomType=${param.roomType}&bed=${param.bed}&status=${param.status}&booked=${param.booked}">${i}</a>
                                            </li>
                                        </c:forEach>

                                        <c:if test="${pageIndex < totalPages}">
                                            <li>
                                                <a href="listRoom?page=${pageIndex + 1}&roomNumber=${param.roomNumber}&roomType=${param.roomType}&bed=${param.bed}&status=${param.status}&booked=${param.booked}">&gt;</a>
                                            </li>
                                        </c:if>
                                    </ul>
                                </div>
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
