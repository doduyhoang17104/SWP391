package controller.manager;

import dal.FoodOrderDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.FoodOrder;

import java.io.IOException;
import java.util.List;

public class FoodOrderListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            FoodOrderDAO dao = new FoodOrderDAO();
            List<FoodOrder> orders = dao.getAllOrders();
            request.setAttribute("orders", orders);
            request.getRequestDispatcher("views/dashboard/orderlist.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
 