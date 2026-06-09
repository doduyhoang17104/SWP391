package controller.manager;

import dal.FoodDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Food;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class RestaurantServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            request.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");

            String keyword = request.getParameter("keyword");
            String category = request.getParameter("category");
            String priceFromStr = request.getParameter("priceFrom");
            String priceToStr = request.getParameter("priceTo");

            Double priceFrom = (priceFromStr != null && !priceFromStr.isEmpty()) ? Double.parseDouble(priceFromStr) : null;
            Double priceTo = (priceToStr != null && !priceToStr.isEmpty()) ? Double.parseDouble(priceToStr) : null;

            int page = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
            int limit = 8; // mặc định 8 món ăn mỗi trang
            int offset = (page - 1) * limit;

            FoodDAO dao = new FoodDAO();

            List<Food> foodList;
            int totalPages = 1;

            // Nếu có bộ lọc tìm kiếm → dùng search
            if ((keyword != null && !keyword.trim().isEmpty()) ||
                (category != null && !category.isEmpty()) ||
                priceFrom != null || priceTo != null) {

                foodList = dao.searchFood(keyword, category, priceFrom, priceTo, offset, limit);
                int totalFood = dao.countSearchFood(keyword, category, priceFrom, priceTo);
                totalPages = (int) Math.ceil((double) totalFood / limit);
            } else {
                // Không tìm → lấy 8 món đầu tiên để hiển thị trang chủ
                foodList = dao.getLimitedFood(limit);
            }

            request.setAttribute("foodList", foodList);
            request.setAttribute("keyword", keyword);
            request.setAttribute("category", category);
            request.setAttribute("priceFrom", priceFromStr);
            request.setAttribute("priceTo", priceToStr);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);

            request.getRequestDispatcher("restaurant.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi xử lý dữ liệu món ăn");
        }
    }
}
