<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
    <title>Phản hồi thành công</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f4f6f8;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        .container {
            background-color: #fff;
            padding: 40px 50px;
            border-radius: 12px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
            text-align: center;
        }

        h2 {
            color: #2ecc71;
            margin-bottom: 20px;
        }

        /* Thêm CSS cho ảnh GIF */
        .success-gif {
            width: 150px; /* Điều chỉnh kích thước ảnh GIF theo ý muốn */
            height: auto;
            margin-bottom: 25px; /* Khoảng cách dưới ảnh GIF */
        }

        a {
            display: inline-block;
            margin-top: 20px;
            padding: 10px 20px;
            background-color: #3498db;
            color: white;
            text-decoration: none;
            border-radius: 6px;
            font-weight: bold;
            transition: background-color 0.3s ease;
        }

        a:hover {
            background-color: #2c80b4;
        }
    </style>
</head>
<body>

    <div class="container">
        <img src="https://i.pinimg.com/originals/f6/79/31/f6793115422c292a8193d43a2b66fd40.gif" alt="Phản hồi thành công" class="success-gif">

        <h2>🎉 Cảm ơn bạn đã gửi phản hồi!</h2>
        <a href="feedback.jsp">Gửi phản hồi khác</a>
        <a href="home">Quay về trang chính</a>
    </div>

</body>
</html>