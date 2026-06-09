
package controller.Service;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class ServiceManagerServlet extends HttpServlet {
   
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
            out.println("<title>Servlet ServiceManagerServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServiceManagerServlet at " + request.getContextPath () + "</h1>");
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
        String action = request.getParameter("action");
        dal.Service.ServiceDAO serviceDAO = new dal.Service.ServiceDAO();

        if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            int categoryId = Integer.parseInt(request.getParameter("categoryId"));
            serviceDAO.deleteService(id);
            response.sendRedirect(request.getContextPath() + "/servicemanager?categoryId=" + categoryId);
            return;
        }

        dal.Service.ServiceCategoryDAO categoryDAO = new dal.Service.ServiceCategoryDAO();

        // Lấy tất cả category
        ArrayList<model.Service.ServiceCategory> serviceCategories = categoryDAO.getAllServiceCategory();
        // Lấy tất cả service
        ArrayList<model.Service.Service> allServices = serviceDAO.getAllService();

        // Lọc theo category nếu có
        String categoryIdParam = request.getParameter("categoryId");
        Integer selectedCategoryId = null;
        ArrayList<model.Service.Service> services = new ArrayList<>();
        if (categoryIdParam != null && !categoryIdParam.isEmpty()) {
            selectedCategoryId = Integer.parseInt(categoryIdParam);
            for (model.Service.Service s : allServices) {
                if (s.getServiceCategoryId() == selectedCategoryId) {
                    services.add(s);
                }
            }
        } else {
            services = allServices;
        }

        request.setAttribute("serviceCategories", serviceCategories);
        request.setAttribute("services", services);
        request.setAttribute("selectedCategoryId", selectedCategoryId);

        request.getRequestDispatcher("/views/dashboard/service.jsp").forward(request, response);
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
        String action = request.getParameter("action");
        dal.Service.ServiceDAO serviceDAO = new dal.Service.ServiceDAO();

        if ("add".equals(action)) {
            String name = request.getParameter("serviceName");
            int price = Integer.parseInt(request.getParameter("servicePrice"));
            int categoryId = Integer.parseInt(request.getParameter("categoryId"));
            serviceDAO.addService(name, price, categoryId);
            response.sendRedirect(request.getContextPath() + "/servicemanager?categoryId=" + categoryId);
            return;
        } else if ("edit".equals(action)) {
            int id = Integer.parseInt(request.getParameter("serviceId"));
            String name = request.getParameter("serviceName");
            int price = Integer.parseInt(request.getParameter("servicePrice"));
            int categoryId = Integer.parseInt(request.getParameter("categoryId"));
            serviceDAO.updateService(id, name, price);
            response.sendRedirect(request.getContextPath() + "/servicemanager?categoryId=" + categoryId);
            return;
        }
        processRequest(request, response);
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
