package dal;

import model.Food;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FoodDAO {

    private Connection c;

    // Constructor dùng chung Connection (transaction)
    public FoodDAO(Connection c) {
        this.c = c;
    }

    // Constructor mặc định
    public FoodDAO() {
        this.c = new DBcontext().c;
    }

    // Lấy tất cả món ăn
    public List<Food> getAllFood() throws SQLException {
        List<Food> list = new ArrayList<>();
        String sql = "SELECT * FROM Food";
        PreparedStatement ps = c.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            list.add(mapResultSetToFood(rs));
        }
        return list;
    }

    // Phân trang không điều kiện
    public List<Food> getFoodByPage(int offset, int limit) throws SQLException {
        List<Food> list = new ArrayList<>();
        String sql = "SELECT * FROM Food ORDER BY Food_Id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setInt(1, offset);
        ps.setInt(2, limit);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            list.add(mapResultSetToFood(rs));
        }
        return list;
    }

    // Tìm kiếm món ăn với phân trang
    public List<Food> searchFood(String keyword, String category, Double priceFrom, Double priceTo, int offset, int limit) throws SQLException {
        List<Food> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM Food WHERE 1=1");

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND Food_Name LIKE ?");
        }
        if (category != null && !category.isEmpty()) {
            sql.append(" AND Category = ?");
        }
        if (priceFrom != null) {
            sql.append(" AND Price >= ?");
        }
        if (priceTo != null) {
            sql.append(" AND Price <= ?");
        }

        sql.append(" ORDER BY Food_Id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");

        PreparedStatement ps = c.prepareStatement(sql.toString());

        int paramIndex = 1;
        if (keyword != null && !keyword.trim().isEmpty()) {
            ps.setString(paramIndex++, "%" + keyword + "%");
        }
        if (category != null && !category.isEmpty()) {
            ps.setString(paramIndex++, category);
        }
        if (priceFrom != null) {
            ps.setDouble(paramIndex++, priceFrom);
        }
        if (priceTo != null) {
            ps.setDouble(paramIndex++, priceTo);
        }

        ps.setInt(paramIndex++, offset);
        ps.setInt(paramIndex, limit);

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            list.add(mapResultSetToFood(rs));
        }
        return list;
    }

    // Đếm tổng số bản ghi không điều kiện
    public int getTotalFoodCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM Food";
        PreparedStatement ps = c.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }

    // Đếm tổng bản ghi có điều kiện
    public int getTotalFoodCount(String keyword, String category, Double priceFrom, Double priceTo) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM Food WHERE 1=1");

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND Food_Name LIKE ?");
        }
        if (category != null && !category.isEmpty()) {
            sql.append(" AND Category = ?");
        }
        if (priceFrom != null) {
            sql.append(" AND Price >= ?");
        }
        if (priceTo != null) {
            sql.append(" AND Price <= ?");
        }

        PreparedStatement ps = c.prepareStatement(sql.toString());

        int paramIndex = 1;
        if (keyword != null && !keyword.trim().isEmpty()) {
            ps.setString(paramIndex++, "%" + keyword + "%");
        }
        if (category != null && !category.isEmpty()) {
            ps.setString(paramIndex++, category);
        }
        if (priceFrom != null) {
            ps.setDouble(paramIndex++, priceFrom);
        }
        if (priceTo != null) {
            ps.setDouble(paramIndex++, priceTo);
        }

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }

    // Thêm món ăn
    public void insertFood(Food food) throws SQLException {
        String sql = "INSERT INTO Food (Food_Name, Description, Price, Category, Availability, Image) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setString(1, food.getName());
        ps.setString(2, food.getDescription());
        ps.setDouble(3, food.getPrice());
        ps.setString(4, food.getCategory());
        ps.setBoolean(5, food.isAvailability());
        ps.setString(6, food.getImage());
        ps.executeUpdate();
    }

    // Xoá món ăn
    public void deleteFood(int id) throws SQLException {
        String sql = "DELETE FROM Food WHERE Food_Id = ?";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    // Cập nhật món ăn
    public void updateFood(Food food) throws SQLException {
        String sql = "UPDATE Food SET Food_Name=?, Description=?, Price=?, Category=?, Availability=?, Image=?, Updated_At=GETDATE() WHERE Food_Id=?";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setString(1, food.getName());
        ps.setString(2, food.getDescription());
        ps.setDouble(3, food.getPrice());
        ps.setString(4, food.getCategory());
        ps.setBoolean(5, food.isAvailability());
        ps.setString(6, food.getImage());
        ps.setInt(7, food.getId());
        ps.executeUpdate();
    }

    // Lấy món ăn theo ID
    public Food getFoodById(int id) throws SQLException {
        String sql = "SELECT * FROM Food WHERE Food_Id = ?";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return mapResultSetToFood(rs);
        }
        return null;
    }

    // Map ResultSet → Food object
    private Food mapResultSetToFood(ResultSet rs) throws SQLException {
        Food f = new Food();
        f.setId(rs.getInt("Food_Id"));
        f.setName(rs.getString("Food_Name"));
        f.setDescription(rs.getString("Description"));
        f.setPrice(rs.getDouble("Price"));
        f.setCategory(rs.getString("Category"));
        f.setAvailability(rs.getBoolean("Availability"));
        f.setImage(rs.getString("Image"));
        f.setCreatedAt(rs.getTimestamp("Created_At"));
        f.setUpdatedAt(rs.getTimestamp("Updated_At"));
        return f;
    }
    public int countSearchFood(String keyword, String category, Double priceFrom, Double priceTo) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM Food WHERE 1=1");

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND Food_Name LIKE ?");
        }
        if (category != null && !category.isEmpty()) {
            sql.append(" AND Category = ?");
        }
        if (priceFrom != null) {
            sql.append(" AND Price >= ?");
        }
        if (priceTo != null) {
            sql.append(" AND Price <= ?");
        }

        PreparedStatement ps = c.prepareStatement(sql.toString());
        int paramIndex = 1;
        if (keyword != null && !keyword.trim().isEmpty()) {
            ps.setString(paramIndex++, "%" + keyword + "%");
        }
        if (category != null && !category.isEmpty()) {
            ps.setString(paramIndex++, category);
        }
        if (priceFrom != null) {
            ps.setDouble(paramIndex++, priceFrom);
        }
        if (priceTo != null) {
            ps.setDouble(paramIndex++, priceTo);
        }

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }

    public List<Food> getLimitedFood(int limit) throws SQLException {
        List<Food> list = new ArrayList<>();
        String sql = "SELECT * FROM Food WHERE Availability = 1 ORDER BY Food_Id OFFSET 0 ROWS FETCH NEXT ? ROWS ONLY";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setInt(1, limit);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            list.add(mapResultSetToFood(rs));
        }
        return list;
    }
}
