package app.sandoval.com.myminitwitter.service.tweets;


import java.util.List;

import app.sandoval.com.myminitwitter.data.Request.RequestCreateTweet;
import app.sandoval.com.myminitwitter.data.Response.Tweet;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AuthTwitterService {

    @GET("tweets/all")
    Call<List<Tweet>> getAllTweets();

    @POST("tweets/create")
    Call<Tweet> createTweet(@Body RequestCreateTweet requestCreateTweet);

}
