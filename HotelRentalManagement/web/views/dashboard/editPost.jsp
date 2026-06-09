<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Chỉnh sửa bài viết</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-5">
        <h2 class="mb-4">Chỉnh sửa bài viết</h2>

        <!-- Hiển thị lỗi nếu có -->
        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>

        <!-- Form chỉnh sửa -->
        <form action="editpost" method="post">
            <!-- Mã bài viết (ẩn) -->
            <input type="hidden" name="postId" value="${post.postId}" />

            <div class="mb-3">
                <label for="title" class="form-label">Tiêu đề</label>
                <input type="text" class="form-control" id="title" name="title" 
                       value="${post.title}" required />
            </div>

            <div class="mb-3">
                <label for="content" class="form-label">Nội dung</label>
                <textarea class="form-control" id="content" name="content" rows="6" required>${post.content}</textarea>
            </div>

            <div class="mb-3">
                <label for="image" class="form-label">Link ảnh đại diện</label>
                <input type="text" class="form-control" id="image" name="image" 
                       value="${post.image}" placeholder="Nhập URL ảnh (nếu có)" />
            </div>

            <button type="submit" class="btn btn-primary">Cập nhật</button>
            <a href="listpost" class="btn btn-secondary">Hủy</a>
        </form>
    </div>
</body>
</html>
