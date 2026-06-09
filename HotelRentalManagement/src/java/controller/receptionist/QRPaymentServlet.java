/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.receptionist;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalTime;

/**
 *
 * @author ddhoang
 */
@WebServlet(name = "QRPaymentServlet", urlPatterns = {"/qrpayment"})
public class QRPaymentServlet extends HttpServlet {

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
            out.println("<title>Servlet QRPaymentServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet QRPaymentServlet at " + request.getContextPath() + "</h1>");
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
        System.out.println("Đã vào get để đẩy lên qr");
        int bookingId = Integer.parseInt(request.getParameter("bookingId"));
        System.out.println("bookingId" + bookingId);
        double totalAmount = Double.parseDouble(request.getParameter("totalAmount"));
        System.out.println("totalAmount" + totalAmount);
        double depositRatio = Double.parseDouble(request.getParameter("remainingAmount"));
        System.out.println("depositRatio" + depositRatio);
        int paymentId = Integer.parseInt(request.getParameter("paymentMethod"));
        System.out.println("paymentId" + paymentId);
        int phat = Integer.parseInt(request.getParameter("phat"));
        System.out.println("phat" + phat);
        request.setAttribute("phat", phat);
        request.setAttribute("bookingId", bookingId);
        request.setAttribute("totalAmount", totalAmount);
        request.setAttribute("depositRatio", depositRatio);
        request.setAttribute("paymentId", paymentId);

//        String depositAmountStr = request.getParameter("depositAmount");
//        double depositAmount = Double.parseDouble(depositAmountStr);
//        
//        request.setAttribute("depositAmount", depositAmount);
        request.getRequestDispatcher("views/receptionist/qr-checkout.jsp").forward(request, response);
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
        int bookingId = Integer.parseInt(request.getParameter("bookingId"));
        double totalAmount = Double.parseDouble(request.getParameter("totalAmount"));
        double depositRatio = Double.parseDouble(request.getParameter("depositOption"));
        int paymentId = Integer.parseInt(request.getParameter("paymentMethod"));
        request.setAttribute("bookingId", bookingId);
        request.setAttribute("totalAmount", totalAmount);
        request.setAttribute("depositRatio", depositRatio);
        request.setAttribute("paymentId", paymentId);
        String depositAmountStr = request.getParameter("depositAmount");
        double depositAmount = Double.parseDouble(depositAmountStr);

        System.out.println(">>> Số tiền cần thanh toán (deposit): " + depositAmount);

        request.setAttribute("depositAmount", depositAmount);
        System.out.println("post");
        request.getRequestDispatcher("views/receptionist/qr.jsp").forward(request, response);
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
