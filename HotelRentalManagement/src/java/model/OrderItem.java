package model;

public class OrderItem {
    private Food food;        // Món ăn
    private String status;    // Trạng thái: "Đang chờ xử lý", "Đang làm", "Đã xong"

    public OrderItem() {
    }

    public OrderItem(Food food, String status) {
        this.food = food;
        this.status = status;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
