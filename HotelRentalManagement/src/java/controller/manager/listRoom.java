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
import java.util.List;
import model.Room;
import model.RoomType;

/**
 *
 * @author admin
 */
public class listRoom extends HttpServlet {

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
            out.println("<title>Servlet listRoom</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet listRoom at " + request.getContextPath() + "</h1>");
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

        // Lấy các tham số lọc
        String roomNumber = request.getParameter("roomNumber");
        String roomType = request.getParameter("roomType");
        String bedStr = request.getParameter("bed");
        String status = request.getParameter("status");
        String bookedStr = request.getParameter("booked");

        Integer bed = null;
        Boolean booked = null;

        try {
            if (bedStr != null && !bedStr.isEmpty()) {
                bed = Integer.parseInt(bedStr);
            }
            if (bookedStr != null && !bookedStr.isEmpty()) {
                booked = Boolean.parseBoolean(bookedStr);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Dữ liệu lọc không hợp lệ.");
        }

        // Phân trang
        int pageIndex = 1;
        int pageSize = 10; // hoặc 6 tùy thiết kế
        String pageParam = request.getParameter("page");
        if (pageParam != null) {
            try {
                pageIndex = Integer.parseInt(pageParam);
                if (pageIndex < 1) {
                    pageIndex = 1;
                }
            } catch (NumberFormatException e) {
                pageIndex = 1;
            }
        }

        // Gọi DAO xử lý
        List<Room> roomList = RoomDAO.searchRoomsWithPagination(roomNumber, roomType, bed, status, booked, pageIndex, pageSize);
        int totalRooms = RoomDAO.countFilteredRooms(roomNumber, roomType, bed, status, booked);
        int totalPages = (int) Math.ceil((double) totalRooms / pageSize);
        List<RoomType> roomTypes = RoomDAO.getAllRoomTypes();
        request.setAttribute("roomTypes", roomTypes);

        // Gửi dữ liệu về view
        request.setAttribute("roomList", roomList);
        request.setAttribute("pageIndex", pageIndex);
        request.setAttribute("totalPages", totalPages);

        // Giữ lại tham số filter để hiển thị lại trên form
        request.setAttribute("roomNumber", roomNumber);
        request.setAttribute("roomType", roomType);
        request.setAttribute("bed", bedStr);
        request.setAttribute("status", status);
        request.setAttribute("booked", bookedStr);

        request.getRequestDispatcher("views/dashboard/room.jsp").forward(request, response);
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
