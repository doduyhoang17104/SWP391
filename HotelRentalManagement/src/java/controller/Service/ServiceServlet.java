/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.Service;

import dal.Service.ServiceCategoryDAO;
import dal.Service.ServiceDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import model.Service.Service;
import model.Service.ServiceCategory;

/**
 *
 * @author phamv
 */
public class ServiceServlet extends HttpServlet {

    ServiceDAO serviceDAO = new ServiceDAO();
    ServiceCategoryDAO serviceCategoryDAO = new ServiceCategoryDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ArrayList<Service> serviceList = serviceDAO.getAllService();
        ArrayList<ServiceCategory> serviceCategoryList = serviceCategoryDAO.getAllServiceCategory();
        int total = serviceCategoryList.size();
        int part = total / 3;
        int rem = total % 3;

        List<ServiceCategory> left = serviceCategoryList.subList(0, part + (rem > 0 ? 1 : 0));
        List<ServiceCategory> center = serviceCategoryList.subList(left.size(), left.size() + part + (rem > 1 ? 1 : 0));
        List<ServiceCategory> right = serviceCategoryList.subList(center.size() + left.size(), total);

        request.setAttribute("leftList", left);
        request.setAttribute("centerList", center);
        request.setAttribute("rightList", right);

        request.setAttribute("serviceList", serviceList);
        request.setAttribute("serviceCategoryList", serviceCategoryList);
        request.getRequestDispatcher("about.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

}
