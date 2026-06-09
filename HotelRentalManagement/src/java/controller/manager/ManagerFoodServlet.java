package controller.manager;

import dal.FoodDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Food;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ManagerFoodServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lấy các tham số tìm kiếm và phân trang từ request
        String keyword = request.getParameter("keyword");
        String category = request.getParameter("category");
        String priceFromParam = request.getParameter("priceFrom");
        String priceToParam = request.getParameter("priceTo");

        // Chuyển các tham số giá thành số nếu có
        Double priceFrom = (priceFromParam != null && !priceFromParam.isEmpty()) ? Double.parseDouble(priceFromParam) : null;
        Double priceTo = (priceToParam != null && !priceToParam.isEmpty()) ? Double.parseDouble(priceToParam) : null;

        // Lấy tham số phân trang
        String pageParam = request.getParameter("page");
        int page = 1;
        int recordsPerPage = 5;

        if (pageParam != null) {
            try {
                page = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                page = 1;
            }
        }

        int offset = (page - 1) * recordsPerPage;

        try {
            FoodDAO dao = new FoodDAO();
            List<Food> foodList;
            int totalRecords;

            // Tìm kiếm với các tham số keyword, category, priceFrom, priceTo
            foodList = dao.searchFood(keyword, category, priceFrom, priceTo, offset, recordsPerPage);
            totalRecords = dao.getTotalFoodCount(keyword, category, priceFrom, priceTo);

            // Tính tổng số trang
            int totalPages = (int) Math.ceil((double) totalRecords / recordsPerPage);

            // Truyền các tham số vào JSP
            request.setAttribute("foodList", foodList);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("keyword", keyword);
            request.setAttribute("category", category);
            request.setAttribute("priceFrom", priceFrom);
            request.setAttribute("priceTo", priceTo);

            // Chuyển hướng tới trang JSP để hiển thị kết quả
            request.getRequestDispatcher("views/dashboard/managerfood.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
