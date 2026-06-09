package model;

public class FoodOrderDetail {
    private int id;
    private int foodOrderId;
    private Food food;
    private int quantity;
    private double price;

    // Getter, setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFoodOrderId() {
        return foodOrderId;
    }

    public void setFoodOrderId(int foodOrderId) {
        this.foodOrderId = foodOrderId;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // Constructor dùng khi insert
    public FoodOrderDetail(int foodOrderId, Food food, int quantity, double price) {
        this.foodOrderId = foodOrderId;
        this.food = food;
        this.quantity = quantity;
        this.price = price;
    }

    // Constructor đầy đủ dùng khi truy xuất có id
    public FoodOrderDetail(int id, int foodOrderId, Food food, int quantity, double price) {
        this.id = id;
        this.foodOrderId = foodOrderId;
        this.food = food;
        this.quantity = quantity;
        this.price = price;
    }
}
