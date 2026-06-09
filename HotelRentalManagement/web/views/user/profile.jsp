<%@ page import="model.User" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login");
        return;
    }
    request.setAttribute("activePage", "updateprofile");
%>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Thông tin người dùng</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
    </head>
    <style>
        .loyalty-card {
            background: linear-gradient(135deg, #fff3cd, #ffeeba);
            border: 2px solid #ffdd57;
            color: #333;
            font-size: 20px;
            padding: 15px 25px;
            border-radius: 12px;
            display: inline-flex;
            align-items: center;
            gap: 12px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
            transition: transform 0.2s ease;
        }

        .loyalty-card:hover {
            transform: scale(1.03);
        }

        .loyalty-card i {
            font-size: 24px;
        }

        .loyalty-card span.text-primary {
            color: #0d6efd;
            font-size: 22px;
        }

        .loyalty-card span.fw-bold {
            font-size: 21px;
        }
    </style>

    <body class="bg-light">

        <div class="container py-5">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <a href="<%=request.getContextPath()%>/home" class="btn btn-outline-secondary">
                    <i class="bi bi-arrow-left-circle"></i> Quay về trang chính
                </a>

                <h2 class="text-primary"><i class="bi bi-person-circle"></i> Thông tin cá nhân</h2>


            </div>

            <div class="row g-4">
                <div class="col-md-3">
                    <%@ include file="menu-profile.jsp" %>
                </div>
                <div class="col-md-9">
                    <div class="card shadow-sm">
                        <form action="updateprofile" method="POST" enctype="multipart/form-data">
                            <div class="card-body">
                                <div class="loyalty-card shadow-sm p-3 rounded d-inline-block mt-2">
                                    <i class="bi bi-star-fill text-warning"></i>
                                    <span class="fw-bold">Điểm tích lũy:</span>
                                    <span class="text-primary"><%= user.getPoints() %> điểm</span>
                                </div>
                                <div class="text-center mb-4">
                                    <%
                                        String imageFile = (user.getImage() != null && !user.getImage().isEmpty())
                                                ? request.getContextPath() + "/images/avatar/" + user.getImage()
                                                : "https://dongvat.edu.vn/upload/2025/03/anh-dai-dien-facebook-mac-dinh-005.webp";
                                    %>
                                    <img id="avatar-preview"
                                         src="<%= imageFile %>"
                                         alt="Avatar"
                                         class="rounded-circle border shadow"
                                         style="width: 140px; height: 140px; object-fit: cover;">


                                    <div class="mt-2">
                                        <label class="btn btn-outline-primary">
                                            <i class="bi bi-upload"></i> Tải ảnh mới
                                            <input type="file" name="avatar" hidden onchange="previewAvatar(this)">
                                        </label>
                                    </div>
                                </div>

                                <input type="hidden" name="id" value="<%= user.getId() %>" />

                                <div class="mb-3">
                                    <label class="form-label"><i class="bi bi-person-badge"></i> Tên đăng nhập</label>
                                    <input type="text" class="form-control" value="<%= user.getUsername() %>" readonly>
                                </div>

                                <div class="mb-3">
                                    <label class="form-label"><i class="bi bi-envelope-at"></i> Email</label>
                                    <input type="text" class="form-control" value="<%= user.getEmail() %>" readonly>
                                </div>

                                <div class="mb-3">
                                    <label class="form-label"><i class="bi bi-person-lines-fill"></i> Họ và tên</label>
                                    <input type="text" name="name" class="form-control" value="<%= user.getName() %>">
                                </div>

                                <div class="mb-3">
                                    <label class="form-label"><i class="bi bi-telephone-forward"></i> Số điện thoại</label>
                                    <input type="text" name="phone" class="form-control" value="<%= user.getPhone() %>">
                                </div>

                                <div class="mb-3">
                                    <label class="form-label"><i class="bi bi-geo-alt"></i> Địa chỉ</label>
                                    <input type="text" name="address" class="form-control" value="<%= user.getAddress() %>">
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

        <script>
            function previewAvatar(input) {
                const file = input.files[0];
                if (file) {
                    const reader = new FileReader();
                    reader.onload = function (e) {
                        document.getElementById('avatar-preview').src = e.target.result;
                    };
                    reader.readAsDataURL(file);
                }
            }
        </script>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>