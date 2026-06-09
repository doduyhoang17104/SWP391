/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author admin
 */
public class RoomAmenity {
    private int roomAmenityId;
    private RoomType roomType;  // Tham chiếu RoomType
    private String amenityName;

    public RoomAmenity() {}

    public RoomAmenity(int roomAmenityId, RoomType roomType, String amenityName) {
        this.roomAmenityId = roomAmenityId;
        this.roomType = roomType;
        this.amenityName = amenityName;
    }

    public int getRoomAmenityId() { return roomAmenityId; }
    public void setRoomAmenityId(int roomAmenityId) { this.roomAmenityId = roomAmenityId; }

    public RoomType getRoomType() { return roomType; }
    public void setRoomType(RoomType roomType) { this.roomType = roomType; }

    public String getAmenityName() { return amenityName; }
    public void setAmenityName(String amenityName) { this.amenityName = amenityName; }
}

