<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <title>Thêm Bài Viết Mới</title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet" />

        <style>
            .form-section {
                max-width: 700px;
                margin: auto;
                background: #ffffff;
                padding: 30px;
                border-radius: 12px;
                box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
            }
            .form-section h2 {
                text-align: center;
                margin-bottom: 30px;
                color: #0d6efd;
                font-weight: 600;
            }
            #imagePreview {
                max-width: 100%;
                max-height: 300px;
                margin-top: 15px;
                border-radius: 8px;
                display: none;
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
                    <div class="form-section bg-light">
                        <h2><i class="fas fa-pen-nib me-2"></i>Thêm Bài Viết Mới</h2>

                        <form action="addpost" method="post">
                            <!-- Tiêu đề -->
                            <div class="mb-3">
                                <label for="title" class="form-label">Tiêu đề bài viết</label>
                                <input type="text" id="title" name="title" class="form-control" placeholder="Nhập tiêu đề..." required>
                            </div>

                            <!-- Nội dung -->
                            <div class="mb-3">
                                <label for="content" class="form-label">Nội dung bài viết</label>
                                <textarea id="content" name="content" class="form-control" rows="6" placeholder="Nhập nội dung chi tiết..." required></textarea>
                            </div>

                            <!-- Link ảnh -->
                            <div class="mb-3">
                                <label for="image" class="form-label">Link ảnh (URL)</label>
                                <input type="text" id="image" name="image" class="form-control" placeholder="Dán link ảnh từ Google hoặc nơi khác" oninput="previewImage()">
                            </div>

                            <!-- Ảnh preview -->
                            <img id="imagePreview" alt="Xem trước ảnh" />

                            <!-- Nút submit -->
                            <div class="text-center mt-4">
                                <button type="submit" class="btn btn-primary px-4 py-2 rounded-pill">
                                    <i class="fas fa-plus-circle me-2"></i>Thêm bài viết
                                </button>
                            </div>
                        </form>
                    </div>
                </main>
            </div>
        </div>

        <!-- Script -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
        <script src="${pageContext.request.contextPath}/js/scripts.js"></script>
        <script>
            function previewImage() {
                const imageUrl = document.getElementById("image").value;
                const imagePreview = document.getElementById("imagePreview");
                if (imageUrl.trim() !== "") {
                    imagePreview.src = imageUrl;
                    imagePreview.style.display = "block";
                } else {
                    imagePreview.style.display = "none";
                }
            }
        </script>
    </body>
</html>
