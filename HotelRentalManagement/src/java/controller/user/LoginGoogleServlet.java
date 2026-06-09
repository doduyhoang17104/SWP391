
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.user;

import dal.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Objects;
import model.GoogleAccount;
import model.GoogleLogin;
import model.PasswordUtil;
import model.User;
import dal.LoginHistoryDAO;

/**
 *
 * @author ddhoang
 */
public class LoginGoogleServlet extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        String code = request.getParameter("code");
        if (code == null || code.isEmpty()) {
            response.sendRedirect("login?error=missing_code");
            return;
        }
        GoogleLogin gg = new GoogleLogin();
        String accessToken = gg.getToken(code);
        if (accessToken == null) {
            response.sendRedirect("login?error=token_failed");
            return;
        }
        GoogleAccount gAcc = gg.getUserInfo(accessToken);
        if (gAcc == null || gAcc.getEmail() == null) {
            response.sendRedirect("login?error=userinfo_failed");
            return;
        }
        System.out.println(gAcc);
        String email = gAcc.getEmail();
        String name = gAcc.getName();
        UserDAO dao = new UserDAO();
        User user = dao.getUserByEmail(email);
        // Nếu chưa có tài khoản → tạo mới
        if (user == null) {
            String randomPass = dao.generateRandomPassword(8);
            byte[] salt = PasswordUtil.generateSalt();
            byte[] hashedPassword;
            try {
                hashedPassword = PasswordUtil.hashPassword(randomPass.toCharArray(), salt);
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect("login?error=password_hash");
                return;
            }
            dao.insertGoogleAccount(email, name, hashedPassword, salt);
            user = dao.getUserByEmail(email);
        }
        // Kiểm tra nếu tài khoản bị khóa
        if (user.getStatus() == 0) {
            request.setAttribute("msg", "Tài khoản hiện đang bị khóa. Vui lòng liên hệ trực tiếp với nhân viên!");
            request.getRequestDispatcher("views/user/login.jsp").forward(request, response);
            return;
        }
        // Đăng nhập thành công
        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        if (user.getRoleid() == 1) {
            LoginHistoryDAO ld = new LoginHistoryDAO();
            ld.insertLogin(user.getId());
        }
        // Điều hướng theo vai trò
        switch (user.getRoleid()) {
            case 2:
                response.sendRedirect("listuser?loginSuccess=true");
                break;
            case 1:
                response.sendRedirect("home?loginSuccess=true");
                break;
            case 3:
                response.sendRedirect("chart?loginSuccess=true");
                break;
            case 4:
                response.sendRedirect("listBooking?loginSuccess=true");
                break;
            default:
                response.sendRedirect("home");
                break;
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
        processRequest(request, response);
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
