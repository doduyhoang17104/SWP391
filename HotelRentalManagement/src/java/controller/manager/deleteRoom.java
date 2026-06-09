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

/**
 *
 * @author admin
 */
public class deleteRoom extends HttpServlet {
        private RoomDAO roomDAO = new RoomDAO();
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
            out.println("<title>Servlet deleteRoom</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet deleteRoom at " + request.getContextPath() + "</h1>");
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

        String idParam = request.getParameter("id");

        if (idParam != null) {
            try {
                int roomId = Integer.parseInt(idParam);
                boolean deleted = RoomDAO.deleteRoom(roomId);

                if (deleted) {
                    request.getSession().setAttribute("message", "Xóa phòng thành công.");
                } else {
                    request.getSession().setAttribute("error", "Không thể xóa phòng (có thể do khóa ngoại).");
                }

            } catch (NumberFormatException e) {
                request.getSession().setAttribute("error", "ID phòng không hợp lệ.");
            }
        } else {
            request.getSession().setAttribute("error", "Thiếu ID phòng.");
        }
        response.sendRedirect("listRoom");
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
        String roomIdParam = request.getParameter("roomId");

        if (roomIdParam != null) {
            try {
                int roomId = Integer.parseInt(roomIdParam);
                RoomDAO roomDAO = new RoomDAO();
                boolean deleted = roomDAO.deleteRoom(roomId);

                if (deleted) {
                    request.setAttribute("message", "Room deleted successfully.");
                } else {
                    request.setAttribute("error", "Cannot delete room. It might be booked.");
                }

            } catch (NumberFormatException e) {
                request.setAttribute("error", "Invalid room ID.");
            }
        } else {
            request.setAttribute("error", "Room ID is required.");
        }

        // Redirect or forward to the room list
        request.getRequestDispatcher("listRoom").forward(request, response);
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