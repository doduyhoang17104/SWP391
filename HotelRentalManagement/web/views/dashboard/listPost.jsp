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
        <title>Danh sách bài viết</title>
        <link href="https://cdn.jsdelivr.net/npm/simple-datatables@latest/dist/style.css" rel="stylesheet" />
        <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet" />
        <link href="css/style-room.css" rel="stylesheet" type="text/css"/>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/js/all.min.js" crossorigin="anonymous"></script>
        <style>
            .content-cell {
                max-width: 300px;
                word-wrap: break-word;
                white-space: normal;
            }
            .thumbnail {
                width: 100px;
                height: auto;
                border-radius: 8px;
            }
        </style>
        <style>
            table.dataTable thead {
                background-color: #343a40 !important;
            }

            table.dataTable thead th {
                color: white !important;
                font-weight: bold;
                text-align: center;
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


                <main class="container-fluid px-4 mt-4">
                    <h2 class="text-primary mb-4">Danh sách bài viết</h2>
                    <!--                    search-->
                    <div class="bg-white shadow rounded p-4 mb-4">
                        <form action="${pageContext.request.contextPath}/searchpost" method="get" class="row gy-3 gx-4 align-items-end">

                            <!-- ID bài viết -->
                            <div class="col-md-3">
                                <label class="form-label"><i class="bi bi-hash"></i> ID bài viết</label>
                                <input type="text" name="postId" class="form-control" value="${param.postId}">
                            </div>

                            <!-- Tiêu đề -->
                            <div class="col-md-3">
                                <label class="form-label"><i class="bi bi-type"></i> Tiêu đề</label>
                                <input type="text" name="title" class="form-control" value="${param.title}">
                            </div>

                            <!-- Tác giả -->
                            <div class="col-md-3">
                                <label class="form-label"><i class="bi bi-person-circle"></i> Tác giả</label>
                                <input type="text" name="author" class="form-control" value="${param.author}">
                            </div>

                            <!-- Nội dung -->
                            <div class="col-md-3">
                                <label class="form-label"><i class="bi bi-card-text"></i> Nội dung</label>
                                <input type="text" name="content" class="form-control" value="${param.content}">
                            </div>

                            <!-- Ngày tạo từ -->
                            <div class="col-md-3">
                                <label class="form-label"><i class="bi bi-calendar-date"></i> Ngày tạo từ</label>
                                <div class="input-group">
                                    <span class="input-group-text"><i class="bi bi-calendar3"></i></span>
                                    <input type="date" name="createdFrom" class="form-control" value="${param.createdFrom}">
                                </div>
                            </div>

                            <!-- Ngày tạo đến -->
                            <div class="col-md-3">
                                <label class="form-label">Đến</label>
                                <div class="input-group">
                                    <span class="input-group-text"><i class="bi bi-calendar3"></i></span>
                                    <input type="date" name="createdTo" class="form-control" value="${param.createdTo}">
                                </div>
                            </div>

                            <!-- Ngày sửa từ -->
                            <div class="col-md-3">
                                <label class="form-label"><i class="bi bi-pencil-square"></i> Ngày sửa từ</label>
                                <div class="input-group">
                                    <span class="input-group-text"><i class="bi bi-calendar3"></i></span>
                                    <input type="date" name="updatedFrom" class="form-control" value="${param.updatedFrom}">
                                </div>
                            </div>

                            <!-- Ngày sửa đến -->
                            <div class="col-md-3">
                                <label class="form-label">Đến</label>
                                <div class="input-group">
                                    <span class="input-group-text"><i class="bi bi-calendar3"></i></span>
                                    <input type="date" name="updatedTo" class="form-control" value="${param.updatedTo}">
                                </div>
                            </div>

                            <!-- Nút lọc -->
                            <div class="col-12 d-flex justify-content-end gap-2 mt-3">
                                <button type="submit" class="btn btn-primary px-4">
                                    <i class="bi bi-search me-1"></i> Tìm bài viết
                                </button>

                                <a href="${pageContext.request.contextPath}/listpost" class="btn btn-outline-dark px-4">
                                    <i class="bi bi-card-list me-1"></i> Tất cả bài viết
                                </a>
                            </div>
                        </form>
                    </div>








                    <div class="d-flex justify-content-end mb-3">
                        <a href="addpost" class="btn btn-success">+ Thêm bài viết mới</a>
                    </div>

                    <c:if test="${empty posts}">
                        <div class="alert alert-warning">Không có bài viết nào.</div>
                    </c:if>
                    <script src="https://cdn.jsdelivr.net/npm/simple-datatables@latest" crossorigin="anonymous"></script>
                    <script>
                        window.addEventListener('DOMContentLoaded', () => {
                            const postTable = document.querySelector("#postTable");
                            if (postTable) {
                                new simpleDatatables.DataTable(postTable, {
                                    searchable: false, // ❌ Tắt ô tìm kiếm
                                    perPageSelect: false, // ❌ Tắt dropdown số dòng
                                    perPage: 10, // ✅ Số dòng mặc định mỗi trang
                                    labels: {
                                        noRows: "Không có dữ liệu",
                                        info: "Hiển thị {start} đến {end} của {rows} bài viết"
                                    }
                                });
                            }
                        });
                    </script>



                    <c:if test="${not empty posts}">
                        <div class="table-responsive">
                            <table class="table table-bordered table-hover align-middle bg-white" id="postTable">
                                <thead class="table-dark text-center">

                                    <tr>
                                        <th>ID</th>
                                        <th>Tiêu đề</th>
                                        <th>Tác giả</th>
                                        <th>Ngày tạo</th>
                                        <th>Ngày sửa</th>
                                        <th>Ảnh</th>
                                        <th>Nội dung</th>
                                        <th>Hành động</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${posts}" var="c">
                                        <tr>
                                            <td class="text-center">${c.postId}</td>
                                            <td>${c.title}</td>
                                            <td class="text-center">${c.authorName}</td>
                                            <td class="text-center">${c.createdAt}</td>
                                            <td class="text-center">${c.updatedAt}</td>
                                            <td class="text-center">
                                                <img src="${c.image}" alt="Ảnh" class="thumbnail" />
                                            </td>
                                            <td class="content-cell">${c.content}</td>
                                            <td class="text-center">
                                                <a href="editpost?id=${c.postId}" class="btn btn-sm btn-warning mb-1">Sửa</a><br>
                                                <a href="viewPost?postId=${c.postId}" class="btn btn-sm btn-primary mb-1">Xem</a><br>
                                                <a href="deletepost?id=${c.postId}" 
                                                   class="btn btn-sm btn-danger" 
                                                   onclick="return confirm('Bạn có chắc chắn muốn xóa bài viết này?');">
                                                    Xoá
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </c:if>
                </main>


            </div>
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
            <script src="${pageContext.request.contextPath}/js/scripts.js"></script>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js" crossorigin="anonymous"></script>
            <script src="assets/demo/chart-area-demo.js"></script>
            <script src="assets/demo/chart-bar-demo.js"></script>
            <script src="https://cdn.jsdelivr.net/npm/simple-datatables@latest" crossorigin="anonymous"></script>
            <script src="js/datatables-simple-demo.js"></script>


    </body>
</html>