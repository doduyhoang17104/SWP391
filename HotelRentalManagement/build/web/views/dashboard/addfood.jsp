<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Thêm món ăn</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f0f0f0;
                padding: 20px;
            }
            .form-container {
                background-color: #fff;
                padding: 30px;
                border-radius: 8px;
                max-width: 600px;
                margin: auto;
                box-shadow: 0 0 10px rgba(0,0,0,0.1);
            }
            h2 {
                color: #333;
                margin-bottom: 20px;
            }
            label {
                display: block;
                margin-top: 10px;
                font-weight: bold;
            }
            input[type="text"],
            input[type="number"],
            textarea,
            select {
                width: 100%;
                padding: 10px;
                margin-top: 5px;
                border-radius: 4px;
                border: 1px solid #ccc;
            }
            input[type="submit"] {
                margin-top: 20px;
                background-color: #28a745;
                color: white;
                border: none;
                padding: 10px 20px;
                border-radius: 4px;
                cursor: pointer;
            }
            input[type="submit"]:hover {
                background-color: #218838;
            }
        </style>
    </head>
    <body>
        <div class="form-container">
            <h2>Thêm món ăn mới</h2>
            <form action="addfood" method="post">
                <label>Tên món:</label>
                <input type="text" name="name" required>

                <label>Mô tả:</label>
                <textarea name="description" required></textarea>

                <label>Giá:</label>
                <input type="number" name="price" step="0.01" required>

                <label>Danh mục:</label>
                <select name="category" required>
                    <option value="food">Đồ ăn</option>
                    <option value="drink">Đồ uống</option>
                </select>

                <label>Tình trạng:</label>
                <select name="availability">
                    <option value="true">Còn hàng</option>
                    <option value="false">Hết hàng</option>
                </select>

                <label>URL hình ảnh:</label>
                <input type="text" name="image">

                <input type="submit" value="Thêm món">
            </form>
        </div>
    </body>
</html>
