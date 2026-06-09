/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ddhoang
 */
import java.util.Date;

public class Revenue {

    private Date day;
    private int year;
    private int month;
    private double totalRevenue;

    public Revenue() {
    }

    public Revenue(Date day, int year, int month, double totalRevenue) {
        this.day = day;
        this.year = year;
        this.month = month;
        this.totalRevenue = totalRevenue;
    }

    // Constructor for daily revenue
    public Revenue(Date day, double totalRevenue) {
        this.day = day;
        this.totalRevenue = totalRevenue;
    }

    // Constructor for monthly revenue
    public Revenue(int year, int month, double totalRevenue) {
        this.year = year;
        this.month = month;
        this.totalRevenue = totalRevenue;
    }

    // Constructor for yearly revenue
    public Revenue(int year, double totalRevenue) {
        this.year = year;
        this.totalRevenue = totalRevenue;
    }

    // Getters and Setters
    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}
