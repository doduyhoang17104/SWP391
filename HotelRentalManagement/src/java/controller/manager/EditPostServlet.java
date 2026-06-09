/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.manager;

import dal.BlogPostDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.nio.file.Paths;
import java.util.Date;
import model.BlogPost;
import java.sql.*;

/**
 *
 * @author ADMIN
 */
public class EditPostServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet editPost</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet editPost at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy ID từ URL ?id=xxx
        String postIdStr = request.getParameter("id");

        if (postIdStr == null || postIdStr.isEmpty()) {
            response.sendRedirect("listpost"); // Trở về danh sách nếu không có ID
            return;
        }

        try {
            int postId = Integer.parseInt(postIdStr);
            BlogPost post = BlogPostDAO.getPostById(postId);

            if (post == null) {
                response.sendRedirect("listpost");
                return;
            }

            request.setAttribute("post", post);
            request.getRequestDispatcher("/views/dashboard/editPost.jsp").forward(request, response); // Trang form chỉnh sửa
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("listpost");
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int postId = Integer.parseInt(request.getParameter("postId"));
            String title = request.getParameter("title");
            String content = request.getParameter("content");
            String image = request.getParameter("image");

            BlogPost post = new BlogPost();
            post.setPostId(postId);
            post.setTitle(title);
            post.setContent(content);
            post.setImage(image);

            boolean success = BlogPostDAO.editPost(post);

            if (success) {
                response.sendRedirect("listpost?message=updated");
            } else {
                request.setAttribute("error", "Cập nhật thất bại.");
                request.setAttribute("post", post);
                request.getRequestDispatcher("views/dashboard/editPost.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi hệ thống: " + e.getMessage());
            request.getRequestDispatcher("views/dashboard/editPost.jsp").forward(request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
