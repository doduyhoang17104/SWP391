package controller.manager;

import dal.FoodOrderDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.FoodOrder;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ManagerOrderServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        FoodOrderDAO dao = new FoodOrderDAO();
        try {
            List<FoodOrder> orders = dao.getAllOrders();
            request.setAttribute("orders", orders);
            request.getRequestDispatcher("views/dashboard/managerorder.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(500, "Lỗi khi lấy danh sách đơn hàng.");
        }
    }
}
