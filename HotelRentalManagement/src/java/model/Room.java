    package model;

    import java.sql.Timestamp;
    import java.util.List;

    public class Room {
        private int roomId;
        private int roomNumber;
        private RoomType roomType;
        private boolean booked;
        private String capacity;
        private String size;
        private int bed;
        private Timestamp createdAt;
        private String status;
        private Timestamp updatedAt;
        
        private List<String> imageUrls; 
        private List<RoomAmenity> amenities;
        private Integer daysLeft;
        private boolean bookedInRange;
        private Timestamp bookingCheckinDate;
        private Timestamp bookingCheckoutDate;
        public Room() {
        }

    public Room(int roomId, int roomNumber, RoomType roomType, boolean booked, String capacity, String size, int bed, Timestamp createdAt, String status, Timestamp updatedAt, List<String> imageUrls, List<RoomAmenity> amenities, Integer daysLeft, boolean bookedInRange) {
        this.roomId = roomId;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.booked = booked;
        this.capacity = capacity;
        this.size = size;
        this.bed = bed;
        this.createdAt = createdAt;
        this.status = status;
        this.updatedAt = updatedAt;
        this.imageUrls = imageUrls;
        this.amenities = amenities;
        this.daysLeft = daysLeft;
        this.bookedInRange = bookedInRange;
    }

    public Room(int roomId, int roomNumber, RoomType roomType, boolean booked, String capacity, String size, int bed, Timestamp createdAt, String status, Timestamp updatedAt, List<String> imageUrls, List<RoomAmenity> amenities, Integer daysLeft, boolean bookedInRange, Timestamp bookingCheckinDate, Timestamp bookingCheckoutDate) {
        this.roomId = roomId;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.booked = booked;
        this.capacity = capacity;
        this.size = size;
        this.bed = bed;
        this.createdAt = createdAt;
        this.status = status;
        this.updatedAt = updatedAt;
        this.imageUrls = imageUrls;
        this.amenities = amenities;
        this.daysLeft = daysLeft;
        this.bookedInRange = bookedInRange;
        this.bookingCheckinDate = bookingCheckinDate;
        this.bookingCheckoutDate = bookingCheckoutDate;
    }

    

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getBed() {
        return bed;
    }

    public void setBed(int bed) {
        this.bed = bed;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public List<RoomAmenity> getAmenities() {
        return amenities;
    }

    public void setAmenities(List<RoomAmenity> amenities) {
        this.amenities = amenities;
    }

    public Integer getDaysLeft() {
        return daysLeft;
    }

    public void setDaysLeft(Integer daysLeft) {
        this.daysLeft = daysLeft;
    }

    public boolean isBookedInRange() {
        return bookedInRange;
    }

    public void setBookedInRange(boolean bookedInRange) {
        this.bookedInRange = bookedInRange;
    }

    public Timestamp getBookingCheckinDate() {
        return bookingCheckinDate;
    }

    public void setBookingCheckinDate(Timestamp bookingCheckinDate) {
        this.bookingCheckinDate = bookingCheckinDate;
    }

    public Timestamp getBookingCheckoutDate() {
        return bookingCheckoutDate;
    }

    public void setBookingCheckoutDate(Timestamp bookingCheckoutDate) {
        this.bookingCheckoutDate = bookingCheckoutDate;
    }

    @Override
    public String toString() {
        return "Room{" + "roomId=" + roomId + ", roomNumber=" + roomNumber + ", roomType=" + roomType + ", booked=" + booked + ", capacity=" + capacity + ", size=" + size + ", bed=" + bed + ", createdAt=" + createdAt + ", status=" + status + ", updatedAt=" + updatedAt + ", imageUrls=" + imageUrls + ", amenities=" + amenities + ", daysLeft=" + daysLeft + ", bookedInRange=" + bookedInRange + ", bookingCheckinDate=" + bookingCheckinDate + ", bookingCheckoutDate=" + bookingCheckoutDate + '}';
    }

  

   

    
        }
