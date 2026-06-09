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
                <main class="container mt-4">
                    <h2>Tạo phòng mới</h2>

                    <!-- Hiển thị lỗi nếu có -->
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger">${error}</div>
                    </c:if>

                    <!-- Bổ sung enctype để upload file -->
                    <form action="createRoom" method="post" enctype="multipart/form-data">
                        <div class="mb-3">
                            <label for="roomNumber" class="form-label">Số phòng (3 chữ số, tầng 1-9, phòng 01-99)</label>
                            <input type="text" class="form-control" id="roomNumber" name="roomNumber"
                                   value="${roomNumber != null ? roomNumber : ''}" required />
                        </div>

                        <div class="mb-3">
                            <label for="roomTypeId" class="form-label">Loại phòng</label>
                            <select class="form-select" id="roomTypeId" name="roomTypeId" required>
                                <option value="">-- Chọn loại phòng --</option>
                                <option value="1" ${roomTypeId == '1' ? 'selected' : ''}>Phòng Cổ điển</option>
                                <option value="2" ${roomTypeId == '2' ? 'selected' : ''}>Phòng Cao cấp</option>
                                <option value="3" ${roomTypeId == '3' ? 'selected' : ''}>Phòng Hạng Sang</option>
                                <option value="4" ${roomTypeId == '4' ? 'selected' : ''}>Phòng Gia đình</option>
                                <option value="5" ${roomTypeId == '5' ? 'selected' : ''}>Phòng Hạng Suite</option>
                                <option value="6" ${roomTypeId == '6' ? 'selected' : ''}>Phòng Sang trọng</option>
                            </select>
                        </div>

                        <div class="mb-3">
                            <label for="capacity" class="form-label">Sức chứa</label>
                            <select class="form-select" id="capacity" name="capacity" required>
                                <option value="">-- Chọn sức chứa --</option>
                                <option value="1 người lớn" ${capacity == '1 người lớn' ? 'selected' : ''}>1 người lớn</option>
                                <option value="2 người lớn" ${capacity == '2 người lớn' ? 'selected' : ''}>2 người lớn</option>
                                <option value="4 người lớn" ${capacity == '4 người lớn' ? 'selected' : ''}>4 người lớn</option>
                                <option value="6 người lớn" ${capacity == '6 người lớn' ? 'selected' : ''}>6 người lớn</option>
                                <option value="1 người lớn, 1 trẻ em" ${capacity == '1 người lớn, 1 trẻ em' ? 'selected' : ''}>1 người lớn, 1 trẻ em</option>
                                <option value="2 người lớn, 1 trẻ em" ${capacity == '2 người lớn, 1 trẻ em' ? 'selected' : ''}>2 người lớn, 1 trẻ em</option>
                                <option value="2 người lớn, 2 trẻ em" ${capacity == '2 người lớn, 2 trẻ em' ? 'selected' : ''}>2 người lớn, 2 trẻ em</option>
                            </select>
                        </div>

                        <!-- THÊM size -->
                        <div class="mb-3">
                            <label for="size" class="form-label">Kích thước phòng</label>
                            <input type="text" class="form-control" id="size" name="size"
                                   value="${size != null ? size : ''}" placeholder="VD: 30m²" required />
                        </div>

                        <script>

                            document.getElementById('size').addEventListener('blur', function () {
                                let val = this.value.trim();

                                if (val !== '' && !val.toLowerCase().includes('m²') && !val.toLowerCase().includes('m2')) {
                                    this.value = val + 'm2';
                                }
                            });
                        </script>

                        <!-- THÊM bed -->
                        <div class="mb-3">

                            <label for="bed" class="form-label">Số lượng giường</label>
                            <input type="number" class="form-control" id="bed" name="bed"
                                   value="${bed != null ? bed : ''}" min="1" max="10" required />


                        </div>

                        <div class="mb-3">
                            <label for="status" class="form-label">Trạng thái</label>
                            <select class="form-select" id="status" name="status" required>
                                <option value="">-- Chọn trạng thái --</option>
                                <option value="maintenance" <c:if test="${status == 'maintenance'}">selected</c:if>>Bảo trì</option>
                                <option value="available" <c:if test="${status == 'available'}">selected</c:if>>Có sẵn</option>
                            </select>
                        </div>

                        <!-- Thêm phần upload ảnh, cho phép chọn nhiều ảnh -->
                        <div class="mb-3">
                            <label for="images" class="form-label">Ảnh phòng (có thể chọn nhiều ảnh)</label>
                            <input type="file" class="form-control" id="images" name="images" multiple accept="image/*" />
                        </div>

                        <div id="preview" style="display:flex; gap:10px; flex-wrap: wrap; margin-top:10px;"></div>

                        <button type="submit" class="btn btn-primary">Tạo phòng</button>
                        <a href="listRoom" class="btn btn-secondary">Hủy</a>
                    </form>
                </main>

            </div>
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
            <script src="js/scripts.js"></script>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js" crossorigin="anonymous"></script>
            <script src="assets/demo/chart-area-demo.js"></script>
            <script src="assets/demo/chart-bar-demo.js"></script>
            <script src="https://cdn.jsdelivr.net/npm/simple-datatables@latest" crossorigin="anonymous"></script>
            <script src="js/datatables-simple-demo.js"></script>
            <script>
                            const preview = document.getElementById('preview');
                            const imagesInput = document.getElementById('images');

                            imagesInput.addEventListener('change', function () {
                                preview.innerHTML = ''; // Xóa preview cũ
                                const files = imagesInput.files;

                                for (let i = 0; i < files.length; i++) {
                                    const file = files[i];
                                    const reader = new FileReader();

                                    reader.onload = function (e) {
                                        const img = document.createElement('img');
                                        img.src = e.target.result;
                                        img.style.width = '100px';
                                        img.style.cursor = 'pointer';
                                        img.style.border = '1px solid #ccc';
                                        img.style.borderRadius = '4px';

                                        // Thêm sự kiện click để xem ảnh lớn
                                        img.addEventListener('click', () => {
                                            // Tạo overlay
                                            const overlay = document.createElement('div');
                                            overlay.style.position = 'fixed';
                                            overlay.style.top = 0;
                                            overlay.style.left = 0;
                                            overlay.style.width = '100vw';
                                            overlay.style.height = '100vh';
                                            overlay.style.backgroundColor = 'rgba(0,0,0,0.8)';
                                            overlay.style.display = 'flex';
                                            overlay.style.justifyContent = 'center';
                                            overlay.style.alignItems = 'center';
                                            overlay.style.cursor = 'zoom-out';
                                            overlay.style.zIndex = 1000;

                                            // Tạo ảnh lớn
                                            const bigImg = document.createElement('img');
                                            bigImg.src = e.target.result;
                                            bigImg.style.maxWidth = '90%';
                                            bigImg.style.maxHeight = '90%';
                                            bigImg.style.borderRadius = '8px';
                                            bigImg.style.boxShadow = '0 0 10px #fff';

                                            overlay.appendChild(bigImg);

                                            // Click overlay để đóng
                                            overlay.addEventListener('click', () => {
                                                document.body.removeChild(overlay);
                                            });

                                            document.body.appendChild(overlay);
                                        });

                                        preview.appendChild(img);
                                    };

                                    reader.readAsDataURL(file);
                                }
                            });
            </script>

    </body>
</html>
