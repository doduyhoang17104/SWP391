<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Chi tiết đánh giá</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet"/>
        <style>
            .rating-stars {
                color: #ffc107;
                font-size: 1.3rem;
            }
            .label {
                font-weight: 600;
                color: #343a40;
            }
            .value {
                font-size: 1rem;
                color: #555;
            }
            .card {
                border-left: 5px solid #198754;
            }
        </style>
    </head>
    <body>
        <div class="container mt-5">
            <h2 class="mb-4 text-success"><i class="fa-solid fa-comment-dots"></i> Chi tiết đánh giá</h2>

            <div class="card shadow-sm">
                <div class="card-body">
                    <div class="row mb-3">
                        <div class="col-sm-4 label">Mã đặt phòng</div>
                        <div class="col-sm-8 value"><strong>#${fRoom.bookingId}</strong></div>
                    </div>
                    <div class="row mb-3">
                        <div class="col-sm-4 label">Số phòng</div>
                        <div class="col-sm-8 value"><strong>${fRoom.roomNumber}</strong></div>
                    </div>

                    <div class="row mb-3">
                        <div class="col-sm-4 label">Người đánh giá</div>
                        <div class="col-sm-8 value">${fRoom.authorName}</div>
                    </div>

                    <div class="row mb-3">
                        <div class="col-sm-4 label">Điểm đánh giá</div>
                        <div class="col-sm-8 value">
                            <span class="rating-stars">${fRoom.rating} <i class="fa-solid fa-star"></i></span>
                        </div>
                    </div>
                    <div class="row mb-3">
                        <div class="col-sm-4 label">Ngày đánh giá</div>
                        <div class="col-sm-8 value">
                            <fmt:formatDate value="${fRoom.createAt}" pattern="dd/MM/yyyy"/>
                        </div>
                    </div>
                    <div class="row mb-3">
                        <div class="col-sm-4 label">Ngày sửa đánh giá</div>
                        <div class="col-sm-8 value">
                            <fmt:formatDate value="${fRoom.updateAt}" pattern="dd/MM/yyyy"/>
                        </div>
                    </div>

                    <div class="row mb-3">
                        <div class="col-sm-4 label">Thời gian lưu trú</div>
                        <div class="col-sm-8 value">
                            <fmt:formatDate value="${fRoom.checkIn}" pattern="dd/MM/yyyy" /> - 
                            <fmt:formatDate value="${fRoom.checkOut}" pattern="dd/MM/yyyy" />
                        </div>
                    </div>

                    <div class="row mb-3">
                        <div class="col-sm-4 label">Nội dung đánh giá</div>
                        <div class="col-sm-8 value">
                            <em>${fRoom.content}</em>
                        </div>
                    </div>
                </div>

            </div>

            <div class="mt-4">
                <a href="feedbackroommanagement" class="btn btn-outline-secondary">
                    <i class="fa-solid fa-arrow-left"></i> Quay lại danh sách
                </a>
            </div>
        </div>
    </body>
</html>
