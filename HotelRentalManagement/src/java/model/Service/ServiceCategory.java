/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author phamv
 */
public class ServiceCategory {

    private int serviceCategoryId;
    private String serviceCategoryName;
    private List<Service> services = new ArrayList<>();

    public ServiceCategory(int serviceCategoryId, String serviceCategoryName) {
        this.serviceCategoryId = serviceCategoryId;
        this.serviceCategoryName = serviceCategoryName;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public void setServiceCategoryId(int serviceCategoryId) {
        this.serviceCategoryId = serviceCategoryId;
    }

    public void setServiceCategoryName(String serviceCategoryName) {
        this.serviceCategoryName = serviceCategoryName;
    }

    public ServiceCategory() {
    }

    public int getServiceCategoryId() {
        return serviceCategoryId;
    }

    public String getServiceCategoryName() {
        return serviceCategoryName;
    }

}
