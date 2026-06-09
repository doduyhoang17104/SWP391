<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Chi tiết bài viết</title>
        <link href="../../css/styles.css" rel="stylesheet" />
        <style>
            .post-container {
                max-width: 800px;
                margin: 40px auto;
                padding: 20px;
                background: #fff;
                border-radius: 10px;
                box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            }
            .post-image {
                max-width: 100%;
                border-radius: 8px;
                margin-bottom: 20px;
            }
        </style>
    </head>
    <body class="sb-nav-fixed">
        <%@ include file="header.jsp" %>
        <div id="layoutSidenav">
            <%@ include file="menu-manager.jsp" %>

            <div id="layoutSidenav_content">
                <main>
                    <div class="post-container">

                        <!-- Hiển thị thông báo lỗi nếu có -->
                        <c:if test="${not empty error}">
                            <div class="alert alert-danger">${error}</div>
                        </c:if>

                        <!-- Hiển thị nội dung bài viết nếu có -->
                        <c:if test="${not empty post}">
                            <h2 class="text-primary">${post.title}</h2>
                            <p><strong>Tác giả:</strong> ${post.authorName}</p>
                            <p><strong>Ngày tạo:</strong> ${post.createdAt}</p>
                            <c:if test="${not empty post.image}">
                                <img src="${post.image}" alt="Ảnh bài viết" class="post-image"/>
                            </c:if>
                            <hr>
                            <pre style="white-space: pre-wrap; font-family: inherit; font-size: 16px; line-height: 1.6;">${post.content}</pre>

                            <hr>
                            <div class="contact-links mt-3">
                                <p><strong>Liên hệ:</strong></p>
                                <ul style="list-style: none; padding-left: 0;">
                                    <li>📞 <a href="tel:0375457065">0375457065</a></li>
                                    <li>✉ <a href="https://mail.google.com/mail/u/0/#inbox">example@email.com</a></li>
                                    <li>🅕  <a href="https://www.facebook.com/tung.vu.453672" target="_blank">Facebook của chúng tôi</a></li>
                                </ul>
                            </div>

                        </c:if>

                        <a href="listpost" class="btn btn-secondary mt-3">⬅ Quay lại danh sách</a>
                    </div>
                </main>
            </div>
        </div>
    </body>
</html>
