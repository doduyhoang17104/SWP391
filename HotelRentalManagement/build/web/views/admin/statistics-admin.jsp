<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<!-- Thống kê tài khoản -->
<div class="d-flex justify-content-between align-items-center mb-4">
    <h2 class="text-primary"><i class="fas fa-chart-bar me-2"></i>Thống kê tài khoản</h2>
</div>

<div class="row g-4 mb-4">
    <div class="col-md-4">
        <div class="card-stat bg-total">
            <div class="stat-icon"><i class="fas fa-users"></i></div>
            <div>
                <div class="stat-title">Tổng tài khoản</div>
                <div class="stat-value">${total}</div>
            </div>
        </div>
    </div>

    <div class="col-md-4">
        <div class="card-stat bg-active">
            <div class="stat-icon"><i class="fas fa-user-check"></i></div>
            <div>
                <div class="stat-title">Tài khoản hoạt động</div>
                <div class="stat-value">${active}</div>
            </div>
        </div>
    </div>

    <div class="col-md-4">
        <div class="card-stat bg-locked">
            <div class="stat-icon"><i class="fas fa-user-lock"></i></div>
            <div>
                <div class="stat-title">Tài khoản bị khóa</div>
                <div class="stat-value">${locked}</div>
            </div>
        </div>
    </div>

    <div class="col-md-4">
        <div class="card-stat bg-admin">
            <div class="stat-icon"><i class="fas fa-user-shield"></i></div>
            <div>
                <div class="stat-title">Admin</div>
                <div class="stat-value">${admin}</div>
            </div>
        </div>
    </div>

    <div class="col-md-4">
        <div class="card-stat bg-customer">
            <div class="stat-icon"><i class="fas fa-user"></i></div>
            <div>
                <div class="stat-title">Khách hàng</div>
                <div class="stat-value">${customer}</div>
            </div>
        </div>
    </div>

    <div class="col-md-4">
        <div class="card-stat bg-reception">
            <div class="stat-icon"><i class="fas fa-concierge-bell"></i></div>
            <div>
                <div class="stat-title">Lễ tân</div>
                <div class="stat-value">${receptionist}</div>
            </div>
        </div>
    </div>
</div>

