/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;

/**
 *
 * @author ddhoang
 */
public class DailyLogin {

    private LocalDate day;
    private int count;

    public DailyLogin(LocalDate day, int count) {
        this.day = day;
        this.count = count;
    }

    public LocalDate getDay() {
        return day;
    }

    public int getCount() {
        return count;
    }
}
