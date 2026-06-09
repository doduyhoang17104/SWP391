package model;

import java.util.Objects;

public class ServiceCategory {
    private int serviceCategoryId;
    private String categoryName;

    public ServiceCategory() {}

    public ServiceCategory(int serviceCategoryId, String categoryName) {
        this.serviceCategoryId = serviceCategoryId;
        this.categoryName = categoryName;
    }

    public int getServiceCategoryId() {
        return serviceCategoryId;
    }

    public void setServiceCategoryId(int serviceCategoryId) {
        this.serviceCategoryId = serviceCategoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ServiceCategory)) return false;
        ServiceCategory that = (ServiceCategory) o;
        return serviceCategoryId == that.serviceCategoryId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(serviceCategoryId);
    }

    @Override
    public String toString() {
        return categoryName;
    }
}
