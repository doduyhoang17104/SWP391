/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.manager;

import dal.RoomTypeDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.RoomType;

/**
 *
 * @author admin
 */
public class editRoomType extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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
            out.println("<title>Servlet editRoomType</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet editRoomType at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
int id = Integer.parseInt(request.getParameter("id"));
RoomType roomType = RoomTypeDAO.getRoomTypeById(id);

List<String> amenities = RoomTypeDAO.getAmenitiesByRoomTypeId(id);
roomType.setAmenities(amenities);

request.setAttribute("roomType", roomType);
request.getRequestDispatcher("views/dashboard/edit-roomType.jsp").forward(request, response);
}

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        int id = Integer.parseInt(request.getParameter("id"));
        String typeName = request.getParameter("typeName");
        String description = request.getParameter("description");
        String basePriceStr = request.getParameter("basePrice");

        // Làm sạch giá
        basePriceStr = basePriceStr.replace(".", "");
        double basePrice = 0;
        try {
            basePrice = Double.parseDouble(basePriceStr);
        } catch (NumberFormatException e) {
            basePrice = 0;
        }

        RoomType roomType = new RoomType();
        roomType.setRoomTypeId(id);
        roomType.setTypeName(typeName);
        roomType.setDescription(description);
        roomType.setBasePrice(basePrice);

        boolean success = RoomTypeDAO.updateRoomType(roomType);

        if (success) {
            response.sendRedirect("listRoomType"); 
        } else {
            request.setAttribute("error", "Cập nhật thất bại");
            request.setAttribute("roomType", roomType);
            request.getRequestDispatcher("views/dashboard/edit-RoomType.jsp").forward(request, response);
        }
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
