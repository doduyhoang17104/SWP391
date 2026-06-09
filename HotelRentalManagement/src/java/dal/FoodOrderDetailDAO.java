package dal;

import model.Food;
import model.FoodOrderDetail;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FoodOrderDetailDAO {
    private Connection c;

    public FoodOrderDetailDAO(Connection c) {
        this.c = c;
    }

    // Thêm chi tiết đơn món
    public void insertFoodOrderDetail(FoodOrderDetail detail) throws SQLException {
    String sql = "INSERT INTO FoodOrderDetail (Food_Order_Id, Food_Id, Quantity, Price) VALUES (?, ?, ?, ?)";
    PreparedStatement ps = c.prepareStatement(sql);
    ps.setInt(1, detail.getFoodOrderId());
    ps.setInt(2, detail.getFood().getId());
    ps.setInt(3, detail.getQuantity());
    ps.setDouble(4, detail.getPrice());
    ps.executeUpdate();
}



    // Lấy danh sách món theo đơn
    public List<FoodOrderDetail> getOrderDetailsByOrderId(int orderId) throws SQLException {
        List<FoodOrderDetail> list = new ArrayList<>();
        String sql = "SELECT * FROM FoodOrderDetail WHERE Food_Order_Id = ?";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setInt(1, orderId);
        ResultSet rs = ps.executeQuery();

        FoodDAO foodDAO = new FoodDAO(); // Giả sử bạn đã có DAO này

        while (rs.next()) {
            int foodId = rs.getInt("Food_Id");
            Food food = foodDAO.getFoodById(foodId); // Lấy thông tin món ăn

            FoodOrderDetail detail = new FoodOrderDetail(
                rs.getInt("Food_Order_Detail_Id"),
                orderId,
                food,
                rs.getInt("Quantity"),
                rs.getDouble("Price")
            );
            list.add(detail);
        }

        return list;
    }
}
