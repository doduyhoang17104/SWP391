/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author ddhoang
 */
public class FeedbackRoom {

    private int feedbackId, userId, roomId, rating, bookingId, roomNumber;
    private String content, authorName;
    private Date createAt, checkIn, checkOut,updateAt;

    public FeedbackRoom(int feedbackId, int userId, int roomId, int rating, int bookingId, int roomNumber, String content, String authorName, Date createAt, Date checkIn, Date checkOut, Date updateAt) {
        this.feedbackId = feedbackId;
        this.userId = userId;
        this.roomId = roomId;
        this.rating = rating;
        this.bookingId = bookingId;
        this.roomNumber = roomNumber;
        this.content = content;
        this.authorName = authorName;
        this.createAt = createAt;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.updateAt = updateAt;
    }

    public FeedbackRoom(int feedbackId, int userId, int roomId, int rating, int bookingId, int roomNumber, String content, String authorName, Date createAt, Date checkIn, Date checkOut) {
        this.feedbackId = feedbackId;
        this.userId = userId;
        this.roomId = roomId;
        this.rating = rating;
        this.bookingId = bookingId;
        this.roomNumber = roomNumber;
        this.content = content;
        this.authorName = authorName;
        this.createAt = createAt;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }



    public int getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(int feedbackId) {
        this.feedbackId = feedbackId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Date checkIn) {
        this.checkIn = checkIn;
    }

    public Date getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Date checkOut) {
        this.checkOut = checkOut;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    @Override
    public String toString() {
        return "FeedbackRoom{" + "feedbackId=" + feedbackId + ", userId=" + userId + ", roomId=" + roomId + ", rating=" + rating + ", bookingId=" + bookingId + ", roomNumber=" + roomNumber + ", content=" + content + ", authorName=" + authorName + ", createAt=" + createAt + ", checkIn=" + checkIn + ", checkOut=" + checkOut + '}';
    }

}
