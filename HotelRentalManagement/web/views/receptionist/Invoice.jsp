<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8" />
        <title>Hóa đơn</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet" />
    </head>
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
    <body>
        <div class="container py-5">
            <div class="text-center mb-5">
                <h2 class="text-center text-primary fw-bold">
                    <i class="bi bi-receipt"></i> Hóa đơn đặt phòng
                </h2>
                <p class="text-muted">🎉 Cảm ơn bạn đã đặt phòng tại hệ thống của chúng tôi!</p>
            </div>

            <div class="invoice-box">
                <div class="section-title"><i class="bi bi-person-circle"></i>Thông tin lễ tân</div>
                <div class="row">
                    <div class="col-md-6">
                        <i class="bi bi-person"></i> Họ tên: <strong>${sessionScope.user.name}</strong>
                    </div>
                    <div class="col-md-6">
                        <i class="bi bi-telephone"></i> Số điện thoại: <strong>${sessionScope.user.phone}</strong>
                    </div>
                </div>

                <hr class="my-4">
                <div class="section-title"><i class="bi bi-person-circle"></i>Thông tin khách hàng</div>
                <div class="row">
                    <div class="col-md-6">
                        <i class="bi bi-person"></i> Họ tên: <strong>${booking.userName}</strong>
                    </div>

                    <div class="col-md-6">
                        <i class="bi bi-telephone"></i> Số điện thoại: <strong>${booking.userPhone}</strong>
                    </div>

                    <div class="col-md-6">

                        <c:choose>
                            <c:when test="${not empty booking.userEmail}">
                                <i class="bi bi-envelope-at"></i> Email: <strong>${booking.userEmail}</strong>
                            </c:when>
                            <c:otherwise>
                                <i class="bi bi-person-vcard"></i> CMND/CCCD: <strong>${booking.userIdentity}</strong>
                            </c:otherwise>
                        </c:choose>


                    </div>
                </div>

                <hr class="my-4">
                <div class="section-title"><i class="bi bi-door-open"></i>Chi tiết đặt phòng</div>
                <div class="row invoice-summary">
                    <div class="col-md-6">
                        <i class="bi bi-receipt"></i> Mã đơn đặt phòng:
                        <strong>#${booking.bookingId}</strong>
                    </div>
                    <div class="col-md-6">
                        <i class="bi bi-house-heart"></i> Loại phòng:
                        <strong>${booking.roomTypeName}</strong>
                    </div>   
                    <div class="col-md-6">
                        <i class="bi bi-calendar-check"></i> Nhận phòng:
                        <strong>${booking.checkIn}</strong>
                    </div>
                    <div class="col-md-6">
                        <i class="bi bi-house-heart"></i> Số phòng:
                        <strong>${booking.roomNumber}</strong>
                    </div>   
                    <div class="col-md-6">
                        <i class="bi bi-calendar-x"></i> Trả phòng:
                        <strong>${booking.checkOut}</strong>
                    </div>  
                </div>
                <hr class="my-4">
                <div class="section-title"><i class="bi bi-box-seam"></i> Dịch vụ đã chọn</div>
                <div class="mb-4">
                    <ul class="list-group selected-service-list">
                        <c:if test="${not empty selectedServices}">
                            <ul class="list-group">
                                <c:forEach var="entry" items="${selectedServices}">
                                    <li class="list-group-item">
                                        <i class="bi bi-check-circle text-success me-2"></i>
                                        ${entry.key} (x${entry.value})
                                    </li>
                                </c:forEach>
                            </ul>
                        </c:if>
                        <c:if test="${empty selectedServices}">
                            <div class="alert alert-secondary">Không sử dụng dịch vụ nào.</div>
                        </c:if>
                    </ul>
                </div>
                <hr class="my-4">
                <div class="mt-4">
                    <h4><i class="bi bi-cash-coin"></i> Chi phí</h4>
                    <ul class="list-group">
                        <li class="list-group-item d-flex justify-content-between">
                            <span>Tổng tiền:</span>
                            <strong><fmt:formatNumber value="${totalAmountNow}" type="currency" currencySymbol="₫" /></strong>
                        </li>
                        <c:if test="${phat != 0}">
                            <li class="list-group-item d-flex justify-content-between">
                                <span>Checkout quá giờ:</span>
                                <strong><fmt:formatNumber value="${phat}" type="currency" currencySymbol="₫" /></strong>
                            </li>
                        </c:if>
                        <li class="list-group-item bg-warning-subtle border-warning rounded-3 shadow-sm mt-3 p-4 d-flex justify-content-between align-items-center">
                            <div>
                                <i class="bi bi-exclamation-circle-fill text-warning me-2 fs-5"></i>
                                <span class="fw-bold text-dark">Số tiền đã thanh toán (<span id="prepayPercent">${prepayment}</span>%):</span>
                            </div>
                            <strong class="fs-5" id="prepayAmount" style="color: #ff6f00;">
                                <fmt:formatNumber value="${summary.depositAmount}" type="currency" currencySymbol="₫" />                            </strong>
                        </li>

                        <li class="list-group-item d-flex justify-content-between align-items-center bg-light border-0">
                            <span class="fw-semibold text-dark">Số tiền còn lại phải thanh toán</span>
                            <strong class="text-danger fs-5" id="remainingAmount">
                                <fmt:formatNumber value="${summary.remainingAmount}" type="currency" currencySymbol="₫" />
                            </strong>
                        </li>

                    </ul>
                </div>  
                <hr class="my-4">

                <form action="generateInvoice" method="POST" id="paymentForm">
                    <input type="hidden" name="phat" value="${phat}" /> 
                    <input type="hidden" name="bookingId" value="${booking.bookingId}" />
                    <input type="hidden" name="remainingAmount" id="remainingAmountInput" value="${summary.remainingAmount}" />
                    <input type="hidden" name="totalAmount" value="${booking.totalAmount}" />
                    <input type="hidden" name="depositOption" id="depositOption" value="${prepayment}" />
                    <div class="mt-4">
                        <div class="mt-3">
                            <label for="paymentMethod" class="form-label">Hình thức thanh toán</label>
                            <select class="form-select" name="paymentMethod" id="paymentMethodSelect" required>
                                <option value="" disabled selected>--- Chọn phương thức ---</option>
                                <c:forEach var="pay" items="${payments}">
                                    <option value="${pay.paymentId}">${pay.paymentType}</option>
                                </c:forEach>
                            </select>
                        </div>


                        <div class="mt-4 text-center">
                            <div class="mt-4 text-center">
                                <button type="button" class="btn btn-success btn-lg" onclick="handlePayment()">
                                    <i class="bi bi-credit-card"></i> Xác nhận thanh toán   
                                </button>
                                <button type="button" class="btn btn-outline-primary btn-lg" onclick="window.print()">
                                    <i class="bi bi-printer me-1"></i> In hóa đơn
                                </button>
                            </div>

                        </div>
                    </div>
                </form>
            </div>
        </div>
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                const paymentMethodSelect = document.querySelector('select[name="paymentMethod"]');
                const qrModal = new bootstrap.Modal(document.getElementById('qrModal'));

                paymentMethodSelect.addEventListener("change", function () {
                    if (this.value === "1") {
                        qrModal.show();
                    }
                });
            });
            function handlePayment() {
                const selectedMethod = document.getElementById('paymentMethodSelect').value;
                const form = document.getElementById('paymentForm');

                if (!selectedMethod) {
                    alert("Vui lòng chọn phương thức thanh toán!");
                    return;
                }

                if (selectedMethod === "1") {
                    // Chuyển khoản (QR)
                    form.action = "qrpayment";
                    form.method = "get";
                } else {
                    // Tiền mặt hoặc hình thức khác
                    form.action = "totalAmout";
                }


                form.submit();
            }
        </script>

        <script>
            document.addEventListener("DOMContentLoaded", function () {
                const radioButtons = document.querySelectorAll('input[name="prepayOption"]');
                const totalAmount = parseFloat("${booking.totalAmount}");

                const prepayPercentEl = document.getElementById('prepayPercent');
                const prepayAmountEl = document.getElementById('prepayAmount');
                const remainingAmountEl = document.getElementById('remainingAmount');
                const depositInput = document.getElementById('depositAmountInput');
                const depositOptionInput = document.getElementById('depositOption');

                function formatCurrency(amount) {
                    return new Intl.NumberFormat('vi-VN').format(amount) + 'đ';
                }

                function updateAmounts(ratio) {
                    const prepay = totalAmount * ratio;
                    const remain = totalAmount - prepay;

                    prepayPercentEl.innerText = ratio * 100;
                    prepayAmountEl.innerText = formatCurrency(prepay);
                    remainingAmountEl.innerText = formatCurrency(remain);
                    depositInput.value = prepay;
                    depositOptionInput.value = ratio;
                    remainingAmountInput.value = remain;
                }


                // Gán sự kiện khi người dùng đổi radio
                radioButtons.forEach(radio => {
                    radio.addEventListener('change', function () {
                        updateAmounts(parseFloat(this.value));
                    });
                });

                // 🔥 Gọi cập nhật mặc định ban đầu theo radio đang được checked
                const defaultRadio = document.querySelector('input[name="prepayOption"]:checked');
                if (defaultRadio) {
                    updateAmounts(parseFloat(defaultRadio.value));
                }
            });
        </script>


        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
