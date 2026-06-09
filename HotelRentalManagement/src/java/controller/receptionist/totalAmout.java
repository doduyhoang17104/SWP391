package controller.receptionist;

import dal.BookingDAO;
import dal.InvoiceDAO;
import dal.ServicesDAO;
import dal.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import model.Booking;
import model.Invoice;
import model.InvoiceSummary;
import model.OfflineBookingUser;
import model.Payment;

public class totalAmout extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String bookingIdParam = request.getParameter("bookingId");
            if (bookingIdParam == null || bookingIdParam.trim().isEmpty()) {
                throw new IllegalArgumentException("Thiếu mã đặt phòng (bookingId).");
            }

            int bookingId = Integer.parseInt(bookingIdParam);
            System.out.println("bookingId: " + bookingId);

            BookingDAO bookingDAO = new BookingDAO();
            Booking booking = bookingDAO.getBookingUserInfoByBookingId(bookingId); // ✅ đã chứa user info
            System.out.println("time checkout:" + booking.getCheckOut());
            System.out.println("CheckOut class: " + booking.getCheckOut().getClass().getName());

            if (booking == null) {
                throw new IllegalArgumentException("Không tìm thấy đặt phòng với ID: " + bookingId);
            }

            ServicesDAO servicesDAO = new ServicesDAO();
            Map<String, Integer> selectedServices = servicesDAO.getSelectedServiceNamesWithQuantities(bookingId);

            InvoiceDAO invoiceDAO = new InvoiceDAO();
            double totalPaidBefore = invoiceDAO.getTotalPaidAmountByBookingId(bookingId);
            int phat = 0;

// Lấy ngày checkout từ đơn booking (ví dụ kiểu java.sql.Date hoặc java.util.Date)
            LocalDate checkOutDate = LocalDate.parse(booking.getCheckOut().toString());

            LocalDate today = LocalDate.now();

// Nếu hôm nay là ngày checkout, thì kiểm tra giờ hiện tại
            if (today.equals(checkOutDate)) {
                LocalTime now = LocalTime.now(); // Giờ hiện tại
                LocalTime deadline = LocalTime.of(12, 0); // Quy định checkout trước 12:00 trưa

                if (now.isAfter(deadline)) {
                    long minutesLate = Duration.between(deadline, now).toMinutes(); // phút trễ
                    long hoursLate = (long) Math.ceil(minutesLate / 60.0); // làm tròn giờ

                    phat = (int) (hoursLate * 50000); // 50k mỗi giờ
                    System.out.println("Checkout muộn: " + hoursLate + " giờ. Phạt: " + phat + " VND");
                } else {
                    System.out.println("Checkout đúng giờ.");
                }
            } else {
                System.out.println("Không phải ngày checkout hôm nay => không tính phạt.");
            }

            double totalAmountNow = booking.getTotalAmount() + phat;
            double remainingAmount = totalAmountNow - totalPaidBefore;
            System.out.println("totalAmountNow"+totalAmountNow);
            List<Payment> payments = bookingDAO.getAllPayments();

            InvoiceSummary summary = new InvoiceSummary();
            summary.setTotalAmount(totalAmountNow);
            summary.setDepositAmount(totalPaidBefore);
            summary.setRemainingAmount(remainingAmount);

            request.setAttribute("totalAmountNow", totalAmountNow);
            request.setAttribute("phat", phat);
            request.setAttribute("booking", booking);
            System.out.println("name: " + booking.getUserName());
            request.setAttribute("selectedServices", selectedServices);
            request.setAttribute("summary", summary);
            request.setAttribute("payments", payments);

            request.getRequestDispatcher("views/receptionist/Invoice.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Định dạng mã đặt phòng không hợp lệ.");
            request.getRequestDispatcher("views/receptionist/Invoice.jsp").forward(request, response);
        } catch (IllegalArgumentException | IllegalStateException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("views/receptionist/Invoice.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Lỗi hệ thống khi hiển thị hóa đơn: " + e.getMessage());
            request.getRequestDispatcher("views/receptionist/Invoice.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int bookingId = Integer.parseInt(request.getParameter("bookingId"));
            System.out.println("bookingId trong post:" + bookingId);
            int paymentId = Integer.parseInt(request.getParameter("paymentMethod"));
            int phat = Integer.parseInt(request.getParameter("phat"));

            BookingDAO bookingDAO = new BookingDAO();
            Booking booking = bookingDAO.getBookingById(bookingId);
            if (booking == null) {
                throw new IllegalArgumentException("Không tìm thấy đặt phòng với ID: " + bookingId);
            }

            double totalAmount = booking.getTotalAmount()+phat;

            InvoiceDAO invoiceDAO = new InvoiceDAO();
            double totalPaidBefore = invoiceDAO.getTotalPaidAmountByBookingId(bookingId);
            double remainingAmount = totalAmount - totalPaidBefore;

            if (remainingAmount >= 0) {
                invoiceDAO.createInvoice(bookingId, totalAmount, remainingAmount, paymentId);
                boolean updated = bookingDAO.updateBookingStatus(bookingId, "completed");
                bookingDAO.updateTotalAmountByBookingId(bookingId, totalAmount);
                System.out.println("đã đổi đưuọc trạng thái");
                if (!updated) {
                    throw new IllegalStateException("Không thể cập nhật trạng thái trả phòng.");
                }
            }

            request.getSession().setAttribute("showSuccessModal", "Checkout thành công!");
            response.sendRedirect("checkoutBooking");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Có lỗi khi xử lý thanh toán: " + e.getMessage());
            request.getRequestDispatcher("views/receptionist/Invoice.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Hiển thị thông tin hóa đơn (dành cho offline booking)";
    }
}
