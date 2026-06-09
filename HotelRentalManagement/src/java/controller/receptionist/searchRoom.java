/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.receptionist;

import dal.BookingDAO;
import dal.RoomDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.Room;
import model.RoomBookingInfo;
import model.RoomType;
import java.time.temporal.ChronoUnit;
import java.sql.*;
import java.time.LocalDateTime;

/**
 *
 * @author admin
 */
public class searchRoom extends HttpServlet {

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
            out.println("<title>Servlet searchRoom</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet searchRoom at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

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

        String checkinStr = request.getParameter("checkin");
        String checkoutStr = request.getParameter("checkout");
        String guestsRaw = request.getParameter("guests");
        String roomType = request.getParameter("roomType");
        String bedStr = request.getParameter("bed");
        String maxPriceStr = request.getParameter("maxPrice");
        String minPriceStr = request.getParameter("minPrice");

        List<Room> rooms;
        List<RoomType> roomTypes = RoomDAO.getAllRoomTypes();

        if (checkinStr != null && checkoutStr != null && !checkinStr.isEmpty() && !checkoutStr.isEmpty()) {
            try {
                LocalDate checkin = LocalDate.parse(checkinStr);
                LocalDate checkout = LocalDate.parse(checkoutStr);
                LocalDateTime checkinDateTime = checkin.atTime(14, 0);
                LocalDateTime checkoutDateTime = checkout.atTime(12, 0);
                int guests = RoomDAO.extractTotalGuests(guestsRaw);
                int bed = Integer.parseInt(bedStr);
                double maxPrice = Double.parseDouble(maxPriceStr);
                double minPrice = Double.parseDouble(minPriceStr);

                rooms = new RoomDAO().searchAvailableRooms(checkin, checkout, guests, roomType, bed, minPrice, maxPrice);

                for (Room r : rooms) {
                    List<String> images = RoomDAO.getRoomImagesByRoomId(r.getRoomId());
                    r.setImageUrls(images);
                    boolean isBooked = BookingDAO.isRoomBookedInRangenew(r.getRoomId(), checkinDateTime, checkoutDateTime);
                    r.setBookedInRange(isBooked);
                    RoomBookingInfo bookingInfo = BookingDAO.getCurrentBookingForRoom(r.getRoomId());
                    if (bookingInfo != null && bookingInfo.getCheckOutDate() != null) {
                        r.setBookingCheckinDate(Timestamp.valueOf(bookingInfo.getCheckInDate()));
                        r.setBookingCheckoutDate(Timestamp.valueOf(bookingInfo.getCheckOutDate()));

                        LocalDate today = LocalDate.now();
                        LocalDate checkoutDate = bookingInfo.getCheckOutDate().toLocalDate();
                        long daysLeft = ChronoUnit.DAYS.between(today, checkoutDate);
                        if (daysLeft >= 0) {
                            r.setDaysLeft((int) daysLeft);
                        }
                    }

                }
                request.setAttribute("rooms", rooms);
                request.setAttribute("roomTypes", roomTypes);
                request.getRequestDispatcher("views/receptionist/roomBooking.jsp").forward(request, response);
                return;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String pageParam = request.getParameter("page");
        int pageIndex = 1;
        try {
            if (pageParam != null) {
                pageIndex = Integer.parseInt(pageParam);
                if (pageIndex < 1) {
                    pageIndex = 1;
                }
            }
        } catch (NumberFormatException e) {
            pageIndex = 1;
        }

        int pageSize = 8;
        rooms = RoomDAO.getRoomsWithImageAndPriceByPage(pageIndex, pageSize);
        int totalRooms = RoomDAO.getTotalRoomCount();
        int totalPages = (int) Math.ceil((double) totalRooms / pageSize);

        for (Room r : rooms) {
            List<String> images = RoomDAO.getRoomImagesByRoomId(r.getRoomId());
            r.setImageUrls(images);

            RoomBookingInfo bookingInfo = BookingDAO.getCurrentBookingForRoom(r.getRoomId());
            if (bookingInfo != null && bookingInfo.getCheckOutDate() != null) {

                r.setBookingCheckinDate(Timestamp.valueOf(bookingInfo.getCheckInDate()));
                r.setBookingCheckoutDate(Timestamp.valueOf(bookingInfo.getCheckOutDate()));

                LocalDate today = LocalDate.now();
                LocalDate checkoutDate = bookingInfo.getCheckOutDate().toLocalDate();
                long daysLeft = ChronoUnit.DAYS.between(today, checkoutDate);
                if (daysLeft >= 0) {
                    r.setDaysLeft((int) daysLeft);
                }
            }

        }
        request.setAttribute("rooms", rooms);
        request.setAttribute("roomTypes", roomTypes);
        request.setAttribute("pageIndex", pageIndex);
        request.setAttribute("totalPages", totalPages);

        request.getRequestDispatcher("views/receptionist/roomBooking.jsp").forward(request, response);
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
