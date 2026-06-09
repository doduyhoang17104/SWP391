/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.manager;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dal.PromotionDAO;
import model.Promotion;
import java.util.List;
import jakarta.servlet.annotation.WebServlet;

/**
 *
 * @author Admin
 */
@WebServlet({"/listPromotion", "/addPromotion", "/editPromotion", "/deletePromotion"})
public class PromotionServlet extends HttpServlet {
   
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
            out.println("<title>Servlet PromotionServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PromotionServlet at " + request.getContextPath () + "</h1>");
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
        String action = request.getServletPath();
        if ("/addPromotion".equals(action)) {
            request.getRequestDispatcher("views/dashboard/add-promotion.jsp").forward(request, response);
        } else if ("/editPromotion".equals(action)) {
            String idStr = request.getParameter("id");
            if (idStr != null) {
                try {
                    int id = Integer.parseInt(idStr);
                    Promotion p = new PromotionDAO().getPromotionById(id);
                    request.setAttribute("promotion", p);
                    request.getRequestDispatcher("views/dashboard/edit-promotion.jsp").forward(request, response);
                    return;
                } catch (Exception ex) {}
            }
            response.sendRedirect("listPromotion");
        } else if ("/deletePromotion".equals(action)) {
            String idStr = request.getParameter("id");
            if (idStr != null) {
                try {
                    int id = Integer.parseInt(idStr);
                    new PromotionDAO().deletePromotion(id);
                } catch (Exception ex) {}
            }
            response.sendRedirect("listPromotion");
        } else { // listPromotion
            PromotionDAO dao = new PromotionDAO();
            List<Promotion> promotions = dao.getAllPromotions();
            request.setAttribute("promotions", promotions);
            request.getRequestDispatcher("views/dashboard/promotion-list.jsp").forward(request, response);
        }
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
        String action = request.getServletPath();
        if ("/addPromotion".equals(action)) {
            String name = request.getParameter("promotionName");
            String percentStr = request.getParameter("discountPercent");
            String startDateStr = request.getParameter("startDate");
            String endDateStr = request.getParameter("endDate");
            String statusStr = request.getParameter("status");
            String desc = request.getParameter("description");
            String quantityStr = request.getParameter("quantity");
            int quantity = 0;
            if (quantityStr != null && !quantityStr.isEmpty()) {
                try { quantity = Integer.parseInt(quantityStr); } catch (Exception ex) { quantity = 0; }
            }
            String msg = null;
            try {
                java.math.BigDecimal percent = new java.math.BigDecimal(percentStr);
                java.sql.Date startDate = java.sql.Date.valueOf(startDateStr);
                java.sql.Date endDate = java.sql.Date.valueOf(endDateStr);
                int status = Integer.parseInt(statusStr);
                if (endDate.before(startDate)) {
                    msg = "Ngày kết thúc phải sau ngày bắt đầu!";
                } else {
                    model.Promotion p = new model.Promotion(0, name, percent, startDate, endDate, status, desc, quantity);
                    dal.PromotionDAO dao = new dal.PromotionDAO();
                    boolean ok = dao.insertPromotion(p);
                    if (ok) {
                        response.sendRedirect("listPromotion");
                        return;
                    } else {
                        msg = "Thêm mã giảm giá thất bại!";
                    }
                }
            } catch (Exception ex) {
                msg = "Dữ liệu không hợp lệ!";
            }
            request.setAttribute("msg", msg);
            request.getRequestDispatcher("views/dashboard/add-promotion.jsp").forward(request, response);
            return;
        } else if ("/editPromotion".equals(action)) {
            String idStr = request.getParameter("promotionId");
            String name = request.getParameter("promotionName");
            String percentStr = request.getParameter("discountPercent");
            String startDateStr = request.getParameter("startDate");
            String endDateStr = request.getParameter("endDate");
            String statusStr = request.getParameter("status");
            String desc = request.getParameter("description");
            String quantityStr = request.getParameter("quantity");
            int quantity = 0;
            if (quantityStr != null && !quantityStr.isEmpty()) {
                try { quantity = Integer.parseInt(quantityStr); } catch (Exception ex) { quantity = 0; }
            }
            String msg = null;
            try {
                int id = Integer.parseInt(idStr);
                java.math.BigDecimal percent = new java.math.BigDecimal(percentStr);
                java.sql.Date startDate = java.sql.Date.valueOf(startDateStr);
                java.sql.Date endDate = java.sql.Date.valueOf(endDateStr);
                int status = Integer.parseInt(statusStr);
                System.out.println(status);
                if (endDate.before(startDate)) {
                    msg = "Ngày kết thúc phải sau ngày bắt đầu!";
                } else {
                    model.Promotion p = new model.Promotion(id, name, percent, startDate, endDate, status, desc, quantity);
                    dal.PromotionDAO dao = new dal.PromotionDAO();
                    boolean ok = dao.updatePromotion(p);
                    if (ok) {
                        response.sendRedirect("listPromotion");
                        return;
                    } else {
                        msg = "Cập nhật mã giảm giá thất bại!";
                    }
                }
            } catch (Exception ex) {
                msg = "Dữ liệu không hợp lệ!";
            }
            request.setAttribute("msg", msg);
            request.setAttribute("promotion", new PromotionDAO().getPromotionById(Integer.parseInt(idStr)));
            request.getRequestDispatcher("views/dashboard/edit-promotion.jsp").forward(request, response);
            return;
        } else {
            super.doPost(request, response);
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
