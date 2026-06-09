<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.OrderItem" %>
<%@ page import="model.Food" %>

<%
    List<OrderItem> orderList = (List<OrderItem>) request.getAttribute("orderList");
    Integer bookingId = (Integer) session.getAttribute("bookingId"); // Lấy từ session
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đơn hàng của bạn</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">
<div class="container mt-5">
    <h2 class="mb-4">Đơn hàng của bạn</h2>

    <% if (orderList != null && !orderList.isEmpty()) { %>
    <form action="checkout" method="post">
        <!-- Truyền bookingId nếu có -->
        <input type="hidden" name="bookingId" value="<%= bookingId != null ? bookingId : "" %>" />

        <table class="table table-bordered table-hover">
            <thead class="table-primary">
            <tr>
                <th>Tên món</th>
                <th>Giá</th>
                <th>Trạng thái</th>
            </tr>
            </thead>
            <tbody>
            <% for (OrderItem item : orderList) {
                Food food = item.getFood();
                String formattedPrice = String.format("%,.0f", food.getPrice()) + " VNĐ";
            %>
                <tr>
                    <td><%= food.getName() %></td>
                    <td><%= formattedPrice %></td>
                    <td><span class="badge bg-warning text-dark"><%= item.getStatus() %></span></td>
                </tr>
            <% } %>
            </tbody>
        </table>

        <button type="submit" class="btn btn-primary">Xác nhận đặt món</button>
        <a href="listfood" class="btn btn-success">Tiếp tục mua sắm</a>
    </form>
    <% } else { %>
    <p class="alert alert-info">Không có món nào trong đơn hàng.</p>
    <a href="listfood" class="btn btn-primary">Quay lại</a>
    <% } %>
</div>
</body>
</html>
