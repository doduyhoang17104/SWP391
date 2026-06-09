
package controller.Service;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dal.Service.ServiceCategoryDAO;
import dal.Service.ServiceDAO;
import model.Service.Service;

/**
 *
 * @author Admin
 */
public class CategoryManagerServlet extends HttpServlet {
   
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
            out.println("<title>Servlet CategoryManagerServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CategoryManagerServlet at " + request.getContextPath () + "</h1>");
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
        ServiceCategoryDAO dao = new ServiceCategoryDAO();
        ServiceDAO serviceDAO = new ServiceDAO();

        if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            boolean hasService = false;
            for (Service s : serviceDAO.getAllService()) {
                if (s.getServiceCategoryId() == id) {
                    hasService = true;
                    break;
                }
            }
            if (!hasService) {
                dao.deleteServiceCategory(id);
            } else {
                request.getSession().setAttribute("error", "Không thể xóa danh mục vì vẫn còn dịch vụ thuộc danh mục này!");
            }
        }
        response.sendRedirect(request.getContextPath() + "/servicemanager");
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
        ServiceCategoryDAO dao = new ServiceCategoryDAO();

        if ("add".equals(action)) {
            String name = request.getParameter("categoryName");
            dao.addServiceCategory(name);
        } else if ("edit".equals(action)) {
            int id = Integer.parseInt(request.getParameter("categoryId"));
            String name = request.getParameter("categoryName");
            dao.updateServiceCategory(id, name);
        }
        response.sendRedirect(request.getContextPath() + "/servicemanager");
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
