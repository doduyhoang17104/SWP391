<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <title>Danh sách mã giảm giá</title>
        <link href="https://cdn.jsdelivr.net/npm/simple-datatables@latest/dist/style.css" rel="stylesheet" />
        <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet" />
        <style>
            .form-label {
                font-weight: 500;
            }
            .container {
                max-width: 600px;
                margin-top: 40px;
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
                <main class="container-fluid px-4 mt-4">
                    <h2 class="mb-4 text-primary">Thêm mã giảm giá</h2>
                    <c:if test="${not empty msg}">
                        <div class="alert alert-danger">${msg}</div>
                    </c:if>
                    <form action="addPromotion" method="post">
                        <div class="mb-3">
                            <label class="form-label">Tên mã</label>
                            <input type="text" name="promotionName" class="form-control" required maxlength="100">
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Phần trăm giảm (%)</label>
                            <input type="number" name="discountPercent" class="form-control" min="0" max="100" step="0.01" required>
                        </div>
                        <div class="mb-3 row">
                            <div class="col">
                                <label class="form-label">Ngày bắt đầu</label>
                                <input type="date" name="startDate" class="form-control" required>
                            </div>
                            <div class="col">
                                <label class="form-label">Ngày kết thúc</label>
                                <input type="date" name="endDate" class="form-control" required>
                            </div>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Trạng thái</label>
                            <select name="status" class="form-select" required>
                                <option value="1">Đang hoạt động</option>
                                <option value="0">Ngừng</option>
                            </select>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Mô tả</label>
                            <textarea name="description" class="form-control" maxlength="255"></textarea>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Số lượng</label>
                            <input type="number" name="quantity" class="form-control" min="0" value="0" required>
                        </div>
                        <div class="d-flex justify-content-between">
                            <button type="submit" class="btn btn-success">Thêm mã</button>
                            <a href="listPromotion" class="btn btn-secondary">Quay lại</a>
                        </div>
                    </form>
                </main>
            </div>
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
            <script src="${pageContext.request.contextPath}/js/scripts.js"></script>
        </div>
    </body>
</html>
