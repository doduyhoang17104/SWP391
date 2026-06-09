/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.manager;

import dal.RoomDAO;
import dal.RoomTypeDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.RoomType;

/**
 *
 * @author admin
 */
public class createRoomType extends HttpServlet {

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
            out.println("<title>Servlet createRoomType</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet createRoomType at " + request.getContextPath() + "</h1>");
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
        request.getRequestDispatcher("views/dashboard/create-roomType.jsp").forward(request, response);
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
    request.setCharacterEncoding("UTF-8");

    String typeName = request.getParameter("typeName");
    String description = request.getParameter("description");
    String basePriceStr = request.getParameter("basePrice");

    double basePrice = 0;
    boolean valid = true;
    String errorMessage = null;

    basePriceStr = basePriceStr.replace(".", "").trim();

    if (typeName == null || typeName.trim().isEmpty()) {
        valid = false;
        errorMessage = "Tên loại phòng không được để trống.";
    } else if (RoomTypeDAO.isTypeNameExists(typeName.trim())) {
        valid = false;
        errorMessage = "Tên loại phòng đã tồn tại.";
    } else if (description == null || description.trim().isEmpty()) {
        valid = false;
        errorMessage = "Mô tả không được để trống.";
    } else {
        try {
            basePrice = Double.parseDouble(basePriceStr);
            if (basePrice <= 0 || basePrice != Math.floor(basePrice)) {
                valid = false;
                errorMessage = "Giá cơ bản phải là số nguyên dương.";
            }
        } catch (NumberFormatException e) {
            valid = false;
            errorMessage = "Giá cơ bản không hợp lệ.";
        }
    }

    if (!valid) {
        request.setAttribute("error", errorMessage);
        request.setAttribute("typeName", typeName);
        request.setAttribute("description", description);
        request.setAttribute("basePrice", basePriceStr);
        request.getRequestDispatcher("views/dashboard/create-roomType.jsp").forward(request, response);
        return;
    }

    RoomType roomType = new RoomType();
    roomType.setTypeName(typeName.trim());
    roomType.setDescription(description.trim());
    roomType.setBasePrice(basePrice);

    boolean success = RoomTypeDAO.addRoomType(roomType);

    if (success) {
        response.sendRedirect("listRoomType");
    } else {
        request.setAttribute("error", "Thêm thất bại");
        request.setAttribute("typeName", typeName);
        request.setAttribute("description", description);
        request.setAttribute("basePrice", basePriceStr);
        request.getRequestDispatcher("views/dashboard/create-roomType.jsp").forward(request, response);
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
