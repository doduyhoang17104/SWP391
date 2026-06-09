/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Revenue;

/**
 *
 * @author ddhoang
 */
public class ChartDAO extends DBcontext {

    public List<Revenue> getRevenueByDay() {
        List<Revenue> revenueList = new ArrayList<>();
        Map<LocalDate, Double> revenueMap = new HashMap<>();

        String sql = "SELECT CAST(i.Date AS DATE) AS Day, SUM(i.Total_Amount) AS Total_Revenue "
                + "FROM [Invoice] i "
                + "JOIN [Booking] b ON i.Booking_Id = b.Booking_Id "
                + "WHERE b.Status = 'completed' "
                + "AND MONTH(i.Date) = MONTH(GETDATE()) AND YEAR(i.Date) = YEAR(GETDATE()) "
                + "GROUP BY CAST(i.Date AS DATE) "
                + "ORDER BY CAST(i.Date AS DATE)";

        try {
            PreparedStatement st = c.prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            // Lưu doanh thu thực tế vào map theo ngày
            while (rs.next()) {
                LocalDate date = rs.getDate("Day").toLocalDate();
                double total = rs.getDouble("Total_Revenue");
                revenueMap.put(date, total);
            }

            // Tạo list đủ ngày trong tháng hiện tại
            LocalDate today = LocalDate.now();
            YearMonth currentMonth = YearMonth.of(today.getYear(), today.getMonth());
            int totalDays = currentMonth.lengthOfMonth();

            for (int i = 1; i <= totalDays; i++) {
                LocalDate date = currentMonth.atDay(i);
                double totalRevenue = revenueMap.getOrDefault(date, 0.0);

                Revenue revenue = new Revenue();
                revenue.setDay(Date.valueOf(date)); // date là LocalDate
                revenue.setTotalRevenue(totalRevenue);
                revenueList.add(revenue);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return revenueList;
    }

    public List<Revenue> getRevenueByMonth() {
        List<Revenue> revenueList = new ArrayList<>();
        String sql = "SELECT m.Month AS Month, ISNULL(SUM(i.Total_Amount), 0) AS Total_Revenue, YEAR(GETDATE()) AS Year "
                + "FROM (SELECT 1 AS Month UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 "
                + "UNION SELECT 7 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10 UNION SELECT 11 UNION SELECT 12) m "
                + "LEFT JOIN Invoice i ON MONTH(i.Date) = m.Month AND YEAR(i.Date) = YEAR(GETDATE()) "
                + "LEFT JOIN Booking b ON i.Booking_Id = b.Booking_Id AND b.Status = 'completed' "
                + "GROUP BY m.Month "
                + "ORDER BY m.Month";
        try {
            PreparedStatement st = c.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Revenue revenue = new Revenue(
                        rs.getInt("Year"),
                        rs.getInt("Month"),
                        rs.getDouble("Total_Revenue")
                );
                revenueList.add(revenue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return revenueList;
    }

    public List<Revenue> getRevenueByYear() {
        List<Revenue> revenueList = new ArrayList<>();
        String sql = "SELECT y.Year AS Year, ISNULL(SUM(i.Total_Amount), 0) AS Total_Revenue "
                + "FROM (SELECT 2021 AS Year UNION SELECT 2022 UNION SELECT 2023 UNION SELECT 2024 UNION SELECT 2025) y "
                + "LEFT JOIN Invoice i ON YEAR(i.Date) = y.Year "
                + "LEFT JOIN Booking b ON i.Booking_Id = b.Booking_Id AND b.Status = 'completed' "
                + "GROUP BY y.Year "
                + "ORDER BY y.Year";
        try {
            PreparedStatement st = c.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Revenue revenue = new Revenue(
                        rs.getInt("Year"),
                        rs.getDouble("Total_Revenue")
                );
                revenueList.add(revenue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return revenueList;
    }

}
