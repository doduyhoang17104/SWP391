package controller.manager;

import dal.FeedbackDAO;
import model.Feedback;
import model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;

public class FeedbackServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false);
            User user = (User) session.getAttribute("user");

            if (user == null) {
                response.sendRedirect("login.jsp");
                return;
            }

            // Ép kiểu userId từ String → int
            int userId = Integer.parseInt(user.getId());

            int overallRating = Integer.parseInt(request.getParameter("overallRating"));
            int serviceRating = Integer.parseInt(request.getParameter("serviceRating"));
            String comment = request.getParameter("comment");
            String rentalPeriod = request.getParameter("rentalPeriod");

            Feedback feedback = new Feedback();
            feedback.setOverallRating(overallRating);
            feedback.setServiceRating(serviceRating);
            feedback.setComment(comment);
            feedback.setRentalPeriod(rentalPeriod);
            feedback.setUserId(userId);

            FeedbackDAO dao = new FeedbackDAO();
            dao.insertFeedback(feedback);

            response.sendRedirect("feedbacksuccess.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("feedback.jsp");
        }
    }
}