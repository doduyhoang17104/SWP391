<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.FoodOrder" %>
<%@ page import="model.FoodOrderDetail" %>

<%
    List<FoodOrder> orders = (List<FoodOrder>) request.getAttribute("orders");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Danh sách đơn đặt món</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="container mt-4">

        <h2 class="text-center mb-4">📝 Danh sách món đã đặt</h2>

        <% if (orders != null && !orders.isEmpty()) {
        for (FoodOrder order : orders) { %>
        <div class="card mb-3">
            <div class="card-header">
                <strong>Đơn hàng #: <%= order.getId() %></strong> - 
                Trạng thái: <span class="badge
                                  <%= order.getStatus().equals("Đang chờ xử lý") ? "bg-secondary" 
                                      : order.getStatus().equals("Đang làm") ? "bg-warning text-dark" 
                                      : order.getStatus().equals("Đã xong") ? "bg-success" 
                                      : "bg-danger" %>">
                    <%= order.getStatus() %>
                </span>

            </div>
            <div class="card-body">
                <ul class="list-group list-group-flush">
                    <% for (FoodOrderDetail d : order.getDetails()) { %>
                    <li class="list-group-item d-flex justify-content-between align-items-center">
                        <span>
                            🍽 <strong><%= d.getFood().getName() %></strong> - Số lượng: <%= d.getQuantity() %>
                        </span>
                        <span>
                            <%= String.format("%,.0f", d.getPrice()) %> VNĐ
                        </span>
                    </li>
                    <% } %>
                </ul>
                <% if (order.getNote() != null && !order.getNote().isEmpty()) { %>
                <div class="mt-2"><strong>Ghi chú:</strong> <%= order.getNote() %></div>
                <% } %>
                <div class="text-end mt-3 text-muted">🕒 Thời gian đặt: <%= order.getOrderTime() %></div>
            </div>
        </div>
        <% } 
    } else { %>
        <div class="alert alert-warning text-center">Không có đơn hàng nào được đặt.</div>
        <% } %>

        <div class="text-center mt-4">
            <a href="<%= request.getContextPath() %>/listfood" class="btn btn-primary">⬅ Quay lại menu món ăn</a>
        </div>

    </body>
</html>
