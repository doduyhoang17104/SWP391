package controller.manager;

import dal.PromotionDAO;
import model.Promotion;
import java.io.IOException;
import java.util.ArrayList;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "PromotionListServlet", urlPatterns = {"/promotionList"})
public class PromotionListServlet extends HttpServlet {
    private static final int PAGE_SIZE = 10;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PromotionDAO dao = new PromotionDAO();
        String promotionName = request.getParameter("promotionName");
        String discountPercent = request.getParameter("discountPercent");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String quantity = request.getParameter("quantity");
        String status = request.getParameter("status");

        boolean hasSearch = (promotionName != null && !promotionName.trim().isEmpty()) ||
                            (discountPercent != null && !discountPercent.trim().isEmpty()) ||
                            (startDate != null && !startDate.trim().isEmpty()) ||
                            (endDate != null && !endDate.trim().isEmpty()) ||
                            (quantity != null && !quantity.trim().isEmpty()) ||
                            (status != null && !status.trim().isEmpty());

        ArrayList<Promotion> promotions;
        if (hasSearch) {
            promotions = dao.searchPromotions(promotionName, discountPercent, startDate, endDate, quantity, status);
        } else {
            promotions = dao.getAllPromotions();
        }
        request.setAttribute("promotions", promotions);
        request.getRequestDispatcher("views/dashboard/promotion-list.jsp").forward(request, response);
    }
} 