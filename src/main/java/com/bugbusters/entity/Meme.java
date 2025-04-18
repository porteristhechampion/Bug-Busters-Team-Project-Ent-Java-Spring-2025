package com.bugbusters.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

/**
 * A class to represent a meme in the system.
 * This class maps to the "memes" table in the database.
 *
 * @author Jared Doderer
 * @author ptaylor
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

    /**
     * Instantiates a new Meme.
     */
    public Meme() {
    }

    /**
     * Instantiates a new Meme and
     * initializes the fields.
     *
     * @param url        the url
     * @param textTop    the text top
     * @param textBottom the text bottom
     */
    public Meme(String url, String textTop, String textBottom) {
        this.url = url;
        this.textTop = textTop;
        this.textBottom = textBottom;
    }

    /**
     * Gets id.
     * @return the id
     */
    public int getId() {
        return id;
    }
    /**
     * Sets id.
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets url.
     * @return the url
     */
    public String getUrl() {
        return url;
    }
    /**
     * Sets url.
     * @param url the url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Gets text top.
     * @return the text top
     */
    public String getTextTop() {
        return textTop;
    }
    /**
     * Sets text top.
     * @param textTop the text top
     */
    public void setTextTop(String textTop) {
        this.textTop = textTop;
    }

    /**
     * Gets text bottom.
     * @return the text bottom
     */
    public String getTextBottom() {
        return textBottom;
    }
    /**
     * Sets text bottom.
     * @param textBottom the text bottom
     */
    public void setTextBottom(String textBottom) {
        this.textBottom = textBottom;
    }

    /**
     * Gets created at.
     * @return the created at
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets created at.
     * @param createdAt time created
     */
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    /**
     * Converts object data into a string.
     * @return string containing object data
     */
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