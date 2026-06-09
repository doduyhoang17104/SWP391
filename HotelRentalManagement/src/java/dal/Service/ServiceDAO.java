/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal.Service;

import dal.DBcontext;
import dal.UserDAO;
import java.util.ArrayList;
import model.Service.Service;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import model.Service.ServiceCategory;
import model.User;

/**
 *
 * @author phamv
 */
public class ServiceDAO extends DBcontext {

    public ArrayList<Service> getAllService() {
        ArrayList<Service> serviceList = new ArrayList<>();
        String sql = "SELECT * FROM Services";
        try {
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Service s = new Service();
                s.setServiceId(rs.getInt("Service_Id"));
                s.setServiceName(rs.getString("Service_Name"));
                s.setDescription(rs.getString("Description"));
                s.setPrice(rs.getInt("Price"));
                s.setServiceCategoryId(rs.getInt("Service_Category_Id"));
                serviceList.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serviceList;
    }

    public void addService(Service service) {
        String sql = "INSERT INTO Services (Service_Name, Description, Price, Service_Category_Id) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, service.getServiceName());
            ps.setString(2, service.getDescription());
            ps.setInt(3, service.getPrice());
            ps.setInt(4, service.getServiceCategoryId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<ServiceCategory> getAllServicesGroupedByCategory() {
        Map<Integer, ServiceCategory> categoryMap = new LinkedHashMap<>();

        String sql = "SELECT c.Service_Category_Id, c.Service_Category_Name, "
                + "s.Service_Id, s.Service_Name, s.Description, s.Price "
                + "FROM ServiceCategory c "
                + "LEFT JOIN Services s ON c.Service_Category_Id = s.Service_Category_Id "
                + "ORDER BY c.Service_Category_Id";

        try {
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int categoryId = rs.getInt("Service_Category_Id");
                ServiceCategory category = categoryMap.get(categoryId);

                if (category == null) {
                    category = new ServiceCategory();
                    category.setServiceCategoryId(categoryId);
                    category.setServiceCategoryName(rs.getString("Service_Category_Name"));
                    categoryMap.put(categoryId, category);
                }

                int serviceId = rs.getInt("Service_Id");
                if (!rs.wasNull()) { // Có dịch vụ gắn với category
                    Service s = new Service();
                    s.setServiceId(serviceId);
                    s.setServiceName(rs.getString("Service_Name"));
                    s.setDescription(rs.getString("Description"));
                    s.setPrice(rs.getInt("Price"));
                    s.setServiceCategoryId(categoryId);
                    category.getServices().add(s);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>(categoryMap.values());
    }

    public static void main(String[] args) {
        UserDAO u = new UserDAO();
//        User user = u.getUserByUsernamePassword("ma", "123");
//        System.out.println(user.toString());

        User user2 = u.getUserByUsername("ma");
        System.out.println(user2.toString());

    }

    // Thêm dịch vụ
    public void addService(String name, int price, int categoryId) {
        String sql = "INSERT INTO Services (Service_Name, Price, Service_Category_Id) VALUES (?, ?, ?)";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setInt(2, price);
            ps.setInt(3, categoryId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Sửa dịch vụ
    public void updateService(int id, String name, int price) {
        String sql = "UPDATE Services SET Service_Name=?, Price=? WHERE Service_Id=?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setInt(2, price);
            ps.setInt(3, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Xóa dịch vụ
    public void deleteService(int id) {
        String sql = "DELETE FROM Services WHERE Service_Id=?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
