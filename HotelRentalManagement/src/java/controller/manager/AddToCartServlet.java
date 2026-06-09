/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.manager;

import dal.FoodDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Food;

/**
 *
 * @author ADMIN
 */
public class AddToCartServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        FoodDAO dao = new FoodDAO();
        HttpSession session = request.getSession();
        List<Food> cart = (List<Food>) session.getAttribute("cart");

        if (cart == null) {
            cart = new ArrayList<>();
        }

        try {
            Food food = dao.getFoodById(id);
            if (food != null) {
                cart.add(food);
                session.setAttribute("cart", cart);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.sendRedirect("listfood");
    }
}