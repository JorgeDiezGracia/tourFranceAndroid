package com.svalero.tourfrance.model;

public class Team {

    private long id;
    private String name;
    private String country;
    private String email;
    private float budget;
    private String fundationDate;

    public Team() {}

    public Team(String name, String country, String email, float budget, String fundationDate) {
        this.name = name;
        this.country = country;
        this.email = email;
        this.budget = budget;
        this.fundationDate = fundationDate;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public float getBudget() { return budget; }
    public void setBudget(float budget) { this.budget = budget; }

    public String getFundationDate() { return fundationDate; }
    public void setFundationDate(String fundationDate) { this.fundationDate = fundationDate; }

    @Override
    public String toString() { return name + " (" + country + ")"; }
}