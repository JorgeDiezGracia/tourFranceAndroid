package com.svalero.tourfrance.model;

import com.google.gson.annotations.SerializedName;

public class Cyclist {

    private long id;
    private String name;
    private String specialty;
    private String birthplace;
    private int titles;
    private String birthdate;  // String porque Gson no deserializa LocalDate directamente

    @SerializedName("teamId")
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

    public String getBirthdate() { return birthdate; }
    public void setBirthdate(String birthdate) { this.birthdate = birthdate; }

    public long getTeamId() { return teamId; }
    public void setTeamId(long teamId) { this.teamId = teamId; }

    @Override
    public String toString() {
        return name + " (" + specialty + ")";
    }
}
