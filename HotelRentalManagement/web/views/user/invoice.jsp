<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Hóa đơn thanh toán</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
        <style>
            body {
                background: #f0f2f5;
                font-family: "Segoe UI", sans-serif;
                font-size: 1.05rem;
            }
            .invoice-box {
                background: #fff;
                padding: 40px;
                border-radius: 15px;
                box-shadow: 0 8px 20px rgba(0, 0, 0, 0.05);
            }
            .section-title {
                font-size: 1.5rem;
                font-weight: 600;
                color: #0d6efd;
                margin-bottom: 20px;
                display: flex;
                align-items: center;
                gap: 0.5rem;
            }
            .section-title i {
                font-size: 1.4rem;
            }
            .invoice-summary .row > div {
                margin-bottom: 12px;
            }
            .list-group-item {
                border: none;
                background-color: #f8f9fa;
                border-radius: 8px;
                margin-bottom: 5px;
            }
            .alert {
                border-radius: 10px;
            }
            .alert-warning {
                background-color: #fff3cd;
                color: #856404;
            }
            .alert-info {
                background-color: #d1ecf1;
                color: #0c5460;
            }
            .alert-success {
                background-color: #d4edda;
                color: #155724;
            }
            .selected-service-list .list-group-item {
                background-color: #e8f5e9; /* xanh lá nhạt */
                border: none;
                border-radius: 12px;
                padding: 12px 16px;
                margin-bottom: 8px;
                transition: all 0.2s ease;
                box-shadow: 0 1px 3px rgba(0,0,0,0.05);
            }

            .selected-service-list .list-group-item:hover {
                background-color: #d0f0d5;
                transform: scale(1.01);
                box-shadow: 0 2px 6px rgba(0, 0, 0, 0.08);
                cursor: default;
            }
        </style>
    </head>
    <body>
        <div class="container py-5">
            <div class="text-center mb-5">
                <h2 class="text-primary fw-bold">
                    <i class="bi bi-receipt me-2"></i>Hóa đơn thanh toán
                </h2>
                <p class="text-muted">🎉 Cảm ơn bạn đã đặt phòng tại hệ thống của chúng tôi!</p>
            </div>

            <div class="invoice-box">
                <div class="section-title"><i class="bi bi-person-circle"></i>Thông tin khách hàng</div>
                <div class="row">
                    <div class="col-md-6"><i class="bi bi-person"></i> Họ tên: <strong>${user.name}</strong></div>
                    <div class="col-md-6"><i class="bi bi-telephone"></i> Số điện thoại: <strong>${user.phone}</strong></div>
                    <div class="col-md-6"><i class="bi bi-envelope-at"></i> Email: <strong>${user.email}</strong></div>
                    <div class="col-md-6"><i class="bi bi-people"></i> Số người: <strong>${numPeople}</strong></div>
                </div>

                <hr class="my-4">
                <div class="section-title"><i class="bi bi-door-open"></i>Chi tiết đặt phòng</div>
                <div class="row invoice-summary">
                    <div class="col-md-6">
                        <i class="bi bi-house-heart"></i> Loại phòng:
                        <strong>${room.roomType.typeName}</strong>
                    </div>   

                    <div class="col-md-6">
                        <i class="bi bi-cash-coin"></i> Giá/đêm:
                        <strong><fmt:formatNumber value="${room.roomType.basePrice}" type="number" groupingUsed="true"/>đ</strong>
                    </div>
                    <div class="col-md-6">
                        <i class="bi bi-moon-stars"></i> Số đêm:
                        <strong>${night}</strong>
                    </div>
                    <div class="col-md-6">
                        <i class="bi bi-calendar-check"></i> Nhận phòng:
                        <strong>${checkIn}</strong>
                    </div>
                    <div class="col-md-6">
                        <i class="bi bi-journal-text"></i> Yêu cầu đặc biệt:
                        <strong>${specialRequest}</strong>
                    </div>
                    <div class="col-md-6">
                        <i class="bi bi-calendar-x"></i> Trả phòng:
                        <strong>${checkOut}</strong>
                    </div>  
                </div>
                <hr class="my-4">
                <div class="section-title"><i class="bi bi-box-seam"></i> Dịch vụ đã chọn</div>
                <div class="mb-4">
                    <ul class="list-group selected-service-list">
                        <c:forEach var="s" items="${selectedServiceNames}">
                            <li class="list-group-item d-flex align-items-center gap-3">
                                <i class="bi bi-check2-circle text-success fs-5"></i>
                                <span class="fw-semibold text-dark">${s}</span>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
                <hr class="my-4">
                <div class="section-title"><i class="bi bi-cash-stack"></i> Điều chỉnh chi phí</div>
                <div class="mb-4">
                    <ul class="list-group list-group-flush adjustment-list">
                        <li class="list-group-item alert alert-warning d-flex justify-content-between align-items-center">
                            <div><i class="bi bi-person-plus-fill me-2"></i>Phụ phí thêm người</div>
                            <strong><fmt:formatNumber value="${extraFee}" type="number" groupingUsed="true"/>đ</strong>
                        </li>
                        <li class="list-group-item alert alert-info d-flex justify-content-between align-items-center">
                            <div><i class="bi bi-ticket-perforated me-2"></i>Giảm giá từ mã voucher</div>
                            <strong class="text-primary">-<fmt:formatNumber value="${voucherDiscount}" type="number" groupingUsed="true"/>đ</strong>
                        </li>
                        <li class="list-group-item alert alert-success d-flex justify-content-between align-items-center">
                            <div><i class="bi bi-star-fill me-2"></i>Đổi điểm thành tiền</div>
                            <strong class="text-success">-<fmt:formatNumber value="${pointsUsedAmount}" type="number" groupingUsed="true"/>đ</strong>
                        </li>
                    </ul>
                </div>
                <hr class="my-4">
                <div class="section-title"><i class="bi bi-calculator-fill"></i> Tổng kết thanh toán</div>
                <ul class="list-group fs-5 mb-4">
                    <li class="list-group-item d-flex justify-content-between align-items-center bg-light border-0">
                        <span class="fw-semibold text-dark">Tổng tiền cần thanh toán:</span>
                        <strong class="text-danger fs-5">
                            <fmt:formatNumber value="${totalAmount}" type="number" groupingUsed="true"/>đ
                        </strong>
                    </li>
                    <li class="list-group-item bg-warning-subtle border-warning rounded-3 shadow-sm mt-3 p-4 d-flex justify-content-between align-items-center">
                        <div>
                            <i class="bi bi-exclamation-circle-fill text-warning me-2 fs-5"></i>
                            <span class="fw-bold text-dark">Số tiền cần thanh toán trước (${prepayment}%):</span>
                        </div>
                        <strong class="fs-5" style="color: #ff6f00;">
                            <fmt:formatNumber value="${paymentAmount}" type="number" groupingUsed="true"/>đ
                        </strong>
                    </li>
                </ul>
                <div class="text-center d-flex justify-content-center gap-3">
                    <form action="invoice" method="GET">
                        <input type="hidden" name="roomId" value="${room.roomId}" />
                        <button type="submit" class="btn btn-outline-secondary btn-lg">
                            <i class="bi bi-arrow-left me-1"></i> Quay lại chỉnh sửa
                        </button>
                    </form>

                    <button type="button" class="btn btn-primary btn-lg px-5" data-bs-toggle="modal" data-bs-target="#qrModal">
                        <i class="bi bi-wallet2 me-1"></i> Thanh toán ngay
                    </button>

                </div>

            </div>
        </div>
        <!-- QR Code Modal -->
        <div class="modal fade" id="qrModal" tabindex="-1" aria-labelledby="qrModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered modal-lg">
                <div class="modal-content border-0 rounded-4 overflow-hidden">
                    <div class="modal-header bg-primary text-white">
                        <h5 class="modal-title d-flex align-items-center" id="qrModalLabel">
                            <i class="bi bi-qr-code-scan me-2"></i> Thanh toán bằng QR
                        </h5>
                        <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Đóng"></button>
                    </div>
                    <div class="modal-body text-center px-4 py-4">
                        <p class="mb-2 fs-5 fw-semibold">Quét mã để thanh toán</p>
                        <img src='https://img.vietqr.io/image/MB-0394855685-compact2.jpg?amount=${paymentAmount}&addInfo=BookingId ${lastBookingId}&accountName=DO%20DUY%20HOANG'/>
                        <div class="text-muted mt-2" style="font-size: 0.9rem;">
                            Sử dụng ứng dụng ngân hàng hoặc ví điện tử để quét mã
                        </div>
                    </div>
                    <div class="modal-footer justify-content-center border-0 pb-4">
                        <form action="payment" method="POST">
                            <input type="hidden" name="night" value="${night}" />
                            <input type="hidden" name="basePrice" value="${room.roomType.basePrice}" />
                            <input type="hidden" name="roomType" value="${room.roomType.typeName}" />
                            <input type="hidden" name="numPeople" value="${numPeople}" />
                            <input type="hidden" name="prepayment" value="${prepayment}" />
                            <input type="hidden" name="pointsUsedAmount" value="${pointsUsedAmount}" />
                            <input type="hidden" name="voucherDiscount" value="${voucherDiscount}" />
                            <input type="hidden" name="extraFee" value="${extraFee}" />
                            <input type="hidden" name="roomId" value="${room.roomId}" />
                            <input type="hidden" name="userId" value="${user.id}" />
                            <input type="hidden" name="userPoint" value="${user.points}" />
                            <input type="hidden" name="pointsUsed" value="${pointsUsed}" />
                            <input type="hidden" name="checkin" value="${checkIn}" />
                            <input type="hidden" name="checkout" value="${checkOut}" />
                            <input type="hidden" name="request" value="${specialRequest}" />
                            <input type="hidden" name="promotionId" value="${voucherId}" />
                            <input type="hidden" name="total" value="${totalAmount}" />
                            <input type="hidden" name="depositAmount" value="${paymentAmount}" />
                            <c:forEach var="id" items="${selectedServiceIds}">
                                <input type="hidden" name="services" value="${id}" />
                            </c:forEach>
                            <c:forEach var="n" items="${selectedServiceNames}">
                                <input type="hidden" name="servicesName" value="${n}" />
                            </c:forEach>

                            <button type="submit" class="btn btn-success px-4 me-2">
                                <i class="bi bi-check-circle me-1"></i> Xác nhận đã thanh toán
                            </button>
                        </form>
                        <button type="button" class="btn btn-outline-danger px-4" data-bs-dismiss="modal">
                            <i class="bi bi-x-circle me-1"></i> Huỷ thanh toán
                        </button>
                    </div>
                </div>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

    </body>
</html>