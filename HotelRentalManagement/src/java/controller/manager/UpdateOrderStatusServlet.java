package controller.manager;

import dal.FoodOrderDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet(name = "UpdateOrderStatusServlet", urlPatterns = {"/updateorderstatus"})
public class UpdateOrderStatusServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int orderId = Integer.parseInt(request.getParameter("orderId"));
        String newStatus = request.getParameter("newStatus");

        try {
            FoodOrderDAO dao = new FoodOrderDAO();
            dao.updateOrderStatus(orderId, newStatus);
            response.sendRedirect("managerorder");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500, "Không thể cập nhật trạng thái đơn hàng.");
        }
    }
}
