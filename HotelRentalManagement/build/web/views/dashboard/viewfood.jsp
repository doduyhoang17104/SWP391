<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Food" %>
<%
    Food food = (Food) request.getAttribute("food");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Chi tiết món ăn</title>
    <meta charset="UTF-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .price {
            color: red;
            font-size: 22px;
            font-weight: bold;
        }
    </style>
</head>
<body>
<div class="container py-5">
    <% if (food == null) { %>
        <div class="alert alert-danger">Không tìm thấy thông tin món ăn.</div>
    <% } else { %>
        <div class="row">
            <!-- Hình ảnh món ăn -->
            <div class="col-md-5 mb-4">
                <img src="<%= food.getImage() %>" class="img-fluid rounded shadow-sm" alt="Ảnh món ăn">
            </div>

            <!-- Thông tin món ăn -->
            <div class="col-md-7">
                <h2>Tên món: <%= food.getName() %></h2>
                <p>Mô tả: <%= food.getDescription() %></p>
                <p class="price">Giá: <%= String.format("%,.0f", food.getPrice()) %>.000 VNĐ</p>
                <p>Danh mục: <%= food.getCategory() %></p>
                <p>Trạng thái: <%= food.isAvailability() ? "Còn hàng" : "Hết hàng" %></p>

                <a href="restaurant" class="btn btn-secondary mt-3">🔙 Trở lại danh sách</a>
            </div>
        </div>
    <% } %>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
