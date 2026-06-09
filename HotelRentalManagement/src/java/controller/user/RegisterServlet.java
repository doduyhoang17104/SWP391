/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.user;

import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.PasswordUtil;
import model.User;

/**
 *
 * @author ddhoang
 */
public class RegisterServlet extends HttpServlet {

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
            out.println("<title>Servlet registerControl</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet registerControl at " + request.getContextPath() + "</h1>");
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
        request.getRequestDispatcher("views/user/register.jsp").forward(request, response);
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
        String name = request.getParameter("name");
        String username = request.getParameter("username");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String gmail = request.getParameter("gmail");
        String password = request.getParameter("password");
        String repassword = request.getParameter("repassword");
        if (!phone.matches("^0[0-9]{9,10}$")) {
            request.setAttribute("msg", "Số điện thoại không hợp lệ!");
            request.getRequestDispatcher("views/user/register.jsp").forward(request, response);
            return;
        }
        if (!name.matches("^[\\p{L}\\s]+$")) {
            request.setAttribute("msg", "Họ và tên không được chứa số hoặc ký tự đặc biệt!");
            request.getRequestDispatcher("views/user/register.jsp").forward(request, response);
            return;
        }
        if (!address.matches("^[\\p{L}\\s]+$")) {
            request.setAttribute("msg", "Địa chỉ không được chứa số hoặc ký tự đặc biệt!");
            request.getRequestDispatcher("views/user/register.jsp").forward(request, response);
            return;
        }
        if (!password.equals(repassword)) {
            request.setAttribute("msg", "Mật khẩu không trùng khớp!");
            request.getRequestDispatcher("views/user/register.jsp").forward(request, response);
        } else {
            UserDAO ud = new UserDAO();
            User userByUsername = ud.getUserByUsername(username);
            User userByEmail = ud.getUserByEmail(gmail); // thêm dòng này

            if (userByUsername != null) {
                request.setAttribute("msg", "Tên đăng nhập đã tồn tại!");
                request.getRequestDispatcher("views/user/register.jsp").forward(request, response);
            } else if (userByEmail != null) {
                request.setAttribute("msg", "Email đã được sử dụng để đăng ký!");
                request.getRequestDispatcher("views/user/register.jsp").forward(request, response);
            } else {
                byte[] salt = PasswordUtil.generateSalt(); // Tạo salt mới
                System.out.println(salt);
                byte[] hashedPassword = null;
                try {
                    hashedPassword = PasswordUtil.hashPassword(password.toCharArray(), salt);
                    System.out.println(hashedPassword);;
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
                ud.addUser(gmail, username, name, hashedPassword, salt, phone, address);
                request.setAttribute("msg", "Đăng kí tài khoản thành công");
                request.getRequestDispatcher("views/user/register.jsp").forward(request, response);
            }

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
