<%@ page import="model.User" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login");
        return;
    }
    request.setAttribute("activePage", "changepassword");
%>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Đổi mật khẩu</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
    </head>
    <body class="bg-light">

        <div class="container py-5">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <a href="<%=request.getContextPath()%>/home" class="btn btn-outline-secondary">
                    <i class="bi bi-arrow-left-circle"></i> Quay về trang chính
                </a>
                <h2 class="text-primary"><i class="bi bi-shield-lock-fill"></i> Đổi mật khẩu</h2>
            </div>

            <div class="row g-4">
                <div class="col-md-3">
                    <%@ include file="menu-profile.jsp" %>
                </div>

                <div class="col-md-9">
                    <div class="card shadow-sm">
                        <form action="changepassword" method="POST">
                            <input type="hidden" name="gmail" value="<%= user.getEmail() %>">
                            <div class="card-body">
                                <div class="mb-3">
                                    <label class="form-label"><i class="bi bi-key"></i> Mật khẩu hiện tại</label>
                                    <input type="password" name="currentPassword" class="form-control" required>
                                </div>
                                <div class="mb-3">
                                    <label class="form-label"><i class="bi bi-lock-fill"></i> Mật khẩu mới</label>
                                    <input type="password" name="newPassword" class="form-control" required>
                                </div>
                                <div class="mb-3">
                                    <label class="form-label"><i class="bi bi-lock"></i> Xác nhận mật khẩu mới</label>
                                    <input type="password" name="repeatPassword" class="form-control" required>
                                </div>

                                <c:if test="${not empty requestScope.msg}">
                                    <div class="alert alert-danger">${msg}</div>
                                </c:if>

                                <div class="text-end">
                                    <button type="submit" class="btn btn-success">
                                        <i class="bi bi-check-circle"></i> Lưu thay đổi
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
