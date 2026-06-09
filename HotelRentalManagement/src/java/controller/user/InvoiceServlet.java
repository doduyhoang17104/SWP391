/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.user;

import dal.BookingDAO;
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
import java.util.ArrayList;
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
public class InvoiceServlet extends HttpServlet {

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
            out.println("<title>Servlet InvoiceServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet InvoiceServlet at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession();
        int roomId = Integer.parseInt(request.getParameter("roomId"));
        System.out.println("phòng quay lại :" + roomId);
        RoomDAO rd = new RoomDAO();
        Room room = rd.getRoomDetailById(roomId);
        String checkIn = (String) session.getAttribute("checkIn");
        String checkOut = (String) session.getAttribute("checkOut");
        String specialRequest = (String) session.getAttribute("specialRequest");
        int numPeople = (int) session.getAttribute("numPeople");
        List<String> selectedServiceNames = (List<String>) session.getAttribute("selectedServiceNames");
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
        request.setAttribute("listP", listP);
        request.setAttribute("selectedServiceNames", selectedServiceNames);
        request.setAttribute("specialRequest", specialRequest);
        request.setAttribute("numPeople", numPeople);
        request.setAttribute("checkIn", checkIn);
        request.setAttribute("checkOut", checkOut);
        request.setAttribute("room", room);
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

        int roomId = Integer.parseInt(request.getParameter("roomId"));
        RoomDAO rd = new RoomDAO();
        Room room = rd.getRoomDetailById(roomId);

        String specialRequest = request.getParameter("specialRequest");
        String checkIn = request.getParameter("checkIn");
        String checkOut = request.getParameter("checkOut");
        String night = request.getParameter("numNights");

        String voucherId = request.getParameter("voucherId");

        int numPeople = Integer.parseInt(request.getParameter("numPeople"));
        int pointsUsed = Integer.parseInt(request.getParameter("pointsUsed"));
        int extraFee = Integer.parseInt(request.getParameter("extraPeopleFee"));
        int voucherDiscount = Integer.parseInt(request.getParameter("voucherDiscount"));
        int pointsUsedAmount = Integer.parseInt(request.getParameter("pointsUsedAmount"));
        String[] selectedServices = request.getParameterValues("services");
        int prepayment = Integer.parseInt(request.getParameter("paymentRate"));
        int total = Integer.parseInt(request.getParameter("totalAmount"));
        int paymentAmount = total * prepayment / 100;

        //Lưu danh sách ID dịch vụ đã chọn
        List<Integer> selectedServiceIds = new ArrayList<>();
        List<String> selectedServiceNames = new ArrayList<>();

        if (selectedServices != null && selectedServices.length > 0) {
            ServiceDAO dao = new ServiceDAO();
            List<ServiceCategory> allCategories = dao.getAllServicesGroupedByCategory();

            for (String s : selectedServices) {
                String[] parts = s.split(":");
                if (parts.length == 2) {
                    try {
                        int serviceId = Integer.parseInt(parts[0]);
                        int price = Integer.parseInt(parts[1]);

                        selectedServiceIds.add(serviceId);

                        String serviceName = "Không rõ";
                        outer:
                        for (ServiceCategory cat : allCategories) {
                            for (Service svc : cat.getServices()) {
                                if (svc.getServiceId() == serviceId) {
                                    serviceName = svc.getServiceName();
                                    break outer;
                                }
                            }
                        }

                        selectedServiceNames.add(serviceName + " (+" + String.format("%,d", price) + "₫)");

                    } catch (NumberFormatException e) {
                        selectedServiceNames.add("Dịch vụ lỗi");
                    }
                }
            }
        }
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        session.setAttribute("specialRequest", specialRequest);
        session.setAttribute("checkIn", checkIn);
        session.setAttribute("checkOut", checkOut);
        session.setAttribute("numPeople", numPeople);
        session.setAttribute("extraFee", extraFee);
        session.setAttribute("voucherId", voucherId);
        session.setAttribute("totalAmount", total);
        session.setAttribute("voucherDiscount", voucherDiscount);
        session.setAttribute("pointsUsedAmount", pointsUsedAmount);
        session.setAttribute("selectedServicesRaw", selectedServices);       // checkbox
        session.setAttribute("selectedServiceNames", selectedServiceNames); // hiển thị
        session.setAttribute("selectedServiceIds", selectedServiceIds);     // để submit sang payment
        BookingDAO bd = new BookingDAO();
        int lastBookingId = bd.getLatestBookingId() +1;
        request.setAttribute("lastBookingId", lastBookingId);
        request.setAttribute("user", user);
        request.setAttribute("room", room);
        request.setAttribute("night", night);
        request.setAttribute("specialRequest", specialRequest);
        request.setAttribute("paymentAmount", paymentAmount);
        request.setAttribute("prepayment", prepayment);
        request.setAttribute("checkIn", checkIn);
        request.setAttribute("checkOut", checkOut);
        request.setAttribute("numPeople", numPeople);
        request.setAttribute("pointsUsed", pointsUsed);
        request.setAttribute("extraFee", extraFee);
        request.setAttribute("voucherId", voucherId);
        request.setAttribute("totalAmount", total);
        request.setAttribute("voucherDiscount", voucherDiscount);
        request.setAttribute("pointsUsedAmount", pointsUsedAmount);
        request.setAttribute("selectedServiceNames", selectedServiceNames);
        request.setAttribute("selectedServiceIds", selectedServiceIds); // Dùng trong <form> ở invoice.jsp

        request.getRequestDispatcher("views/user/invoice.jsp").forward(request, response);
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
