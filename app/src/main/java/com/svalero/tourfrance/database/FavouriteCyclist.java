package com.svalero.tourfrance.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favourite_cyclists")
public class FavouriteCyclist {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String specialty;
    private String birthplace;
    private int titles;
    private String notes;

    public FavouriteCyclist() {}

    public FavouriteCyclist(String name, String specialty, String birthplace, int titles, String notes) {
        this.name = name;
        this.specialty = specialty;
        this.birthplace = birthplace;
        this.titles = titles;
        this.notes = notes;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSpecialty() { return specialty; }
    public void setSpecialty(String specialty) { this.specialty = specialty; }
    public String getBirthplace() { return birthplace; }
    public void setBirthplace(String birthplace) { this.birthplace = birthplace; }
    public int getTitles() { return titles; }
    public void setTitles(int titles) { this.titles = titles; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
