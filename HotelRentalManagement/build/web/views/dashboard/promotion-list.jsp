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
        table.dataTable thead {
            background-color: #343a40 !important;
        }
        table.dataTable thead th {
            color: white !important;
            font-weight: bold;
            text-align: center;
        }
        .block-27 ul {
            padding: 0;
            margin: 0 auto;
            display: inline-block;
            list-style: none;
        }
        .block-27 ul li {
            display: inline-block;
            margin: 0 4px;
        }
        .block-27 ul li a {
            display: block;
            padding: 8px 14px;
            border: 1px solid #ccc;
            background: #fff;
            color: #333;
            text-decoration: none;
            border-radius: 4px;
            transition: all 0.2s ease-in-out;
        }
        .block-27 ul li a:hover {
            background: #007bff;
            color: white;
            border-color: #007bff;
        }
        .block-27 ul li.active a {
            background: #007bff;
            color: #fff;
            border-color: #007bff;
            pointer-events: none;
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
                <% String message = (String) session.getAttribute("message");
                   String error = (String) session.getAttribute("error");
                   session.removeAttribute("message");
                   session.removeAttribute("error");
                %>
                <% if (message != null) { %>
                <div class="alert alert-success"><%= message %></div>
                <% } else if (error != null) { %>
                <div class="alert alert-danger"><%= error %></div>
                <% } %>
                <h2 class="text-primary mb-4">Danh sách mã giảm giá</h2>
                <div class="d-flex justify-content-end mb-3">
                    <a href="addPromotion" class="btn btn-success">+ Thêm mã giảm giá mới</a>
                </div>
                <div class="card mb-4">
                    <div class="card-body">
                        <form class="row g-3 align-items-end" method="get" action="promotionList">
                            <div class="col-md-2">
                                <label for="promotionName" class="form-label">Tên mã</label>
                                <input type="text" class="form-control" id="promotionName" name="promotionName" value="${param.promotionName != null ? param.promotionName : ''}">
                            </div>
                            <div class="col-md-2">
                                <label for="discountPercent" class="form-label">Phần trăm giảm</label>
                                <input type="number" class="form-control" id="discountPercent" name="discountPercent" min="0" max="100" value="${param.discountPercent != null ? param.discountPercent : ''}">
                            </div>
                            <div class="col-md-2">
                                <label for="startDate" class="form-label">Ngày bắt đầu</label>
                                <input type="date" class="form-control" id="startDate" name="startDate" value="${param.startDate != null ? param.startDate : ''}">
                            </div>
                            <div class="col-md-2">
                                <label for="endDate" class="form-label">Ngày kết thúc</label>
                                <input type="date" class="form-control" id="endDate" name="endDate" value="${param.endDate != null ? param.endDate : ''}">
                            </div>
                            <div class="col-md-2">
                                <label for="quantity" class="form-label">Số lượng</label>
                                <input type="number" class="form-control" id="quantity" name="quantity" min="0" value="${param.quantity != null ? param.quantity : ''}">
                            </div>
                            <div class="col-md-2">
                                <label for="status" class="form-label">Trạng thái</label>
                                <select class="form-select" id="status" name="status">
                                    <option value="" ${empty param.status ? 'selected' : ''}>Tất cả</option>
                                    <option value="1" ${param.status == '1' ? 'selected' : ''}>Đang hoạt động</option>
                                    <option value="0" ${param.status == '0' ? 'selected' : ''}>Ngừng</option>
                                </select>
                            </div>
                            <div class="col-md-2">
                                <button type="submit" class="btn btn-primary w-100">Tìm kiếm</button>
                            </div>
                            <div class="col-md-2">
                                <a href="promotionList" class="btn btn-secondary w-100">Hiển thị tất cả</a>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="table-responsive">
                    <table class="table table-bordered table-hover align-middle bg-white" id="promoTable">
                        <thead class="table-dark text-center">
                            <tr>
                                <th>ID</th>
                                <th>Tên mã</th>
                                <th>Phần trăm giảm</th>
                                <th>Ngày bắt đầu</th>
                                <th>Ngày kết thúc</th>
                                <th>Số lượng</th>
                                <th>Trạng thái</th>
                                <th>Hành động</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${not empty promotions}">
                                    <c:forEach items="${promotions}" var="p">
                                        <tr>
                                            <td class="text-center">${p.promotionId}</td>
                                            <td>${p.promotionName}</td>
                                            <td class="text-center">${p.discountPercent}%</td>
                                            <td class="text-center"><fmt:formatDate value="${p.startDate}" pattern="yyyy-MM-dd"/></td>
                                            <td class="text-center"><fmt:formatDate value="${p.endDate}" pattern="yyyy-MM-dd"/></td>
                                            <td class="text-center">${p.quantity}</td>
                                            <td class="text-center">
                                                <c:choose>
                                                    <c:when test="${p.status == 1}">
                                                        <span class="badge bg-success">Đang hoạt động</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="badge bg-secondary">Ngừng</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td class="text-center">
                                                <a href="editPromotion?id=${p.promotionId}" class="btn btn-sm btn-warning mb-1">Sửa</a><br>
                                                <a href="deletePromotion?id=${p.promotionId}" class="btn btn-sm btn-danger" onclick="return confirm('Bạn có chắc chắn muốn xóa mã này?');">Xoá</a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr>
                                        <td colspan="8" class="text-center">Không có mã giảm giá nào.</td>
                                    </tr>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>
                </div>
                <div class="row mt-4">
                    <div class="col text-center">
                        <div class="block-27">
                            <ul>
                                <c:if test="${pageIndex > 1}">
                                    <li>
                                        <a href="promotionList?page=${pageIndex - 1}&promotionName=${param.promotionName}&discountPercent=${param.discountPercent}&status=${param.status}&quantity=${param.quantity}">&lt;</a>
                                    </li>
                                </c:if>
                                <c:forEach begin="1" end="${totalPages}" var="i">
                                    <li class="${i == pageIndex ? 'active' : ''}">
                                        <a href="promotionList?page=${i}&promotionName=${param.promotionName}&discountPercent=${param.discountPercent}&status=${param.status}&quantity=${param.quantity}">${i}</a>
                                    </li>
                                </c:forEach>
                                <c:if test="${pageIndex < totalPages}">
                                    <li>
                                        <a href="promotionList?page=${pageIndex + 1}&promotionName=${param.promotionName}&discountPercent=${param.discountPercent}&status=${param.status}&quantity=${param.quantity}">&gt;</a>
                                    </li>
                                </c:if>
                            </ul>
                        </div>
                    </div>
                </div>
            </main>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
        <script src="${pageContext.request.contextPath}/js/scripts.js"></script>
    </div>
</body>
</html>
