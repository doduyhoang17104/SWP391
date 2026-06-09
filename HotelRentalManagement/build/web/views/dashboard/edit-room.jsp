<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Room" %>
<%@ page import="dal.RoomDAO" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>Room</title>
        <link href="https://cdn.jsdelivr.net/npm/simple-datatables@latest/dist/style.css" rel="stylesheet" />
        <link href="css/style-room.css" rel="stylesheet" type="text/css"/>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/js/all.min.js" crossorigin="anonymous"></script>
    </head>
    <body class="sb-nav-fixed">
        <nav class="sb-topnav navbar navbar-expand navbar-dark bg-dark">
            <%@ include file="header.jsp" %>  
        </nav>

        <div id="layoutSidenav">
            <%@ include file="menu-manager.jsp" %>  

            <div id="layoutSidenav_content">
                <main class="container-fluid px-4">
                    <h1 class="mt-4">Chỉnh sửa Phòng</h1>

                    <c:if test="${not empty error}">
                        <div class="alert alert-danger">${error}</div>
                    </c:if>

                    <form action="editRoom" method="post" enctype="multipart/form-data" class="mt-4">
                        <!-- ID Phòng -->
                        <div class="mb-3">
                            <label class="form-label">ID Phòng</label>
                            <input type="text" name="roomIdDisplay" value="${room.roomId}" readonly class="form-control"/>
                            <input type="hidden" name="roomId" value="${room.roomId}" />
                        </div>

                        <!-- Số phòng -->
                        <div class="mb-3">
                            <label for="roomNumber" class="form-label">Số phòng</label>
                            <input type="number" id="roomNumber" name="roomNumber"
                                   value="${room.roomNumber}" min="101" max="9999"
                                   required class="form-control"/>
                        </div>

                        <!-- Loại phòng -->
                        <div class="mb-3">
                            <label class="form-label">Loại phòng</label>
                            <select name="roomTypeId" class="form-select" required>
                                <c:forEach var="type" items="${roomTypes}">
                                    <option value="${type.roomTypeId}"
                                            <c:if test="${type.roomTypeId == room.roomType.roomTypeId}">selected</c:if>>
                                        ${type.typeName}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>

                        <!-- Sức chứa -->
                        <div class="mb-3">
                            <label class="form-label">Sức chứa</label>
                            <select name="capacity" class="form-select" required>
                                <option value="1 người lớn" <c:if test="${room.capacity == '1 người lớn'}">selected</c:if>>1 người lớn</option>
                                <option value="2 người lớn" <c:if test="${room.capacity == '2 người lớn'}">selected</c:if>>2 người lớn</option>
                                <option value="4 người lớn" <c:if test="${room.capacity == '4 người lớn'}">selected</c:if>>3 người lớn</option>
                                <option value="6 người lớn" <c:if test="${room.capacity == '6 người lớn'}">selected</c:if>>4 người lớn</option>

                                    <option value="1 người lớn, 1 trẻ em" <c:if test="${room.capacity == '1 người lớn, 1 trẻ em'}">selected</c:if>>1 người lớn, 1 trẻ em</option>
                                <option value="2 người lớn, 1 trẻ em" <c:if test="${room.capacity == '2 người lớn, 1 trẻ em'}">selected</c:if>>2 người lớn, 1 trẻ em</option>
                                <option value="2 người lớn, 2 trẻ em" <c:if test="${room.capacity == '2 người lớn, 2 trẻ em'}">selected</c:if>>2 người lớn, 2 trẻ em</option>

                                </select>
                            </div>

                            <!-- Kích thước phòng -->
                            <div class="mb-3">
                                <label for="size" class="form-label">Kích thước phòng</label>
                                <input type="text" class="form-control" id="size" name="size"
                                       value="${room.size != null ? room.size : ''}" placeholder="VD: 30m²" required />
                        </div>

                        <!-- Số lượng giường -->
                        <div class="mb-3">
                            <label for="bed" class="form-label">Số lượng giường</label>
                            <input type="number" class="form-control" id="bed" name="bed"
                                   value="${room.bed != null ? room.bed : ''}" min="1" max="10" required />
                        </div>

                        <!-- Trạng thái -->
                        <div class="mb-3">
                            <label class="form-label">Trạng thái</label>
                            <select name="status" class="form-select" required>
                                <option value="available" <c:if test="${room.status == 'available'}">selected</c:if>>Có sẵn</option>
                                <option value="maintenance" <c:if test="${room.status == 'maintenance'}">selected</c:if>>Bảo trì</option>
                                </select>
                            </div>

                            <!-- Ảnh -->
                            <div class="mb-3">
                                <label class="form-label">Ảnh phòng muốn cập nhật</label>
                                <input type="file" id="images" name="images" multiple accept="image/*" class="form-control" onchange="previewImages(this.files)" />

                                <!-- Ảnh cũ -->
                                <label class="form-label mt-3">Ảnh phòng</label>
                                <div class="mt-2" id="oldImages">
                                <c:forEach var="img" items="${room.imageUrls}">
                                    <img src="${img}" width="100px" class="me-2 mb-2 rounded" />
                                </c:forEach>
                            </div>

                            <!-- Ảnh mới preview -->
                            <div class="mt-2" id="preview"></div>
                        </div>

                        <button type="submit" class="btn btn-primary">Lưu thay đổi</button>
                    </form>

                    <script>
                        function previewImages(files) {
                            const preview = document.getElementById('preview');
                            preview.innerHTML = ''; // xóa ảnh cũ
                            if (files.length === 0)
                                return;
                            for (let i = 0; i < files.length; i++) {
                                const file = files[i];
                                const img = document.createElement('img');
                                img.classList.add('me-2', 'mb-2', 'rounded');
                                img.style.width = '100px';

                                const reader = new FileReader();
                                reader.onload = function (e) {
                                    img.src = e.target.result;
                                    preview.appendChild(img);
                                }
                                reader.readAsDataURL(file);
                            }
                        }

                        // Tự động thêm 'm2' nếu người dùng nhập size thiếu đơn vị
                        document.getElementById('size').addEventListener('blur', function () {
                            let val = this.value.trim();

                            if (val !== '' && !val.toLowerCase().includes('m²') && !val.toLowerCase().includes('m2')) {
                                this.value = val + 'm2';
                            }
                        });
                    </script>
                </main>


            </div>

            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
            <script src="js/scripts.js"></script>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js" crossorigin="anonymous"></script>
            <script src="assets/demo/chart-area-demo.js"></script>
            <script src="assets/demo/chart-bar-demo.js"></script>
            <script src="https://cdn.jsdelivr.net/npm/simple-datatables@latest" crossorigin="anonymous"></script>
            <script src="js/datatables-simple-demo.js"></script>
            <script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
            <script>
                        $(document).ready(function () {
                            $('#roomTable').DataTable({
                                stateSave: true // ✅ Lưu trạng thái phân trang, tìm kiếm, v.v.
                            });
                        });
            </script>

    </body>
</html>
