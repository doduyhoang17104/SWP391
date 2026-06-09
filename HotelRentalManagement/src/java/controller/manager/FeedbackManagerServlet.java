package controller.manager;

import dal.FeedbackDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import model.Feedback;

public class FeedbackManagerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int pageSize = 5;
        int page = 1;

        try {
            String pageParam = request.getParameter("page");
            if (pageParam != null && !pageParam.trim().isEmpty()) {
                page = Integer.parseInt(pageParam);
                if (page <= 0) {
                    page = 1;
                }
            }
        } catch (NumberFormatException e) {
            page = 1;
        }

        String nameKeyword = request.getParameter("nameKeyword");
        String dateKeyword = request.getParameter("dateKeyword");

        String ratingRaw = request.getParameter("ratingFilter");
        Integer ratingFilter = null;
        if (ratingRaw != null && !ratingRaw.trim().isEmpty()) {
            try {
                ratingFilter = Integer.parseInt(ratingRaw);
            } catch (NumberFormatException e) {
                ratingFilter = null;
            }
        }

        FeedbackDAO dao = new FeedbackDAO();

        // ✅ Lấy thống kê sao và điểm trung bình
        Map<Integer, Integer> ratingStats = dao.countByRating();
        double averageRating = dao.getAverageRating();
        int reviewCount = dao.getTotalFeedback();

        // ✅ Lấy danh sách feedback phân trang + lọc
        int totalFeedbacks = dao.countFilteredFeedbacks(nameKeyword, dateKeyword, ratingFilter);
        int totalPages = (int) Math.ceil((double) totalFeedbacks / pageSize);

        List<Feedback> feedbackList = dao.searchFeedbacksByPage(
                nameKeyword,
                dateKeyword,
                ratingFilter,
                page,
                pageSize
        );

        // ✅ Đưa dữ liệu lên JSP
        request.setAttribute("feedbackList", feedbackList);
        request.setAttribute("nameKeyword", nameKeyword);
        request.setAttribute("dateKeyword", dateKeyword);
        request.setAttribute("ratingFilter", ratingRaw);
        request.setAttribute("page", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalFeedback", totalFeedbacks);

        request.setAttribute("ratingStats", ratingStats);
        request.setAttribute("averageRating", averageRating);
        request.setAttribute("reviewCount", reviewCount); // hoặc totalFeedbacks nếu muốn dùng chung

        // ✅ Forward cuối cùng
        request.getRequestDispatcher("feedbackmanager.jsp").forward(request, response);
    }
}
