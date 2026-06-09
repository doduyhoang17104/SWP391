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
        <title>Duyệt đơn</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
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
                    <div class="position-fixed top-0 end-0 p-4" style="z-index: 1055; max-width: 350px;">
                        <!-- Phê duyệt thành công -->
                        <c:if test="${not empty sessionScope.successMessage}">
                            <div class="toast text-white border-0 shadow-lg show"
                                 role="alert" aria-live="assertive" aria-atomic="true"
                                 id="successToast"
                                 style="background-color: #28a745 !important;">
                                <div class="d-flex align-items-center p-3 fs-6">
                                    <div class="me-auto fw-semibold">
                                        ✅ ${sessionScope.successMessage}
                                    </div>
                                    <button type="button" class="btn-close btn-close-white ms-2"
                                            data-bs-dismiss="toast" aria-label="Close"></button>
                                </div>
                            </div>
                            <c:remove var="successMessage" scope="session" />
                        </c:if>
                        <!-- Từ chối thành công -->
                        <c:if test="${not empty sessionScope.rejectMessage}">
                            <div class="toast text-white border-0 shadow-lg show"
                                 role="alert" aria-live="assertive" aria-atomic="true"
                                 id="rejectToast"
                                 style="background-color:red !important;">
                                <div class="d-flex align-items-center p-3 fs-6">
                                    <div class="me-auto fw-semibold">
                                        ⚠️ ${sessionScope.rejectMessage}
                                    </div>
                                    <button type="button" class="btn-close btn-close-white ms-2"
                                            data-bs-dismiss="toast" aria-label="Close"></button>
                                </div>
                            </div>
                            <c:remove var="rejectMessage" scope="session" />
                        </c:if>
                    </div>
                    <div class="w-100 p-4">
                        <h2 class="text-center text-primary mb-4"><i class="bi bi-journal-check"></i> Danh sách phòng cần phê duyệt</h2>

                        <!-- BỘ LỌC -->
                        <form action="searchapprovebooking" method="POST" class="bg-white border rounded-4 shadow-sm p-4 mb-4">
                            <div class="row gy-3 gx-3 align-items-center">
                                <!-- Ô nhập tên khách hàng -->
                                <div class="col-lg-6 col-md-12">
                                    <label class="form-label fw-semibold text-secondary">
                                        <i class="bi bi-person-lines-fill me-1 text-dark"></i> Tên khách hàng
                                    </label>
                                    <input type="text" class="form-control rounded-3 shadow-sm"
                                           name="name" placeholder="Nhập tên cần tìm..."
                                           value="${param.keyword}">
                                </div>

                                <!-- Nút Tất cả -->
                                <div class="col-lg-3 col-md-6">
                                    <label class="form-label invisible">Ẩn</label>
                                    <a href="listapprovebooking" class="btn btn-outline-secondary w-100 rounded-pill shadow-sm">
                                        <i class="bi bi-arrow-counterclockwise"></i> Tất cả
                                    </a>
                                </div>

                                <!-- Nút Tìm kiếm -->
                                <div class="col-lg-3 col-md-6">
                                    <label class="form-label invisible">Ẩn</label>
                                    <button type="submit" class="btn btn-primary w-100 rounded-pill shadow-sm">
                                        <i class="bi bi-search"></i> Tìm kiếm
                                    </button>
                                </div>
                            </div>
                        </form>

                        <div class="row g-3 mb-4">
                            <div class="col-md-6">
                                <div class="bg-primary-subtle border border-primary-subtle rounded-4 p-3 shadow-sm d-flex justify-content-between align-items-center" style="min-height: 100px;">
                                    <div class="text-center w-100">
                                        <div class="text-secondary small">Tổng số đơn</div>
                                        <div class="fs-5 fw-bold text-primary">${count}</div>
                                    </div>
                                    <i class="bi bi-clipboard-check fs-2 text-primary"></i>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="bg-danger-subtle border border-danger-subtle rounded-4 p-3 shadow-sm d-flex justify-content-between align-items-center" style="min-height: 100px;">
                                    <div class="text-center w-100">
                                        <div class="text-secondary small">Tổng tiền</div>
                                        <div class="fs-5 fw-bold text-danger">
                                            <fmt:formatNumber value="${totalAmountAll}" type="number" groupingUsed="true" />₫
                                        </div>
                                    </div>
                                    <i class="bi bi-cash-stack fs-2 text-danger"></i>
                                </div>
                            </div>
                        </div>
                        <!-- THỐNG KÊ TRẠNG THÁI -->
                        <div class="row text-center mb-4">
                            <div class="col-md-4 mb-2">
                                <div class="bg-warning-subtle text-warning border border-warning rounded-4 py-3 shadow-sm">
                                    <h5 class="mb-1 fw-bold"><i class="bi bi-hourglass-split me-1"></i> Chờ duyệt</h5>
                                    <div class="fs-4">${pendingCount}</div>
                                </div>
                            </div>
                            <div class="col-md-4 mb-2">
                                <div class="bg-success-subtle text-success border border-success rounded-4 py-3 shadow-sm">
                                    <h5 class="mb-1 fw-bold"><i class="bi bi-check2-circle me-1"></i> Đã duyệt</h5>
                                    <div class="fs-4">${approvedCount}</div>
                                </div>
                            </div>
                            <div class="col-md-4 mb-2">
                                <div class="bg-danger-subtle text-danger border border-danger rounded-4 py-3 shadow-sm">
                                    <h5 class="mb-1 fw-bold"><i class="bi bi-x-circle me-1"></i> Đã hủy</h5>
                                    <div class="fs-4">${rejectedCount}</div>
                                </div>
                            </div>
                        </div>
                        <c:choose>

                            <c:when test="${not empty listB}">
                                <div class="table-responsive bg-white shadow-sm rounded">
                                    <table class="table table-bordered table-hover align-middle mb-5">
                                        <thead class="table-primary text-center">
                                            <tr>
                                                <th>Mã đơn</th>
                                                <th>Phòng</th>
                                                <th>Loại phòng</th>
                                                <th>Khách hàng</th>
                                                <th>Nhận - Trả</th>
                                                <th>Khuyến mãi</th>
                                                <th>Dịch vụ</th>
                                                <th>Tổng tiền</th>
                                                <th>Thanh toán trước</th>
                                                <th>Ghi chú</th>
                                                <th>Hành động</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="c" items="${listB}">
                                                <tr>
                                                    <td>#${c.bookingId}</td>
                                                    <td class="text-center fw-bold text-success">${c.roomNumber}</td>
                                                    <td>${c.roomTypeName}</td>
                                                    <td>${c.userName}</td>
                                                    <td class="text-nowrap">
                                                        <i class="bi bi-arrow-right-circle text-primary"></i> <strong>${c.checkIn}</strong><br>
                                                        <i class="bi bi-arrow-left-circle text-danger"></i> <strong>${c.checkOut}</strong>
                                                    </td>
                                                    <td class="text-center">
                                                        <c:choose>
                                                            <c:when test="${not empty c.promotionId}">
                                                                <span class="badge bg-warning text-dark">#${c.promotionId}</span>
                                                            </c:when>
                                                            <c:otherwise><span class="text-muted">Không</span></c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${not empty c.serviceNames}">
                                                                <ul class="mb-0 ps-3">
                                                                    <c:forEach var="name" items="${c.serviceNames}" varStatus="loop">
                                                                        <li class="fw-bold">
                                                                            <i class="bi bi-check2-circle text-success me-1"></i>
                                                                            ${name} <span class="text-muted">(x${c.serviceQuantities[loop.index]})</span>
                                                                        </li>
                                                                    </c:forEach>
                                                                </ul>
                                                            </c:when>
                                                            <c:otherwise><span class="text-muted">Không</span></c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                    <td class="text-end text-danger fw-bold">
                                                        <fmt:formatNumber value="${c.totalAmount}" type="number" groupingUsed="true"/>₫
                                                    </td>
                                                    <td class="text-end text-danger fw-bold">
                                                        <fmt:formatNumber value="${c.depositAmount}" type="number" groupingUsed="true"/>₫
                                                    </td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${not empty c.request}">
                                                                <i class="bi bi-pencil-square text-info me-1"></i> ${c.request}
                                                            </c:when>
                                                            <c:otherwise><span class="text-muted">Không</span></c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                    <td class="text-center d-flex flex-column gap-2">
                                                        <form action="approvebooking" method="POST">
                                                            <input type="hidden" name="bookingId" value="${c.bookingId}" />
                                                            <input type="hidden" name="action" value="approve" />
                                                            <input type="hidden" name="checkIn" value="${c.checkIn}" />
                                                            <input type="hidden" name="checkOut" value="${c.checkOut}" />
                                                            <button type="submit" class="btn btn-success btn-sm w-100">
                                                                <i class="bi bi-check2-circle"></i> Duyệt
                                                            </button>
                                                        </form>

                                                        <button type="button" class="btn btn-danger btn-sm w-100"
                                                                data-bs-toggle="modal"
                                                                data-bs-target="#rejectModal${c.bookingId}">
                                                            <i class="bi bi-x-circle"></i> Hủy
                                                        </button>
                                                    </td>
                                                </tr>
                                            </c:forEach>
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
                                                    <a class="page-link" href="listapprovebooking?page=${currentPage - 1}">
                                                        <i class="bi bi-chevron-left"></i> Trước
                                                    </a>
                                                </li>

                                                <!-- Trang đầu + dấu ... nếu cần -->
                                                <c:if test="${startPage > 1}">
                                                    <li class="page-item">
                                                        <a class="page-link" href="listapprovebooking?page=1">1</a>
                                                    </li>
                                                    <c:if test="${startPage > 2}">
                                                        <li class="page-item disabled"><a class="page-link">...</a></li>
                                                        </c:if>
                                                    </c:if>

                                                <!-- Các trang lân cận -->
                                                <c:forEach begin="${startPage}" end="${endPage}" var="i">
                                                    <li class="page-item ${i == currentPage ? 'active' : ''}">
                                                        <a class="page-link" href="listapprovebooking?page=${i}">${i}</a>
                                                    </li>
                                                </c:forEach>

                                                <!-- Dấu ... + trang cuối nếu cần -->
                                                <c:if test="${endPage < totalPages}">
                                                    <c:if test="${endPage < totalPages - 1}">
                                                        <li class="page-item disabled"><a class="page-link">...</a></li>
                                                        </c:if>
                                                    <li class="page-item">
                                                        <a class="page-link" href="listapprovebooking?page=${totalPages}">${totalPages}</a>
                                                    </li>
                                                </c:if>

                                                <!-- Nút Sau -->
                                                <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                                                    <a class="page-link" href="listapprovebooking?page=${currentPage + 1}">
                                                        Sau <i class="bi bi-chevron-right"></i>
                                                    </a>
                                                </li>
                                            </ul>
                                        </nav>
                                    </c:if>
                                </div>

                                <!-- 🔻 MODAL phải đặt ngoài <table>, nhưng vẫn trong vòng forEach -->
                                <c:forEach var="c" items="${listB}">
                                    <div class="modal fade" id="rejectModal${c.bookingId}" tabindex="-1"
                                         aria-labelledby="rejectModalLabel${c.bookingId}" aria-hidden="true">
                                        <div class="modal-dialog">
                                            <form action="approvebooking" method="POST" class="modal-content">
                                                <div class="modal-header bg-danger text-white">
                                                    <h5 class="modal-title" id="rejectModalLabel${c.bookingId}">
                                                        Từ chối đơn #${c.bookingId}
                                                    </h5>
                                                    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
                                                </div>
                                                <div class="modal-body">
                                                    <input type="hidden" name="bookingId" value="${c.bookingId}">
                                                    <input type="hidden" name="action" value="reject">
                                                    <div class="mb-3">
                                                        <label for="reason${c.bookingId}" class="form-label">Lý do từ chối</label>
                                                        <textarea class="form-control" id="reason${c.bookingId}" name="rejectReason" rows="3" required></textarea>
                                                    </div>
                                                </div>
                                                <div class="modal-footer">
                                                    <button type="submit" class="btn btn-danger">Từ chối</button>
                                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                </c:forEach>
                            </c:when>



                            <c:otherwise>
                                <div class="text-center p-5 bg-white border border-2 border-secondary-subtle rounded-4 shadow-sm mt-5">
                                    <i class="bi bi-inbox fs-1 text-secondary mb-3"></i>
                                    <h5 class="text-dark fw-bold mb-2">Không có đơn cần phê duyệt</h5>
                                    <p class="text-muted mb-4">Hiện tại chưa có đơn đặt phòng nào đang chờ xử lý.</p>
                                    <a href="listRoom" class="btn btn-outline-primary rounded-pill">
                                        <i class="bi bi-arrow-left-circle"></i> Quay về danh sách phòng
                                    </a>
                                </div>
                            </c:otherwise>
                        </c:choose>
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
        <script>
        window.addEventListener('DOMContentLoaded', () => {
                const successToast = document.getElementById('successToast');
        const rejectToast = document.getElementById('rejectToast');
        const errorToast = document.getElementById('errorToast');

        if (successToast)
            new bootstrap.Toast(successToast, {delay: 2000}).show();
        if (rejectToast)
            new bootstrap.Toast(rejectToast, {delay: 2000}).show();
        if (errorToast)
            new bootstrap.Toast(errorToast, {delay: 2000}).show();
            });
    </script>
</html>
