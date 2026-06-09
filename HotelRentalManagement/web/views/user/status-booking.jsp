<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Trạng thái đơn đặt phòng</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
    </head>
    <body class="bg-light">
        <div class="container py-5">
            <c:if test="${not empty sessionScope.success}">
                <div class="toast-container position-fixed top-0 end-0 p-3">
                    <div class="toast text-bg-success show" role="alert">
                        <div class="d-flex">
                            <div class="toast-body fw-semibold">
                                <i class="bi bi-check-circle-fill me-2"></i> ${sessionScope.success}
                            </div>
                            <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast"></button>
                        </div>
                    </div>
                </div>
                <c:remove var="success" scope="session" />
            </c:if>
            <c:if test="${not empty sessionScope.error}">
                <div class="toast-container position-fixed top-0 end-0 p-3">
                    <div class="toast text-bg-danger show" role="alert">
                        <div class="d-flex">
                            <div class="toast-body fw-semibold">
                                <i class="bi bi-x-circle-fill me-2"></i> ${sessionScope.error}
                            </div>
                            <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast"></button>
                        </div>
                    </div>
                </div>
                <c:remove var="error" scope="session" />
            </c:if>
            <!-- Quay lại -->
            <div class="mb-4">
                <a href="${pageContext.request.contextPath}/updateprofile" class="btn btn-outline-secondary">
                    <i class="bi bi-arrow-left-circle"></i> Quay lại hồ sơ
                </a>
            </div>

            <!-- Tiêu đề -->
            <h2 class="text-center text-primary mb-4">
                <i class="bi bi-journal-check"></i> Danh sách đơn đặt phòng của bạn
            </h2>
            <!-- Bộ lọc trạng thái -->
            <div class="bg-white border rounded-4 shadow-sm p-4 mb-4">
                <h5 class="mb-3 text-primary fw-bold">
                    <i class="bi bi-funnel-fill me-1"></i> Bộ lọc đơn đặt phòng
                </h5>

                <form action="searchmybooking" method="POST" class="row g-3 align-items-end">

                    <!-- Trạng thái -->
                    <div class="col-md-4">
                        <label class="form-label text-secondary fw-semibold">Trạng thái</label>
                        <select name="status" class="form-select shadow-sm">
                            <option value="">-- Tất cả --</option>
                            <option value="approve" ${param.status == 'approve' ? 'selected' : ''}>Đã duyệt</option>
                            <option value="pending" ${param.status == 'pending' ? 'selected' : ''}>Chờ xác nhận</option>
                            <option value="reject" ${param.status == 'reject' ? 'selected' : ''}>Bị từ chối</option>
                            <option value="checkin" ${param.status == 'checkin' ? 'selected' : ''}>Đã nhận phòng</option>
                        </select>
                    </div>

                    <!-- Loại phòng -->
                    <div class="col-md-3 col-sm-6">
                        <label class="form-label fw-semibold text-secondary">
                            <i class="bi bi-tags-fill me-1 text-dark"></i> Loại phòng
                        </label>
                        <select name="roomType" class="form-select form-select-sm rounded-3 shadow-sm">
                            <option value="">-- Tất cả --</option>
                            <c:forEach var="type" items="${roomType}">
                                <option value="${type.typeName}" ${param.roomType == type.typeName ? 'selected' : ''}>
                                    ${type.typeName}
                                </option>
                            </c:forEach>
                        </select>
                    </div>                    <!-- Khoảng giá -->
                    <div class="col-md-6">
                        <label class="form-label fw-semibold text-secondary">
                            <i class="bi bi-cash me-1 text-dark"></i> Khoảng giá (VNĐ)
                        </label>
                        <div class="d-flex flex-column">
                            <div class="d-flex justify-content-between align-items-center mb-2">
                                <div>
                                    Từ: <output id="minPriceOutput" class="fw-bold text-success">0</output> VNĐ
                                </div>
                                <div>
                                    Đến: <output id="maxPriceOutput" class="fw-bold text-danger">10.000.000</output> VNĐ
                                </div>
                            </div>
                            <div class="d-flex gap-2">
                                <input type="range" class="form-range" id="minPrice" name="priceFrom" min="0" max="10000000" step="50000"
                                       value="${param.priceFrom != null ? param.priceFrom : 0}" oninput="updatePriceOutput()">
                                <input type="range" class="form-range" id="maxPrice" name="priceTo" min="0" max="10000000" step="50000"
                                       value="${param.priceTo != null ? param.priceTo : 5000000}" oninput="updatePriceOutput()">
                            </div>
                        </div>
                    </div>
                    <!-- Nút tất cả -->
                    <div class="col-md-3 d-grid">
                        <a href="mybooking" class="btn btn-outline-secondary shadow-sm">
                            <i class="bi bi-arrow-counterclockwise"></i> Tất cả
                        </a>
                    </div>
                    <!-- Nút lọc -->
                    <div class="col-md-3 d-grid">
                        <button type="submit" class="btn btn-primary shadow-sm">
                            <i class="bi bi-search"></i> Lọc kết quả
                        </button>
                    </div>
                </form>
            </div>


            <!-- Thông báo khi danh sách trống -->
            <c:choose>
                <c:when test="${not empty listB}">
                    <!-- Bảng đơn -->
                    <div class="table-responsive bg-white shadow rounded-3">
                        <table class="table table-bordered align-middle text-center">
                            <thead class="table-primary text-dark">
                                <tr>
                                    <th><i class="bi bi-receipt"></i> Mã đơn</th>
                                    <th><i class="bi bi-door-closed-fill"></i> Loại phòng</th>
                                    <th><i class="bi bi-calendar-week"></i> Nhận - Trả</th>
                                    <th><i class="bi bi-info-circle"></i> Trạng thái</th>
                                    <th><i class="bi bi-cash-coin"></i> Tổng tiền</th>
                                    <th><i class="bi bi-credit-card-2-front"></i> Đã thanh toán</th>
                                    <th><i class="bi bi-chat-left-text"></i> Ghi chú</th>
                                    <th><i class="bi bi-gear-fill"></i> Hành động</th>

                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${listB}" var="c">
                                    <tr>
                                        <td class="fw-bold text-primary">#${c.bookingId}</td>
                                        <td class="text-uppercase">${c.roomTypeName}</td>
                                        <td>
                                            <i class="bi bi-arrow-right-circle text-success"></i> ${c.checkIn}
                                            <br>
                                            <i class="bi bi-arrow-left-circle text-danger"></i> ${c.checkOut}
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${c.status == 'approve'}">
                                                    <span class="badge bg-success"><i class="bi bi-check-circle me-1"></i>Đã duyệt</span>
                                                </c:when>
                                                <c:when test="${c.status == 'reject'}">
                                                    <span class="badge bg-danger"><i class="bi bi-x-circle me-1"></i>Đã từ chối</span>
                                                </c:when>
                                                <c:when test="${c.status == 'checkin'}">
                                                    <span class="badge bg-primary"><i class="bi bi-door-open me-1"></i>Đã nhận phòng</span>
                                                </c:when>
                                                <c:when test="${c.status == 'pending'}">
                                                    <span class="badge bg-warning text-dark"><i class="bi bi-clock me-1"></i>Chờ xử lý</span>   
                                                </c:when>

                                            </c:choose>
                                        </td>
                                        <td class="text-end text-danger fw-bold">
                                            <fmt:formatNumber value="${c.totalAmount}" type="number" groupingUsed="true"/>₫
                                        </td>
                                        <td align="right" style="color: green; font-weight: bold;">
                                            <fmt:formatNumber value="${c.depositAmount}" type="number" maxFractionDigits="0" groupingUsed="true"/>₫
                                        </td>

                                        <td>
                                            <c:choose>
                                                <c:when test="${not empty c.note}">
                                                    <i class="bi bi-pencil-square text-info me-1"></i> ${c.note}
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="text-muted">Không có</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <c:if test="${c.status == 'pending' || c.status == 'approve'}">
                                                <form action="cancelbooking" method="POST" class="d-inline booking-cancel-form">
                                                    <input type="hidden" name="bookingId" value="${c.bookingId}">
                                                    <input type="hidden" name="status" value="${c.status}">
                                                    <button type="button" class="btn btn-sm btn-outline-danger show-confirm-modal" data-bs-toggle="modal" data-bs-target="#confirmCancelModal">
                                                        <i class="bi bi-x-circle"></i> Hủy
                                                    </button>
                                                </form>

                                            </c:if>
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
                                        <a class="page-link" href="mybooking?page=${currentPage - 1}">
                                            <i class="bi bi-chevron-left"></i> Trước
                                        </a>
                                    </li>

                                    <!-- Trang đầu + dấu ... nếu cần -->
                                    <c:if test="${startPage > 1}">
                                        <li class="page-item">
                                            <a class="page-link" href="mybooking?page=1">1</a>
                                        </li>
                                        <c:if test="${startPage > 2}">
                                            <li class="page-item disabled"><a class="page-link">...</a></li>
                                            </c:if>
                                        </c:if>

                                    <!-- Các trang lân cận -->
                                    <c:forEach begin="${startPage}" end="${endPage}" var="i">
                                        <li class="page-item ${i == currentPage ? 'active' : ''}">
                                            <a class="page-link" href="mybooking?page=${i}">${i}</a>
                                        </li>
                                    </c:forEach>

                                    <!-- Dấu ... + trang cuối nếu cần -->
                                    <c:if test="${endPage < totalPages}">
                                        <c:if test="${endPage < totalPages - 1}">
                                            <li class="page-item disabled"><a class="page-link">...</a></li>
                                            </c:if>
                                        <li class="page-item">
                                            <a class="page-link" href="mybooking?page=${totalPages}">${totalPages}</a>
                                        </li>
                                    </c:if>

                                    <!-- Nút Sau -->
                                    <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                                        <a class="page-link" href="mybooking?page=${currentPage + 1}">
                                            Sau <i class="bi bi-chevron-right"></i>
                                        </a>
                                    </li>
                                </ul>
                            </nav>
                        </c:if>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="alert alert-info text-center fw-semibold">
                        <i class="bi bi-exclamation-circle me-2"></i>Không có đơn đặt phòng nào phù hợp.
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
        <!-- Modal xác nhận huỷ -->
        <div class="modal fade" id="confirmCancelModal" tabindex="-1" aria-labelledby="confirmCancelModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content border-0 shadow">
                    <div class="modal-header bg-danger text-white">
                        <h5 class="modal-title" id="confirmCancelModalLabel"><i class="bi bi-exclamation-triangle-fill me-2"></i>Xác nhận huỷ đơn</h5>
                        <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
                    </div>
                    <div class="modal-body">
                        Nếu hủy bạn sẽ bị mất tiền cọc.Bạn có chắc chắn muốn huỷ đơn đặt phòng này không?
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Không</button>
                        <button type="button" class="btn btn-danger" id="confirmCancelBtn">Đồng ý huỷ</button>
                    </div>
                </div>
            </div>
        </div>

        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

    </body>
    <script>
        // Tự động ẩn thông báo sau 4 giây
        window.addEventListener('DOMContentLoaded', () => {
            const toastElList = [].slice.call(document.querySelectorAll('.toast'));
            toastElList.map(function (toastEl) {
                const toast = new bootstrap.Toast(toastEl, {delay: 3000});
                toast.show();
            });
        });
        let selectedForm = null;

        document.querySelectorAll(".show-confirm-modal").forEach(btn => {
            btn.addEventListener("click", function () {
                selectedForm = this.closest("form");
            });
        });

        document.getElementById("confirmCancelBtn").addEventListener("click", function () {
            if (selectedForm) {
                selectedForm.submit();
            }
        });
        function updatePriceOutput() {
            const minSlider = document.getElementById("minPrice");
            const maxSlider = document.getElementById("maxPrice");
            const minOutput = document.getElementById("minPriceOutput");
            const maxOutput = document.getElementById("maxPriceOutput");

            let minVal = parseInt(minSlider.value);
            let maxVal = parseInt(maxSlider.value);

            if (minVal > maxVal) {
                const temp = minVal;
                minVal = maxVal;
                maxVal = temp;
            }

            minOutput.textContent = minVal.toLocaleString("vi-VN");
            maxOutput.textContent = maxVal.toLocaleString("vi-VN");
        }

        window.addEventListener("DOMContentLoaded", updatePriceOutput);

    </script>

</html>
