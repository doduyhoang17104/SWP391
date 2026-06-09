<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Gửi phản hồi phòng</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

    </head>
    <body class="bg-light">
        <div class="container py-5">
            <div class="row justify-content-center">
                <div class="col-md-8 col-lg-6">

                    <!-- Card -->
                    <div class="card shadow-lg rounded-4">
                        <div class="card-header bg-primary text-white text-center rounded-top-4">
                            <h4><i class="bi bi-star-fill me-2"></i>Đánh giá phòng</h4>
                        </div>
                        <div class="card-body p-4">
                            <form action="${pageContext.request.contextPath}/feedbackroom" method="POST">
                                <!-- Room ID -->
                                <input type="hidden" name="roomId" value="${param.roomId}" />
                                <input type="hidden" name="bookingId" value="${param.bookingId}" />
                                <!-- Rating -->
                                <div class="mb-3">
                                    <label class="form-label fw-bold">Đánh giá ⭐</label>
                                    <select name="rating" class="form-select" required>
                                        <option value="">-- Chọn sao --</option>
                                        <option value="5">5 - Tuyệt vời</option>
                                        <option value="4">4 - Tốt</option>
                                        <option value="3">3 - Tạm ổn</option>
                                        <option value="2">2 - Chưa hài lòng</option>
                                        <option value="1">1 - Tệ</option>
                                    </select>
                                </div>
                                <!-- Comment -->
                                <div class="mb-3">
                                    <label class="form-label fw-bold">Bình luận</label>
                                    <textarea name="comment" rows="4" class="form-control" placeholder="Viết cảm nhận của bạn..." required></textarea>
                                </div>
                                <!-- Submit -->
                                <div class="d-grid">
                                    <button type="submit" class="btn btn-success">
                                        <i class="bi bi-send-fill"></i> Gửi phản hồi
                                    </button>
                                </div>
                            </form>

                        </div>
                    </div>
                    <!-- End Card -->

                    <!-- Back button -->
                    <div class="text-center mt-3">
                        <a href="${pageContext.request.contextPath}/bookinghistory" class="btn btn-outline-secondary btn-sm">
                            <i class="bi bi-arrow-left-circle"></i> Quay lại lịch sử đặt phòng
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
