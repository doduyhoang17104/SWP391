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
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <title>Thống kê</title>
        <link href="https://cdn.jsdelivr.net/npm/simple-datatables@latest/dist/style.css" rel="stylesheet" />
        <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet" />
        <link href="css/style-room.css" rel="stylesheet" />
        <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/js/all.min.js" crossorigin="anonymous"></script>

        <style>
            .container-fluid.py-4 {
                padding-top: 1.5rem !important;
                padding-bottom: 1.5rem !important;
            }

            .card {
                height: 100%;
                transition: all 0.3s ease;
            }

            .card:hover {
                transform: scale(1.02);
                box-shadow: 0 8px 20px rgba(0, 0, 0, 0.08);
            }

            .card-body {
                height: 420px;
                display: flex;
                flex-direction: column;
                justify-content: space-between;
                padding: 1rem;
                background-color: #f8f9fa;
                border-radius: 12px;
            }

            canvas {
                height: 350px !important;
                width: 100% !important;
            }

            .chart-row {
                margin-bottom: 1rem;
            }

            .card-title {
                margin-bottom: 1rem;
                font-size: 1.1rem;
            }

            @media (min-width: 992px) {
                .chart-col-main {
                    flex: 0 0 66.666667%;
                    max-width: 66.666667%;
                }

                .chart-col-side {
                    flex: 0 0 33.333333%;
                    max-width: 33.333333%;
                }
            }

            @media (min-width: 768px) and (max-width: 991.98px) {
                .chart-col-main {
                    flex: 0 0 58.333333%;
                    max-width: 58.333333%;
                }

                .chart-col-side {
                    flex: 0 0 41.666667%;
                    max-width: 41.666667%;
                }
            }
            .card-title i {
                color: #0d6efd;
                font-size: 1.2rem;
            }

            .chart-container {
                position: relative;
                height: 250px;
                padding: 10px;
            }

            #loginTodayChart {
                max-height: 220px;
                width: 100% !important;
            }

            .card {
                transition: all 0.3s ease-in-out;
            }

            .card:hover {
                transform: translateY(-2px);
                box-shadow: 0 8px 24px rgba(0, 0, 0, 0.08);
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
                <main>
                    <div class="container-fluid py-4">
                        <h3 class="text-center text-primary fw-bold mb-4">
                            <i class="fas fa-chart-line me-2"></i>Thống kê lượt truy cập hôm nay
                        </h3>

                        <div class="row justify-content-center">
                            <div class="col-lg-8 col-md-10">
                                <div class="card border-0 shadow-sm rounded-4 p-3 bg-white">
                                    <div class="card-body">
                                        <h5 class="card-title text-primary d-flex align-items-center mb-4">
                                            <i class="fas fa-user-check me-2"></i> Số người truy cập
                                        </h5>
                                        <div class="chart-container" style="position: relative; height: 250px;">
                                            <canvas id="loginTodayChart"></canvas>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="container-fluid py-4">
                        <h3 class="text-center text-primary fw-bold mb-4">
                            <i class="fas fa-chart-line me-2"></i>Thống kê doanh thu
                        </h3>

                        <div class="row chart-row">
                            <div class="col-lg-8 col-md-7 mb-3 chart-col-main">
                                <div class="card shadow-sm rounded-3 h-100">
                                    <div class="card-body">
                                        <h5 class="card-title text-info"><i class="fas fa-calendar-day me-2"></i>Doanh thu theo ngày</h5>
                                        <canvas id="lineDayChart"></canvas>
                                    </div>
                                </div>
                            </div>

                            <div class="col-lg-4 col-md-5 mb-3 chart-col-side">
                                <div class="card shadow-sm rounded-3 h-100">
                                    <div class="card-body">
                                        <h5 class="card-title text-danger"><i class="fas fa-chart-pie me-2"></i>Tỷ lệ doanh thu theo năm</h5>
                                        <canvas id="pieYearChart"></canvas>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="row chart-row">
                            <div class="col-lg-6 col-md-6 mb-3">
                                <div class="card shadow-sm rounded-3 h-100">
                                    <div class="card-body">
                                        <h5 class="card-title text-success"><i class="fas fa-calendar-alt me-2"></i>Doanh thu theo tháng</h5>
                                        <canvas id="barMonthChart"></canvas>
                                    </div>
                                </div>
                            </div>

                            <div class="col-lg-6 col-md-6 mb-3">
                                <div class="card shadow-sm rounded-3 h-100">
                                    <div class="card-body">
                                        <h5 class="card-title text-warning"><i class="fas fa-calendar me-2"></i>Doanh thu theo năm</h5>
                                        <canvas id="lineYearChart"></canvas>
                                    </div>
                                </div>
                            </div>


                        </div>
                    </div>

                    <!-- Chart.js -->
                    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
                    <script>
                        const dayLabels = [
                        <c:forEach var="item" items="${revenueByDay}" varStatus="loop">
                        "<fmt:formatDate value='${item.day}' pattern='dd/MM' />"<c:if test="${!loop.last}">,</c:if>
                        </c:forEach>
                        ];
                        const dayData = [
                        <c:forEach var="item" items="${revenueByDay}" varStatus="loop">
                            ${item.totalRevenue}<c:if test="${!loop.last}">,</c:if>
                        </c:forEach>
                        ];

                        const monthLabels = [
                        <c:forEach var="item" items="${revenueByMonth}" varStatus="loop">
                        "Tháng ${item.month}"<c:if test="${!loop.last}">,</c:if>
                        </c:forEach>
                        ];
                        const monthData = [
                        <c:forEach var="item" items="${revenueByMonth}" varStatus="loop">
                            ${item.totalRevenue}<c:if test="${!loop.last}">,</c:if>
                        </c:forEach>
                        ];

                        const yearLabels = ["2021", "2022", "2023", "2024", "2025"];
                        const yearData = [
                        <c:forEach var="item" items="${revenueByYear}" varStatus="loop">
                            ${item.totalRevenue}<c:if test="${!loop.last}">,</c:if>
                        </c:forEach>
                        ];

                        new Chart(document.getElementById("lineDayChart"), {
                            type: 'line',
                            data: {
                                labels: dayLabels,
                                datasets: [{
                                        label: 'Doanh thu (VNĐ)',
                                        data: dayData,
                                        borderColor: "#0d6efd",
                                        backgroundColor: "#0d6efd33",
                                        fill: true,
                                        tension: 0.4
                                    }]
                            },
                            options: {
                                responsive: true,
                                maintainAspectRatio: false,
                                plugins: {
                                    legend: {display: false}
                                },
                                scales: {
                                    y: {beginAtZero: true}
                                }
                            }
                        });

                        new Chart(document.getElementById("barMonthChart"), {
                            type: 'bar',
                            data: {
                                labels: monthLabels,
                                datasets: [{
                                        label: 'Doanh thu (VNĐ)',
                                        data: monthData,
                                        backgroundColor: "#198754"
                                    }]
                            },
                            options: {
                                responsive: true,
                                maintainAspectRatio: false,
                                plugins: {
                                    legend: {display: false}
                                },
                                scales: {
                                    y: {beginAtZero: true}
                                }
                            }
                        });

                        new Chart(document.getElementById("lineYearChart"), {
                            type: 'line',
                            data: {
                                labels: yearLabels,
                                datasets: [{
                                        label: 'Doanh thu (VNĐ)',
                                        data: yearData,
                                        borderColor: "#ffc107",
                                        backgroundColor: "#ffc10733",
                                        fill: true,
                                        tension: 0.4
                                    }]
                            },
                            options: {
                                responsive: true,
                                maintainAspectRatio: false,
                                plugins: {
                                    legend: {display: false}
                                },
                                scales: {
                                    y: {beginAtZero: true}
                                }
                            }
                        });

                        new Chart(document.getElementById("pieYearChart"), {
                            type: 'pie',
                            data: {
                                labels: yearLabels,
                                datasets: [{
                                        data: yearData,
                                        backgroundColor: ["#dc3545", "#fd7e14", "#ffc107", "#0d6efd", "#198754"]
                                    }]
                            },
                            options: {
                                responsive: true,
                                maintainAspectRatio: false,
                                plugins: {
                                    legend: {
                                        position: 'bottom',
                                        labels: {
                                            boxWidth: 20,
                                            padding: 10
                                        }
                                    }
                                }
                            }
                        });
                        new Chart(document.getElementById("loginTodayChart"), {
                            type: 'bar',
                            data: {
                                labels: ["Số người đăng nhập hôm nay"],
                                datasets: [{
                                        label: "Số người",
                                        data: [${loginToday}], // giá trị động từ servlet
                                        backgroundColor: "#0d6efd",
                                        borderRadius: 8
                                    }]
                            },
                            options: {
                                indexAxis: 'y', // 🔁 Cột ngang
                                responsive: true,
                                maintainAspectRatio: false,
                                plugins: {
                                    legend: {
                                        display: false
                                    },
                                    tooltip: {
                                        callbacks: {
                                            label: function (context) {
                                                return ` ${loginToday} người`;
                                            }
                                        }
                                    }
                                },
                                scales: {
                                    x: {
                                        beginAtZero: true,
                                        ticks: {
                                            stepSize: 1
                                        }
                                    },
                                    y: {
                                        ticks: {
                                            font: {
                                                weight: 'bold'
                                            }
                                        }
                                    }
                                }
                            }
                        });


                    </script>
                </main>
            </div>

        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
        <script src="${pageContext.request.contextPath}/js/scripts.js"></script>
        <!-- Modal Bootstrap đã tùy chỉnh -->
        <div class="modal fade" id="loginSuccessModal" tabindex="-1" aria-labelledby="loginSuccessLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered modal-lg">
                <div class="modal-content shadow-lg border-0">
                    <div class="modal-header bg-success text-white">
                        <h5 class="modal-title d-flex align-items-center" id="loginSuccessLabel">
                            <i class="fas fa-circle-check me-2 fs-4"></i> Đăng nhập thành công
                        </h5>
                        <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Đóng"></button>
                    </div>
                    <div class="modal-body text-center py-4">
                        <i class="fas fa-smile-beam fa-3x text-success mb-3"></i>
                        <h4 class="fw-semibold">Chào mừng bạn quay trở lại hệ thống quản lí khách sạn!</h4>
                        <p class="text-muted">Chúc bạn một ngày làm việc hiệu quả và suôn sẻ.</p>
                    </div>
                </div>
            </div>
        </div>
    </body>
    <script>
                        function closeLoginModal() {
                            const modal = bootstrap.Modal.getInstance(document.getElementById('loginSuccessModal'));
                            if (modal)
                                modal.hide();

                            // Xoá tham số ?loginSuccess=true khỏi URL
                            const url = new URL(window.location);
                            url.searchParams.delete('loginSuccess');
                            window.history.replaceState({}, document.title, url);
                        }

                        window.addEventListener("DOMContentLoaded", () => {
                            const params = new URLSearchParams(window.location.search);
                            if (params.get("loginSuccess") === "true") {
                                const loginModal = new bootstrap.Modal(document.getElementById('loginSuccessModal'));
                                loginModal.show();

                                // Tự động ẩn sau 3 giây
                                setTimeout(() => {
                                    closeLoginModal();
                                }, 3000);
                            }
                        });

    </script>
</html>
