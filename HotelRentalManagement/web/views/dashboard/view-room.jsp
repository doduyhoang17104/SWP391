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
        <link href="css/style-room.css" rel="stylesheet" type="text/css"/>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/js/all.min.js" crossorigin="anonymous"></script>
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
                        <h1 class="mt-4">Chi tiết phòng</h1>
                        <div class="card mb-4">
                            <div class="card-body">
                                <p><strong>Mã phòng:</strong> ${room.roomId}</p>
                                <p><strong>Số phòng:</strong> ${room.roomNumber}</p>
                                <p><strong>Loại phòng:</strong> ${room.roomType.typeName}</p>
                                <p><strong>Sức chứa:</strong> ${room.capacity}</p>
                                <p><strong>Kích thước phòng:</strong> ${room.size}</p>
                                <p><strong>Loại giường:</strong> ${room.bed}</p>
                                <p><strong>Đã đặt:</strong>
                                    <c:choose>
                                        <c:when test="${room.booked}">Đã đặt</c:when>
                                        <c:otherwise>Chưa đặt</c:otherwise>
                                    </c:choose>
                                </p>
                                <p><strong>Trạng thái:</strong> ${room.status}</p>
                                <p><strong>Ngày tạo:</strong> <fmt:formatDate value="${room.createdAt}" pattern="yyyy-MM-dd HH:mm:ss"/></p>
                                <p><strong>Ngày cập nhật:</strong> <fmt:formatDate value="${room.updatedAt}" pattern="yyyy-MM-dd HH:mm:ss"/></p>

                                <!-- Danh sách ảnh (nếu có) -->
                                <c:if test="${not empty room.imageUrls}">
                                    <p><strong>Hình ảnh:</strong></p>
                                    <div class="d-flex flex-wrap gap-3">
                                        <c:forEach var="img" items="${room.imageUrls}">
                                            <img src="${img}" alt="Ảnh phòng" style="width:150px; height:auto; border-radius:8px; cursor:pointer;" onclick="showImageModal('${img}')"/>
                                        </c:forEach>
                                    </div>
                                </c:if>

                                <!-- Danh sách tiện nghi -->
                                <c:if test="${not empty room.amenities}">
                                    <p class="mt-3"><strong>Tiện nghi:</strong></p>
                                    <ul>
                                        <c:forEach var="a" items="${room.amenities}">
                                            <li>${a.amenityName}</li>
                                            </c:forEach>
                                    </ul>
                                </c:if>

                                <a href="listRoom" class="btn btn-secondary mt-3">← Quay lại danh sách</a>
                            </div>
                        </div>
                    </div>
                </main>

                <script>
                    function showImageModal(imgUrl) {
                        const modal = document.getElementById('imageModal');
                        const largeImg = document.getElementById('largeImage');
                        largeImg.src = imgUrl;
                        modal.style.display = 'flex';
                    }

                    function closeModal() {
                        document.getElementById('imageModal').style.display = 'none';
                    }
                </script>




            </div>

            <!-- Modal hiển thị ảnh lớn -->
            <div id="imageModal" style="display:none; position:fixed; top:0; left:0; width:100%; height:100%;
                 background-color:rgba(0,0,0,0.8); justify-content:center; align-items:center; z-index:1000;">
                <span style="position:absolute; top:20px; right:30px; color:#fff; font-size:30px; cursor:pointer;" onclick="closeModal()">×</span>
                <img id="largeImage" src="" style="max-width:90%; max-height:90%; border-radius:8px; box-shadow:0 0 10px #fff;" />
            </div>
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
            <script src="js/scripts.js"></script>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js" crossorigin="anonymous"></script>
            <script src="assets/demo/chart-area-demo.js"></script>
            <script src="assets/demo/chart-bar-demo.js"></script>
            <script src="https://cdn.jsdelivr.net/npm/simple-datatables@latest" crossorigin="anonymous"></script>
            <script src="js/datatables-simple-demo.js"></script>
    </body>
</html>
