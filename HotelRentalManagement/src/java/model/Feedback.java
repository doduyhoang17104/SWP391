package model;

import java.sql.Timestamp;

public class Feedback {
    private int feedbackId;
    private int overallRating;
    private int serviceRating;
    private String comment;
    private String rentalPeriod;
    private Timestamp createdAt;
    private int userId;
    private String userFullName;

    // Getters and Setters
    public int getFeedbackId() { return feedbackId; }
    public void setFeedbackId(int feedbackId) { this.feedbackId = feedbackId; }

    public int getOverallRating() { return overallRating; }
    public void setOverallRating(int overallRating) { this.overallRating = overallRating; }

    public int getServiceRating() { return serviceRating; }
    public void setServiceRating(int serviceRating) { this.serviceRating = serviceRating; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public String getRentalPeriod() { return rentalPeriod; }
    public void setRentalPeriod(String rentalPeriod) { this.rentalPeriod = rentalPeriod; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getUserFullName() { return userFullName; }
    public void setUserFullName(String userFullName) { this.userFullName = userFullName; }
}
