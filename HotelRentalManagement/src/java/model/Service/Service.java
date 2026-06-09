
package model.Service;


public class Service {
        private int serviceId;
        private String serviceName, description;
        private int price, serviceCategoryId;

    public Service(int serviceId, String serviceName, String description, int price, int serviceCategoryId) {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.description = description;
        this.price = price;
        this.serviceCategoryId = serviceCategoryId;
    }

    public Service() {
    }

    public int getServiceId() {
        return serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public int getServiceCategoryId() {
        return serviceCategoryId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setServiceCategoryId(int serviceCategoryId) {
        this.serviceCategoryId = serviceCategoryId;
    }
        
        
}
