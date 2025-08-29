package com.java.springboot.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "wikimedia_recentchange")
@Getter
@Setter
public class WikimediaData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long data;

    @Lob
    private String wikimediaData;

    public Long getData() {
        return data;
    }

    public void setData(Long data) {
        this.data = data;
    }

    public String getWikimediaData() {
        return wikimediaData;
    }

    public void setWikimediaData(String wikimediaData) {
        this.wikimediaData = wikimediaData;
    }
}
