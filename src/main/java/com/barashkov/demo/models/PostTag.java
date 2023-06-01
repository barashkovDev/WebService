package com.barashkov.demo.models;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("postTag")
public class PostTag extends Post {

    private String tag;

    public PostTag(){}

    public PostTag(String title, String fullText, String tag) {
        super(title, fullText);
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "PostTag{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", fullText='" + fullText + '\'' +
                ", tag='" + tag + '\'' +
                '}';
    }
}
