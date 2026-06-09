/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDateTime;

/**
 *
 * @author admin
 */
public class OfflineBookingUser {
     private int offlineUserId;
    private String fullName;
    private String phoneNumber;
    private String address;
    private String identityCard;
    private int bookingId;
    private int bookedByReceptionistId;
    private LocalDateTime createdAt;

    public OfflineBookingUser() {
    }

    public OfflineBookingUser(int offlineUserId, String fullName, String phoneNumber, String address, String identityCard, int bookingId, int bookedByReceptionistId, LocalDateTime createdAt) {
        this.offlineUserId = offlineUserId;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.identityCard = identityCard;
        this.bookingId = bookingId;
        this.bookedByReceptionistId = bookedByReceptionistId;
        this.createdAt = createdAt;
    }

    public int getOfflineUserId() {
        return offlineUserId;
    }

    public void setOfflineUserId(int offlineUserId) {
        this.offlineUserId = offlineUserId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getBookedByReceptionistId() {
        return bookedByReceptionistId;
    }

    public void setBookedByReceptionistId(int bookedByReceptionistId) {
        this.bookedByReceptionistId = bookedByReceptionistId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
}
