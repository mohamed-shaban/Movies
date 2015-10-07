package com.example.mohamed.movies;

/**
 * Created by mohamed on 10/7/2015.
 */
public class Review {
    private String author;
    private String content;
    private String url;

    public Review() {
        super();
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
