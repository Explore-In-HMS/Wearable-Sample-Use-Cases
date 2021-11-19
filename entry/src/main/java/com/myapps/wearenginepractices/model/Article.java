package com.myapps.wearenginepractices.model;

import java.io.Serializable;

public class Article implements Serializable {

    private String author;
    private String content;
    private String description;
    private String publishedAt;
    private Source source;
    private String title;
    private String url;
    private String urlToImage;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(final String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(final String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(final Source source) {
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(final String urlToImage) {
        this.urlToImage = urlToImage;
    }
}
