    
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <title>Dịch vụ</title>
        <link href="https://cdn.jsdelivr.net/npm/simple-datatables@latest/dist/style.css" rel="stylesheet" />
        <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet" />
        <style>
            .service-container { display: flex; gap: 32px; margin-top: 32px; }
            .service-categories, .service-list { background: #fff; border: 1px solid #ccc; border-radius: 8px; padding: 24px; flex: 1; }
            .service-categories { max-width: 350px; }
            .service-categories ul { list-style: none; padding: 0; margin: 0; }
            .service-categories li { padding: 12px 0; border-bottom: 1px dashed #ccc; display: flex; align-items: center; }
            .service-categories li:last-child { border-bottom: none; }
            .service-categories .active { font-weight: bold; color: #007bff; }
            .service-list table { width: 100%; border-collapse: collapse; margin-top: 16px; }
            .service-list th, .service-list td { padding: 10px; border: 1px solid #ddd; text-align: left; }
            .service-list th { background: #f8f9fa; }
            .service-list .price { color: #007bff; font-weight: bold; }
            .service-list .muted { color: #aaa; font-style: italic; }
            .action-link { margin: 0 4px; color: #d63384; text-decoration: none; font-weight: bold; }
            .action-link.edit { color: #007bff; }
            .action-link.delete { color: #dc3545; }
        </style>
    </head>
    <body class="sb-nav-fixed">
        <nav class="sb-topnav navbar navbar-expand navbar-dark bg-dark">
            <%@ include file="header.jsp" %>  
        </nav>
        <div id="layoutSidenav">
            <%@ include file="menu-manager.jsp" %>  
            <div id="layoutSidenav_content">
                <main>
                    <div class="container-fluid px-4">
                        <c:if test="${not empty sessionScope.error}">
                            <div class="alert alert-danger">${sessionScope.error}</div>
                            <c:remove var="error" scope="session"/>
                        </c:if>
                        <h1 class="mt-4">Quản lý dịch vụ</h1>
                        <div class="service-container">
                            <!-- Service Categories -->
                            <div class="service-categories">
                                <h5>Danh mục dịch vụ</h5>
                                <!-- Thanh tìm kiếm danh mục -->
                                <div class="mb-3">
                                    <input type="text" id="categorySearch" class="form-control" placeholder="Tìm kiếm danh mục...">
                                </div>
                                <ul>
                                    <c:forEach var="cat" items="${serviceCategories}">
                                        <li class="${cat.serviceCategoryId == selectedCategoryId ? 'active' : ''}">
                                            <a href="${pageContext.request.contextPath}/servicemanager?categoryId=${cat.serviceCategoryId}" style="text-decoration:none; color:inherit;">
                                                &#9654; ${cat.serviceCategoryName}
                                            </a>
                                        </li>
                                    </c:forEach>
                                </ul>
                                <!-- Nút mở modal thêm danh mục -->
                                <button type="button" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#addCategoryModal">
                                    Thêm danh mục mới
                                </button>
                            </div>
                            <!-- Services Table -->
                            <div class="service-list">
                                <h5>Dịch vụ
                                    <c:if test="${selectedCategoryId != null}">
                                        <span style="font-weight:normal; color:#888;">- 
                                            <c:forEach var="cat" items="${serviceCategories}">
                                                <c:if test="${cat.serviceCategoryId == selectedCategoryId}">
                                                    ${cat.serviceCategoryName}
                                                    <!-- Nút Sửa danh mục -->
                                                    <button type="button" class="btn btn-sm btn-primary ms-2" data-bs-toggle="modal" data-bs-target="#editCategoryModal">
                                                        Sửa
                                                    </button>
                                                    <!-- Nút Xóa danh mục -->
                                                    <a href="categorymanager?action=delete&id=${cat.serviceCategoryId}" class="btn btn-sm btn-danger ms-1" onclick="return confirm('Bạn có chắc chắn muốn xóa danh mục này?');">
                                                        Xóa
                                                    </a>
                                                </c:if>
                                            </c:forEach>
                                        </span>
                                    </c:if>
                                </h5>
                                <!-- Thanh tìm kiếm dịch vụ -->
                                <c:if test="${selectedCategoryId != null}">
                                    <div class="mb-3">
                                        <input type="text" id="serviceSearch" class="form-control" placeholder="Tìm kiếm dịch vụ theo tên hoặc giá...">
                                    </div>
                                </c:if>
                                <c:choose>
                                    <c:when test="${selectedCategoryId == null}">
                                        <div class="muted">Vui lòng chọn một danh mục dịch vụ để xem danh sách dịch vụ.</div>
                                    </c:when>
                                    <c:otherwise>
                                        <!-- Nút mở modal thêm dịch vụ (chỉ hiện khi đã chọn category) -->
                                        <c:if test="${selectedCategoryId != null}">
                                            <button type="button" class="btn btn-success mb-3" data-bs-toggle="modal" data-bs-target="#addServiceModal">
                                                Thêm dịch vụ mới
                                            </button>
                                        </c:if>
                                        <table>
                                            <thead>
                                                <tr>
                                                    <th>Tên dịch vụ</th>
                                                    <th>Giá</th>
                                                    <th>Thao tác</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:choose>
                                                    <c:when test="${not empty services}">
                                                        <c:forEach var="s" items="${services}">
                                                            <tr>
                                                                <td>${s.serviceName}</td>
                                                                <td class="price">
                                                                    <fmt:formatNumber value="${s.price}" type="currency" currencySymbol="₫"/>
                                                                </td>
                                                                <td>
                                                                    <button type="button" class="action-link edit btn btn-link p-0" data-bs-toggle="modal" data-bs-target="#editServiceModal${s.serviceId}">Sửa</button> |
                                                                    <a href="servicemanager?action=delete&id=${s.serviceId}&categoryId=${selectedCategoryId}" class="action-link delete" onclick="return confirm('Bạn có chắc chắn muốn xóa dịch vụ này?');">Xóa</a>
                                                                </td>
                                                            </tr>
                                                            <!-- Modal Sửa Dịch Vụ cho từng dịch vụ -->
                                                            <div class="modal fade" id="editServiceModal${s.serviceId}" tabindex="-1" aria-labelledby="editServiceModalLabel${s.serviceId}" aria-hidden="true">
                                                              <div class="modal-dialog">
                                                                <form action="servicemanager?action=edit" method="post">
                                                                  <div class="modal-content">
                                                                    <div class="modal-header">
                                                                      <h5 class="modal-title" id="editServiceModalLabel${s.serviceId}">Sửa dịch vụ</h5>
                                                                      <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Đóng"></button>
                                                                    </div>
                                                                    <div class="modal-body">
                                                                      <input type="hidden" name="serviceId" value="${s.serviceId}">
                                                                      <input type="hidden" name="categoryId" value="${selectedCategoryId}">
                                                                      <div class="mb-3">
                                                                        <label for="editServiceName${s.serviceId}" class="form-label">Tên dịch vụ</label>
                                                                        <input type="text" class="form-control" id="editServiceName${s.serviceId}" name="serviceName" value="${s.serviceName}" required>
                                                                      </div>
                                                                      <div class="mb-3">
                                                                        <label for="editServicePrice${s.serviceId}" class="form-label">Giá</label>
                                                                        <input type="number" class="form-control" id="editServicePrice${s.serviceId}" name="servicePrice" value="${s.price}" required>
                                                                      </div>
                                                                    </div>
                                                                    <div class="modal-footer">
                                                                      <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                                                                      <button type="submit" class="btn btn-primary">Lưu</button>
                                                                    </div>
                                                                  </div>
                                                                </form>
                                                              </div>
                                                            </div>
                                                        </c:forEach>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <tr>
                                                            <td colspan="3" class="muted">Không có dịch vụ nào.</td>
                                                        </tr>
                                                    </c:otherwise>
                                                </c:choose>
                                            </tbody>
                                        </table>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                </main>
            </div>
        </div>
        <!-- Modal Thêm Danh Mục -->
        <div class="modal fade" id="addCategoryModal" tabindex="-1" aria-labelledby="addCategoryModalLabel" aria-hidden="true">
          <div class="modal-dialog">
            <form action="categorymanager?action=add" method="post">
              <div class="modal-content">
                <div class="modal-header">
                  <h5 class="modal-title" id="addCategoryModalLabel">Thêm danh mục mới</h5>
                  <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Đóng"></button>
                </div>
                <div class="modal-body">
                  <div class="mb-3">
                    <label for="categoryName" class="form-label">Tên danh mục</label>
                    <input type="text" class="form-control" id="categoryName" name="categoryName" required>
                  </div>
                </div>
                <div class="modal-footer">
                  <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                  <button type="submit" class="btn btn-success">Thêm</button>
                </div>
              </div>
            </form>
          </div>
        </div>

        <!-- Modal Thêm Dịch Vụ -->
        <div class="modal fade" id="addServiceModal" tabindex="-1" aria-labelledby="addServiceModalLabel" aria-hidden="true">
          <div class="modal-dialog">
            <form action="servicemanager?action=add" method="post">
              <div class="modal-content">
                <div class="modal-header">
                  <h5 class="modal-title" id="addServiceModalLabel">Thêm dịch vụ mới</h5>
                  <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Đóng"></button>
                </div>
                <div class="modal-body">
                  <div class="mb-3">
                    <label for="serviceName" class="form-label">Tên dịch vụ</label>
                    <input type="text" class="form-control" id="serviceName" name="serviceName" required>
                  </div>
                  <div class="mb-3">
                    <label for="servicePrice" class="form-label">Giá</label>
                    <input type="number" class="form-control" id="servicePrice" name="servicePrice" required>
                  </div>
                  <input type="hidden" name="categoryId" value="${selectedCategoryId}">
                </div>
                <div class="modal-footer">
                  <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                  <button type="submit" class="btn btn-success">Thêm</button>
                </div>
              </div>
            </form>
          </div>
        </div>

        <!-- Modal Sửa Danh Mục -->
        <div class="modal fade" id="editCategoryModal" tabindex="-1" aria-labelledby="editCategoryModalLabel" aria-hidden="true">
          <div class="modal-dialog">
            <form action="categorymanager?action=edit" method="post">
              <div class="modal-content">
                <div class="modal-header">
                  <h5 class="modal-title" id="editCategoryModalLabel">Sửa danh mục</h5>
                  <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Đóng"></button>
                </div>
                <div class="modal-body">
                  <input type="hidden" name="categoryId" value="${selectedCategoryId}">
                  <div class="mb-3">
                    <label for="editCategoryName" class="form-label">Tên danh mục</label>
                    <c:forEach var="cat" items="${serviceCategories}">
                        <c:if test="${cat.serviceCategoryId == selectedCategoryId}">
                            <input type="text" class="form-control" id="editCategoryName" name="categoryName" value="${cat.serviceCategoryName}" required>
                        </c:if>
                    </c:forEach>
                  </div>
                </div>
                <div class="modal-footer">
                  <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                  <button type="submit" class="btn btn-primary">Lưu</button>
                </div>
              </div>
            </form>
          </div>
        </div>
        <script>
        // Lọc danh mục dịch vụ theo từ khóa
        const searchInput = document.getElementById('categorySearch');
        if (searchInput) {
            searchInput.addEventListener('input', function() {
                const keyword = this.value.toLowerCase();
                document.querySelectorAll('.service-categories ul li').forEach(function(li) {
                    const text = li.textContent.toLowerCase();
                    li.style.display = text.includes(keyword) ? '' : 'none';
                });
            });
        }
        // Lọc dịch vụ theo tên hoặc giá
        const serviceSearch = document.getElementById('serviceSearch');
        if (serviceSearch) {
            serviceSearch.addEventListener('input', function() {
                const keyword = this.value.toLowerCase();
                document.querySelectorAll('.service-list table tbody tr').forEach(function(row) {
                    // Lấy text của cột tên dịch vụ và giá
                    const name = row.querySelector('td:nth-child(1)')?.textContent.toLowerCase() || '';
                    const price = row.querySelector('td:nth-child(2)')?.textContent.toLowerCase() || '';
                    row.style.display = (name.includes(keyword) || price.includes(keyword)) ? '' : 'none';
                });
            });
        }
        </script>
    </body>
</html>
