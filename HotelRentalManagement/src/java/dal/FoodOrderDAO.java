package dal;

import model.FoodOrder;
import model.FoodOrderDetail;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FoodOrderDAO extends DBcontext {

    // 1. Thêm đơn hàng + chi tiết
    public int insertFoodOrder(FoodOrder order) throws SQLException {
        String sql = "INSERT INTO FoodOrder (Booking_Id, Order_Time, Status, Note) VALUES (?, ?, ?, ?)";
        try {
            c.setAutoCommit(false); // 🔒 Bắt đầu transaction

            PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, order.getBookingId());
            ps.setTimestamp(2, new Timestamp(order.getOrderTime().getTime()));
            ps.setString(3, order.getStatus());
            ps.setString(4, order.getNote());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int orderId = rs.getInt(1);

                    // Thêm chi tiết món ăn
                    FoodOrderDetailDAO detailDAO = new FoodOrderDetailDAO(c); // truyền cùng Connection
                    for (FoodOrderDetail detail : order.getDetails()) {
                        detail.setFoodOrderId(orderId);
                        detailDAO.insertFoodOrderDetail(detail);
                    }

                    c.commit(); // ✅ Commit nếu không lỗi
                    return orderId;
                }
            }
            c.rollback(); // ❌ Nếu không có row nào được thêm
        } catch (SQLException e) {
            e.printStackTrace();
            c.rollback(); // ❌ Có lỗi thì rollback
            throw e;
        } finally {
            c.setAutoCommit(true); // ✅ Trả lại trạng thái ban đầu
        }

        return -1;
    }

    // 2. Lấy đơn theo ID
    public FoodOrder getFoodOrderById(int orderId) throws SQLException {
        String sql = "SELECT * FROM FoodOrder WHERE Food_Order_Id = ?";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setInt(1, orderId);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            FoodOrder order = new FoodOrder();
            order.setId(orderId);
            order.setBookingId(rs.getInt("Booking_Id"));
            order.setOrderTime(rs.getTimestamp("Order_Time"));
            order.setStatus(rs.getString("Status"));
            order.setNote(rs.getString("Note"));

            FoodOrderDetailDAO detailDAO = new FoodOrderDetailDAO(c); // ✅ truyền connection
            order.setDetails(detailDAO.getOrderDetailsByOrderId(orderId));

            return order;
        }
        return null;
    }

    // 3. Lấy tất cả đơn
    public List<FoodOrder> getAllOrders() throws SQLException {
        List<FoodOrder> list = new ArrayList<>();
        String sql = "SELECT * FROM FoodOrder ORDER BY Food_Order_Id DESC";
        PreparedStatement ps = c.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        FoodOrderDetailDAO detailDAO = new FoodOrderDetailDAO(c); // ✅ truyền connection

        while (rs.next()) {
            int orderId = rs.getInt("Food_Order_Id");

            FoodOrder order = new FoodOrder();
            order.setId(orderId);
            order.setBookingId(rs.getInt("Booking_Id"));
            order.setOrderTime(rs.getTimestamp("Order_Time"));
            order.setStatus(rs.getString("Status"));
            order.setNote(rs.getString("Note"));

            order.setDetails(detailDAO.getOrderDetailsByOrderId(orderId));
            list.add(order);
        }

        return list;
    }

    // 4. Lấy đơn theo bookingId
    public List<FoodOrder> getOrdersByBookingId(int bookingId) throws SQLException {
        List<FoodOrder> list = new ArrayList<>();
        String sql = "SELECT * FROM FoodOrder WHERE Booking_Id = ? ORDER BY Food_Order_Id DESC";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setInt(1, bookingId);
        ResultSet rs = ps.executeQuery();

        FoodOrderDetailDAO detailDAO = new FoodOrderDetailDAO(c); // ✅ truyền connection

        while (rs.next()) {
            int orderId = rs.getInt("Food_Order_Id");

            FoodOrder order = new FoodOrder();
            order.setId(orderId);
            order.setBookingId(bookingId);
            order.setOrderTime(rs.getTimestamp("Order_Time"));
            order.setStatus(rs.getString("Status"));
            order.setNote(rs.getString("Note"));

            order.setDetails(detailDAO.getOrderDetailsByOrderId(orderId));
            list.add(order);
        }

        return list;
    }

    // 5. Cập nhật trạng thái đơn
    public void updateOrderStatus(int orderId, String status) throws SQLException {
        String sql = "UPDATE FoodOrder SET Status = ? WHERE Food_Order_Id = ?";
        DBcontext db = new DBcontext();
        try (Connection conn = db.c; PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, orderId);
            stmt.executeUpdate();
        }
    }

}
