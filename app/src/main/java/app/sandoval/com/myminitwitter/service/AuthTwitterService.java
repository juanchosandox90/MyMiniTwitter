package app.sandoval.com.myminitwitter.service;


import java.util.List;

import app.sandoval.com.myminitwitter.data.Response.Tweet;
import retrofit2.Call;
import retrofit2.http.GET;

public interface AuthTwitterService {

    @GET("tweets/all")
    Call<List<Tweet>> getAllTweets();

}
