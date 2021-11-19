package com.myapps.wearenginepractices.network;

import com.myapps.wearenginepractices.model.NewsResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    //TODO implement your API here
    @GET("v2/top-headlines")
    Call<NewsResponse> queryArticles(@Query("country") String countryCode,
                                     @Query("page") Integer pageNumber,
                                     @Query("apiKey") String apiKey);
}
