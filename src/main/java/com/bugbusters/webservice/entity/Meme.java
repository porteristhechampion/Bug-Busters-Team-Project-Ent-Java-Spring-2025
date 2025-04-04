package com.bugbusters.webservice.entity;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;

/**
 * The type Meme.
 */
@Entity
@Table(name = "memes")
public class Meme {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native")
    private int id;

    @Column (name = "url", nullable = false)
    private String url;

    /**
     * Instantiates a new Meme.
     */
    public Meme() {}

    /**
     * Instantiates a new Meme.
     *
     * @param url the url
     */
    public Meme(String url) {
        this.url = url;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets url.
     *
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets url.
     *
     * @param url the url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Meme [id=" + id + ", url=" + url + "]";
    }
}
