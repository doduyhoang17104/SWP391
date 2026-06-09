<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Room" %>
<%@ page import="dal.RoomDAO" %>
<%@ page import="model.Food" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    List<Food> foodList = (List<Food>) request.getAttribute("foodList");
    String keyword = (String) request.getAttribute("keyword");
    Integer currentPage = (Integer) request.getAttribute("currentPage");
    Integer totalPages = (Integer) request.getAttribute("totalPages");
    if (currentPage == null) currentPage = 1;
%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <title>Room</title>
        <link href="https://cdn.jsdelivr.net/npm/simple-datatables@latest/dist/style.css" rel="stylesheet" />
        <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet" />
        <link href="css/style-room.css" rel="stylesheet" type="text/css"/>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/js/all.min.js" crossorigin="anonymous"></script>
        <style>
            .block-27 ul {
                padding: 0;
                margin: 0 auto;
                display: inline-block;
                list-style: none;
            }

            .block-27 ul li {
                display: inline-block;
                margin: 0 4px;
            }

            .block-27 ul li a {
                display: block;
                padding: 8px 14px;
                border: 1px solid #ccc;
                background: #fff;
                color: #333;
                text-decoration: none;
                border-radius: 4px;
                transition: all 0.2s ease-in-out;
            }

            .block-27 ul li a:hover {
                background: #007bff;
                color: white;
                border-color: #007bff;
            }

            .block-27 ul li.active a {
                background: #007bff;
                color: #fff;
                border-color: #007bff;
                pointer-events: none;
            }
        </style>
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
                        <div class="card shadow rounded">
                            <div class="card-header bg-dark text-white d-flex justify-content-between align-items-center">
                                <h4 class="mb-0">🍽️ Quản lý món ăn</h4>
                                <a href="addfood" class="btn btn-light">➕ Thêm món Ăn</a>
                            </div>


                            <div class="card-body">
                                <form class="row g-3 mb-4" method="get" action="managerfood">
                                    <!-- Tên món -->
                                    <div class="col-md-4">
                                        <input type="text" name="keyword" class="form-control" placeholder="🔍 Tên món ăn..."
                                               value="<%= keyword != null ? keyword : "" %>">
                                    </div>

                                    <!-- Danh mục -->
                                    <div class="col-md-3">
                                        <select class="form-select" name="category">
                                            <option value="">-- Tất cả danh mục --</option>
                                            <option value="Đồ ăn" <%= "Đồ ăn".equals(request.getParameter("category")) ? "selected" : "" %>>Đồ ăn</option>
                                            <option value="Đồ uống" <%= "Đồ uống".equals(request.getParameter("category")) ? "selected" : "" %>>Đồ uống</option>
                                        </select>
                                    </div>

                                    <!-- Giá từ -->
                                    <div class="col-md-2">
                                        <input type="number" name="priceFrom" class="form-control" placeholder="Giá từ"
                                               value="<%= request.getParameter("priceFrom") != null ? request.getParameter("priceFrom") : "" %>">
                                    </div>

                                    <!-- Giá đến -->
                                    <div class="col-md-2">
                                        <input type="number" name="priceTo" class="form-control" placeholder="Giá đến"
                                               value="<%= request.getParameter("priceTo") != null ? request.getParameter("priceTo") : "" %>">
                                    </div>

                                    <!-- Nút tìm -->
                                    <div class="col-md-1 d-grid">
                                        <button type="submit" class="btn btn-outline-primary">Tìm</button>
                                    </div>
                                </form>


                                <div class="table-responsive">
                                    <table class="table table-bordered table-hover align-middle text-center bg-white">
                                        <thead class="table-info">
                                            <tr>
                                                <th>ID</th>
                                                <th>Tên món</th>
                                                <th>Giá</th>
                                                <th>Danh mục</th>
                                                <th>Trạng thái</th>
                                                <th>Hình ảnh</th>
                                                <th>Hành động</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <%
                                                if (foodList != null && !foodList.isEmpty()) {
                                                    for (Food food : foodList) {
                                                        String formattedPrice = String.format("%,.3f", food.getPrice()).replace(",", ".") + " VNĐ";
                                            %>
                                            <tr>
                                                <td><%= food.getId() %></td>
                                                <td><%= food.getName() %></td>
                                                <td class="text-danger fw-bold"><%= formattedPrice %></td>
                                                <td><%= food.getCategory() %></td>
                                                <td>
                                                    <span class="badge bg-<%= food.isAvailability() ? "success" : "secondary" %>">
                                                        <%= food.isAvailability() ? "Còn hàng" : "Hết hàng" %>
                                                    </span>
                                                </td>
                                                <td><img src="<%= food.getImage() %>" width="90" class="img-thumbnail"></td>
                                                <td>
                                                    <a href="viewfood?id=<%= food.getId() %>" class="btn btn-info btn-sm mb-1">Xem</a>
                                                    <a href="editfood?id=<%= food.getId() %>" class="btn btn-warning btn-sm mb-1">Sửa</a>
                                                    <a href="deletefood?id=<%= food.getId() %>" class="btn btn-danger btn-sm"
                                                       onclick="return confirm('Bạn có chắc chắn muốn xóa món này không?')">Xóa</a>
                                                </td>
                                            </tr>
                                            <% }
                                    } else { %>
                                            <tr>
                                                <td colspan="7" class="text-center text-muted">
                                                    <% if (keyword != null && !keyword.isEmpty()) { %>
                                                    Không tìm thấy món ăn cho từ khóa "<strong><%= keyword %></strong>"
                                                    <% } else { %>
                                                    Không có dữ liệu món ăn.
                                                    <% } %>
                                                </td>
                                            </tr>
                                            <% } %>
                                        </tbody>
                                    </table>
                                </div>

                                <nav class="d-flex justify-content-center">
                                    <ul class="pagination">
                                        <% for (int i = 1; i <= totalPages; i++) {
                                            String active = (i == currentPage) ? "active" : "";
                                        %>
                                        <li class="page-item <%= active %>">
                                            <a class="page-link"
                                               href="managerfood?page=<%= i %>
                                               <%= keyword != null ? "&keyword=" + keyword : "" %>
                                               <%= request.getParameter("category") != null ? "&category=" + request.getParameter("category") : "" %>
                                               <%= request.getParameter("priceFrom") != null ? "&priceFrom=" + request.getParameter("priceFrom") : "" %>
                                               <%= request.getParameter("priceTo") != null ? "&priceTo=" + request.getParameter("priceTo") : "" %>">
                                                <%= i %>
                                            </a>                                                                                   
                                        </li>
                                        <% } %>
                                    </ul>
                                </nav>
                            </div>
                        </div>
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
