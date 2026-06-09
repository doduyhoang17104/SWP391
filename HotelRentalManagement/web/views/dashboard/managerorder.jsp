<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Room" %>
<%@ page import="dal.RoomDAO" %>
<%@ page import="model.FoodOrder" %>
<%@ page import="model.FoodOrderDetail" %>
<%@ page import="model.Food" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    List<FoodOrder> orders = (List<FoodOrder>) request.getAttribute("orders");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <title>Quản lý đơn món ăn</title>
    <link href="https://cdn.jsdelivr.net/npm/simple-datatables@latest/dist/style.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet" />
    <link href="css/style-room.css" rel="stylesheet" type="text/css"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/js/all.min.js" crossorigin="anonymous"></script>
</head>
<body class="sb-nav-fixed">
<nav class="sb-topnav navbar navbar-expand navbar-dark bg-dark">
    <%@ include file="header.jsp" %>
</nav>

<div id="layoutSidenav">
    <%@ include file="menu-manager.jsp" %>

    <div id="layoutSidenav_content">
        <main>
            <div class="container mt-5">
                <h2 class="mb-4 text-center">📦 Danh sách đơn món ăn</h2>

                <% if (orders != null && !orders.isEmpty()) {
                    for (FoodOrder order : orders) {
                %>
                <div class="card mb-4 shadow-sm">
                    <div class="card-header d-flex justify-content-between align-items-center">
                        <div>
                            <strong>Đơn hàng #<%= order.getId() %></strong> -
                            Trạng thái:
                            <span class="badge
                                  <% if(order.getStatus().equals("Đang chờ xử lý")) { %> bg-secondary
                                  <% } else if(order.getStatus().equals("Đang làm")) { %> bg-warning
                                  <% } else if(order.getStatus().equals("Đã xong")) { %> bg-success
                                  <% } else { %> bg-danger <% } %>">
                                <%= order.getStatus() %>
                            </span>
                        </div>

                        <div class="d-flex gap-2">
                            <% if (order.getStatus().equals("Đang chờ xử lý")) { %>
                                <form method="post" action="updateorderstatus">
                                    <input type="hidden" name="orderId" value="<%= order.getId() %>">
                                    <input type="hidden" name="newStatus" value="Đang làm">
                                    <button type="submit" class="btn btn-sm btn-primary">Duyệt đơn</button>
                                </form>
                                <form method="post" action="updateorderstatus">
                                    <input type="hidden" name="orderId" value="<%= order.getId() %>">
                                    <input type="hidden" name="newStatus" value="Đã huỷ">
                                    <button type="submit" class="btn btn-sm btn-danger">Huỷ</button>
                                </form>
                            <% } else if (order.getStatus().equals("Đang làm")) { %>
                                <form method="post" action="updateorderstatus">
                                    <input type="hidden" name="orderId" value="<%= order.getId() %>">
                                    <input type="hidden" name="newStatus" value="Đã xong">
                                    <button type="submit" class="btn btn-sm btn-success">Hoàn thành</button>
                                </form>
                                <form method="post" action="updateorderstatus">
                                    <input type="hidden" name="orderId" value="<%= order.getId() %>">
                                    <input type="hidden" name="newStatus" value="Đã huỷ">
                                    <button type="submit" class="btn btn-sm btn-danger">Huỷ</button>
                                </form>
                            <% } %>
                        </div>
                    </div>

                    <div class="card-body">
                        <ul class="list-group mb-2">
                            <% for (FoodOrderDetail d : order.getDetails()) { %>
                            <li class="list-group-item d-flex justify-content-between">
                                <span><strong><%= d.getFood().getName() %></strong> - SL: <%= d.getQuantity() %></span>
                                <span><%= String.format("%,.0f", d.getPrice()) %> VNĐ</span>
                            </li>
                            <% } %>
                        </ul>
                        <% if (order.getNote() != null && !order.getNote().isEmpty()) { %>
                            <div>
                                <strong>📝 Ghi chú:</strong> <%= order.getNote() %>
                            </div>
                        <% } %>
                    </div>
                </div>
                <% }
                } else { %>
                    <div class="alert alert-info text-center">
                        Chưa có đơn hàng nào được đặt.
                    </div>
                <% } %>
            </div>
        </main>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
<script src="${pageContext.request.contextPath}/js/scripts.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js" crossorigin="anonymous"></script>
<script src="assets/demo/chart-area-demo.js"></script>
<script src="assets/demo/chart-bar-demo.js"></script>

</body>
</html>
