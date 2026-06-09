<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Thanh toán QR</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet" />
        <style>
            body {
                background-color: #f8f9fa;
            }
            .qr-card {
                max-width: 480px;
                margin: auto;
                border-radius: 15px;
                box-shadow: 0 4px 12px rgba(0,0,0,0.1);
            }
            .qr-header {
                border-top-left-radius: 15px;
                border-top-right-radius: 15px;
            }
            .qr-img {
                max-width: 100%;
                border-radius: 12px;
                margin-bottom: 20px;
            }
            .qr-footer {
                display: flex;
                justify-content: center;
                gap: 15px;
                padding-bottom: 25px;
            }
        </style>
    </head>
    <body>
        <div class="container py-5">
            <div class="card qr-card">
                <div class="card-header bg-primary text-white text-center qr-header">
                    <h4 class="mb-0"><i class="bi bi-qr-code-scan me-2"></i>Quét mã để thanh toán</h4>
                </div>
                <div class="card-body text-center">
                    <p class="fs-5 fw-semibold">Vui lòng sử dụng ứng dụng ngân hàng hoặc ví điện tử</p>
                    <img class="qr-img" src='https://img.vietqr.io/image/MB-0394855685-compact2.jpg?amount=${requestScope.depositAmount}&addInfo=Booking Id ${bookingId}&accountName=DO%20DUY%20HOANG' alt="QR Code"/>
                    <p class="text-muted" style="font-size: 0.9rem;">Quét mã để hoàn tất thanh toán</p>
                </div>
                <div class="qr-footer">
                    <form action="generateInvoice" method="POST">
                        <input type="hidden" name="bookingId" value="${bookingId}" />
                        <input type="hidden" name="totalAmount" value="${totalAmount}" />
                        <input type="hidden" name="depositOption" value="${depositRatio}" />
                        <input type="hidden" name="paymentMethod" value="${paymentId}" />
                        <button type="submit" class="btn btn-success px-4">
                            <i class="bi bi-check-circle me-1"></i> Xác nhận đã thanh toán
                        </button>
                    </form>
                    <!--                <a href="invoiceOffline.jsp" class="btn btn-outline-danger px-4">
                                        <i class="bi bi-x-circle me-1"></i> Huỷ thanh toán
                                    </a>-->
                </div>
            </div>
        </div>
    </body>
</html>
