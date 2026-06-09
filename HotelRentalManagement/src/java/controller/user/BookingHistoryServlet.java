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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import model.Booking;
import model.RoomType;
import model.User;

/**
 *
 * @author ddhoang
 */
public class BookingHistoryServlet extends HttpServlet {

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
            out.println("<title>Servlet BookingHistoryServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet BookingHistoryServlet at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int userID = Integer.parseInt(user.getId());
        RoomDAO rd = new RoomDAO();
        List<RoomType> roomType = rd.getAllRoomTypes();
        BookingDAO bd = new BookingDAO();
        int pageSize = 5;
        int currentPage = 1;

        String indexParam = request.getParameter("page");
        if (indexParam != null) {
            currentPage = Integer.parseInt(indexParam);
        }

        int offset = (currentPage - 1) * pageSize;
        List<Booking> listB = bd.getCompletedBookingsByUserIdWithPaging(userID, offset, pageSize);
        int totalRecords = bd.countCompletedBookingsByCustomer(userID);
        int totalPages = (totalRecords + pageSize - 1) / pageSize;

        Set<Integer> feedbackBookingIds = new HashSet<>();
        for (Booking b : listB) {
            if (rd.hasFeedback(userID, b.getBookingId())) {
                request.setAttribute("hasFeedback", true);
                feedbackBookingIds.add(b.getBookingId());
            }
        }
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("listB", listB);
        request.setAttribute("roomType", roomType);
        request.setAttribute("feedbackBookingIds", feedbackBookingIds);
        request.getRequestDispatcher("views/user/booking-history.jsp").forward(request, response);
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
