package model;

import java.util.Date;
import java.util.List;

public class FoodOrder {
    private int id;
    private int bookingId;
    private Date orderTime;
    private String status;
    private String note;
    private List<FoodOrderDetail> details;

    // Constructor mặc định (bắt buộc để tạo object trống)
    public FoodOrder() {
    }

    // Constructor đầy đủ
    public FoodOrder(int id, int bookingId, Date orderTime, String status, String note, List<FoodOrderDetail> details) {
        this.id = id;
        this.bookingId = bookingId;
        this.orderTime = orderTime;
        this.status = status;
        this.note = note;
        this.details = details;
    }

    // Getter và Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<FoodOrderDetail> getDetails() {
        return details;
    }

    public void setDetails(List<FoodOrderDetail> details) {
        this.details = details;
    }
}
