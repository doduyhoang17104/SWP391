package controller.manager;

import dal.FoodDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Food;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EditFoodServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");
        if (idParam != null) {
            try {
                int id = Integer.parseInt(idParam);
                FoodDAO dao = new FoodDAO();
                Food food = dao.getFoodById(id);

                if (food != null) {
                    request.setAttribute("food", food);
                    request.getRequestDispatcher("views/dashboard/editfood.jsp").forward(request, response);
                } else {
                    response.sendRedirect("managerfood?error=notfound");
                }

            } catch (NumberFormatException e) {
                response.sendRedirect("managerfood?error=invalidid");
            } catch (SQLException ex) {
                Logger.getLogger(EditFoodServlet.class.getName()).log(Level.SEVERE, null, ex);
                response.sendRedirect("managerfood?error=servererror");
            }
        } else {
            response.sendRedirect("managerfood?error=missingid");
        }
    }

    // 👉 Xử lý cập nhật món ăn sau khi người dùng submit form
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            double price = Double.parseDouble(request.getParameter("price"));
            String category = request.getParameter("category");
            boolean availability = Boolean.parseBoolean(request.getParameter("availability"));
            String image = request.getParameter("image");

            // Tạo đối tượng Food với dữ liệu đã chỉnh sửa
            Food food = new Food();
            food.setId(id);
            food.setName(name);
            food.setDescription(description);
            food.setPrice(price);
            food.setCategory(category);
            food.setAvailability(availability);
            food.setImage(image);

            // Gọi DAO để cập nhật dữ liệu
            FoodDAO dao = new FoodDAO();
            dao.updateFood(food);

            // ✅ Chuyển hướng về trang quản lý sau khi cập nhật
            response.sendRedirect("managerfood");

        } catch (Exception e) {
            e.printStackTrace();
            // Nếu lỗi, quay lại form sửa với thông báo lỗi
            response.sendRedirect("editfood?id=" + request.getParameter("id") + "&error=updatefail");
        }
    }
}
