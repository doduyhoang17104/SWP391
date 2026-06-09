<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
    <title>Chi tiết phản hồi</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f4f6f8;
            margin: 0;
            padding: 40px;
            color: #333;
        }

        .container {
            max-width: 800px;
            margin: auto;
            background: #ffffff;
            padding: 30px 40px;
            border-radius: 12px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
        }

        h2 {
            text-align: center;
            color: #2c3e50;
            margin-bottom: 30px;
        }

        .feedback-detail p {
            font-size: 17px;
            margin: 12px 0;
            line-height: 1.6;
        }

        .feedback-detail strong {
            color: #34495e;
        }

        .back-link {
            display: inline-block;
            margin-top: 30px;
            text-decoration: none;
            color: #3498db;
            font-weight: bold;
            transition: color 0.3s;
        }

        .back-link:hover {
            color: #21618c;
        }

        .not-found {
            text-align: center;
            font-size: 18px;
            color: #e74c3c;
        }
    </style>
</head>
<body>

    <div class="container">
        <h2>📄 Chi tiết phản hồi #${feedback.feedbackId}</h2>

        <c:if test="${not empty feedback}">
            <div class="feedback-detail">
                <p><strong>👤 Khách hàng:</strong> ${feedback.userFullName}</p>
                <p><strong>⭐ Đánh giá tổng thể:</strong> ${feedback.overallRating}/5</p>
                <p><strong>🛎️ Đánh giá dịch vụ:</strong> ${feedback.serviceRating}/5</p>
                <p><strong>📅 Thời gian lưu trú:</strong> ${feedback.rentalPeriod}</p>
                <p><strong>💬 Bình luận:</strong> ${feedback.comment}</p>
                <p><strong>🕒 Ngày gửi phản hồi:</strong> ${feedback.createdAt}</p>
            </div>
        </c:if>

        <c:if test="${empty feedback}">
            <p class="not-found">Không tìm thấy phản hồi.</p>
        </c:if>

        <a class="back-link" href="feedbackmanager">← Quay lại danh sách phản hồi</a>
    </div>

</body>
</html>
