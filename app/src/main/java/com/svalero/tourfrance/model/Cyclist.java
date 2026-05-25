package com.svalero.tourfrance.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cyclist {

    @Expose(serialize = false, deserialize = true)
    private long id;

    @Expose
    private String name;
    @Expose
    private String specialty;
    @Expose
    private String birthplace;
    @Expose
    private int titles;
    @Expose
    private float weight;
    @Expose
    private String birthdate;
    @Expose
    @SerializedName("leader")
    private boolean isLeader;
    @Expose
    private long teamId;

    public Cyclist() {}

    public Cyclist(String name, String specialty, String birthplace,
                   int titles, String birthdate, long teamId) {
        this.name = name;
        this.specialty = specialty;
        this.birthplace = birthplace;
        this.titles = titles;
        this.birthdate = birthdate;
        this.teamId = teamId;
        this.weight = 0;
        this.isLeader = false;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSpecialty() { return specialty; }
    public void setSpecialty(String specialty) { this.specialty = specialty; }
    public String getBirthplace() { return birthplace; }
    public void setBirthplace(String birthplace) { this.birthplace = birthplace; }
    public int getTitles() { return titles; }
    public void setTitles(int titles) { this.titles = titles; }
    public float getWeight() { return weight; }
    public void setWeight(float weight) { this.weight = weight; }
    public String getBirthdate() { return birthdate; }
    public void setBirthdate(String birthdate) { this.birthdate = birthdate; }
    public boolean isLeader() { return isLeader; }
    public void setLeader(boolean leader) { isLeader = leader; }
    public long getTeamId() { return teamId; }
    public void setTeamId(long teamId) { this.teamId = teamId; }

    @Override
    public String toString() { return name + " (" + specialty + ")"; }
}