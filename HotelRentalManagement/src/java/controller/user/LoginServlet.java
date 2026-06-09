/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.user;

import dal.LoginHistoryDAO;
import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

/**
 *
 * @author ddhoang
 */
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("views/user/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UserDAO u = new UserDAO();
        User user = u.getUserByUsernamePassword(username, password);

        // 1. Kiểm tra tài khoản tồn tại
        if (user == null) {
            String msg = "Sai thông tin tài khoản hoặc mật khẩu!";
            request.setAttribute("msg", msg);
            request.getRequestDispatcher("views/user/login.jsp").forward(request, response);
            return;
        }

        // 2. Kiểm tra tài khoản bị khóa
        if (user.getStatus() == 0) {
            String msg = "Tài khoản hiện đang bị khóa. Vui lòng liên hệ trực tiếp với nhân viên!";
            request.setAttribute("msg", msg);
            request.getRequestDispatcher("views/user/login.jsp").forward(request, response);
            return;
        }

        // 3. Đăng nhập thành công
        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        if (user.getRoleid() == 1) {
            LoginHistoryDAO ld = new LoginHistoryDAO();
            ld.insertLogin(user.getId());
        }
        // 4. Điều hướng theo vai trò
        switch (user.getRoleid()) {
            case 2:
                response.sendRedirect("listuser?loginSuccess=true");
                break;
            case 1:
                response.sendRedirect("home?loginSuccess=true");
                break;
            case 4:
                response.sendRedirect("listBooking?loginSuccess=true");
                break;
            case 3:
                response.sendRedirect("chart?loginSuccess=true");
                break;
            default:
                response.sendRedirect("error.jsp");
                break;
        }
    }

}
