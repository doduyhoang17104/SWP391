/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.user;

import dal.BookingDAO;
import dal.InvoiceDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import model.Booking;

/**
 *
 * @author ddhoang
 */
public class CancelBookingServlet extends HttpServlet {

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
            out.println("<title>Servlet CancelBoookingServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CancelBoookingServlet at " + request.getContextPath() + "</h1>");
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

        try {
            String status = request.getParameter("status");
            int bId = Integer.parseInt(request.getParameter("bookingId"));
            System.out.println(bId);
            BookingDAO bd = new BookingDAO();
            Booking booking = bd.getBookingById(bId);
            HttpSession session = request.getSession();
            if (booking == null) {
                request.setAttribute("error", "Không tìm thấy đơn đặt phòng.");
            } else {
                // Lấy ngày checkin
                java.sql.Date checkInDate = (java.sql.Date) booking.getCheckIn();
                LocalDate checkinLocalDate = checkInDate.toLocalDate();
                LocalDate today = LocalDate.now();

                boolean canCancel = false;

                // Điều kiện huỷ
                if ("pending".equalsIgnoreCase(status)) {
                    canCancel = true;
                } else if ("approve".equalsIgnoreCase(status)) {
                    if (today.isBefore(checkinLocalDate.minusDays(1))) {
                        canCancel = true;
                    }
                }

                // Tiến hành huỷ nếu đủ điều kiện
                if (canCancel) {
                    boolean cancelSuccess = bd.updateStatusBookingByBookingId(bId, "cancel", null);
                    if (cancelSuccess) {
                        session.setAttribute("success", "Huỷ đơn thành công.");
                    } else {
                        session.setAttribute("error", "Không thể huỷ đơn do lỗi hệ thống.");
                    }
                } else {
                    session.setAttribute("error", "Chỉ có thể huỷ đơn trước ngày check-in 1 ngày.");
                }
            }

            // Trả về trang kết quả
            response.sendRedirect("mybooking");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Đã xảy ra lỗi trong quá trình xử lý huỷ đơn.");
            request.getRequestDispatcher("views/user/status-booking.jsp").forward(request, response);
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
