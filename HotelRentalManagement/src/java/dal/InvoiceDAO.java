/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Invoice;

/**
 *
 * @author ddhoang
 */
public class InvoiceDAO extends DBcontext {

    public void insertInvoice(double totalAmount, int bookingId, int depositAmount) {
        String sql = "INSERT INTO Invoice (Total_Amount, Date, Payment_Id, Booking_Id, Deposit_Amount) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement st = c.prepareStatement(sql)) {
            java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(System.currentTimeMillis());
            st.setDouble(1, totalAmount);
            st.setTimestamp(2, currentTimestamp);
            st.setInt(3, 1);
            st.setInt(4, bookingId);
            st.setInt(5, depositAmount);
            st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createInvoice(int bookingId, double totalAmount, double depositAmount, int paymentId) {
        String sql = "INSERT INTO Invoice (Total_Amount, Deposit_Amount, Date, Payment_Id, Booking_Id) VALUES (?, ?, GETDATE(), ?, ?)";
        try (PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setDouble(1, totalAmount);
            ps.setDouble(2, depositAmount);
            ps.setInt(3, paymentId);
            ps.setInt(4, bookingId);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateInvoiceTotalAmount(int bookingId, double totalAmount) {
        String sql = "UPDATE Invoice SET Total_Amount = ? WHERE Booking_Id = ? AND Deposit_Amount < Total_Amount";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setDouble(1, totalAmount);
            ps.setInt(2, bookingId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Invoice getInvoiceByBookingId(int bookingId) {
        String sql = "SELECT * FROM Invoice WHERE Booking_Id = ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, bookingId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Invoice invoice = new Invoice();
                    invoice.setInvoiceId(rs.getInt("Invoice_Id"));
                    invoice.setTotalAmount(rs.getDouble("Total_Amount"));
                    invoice.setDepositAmount(rs.getDouble("Deposit_Amount"));
                    invoice.setPaymentId(rs.getInt("Payment_Id"));
                    invoice.setBookingId(rs.getInt("Booking_Id"));
                    invoice.setDate(rs.getTimestamp("Date"));
                    return invoice;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public double getTotalPaidAmountByBookingId(int bookingId) {
    String sql = "SELECT SUM(Deposit_Amount) AS TotalPaid FROM Invoice WHERE Booking_Id = ?";
    try (PreparedStatement ps = c.prepareStatement(sql)) {
        ps.setInt(1, bookingId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getDouble("TotalPaid");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return 0.0;
}

public int getPaymentIdByBookingId(int bookingId) {
    String sql = "SELECT TOP 1 Payment_Id FROM Invoice WHERE Booking_Id = ? ORDER BY Date DESC";
    try (PreparedStatement ps = c.prepareStatement(sql)) {
        ps.setInt(1, bookingId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt("Payment_Id");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return 0; // Trả về 0 nếu không tìm thấy
}

    public static void main(String[] args) {
        int bookingId = 100;
        InvoiceDAO dao = new InvoiceDAO();
        Invoice invoice = dao.getInvoiceByBookingId(bookingId);

        if (invoice != null) {
            System.out.println("Invoice found!");
            System.out.println("InvoiceId      : " + invoice.getInvoiceId());
            System.out.println("BookingId      : " + invoice.getBookingId());
            System.out.println("Total Amount   : " + invoice.getTotalAmount());
            System.out.println("Deposit Amount : " + invoice.getDepositAmount());
            System.out.println("Payment Id     : " + invoice.getPaymentId());
            System.out.println("Date           : " + invoice.getDate());
        } else {
            System.out.println("No invoice found for bookingId = " + bookingId);
        }
    }
}
