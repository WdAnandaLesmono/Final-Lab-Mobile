package com.example.project.model;

import com.google.gson.annotations.SerializedName;

public class Genres {
    @SerializedName("name")
    private final String genre;

    public Genres(String genre) {
        this.genre = genre;
    }

    public String getGenre() {
        return genre;
    }
}
