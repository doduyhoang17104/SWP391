/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author admin
 */
import java.util.Date;

public class RoomImage {
    private int imageId;
    private Room room;  // Tham chiếu Room
    private String imageUrl;
    private Date uploadAt;

    public RoomImage() {}

    public RoomImage(int imageId, Room room, String imageUrl, Date uploadAt) {
        this.imageId = imageId;
        this.room = room;
        this.imageUrl = imageUrl;
        this.uploadAt = uploadAt;
    }

    public int getImageId() { return imageId; }
    public void setImageId(int imageId) { this.imageId = imageId; }

    public Room getRoom() { return room; }
    public void setRoom(Room room) { this.room = room; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public Date getUploadAt() { return uploadAt; }
    public void setUploadAt(Date uploadAt) { this.uploadAt = uploadAt; }
}

