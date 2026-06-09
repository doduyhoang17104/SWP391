package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.Promotion;

/**
 *
 * @author ddhoang
 */
public class PromotionDAO extends DBcontext {

    public ArrayList<Promotion> getAllPromotions() {
        ArrayList<Promotion> list = new ArrayList<>();
        String sql = "SELECT * FROM Promotion";

        try {
            PreparedStatement st = c.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Promotion p = new Promotion();
                p.setPromotionId(rs.getInt("Promotion_Id"));
                p.setPromotionName(rs.getString("Promotion_Name"));
                p.setDiscountPercent(rs.getBigDecimal("Discount_Percent"));
                p.setStartDate(rs.getDate("Start_Date"));
                p.setEndDate(rs.getDate("End_Date"));
                p.setStatus(rs.getInt("Status"));
                p.setDescription(rs.getString("Description"));
                p.setQuantity(rs.getInt("Quantity"));

                list.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace(); // hoặc log lỗi ra file
        }

        return list;
    }

    public boolean insertPromotion(Promotion p) {
        String sql = "INSERT INTO Promotion (Promotion_Name, Discount_Percent, Start_Date, End_Date, Status, Description, Quantity) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement st = c.prepareStatement(sql)) {
            st.setString(1, p.getPromotionName());
            st.setBigDecimal(2, p.getDiscountPercent());
            st.setDate(3, new java.sql.Date(p.getStartDate().getTime()));
            st.setDate(4, new java.sql.Date(p.getEndDate().getTime()));
            st.setInt(5, p.getStatus());
            st.setString(6, p.getDescription());
            st.setInt(7, p.getQuantity());
            return st.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Promotion getPromotionById(int id) {
        String sql = "SELECT Promotion_Id, Promotion_Name, Discount_Percent, Start_Date, End_Date, Status, Description, Quantity FROM Promotion WHERE Promotion_Id = ?";
        try (PreparedStatement st = c.prepareStatement(sql)) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return new Promotion(
                            rs.getInt("Promotion_Id"),
                            rs.getString("Promotion_Name"),
                            rs.getBigDecimal("Discount_Percent"),
                            rs.getDate("Start_Date"),
                            rs.getDate("End_Date"),
                            rs.getInt("Status"),
                            rs.getString("Description"),
                            rs.getInt("Quantity")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updatePromotion(Promotion p) {
        String sql = "UPDATE Promotion SET Promotion_Name=?, Discount_Percent=?, Start_Date=?, End_Date=?, Status=?, Description=?, Quantity=? WHERE Promotion_Id=?";
        try (PreparedStatement st = c.prepareStatement(sql)) {
            st.setString(1, p.getPromotionName());
            st.setBigDecimal(2, p.getDiscountPercent());
            st.setDate(3, new java.sql.Date(p.getStartDate().getTime()));
            st.setDate(4, new java.sql.Date(p.getEndDate().getTime()));
            st.setInt(5, p.getStatus());
            st.setString(6, p.getDescription());
            st.setInt(7, p.getQuantity());
            st.setInt(8, p.getPromotionId());
            return st.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deletePromotion(int id) {
        String sql = "DELETE FROM Promotion WHERE Promotion_Id=?";
        try (PreparedStatement st = c.prepareStatement(sql)) {
            st.setInt(1, id);
            return st.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<Promotion> searchPromotions(String promotionName, String discountPercent, String startDate, String endDate, String quantity, String status) {
        ArrayList<Promotion> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM Promotion WHERE 1=1");
        ArrayList<Object> params = new ArrayList<>();

        if (promotionName != null && !promotionName.trim().isEmpty()) {
            sql.append(" AND Promotion_Name LIKE ?");
            params.add("%" + promotionName.trim() + "%");
        }
        if (discountPercent != null && !discountPercent.trim().isEmpty()) {
            sql.append(" AND Discount_Percent = ?");
            params.add(new java.math.BigDecimal(discountPercent.trim()));
        }
        if (startDate != null && !startDate.trim().isEmpty()) {
            sql.append(" AND Start_Date >= ?");
            params.add(java.sql.Date.valueOf(startDate.trim()));
        }
        if (endDate != null && !endDate.trim().isEmpty()) {
            sql.append(" AND End_Date <= ?");
            params.add(java.sql.Date.valueOf(endDate.trim()));
        }
        if (quantity != null && !quantity.trim().isEmpty()) {
            sql.append(" AND Quantity = ?");
            params.add(Integer.parseInt(quantity.trim()));
        }
        if (status != null && !status.trim().isEmpty()) {
            sql.append(" AND Status = ?");
            params.add(Integer.parseInt(status.trim()));
        }

        try {
            PreparedStatement st = c.prepareStatement(sql.toString());
            for (int i = 0; i < params.size(); i++) {
                st.setObject(i + 1, params.get(i));
            }
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Promotion p = new Promotion();
                p.setPromotionId(rs.getInt("Promotion_Id"));
                p.setPromotionName(rs.getString("Promotion_Name"));
                p.setDiscountPercent(rs.getBigDecimal("Discount_Percent"));
                p.setStartDate(rs.getDate("Start_Date"));
                p.setEndDate(rs.getDate("End_Date"));
                p.setStatus(rs.getInt("Status"));
                p.setDescription(rs.getString("Description"));
                p.setQuantity(rs.getInt("Quantity"));
                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    public int countPromotionsWithFilter(String promotionName, String discountPercent, String status, String quantity) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM Promotion WHERE 1=1");
        ArrayList<Object> params = new ArrayList<>();
        if (promotionName != null && !promotionName.isEmpty()) {
            sql.append(" AND Promotion_Name LIKE ?");
            params.add("%" + promotionName + "%");
        }
        if (discountPercent != null && !discountPercent.isEmpty()) {
            sql.append(" AND Discount_Percent = ?");
            params.add(discountPercent);
        }
        if (status != null && !status.isEmpty()) {
            sql.append(" AND Status = ?");
            params.add(status);
        }
        if (quantity != null && !quantity.isEmpty()) {
            sql.append(" AND Quantity = ?");
            params.add(quantity);
        }
        try (PreparedStatement st = c.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                st.setObject(i + 1, params.get(i));
            }
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean decreasePromotionQuantity(int promotionId) {
        String sql = "UPDATE Promotion SET Quantity = Quantity - 1 WHERE Promotion_Id = ? AND Quantity > 0";
        try {
            PreparedStatement st = c.prepareStatement(sql);
            st.setInt(1, promotionId);
            int affectedRows = st.executeUpdate();
            return affectedRows > 0; // true nếu có ít nhất 1 dòng được cập nhật

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}

