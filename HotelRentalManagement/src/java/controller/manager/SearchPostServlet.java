/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.manager;

import dal.BlogPostDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.BlogPost;

/**
 *
 * @author ddhoang
 */
@WebServlet(name = "SearchPostServlet", urlPatterns = {"/searchpost"})

public class SearchPostServlet extends HttpServlet {

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
            out.println("<title>Servlet SearchPostServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SearchPostServlet at " + request.getContextPath() + "</h1>");
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
        String postIdStr = request.getParameter("postId");
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String content = request.getParameter("content");

        String createdFrom = request.getParameter("createdFrom");
        String createdTo = request.getParameter("createdTo");
        String updatedFrom = request.getParameter("updatedFrom");
        String updatedTo = request.getParameter("updatedTo");

        Integer postId = null;
        try {
            if (postIdStr != null && !postIdStr.isEmpty()) {
                postId = Integer.parseInt(postIdStr);
            }
        } catch (NumberFormatException e) {
            postId = null;
        }

        BlogPostDAO dao = new BlogPostDAO();
        List<BlogPost> result = dao.advancedSearchPosts(postId, title, author, content,
                createdFrom, createdTo,
                updatedFrom, updatedTo);

        request.setAttribute("posts", result);
        request.getRequestDispatcher("views/dashboard/listPost.jsp").forward(request, response);
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
        String keyword = request.getParameter("key");
        String author = request.getParameter("author");
        String date = request.getParameter("date");
        System.out.println(date);
        BlogPostDAO bp = new BlogPostDAO();
        List<BlogPost> listsearch = bp.searchPostsFlexible(keyword, author, date);
        System.out.println(listsearch.size());
        request.setAttribute("posts", listsearch);

        request.getRequestDispatcher("blog.jsp").forward(request, response);
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
