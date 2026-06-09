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
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import model.Room;
import model.RoomBookingInfo;

/**
 *
 * @author admin
 */
public class listBooking extends HttpServlet {

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
            out.println("<title>Servlet listBooking</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet listBooking at " + request.getContextPath() + "</h1>");
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

        // 1. Lập danh sách ngày hiển thị
        LocalDate today = LocalDate.now();
        List<String> dayOfWeekList = new ArrayList<>();
        List<Integer> dayOfMonthList = new ArrayList<>();
        List<Boolean> isWeekendList = new ArrayList<>();

        for (int i = 0; i < 14; i++) {
            LocalDate date = today.plusDays(i);
            DayOfWeek dayOfWeek = date.getDayOfWeek();
            dayOfWeekList.add(dayOfWeek.toString().substring(0, 2));
            dayOfMonthList.add(date.getDayOfMonth());
            isWeekendList.add(dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY);
        }

        request.setAttribute("dayOfWeek", dayOfWeekList);
        request.setAttribute("dayOfMonth", dayOfMonthList);
        request.setAttribute("isWeekend", isWeekendList);

        BookingDAO bookingDAO = new BookingDAO();
        List<RoomBookingInfo> allBookings = bookingDAO.getRoomBookingSchedule();
        List<RoomBookingInfo> futureBookings = new ArrayList<>();
        for (RoomBookingInfo b : allBookings) {
            if (b.getCheckOutDate() != null && b.getCheckOutDate().isAfter(java.time.LocalDateTime.now())) {
                futureBookings.add(b);
            }
        }

        List<Room> allRooms = RoomDAO.getAllRooms();
        Map<String, Map<String, List<RoomBookingInfo>>> groupedBookings = new LinkedHashMap<>();

        Map<String, List<RoomBookingInfo>> bookingsByRoomNumber = new HashMap<>();
        for (RoomBookingInfo b : futureBookings) {
            bookingsByRoomNumber
                    .computeIfAbsent(b.getRoomNumber(), k -> new ArrayList<>())
                    .add(b);
        }

        for (Room room : allRooms) {
            String roomNumber = String.valueOf(room.getRoomNumber());
            String roomTypeName = room.getRoomType().getTypeName();

            List<RoomBookingInfo> bookingsForRoom = bookingsByRoomNumber.getOrDefault(roomNumber, new ArrayList<>());

            if (bookingsForRoom.isEmpty()) {
                RoomBookingInfo empty = new RoomBookingInfo();
                empty.setRoomId(room.getRoomId());
                empty.setRoomNumber(roomNumber);
                empty.setRoomTypeName(roomTypeName);
                bookingsForRoom.add(empty);
            }

            groupedBookings
                    .computeIfAbsent(roomTypeName, k -> new LinkedHashMap<>())
                    .put(roomNumber, bookingsForRoom);
        }

        // 5. Đẩy dữ liệu ra JSP
        request.setAttribute("groupedBookings", groupedBookings);
        request.getRequestDispatcher("views/receptionist/listBooking.jsp").forward(request, response);
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
