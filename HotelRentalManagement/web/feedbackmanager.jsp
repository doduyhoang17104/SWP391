<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>Room</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/js/all.min.js" crossorigin="anonymous"></script>




    </head>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f4f6f8;
            padding: 40px;
            color: #333;
        }

        h2 {
            text-align: center;
            color: #2c3e50;
            margin-bottom: 30px;
        }

        form {
            display: flex;
            flex-wrap: wrap;
            gap: 20px;
            background-color: #fff;
            padding: 20px;
            border-radius: 10px;
            margin-bottom: 20px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
            align-items: end;
        }

        form div {
            display: flex;
            flex-direction: column;
            width: 200px;
        }

        form label {
            font-weight: bold;
            margin-bottom: 5px;
        }

        input[type="text"],
        input[type="date"],
        select {
            padding: 8px;
            font-size: 14px;
            border: 1px solid #ccc;
            border-radius: 6px;
        }

        input[type="submit"] {
            padding: 10px 20px;
            background-color: #3498db;
            color: white;
            border: none;
            border-radius: 6px;
            cursor: pointer;
        }

        input[type="submit"]:hover {
            background-color: #2c80b4;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            background-color: #fff;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            border-radius: 10px;
            overflow: hidden;
        }

        th, td {
            padding: 12px 15px;
            border-bottom: 1px solid #eee;
        }

        th {
            background-color: #ecf0f1;
            text-align: left;
        }

        tr:hover {
            background-color: #f9f9f9;
        }

        .view-link {
            color: #2980b9;
            text-decoration: none;
            font-weight: bold;
        }

        .view-link:hover {
            text-decoration: underline;
        }

        .pagination {
            margin-top: 30px;
            text-align: center;
        }

        .pagination a,
        .pagination span {
            margin: 0 6px;
            padding: 6px 12px;
            text-decoration: none;
            border-radius: 4px;
            color: #3498db;
            font-weight: bold;
        }

        .pagination span {
            background-color: #3498db;
            color: white;
        }

        .no-result {
            color: #e74c3c;
            text-align: center;
            margin-top: 30px;
        }

        .total-count {
            margin: 15px 0;
            font-weight: bold;
        }
    </style>
    <body class="sb-nav-fixed">
        <nav class="sb-topnav navbar navbar-expand navbar-dark bg-dark">
            <%@ include file="views/dashboard/header.jsp" %>  
        </nav>

        <div id="layoutSidenav">
            <%@ include file="views/dashboard/menu-manager.jsp" %>  

            <div id="layoutSidenav_content">
                <main>
                    <div class="position-fixed top-0 end-0 p-4" style="z-index: 1055; max-width: 350px;">
                        <!-- Phê duyệt thành công -->
                        <c:if test="${not empty sessionScope.successMessage}">
                            <div class="toast text-white border-0 shadow-lg show"
                                 role="alert" aria-live="assertive" aria-atomic="true"
                                 id="successToast"
                                 style="background-color: #28a745 !important;">
                                <div class="d-flex align-items-center p-3 fs-6">
                                    <div class="me-auto fw-semibold">
                                        ✅ ${sessionScope.successMessage}
                                    </div>
                                    <button type="button" class="btn-close btn-close-white ms-2"
                                            data-bs-dismiss="toast" aria-label="Close"></button>
                                </div>
                            </div>
                            <c:remove var="successMessage" scope="session" />
                        </c:if>
                        <!-- Từ chối thành công -->
                        <c:if test="${not empty sessionScope.rejectMessage}">
                            <div class="toast text-white border-0 shadow-lg show"
                                 role="alert" aria-live="assertive" aria-atomic="true"
                                 id="rejectToast"
                                 style="background-color:red !important;">
                                <div class="d-flex align-items-center p-3 fs-6">
                                    <div class="me-auto fw-semibold">
                                        ⚠️ ${sessionScope.rejectMessage}
                                    </div>
                                    <button type="button" class="btn-close btn-close-white ms-2"
                                            data-bs-dismiss="toast" aria-label="Close"></button>
                                </div>
                            </div>
                            <c:remove var="rejectMessage" scope="session" />
                        </c:if>
                    </div>
                    <div class="w-100 p-4">
                        <h2>📋 Danh sách phản hồi khách hàng</h2>

                        <!-- FORM TÌM KIẾM -->
                        <form method="get" action="feedbackmanager">
                            <div>
                                <label>Tên khách hàng:</label>
                                <input type="text" name="nameKeyword" value="${fn:escapeXml(nameKeyword)}" />
                            </div>
                            <div>
                                <label>Đánh giá ⭐ :</label>
                                <select name="ratingFilter">
                                    <option value="">-- Tất cả --</option>
                                    <option value="5" ${ratingFilter == '5' ? 'selected' : ''}>5 ⭐</option>
                                    <option value="4" ${ratingFilter == '4' ? 'selected' : ''}>4 ⭐</option>
                                    <option value="3" ${ratingFilter == '3' ? 'selected' : ''}>3 ⭐</option>
                                    <option value="2" ${ratingFilter == '2' ? 'selected' : ''}>2 ⭐</option>
                                    <option value="1" ${ratingFilter == '1' ? 'selected' : ''}>1 ⭐</option>
                                </select>
                            </div>
                            <div>
                                <label>Ngày phản hồi:</label>
                                <input type="date" name="dateKeyword" value="${fn:escapeXml(dateKeyword)}" />
                            </div>
                            <div>
                                <label>&nbsp;</label>
                                <input type="submit" value="🔍 Tìm kiếm" />
                            </div>
                        </form>

                        <!-- NÚT RESET BỘ LỌC -->
                        <c:if test="${not empty nameKeyword || not empty ratingFilter || not empty dateKeyword}">
                            <div style="margin-bottom: 20px;">
                                <form method="get" action="feedbackmanager">
                                    <input type="submit" value="🔁 Xem tất cả phản hồi"
                                           style="padding: 10px 20px; background-color: #66FF66; color: black; border: none; border-radius: 6px;" />
                                </form>
                            </div>
                        </c:if>

                        <!-- REVIEW SUMMARY -->
                        <div style="margin-bottom: 30px;">
                            <div style="background: #fff; padding: 20px; border-radius: 10px; box-shadow: 0 2px 8px rgba(0,0,0,0.1); width: 100%;">
                                <h3 style="margin-bottom: 15px;">📊 Review summary</h3>

                                <c:forEach var="i" begin="1" end="5">
                                    <c:set var="rating" value="${6 - i}" />
                                    <c:set var="count" value="${ratingStats[i]}" />
                                    <c:if test="${empty count}">
                                        <c:set var="count" value="0" />
                                    </c:if>
                                    <c:set var="percent" value="${reviewCount > 0 ? (count * 100 / reviewCount) : 0}" />

                                    <div style="display: flex; align-items: center; margin: 5px 0;">
                                        <span style="width: 25px;">${i}</span>
                                        <div style="background: #eee; height: 10px; flex: 1; margin: 0 10px; border-radius: 5px;">
                                            <div style="background: #ffc107; height: 100%; border-radius: 5px; width: ${percent}%"></div>
                                        </div>
                                        <span style="width: 30px; text-align: right;">${count}</span>
                                    </div>
                                </c:forEach>

                                <div style="margin-top: 15px; font-size: 20px; font-weight: bold;">
                                    ⭐ <fmt:formatNumber value="${averageRating}" type="number" maxFractionDigits="1" /> / 5
                                </div>
                            </div>
                        </div>

                        <!-- DANH SÁCH FEEDBACK -->
                        <c:choose>
                            <c:when test="${not empty feedbackList}">
                                <p class="total-count">Tổng phản hồi tìm thấy: ${totalFeedback}</p>
                                <table>
                                    <tr>
                                        <th>ID</th>
                                        <th>Khách hàng</th>
                                        <th>Đánh giá</th>
                                        <th>Bình luận</th>
                                        <th>Ngày gửi</th>
                                        <th>Xem</th>
                                    </tr>
                                    <c:forEach var="f" items="${feedbackList}">
                                        <tr>
                                            <td>${f.feedbackId}</td>
                                            <td>${f.userFullName}</td>
                                            <td>${f.overallRating}/5 ⭐</td>
                                            <td>${fn:substring(f.comment, 0, 50)}...</td>
                                            <td>${f.createdAt}</td>
                                            <td><a class="view-link" href="viewfeedback?id=${f.feedbackId}">Xem</a></td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </c:when>
                            <c:otherwise>
                                <p class="no-result">❌ Không có phản hồi nào phù hợp.</p>
                            </c:otherwise>
                        </c:choose>

                        <!-- PHÂN TRANG -->
                        <c:if test="${totalPages > 1}">
                            <div class="pagination">
                                <c:forEach var="i" begin="1" end="${totalPages}">
                                    <c:choose>
                                        <c:when test="${i == page}">
                                            <span>${i}</span>
                                        </c:when>
                                        <c:otherwise>
                                            <a href="feedbackmanager?page=${i}&nameKeyword=${fn:escapeXml(nameKeyword)}&ratingFilter=${ratingFilter}&dateKeyword=${fn:escapeXml(dateKeyword)}">${i}</a>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </div>
                        </c:if>
                    </div>
                </main>
            </div>
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
            <script src="${pageContext.request.contextPath}/js/scripts.js"></script>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js" crossorigin="anonymous"></script>
            <script src="assets/demo/chart-area-demo.js"></script>
            <script src="assets/demo/chart-bar-demo.js"></script>

    </body>
    <script>
        <script>
        window.addEventListener('DOMContentLoaded', () => {
                const successToast = document.getElementById('successToast');
        const rejectToast = document.getElementById('rejectToast');
        const errorToast = document.getElementById('errorToast');

        if (successToast)
            new bootstrap.Toast(successToast, {delay: 2000}).show();
        if (rejectToast)
            new bootstrap.Toast(rejectToast, {delay: 2000}).show();
        if (errorToast)
            new bootstrap.Toast(errorToast, {delay: 2000}).show();
    });
    </script>
</html>
