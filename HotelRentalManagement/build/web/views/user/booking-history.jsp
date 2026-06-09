<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Lịch sử đặt phòng</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
    </head>
    <body class="bg-light">
        <!-- Toast Container -->
        <div class="position-fixed top-0 end-0 p-4" style="z-index: 1055; max-width: 350px;">
            <c:if test="${not empty sessionScope.successMessage}">
                <div class="toast text-white bg-success border-0 shadow-lg show" role="alert" aria-live="assertive" aria-atomic="true" id="successToast">
                    <div class="d-flex align-items-center p-3 fs-6">
                        <div class="me-auto fw-semibold">
                            ✅ ${sessionScope.successMessage}
                        </div>
                        <button type="button" class="btn-close btn-close-white ms-2" data-bs-dismiss="toast" aria-label="Close"></button>
                    </div>
                </div>
                <c:remove var="successMessage" scope="session" />
            </c:if>

            <c:if test="${not empty sessionScope.errorMessage}">
                <div class="toast text-white bg-danger border-0 shadow-lg show" role="alert" aria-live="assertive" aria-atomic="true" id="errorToast">
                    <div class="d-flex align-items-center p-3 fs-6">
                        <div class="me-auto fw-semibold">
                            ❌ ${sessionScope.errorMessage}
                        </div>
                        <button type="button" class="btn-close btn-close-white ms-2" data-bs-dismiss="toast" aria-label="Close"></button>
                    </div>
                </div>
                <c:remove var="errorMessage" scope="session" />
            </c:if>
        </div>

        <div class="container py-4">
            <div class="mb-4">
                <a href="${pageContext.request.contextPath}/updateprofile" class="btn btn-outline-secondary">
                    <i class="bi bi-arrow-left-circle"></i> Quay lại hồ sơ
                </a>
            </div>

            <h2 class="text-center text-primary mb-4"><i class="bi bi-journal-check"></i> Danh sách phòng đã đặt</h2>

            <!-- BỘ LỌC -->
            <form action="searchhistorybooking" method="POST" class="bg-white border rounded-4 shadow-sm p-4 mb-4">
                <div class="row g-3 align-items-end">

                    <!-- Từ ngày -->
                    <div class="col-md-3 col-sm-6">
                        <label class="form-label fw-semibold text-secondary">
                            <i class="bi bi-calendar-range me-1 text-dark"></i> Từ ngày
                        </label>
                        <input type="date" class="form-control form-control-sm rounded-3 shadow-sm"
                               name="fromDate" value="${param.fromDate}">
                    </div>

                    <!-- Đến ngày -->
                    <div class="col-md-3 col-sm-6">
                        <label class="form-label fw-semibold text-secondary">
                            <i class="bi bi-calendar-range me-1 text-dark"></i> Đến ngày
                        </label>
                        <input type="date" class="form-control form-control-sm rounded-3 shadow-sm"
                               name="toDate" value="${param.toDate}">
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
                    </div>

                    <!-- Khoảng giá (thanh trượt) -->
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

                    <!-- Nút "Tất cả" -->
                    <div class="col-md-3 col-sm-6">
                        <label class="form-label text-white">Ẩn</label>
                        <a href="bookinghistory" class="btn btn-outline-secondary w-100 rounded-pill">
                            <i class="bi bi-arrow-counterclockwise"></i> Tất cả
                        </a>
                    </div>

                    <!-- Nút lọc -->
                    <div class="col-md-3 col-sm-6">
                        <label class="form-label text-white">Ẩn</label>
                        <button type="submit" class="btn btn-primary w-100 rounded-pill">
                            <i class="bi bi-search"></i> Lọc kết quả
                        </button>
                    </div>
                </div>
            </form>




            <c:if test="${ not empty listB}">
                <!-- DANH SÁCH BOOKING (DẠNG TABLE) -->
                <div class="table-responsive bg-white shadow-sm rounded">
                    <table class="table table-bordered table-hover align-middle mb-5">
                        <thead class="table-primary text-center">
                            <tr>
                                <th><i class="bi bi-door-closed-fill text-success"></i> Phòng</th>
                                <th><i class="bi bi-tags-fill text-primary"></i> Loại phòng</th>
                                <th><i class="bi bi-calendar-week text-primary"></i> Nhận - Trả</th>
                                <th><i class="bi bi-percent text-warning"></i> Khuyến mãi</th>
                                <th><i class="bi bi-cone-striped text-danger"></i> Dịch vụ</th>
                                <th><i class="bi bi-currency-dollar text-success"></i> Tổng tiền</th>
                                <th><i class="bi bi-chat-left-text text-secondary"></i> Ghi chú</th>
                                <th><i class="bi bi-gear text-dark"></i> Hành động</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="c" items="${listB}">
                                <tr>
                                    <td class="text-center fw-bold text-success">#${c.roomNumber}</td>
                                    <td class="text-uppercase">${c.roomTypeName}</td>
                                    <td class="text-nowrap">
                                        <i class="bi bi-arrow-right-circle text-primary"></i>
                                        <strong>${c.checkIn}</strong><br>
                                        <i class="bi bi-arrow-left-circle text-danger"></i>
                                        <strong>${c.checkOut}</strong>
                                    </td>

                                    <td class="text-center">
                                        <c:choose>
                                            <c:when test="${not empty c.promotionId}">
                                                <span class="badge bg-warning text-dark">#${c.promotionId}</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="text-muted">Không</span>
                                            </c:otherwise>
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
                                        <i class="bi bi-cash-coin me-1"></i><fmt:formatNumber value="${c.totalAmount}" type="number" groupingUsed="true"/>₫

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
                                        <a href="roomBooking?roomId=${c.roomId}" class="btn btn-outline-success btn-sm w-100">
                                            <i class="bi bi-arrow-repeat"></i> Đặt lại
                                        </a>
                                        <c:choose>
                                            <c:when test="${not feedbackBookingIds.contains(c.bookingId)}">
                                                <a href="views/user/feedback-room.jsp?roomId=${c.roomId}&bookingId=${c.bookingId}"
                                                   class="btn btn-outline-warning btn-sm w-100">
                                                    <i class="bi bi-chat-dots"></i> Phản hồi
                                                </a>
                                            </c:when>
                                            <c:otherwise>
                                                <a href="views/user/feedback-room-again.jsp?bookingId=${c.bookingId}"
                                                   class="btn btn-warning btn-sm w-100">
                                                    <i class="bi bi-pencil-square"></i> Đánh giá lại
                                                </a>
                                            </c:otherwise>
                                        </c:choose>

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
                                        <a class="page-link" href="bookinghistory?page=1">1</a>
                                    </li>
                                    <c:if test="${startPage > 2}">
                                        <li class="page-item disabled"><a class="page-link">...</a></li>
                                        </c:if>
                                    </c:if>

                                <!-- Các trang lân cận -->
                                <c:forEach begin="${startPage}" end="${endPage}" var="i">
                                    <li class="page-item ${i == currentPage ? 'active' : ''}">
                                        <a class="page-link" href="bookinghistory?page=${i}">${i}</a>
                                    </li>
                                </c:forEach>

                                <!-- Dấu ... + trang cuối nếu cần -->
                                <c:if test="${endPage < totalPages}">
                                    <c:if test="${endPage < totalPages - 1}">
                                        <li class="page-item disabled"><a class="page-link">...</a></li>
                                        </c:if>
                                    <li class="page-item">
                                        <a class="page-link" href="bookinghistory?page=${totalPages}">${totalPages}</a>
                                    </li>
                                </c:if>

                                <!-- Nút Sau -->
                                <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                                    <a class="page-link" href="bookinghistory?page=${currentPage + 1}">
                                        Sau <i class="bi bi-chevron-right"></i>
                                    </a>
                                </li>
                            </ul>
                        </nav>
                    </c:if>
                </div>
            </c:if>
            <div class="text-center p-4 bg-light border rounded-3 shadow-sm mt-4">
                <i class="bi bi-house-add fs-2 text-primary mb-2"></i>
                <h5 class="text-dark fw-semibold mb-1">Đặt thêm phòng?</h5>
                <p class="text-muted small mb-3">Khám phá thêm ưu đãi hấp dẫn dành cho bạn!</p>
                <a href="listRoomBooking" class="btn btn-outline-primary btn-sm">
                    <i class="bi bi-calendar-plus"></i> Đặt ngay
                </a>
            </div>


        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
    <script>
        window.addEventListener('DOMContentLoaded', () => {
            const successToast = document.getElementById('successToast');
            const errorToast = document.getElementById('errorToast');

            if (successToast) {
                const toast = new bootstrap.Toast(successToast, {delay: 4000});
                toast.show();
            }

            if (errorToast) {
                const toast = new bootstrap.Toast(errorToast, {delay: 4000});
                toast.show();
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
