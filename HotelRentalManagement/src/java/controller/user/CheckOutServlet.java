/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.user;

import dal.PromotionDAO;
import dal.RoomDAO;
import dal.Service.ServiceDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Promotion;
import model.Room;
import model.Service.Service;
import model.Service.ServiceCategory;
import model.User;

/**
 *
 * @author ddhoang
 */
public class CheckOutServlet extends HttpServlet {

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
            out.println("<title>Servlet CheckOutServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CheckOutServlet at " + request.getContextPath() + "</h1>");
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
        response.setContentType("text/html;charset=UTF-8");
        String checkIn = request.getParameter("checkin");
        String checkOut = request.getParameter("checkout");
        int roomId = Integer.parseInt(request.getParameter("roomId"));
        System.out.println("phòng :" + roomId);
        RoomDAO rd = new RoomDAO();
        Room room = rd.getRoomDetailById(roomId);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            request.setAttribute("msg", "Vui lòng đăng nhập trước khi đặt phòng!");
            request.getRequestDispatcher("views/user/login.jsp").forward(request, response);
        }
        ServiceDAO dao = new ServiceDAO();
        PromotionDAO pd = new PromotionDAO();
        List<ServiceCategory> categoriesWithServices = dao.getAllServicesGroupedByCategory();
        List<Promotion> listP = pd.getAllPromotions();
        request.setAttribute("categorizedServices", categoriesWithServices);
        for (ServiceCategory cat : categoriesWithServices) {
            System.out.println("== " + cat.getServiceCategoryName());
            for (Service s : cat.getServices()) {
                System.out.println("Dịch vụ: ID=" + s.getServiceId() + "Name=" + s.getServiceName() + ", Price=" + s.getPrice());
            }
        }
        for (ServiceCategory cat : categoriesWithServices) {
            System.out.println("== Danh mục: " + cat.getServiceCategoryName() + " (ID=" + cat.getServiceCategoryId() + ")");
            for (Service s : cat.getServices()) {
                System.out.println("   Dịch vụ: ID=" + s.getServiceId() + ", Tên=" + s.getServiceName() + ", Giá=" + s.getPrice());
            }
        }
        request.setAttribute("checkIn", checkIn);
        request.setAttribute("checkOut", checkOut);
        request.setAttribute("room", room);
        request.setAttribute("user", user);
        request.setAttribute("listP", listP);
        request.getRequestDispatcher("views/user/check-out.jsp").forward(request, response);
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
