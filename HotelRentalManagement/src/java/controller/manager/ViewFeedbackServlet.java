package controller.manager;

import dal.FeedbackDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import model.Feedback;

public class ViewFeedbackServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");
        try {
            int id = Integer.parseInt(idParam);
            FeedbackDAO dao = new FeedbackDAO();
            Feedback feedback = dao.getFeedbackById(id);

            if (feedback != null) {
                request.setAttribute("feedback", feedback);
                request.getRequestDispatcher("viewfeedback.jsp").forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Feedback không tồn tại.");
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID không hợp lệ.");
        }
    }
}
