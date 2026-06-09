<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Gửi phản hồi</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            body {
                background-color: #f4f6f8;
                padding-top: 50px;
            }

            .card {
                border-radius: 12px;
                box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
            }

            .form-label {
                font-weight: 600;
            }

            .star-rating {
                direction: rtl;
                display: flex;
                justify-content: start;
                font-size: 24px;
            }

            .star-rating input[type="radio"] {
                display: none;
            }

            .star-rating label {
                color: #ccc;
                cursor: pointer;
                transition: color 0.2s;
            }

            .star-rating input[type="radio"]:checked ~ label,
            .star-rating label:hover,
            .star-rating label:hover ~ label {
                color: #ffc107;
            }
        </style>
    </head>
    <body>

        <div class="container">
            <div class="row justify-content-center">
                <div class="col-lg-8 col-md-10">
                    <div class="card p-4">
                        <img src="https://i.pinimg.com/originals/47/59/74/475974873af7b3ba92c8050d918d4f9f.gif" alt="Phản hồi thành công" class="success-gif">

                        <h2 class="text-center mb-4 text-primary">📝 Gửi phản hồi của bạn</h2>
                        <form method="post" action="feedback">

                            <!-- Đánh giá tổng thể -->
                            <div class="mb-3">
                                <label class="form-label">Đánh giá tổng thể:</label>
                                <div class="star-rating">
                                    <input type="radio" name="overallRating" id="overall5" value="5"><label for="overall5">★</label>
                                    <input type="radio" name="overallRating" id="overall4" value="4"><label for="overall4">★</label>
                                    <input type="radio" name="overallRating" id="overall3" value="3"><label for="overall3">★</label>
                                    <input type="radio" name="overallRating" id="overall2" value="2"><label for="overall2">★</label>
                                    <input type="radio" name="overallRating" id="overall1" value="1" required><label for="overall1">★</label>
                                </div>
                            </div>

                            <!-- Đánh giá dịch vụ -->
                            <div class="mb-3">
                                <label class="form-label">Đánh giá dịch vụ:</label>
                                <div class="star-rating">
                                    <input type="radio" name="serviceRating" id="service5" value="5"><label for="service5">★</label>
                                    <input type="radio" name="serviceRating" id="service4" value="4"><label for="service4">★</label>
                                    <input type="radio" name="serviceRating" id="service3" value="3"><label for="service3">★</label>
                                    <input type="radio" name="serviceRating" id="service2" value="2"><label for="service2">★</label>
                                    <input type="radio" name="serviceRating" id="service1" value="1" required><label for="service1">★</label>
                                </div>
                            </div>

                            <!-- Thời gian lưu trú -->
                            <div class="mb-3">
                                <label for="rentalPeriod" class="form-label">Thời gian lưu trú:</label>
                                <input type="text" class="form-control" name="rentalPeriod" id="rentalPeriod" required>
                            </div>

                            <!-- Bình luận -->
                            <div class="mb-3">
                                <label for="comment" class="form-label">Bình luận:</label>
                                <textarea class="form-control" name="comment" id="comment" rows="5" required></textarea>
                            </div>


                            <!-- Nút gửi -->
                            <div class="text-center mt-4">
                                <button type="submit" class="btn btn-primary px-4">Gửi phản hồi</button>
                                <a href="home" class="btn btn-secondary px-4 ms-2">Quay lại</a>
                            </div>

                        </form>
                    </div>
                </div>
            </div>
        </div>

        <!-- Bootstrap JS (tùy chọn) -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
