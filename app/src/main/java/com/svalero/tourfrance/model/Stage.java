package com.svalero.tourfrance.model;

import com.google.gson.annotations.Expose;

public class Stage {

    @Expose(serialize = false, deserialize = true)
    private long id;
    @Expose
    private String departure;
    @Expose
    private String arrival;
    @Expose
    private String type;
    @Expose
    private int elevation;
    @Expose
    private float kilometers;
    @Expose
    private boolean mountainStage;
    @Expose
    private String stageDate;

    public Stage() {}

    public Stage(String departure, String arrival, String type,
                 int elevation, float kilometers, boolean mountainStage, String stageDate) {
        this.departure = departure;
        this.arrival = arrival;
        this.type = type;
        this.elevation = elevation;
        this.kilometers = kilometers;
        this.mountainStage = mountainStage;
        this.stageDate = stageDate;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getDeparture() { return departure; }
    public void setDeparture(String departure) { this.departure = departure; }
    public String getArrival() { return arrival; }
    public void setArrival(String arrival) { this.arrival = arrival; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public int getElevation() { return elevation; }
    public void setElevation(int elevation) { this.elevation = elevation; }
    public float getKilometers() { return kilometers; }
    public void setKilometers(float kilometers) { this.kilometers = kilometers; }
    public boolean isMountainStage() { return mountainStage; }
    public void setMountainStage(boolean mountainStage) { this.mountainStage = mountainStage; }
    public String getStageDate() { return stageDate; }
    public void setStageDate(String stageDate) { this.stageDate = stageDate; }

    @Override
    public String toString() { return departure + " → " + arrival + " (" + type + ")"; }
}