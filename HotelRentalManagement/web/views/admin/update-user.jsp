<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Cập nhật User</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css" integrity="sha512-SnH5WK+bZxgPHs44uWIX+LLJAJ9/2PkPKZ5QiAj6Ta86w+fsb2TkcmfRyVX3pBnMFcV7oQPJkl9QevSCWr3W6A==" crossorigin="anonymous" referrerpolicy="no-referrer" />
        <style>
            :root {
                --sidebar-width: 250px;
                --navbar-height: 60px;
                --primary-purple: #8A2BE2; /* Màu tím bạn thấy trong ảnh */
                --dark-background: #212529; /* Màu nền sidebar và navbar */
                --text-light: #f8f9fa;
            }

            body {
                font-family: Arial, sans-serif;
                margin: 0;
                background-color: #f0f2f5; /* Nền tổng thể nhẹ nhàng */
                display: flex; /* Dùng flexbox để layout sidebar và content */
                min-height: 100vh;
            }

            /* Sidebar Styles (Copy từ user-list.jsp) */
            .sidebar {
                width: var(--sidebar-width);
                background-color: var(--dark-background);
                color: var(--text-light);
                padding: 20px 0;
                display: flex;
                flex-direction: column;
                position: fixed; /* Giữ sidebar cố định */
                height: 100vh; /* Chiếm toàn bộ chiều cao màn hình */
                left: 0;
                top: 0;
                z-index: 1000;
                box-shadow: 2px 0 5px rgba(0,0,0,0.2);
            }
            .sidebar .company-logo {
                text-align: center;
                margin-bottom: 30px;
                font-size: 1.8rem;
                font-weight: bold;
                color: #fff;
                padding: 10px 0;
            }
            .sidebar .company-logo .icon {
                color: var(--primary-purple); /* Màu icon */
            }
            .sidebar .section-title {
                padding: 10px 20px;
                color: #adb5bd;
                font-size: 0.85rem;
                text-transform: uppercase;
                letter-spacing: 1px;
                margin-top: 20px;
            }
            .sidebar .nav-link {
                display: flex;
                align-items: center;
                padding: 12px 20px;
                color: #ced4da;
                text-decoration: none;
                transition: background-color 0.3s ease, color 0.3s ease;
            }
            .sidebar .nav-link i {
                margin-right: 15px;
                font-size: 1.1rem;
            }
            .sidebar .nav-link:hover,
            .sidebar .nav-link.active {
                background-color: rgba(255, 255, 255, 0.1);
                color: #fff;
            }
            .sidebar .nav-link.active {
                border-left: 3px solid var(--primary-purple); /* Đường kẻ active */
                padding-left: 17px; /* Điều chỉnh padding cho phù hợp */
            }
            .sidebar .nav-item.has-submenu .nav-link::after {
                content: "\f105"; /* Font Awesome right arrow */
                font-family: "Font Awesome 6 Free";
                font-weight: 900;
                margin-left: auto;
                transition: transform 0.3s ease;
            }
            .sidebar .nav-item.has-submenu.show .nav-link::after {
                transform: rotate(90deg);
            }
            .sidebar .submenu {
                list-style: none;
                padding-left: 0;
                margin-top: 5px;
                background-color: rgba(0,0,0,0.15); /* Nền submenu */
                display: none; /* Ẩn mặc định */
            }
            .sidebar .submenu li a {
                display: block;
                padding: 8px 20px 8px 50px; /* Thụt lề cho submenu item */
                color: #adb5bd;
                text-decoration: none;
                transition: background-color 0.3s ease, color 0.3s ease;
            }
            .sidebar .submenu li a:hover {
                background-color: rgba(255, 255, 255, 0.05);
                color: #fff;
            }
            .sidebar .nav-item.show .submenu {
                display: block; /* Hiện submenu khi có class show */
            }
            .sidebar-footer {
                margin-top: auto; /* Đẩy footer xuống dưới cùng */
                padding: 20px;
                font-size: 0.8rem;
                color: #6c757d;
            }

            /* Main Content Area */
            .main-content {
                margin-left: var(--sidebar-width); /* Để lại khoảng trống cho sidebar */
                flex-grow: 1; /* Chiếm hết phần còn lại */
                display: flex;
                flex-direction: column;
            }

            /* Navbar Styles (Copy từ user-list.jsp) */
            .navbar-top {
                background-color: #fff;
                height: var(--navbar-height);
                display: flex;
                align-items: center;
                justify-content: space-between;
                padding: 0 20px;
                box-shadow: 0 2px 5px rgba(0,0,0,0.1);
                position: sticky;
                top: 0;
                z-index: 999;
            }
            .navbar-top .search-bar {
                flex-grow: 1;
                margin: 0 20px;
            }
            .navbar-top .search-bar .input-group {
                max-width: 400px; /* Giới hạn chiều rộng thanh tìm kiếm */
                margin-left: auto; /* Căn phải trong khu vực tìm kiếm */
            }
            .navbar-top .search-bar .form-control {
                border-radius: 20px;
                padding-left: 20px;
                border-color: #e9ecef;
            }
            .navbar-top .search-bar .btn-outline-secondary {
                border-radius: 20px;
                border-left: none;
                border-color: #e9ecef;
            }
            .navbar-top .right-icons .btn {
                font-size: 1.2rem;
                color: #6c757d;
                margin-left: 15px;
            }

            /* Content Area for Form */
            .content-wrapper {
                padding: 20px;
                flex-grow: 1;
                display: flex;
                justify-content: center; /* Căn giữa form theo chiều ngang */
                align-items: flex-start; /* Căn đầu form theo chiều dọc */
            }

            /* Form Container Styles (Điều chỉnh từ bản cũ) */
            .form-container {
                max-width: 600px;
                width: 100%; /* Đảm bảo form chiếm đủ chiều rộng trong max-width */
                background-color: #ffffff;
                padding: 30px;
                border-radius: 8px;
                box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
                margin-top: 30px; /* Khoảng cách từ navbar */
                margin-bottom: 30px; /* Khoảng cách tới footer */
            }
            h2 {
                color: #0056b3; /* Màu xanh đậm cho tiêu đề */
                font-weight: 600;
                text-align: center;
                border-bottom: 2px solid #e9ecef;
                padding-bottom: 15px;
                margin-bottom: 30px;
            }
            .form-label {
                font-weight: bold; /* Nhãn input đậm hơn */
            }
            /* Nút chính dùng màu tím từ biến CSS */
            .btn-primary {
                background-color: var(--primary-purple);
                border-color: var(--primary-purple);
                transition: all 0.3s ease;
            }
            .btn-primary:hover {
                background-color: #6A1B9A; /* Tím đậm hơn khi hover */
                border-color: #6A1B9A;
                transform: translateY(-2px);
            }
            .btn-secondary {
                background-color: #6c757d;
                border-color: #6c757d;
                transition: all 0.3s ease;
            }
            .btn-secondary:hover {
                background-color: #5a6268;
                border-color: #5a6268;
                transform: translateY(-2px);
            }
            .error-message {
                color: #dc3545; /* Màu đỏ cho thông báo lỗi */
                font-weight: bold;
                text-align: center;
                margin-bottom: 20px;
            }

            /* Footer (Copy từ user-list.jsp) */
            .app-footer {
                background-color: #fff;
                padding: 15px 20px;
                text-align: center;
                font-size: 0.85rem;
                color: #6c757d;
                border-top: 1px solid #e9ecef;
                margin-top: auto; /* Đẩy footer xuống cuối */
            }
        </style>
    </head>
    <body>
        <%@include file="menu-admin.jsp" %>
        <div class="main-content">
            <nav class="navbar-top">
                <button class="btn btn-light d-lg-none" type="button" data-bs-toggle="offcanvas" data-bs-target="#sidebarOffcanvas" aria-controls="sidebarOffcanvas">
                    <i class="fas fa-bars"></i>
                </button>
                <div class="search-bar">
                    <div class="input-group">
                        <input type="text" class="form-control" placeholder="Search here...">
                        <button class="btn btn-outline-secondary" type="button"><i class="fas fa-search"></i></button>
                    </div>
                </div>
                <div class="right-icons">
                    <button class="btn btn-light"><i class="fas fa-bell"></i></button>
                    <button class="btn btn-light"><i class="fas fa-moon"></i></button>
                    <button class="btn btn-light"><i class="fas fa-user-circle"></i></button>
                    <button class="btn btn-light d-lg-none"><i class="fas fa-bars"></i></button>
                </div>
            </nav>

            <div class="content-wrapper">
                <div class="form-container">
                    <h2 class="text-center">
                        <i class="fas fa-user-edit me-2"></i> Cập nhật User
                    </h2>

                    <c:if test="${not empty errorMessage}">
                        <div class="alert alert-danger error-message" role="alert">
                            ${errorMessage}
                        </div>
                    </c:if>

                    <form action="edituser" method="POST" accept-charset="UTF-8">
                        <input type="hidden" name="userId" value="${user.id}" />

                        <div class="mb-3">
                            <label for="fullName" class="form-label">Họ Tên:</label>
                            <input type="text" id="fullName" name="fullName" value="${user.name}" class="form-control" required/>
                        </div>

                        <div class="mb-3">
                            <label for="username" class="form-label">Tên đăng nhập</label>
                            <input type="text" id="username" name="username" value="${user.username}" class="form-control" required/>
                        </div>
                        <div class="mb-3">
                            <label for="email" class="form-label">Email:</label>
                            <input type="email" id="email" name="email" value="${user.email}" class="form-control" pattern="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$" title="Email không hợp lệ" required />
                        </div>

                        <div class="mb-3">
                            <label for="phoneNumber" class="form-label">Số điện thoại:</label>
                            <input type="text" id="phone" name="phone" value="${user.phone}" class="form-control" pattern="\d{10}" title="Phone number phải đủ 10 số" required />
                        </div>

                        <div class="mb-3">
                            <label for="address" class="form-label">Địa chỉ</label>
                            <input type="text" id="address" name="address" value="${user.address}" class="form-control" />
                        </div>
                        <div class="mb-3">
                            <label for="status" class="form-label">Trạng thái:</label>
                            <select id="status" name="status" class="form-select" required>
                                <option value="" disabled ${user.status != 0 && user.status != 1 ? 'selected' : ''}>Chọn trạng thái</option>
                                <option value="1" ${user.status == 1 ? 'selected' : ''}>Mở</option>
                                <option value="0" ${user.status == 0 ? 'selected' : ''}>Khóa</option>
                            </select>
                        </div>
                        <div class="mb-3">
                            <label for="roleId" class="form-label">Role:</label>

                            <c:choose>
                                <c:when test="${user.roleid == 2}">
                                    <select id="roleId" name="roleId" class="form-select" disabled>
                                        <option value="2" selected>Admin</option>
                                    </select>
                                    <input type="hidden" name="roleId" value="2" />
                                </c:when>
                                <c:otherwise>
                                    <select id="roleId" name="roleId" class="form-select" required>
                                        <option value="" disabled ${empty user.roleid ? 'selected' : ''}>Chọn role</option>
                                        <option value="1" ${user.roleid == 1 ? 'selected' : ''}>Customer</option>
                                        <option value="3" ${user.roleid == 3 ? 'selected' : ''}>Manager</option>
                                        <option value="4" ${user.roleid == 4 ? 'selected' : ''}>Receptionist</option>
                                    </select>
                                </c:otherwise>
                            </c:choose>
                        </div>

                        <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                            <button type="submit" class="btn btn-primary me-md-2">
                                <i class="fas fa-save me-2"></i> Cập nhật
                            </button>
                            <a href="${pageContext.request.contextPath}/listuser" class="btn btn-secondary">
                                <i class="fas fa-times-circle me-2"></i> Hủy
                            </a>
                        </div>
                    </form>
                </div>
            </div>

            <footer class="app-footer">
                Copyright Ajs Developed by DesignZone 2023
            </footer>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
        <script>
            // JavaScript để toggle submenu
            document.querySelectorAll('.sidebar .nav-item.has-submenu').forEach(item => {
                item.addEventListener('click', function (e) {
                    if (e.target.tagName === 'A' && e.target.classList.contains('nav-link')) {
                        e.preventDefault();
                    }
                    this.classList.toggle('show');
                });
            });
        </script>
    </body>
</html>