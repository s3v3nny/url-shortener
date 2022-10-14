package ru.s3v3nny.urlshortener.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "links")
public class LinkModel {
    @Id
    private String id;

    @Column(name = "link")
    private String link;

    @Column(name = "date")
    private Timestamp date;

    public LinkModel() {
    }

    public LinkModel(String id, String link, Timestamp date) {
        this.id = id;
        this.link = link;
        this.date = date;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
}
