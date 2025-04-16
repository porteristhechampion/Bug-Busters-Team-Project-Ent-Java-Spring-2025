package com.bugbusters.webservice.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

/**
 * The type Meme.
 */
@Entity
@Table(name = "memes")
public class Meme {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int id;
    @Column(name = "url", nullable = false)
    private String url;
    @Column(name = "text_top")
    private String textTop;
    @Column(name = "text_bottom")
    private String textBottom;
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public Meme() {
    }

    public Meme(String url, String textTop, String textBottom) {
        this.url = url;
        this.textTop = textTop;
        this.textBottom = textBottom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTextTop() {
        return textTop;
    }

    public void setTextTop(String textTop) {
        this.textTop = textTop;
    }

    public String getTextBottom() {
        return textBottom;
    }

    public void setTextBottom(String textBottom) {
        this.textBottom = textBottom;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "Meme{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", textTop='" + textTop + '\'' +
                ", textBottom='" + textBottom + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}