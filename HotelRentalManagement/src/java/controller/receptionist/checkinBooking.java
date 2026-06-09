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
public class checkinBooking extends HttpServlet {

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
        List<Booking> bookings = dao.getCurrentApprovedBookings(selectedDate);

        request.setAttribute("todayBookings", bookings);
        request.setAttribute("selectedDate", selectedDate.toString());
        request.getRequestDispatcher("views/receptionist/checkin-booking.jsp").forward(request, response);
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

            request.setAttribute("error", "Thiếu thông tin cần thiết để check-in!");
            request.getRequestDispatcher("views/receptionist/checkin-booking.jsp").forward(request, response);
            return;
        }

        try {
            int roomIdInt = Integer.parseInt(roomId);
            int bookingIdInt = Integer.parseInt(bookingId);

            BookingDAO dao = new BookingDAO();
            boolean success = dao.updateBookingStatus(bookingIdInt, "checkin");

            if (!success) {
                request.setAttribute("error", "Không thể cập nhật trạng thái check-in!");
                request.getRequestDispatcher("views/receptionist/checkin-booking.jsp").forward(request, response);
                return;
            }

            session.setAttribute("roomId", roomIdInt);
            session.setAttribute("bookingId", bookingIdInt);
            session.setAttribute("roomNumber", roomNumber);
            session.setAttribute("checkin", checkin);
            session.setAttribute("checkout", checkout);

            response.sendRedirect("checkinBooking?selectedDate=" + checkin + "&checkinSuccess=true");
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Dữ liệu không hợp lệ.");
            request.getRequestDispatcher("views/receptionist/checkin-booking.jsp").forward(request, response);
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
