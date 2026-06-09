/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.receptionist;

import dal.ServicesDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import model.Service.Service;
import model.ServiceCategory;

/**
 *
 * @author admin
 */
public class checkinBookingService extends HttpServlet {
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String bookingIdParam = request.getParameter("bookingId");
        String roomIdParam = request.getParameter("roomId");
        String roomNumberParam = request.getParameter("roomNumber");
        String checkinParam = request.getParameter("checkin");
        String checkoutParam = request.getParameter("checkout");

        try {
            if (bookingIdParam != null && !bookingIdParam.isEmpty()) {
                session.setAttribute("bookingId", Integer.parseInt(bookingIdParam));
            }
            if (roomIdParam != null && !roomIdParam.isEmpty()) {
                session.setAttribute("roomId", Integer.parseInt(roomIdParam));
            }
        } catch (NumberFormatException e) {
        }

        if (roomNumberParam != null) {
            session.setAttribute("roomNumber", roomNumberParam);
        }
        if (checkinParam != null) {
            session.setAttribute("checkin", checkinParam);
        }
        if (checkoutParam != null) {
            session.setAttribute("checkout", checkoutParam);
        }
        
        ServicesDAO servicesDAO = new ServicesDAO();
        Map<ServiceCategory, List<Service>> serviceMap = servicesDAO.getServiceMapByCategory();
        request.setAttribute("serviceMap", serviceMap);
        Integer bookingId = (Integer) session.getAttribute("bookingId");
        if (bookingId != null) {
            List<Integer> selectedServiceIds = servicesDAO.getSelectedServiceIdsForBooking(bookingId);
            request.setAttribute("selectedServiceIds", selectedServiceIds);
        }
        request.getRequestDispatcher("views/receptionist/checkin-Booking-Service.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String bookingIdStr = request.getParameter("bookingId");
        Integer bookingId = null;

        if (bookingIdStr != null && !bookingIdStr.isEmpty()) {
            try {
                bookingId = Integer.parseInt(bookingIdStr);
            } catch (NumberFormatException e) {
                request.setAttribute("errorMessage", "Mã đặt phòng không hợp lệ.");
                request.getRequestDispatcher("searchRoom").forward(request, response);
                return;
            }
        } else {
            HttpSession session = request.getSession();
            bookingId = (Integer) session.getAttribute("bookingId");
        }

        if (bookingId == null) {
            request.setAttribute("errorMessage", "Thiếu thông tin booking. Vui lòng quay lại bước trước.");
            request.getRequestDispatcher("searchRoom").forward(request, response);
            return;
        }

        String[] selectedServices = request.getParameterValues("selectedServices");

        ServicesDAO servicesDAO = new ServicesDAO();

        servicesDAO.deleteAllServicesFromBooking(bookingId);

        if (selectedServices != null && selectedServices.length > 0) {
            for (String serviceIdStr : selectedServices) {
                try {
                    int serviceId = Integer.parseInt(serviceIdStr);
                    servicesDAO.addServiceToBooking(bookingId, serviceId, 1);
                } catch (NumberFormatException e) {

                    request.setAttribute("errorMessage", "Dịch vụ không hợp lệ.");
                    request.getRequestDispatcher("searchRoom").forward(request, response);
                    return;
                }
            }
        }

        servicesDAO.updateTotalAmountIncludingRoom(bookingId);
         HttpSession session = request.getSession();
         session.setAttribute("showSuccessModal", "Đặt dịch vụ phòng thành công!");
        response.sendRedirect("checkinBooking");
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
