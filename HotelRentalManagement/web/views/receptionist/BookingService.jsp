<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Đặt dịch vụ</title>
        <link rel="preconnect" href="https://fonts.googleapis.com" />
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600;700&display=swap" rel="stylesheet" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css" integrity="sha512-Kc323vGBEqzTmouAECnVceyQqyqdsSiqLQISBL29aUW4U/M7pSPA/gEUZQqv1cwx4OnYxTxve5UMg5GT6L4JJg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
        <link rel="stylesheet" href="style/register.css" />


        <style>
            body {
                font-family: 'Poppins', sans-serif;
                margin: 0;
                padding: 0;
                background-color: #f4f7f6; 
                color: #333;
                line-height: 1.6;
            }

            /* Container for the entire registration form and dashboard */
            .registration-container {
                display: flex;
                min-height: 100vh; /* Full viewport height */
                flex-direction: column;
            }

            /* Dashboard Layout */
            .dashboard-container {
                display: flex;
                flex: 1; /* Allows it to grow and take available space */
            }

            /* Main Content Area */
            .main-content {
                flex-grow: 1; /* Takes up the remaining space */
                padding: 20px;
                background-color: #fff; /* White background for the main content */
                border-radius: 8px;
                box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
                margin: 20px; /* Spacing around the main content area */
            }

            /* Form Panels (e.g., Personal Info) */
            .form-panel {
                background-color: #ffffff;
                border-radius: 8px;
                margin-bottom: 25px;
                border: 1px solid #e0e0e0;
                overflow: hidden; /* Ensures border-radius applies to children */
            }

            .form-header {
                background-color: #007bff; /* Default header color */
                color: #fff;
                padding: 15px 20px;
                font-size: 1.2em;
                font-weight: 600;
                border-bottom: 1px solid rgba(0, 0, 0, 0.1);
                display: flex;
                justify-content: space-between;
                align-items: center;
                cursor: pointer;
            }

            .form-header.green {
                background-color: #28a745; /* Green for "Thông tin người đặt phòng" */
            }

            .form-header i {
                transition: transform 0.3s ease;
            }

            .form-header i.fa-chevron-down {
                transform: rotate(0deg);
            }

            .form-header i.fa-chevron-right {
                transform: rotate(-90deg); /* Rotate for collapsed state */
            }

            .form-body {
                padding: 20px;
                display: block; /* Default to visible */
            }

            /* Form Groups */
            .form-group {
                margin-bottom: 20px;
            }

            .form-group label {
                display: block;
                margin-bottom: 8px;
                font-weight: 600;
                color: #555;
            }

            .form-group label span {
                color: #dc3545; /* Red for required fields */
                margin-left: 4px;
            }

            .form-group input[type="text"],
            .form-group input[type="email"],
            .form-group input[type="password"],
            .form-group textarea,
            .form-group select {
                width: 100%;
                padding: 12px 15px;
                border: 1px solid #ccc;
                border-radius: 5px;
                font-size: 1em;
                box-sizing: border-box; /* Include padding and border in the element's total width and height */
                transition: border-color 0.3s ease, box-shadow 0.3s ease;
            }

            .form-group input[type="text"]:focus,
            .form-group input[type="email"]:focus,
            .form-group input[type="password"]:focus,
            .form-group textarea:focus,
            .form-group select:focus {
                border-color: #007bff;
                box-shadow: 0 0 0 0.2rem rgba(0, 123, 255, 0.25);
                outline: none;
            }

            .form-group textarea {
                resize: vertical; /* Allow vertical resizing */
                min-height: 80px;
            }

            /* Form Submit Wrapper and Button */
            .form-submit-wrapper {
                background-color: #f8f9fa;
                padding: 20px;
                border-top: 1px solid #e9ecef;
                text-align: right; /* Align button to the right */
                border-radius: 0 0 8px 8px; /* Rounded bottom corners if form-panel has them */
                margin-top: auto; /* Pushes the submit wrapper to the bottom */
            }

            .btn-register {
                background-color: #007bff;
                color: #fff;
                padding: 12px 25px;
                border: none;
                border-radius: 5px;
                font-size: 1.1em;
                font-weight: 600;
                cursor: pointer;
                transition: background-color 0.3s ease, transform 0.2s ease;
                display: inline-flex; /* Allows content inside to be aligned */
                align-items: center;
                gap: 8px; /* Space between text and icons/details */
            }

            .btn-register:hover {
                background-color: #0056b3;
                transform: translateY(-2px);
            }

            .btn-register:active {
                transform: translateY(0);
            }

            @media (max-width: 768px) {
                .dashboard-container {
                    flex-direction: column; /* Stack dashboard items vertically */
                }

                .main-content {
                    margin: 10px;
                    padding: 15px;
                }

                .form-panel {
                    margin-bottom: 15px;
                }

                .form-header {
                    font-size: 1.1em;
                    padding: 12px 15px;
                }

                .form-group input,
                .form-group textarea,
                .form-group select {
                    padding: 10px 12px;
                }

                .btn-register {
                    width: 100%; /* Full width button on small screens */
                    justify-content: center; /* Center text in full-width button */
                    padding: 15px;
                }

                .form-submit-wrapper {
                    text-align: center; /* Center button on small screens */
                }
            }

            /* Styles for menu-receptionist.jsp (assuming a basic sidebar) */
            /* You would need to define styles for menu-receptionist.jsp separately,
               but here's a placeholder for context. */
            .sidebar-menu {
                width: 250px; /* Fixed width for sidebar */
                background-color: #343a40; /* Dark background */
                color: #fff;
                padding: 20px 0;
                box-shadow: 2px 0 5px rgba(0, 0, 0, 0.1);
                flex-shrink: 0; /* Prevents sidebar from shrinking */
            }

            .scrollable-services {
                height: 400px; /* Đặt chiều cao cố định cho phần chứa dịch vụ */
                overflow-y: auto; /* Thêm thanh cuộn dọc khi nội dung vượt quá chiều cao */
                margin-bottom: 20px; /* Tùy chọn: Thêm khoảng cách dưới cùng để tạo khoảng cách giữa các phần tử */
            }

            @media (max-width: 768px) {
                .sidebar-menu {
                    width: 100%;
                    height: auto;
                    padding: 10px 0;
                }
            }</style>
    </head>
    <body>

    <body>

        <form id="userForm" action="bookingService" method="post" class="registration-container">
            <div class="dashboard-container">
                <%@ include file="menu-receptionist.jsp" %>

                <main class="main-content">

                    <!-- Check for session attributes -->
                    <c:if test="${empty sessionScope.roomId}">
                        <div class="alert alert-danger">Không có thông tin phòng trong session. Vui lòng quay lại bước trước.</div>
                    </c:if>
                    <c:if test="${empty sessionScope.checkin}">
                        <div class="alert alert-danger">Không có thông tin ngày check-in trong session. Vui lòng quay lại bước trước.</div>
                    </c:if>
                    <c:if test="${empty sessionScope.checkout}">
                        <div class="alert alert-danger">Không có thông tin ngày check-out trong session. Vui lòng quay lại bước trước.</div>
                    </c:if>

                    <div class="form-panel services">
                        <div class="form-header blue">Select Services</div>

                        <div class="scrollable-services">
                            <div class="form-body">
                                <c:forEach var="entry" items="${serviceMap}">
                                    <div class="service-category">
                                        <div class="category-header" onclick="toggleCategory(this)">
                                            <i class="fas fa-chevron-right"></i>
                                            ${entry.key.categoryName}
                                        </div>

                                        <div class="category-body" style="display: none;">
                                            <c:forEach var="s" items="${entry.value}">
                                                <div class="form-check">
                                                    <input class="form-check-input" type="checkbox" name="selectedServices"
                                                           value="${s.serviceId}" id="service${s.serviceId}"
                                                           <c:if test="${selectedServiceIds.contains(s.serviceId)}">checked</c:if> />

                                                           <label class="form-check-label" for="service${s.serviceId}">
                                                        ${s.serviceName}
                                                        <c:if test="${s.price > 0}"> - ${s.price} VND</c:if>
                                                        <c:if test="${s.price == 0}">(Free)</c:if>
                                                        </label>
                                                    </div>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </div>

                    <div class="form-submit-wrapper">
                        <input type="hidden" name="roomId" value="${sessionScope.roomId}" />
                        <input type="hidden" name="checkin" value="${sessionScope.checkin}" />
                        <input type="hidden" name="checkout" value="${sessionScope.checkout}" />

                        <button type="submit" form="userForm" class="btn-register">
                            Register
                            Phòng : ${sessionScope.roomNumber} —
                            Thời gian: ${sessionScope.checkin} đến ${sessionScope.checkout}
                        </button>
                    </div>

                </main>
            </div>
        </form>     

    </body>
    <script>
        function toggleCategory(header) {
            const icon = header.querySelector("i");
            const body = header.nextElementSibling;

            if (body.style.display === "none") {
                body.style.display = "block";
                icon.classList.remove("fa-chevron-right");
                icon.classList.add("fa-chevron-down");
            } else {
                body.style.display = "none";
                icon.classList.remove("fa-chevron-down");
                icon.classList.add("fa-chevron-right");
            }
        }
    </script>
</html>
