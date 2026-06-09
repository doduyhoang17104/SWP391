/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.manager;

import dal.RoomDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.List;
import model.Room;
import model.RoomType;

/**
 *
 * @author admin
 */
public class listRoomBooking extends HttpServlet {

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
            out.println("<title>Servlet listRoomBooking</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet listRoomBooking at " + request.getContextPath() + "</h1>");
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

        List<RoomType> roomTypes = RoomDAO.getAllRoomTypes();
        request.setAttribute("roomTypeList", roomTypes);

        String checkinStr = request.getParameter("checkin");
        String checkoutStr = request.getParameter("checkout");
        String roomType = request.getParameter("roomType");
        String bedStr = request.getParameter("bed");
        String maxPriceStr = request.getParameter("maxPrice");
        String minPriceStr = request.getParameter("minPrice");
        String guestsStr = request.getParameter("guests");

        List<Room> rooms;

        boolean hasSearch = checkinStr != null && !checkinStr.isEmpty()
                && checkoutStr != null && !checkoutStr.isEmpty();

        try {
            int bed = (bedStr != null && !bedStr.isEmpty()) ? Integer.parseInt(bedStr) : 0;
            double minPrice = (minPriceStr != null && !minPriceStr.isEmpty()) ? Double.parseDouble(minPriceStr) : 0;
            double maxPrice = (maxPriceStr != null && !maxPriceStr.isEmpty()) ? Double.parseDouble(maxPriceStr) : Double.MAX_VALUE;
            int guests = (guestsStr != null && !guestsStr.isEmpty()) ? RoomDAO.extractTotalGuests(guestsStr) : 0;

            if (hasSearch) {
                LocalDate checkin = LocalDate.parse(checkinStr);
                LocalDate checkout = LocalDate.parse(checkoutStr);

                rooms = RoomDAO.searchOnlyAvailableRooms(checkin, checkout, guests, roomType, bed, minPrice, maxPrice);

                for (Room room : rooms) {
                    List<String> images = RoomDAO.getRoomImagesByRoomId(room.getRoomId());
                    room.setImageUrls(images);
                }

                request.setAttribute("rooms", rooms);
                request.getRequestDispatcher("rooms.jsp").forward(request, response);
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Đã xảy ra lỗi khi tìm kiếm phòng: " + e.getMessage());
        }

        // Không có filter: load phân trang
        String pageParam = request.getParameter("page");
        int pageIndex = 1;
        try {
            if (pageParam != null) {
                pageIndex = Integer.parseInt(pageParam);
                if (pageIndex < 1) {
                    pageIndex = 1;
                }
            }
        } catch (NumberFormatException ignored) {
        }

        int pageSize = 3;
        rooms = RoomDAO.getOneRoomPerTypeWithImageAndPrice(pageIndex, pageSize);
        int totalRooms = RoomDAO.getTotalRoomTypeCount();
        int totalPages = (int) Math.ceil((double) totalRooms / pageSize);

        request.setAttribute("rooms", rooms);
        request.setAttribute("pageIndex", pageIndex);
        request.setAttribute("totalPages", totalPages);

        request.getRequestDispatcher("rooms.jsp").forward(request, response);
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
