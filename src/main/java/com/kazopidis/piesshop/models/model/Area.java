package com.kazopidis.piesshop.models.model;

import java.io.Serializable;

public class Area implements Serializable {
    private int id;
    private String description;

    public Area() {
    }

    public Area(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Area{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
