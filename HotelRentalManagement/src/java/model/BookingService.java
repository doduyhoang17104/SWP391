/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import model.Service.Service;
/**
 *
 * @author admin
 */
public class BookingService {
    private int bookingServiceId;
    private int quantity;
    private int bookingId;
    private Service service;

    public BookingService() {}

    public BookingService(int bookingServiceId, int quantity, int bookingId, Service service) {
        this.bookingServiceId = bookingServiceId;
        this.quantity = quantity;
        this.bookingId = bookingId;
        this.service = service;
    }

    public int getBookingServiceId() {
        return bookingServiceId;
    }

    public void setBookingServiceId(int bookingServiceId) {
        this.bookingServiceId = bookingServiceId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }
}

