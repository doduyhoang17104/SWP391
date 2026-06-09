<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Thanh toán đặt phòng</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
        <style>
            body {
                background-color: #f2f4f6;
                font-size: 1.1rem;
            }
            .card-custom {
                background: #e3f2fd;
                border-radius: 20px;
                padding: 30px;
                box-shadow: 0 6px 24px rgba(0,0,0,0.08);
                border: 1px solid #90caf9;
            }
            .section-title {
                font-size: 1.4rem;
                font-weight: 600;
                color: #2c3e50;
                margin-bottom: 24px;
            }
            .form-label {
                font-weight: 600;
                font-size: 1rem;
            }
            .form-control, .form-select {
                font-size: 1.05rem;
                padding: 12px 16px;
                border-radius: 10px;
            }
            .btn-lg {
                padding: 14px 22px;
                font-size: 1.1rem;
                border-radius: 12px;
            }
            .accordion-button {
                border-radius: 10px;
            }
            .accordion-button:not(.collapsed) {
                background-color: #bbdefb;
                color: #0d47a1;
            }
            .accordion-body {
                padding-left: 1.5rem;
            }
            .voucher-section {
                background-color: #fff8e1;
                padding: 1rem;
                border-radius: 12px;
                border: 1px solid #ffe082;
            }
            #voucherInfo {
                background-color: #fff3cd !important;
                color: #856404;
                border: 1px solid #ffeeba;
            }
        </style>
    </head>
    <body>
        <div class="container py-5">
            <nav class="navbar navbar-light bg-light border-bottom py-2 px-3">
                <div class="container-fluid d-flex justify-content-between align-items-center">
                    <span class="navbar-brand fw-semibold">
                        👤 ${user.name}
                    </span>
                    <span class="text-muted small">
                        📞 ${user.phone} | ✉️ ${user.email}
                    </span>
                </div>
            </nav>

            <h2 class="text-center mb-5 text-primary fw-bold">
                <i class="bi bi-cash-coin me-2"></i> Xác nhận thông tin đặt phòng
            </h2>

            <form action="invoice" method="POST" class="row g-5">
                <!-- Thông tin khách hàng và dịch vụ -->
                <div class="col-lg-7">
                    <!-- Thông tin khách hàng -->
                    <div class="card-custom">
                        <div class="section-title"><i class="bi bi-person-lines-fill me-2"></i>Xác nhận thông tin của bạn</div>
                        <div class="row g-4">
