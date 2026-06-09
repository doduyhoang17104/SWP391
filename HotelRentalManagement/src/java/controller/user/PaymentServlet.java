/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.user;

import dal.BookingDAO;
import dal.InvoiceDAO;
import dal.PromotionDAO;
import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import model.EmailUtil;
import model.User;

/**
 *
 * @author ddhoang
 */
public class PaymentServlet extends HttpServlet {

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
            out.println("<title>Servlet PaymentServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PaymentServlet at " + request.getContextPath() + "</h1>");
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
        
        int voucherDiscount = Integer.parseInt(request.getParameter("voucherDiscount"));
        int night = Integer.parseInt(request.getParameter("night"));
        int pointsUsedAmount = Integer.parseInt(request.getParameter("pointsUsedAmount"));
        int prepayment = Integer.parseInt(request.getParameter("prepayment"));
        int extraFee = Integer.parseInt(request.getParameter("extraFee"));
        double basePrice = Double.parseDouble(request.getParameter("basePrice"));
        int numPeople = Integer.parseInt(request.getParameter("numPeople"));
        int uId = Integer.parseInt(request.getParameter("userId"));
        int pointsUsed = Integer.parseInt(request.getParameter("pointsUsed"));
        int roomId = Integer.parseInt(request.getParameter("roomId"));
        String roomType = request.getParameter("roomType");
        String checkin = request.getParameter("checkin");
        String checkout = request.getParameter("checkout");
        String requestS = request.getParameter("request");
        if (requestS == null || requestS.trim().isEmpty()) {
            requestS = "";
        }
        String promoRaw = request.getParameter("promotionId");
        Integer promotionId = null;
        if (promoRaw != null && !promoRaw.trim().isEmpty()) {
            int pid = Integer.parseInt(promoRaw);
            if (pid > 0) {
                promotionId = pid;
            }
        }
        System.out.println("pId = " + promotionId);

        int total = Integer.parseInt(request.getParameter("total"));
        int depositAmount = Integer.parseInt(request.getParameter("depositAmount"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date checkInDate = sdf.parse(checkin);
            Date checkOutDate = sdf.parse(checkout);

            BookingDAO bd = new BookingDAO();
            int bookingId = bd.insertBookingOnline(uId, roomId, checkInDate, checkOutDate, requestS, promotionId, total);
            System.out.println("BookingId mới: " + bookingId);
            if (bookingId > 0) {
                String[] selectedServiceIds = request.getParameterValues("services");
                String[] selectedServiceNames = request.getParameterValues("servicesName");
                if (selectedServiceIds != null) {
                    for (String idStr : selectedServiceIds) {
                        int serviceId = Integer.parseInt(idStr);
                        System.out.println("Id sẻvice: " + serviceId);
                        bd.insertBookingService(bookingId, serviceId);
                    }
                }
                if (promotionId != null) {
                    PromotionDAO pd = new PromotionDAO();
                    pd.decreasePromotionQuantity(promotionId);
                }
                InvoiceDAO id = new InvoiceDAO();
                id.insertInvoice(total, bookingId, depositAmount);
                UserDAO ud = new UserDAO();
                ud.subtractUserPoints(uId, pointsUsed);
                User user = ud.getUserById(String.valueOf(uId));
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                EmailUtil.sendInvoice(user.getEmail(), numPeople, roomType, basePrice, night, checkin, checkout, requestS, selectedServiceNames,
                        extraFee, voucherDiscount, pointsUsedAmount, total, prepayment, depositAmount);
                request.getSession().setAttribute("message", "Tạo yêu cầu đặt phòng thành công, bạn có thể kiểm tra trạng thái trong phần trạng thái đặt phòng!");
                response.sendRedirect("home");

            } else {
                request.setAttribute("error", "Insert failed.");
                request.getRequestDispatcher("booking-form.jsp").forward(request, response);
            }

        } catch (ParseException e) {
            e.printStackTrace();
            request.setAttribute("error", "Ngày không hợp lệ");
            request.getRequestDispatcher("booking-form.jsp").forward(request, response);
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
