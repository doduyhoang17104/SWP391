/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */

public class RoomType {
    private int roomTypeId;      
    private String typeName;   
    private String description;  
    private double basePrice;    
     private List<Room> rooms = new ArrayList<>();
     private List<String> amenities;
    public RoomType() {}

    public RoomType(int roomTypeId, String typeName, String description, double basePrice) {
        this.roomTypeId = roomTypeId;
        this.typeName = typeName;
        this.description = description;
        this.basePrice = basePrice;
    }

    public RoomType(int roomTypeId, String typeName, String description, double basePrice, List<String> amenities) {
        this.roomTypeId = roomTypeId;
        this.typeName = typeName;
        this.description = description;
        this.basePrice = basePrice;
        this.amenities = amenities;
    }

    public int getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(int roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public List<String> getAmenities() {
        return amenities;
    }

    public void setAmenities(List<String> amenities) {
        this.amenities = amenities;
    }

    @Override
    public String toString() {
        return "RoomType{" + "roomTypeId=" + roomTypeId + ", typeName=" + typeName + ", description=" + description + ", basePrice=" + basePrice + ", rooms=" + rooms + ", amenities=" + amenities + '}';
    }

    
}


