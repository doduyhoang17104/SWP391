<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Food" %>
<%
    String context = request.getContextPath();
%>

<%
    List<Food> foodList = (List<Food>) request.getAttribute("foodList");
    int currentPage = request.getAttribute("currentPage") != null ? (Integer) request.getAttribute("currentPage") : 1;
    int totalPages = request.getAttribute("totalPages") != null ? (Integer) request.getAttribute("totalPages") : 1;
    String keyword = request.getParameter("keyword") != null ? request.getParameter("keyword") : "";
%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Danh sách món ăn</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            body {
                background-color: #f8f9fa;
            }
            .card-header {
                font-size: 1.2rem;
                font-weight: bold;
            }
            .table th, .table td {
                text-align: center;
                vertical-align: middle;
            }
            .pagination .page-item.active .page-link {
                background-color: #007bff;
                border-color: #007bff;
            }
            .pagination .page-link {
                border-radius: 50%;
                transition: background-color 0.3s ease;
            }
            .pagination .page-link:hover {
                background-color: #007bff;
                color: white;
            }
            .btn-custom {
                transition: background-color 0.3s ease, color 0.3s ease;
            }
            .btn-custom:hover {
                background-color: #0056b3;
                color: white;
            }
            .table tbody tr:hover {
                background-color: #f1f1f1;
            }
        </style>
    </head>
    <body>
        <div class="container mt-5">
            <div class="card shadow-sm rounded">
                <div class="card-header bg-primary text-white d-flex justify-content-between align-items-center">
                    <a href="<%= context %>/restaurant" class="btn btn-light btn-sm">⬅ Quay lại</a>
                    <h4 class="mb-0">🍽️ Danh sách các món ăn</h4>
                </div>


                <div class="card-body">
                    <!-- Thanh tìm kiếm -->
                    <form class="row g-3 mb-4" method="get" action="<%= context %>/listfood">
                        <!-- Tên món -->
                        <div class="col-md-4">
                            <input type="text" name="keyword" class="form-control" placeholder="🔍 Tìm tên món..."
                                   value="<%= request.getParameter("keyword") != null ? request.getParameter("keyword") : "" %>">
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
                            <input type="number" name="priceFrom" class="form-control" placeholder="Giá từ..."
                                   value="<%= request.getParameter("priceFrom") != null ? request.getParameter("priceFrom") : "" %>">
                        </div>

                        <!-- Giá đến -->
                        <div class="col-md-2">
                            <input type="number" name="priceTo" class="form-control" placeholder="Giá đến..."
                                   value="<%= request.getParameter("priceTo") != null ? request.getParameter("priceTo") : "" %>">
                        </div>

                        <!-- Nút tìm -->
                        <div class="col-md-1 d-grid">
                            <button type="submit" class="btn btn-outline-primary">Tìm</button>
                        </div>

                        <div class="">
                            <a href="listfood" class="btn btn-warning">Tất cả</a>
                        </div>
                    </form>



                    <!-- Danh sách món ăn -->
                    <div class="table-responsive">
                        <table class="table table-bordered table-hover text-center align-middle">
                            <thead class="table-primary">
                                <tr>
                                    <th>ID</th>
                                    <th>Tên món</th>
                                    <th>Giá</th>
                                    <th>Danh mục</th>
                                    <th>Trạng thái</th>
                                    <th>Hình ảnh</th>
                                    <th>Thao tác</th>
                                </tr>
                            </thead>
                            <tbody>
                                <% if (foodList != null && !foodList.isEmpty()) {
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
                                    <td><img src="<%= food.getImage() %>" width="80" class="img-thumbnail"></td>
                                    <td>
                                        <a href="<%= context %>/viewfood?id=<%= food.getId() %>" class="btn btn-sm btn-info mb-1 btn-custom">Xem</a>

                                    </td>

                                </tr>
                                <% }
                                } else { %>
                                <tr><td colspan="7" class="text-center text-muted">Không có món ăn nào để hiển thị.</td></tr>
                                <% } %>
                            </tbody>
                        </table>
                    </div>

                    <!-- Phân trang -->
                    <nav class="d-flex justify-content-center">
                        <ul class="pagination">
                            <% for (int i = 1; i <= totalPages; i++) {
                                String active = (i == currentPage) ? "active" : "";
                            %>
                            <li class="page-item <%= active %>">
                                <a class="page-link" href="<%= context %>/listfood?page=<%= i %><%= keyword != null ? "&keyword=" + keyword : "" %>">
                                    <%= i %>
                                </a>
                            </li>
                            <% } %>
                        </ul>
                    </nav>
                </div>
            </div>
        </div>

        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
