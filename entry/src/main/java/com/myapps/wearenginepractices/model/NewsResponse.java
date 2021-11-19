package com.myapps.wearenginepractices.model;

import java.io.Serializable;
import java.util.List;

public class NewsResponse implements Serializable {

    private String status;
    private Integer totalResults;
    private List<Article> articles;

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(final Integer totalResults) {
        this.totalResults = totalResults;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(final List<Article> articles) {
        this.articles = articles;
    }
}
