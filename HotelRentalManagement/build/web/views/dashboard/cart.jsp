<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Food" %>
<%@ page import="dal.BookingDAO" %>

<%
    // Lấy bookingId nếu chưa có trong session
    if (session.getAttribute("bookingId") == null) {
        BookingDAO bookingDAO = new BookingDAO();
        Integer activeBookingId = bookingDAO.getActiveBookingId();

        if (activeBookingId != null) {
            session.setAttribute("bookingId", activeBookingId);
        } else {
            response.sendRedirect("views/dashboard/booking.jsp?error=noActiveBooking");
            return;
        }
    }

    List<Food> cart = (List<Food>) session.getAttribute("cart");
    String success = request.getParameter("success");
    String context = request.getContextPath();
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Giỏ hàng</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container mt-5">
    <h2 class="text-center mb-4">🛒 Giỏ hàng của bạn</h2>

    <% if ("true".equals(success)) { %>
        <div class="alert alert-success text-center">
            ✅ Đặt hàng thành công! Đơn hàng đang chờ xử lý.
        </div>
    <% } else if ("false".equals(success)) { %>
        <div class="alert alert-danger text-center">
            ❌ Có lỗi xảy ra khi đặt hàng. Vui lòng thử lại.
        </div>
    <% } %>

    <% if (cart != null && !cart.isEmpty()) { 
        double total = 0;
    %>
        <table class="table table-bordered table-hover bg-white">
            <thead class="table-primary text-center">
                <tr>
                    <th>ID</th>
                    <th>Tên món</th>
                    <th>Giá</th>
                    <th>Xóa</th>
                </tr>
            </thead>
            <tbody>
                <% for (Food f : cart) { 
                    total += f.getPrice(); %>
                    <tr class="text-center">
                        <td><%= f.getId() %></td>
                        <td><%= f.getName() %></td>
                        <td><%= String.format("%,.3f", f.getPrice()).replace(",", ".") %> VNĐ</td>
                        <td>
                            <a href="<%= context %>/removecart?id=<%= f.getId() %>" class="btn btn-sm btn-danger">Xóa</a>
                        </td>
                    </tr>
                <% } %>
            </tbody>
            <tfoot class="table-secondary text-center">
                <tr>
                    <th colspan="2">Tổng cộng</th>
                    <th colspan="2"><%= String.format("%,.3f", total).replace(",", ".") %> VNĐ</th>
                </tr>
            </tfoot>
        </table>

        <form action="<%= context %>/checkoutfood" method="post" class="text-center mt-4">
            <input type="submit" value="🧾 Đặt hàng" class="btn btn-success px-4">
        </form>

    <% } else if (!"true".equals(success)) { %>
        <div class="alert alert-warning text-center">Giỏ hàng của bạn đang trống.</div>
    <% } %>

    <div class="text-center mt-3">
        <a href="<%= context %>/listfood" class="btn btn-secondary">↩️ Tiếp tục mua hàng</a>
    </div>
</div>
</body>
</html>
