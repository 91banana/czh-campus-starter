package com.campus.dto;

public class EmptyRoomVO {
    private Long id;
    private String building;
    private String roomNo;
    private Integer capacity;
    private Integer hasProjector;
    private Integer hasAc;
    private String type;

    public EmptyRoomVO() {}

    public EmptyRoomVO(Long id, String building, String roomNo, Integer capacity, Integer hasProjector, Integer hasAc, String type) {
        this.id = id;
        this.building = building;
        this.roomNo = roomNo;
        this.capacity = capacity;
        this.hasProjector = hasProjector;
        this.hasAc = hasAc;
        this.type = type;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getBuilding() { return building; }
    public void setBuilding(String building) { this.building = building; }
    public String getRoomNo() { return roomNo; }
    public void setRoomNo(String roomNo) { this.roomNo = roomNo; }
    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }
    public Integer getHasProjector() { return hasProjector; }
    public void setHasProjector(Integer hasProjector) { this.hasProjector = hasProjector; }
    public Integer getHasAc() { return hasAc; }
    public void setHasAc(Integer hasAc) { this.hasAc = hasAc; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}
