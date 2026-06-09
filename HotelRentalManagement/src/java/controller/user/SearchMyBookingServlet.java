/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.user;

import dal.BookingDAO;
import dal.RoomDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Booking;
import model.RoomType;
import model.User;

/**
 *
 * @author ddhoang
 */
public class SearchMyBookingServlet extends HttpServlet {

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
            out.println("<title>Servlet SearchMyBookingServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SearchMyBookingServlet at " + request.getContextPath() + "</h1>");
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
        String status = request.getParameter("status");
        String roomTypes = request.getParameter("roomType");
        System.out.println(roomTypes);

        // Giá từ – đến
        String priceFromStr = request.getParameter("priceFrom");
        String priceToStr = request.getParameter("priceTo");

        Double priceFrom = null;
        Double priceTo = null;

        try {
            if (priceFromStr != null && !priceFromStr.isEmpty()) {
                priceFrom = Double.parseDouble(priceFromStr);
            }
            if (priceToStr != null && !priceToStr.isEmpty()) {
                priceTo = Double.parseDouble(priceToStr);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace(); // hoặc log lỗi
        }

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int uId = Integer.parseInt(user.getId());
        BookingDAO bd = new BookingDAO();
        List<Booking> listB = bd.searchBookingsByFilters(uId, status, roomTypes, priceFrom, priceTo);
        RoomDAO rd = new RoomDAO();
        List<RoomType> roomType = rd.getAllRoomTypes();
        request.setAttribute("roomType", roomType);
        request.setAttribute("listB", listB);
        request.getRequestDispatcher("views/user/status-booking.jsp").forward(request, response);
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
