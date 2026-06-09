<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%
    String activePage = (String) request.getAttribute("activePage");
    if (activePage == null) {
        activePage = "";
    }
%>

<div class="list-group fs-5">
    <a href="<%=request.getContextPath()%>/updateprofile"
       class="list-group-item list-group-item-action d-flex align-items-center gap-2 <%= "updateprofile".equals(activePage) ? "active" : "" %>">
        <i class="bi bi-person-circle"></i> Thông tin người dùng
    </a>
    <a href="<%=request.getContextPath()%>/changepassword"
       class="list-group-item list-group-item-action d-flex align-items-center gap-2 <%= "changepassword".equals(activePage) ? "active" : "" %>">
        <i class="bi bi-shield-lock-fill"></i> Đổi mật khẩu
    </a>
    <a href="<%=request.getContextPath()%>/bookinghistory"
       class="list-group-item list-group-item-action d-flex align-items-center gap-2 <%= "bookinghistory".equals(activePage) ? "active" : "" %>">
        <i class="bi bi-journal-check"></i> Lịch sử đặt phòng
    </a>
    <a href="<%=request.getContextPath()%>/mybooking"
       class="list-group-item list-group-item-action d-flex align-items-center gap-2 <%= "bookingstatus".equals(activePage) ? "active" : "" %>">
        <i class="bi bi-clock-history"></i> Trạng thái đặt phòng
    </a>

    <a href="<%=request.getContextPath()%>/logout"
       class="list-group-item list-group-item-action d-flex align-items-center gap-2 text-danger">
        <i class="bi bi-box-arrow-right"></i> Đăng xuất
    </a>
</div>
