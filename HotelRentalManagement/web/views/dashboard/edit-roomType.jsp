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
        <title>Chỉnh sửa loại phòng</title>
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
                        <h1 class="mt-4">Chỉnh sửa loại phòng</h1>

                        <div class="card mb-4">
                            <div class="card-body">
                                Cập nhật thông tin loại phòng bên dưới.
                            </div>
                        </div>

                        <div class="card mb-4">
                            <div class="card-header">
                                <i class="fas fa-edit me-1"></i>
                                Sửa loại phòng
                            </div>
                            <div class="card-body">
                                <form action="editRoomType" method="post">                                 
                                    <input type="hidden" name="id" value="${roomType.roomTypeId}" />

                                    <div class="mb-3">
                                        <label for="typeName" class="form-label">Tên loại phòng</label>
                                        <input type="text" name="typeName" class="form-control" required 
                                               value="${roomType.typeName}" autocomplete="off" />
                                    </div>

                                    <div class="mb-3">
                                        <label for="basePrice" class="form-label">Giá cơ bản (VND)</label>
                                        <input type="text" name="basePrice" class="form-control" required
                                               value="<fmt:formatNumber value='${roomType.basePrice}' type='number' groupingUsed='true'/>"
                                               placeholder="Ví dụ: 1.200.000" autocomplete="off" />
                                    </div>

                                    <div class="mb-3">
                                        <label for="description" class="form-label">Mô tả</label>
                                        <textarea name="description" rows="4" class="form-control"
                                                  required>${roomType.description}</textarea>
                                    </div>

                                    <button type="submit" class="btn btn-primary">Cập nhật</button>
                                    <a href="listRoomType" class="btn btn-secondary">Hủy</a>

                                    <c:if test="${not empty error}">
                                        <div class="alert alert-danger mt-3">${error}</div>
                                    </c:if>
                                </form>
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
