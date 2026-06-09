/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.manager;

import dal.BookingDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.EmailUtil;
import model.User;

/**
 *
 * @author ddhoang
 */
public class ApproveBookingServlet extends HttpServlet {

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
            out.println("<title>Servlet ApproveBooingServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ApproveBooingServlet at " + request.getContextPath() + "</h1>");
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
        processRequest(request, response);
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
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String status = request.getParameter("action");
        int bookingId = Integer.parseInt(request.getParameter("bookingId"));
        String rejectReason = request.getParameter("rejectReason"); // Có thể null nếu không phải từ chối
        String checkInDateStr = request.getParameter("checkIn");
        String checkOutDateStr = request.getParameter("checkOut");
        BookingDAO bd = new BookingDAO();
        boolean success = bd.updateStatusBookingByBookingId(bookingId, status, rejectReason);

        if (success) {
            if ("approve".equalsIgnoreCase(status)) {
                String subject = "Thông báo duyệt đơn đặt phòng";
                String messageBody = "Yêu cầu đặt phòng của bạn đã được duyệt.\n"
                        + "Nhận phòng: " + checkInDateStr + "\n"
                        + "Trả phòng: " + checkOutDateStr;
                EmailUtil.sendBookingNotification(user.getEmail(), subject, user.getName(), messageBody);

                request.getSession().setAttribute("successMessage", "Đơn #" + bookingId + " đã được phê duyệt.");
            } else if ("reject".equalsIgnoreCase(status)) {
                String subject = "Thông báo từ chối đơn đặt phòng";
                String messageBody = "Chúng tôi rất tiếc phải thông báo rằng yêu cầu đặt phòng của bạn đã bị từ chối.\n"
                        + "Lý do: " + rejectReason;
                EmailUtil.sendBookingNotification(user.getEmail(), subject, user.getName(), messageBody);

                request.getSession().setAttribute("rejectMessage", "Đơn #" + bookingId + " đã bị từ chối.");
            }
        } else {
            request.getSession().setAttribute("errorMessage", "Có lỗi xảy ra khi cập nhật đơn #" + bookingId);
        }

        response.sendRedirect("listapprovebooking");
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
