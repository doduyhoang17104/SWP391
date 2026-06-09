<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Food" %>
<%
    Food food = (Food) request.getAttribute("food");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chỉnh sửa món ăn</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container mt-5">
    <div class="card shadow">
        <div class="card-header bg-primary text-white">
            <h4 class="mb-0">✏️ Chỉnh sửa món ăn</h4>
        </div>
        <div class="card-body">
            <form action="editfood" method="post">
                <input type="hidden" name="id" value="<%= food.getId() %>" />

                <div class="mb-3">
                    <label class="form-label">Tên món:</label>
                    <input type="text" name="name" class="form-control" value="<%= food.getName() %>" required>
                </div>

                <div class="mb-3">
                    <label class="form-label">Mô tả:</label>
                    <textarea name="description" class="form-control" required><%= food.getDescription() %></textarea>
                </div>

                <div class="mb-3">
                    <label class="form-label">Giá:</label>
                    <input type="number" step="0.01" name="price" class="form-control" value="<%= food.getPrice() %>" required>
                </div>

                <div class="mb-3">
                    <label class="form-label">Danh mục:</label>
                    <select name="category" class="form-select" required>
                        <option value="Đồ ăn" <%= "Đồ ăn".equals(food.getCategory()) ? "selected" : "" %>>Đồ ăn</option>
                        <option value="Đồ uống" <%= "Đồ uống".equals(food.getCategory()) ? "selected" : "" %>>Đồ uống</option>
                    </select>
                </div>

                <div class="mb-3">
                    <label class="form-label">Tình trạng:</label>
                    <select name="availability" class="form-select">
                        <option value="true" <%= food.isAvailability() ? "selected" : "" %>>Còn hàng</option>
                        <option value="false" <%= !food.isAvailability() ? "selected" : "" %>>Hết hàng</option>
                    </select>
                </div>

                <div class="mb-3">
                    <label class="form-label">URL hình ảnh:</label>
                    <input type="text" name="image" class="form-control" value="<%= food.getImage() %>">
                </div>

                <div class="text-end">
                    <button type="submit" class="btn btn-success px-4">💾 Cập nhật</button>
                    <a href="managerfood" class="btn btn-secondary">↩️ Quay lại</a>
                </div>
            </form>
        </div>
    </div>
</div>

</body>
</html>
