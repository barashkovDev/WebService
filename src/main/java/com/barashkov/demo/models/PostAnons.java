package com.barashkov.demo.models;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("postAnons")
public class PostAnons extends Post {

    private String anons;

    public PostAnons(){}
    public PostAnons(String title, String fullText, String anons) {
        super(title, fullText);
        this.anons = anons;
    }

    public String getAnons() {
        return anons;
    }

    public void setAnons(String anons) {
        this.anons = anons;
    }

    @Override
    public String toString() {
        return "PostAnons{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", fullText='" + fullText + '\'' +
                ", anons='" + anons + '\'' +
                '}';
    }
}
