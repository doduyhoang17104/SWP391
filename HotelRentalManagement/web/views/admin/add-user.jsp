<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Thêm người dùng mới</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f8f9fa;
            }

            .main-content {
                margin-left: 250px;
                padding: 30px;
            }

            .card-form {
                background-color: #fff;
                border-radius: 8px;
                box-shadow: 0 2px 6px rgba(0,0,0,0.05);
                padding: 20px;
            }

            .btn-primary {
                background-color: #8A2BE2;
                border-color: #8A2BE2;
            }

            .btn-primary:hover {
                background-color: #6A1B9A;
                border-color: #6A1B9A;
            }

            .form-label {
                font-weight: bold;
            }
        </style>
    </head>
    <body>
        <%@include file="menu-admin.jsp" %>

        <div class="main-content">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h2><i class="fas fa-user-plus me-2"></i> Thêm người dùng mới</h2>
            </div>

            <div class="card card-form">
                <c:if test="${not empty errorMessage}">
                    <div class="alert alert-danger">${errorMessage}</div>
                </c:if>

                <c:if test="${not empty msg}">
                    <div class="alert alert-info">${msg}</div>
                </c:if>

                <form action="${pageContext.request.contextPath}/adduser" method="post" accept-charset="UTF-8">
                    <div class="row g-3">
                        <div class="col-md-6">
                            <label for="fullName" class="form-label">Họ tên:</label>
                            <input type="text" id="fullName" name="fullName" class="form-control" required>
                        </div>

                        <div class="col-md-6">
                            <label for="username" class="form-label">Tên đăng nhập:</label>
                            <input type="text" id="username" name="username" class="form-control" required>
                        </div>

                        <div class="col-md-6">
                            <label for="password" class="form-label">Mật khẩu:</label>
                            <input type="password" id="password" name="password" class="form-control" required>
                        </div>

                        <div class="col-md-6">
                            <label for="repassword" class="form-label">Xác nhận mật khẩu:</label>
                            <input type="password" id="repassword" name="repassword" class="form-control" required>
                        </div>

                        <div class="col-md-6">
                            <label for="email" class="form-label">Email:</label>
                            <input type="email" id="email" name="email" class="form-control" pattern="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$" required>
                        </div>

                        <div class="col-md-6">
                            <label for="phone" class="form-label">Số điện thoại:</label>
                            <input type="text" id="phone" name="phone" class="form-control" pattern="0\d{9}" required>
                        </div>

                        <div class="col-12">
                            <label for="address" class="form-label">Địa chỉ:</label>
                            <input type="text" id="address" name="address" class="form-control">
                        </div>

                        <div class="col-md-6">
                            <label for="roleId" class="form-label">Vai trò:</label>
                            <select id="roleId" name="roleId" class="form-select" required>
                                <option value="" disabled selected>Chọn vai trò</option>
                                <option value="1" ${user.roleid == 1 ? 'selected' : ''}>Khách hàng</option>
                                <option value="3" ${user.roleid == 3 ? 'selected' : ''}>Quản lý</option>
                                <option value="4" ${user.roleid == 4 ? 'selected' : ''}>Lễ tân</option>
                            </select>
                        </div>
                    </div>

                    <div class="mt-4 text-end">
                        <button type="submit" class="btn btn-primary me-2">
                            <i class="fas fa-check-circle me-1"></i> Thêm
                        </button>
                        <a href="${pageContext.request.contextPath}/listuser" class="btn btn-secondary">
                            <i class="fas fa-times-circle me-1"></i> Hủy
                        </a>
                    </div>
                </form>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
