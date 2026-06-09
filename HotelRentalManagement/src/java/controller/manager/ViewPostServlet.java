/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.manager;

import dal.BlogPostDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.BlogPost;

import java.io.IOException;

@WebServlet("/viewPost")
public class ViewPostServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
        try {
            int postId = Integer.parseInt(request.getParameter("postId"));
            BlogPost post = BlogPostDAO.getPostById(postId);

            if (post == null) {
                request.setAttribute("error", "Không tìm thấy bài viết.");
            } else {
                request.setAttribute("post", post);
            }

            request.getRequestDispatcher("views/dashboard/viewPost.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Lỗi khi xem bài viết: " + e.getMessage());
        }
    }
}
