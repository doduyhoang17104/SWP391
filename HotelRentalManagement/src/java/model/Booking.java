package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Booking {

    private int bookingId, roomId, userId, promotionId, roomNumber;
    private String request, userName, roomTypeName, status, note;
    private double totalAmount, depositAmount;
    private Date checkIn, checkOut;
    private Date reservationDate;
    private List<String> serviceNames = new ArrayList<>();
    private List<Integer> serviceQuantities = new ArrayList<>();
    private String userPhone;
    private String userEmail;
    private String userIdentity;
    private int roleId;
    public Booking() {
    }

    public Booking(int bookingId, int roomId, int userId, int promotionId, int roomNumber, String request, String userName, String roomTypeName, String status, String note, double totalAmount, double depositAmount, Date checkIn, Date checkOut, Date reservationDate) {
        this.bookingId = bookingId;
        this.roomId = roomId;
        this.userId = userId;
        this.promotionId = promotionId;
        this.roomNumber = roomNumber;
        this.request = request;
        this.userName = userName;
        this.roomTypeName = roomTypeName;
        this.status = status;
        this.note = note;
        this.totalAmount = totalAmount;
        this.depositAmount = depositAmount;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.reservationDate = reservationDate;
    }

    public Booking(int bookingId, int roomId, int userId, int promotionId, int roomNumber, String request, String status, double totalAmount, Date checkIn, Date checkOut, Date reservationDate) {
        this.bookingId = bookingId;
        this.roomId = roomId;
        this.userId = userId;
        this.promotionId = promotionId;
        this.roomNumber = roomNumber;
        this.request = request;
        this.status = status;
        this.totalAmount = totalAmount;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.reservationDate = reservationDate;
    }

    public Booking(int bookingId, int roomId, int userId, int promotionId, int roomNumber, String request, double totalAmount, Date checkIn, Date checkOut, List<String> serviceNames, List<Integer> serviceQuantities) {
        this.bookingId = bookingId;
        this.roomId = roomId;
        this.userId = userId;
        this.promotionId = promotionId;
        this.roomNumber = roomNumber;
        this.request = request;
        this.totalAmount = totalAmount;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.serviceNames = serviceNames;
        this.serviceQuantities = serviceQuantities;
    }

    public Booking(int bookingId, int roomId, int userId, int promotionId, int roomNumber, String request, String userName, String roomTypeName, String status, String note, double totalAmount, double depositAmount, Date checkIn, Date checkOut, Date reservationDate, String userPhone, String userEmail, String userIdentity, int roleId) {
        this.bookingId = bookingId;
        this.roomId = roomId;
        this.userId = userId;
        this.promotionId = promotionId;
        this.roomNumber = roomNumber;
        this.request = request;
        this.userName = userName;
        this.roomTypeName = roomTypeName;
        this.status = status;
        this.note = note;
        this.totalAmount = totalAmount;
        this.depositAmount = depositAmount;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.reservationDate = reservationDate;
        this.userPhone = userPhone;
        this.userEmail = userEmail;
        this.userIdentity = userIdentity;
        this.roleId = roleId;
    }



    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(int promotionId) {
        this.promotionId = promotionId;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
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

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public List<String> getServiceNames() {
        return serviceNames;
    }

    public void setServiceNames(List<String> serviceNames) {
        this.serviceNames = serviceNames;
    }

    public List<Integer> getServiceQuantities() {
        return serviceQuantities;
    }

    public void setServiceQuantities(List<Integer> serviceQuantities) {
        this.serviceQuantities = serviceQuantities;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoomTypeName() {
        return roomTypeName;
    }

    public void setRoomTypeName(String roomTypeName) {
        this.roomTypeName = roomTypeName;
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

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    public double getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(double depositAmount) {
        this.depositAmount = depositAmount;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserIdentity() {
        return userIdentity;
    }

    public void setUserIdentity(String userIdentity) {
        this.userIdentity = userIdentity;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "Booking{"
                + "bookingId=" + bookingId
                + ", roomId=" + roomId
                + ", userId=" + userId
                + ", promotionId=" + promotionId
                + ", roomNumber=" + roomNumber
                + ", request='" + request + '\''
                + ", totalAmount=" + totalAmount
                + ", checkIn=" + checkIn
                + ", checkOut=" + checkOut
                + ", serviceNames=" + serviceNames
                + ", serviceQuantities=" + serviceQuantities
                + '}';
    }
}
