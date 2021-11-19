package com.myapps.wearenginepractices.network;

import com.myapps.wearenginepractices.model.Article;
import com.myapps.wearenginepractices.model.NewsResponse;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

public class ApiInteractorImpl {

    private static final String API_KEY = "API KEY";

    private final ApiService apiService;

    public ApiInteractorImpl(final ApiService apiService) {
        this.apiService = apiService;
    }

    public List<Article> loadArticles() throws Exception {
        final Response<NewsResponse> response = apiService.queryArticles("us", 1, API_KEY).execute();

        if (response.isSuccessful()) {
            final NewsResponse body = response.body();
            assert body != null;
            return body.getArticles();
        } else {
            throw new IOException(String.format("Failed to translate Error code %d Error Message %s",
                    response.code(), response.message()));
        }
    }
}
