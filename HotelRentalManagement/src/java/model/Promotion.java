package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Promotion implements Serializable {
    private int promotionId;
    private String promotionName;
    private BigDecimal discountPercent;
    private Date startDate;
    private Date endDate;
    private int status;
    private String description;
    private int quantity;

    public Promotion() {}

    public Promotion(int promotionId, String promotionName, BigDecimal discountPercent, Date startDate, Date endDate, int status, String description, int quantity) {
        this.promotionId = promotionId;
        this.promotionName = promotionName;
        this.discountPercent = discountPercent;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.description = description;
        this.quantity = quantity;
    }

    public int getPromotionId() { return promotionId; }
    public void setPromotionId(int promotionId) { this.promotionId = promotionId; }
    public String getPromotionName() { return promotionName; }
    public void setPromotionName(String promotionName) { this.promotionName = promotionName; }
    public BigDecimal getDiscountPercent() { return discountPercent; }
    public void setDiscountPercent(BigDecimal discountPercent) { this.discountPercent = discountPercent; }
    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }
    public Date getEndDate() { return endDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }
    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
} 