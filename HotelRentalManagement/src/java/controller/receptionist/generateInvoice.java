/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.receptionist;

import dal.BookingDAO;
import dal.InvoiceDAO;
import dal.ServicesDAO;
import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import model.Booking;
import model.InvoiceSummary;
import model.OfflineBookingUser;
import model.Payment;
import model.User;

/**
 *
 * @author admin
 */
public class generateInvoice extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Integer bookingId = (Integer) session.getAttribute("bookingId");

        if (bookingId == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu mã bookingId trong session.");
            return;
        }

        try {
            BookingDAO bookingDAO = new BookingDAO();
            Booking booking = bookingDAO.getBookingById(bookingId);
            if (booking == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy booking.");
                return;
            }

            ServicesDAO servicesDAO = new ServicesDAO();
            Map<String, Integer> selectedServices = servicesDAO.getSelectedServiceNamesWithQuantities(bookingId);

            UserDAO userDAO = new UserDAO();
            OfflineBookingUser offlineUser = userDAO.getOfflineBookingUserByBookingId(bookingId);
            if (offlineUser == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy thông tin khách offline.");
                return;
            }
            double totalAmount = booking.getTotalAmount();
            InvoiceDAO invoiceDAO = new InvoiceDAO();
            double depositAmount = invoiceDAO.getTotalPaidAmountByBookingId(bookingId);
            BookingDAO paymentDAO = new BookingDAO();
            List<Payment> payments = paymentDAO.getAllPayments();
            request.setAttribute("payments", payments);
            InvoiceSummary summary = new InvoiceSummary();
            summary.setTotalAmount(totalAmount);
            summary.setDepositAmount(depositAmount);
            summary.setRemainingAmount(totalAmount - depositAmount);

            request.setAttribute("summary", summary);

            request.setAttribute("booking", booking);
            request.setAttribute("selectedServices", selectedServices);
            request.setAttribute("bookingUser", offlineUser);
            request.setAttribute("isOffline", true);

            request.getRequestDispatcher("views/receptionist/invoiceOffline.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Lỗi hệ thống khi tạo hóa đơn.");
            request.getRequestDispatcher("searchRoom").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int bookingId = Integer.parseInt(request.getParameter("bookingId"));
            System.out.println("bookingId" + bookingId);
            double totalAmount = Double.parseDouble(request.getParameter("totalAmount"));
            System.out.println("totalAmount" + totalAmount);
            double depositRatio = Double.parseDouble(request.getParameter("depositOption"));
            System.out.println("depositRatio" + depositRatio);
            int paymentId = Integer.parseInt(request.getParameter("paymentMethod"));
            System.out.println("paymentId" + paymentId);
            double depositAmount = totalAmount * depositRatio;

            InvoiceDAO invoiceDAO = new InvoiceDAO();
            invoiceDAO.createInvoice(bookingId, totalAmount, depositAmount, paymentId);
            HttpSession session = request.getSession();
            session.setAttribute("showSuccessModal", "Đặt phòng thànhg công!");
            response.sendRedirect("searchRoom");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Có lỗi khi xử lý thanh toán.");
            request.getRequestDispatcher("views/receptionist/invoiceOffline.jsp").forward(request, response);
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
