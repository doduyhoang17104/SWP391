package dal.Service;

import dal.DBcontext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.Service.ServiceCategory;

public class ServiceCategoryDAO extends DBcontext {

    public ArrayList<ServiceCategory> getAllServiceCategory() {
        ArrayList<ServiceCategory> list = new ArrayList<>();
        String sql = "SELECT * FROM ServiceCategory";
        try {
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ServiceCategory sc = new ServiceCategory();
                sc.setServiceCategoryId(rs.getInt("Service_Category_Id"));
                sc.setServiceCategoryName(rs.getString("Service_Category_Name"));
                list.add(sc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // Thêm danh mục
    public void addServiceCategory(String name) {
        String sql = "INSERT INTO ServiceCategory (Service_Category_Name) VALUES (?)";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    // Sửa danh mục
    public void updateServiceCategory(int id, String name) {
        String sql = "UPDATE ServiceCategory SET Service_Category_Name=? WHERE Service_Category_Id=?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    // Xóa danh mục
    public void deleteServiceCategory(int id) {
        String sql = "DELETE FROM ServiceCategory WHERE Service_Category_Id=?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }
}
