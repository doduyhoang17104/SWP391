package controller.manager;

import dal.FoodOrderDAO;
import dal.BookingDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Food;
import model.FoodOrder;
import model.FoodOrderDetail;

public class CheckoutFoodServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        // Lấy giỏ hàng từ session
        List<Food> cart = (List<Food>) session.getAttribute("cart");

        if (cart == null || cart.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/listfood");
            return;
        }

        // Lấy Booking_Id từ session
        Integer bookingId = (Integer) session.getAttribute("bookingId");

        if (bookingId == null) {
            response.sendRedirect(request.getContextPath() + "/views/dashboard/booking.jsp?error=missingBooking");
            return;
        }

        // ✅ Kiểm tra bookingId có tồn tại trong bảng Booking không
        try {
            BookingDAO bookingDAO = new BookingDAO();
            boolean exists = bookingDAO.existsBookingId(bookingId);

            if (!exists) {
                response.sendRedirect(request.getContextPath() + "/views/dashboard/cart.jsp?success=false&reason=invalidBooking");
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/views/dashboard/cart.jsp?success=false&reason=bookingCheckError");
            return;
        }

        // Tạo danh sách chi tiết đơn hàng
        List<FoodOrderDetail> details = new ArrayList<>();
        for (Food food : cart) {
            FoodOrderDetail detail = new FoodOrderDetail(0, food, 1, food.getPrice()); // số lượng mặc định = 1
            details.add(detail);
        }

        // Tạo đối tượng đơn hàng
        FoodOrder order = new FoodOrder();
        order.setBookingId(bookingId);
        order.setOrderTime(new Date());
        order.setStatus("Đang chờ xử lý");
        order.setNote("Khách đặt từ web");
        order.setDetails(details);

        // Lưu đơn hàng vào DB
        FoodOrderDAO dao = new FoodOrderDAO();
        try {
            dao.insertFoodOrder(order);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Lỗi khi ghi đơn hàng: " + e.getMessage(), e);
        }

        // Xoá giỏ hàng sau khi đặt thành công
        session.removeAttribute("cart");

        // Chuyển hướng tới trang giỏ hàng với thông báo thành công
        response.sendRedirect(request.getContextPath() + "/views/dashboard/cart.jsp?success=true");
    }
}