<!--                            <div class="col-md-6"><label class="form-label">Họ tên</label><input type="text" name="name" class="form-control" value="${user.name}" required></div>
                            <div class="col-md-6"><label class="form-label">Số điện thoại</label><input type="tel" name="phone" class="form-control" value="${user.phone}" required ></div>
                            <div class="col-md-6"><label class="form-label">Email</label><input type="email" name="email" class="form-control" value="${user.email}" required></div>-->
                            <div class="col-md-6"><label class="form-label">Ngày nhận phòng</label><input type="date" name="checkIn" class="form-control" value="${checkIn}" readonly></div>
                            <div class="col-md-6"><label class="form-label">Ngày trả phòng</label><input type="date" name="checkOut" class="form-control" id="checkOut" value="${checkOut}" readonly></div>
                            <div class="col-md">
                                <label class="form-label">Yêu cầu đặc biệt</label>
                                <textarea class="form-control" name="specialRequest" value=""  rows="3" placeholder="Nhập yêu cầu của bạn">${specialRequest}</textarea>
                            </div>
                        </div>
                    </div>
                    <!-- Dịch vụ đi kèm -->
                    <div class="card-custom mt-4">
                        <div class="section-title mb-3"><i class="bi bi-box-seam me-2 text-secondary"></i> Dịch vụ đi kèm</div>
                        <c:forEach var="cat" items="${categorizedServices}">
                            <div class="accordion mb-2" id="accordionService${cat.serviceCategoryId}">
                                <div class="accordion-item border-0">
                                    <h2 class="accordion-header" id="heading${cat.serviceCategoryId}">
                                        <button class="accordion-button collapsed bg-light fw-semibold text-dark"
                                                type="button"
                                                data-bs-toggle="collapse"
                                                data-bs-target="#collapse${cat.serviceCategoryId}"
                                                aria-expanded="false"
                                                aria-controls="collapse${cat.serviceCategoryId}">
                                            <i class="bi bi-folder2-open me-2 text-primary"></i> ${cat.serviceCategoryName}
                                        </button>
                                    </h2>
                                    <div id="collapse${cat.serviceCategoryId}" class="accordion-collapse collapse"
                                         aria-labelledby="heading${cat.serviceCategoryId}">
                                        <div class="accordion-body">
                                            <c:forEach var="s" items="${cat.services}">
                                                <div class="form-check ps-2">
                                                    <input class="form-check-input service-checkbox"
                                                           type="checkbox"
                                                           name="services"
                                                           value="${s.serviceId}:${s.price}"
                                                           data-price="${s.price}"
                                                           id="service${s.serviceId}"
                                                           onchange="updateTotal()"
                                                           <c:if test="${fn:contains(selectedServiceNames, s.serviceName)}">checked</c:if>>


                                                           <label class="form-check-label w-100 d-flex justify-content-between align-items-center" for="service${s.serviceId}">
                                                        <span>${s.serviceName}</span>
                                                        <span class="badge bg-light text-danger fw-semibold shadow-sm px-3">
                                                            +<fmt:formatNumber value="${s.price}" type="number" groupingUsed="true"/>₫
                                                        </span>
                                                    </label>

                                                </div>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>

                <!-- Thông tin phòng & thanh toán -->
                <div class="col-lg-5">
                    <div class="card-custom mb-4">
                        <div class="section-title"><i class="bi bi-house-fill me-2 text-success"></i>Phòng đã chọn</div>
                        <ul class="list-group list-group-flush fs-5">
                            <li class="list-group-item d-flex justify-content-between">
                                <span><i class="bi bi-door-open-fill text-primary me-1"></i>Loại:</span>
                                <strong>${room.roomType.typeName}</strong>
                            </li>
                            <li class="list-group-item d-flex justify-content-between">
                                <span><i class="bi bi-cash-stack text-success me-1"></i>Giá/đêm:</span>
                                <strong><fmt:formatNumber value="${room.roomType.basePrice}" type="number" groupingUsed="true"/>₫</strong>
                            </li>
                            <li class="list-group-item d-flex justify-content-between">
                                <span><i class="bi bi-people-fill text-warning me-1"></i>Số người:</span>
                                <strong>${room.capacity}</strong>
                            </li>
                            <li class="list-group-item d-flex justify-content-between">
                                <span>🛏️ Số giường:</span>
                                <strong>${room.bed}</strong>
                            </li>
                            <li class="list-group-item d-flex justify-content-between">
                                <span><i class="bi bi-moon-fill text-info me-1"></i>Số đêm:</span>
                                <strong id="nightCount">1</strong>
                            </li>
                        </ul>
                        <div class="mt-3">
                            <label class="form-label">Số người:</label>
                            <c:set var="capText" value="${room.capacity}" />
                            <c:set var="capNumber" value="${fn:replace(capText, ' người lớn', '')}" />
                            <input type="number" name="numPeople" class="form-control w-100" value="${capNumber}" min="1" oninput="updateTotal()">
                            <div id="peopleLimitNote" class="text-danger fw-semibold mt-1" style="font-size: 0.95rem;"></div>
                        </div>

                        <input type="hidden" name="roomId" value="${room.roomId}" />

                        <!-- Chọn voucher -->
                        <div class="mt-4">
                            <div class="voucher-section mt-4">

                                <label class="form-label fw-semibold">
                                    <i class="bi bi-ticket-perforated me-1 text-warning"></i> Áp dụng Voucher
                                </label>
                                <select class="form-select" id="voucherSelect" name="voucherId" onchange="updateTotal()">
                                    <option value="0" data-type="none" data-amount="0">-- Không áp dụng --</option>
                                    <c:forEach var="v" items="${listP}">
                                        <option value="${v.promotionId}"
                                                data-type="${v.discountPercent <= 100 ? 'percent' : 'cash'}"
                                                data-amount="${v.discountPercent}"
                                                data-qty="${v.quantity}"
                                                data-start="${v.startDate}"
                                                data-end="${v.endDate}">
                                            ${v.promotionName}
                                            (<c:choose>
                                                <c:when test="${v.discountPercent <= 100}">Giảm ${v.discountPercent}%</c:when>
                                                <c:otherwise>Giảm <fmt:formatNumber value="${v.discountPercent}" type="number" groupingUsed="true"/>đ</c:otherwise>
                                            </c:choose>
                                            - còn lại ${v.quantity} lượt)
                                        </option>

                                    </c:forEach>
                                </select>
                            </div>

                            <div id="voucherInfo" class="alert alert-warning mt-3 py-2 px-3 fs-6 fw-semibold d-flex align-items-center gap-2" style="display: none;">
                                <i class="bi bi-info-circle-fill me-1"></i>
                                <span id="voucherText">Chọn để áp dụng mã giảm giá</span>
                            </div>

                            <!-- Đổi điểm thành tiền -->
                            <label class="form-label fw-semibold">
                                <i class="bi bi-stars me-1 text-primary"></i> Đổi điểm thành viên
                            </label>
                            <div class="input-group">
                                <input type="number"
                                       class="form-control"
                                       id="pointsUsed"
                                       name="pointsUsed"
                                       min="0"
                                       max="${user.points}"
                                       value="${user.points}"
                                       oninput="updateTotal()">
                                <span class="input-group-text">điểm</span>
                            </div>
                            <div class="alert alert-light border d-flex align-items-center gap-2 p-2 mt-2" id="pointsInfo" style="font-size: 0.95rem;">
                                <i class="bi bi-star-fill text-warning"></i>
                                <span>
                                    Bạn có <strong class="text-primary">${user.points}</strong> điểm.
                                    <span class="text-muted">Mỗi điểm = <strong>10.000đ</strong></span>
                                </span>
                            </div>

                        </div>


                        <!-- Tổng tiền -->
                        <div class="d-flex justify-content-between align-items-center border-top pt-3 mt-4">
                            <span class="fw-semibold text-muted fs-5">Tổng cộng:</span>
                            <span class="fs-4 text-danger fw-bold" id="totalAmount">${room.roomType.basePrice}</span>
                        </div>
                        <input type="hidden" name="totalAmount" id="totalAmountInput" value="2400000" />
                        <input type="hidden" name="extraPeopleFee" id="extraPeopleFeeInput" value="0" />
                        <input type="hidden" name="selectedServices" id="selectedServicesInput" value="" />
                        <input type="hidden" name="voucherDiscount" id="voucherDiscountInput" value="0" />
                        <input type="hidden" name="pointsUsedAmount" id="pointsUsedAmountInput" value="0" />
                        <input type="hidden" name="numNights" id="numNightsInput" value="1" />
                    </div>

                    <div class="card-custom">
                        <div class="section-title"><i class="bi bi-credit-card me-2 text-info"></i>Phương thức thanh toán</div>
                        <select class="form-select mb-4">
                            <option>Chuyển khoản ngân hàng</option>
                        </select>
                        <label class="form-label fw-semibold"><i class="bi bi-percent me-1 text-success"></i>Chọn mức thanh toán</label>
                        <div class="d-flex gap-3 mb-4">
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="paymentRate" id="pay50" value="50" checked onchange="updateTotal()">
                                <label class="form-check-label" for="pay50">
                                    Thanh toán trước 50%
                                </label>
                            </div>
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="paymentRate" id="pay100" value="100" onchange="updateTotal()">
                                <label class="form-check-label" for="pay100">
                                    Thanh toán 100%
                                </label>
                            </div>
                        </div>

                        <button type="submit" class="btn btn-success w-100 btn-lg">
                            <i class="bi bi-check-circle me-1"></i> Xác nhận & Thanh toán
                        </button>
                        <div class="mt-4">
                            <a href="roomDetail?roomId=${room.roomId}&checkin=${checkIn}&checkout=${checkOut}" class="btn btn-outline-primary w-100 btn-lg">
                                <i class="bi bi-arrow-left-circle me-1"></i> Quay lại phòng
                            </a>
                        </div>
                    </div>
                </div>
            </form>
        </div>
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                const checkInInput = document.querySelector('input[name="checkIn"]');
                const checkOutInput = document.querySelector('input[name="checkOut"]');

                checkInInput.addEventListener("change", function () {
                    const checkInDate = checkInInput.value;
                    checkOutInput.min = checkInDate;
                    if (checkOutInput.value < checkInDate) {
                        checkOutInput.value = checkInDate;
                    }
                    updateTotal();
                });

                checkOutInput.addEventListener("change", updateTotal);

                updateTotal();

                document.querySelectorAll(".service-checkbox").forEach(cb => {
                    cb.addEventListener("change", updateTotal);
                });

                document.getElementById("voucherSelect").addEventListener("change", updateTotal);
                document.getElementById("pointsUsed").addEventListener("input", updateTotal);
                document.querySelectorAll('input[name="paymentRate"]').forEach(radio => {
                    radio.addEventListener("change", updateTotal);
                });

                const form = document.querySelector("form");
                if (form) {
                    form.addEventListener("submit", function () {
                        updateTotal();
                    });
                }
            });

            const originalRoomPrice = ${room.roomType.basePrice};
            const baseCapacity = ${capNumber}; // từ DB, ví dụ 2, 4, 6
            const maxExtraPeople = 2;
            const maxTotalPeople = baseCapacity + maxExtraPeople;
            const extraFeePerPerson = 200000;

            function updateTotal() {
                const checkIn = new Date(document.querySelector('input[name="checkIn"]').value);
                const checkOut = new Date(document.querySelector('input[name="checkOut"]').value);

                let numNights = 1;
                if (!isNaN(checkIn.getTime()) && !isNaN(checkOut.getTime()) && checkOut > checkIn) {
                    const diffTime = checkOut - checkIn;
                    numNights = Math.ceil(diffTime / (1000 * 60 * 60 * 24));
                }
                document.getElementById("nightCount").innerText = numNights;
                document.getElementById("numNightsInput").value = numNights;

                // Dịch vụ
                let serviceTotal = 0;
                let selectedServices = [];
                document.querySelectorAll('.service-checkbox:checked').forEach(cb => {
                    const price = parseInt(cb.getAttribute("data-price"));
                    const id = cb.value;
                    if (!isNaN(price) && id) {
                        serviceTotal += price;
                        selectedServices.push(`${id}:${price}`);
                                    }
                                });

                                // Số người & phụ phí
                                let numPeople = parseInt(document.querySelector('input[name="numPeople"]').value) || 0;
                                const peopleLimitNote = document.getElementById("peopleLimitNote");

                                if (numPeople > maxTotalPeople) {
                                    numPeople = maxTotalPeople;
                                    document.querySelector('input[name="numPeople"]').value = maxTotalPeople;
                                    peopleLimitNote.innerText = `⚠️ Đã đạt giới hạn tối đa số người ở của phòng!`;
                                } else {
                                    peopleLimitNote.innerText = "";
                                }

                                let extraPeopleFee = 0;
                                if (numPeople > baseCapacity) {
                                    const excess = numPeople - baseCapacity;
                                    extraPeopleFee = excess * extraFeePerPerson;
                                    const feeNoteText = `⚠️ Bạn đã vượt quá số người tối đa. Phụ phí: +200.000đ/người`;
                                    const existingNote = document.getElementById("extraFeeNote");
                                    if (!existingNote) {
                                        const feeNote = document.createElement("div");
                                        feeNote.id = "extraFeeNote";
                                        feeNote.className = "alert alert-warning mt-2 py-2 px-3 fs-6 fw-semibold";
                                        feeNote.innerHTML = feeNoteText;
                                        document.querySelector(".card-custom.mb-4")?.appendChild(feeNote);
                                    } else {
                                        existingNote.innerHTML = feeNoteText;
                                    }
                                } else {
                                    const existingNote = document.getElementById("extraFeeNote");
                                    if (existingNote)
                                        existingNote.remove();
                                }

                                // Voucher
                                const voucherSelect = document.getElementById("voucherSelect");
                                const selectedOption = voucherSelect.options[voucherSelect.selectedIndex];
                                const type = selectedOption.getAttribute("data-type");
                                const amount = parseFloat(selectedOption.getAttribute("data-amount")) || 0;
                                const quantity = parseInt(selectedOption.getAttribute("data-qty")) || 0;
                                const startDateStr = selectedOption.getAttribute("data-start");
                                const endDateStr = selectedOption.getAttribute("data-end");
                                const info = document.getElementById("voucherInfo");
                                const text = document.getElementById("voucherText");
                                info.classList.remove("d-none");

                                let discount = 0;
                                const now = new Date();
                                const startDate = new Date(startDateStr);
                                const endDate = new Date(endDateStr);
                                const roomTotalBeforeDiscount = originalRoomPrice * numNights;

                                if (type === "percent" || type === "cash") {
                                    if (quantity <= 0) {
                                        text.innerText = "⚠️ Mã giảm giá đã hết lượt sử dụng.";
                                    } else if (now < startDate || now > endDate) {
                                        text.innerText = "⚠️ Mã giảm giá hiện không còn hiệu lực.";
                                    } else {
                                        if (type === "percent") {
                                            discount = roomTotalBeforeDiscount * (amount / 100);
                                            text.innerText = `✅ Giảm ${amount}% trên tổng tiền phòng (${numNights} đêm)`;
                                        } else {
                                            discount = Math.min(roomTotalBeforeDiscount, amount);
                                            text.innerText = `✅ Đã áp dụng giảm ${amount.toLocaleString('vi-VN')}đ`;
                                        }
                                    }
                                } else {
                                    text.innerText = "Không áp dụng mã giảm giá";
                                }

                                // Tổng tiền
                                const roomSubtotal = roomTotalBeforeDiscount - discount;
                                const rawTotalBeforePoints = roomSubtotal + serviceTotal + extraPeopleFee;

                                // Điểm
                                const pointsInput = document.getElementById("pointsUsed");
                                const pointsInfo = document.getElementById("pointsInfo");
                                const userPoints = parseInt(pointsInput.getAttribute("max")) || 0;

                                let pointDiscount = 0;
                                if (rawTotalBeforePoints < 0) {
                                    pointsInput.value = 0;
                                    pointsInput.disabled = true;
                                    pointDiscount = 0;
                                    pointsInfo.innerHTML = `<i class="bi bi-star-fill text-warning"></i> <span>🎉 Đơn hàng đã được giảm 100%, không cần dùng điểm nữa.</span>`;
                                } else {
                                    pointsInput.disabled = false;
                                    let points = parseInt(pointsInput.value) || 0;
                                    const maxUsablePoints = Math.floor(rawTotalBeforePoints / 10000);
                                    if (points > maxUsablePoints) {
                                        points = maxUsablePoints;
                                        pointsInput.value = maxUsablePoints;
                                    }
                                    pointDiscount = points * 10000;
                                }

                                let finalTotal = rawTotalBeforePoints - pointDiscount;
                                finalTotal = Math.max(finalTotal, 0);

                                // Gửi dữ liệu về form
                                document.getElementById("totalAmount").innerText = new Intl.NumberFormat('vi-VN').format(finalTotal) + "đ";
                                document.getElementById("totalAmountInput").value = finalTotal;
                                document.getElementById("extraPeopleFeeInput").value = extraPeopleFee;
                                document.getElementById("voucherDiscountInput").value = discount;
                                document.getElementById("pointsUsedAmountInput").value = pointDiscount;
                                document.getElementById("selectedServicesInput").value = selectedServices.join(",");
                            }
        </script>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
