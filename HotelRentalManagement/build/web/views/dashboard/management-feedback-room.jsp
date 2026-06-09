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
        <title>Quản lý đánh giá phòng</title>

        <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/js/all.min.js" crossorigin="anonymous"></script>

        <style>
            html, body {
                height: 100vh;
                margin: 0;
                padding: 0;
            }

            #layoutSidenav {
                display: flex;
                height: 100%;
            }

            #layoutSidenav_content {
                flex: 1;
                display: flex;
                flex-direction: column;
                height: 100%;
            }

            main {
                flex-grow: 1;
                padding: 20px;
                background-color: #f8f9fa;
                overflow: auto;
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
                    <div class="w-100 p-4">
                        <h2 class="mb-4 text-primary"><i class="fa-solid fa-comments"></i> Quản lý đánh giá phòng</h2>
                        <div class="card mb-4 shadow-sm border-0">
                            <div class="card-body bg-light">
                                <form method="POST" action="searchfeedbackroom" class="row g-3 align-items-end">
                                    <div class="col-md-3">
                                        <label class="form-label fw-semibold">Phòng</label>
                                        <input type="text" name="room" class="form-control" value="${param.room}" placeholder="Nhập số phòng">
                                    </div>
                                    <div class="col-md-3">
                                        <label class="form-label fw-semibold">Điểm đánh giá</label>
                                        <select name="rating" class="form-select">
                                            <option value="">-- Chọn điểm --</option>
                                            <option value="1" ${param.rating == '1' ? 'selected' : ''}>1</option>
                                            <option value="2" ${param.rating == '2' ? 'selected' : ''}>2</option>
                                            <option value="3" ${param.rating == '3' ? 'selected' : ''}>3</option>
                                            <option value="4" ${param.rating == '4' ? 'selected' : ''}>4</option>
                                            <option value="5" ${param.rating == '5' ? 'selected' : ''}>5</option>
                                        </select>
                                    </div>

                                    <div class="col-md-3">
                                        <label class="form-label fw-semibold">Người đánh giá</label>
                                        <input type="text" name="user" class="form-control" value="${param.user}" placeholder="Nhập tên người dùng">
                                    </div>
                                    <div class="col-md-3">
                                        <label class="form-label fw-semibold">Ngày đánh giá</label>
                                        <input type="date" name="date" class="form-control" value="${param.date}">
                                    </div>
                                    <div class="col-md-12 d-flex gap-2 mt-3">
                                        <button type="submit" class="btn btn-success w-100">
                                            <i class="fa-solid fa-filter"></i> Lọc
                                        </button>
                                        <a href="feedbackroommanagement" class="btn btn-outline-secondary w-100">
                                            <i class="fa-solid fa-rotate-left"></i> Tất cả
                                        </a>
                                    </div>
                                </form>
                            </div>
                        </div>

                        <div class="card mb-4 shadow-sm border-0">
                            <div class="card-body bg-light">
                                <form method="POST" action="sortfeedbackroom" class="row g-3 align-items-end">
                                    <div class="col-md-4">
                                        <label class="form-label fw-semibold">Sắp xếp điểm đánh giá</label>
                                        <select name="sort" class="form-select">
                                            <option value="">-- Không sắp xếp --</option>
                                            <option value="asc" ${param.sort == 'asc' ? 'selected' : ''}>Tăng dần</option>
                                            <option value="desc" ${param.sort == 'desc' ? 'selected' : ''}>Giảm dần</option>
                                        </select>
                                    </div>
                                    <div class="col-md-2 mt-2">
                                        <button type="submit" class="btn btn-primary mt-4 w-100">
                                            <i class="fa-solid fa-sort"></i> Sắp xếp
                                        </button>
                                    </div>
                                </form>
                            </div>
                        </div>



                        <!-- Bảng feedback -->
                        <table class="table table-bordered table-striped shadow-sm">
                            <thead class="table-dark">
                                <tr>
                                    <th>Phòng</th>
                                    <th>Người đánh giá</th>
                                    <th>Nội dung</th>
                                    <th>Điểm</th>
                                    <th>Ngày đánh giá</th>
                                    <th>Ngày sửa</th>
                                    <th class="text-center">Hành động</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:choose>
                                    <c:when test="${not empty listFR}">
                                        <c:forEach items="${listFR}" var="c">
                                            <tr>
                                                <td>${c.roomNumber}</td>

                                                <td>${c.authorName}</td>
                                                <td>${c.content}</td>
                                                <td><span class="rating-stars">${c.rating} <i class="fa-solid fa-star"></i></span></td>
                                                <td>${c.createAt}</td>
                                                <td>${c.updateAt}</td>
                                                <td class="text-center">
                                                    <a href="feedbackroomdetail?bookingId=${c.bookingId}">
                                                        <button class="btn btn-info btn-sm"><i class="fa-solid fa-eye"></i> Chi tiết</button>
                                                    </a>
                                                    <button class="btn btn-danger btn-sm" onclick="confirmDelete(${c.bookingId})">
                                                        <i class="fa-solid fa-trash"></i> Xóa
                                                    </button>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <tr>
                                            <td colspan="6" class="text-center text-muted fst-italic py-3">
                                                <i class="fa-solid fa-circle-exclamation text-warning me-1"></i> Không có phản hồi phù hợp.
                                            </td>
                                        </tr>
                                    </c:otherwise>
                                </c:choose>



                            </tbody>
                        </table>
                        <!-- Phân trang -->
                        <c:if test="${totalPages > 1}">
                            <c:set var="startPage" value="${currentPage - 2 < 1 ? 1 : currentPage - 2}" />
                            <c:set var="endPage" value="${currentPage + 2 > totalPages ? totalPages : currentPage + 2}" />


                            <nav aria-label="Pagination" class="mt-4">
                                <ul class="pagination justify-content-center">
                                    <!-- Nút Trước -->
                                    <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                                        <a class="page-link" href="feedbackroommanagement?page=${currentPage - 1}">
                                            <i class="bi bi-chevron-left"></i> Trước
                                        </a>
                                    </li>

                                    <!-- Trang đầu + dấu ... nếu cần -->
                                    <c:if test="${startPage > 1}">
                                        <li class="page-item">
                                            <a class="page-link" href="feedbackroommanagement?page=1">1</a>
                                        </li>
                                        <c:if test="${startPage > 2}">
                                            <li class="page-item disabled"><a class="page-link">...</a></li>
                                            </c:if>
                                        </c:if>

                                    <!-- Các trang lân cận -->
                                    <c:forEach begin="${startPage}" end="${endPage}" var="i">
                                        <li class="page-item ${i == currentPage ? 'active' : ''}">
                                            <a class="page-link" href="feedbackroommanagement?page=${i}">${i}</a>
                                        </li>
                                    </c:forEach>

                                    <!-- Dấu ... + trang cuối nếu cần -->
                                    <c:if test="${endPage < totalPages}">
                                        <c:if test="${endPage < totalPages - 1}">
                                            <li class="page-item disabled"><a class="page-link">...</a></li>
                                            </c:if>
                                        <li class="page-item">
                                            <a class="page-link" href="feedbackroommanagement?page=${totalPages}">${totalPages}</a>
                                        </li>
                                    </c:if>

                                    <!-- Nút Sau -->
                                    <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                                        <a class="page-link" href="feedbackroommanagement?page=${currentPage + 1}">
                                            Sau <i class="bi bi-chevron-right"></i>
                                        </a>
                                    </li>
                                </ul>
                            </nav>
                        </c:if>
                    </div>

                </main>
            </div>
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
            <script src="${pageContext.request.contextPath}/js/scripts.js"></script>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js" crossorigin="anonymous"></script>
            <script src="assets/demo/chart-area-demo.js"></script>
            <script src="assets/demo/chart-bar-demo.js"></script>

    </body>
    <script>
                                                        function confirmDelete(bookingId) {
                                                            Swal.fire({
                                                                title: 'Bạn có chắc muốn xóa?',
                                                                text: "Hành động này sẽ không thể hoàn tác!",
                                                                icon: 'warning',
                                                                showCancelButton: true,
                                                                confirmButtonColor: '#d33',
                                                                cancelButtonColor: '#6c757d',
                                                                confirmButtonText: 'Xóa',
                                                                cancelButtonText: 'Hủy'
                                                            }).then((result) => {
                                                                if (result.isConfirmed) {
                                                                    window.location.href = 'deletefeedbackroom?bookingId=' + bookingId;
                                                                }
                                                            });
                                                        }
    </script>
</html>
