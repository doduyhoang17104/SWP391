
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Sửa mã giảm giá</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" />
    <style>
        .form-label { font-weight: 500; }
        .container { max-width: 600px; margin-top: 40px; }
    </style>
</head>
<body>
<div class="container bg-white p-4 rounded shadow">
    <h2 class="mb-4 text-primary">Sửa mã giảm giá</h2>
    <c:if test="${not empty msg}">
        <div class="alert alert-danger">${msg}</div>
    </c:if>
    <form action="editPromotion" method="post">
        <input type="hidden" name="promotionId" value="${promotion.promotionId}" />
        <div class="mb-3">
            <label class="form-label">Tên mã</label>
            <input type="text" name="promotionName" class="form-control" required maxlength="100" value="${promotion.promotionName}">
        </div>
        <div class="mb-3">
            <label class="form-label">Phần trăm giảm (%)</label>
            <input type="number" name="discountPercent" class="form-control" min="0" max="100" step="0.01" required value="${promotion.discountPercent}">
        </div>
        <div class="mb-3 row">
            <div class="col">
                <label class="form-label">Ngày bắt đầu</label>
                <input type="date" name="startDate" class="form-control" required value="${promotion.startDate}">
            </div>
            <div class="col">
                <label class="form-label">Ngày kết thúc</label>
                <input type="date" name="endDate" class="form-control" required value="${promotion.endDate}">
            </div>
        </div>
        <div class="mb-3">
            <label class="form-label">Trạng thái</label>
            <select name="status" class="form-select" required>
                <option value="1" ${promotion.status == 1 ? 'selected' : ''}>Đang hoạt động</option>
                <option value="0" ${promotion.status == 0 ? 'selected' : ''}>Ngừng</option>
            </select>
        </div>
        <div class="mb-3">
            <label class="form-label">Mô tả</label>
            <textarea name="description" class="form-control" maxlength="255">${promotion.description}</textarea>
        </div>
        <div class="mb-3">
            <label class="form-label">Số lượng</label>
            <input type="number" name="quantity" class="form-control" min="0" value="${promotion.quantity}" required>
        </div>
        <div class="d-flex justify-content-between">
            <button type="submit" class="btn btn-success">Cập nhật</button>
            <a href="listPromotion" class="btn btn-secondary">Quay lại</a>
        </div>
    </form>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
