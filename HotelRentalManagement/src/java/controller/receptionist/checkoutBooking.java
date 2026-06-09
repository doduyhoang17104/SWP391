/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.receptionist;

import dal.BookingDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;
import model.Booking;

/**
 *
 * @author admin
 */
public class checkoutBooking extends HttpServlet {

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
            out.println("<title>Servlet checkoutBooking</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet checkoutBooking at " + request.getContextPath() + "</h1>");
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
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        String dateParam = request.getParameter("selectedDate");

        LocalDate selectedDate;
        try {
            selectedDate = (dateParam != null && !dateParam.isEmpty())
                    ? LocalDate.parse(dateParam)
                    : LocalDate.now();
        } catch (Exception e) {
            selectedDate = LocalDate.now();
        }

        BookingDAO dao = new BookingDAO();
        List<Booking> bookings = dao.getCurrentCheckinBookings(selectedDate);

        request.setAttribute("todayBookings", bookings);
        request.setAttribute("selectedDate", selectedDate.toString());
        request.getRequestDispatcher("views/receptionist/checkout-booking.jsp").forward(request, response);
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

        String roomId = request.getParameter("roomId");
        String bookingId = request.getParameter("bookingId");
        String roomNumber = request.getParameter("roomNumber");
        String checkin = request.getParameter("checkin");
        String checkout = request.getParameter("checkout");

        if (roomId == null || bookingId == null || roomNumber == null
                || checkin == null || checkout == null
                || roomId.isEmpty() || bookingId.isEmpty()) {

            request.setAttribute("error", "Thiếu thông tin cần thiết để check-out!");
            request.getRequestDispatcher("views/receptionist/checkout-booking.jsp").forward(request, response);
            return;
        }

        try {
            int roomIdInt = Integer.parseInt(roomId);
            int bookingIdInt = Integer.parseInt(bookingId);

            BookingDAO dao = new BookingDAO();
            boolean success = dao.updateBookingStatus(bookingIdInt, "checkout");

            if (!success) {
                request.setAttribute("error", "Không thể cập nhật trạng thái check-out!");
                request.getRequestDispatcher("views/receptionist/checkout-booking.jsp").forward(request, response);
                return;
            }

            session.setAttribute("roomId", roomIdInt);
            session.setAttribute("bookingId", bookingIdInt);
            session.setAttribute("roomNumber", roomNumber);
            session.setAttribute("checkin", checkin);
            session.setAttribute("checkout", checkout);

            response.sendRedirect("checkoutBooking?selectedDate=" + checkout);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Dữ liệu không hợp lệ.");
            request.getRequestDispatcher("views/receptionist/checkout-booking.jsp").forward(request, response);
        }
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
