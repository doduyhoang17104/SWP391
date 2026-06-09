<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="model.User" %> 
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>List User - Hotel Admin</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css" />
        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 0;
                background-color: #f8f9fa;
            }

            .main-content {
                margin-left: 250px;
                padding: 30px;
            }

            .card-table {
                background-color: #fff;
                border-radius: 8px;
                box-shadow: 0 2px 6px rgba(0,0,0,0.05);
                padding: 20px;
            }

            .table thead th {
                background-color: #e9ecef;
            }

            .avatar {
                width: 40px;
                height: 40px;
                object-fit: cover;
                border-radius: 50%;
                border: 1px solid #dee2e6;
            }

            .action-btns .btn {
                margin-right: 5px;
            }

            .page-header {
                display: flex;
                justify-content: space-between;
                align-items: center;
                margin-bottom: 20px;
            }

            .table-footer-info {
                display: flex;
                justify-content: space-between;
                align-items: center;
                margin-top: 15px;
                font-size: 0.9rem;
                color: #6c757d;
            }

            .pagination .page-link {
                border-radius: 5px;
            }

            .pagination .page-item.active .page-link {
                background-color: #8A2BE2;
                border-color: #8A2BE2;
                color: #fff;
            }
            .card-stat {
                padding: 20px;
                border-radius: 10px;
                color: white;
                display: flex;
                align-items: center;
                gap: 15px;
                box-shadow: 0 3px 10px rgba(0,0,0,0.05);
                transition: transform 0.2s ease;
                height: 100%;
            }
            .card-stat:hover {
                transform: translateY(-3px);
            }
            .stat-icon {
                font-size: 2rem;
            }
            .stat-title {
                font-size: 0.95rem;
                opacity: 0.9;
            }
            .stat-value {
                font-size: 1.5rem;
                font-weight: bold;
            }
            .bg-total      {
                background-color: #6f42c1;
            }
            .bg-active     {
                background-color: #198754;
            }
            .bg-locked     {
                background-color: #dc3545;
            }
            .bg-admin      {
                background-color: #0d6efd;
            }
            .bg-customer   {
                background-color: #20c997;
            }
            .bg-reception  {
                background-color: #fd7e14;
            }
        </style>
    </head>
    <body>
        <%@include file="menu-admin.jsp" %>



        <div class="main-content">
            <div class="page-header">
                <h2>List User</h2>
            </div>
            <form method="get" class="row g-2 mb-3">
                <div class="col">
                    <input type="text" name="name" class="form-control" placeholder="Họ và tên" value="${param.name}">
                </div>
                <div class="col">
                    <input type="text" name="username" class="form-control" placeholder="Tên đăng nhập" value="${param.username}">
                </div>
                <div class="col">
                    <input type="text" name="phone" class="form-control" placeholder="Số điện thoại" value="${param.phone}">
                </div>
                <div class="col">
                    <input type="text" name="email" class="form-control" placeholder="Email" value="${param.email}">
                </div>
                <div class="col">
                    <input type="text" name="address" class="form-control" placeholder="Địa chỉ" value="${param.address}">
                </div>
                <div class="col">
                    <select name="roleid" class="form-select">
                        <option value="">Vai trò</option>
                        <option value="1" ${param.roleid == '1' ? 'selected' : ''}>Customer</option>
                        <option value="2" ${param.roleid == '2' ? 'selected' : ''}>Admin</option>
                        <option value="3" ${param.roleid == '3' ? 'selected' : ''}>Manager</option>
                        <option value="4" ${param.roleid == '4' ? 'selected' : ''}>Receptionist</option>
                    </select>
                </div>
                <div class="col-auto">
                    <button type="submit" class="btn btn-primary">Tìm kiếm</button>
                </div>
            </form>
            <div class="row mb-3">
                <div class="col-md-2">
                    <a href="${pageContext.request.contextPath}/listuser" class="btn btn-secondary w-100">Hiển thị tất cả</a>
                </div>
            </div>
            <jsp:include page="statistics-admin.jsp" />
            <div class="card card-table">
                <div class="table-responsive">
                    <table class="table table-striped align-middle">
                        <thead>
                            <tr>
                                <th>
                                    <a href="?sortBy=id&sortDir=${sortBy eq 'id' && sortDir eq 'asc' ? 'desc' : 'asc'}">
                                        ID
                                        <c:if test="${sortBy eq 'id'}">
                                            <i class="fa fa-sort-${sortDir eq 'asc' ? 'up' : 'down'}"></i>
                                        </c:if>
                                    </a>
                                </th>
                                <th>
                                    <a href="?sortBy=name&sortDir=${sortBy eq 'name' && sortDir eq 'asc' ? 'desc' : 'asc'}">
                                        Họ và tên
                                        <c:if test="${sortBy eq 'name'}">
                                            <i class="fa fa-sort-${sortDir eq 'asc' ? 'up' : 'down'}"></i>
                                        </c:if>
                                    </a>
                                </th>
                                <th>
                                    <a href="?sortBy=username&sortDir=${sortBy eq 'username' && sortDir eq 'asc' ? 'desc' : 'asc'}">
                                        Tên đăng nhập
                                        <c:if test="${sortBy eq 'username'}">
                                            <i class="fa fa-sort-${sortDir eq 'asc' ? 'up' : 'down'}"></i>
                                        </c:if>
                                    </a>
                                </th>
                                <th>Mật khẩu</th>
                                <th>
                                    <a href="?sortBy=phone&sortDir=${sortBy eq 'phone' && sortDir eq 'asc' ? 'desc' : 'asc'}">
                                        Số điện thoại
                                        <c:if test="${sortBy eq 'phone'}">
                                            <i class="fa fa-sort-${sortDir eq 'asc' ? 'up' : 'down'}"></i>
                                        </c:if>
                                    </a>
                                </th>
                                <th>
                                    <a href="?sortBy=email&sortDir=${sortBy eq 'email' && sortDir eq 'asc' ? 'desc' : 'asc'}">
                                        Email
                                        <c:if test="${sortBy eq 'email'}">
                                            <i class="fa fa-sort-${sortDir eq 'asc' ? 'up' : 'down'}"></i>
                                        </c:if>
                                    </a>
                                </th>
                                <th>
                                    <a href="?sortBy=address&sortDir=${sortBy eq 'address' && sortDir eq 'asc' ? 'desc' : 'asc'}">
                                        Địa chỉ
                                        <c:if test="${sortBy eq 'address'}">
                                            <i class="fa fa-sort-${sortDir eq 'asc' ? 'up' : 'down'}"></i>
                                        </c:if>
                                    </a>
                                </th>
                                <th>
                                    <a href="?sortBy=roleid&sortDir=${sortBy eq 'roleid' && sortDir eq 'asc' ? 'desc' : 'asc'}">
                                        Vai trò
                                        <c:if test="${sortBy eq 'roleid'}">
                                            <i class="fa fa-sort-${sortDir eq 'asc' ? 'up' : 'down'}"></i>
                                        </c:if>
                                    </a>
                                </th>
                                <th>Trạng thái</th>
                                <th>Hành động</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:if test="${empty listU}">
                                <tr><td colspan="10" class="text-center">No data available</td></tr>
                            </c:if>
                            <c:forEach var="u" items="${listU}">
                                <tr>
                                    <td>${u.id}</td>
                                    <td>${u.name}</td>
                                    <td>${u.username}</td>
                                    <td>********</td>
                                    <td>${u.phone}</td>
                                    <td>${u.email}</td>
                                    <td>${u.address}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${u.roleid == 1}">Customer</c:when>
                                            <c:when test="${u.roleid == 2}">Admin</c:when>
                                            <c:when test="${u.roleid == 3}">Manager</c:when>
                                            <c:when test="${u.roleid == 4}">Receptionist</c:when>
                                            <c:otherwise>Unknown</c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${u.status == 1}">Mở</c:when>
                                            <c:when test="${u.status == 0}">Khóa</c:when>
                                            <c:otherwise>Không rõ</c:otherwise>
                                        </c:choose>
                                    </td>

                                    <td class="action-btns">
                                        <a href="${pageContext.request.contextPath}/edituser?id=${u.id}" class="btn btn-sm btn-warning" title="Edit"><i class="fas fa-edit"></i></a>
                                        <a href="${pageContext.request.contextPath}/deleteuser?id=${u.id}" class="btn btn-sm btn-danger" onclick="return confirm('Are you sure?');" title="Delete"><i class="fas fa-trash-alt"></i></a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>

                <div class="table-footer-info">
                    <span>Showing ${(currentPage - 1) * pageSize + 1} to ${(currentPage - 1) * pageSize + (listUser.size() > 0 ? listUser.size() : 0)} of ${totalUsers} entries</span>
                    <nav>
                        <ul class="pagination pagination-sm">
                            <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                                <a class="page-link" href="${pageContext.request.contextPath}/Admin?page=${currentPage - 1}">&lt;</a>
                            </li>
                            <c:forEach var="i" begin="1" end="${totalPages}">
                                <li class="page-item ${i == currentPage ? 'active' : ''}">
                                    <a class="page-link" href="${pageContext.request.contextPath}/Admin?page=${i}">${i}</a>
                                </li>
                            </c:forEach>
                            <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                                <a class="page-link" href="${pageContext.request.contextPath}/Admin?page=${currentPage + 1}">&gt;</a>
                            </li>
                        </ul>
                    </nav>
                </div>
            </div>
        </div>
        <!-- Modal Bootstrap đã tùy chỉnh -->
        <div class="modal fade" id="loginSuccessModal" tabindex="-1" aria-labelledby="loginSuccessLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered modal-lg">
                <div class="modal-content shadow-lg border-0">
                    <div class="modal-header bg-success text-white">
                        <h5 class="modal-title d-flex align-items-center" id="loginSuccessLabel">
                            <i class="fas fa-circle-check me-2 fs-4"></i> Đăng nhập thành công
                        </h5>
                        <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Đóng"></button>
                    </div>
                    <div class="modal-body text-center py-4">
                        <i class="fas fa-smile-beam fa-3x text-success mb-3"></i>
                        <h4 class="fw-semibold">Chào mừng bạn quay trở lại hệ thống quản trị viên!</h4>
                        <p class="text-muted">Chúc bạn một ngày làm việc hiệu quả và suôn sẻ.</p>
                    </div>
                </div>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
    <script>
                                            function closeLoginModal() {
                                                const modal = bootstrap.Modal.getInstance(document.getElementById('loginSuccessModal'));
                                                if (modal)
                                                    modal.hide();

                                                // Xoá tham số ?loginSuccess=true khỏi URL
                                                const url = new URL(window.location);
                                                url.searchParams.delete('loginSuccess');
                                                window.history.replaceState({}, document.title, url);
                                            }

                                            window.addEventListener("DOMContentLoaded", () => {
                                                const params = new URLSearchParams(window.location.search);
                                                if (params.get("loginSuccess") === "true") {
                                                    const loginModal = new bootstrap.Modal(document.getElementById('loginSuccessModal'));
                                                    loginModal.show();

                                                    // Tự động ẩn sau 3 giây
                                                    setTimeout(() => {
                                                        closeLoginModal();
                                                    }, 3000);
                                                }
                                            });
    </script>
    </body>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
<script>
                                        function closeLoginModal() {
                                            const modal = bootstrap.Modal.getInstance(document.getElementById('loginSuccessModal'));
                                            if (modal)
                                                modal.hide();

                                            // Xoá tham số ?loginSuccess=true khỏi URL
                                            const url = new URL(window.location);
                                            url.searchParams.delete('loginSuccess');
                                            window.history.replaceState({}, document.title, url);
                                        }

                                        window.addEventListener("DOMContentLoaded", () => {
                                            const params = new URLSearchParams(window.location.search);
                                            if (params.get("loginSuccess") === "true") {
                                                const loginModal = new bootstrap.Modal(document.getElementById('loginSuccessModal'));
                                                loginModal.show();

                                                // Tự động ẩn sau 3 giây
                                                setTimeout(() => {
                                                    closeLoginModal();
                                                }, 3000);
                                            }
                                        });
</script>
</html>