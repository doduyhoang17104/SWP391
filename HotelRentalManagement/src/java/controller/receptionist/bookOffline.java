/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.receptionist;

import dal.BookingDAO;
import dal.InvoiceDAO;
import dal.RoomDAO;
import dal.ServicesDAO;
import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import model.Service.Service;
import model.ServiceCategory;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import model.Room;
import model.RoomType;
import model.User;

/**
 *
 * @author admin
 */
public class bookOffline extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int roomNumber = Integer.parseInt(request.getParameter("roomNumber"));
        int roomId = Integer.parseInt(request.getParameter("roomId"));
        String checkin = request.getParameter("checkin");
        String checkout = request.getParameter("checkout");

        HttpSession session = request.getSession();
        session.setAttribute("roomId", roomId);
        session.setAttribute("roomNumber", roomNumber);
        session.setAttribute("checkin", checkin);
        session.setAttribute("checkout", checkout);

        request.getRequestDispatcher("views/receptionist/bookingoffline-information.jsp").forward(request, response);
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
        System.out.println("đã vào đây");
        String fullName = request.getParameter("fullName");
        String phone = request.getParameter("phoneNumber");
        String address = request.getParameter("address");
        String identityCard = request.getParameter("identityCard");
        String nameRegex = "^[\\p{L}\\s]+$";
        String addressRegex = "^[\\p{L}\\s]+$";
        String phoneRegex = "^\\d{10}$";
        String idCardRegex = "^0\\d{11}$";

        if (!fullName.matches(nameRegex)) {
            request.setAttribute("errorMessage", "Họ tên không được chứa số hoặc kí tự đặc biệt.");
            request.setAttribute("fullName", fullName);
            request.setAttribute("phoneNumber", phone);
            request.setAttribute("address", address);
            request.setAttribute("identityCard", identityCard);
            request.getRequestDispatcher("views/receptionist/bookingoffline-information.jsp").forward(request, response);
            return;
        }

        if (!address.matches(addressRegex)) {
            request.setAttribute("errorMessage", "địa chỉ không được chứa số hoặc kí tự đặc biệt.");
            request.setAttribute("fullName", fullName);
            request.setAttribute("phoneNumber", phone);
            request.setAttribute("address", address);
            request.setAttribute("identityCard", identityCard);
            request.getRequestDispatcher("views/receptionist/bookingoffline-information.jsp").forward(request, response);
            return;
        }
        
        if (!phone.matches(phoneRegex)) {
            request.setAttribute("errorMessage", "Số điện thoại phải ghi 10 chữ số.");
            request.setAttribute("fullName", fullName);
            request.setAttribute("phoneNumber", phone);
            request.setAttribute("address", address);
            request.setAttribute("identityCard", identityCard);
            request.getRequestDispatcher("views/receptionist/bookingoffline-information.jsp").forward(request, response);
            return;
        }

        if (!identityCard.matches(idCardRegex)) {
            request.setAttribute("errorMessage", "CCCD đủ 12 số và đúng cấu trúc");
            request.setAttribute("fullName", fullName);
            request.setAttribute("phoneNumber", phone);
            request.setAttribute("address", address);
            request.setAttribute("identityCard", identityCard);
            request.getRequestDispatcher("views/receptionist/bookingoffline-information.jsp").forward(request, response);
            return;
        }
        HttpSession session = request.getSession();
        Integer roomIdObj = (Integer) session.getAttribute("roomId");
        String checkin = (String) session.getAttribute("checkin");
        String checkout = (String) session.getAttribute("checkout");
        User receptionist = (User) session.getAttribute("user");

        if (roomIdObj == null || checkin == null || checkout == null || receptionist == null) {
            request.setAttribute("errorMessage", "Thiếu thông tin đặt phòng. Vui lòng quay lại bước trước.");
            request.getRequestDispatcher("searchRoom").forward(request, response);
            return;
        }

        int roomId = roomIdObj;
        int receptionistId = Integer.parseInt(receptionist.getId());

        try {
            Timestamp checkinTime = Timestamp.valueOf(checkin + " 14:00:00");
            Timestamp checkoutTime = Timestamp.valueOf(checkout + " 12:00:00");

            Room room = RoomDAO.getRoomDetailById(roomId);
            if (room == null || room.getRoomType() == null) {
                throw new Exception("Không thể lấy thông tin loại phòng.");
            }

            double basePrice = room.getRoomType().getBasePrice();
            BigDecimal pricePerNight = BigDecimal.valueOf(basePrice);

            long daysBetween = java.time.temporal.ChronoUnit.DAYS.between(
                    checkinTime.toLocalDateTime().toLocalDate(),
                    checkoutTime.toLocalDateTime().toLocalDate()
            );
            if (daysBetween <= 0) {
                daysBetween = 1;
            }

            BigDecimal totalAmount = pricePerNight.multiply(BigDecimal.valueOf(daysBetween));

            BookingDAO bookingDAO = new BookingDAO();
            int customerId = Integer.parseInt(receptionist.getId());

            int bookingId = bookingDAO.createBooking(
                    customerId,
                    roomId,
                    checkinTime,
                    checkoutTime,
                    null,
                    totalAmount
            );

            session.setAttribute("bookingId", bookingId);

            UserDAO offlineDAO = new UserDAO();
            offlineDAO.addOfflineBookingUser(
                    fullName,
                    phone,
                    address,
                    identityCard,
                    bookingId,
                    receptionistId
            );

            response.sendRedirect("/HotelRentalManagement/bookingService");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Lỗi khi tạo đơn đặt phòng: " + e.getMessage());
            request.getRequestDispatcher("listBooking").forward(request, response);
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
