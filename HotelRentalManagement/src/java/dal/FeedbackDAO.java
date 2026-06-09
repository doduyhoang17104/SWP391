package dal;

import java.sql.*;
import java.util.*;
import model.Feedback;

public class FeedbackDAO {

    private Connection conn;

    public FeedbackDAO() {
        conn = new DBcontext().c;
    }

    // ✅ Thêm mới feedback
    public void insertFeedback(Feedback feedback) {
        String sql = "INSERT INTO CustomerFeedback (Overall_Rating, Service_Rating, Comment, Rental_Period, Created_At, User_Id) VALUES (?, ?, ?, ?, GETDATE(), ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, feedback.getOverallRating());
            ps.setInt(2, feedback.getServiceRating());
            ps.setString(3, feedback.getComment());
            ps.setString(4, feedback.getRentalPeriod());
            ps.setInt(5, feedback.getUserId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ✅ Lấy feedback theo ID
    public Feedback getFeedbackById(int id) {
        String sql = "SELECT f.*, u.Full_Name "
                + "FROM CustomerFeedback f "
                + "JOIN [User] u ON f.User_Id = u.User_Id "
                + "WHERE f.Feedback_Id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Feedback f = new Feedback();
                f.setFeedbackId(rs.getInt("Feedback_Id"));
                f.setOverallRating(rs.getInt("Overall_Rating"));
                f.setServiceRating(rs.getInt("Service_Rating"));
                f.setComment(rs.getString("Comment"));
                f.setRentalPeriod(rs.getString("Rental_Period"));
                f.setCreatedAt(rs.getTimestamp("Created_At"));
                f.setUserId(rs.getInt("User_Id"));
                f.setUserFullName(rs.getString("Full_Name"));
                return f;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // ✅ Đếm tổng số phản hồi sau khi lọc theo tên, ngày, xếp hạng
    public int countFilteredFeedbacks(String nameKeyword, String dateKeyword, Integer ratingFilter) {
        String sql = "SELECT COUNT(*) "
                + "FROM CustomerFeedback f "
                + "JOIN [User] u ON f.User_Id = u.User_Id WHERE 1=1";

        if (nameKeyword != null && !nameKeyword.trim().isEmpty()) {
            sql += " AND u.Full_Name LIKE ?";
        }
        if (dateKeyword != null && !dateKeyword.trim().isEmpty()) {
            sql += " AND CONVERT(DATE, f.Created_At) = ?";
        }
        if (ratingFilter != null) {
            sql += " AND f.Overall_Rating = ?";
        }

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            int i = 1;
            if (nameKeyword != null && !nameKeyword.trim().isEmpty()) {
                ps.setString(i++, "%" + nameKeyword.trim() + "%");
            }
            if (dateKeyword != null && !dateKeyword.trim().isEmpty()) {
                try {
                    ps.setDate(i++, java.sql.Date.valueOf(dateKeyword.trim()));
                } catch (Exception e) {
                    ps.setDate(i++, null);
                }
            }
            if (ratingFilter != null) {
                ps.setInt(i++, ratingFilter);
            }

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    // ✅ Lấy danh sách phản hồi có phân trang và lọc theo tên, ngày, xếp hạng
    public List<Feedback> searchFeedbacksByPage(String nameKeyword, String dateKeyword, Integer ratingFilter, int pageIndex, int pageSize) {
        List<Feedback> list = new ArrayList<>();

        String sql = "SELECT f.*, u.Full_Name "
                + "FROM CustomerFeedback f "
                + "JOIN [User] u ON f.User_Id = u.User_Id WHERE 1=1";

        if (nameKeyword != null && !nameKeyword.trim().isEmpty()) {
            sql += " AND u.Full_Name LIKE ?";
        }
        if (dateKeyword != null && !dateKeyword.trim().isEmpty()) {
            sql += " AND CONVERT(DATE, f.Created_At) = ?";
        }
        if (ratingFilter != null) {
            sql += " AND f.Overall_Rating = ?";
        }

        sql += " ORDER BY f.Created_At DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            int i = 1;
            if (nameKeyword != null && !nameKeyword.trim().isEmpty()) {
                ps.setString(i++, "%" + nameKeyword.trim() + "%");
            }
            if (dateKeyword != null && !dateKeyword.trim().isEmpty()) {
                try {
                    ps.setDate(i++, java.sql.Date.valueOf(dateKeyword.trim()));
                } catch (Exception e) {
                    ps.setDate(i++, null);
                }
            }
            if (ratingFilter != null) {
                ps.setInt(i++, ratingFilter);
            }

            ps.setInt(i++, (pageIndex - 1) * pageSize); // OFFSET
            ps.setInt(i++, pageSize);                  // FETCH NEXT

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Feedback f = new Feedback();
                f.setFeedbackId(rs.getInt("Feedback_Id"));
                f.setOverallRating(rs.getInt("Overall_Rating"));
                f.setServiceRating(rs.getInt("Service_Rating"));
                f.setComment(rs.getString("Comment"));
                f.setRentalPeriod(rs.getString("Rental_Period"));
                f.setCreatedAt(rs.getTimestamp("Created_At"));
                f.setUserId(rs.getInt("User_Id"));
                f.setUserFullName(rs.getString("Full_Name"));
                list.add(f);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public Map<Integer, Integer> countByRating() {
        Map<Integer, Integer> map = new HashMap<>();

        // Bước 1: Khởi tạo mặc định 1 đến 5 = 0
        for (int i = 1; i <= 5; i++) {
            map.put(i, 0);
        }

        // Bước 2: Lấy dữ liệu từ DB
        String sql = "SELECT Overall_Rating, COUNT(*) AS total FROM CustomerFeedback GROUP BY Overall_Rating";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int rating = rs.getInt("Overall_Rating");
                int total = rs.getInt("total");
                map.put(rating, total); // Ghi đè lại nếu có
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }

    public double getAverageRating() {
        String sql = "SELECT AVG(CAST(Overall_Rating AS FLOAT)) AS avg FROM CustomerFeedback";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble("avg");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getTotalFeedback() {
        String sql = "SELECT COUNT(*) FROM CustomerFeedback";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
