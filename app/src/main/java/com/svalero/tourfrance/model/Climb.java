package com.svalero.tourfrance.model;

public class Climb {

    private long id;
    private String name;
    private String category;
    private String region;
    private int altitude;
    private float slope;
    private boolean goal;
    private String lastAscent;
    private float longitude;
    private float latitude;
    private long stageId;

    public Climb() {}

    public Climb(String name, String category, String region, int altitude,
                 float slope, boolean goal, String lastAscent, float longitude,
                 float latitude, long stageId) {
        this.name = name;
        this.category = category;
        this.region = region;
        this.altitude = altitude;
        this.slope = slope;
        this.goal = goal;
        this.lastAscent = lastAscent;
        this.longitude = longitude;
        this.latitude = latitude;
        this.stageId = stageId;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public int getAltitude() { return altitude; }
    public void setAltitude(int altitude) { this.altitude = altitude; }

    public float getSlope() { return slope; }
    public void setSlope(float slope) { this.slope = slope; }

    public boolean isGoal() { return goal; }
    public void setGoal(boolean goal) { this.goal = goal; }

    public String getLastAscent() { return lastAscent; }
    public void setLastAscent(String lastAscent) { this.lastAscent = lastAscent; }

    public float getLongitude() { return longitude; }
    public void setLongitude(float longitude) { this.longitude = longitude; }

    public float getLatitude() { return latitude; }
    public void setLatitude(float latitude) { this.latitude = latitude; }

    public long getStageId() { return stageId; }
    public void setStageId(long stageId) { this.stageId = stageId; }

    @Override
    public String toString() { return name + " (" + category + ")"; }
}