package controller.manager;

import dal.CommentDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Comment;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "CommentManagerServlet", urlPatterns = {"/commentmanager"})
public class CommentManagerServlet extends HttpServlet {

    private static final int PAGE_SIZE = 5;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        CommentDAO dao = new CommentDAO();

        // Lấy các tham số tìm kiếm từ request
        String id = request.getParameter("id");
        String author = request.getParameter("author");
        String content = request.getParameter("content");
        String date = request.getParameter("date");

        // Xử lý xoá nếu có action delete
        String action = request.getParameter("action");
        String idParam = request.getParameter("id");

        if ("delete".equalsIgnoreCase(action) && idParam != null) {
            dao.deleteCommentById(idParam);
            response.sendRedirect("commentmanager");
            return;
        }

        // Phân trang
        int page = 1;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (Exception ignored) {}

        // Đếm và tìm kiếm có điều kiện
        int totalComments = dao.countAllComments(id, author, content, date);
        int totalPages = (int) Math.ceil((double) totalComments / PAGE_SIZE);

        List<Comment> comments = dao.searchComments(id, author, content, date, page, PAGE_SIZE);
        // Gửi dữ liệu ra JSP
        request.setAttribute("comments", comments);
        request.setAttribute("id", id);
        request.setAttribute("author", author);
        request.setAttribute("content", content);
        request.setAttribute("date", date);
        request.setAttribute("page", page);
        request.setAttribute("totalPages", totalPages);

        request.getRequestDispatcher("views/dashboard/commentManager.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        CommentDAO dao = new CommentDAO();

        if ("update".equalsIgnoreCase(action)) {
            try {
                int commentId = Integer.parseInt(request.getParameter("commentId"));
                String newContent = request.getParameter("newContent");
                dao.updateCommentContent(commentId, newContent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        response.sendRedirect("commentmanager");
    }
}
