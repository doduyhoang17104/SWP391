package model;

import java.util.Date;

public class InvoiceSummary {
   private int roomNumber;
private String roomTypeName;
private Date checkIn;
private Date checkOut;
private String specialRequests;
private double totalAmount;
private double depositAmount;
private double remainingAmount;

    public InvoiceSummary() {
    }


    public InvoiceSummary(int roomNumber, String roomTypeName, Date checkIn, Date checkOut, String specialRequests, double totalAmount, double depositAmount, double remainingAmount) {
        this.roomNumber = roomNumber;
        this.roomTypeName = roomTypeName;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.specialRequests = specialRequests;
        this.totalAmount = totalAmount;
        this.depositAmount = depositAmount;
        this.remainingAmount = remainingAmount;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getRoomTypeName() {
        return roomTypeName;
    }

    public void setRoomTypeName(String roomTypeName) {
        this.roomTypeName = roomTypeName;
    }

    public Date getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Date checkIn) {
        this.checkIn = checkIn;
    }

    public Date getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Date checkOut) {
        this.checkOut = checkOut;
    }

    public String getSpecialRequests() {
        return specialRequests;
    }

    public void setSpecialRequests(String specialRequests) {
        this.specialRequests = specialRequests;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(double depositAmount) {
        this.depositAmount = depositAmount;
    }

    public double getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(double remainingAmount) {
        this.remainingAmount = remainingAmount;
    }

}
