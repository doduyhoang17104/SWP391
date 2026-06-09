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
        <title>Blog</title>
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
                    <h2 class="text-primary mb-4">Quản lý bình luận</h2>

                    <!-- Tìm kiếm -->
                    <form method="get" action="commentmanager" class="row g-2 mb-4">
                        <div class="col-md-2">
                            <input type="text" name="id" value="${param.id}" class="form-control" placeholder="Tìm ID...">
                        </div>
                        <div class="col-md-3">
                            <input type="text" name="author" value="${param.author}" class="form-control" placeholder="Người dùng...">
                        </div>
                        <div class="col-md-3">
                            <input type="text" name="content" value="${param.content}" class="form-control" placeholder="Nội dung...">
                        </div>
                        <div class="col-md-3">
                            <input type="date" name="date" value="${param.date}" class="form-control">
                        </div>
                        <div class="col-md-1">
                            <button type="submit" class="btn btn-primary w-100">Tìm</button>
                        </div>
                    </form>
                    <c:if test="${not empty param.id or not empty param.author or not empty param.content or not empty param.date}">
                        <div class="mb-3">
                            <a href="commentmanager" class="btn btn-secondary">⬅ Trở về tất cả bình luận</a>
                        </div>
                    </c:if>



                    <!-- Danh sách bình luận -->
                    <table class="table table-bordered table-hover shadow-sm">
                        <thead class="table-dark text-center">
                            <tr>
                                <th>ID</th>
                                <th>Người dùng</th>
                                <th>Nội dung</th>
                                <th>Ngày tạo</th>
                                <th>Hành động</th>

                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${not empty comments}">
                                    <c:forEach var="c" items="${comments}">
                                        <tr>
                                            <td class="text-center">${c.commentId}</td>
                                            <td>${c.authorName}</td>
                                            <td>
                                                <div class="form-control" style="height:auto; white-space: pre-wrap;">${c.content}</div>
                                            </td>

                                            <td class="text-center">${c.createdAt}</td>
                                            <td class="text-center">
                                                <form method="post" action="commentmanager">
                                                    <input type="hidden" name="action" value="update"/>
                                                    <input type="hidden" name="commentId" value="${c.commentId}"/>
                                                    <div class="d-flex justify-content-center gap-2">
                                                        <a href="blogdetail?id=${c.postId}" class="btn btn-info btn-sm">Xem</a>
                                                        <a href="commentmanager?action=delete&id=${c.commentId}" 
                                                           class="btn btn-danger btn-sm"
                                                           onclick="return confirm('Bạn có chắc muốn xoá bình luận này?');">Xoá</a>
                                                    </div>
                                                </form>

                                            </td>
                                        </tr>
                                    </c:forEach>

                                </c:when>
                                <c:otherwise>
                                    <tr>
                                        <td colspan="6" class="text-center text-muted">Không có bình luận nào.</td>
                                    </tr>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>

                    <!-- Phân trang -->
                    <c:if test="${totalPages > 1}">
                        <nav aria-label="Page navigation">
                            <ul class="pagination justify-content-center">
                                <c:forEach var="i" begin="1" end="${totalPages}">
                                    <li class="page-item ${i == page ? 'active' : ''}">
                                        <a class="page-link" href="commentmanager?page=${i}&keyword=${param.keyword}">${i}</a>
                                    </li>
                                </c:forEach>
                            </ul>
                        </nav>
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