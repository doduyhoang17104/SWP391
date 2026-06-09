/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import jakarta.mail.internet.MimeUtility;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

/**
 *
 * @author ddhoang
 */
public class EmailUtil {

    private static final String FROM_EMAIL = "hoangdo171004@gmail.com";
    private static final String PASSWORD = "zulsidfvutgmjcuw";

    // Tạo session Gmail dùng chung
    private static Session createGmailSession() {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        return Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_EMAIL, PASSWORD);
            }
        });
    }

    // Gửi OTP đơn giản
    public static boolean sendOTP(String toEmail, String otp) {
        try {
            Session session = createGmailSession();

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL, MimeUtility.encodeText("Khách sạn Royal", "UTF-8", "B")));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            String subject = "Mã xác nhận đặt lại mật khẩu";
            message.setSubject(MimeUtility.encodeText(subject, "UTF-8", "B"));
            String content = "Xin chào,\n\nMã xác nhận của bạn là: " + otp + "\n\nVui lòng không chia sẻ mã này.";
            message.setContent(content, "text/html; charset=UTF-8");
            Transport.send(message);
            return true;
        } catch (Exception e) {
            System.out.println("Lỗi gửi OTP: " + e.getMessage());
            return false;
        }
    }

    // Gửi thông báo booking (phê duyệt / từ chối)
    public static boolean sendBookingNotification(String toEmail, String subject, String userName, String messageBody) {
        try {
            Session session = createGmailSession();

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL, MimeUtility.encodeText("Khách sạn Royal", "UTF-8", "B")));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(MimeUtility.encodeText(subject, "UTF-8", "B")); // Hỗ trợ tiêu đề tiếng Việt

            String htmlContent = "<div style=\"font-family:'Segoe UI',sans-serif; color:#333; font-size:15px; line-height:1.6; max-width:600px; margin:auto; border:1px solid #ddd; border-radius:10px; padding:20px;\">"
                    + "<h2 style=\"color:#0d6efd;\">Thông báo đặt phòng</h2>"
                    + "<p>Xin chào <strong>" + userName + "</strong>,</p>"
                    + "<p>" + messageBody + "</p>"
                    + "<p style=\"margin-top:20px;\">Trân trọng,<br><strong>Khách sạn Royal</strong></p>"
                    + "</div>";

            message.setContent(htmlContent, "text/html; charset=UTF-8");
            Transport.send(message);
            return true;
        } catch (Exception e) {
            System.out.println("Lỗi gửi email booking: " + e.getMessage());
            return false;
        }
    }

    public static boolean sendContactMessage(String fromUserEmail, String userName, String subject, String messageBody) {
        final String toHotelEmail = "hoangdo171004@gmail.com"; // Nhân viên/Quản lý nhận liên hệ

        try {
            Session session = createGmailSession();

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL, MimeUtility.encodeText("Khách sạn Royal", "UTF-8", "B")));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toHotelEmail));
            message.setSubject(MimeUtility.encodeText("Liên hệ từ người dùng: " + subject, "UTF-8", "B"));

            String htmlContent = "<div style=\"font-family:'Segoe UI',sans-serif; font-size:15px; color:#333; border:1px solid #ddd; border-radius:10px; padding:20px; max-width:600px; margin:auto;\">"
                    + "<h3 style=\"color:#0d6efd;\">Yêu cầu liên hệ từ khách hàng</h3>"
                    + "<p><strong>Họ tên:</strong> " + userName + "</p>"
                    + "<p><strong>Email:</strong> " + fromUserEmail + "</p>"
                    + "<p><strong>Chủ đề:</strong> " + subject + "</p>"
                    + "<p><strong>Nội dung:</strong></p>"
                    + "<p style=\"background-color:#f8f9fa; padding:10px; border-left:4px solid #0d6efd;\">" + messageBody + "</p>"
                    + "<br><p>Hệ thống khách sạn tự động gửi thông báo.</p>"
                    + "</div>";

            message.setContent(htmlContent, "text/html; charset=UTF-8");
            Transport.send(message);
            return true;

        } catch (Exception e) {
            System.out.println("Lỗi gửi liên hệ: " + e.getMessage());
            return false;
        }
    }

    public static boolean sendInvoice(String toEmail, int numPeople, String roomType, double basePrice, int night,
            String checkIn, String checkOut, String specialRequest,
            String[] selectedServices,
            int extraFee, double voucherDiscount, int pointsUsedAmount,
            int totalAmount, int prepaymentPercent, int paymentAmount) {
        try {
            Session session = createGmailSession();

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL, MimeUtility.encodeText("Khách sạn Royal", "UTF-8", "B")));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(MimeUtility.encodeText("Hóa đơn đặt phòng từ Khách sạn Royal", "UTF-8", "B"));

            StringBuilder serviceListHtml = new StringBuilder();
            if (selectedServices != null && selectedServices.length > 0) {
                for (String s : selectedServices) {
                    serviceListHtml.append("<li>").append(s).append("</li>");
                }
            } else {
                serviceListHtml.append("<li>Không có dịch vụ nào được chọn.</li>");
            }

            NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));

            String htmlContent
                    = "<div style='font-family:Segoe UI, sans-serif; color:#333; font-size:15px; line-height:1.6; max-width:700px; margin:auto; border:1px solid #eee; border-radius:8px; padding:30px; background:#fff;'>"
                    + "<h2 style='color:#0d6efd; text-align:center; border-bottom:1px solid #ddd; padding-bottom:10px;'>🧾 HÓA ĐƠN THANH TOÁN</h2>"
                    + "<p style='text-align:center; margin-bottom:30px;'>Cảm ơn bạn đã đặt phòng tại <strong>Khách sạn Royal</strong>!</p>"
                    + "<h3 style='color:#0d6efd;'>1. Thông tin khách hàng</h3>"
                    + "<table style='width:100%; border-collapse:collapse; margin-bottom:20px;'>"
                    + "  <tr><td><strong>Email:</strong></td><td>" + toEmail + "</td></tr>"
                    + "  <tr><td><strong>Số người:</strong></td><td>" + numPeople + "</td></tr>"
                    + "</table>"
                    + "<h3 style='color:#0d6efd;'>2. Chi tiết đặt phòng</h3>"
                    + "<table style='width:100%; border-collapse:collapse; margin-bottom:20px;'>"
                    + "  <tr><td><strong>Loại phòng:</strong></td><td>" + roomType + "</td></tr>"
                    + "  <tr><td><strong>Giá/đêm:</strong></td><td>" + formatter.format(basePrice) + "đ</td></tr>"
                    + "  <tr><td><strong>Số đêm:</strong></td><td>" + night + "</td></tr>"
                    + "  <tr><td><strong>Nhận phòng:</strong></td><td>" + checkIn + "</td></tr>"
                    + "  <tr><td><strong>Trả phòng:</strong></td><td>" + checkOut + "</td></tr>"
                    + "  <tr><td><strong>Yêu cầu đặc biệt:</strong></td><td>" + specialRequest + "</td></tr>"
                    + "</table>"
                    + "<h3 style='color:#0d6efd;'>3. Dịch vụ đã chọn</h3>"
                    + "<ul style='margin-left:20px;'>"
                    + serviceListHtml
                    + "</ul>"
                    + "<h3 style='color:#0d6efd;'>4. Điều chỉnh chi phí</h3>"
                    + "<table style='width:100%; border-collapse:collapse; margin-bottom:20px;'>"
                    + "  <tr><td>Phụ phí thêm người:</td><td>" + formatter.format(extraFee) + "đ</td></tr>"
                    + "  <tr><td>Giảm giá mã giảm giá:</td><td>-" + formatter.format(voucherDiscount) + "đ</td></tr>"
                    + "  <tr><td>Đổi điểm thưởng:</td><td>-" + formatter.format(pointsUsedAmount) + "đ</td></tr>"
                    + "</table>"
                    + "<h3 style='color:#0d6efd;'>5. Tổng kết thanh toán</h3>"
                    + "<table style='width:100%; border-collapse:collapse; font-size:17px;'>"
                    + "  <tr>"
                    + "    <td><strong>Tổng tiền cần thanh toán:</strong></td>"
                    + "    <td style='color:#d32f2f;'><strong>" + formatter.format(totalAmount) + "đ</strong></td>"
                    + "  </tr>"
                    + "  <tr>"
                    + "    <td><strong>Số tiền bạn đã thanh toán (" + prepaymentPercent + "%):</strong></td>"
                    + "    <td style='color:#ff6f00;'><strong>" + formatter.format(paymentAmount) + "đ</strong></td>"
                    + "  </tr>"
                    + "</table>"
                    + "<p style='margin-top:40px; text-align:center;'>Trân trọng,<br><strong>Khách sạn Royal</strong></p>"
                    + "</div>";

            message.setContent(htmlContent, "text/html; charset=UTF-8");
            Transport.send(message);
            return true;
        } catch (Exception e) {
            System.out.println("Lỗi gửi email hóa đơn: " + e.getMessage());
            return false;
        }
    }

}
