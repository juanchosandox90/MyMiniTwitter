package app.sandoval.com.myminitwitter.data.repository;

import androidx.lifecycle.MutableLiveData;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import app.sandoval.com.myminitwitter.common.MyApp;
import app.sandoval.com.myminitwitter.data.Request.RequestCreateTweet;
import app.sandoval.com.myminitwitter.data.Response.Tweet;
import app.sandoval.com.myminitwitter.service.tweets.AuthTwitterClient;
import app.sandoval.com.myminitwitter.service.tweets.AuthTwitterService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TweetRepository {

    private AuthTwitterService authTwitterService;
    private AuthTwitterClient authTwitterClient;
    private MutableLiveData<List<Tweet>> allTweets;

    public TweetRepository() {
        authTwitterClient = AuthTwitterClient.getInstance();
        authTwitterService = AuthTwitterClient.getAuthTwitterService();
        allTweets = getAllTweets();
    }

    public MutableLiveData<List<Tweet>> getAllTweets() {
        if (allTweets == null) {
            allTweets = new MutableLiveData<>();
        }
        Call<List<Tweet>> call = authTwitterService.getAllTweets();
        call.enqueue(new Callback<List<Tweet>>() {
            @Override
            public void onResponse(Call<List<Tweet>> call, Response<List<Tweet>> response) {
                if (response.isSuccessful()) {
                    allTweets.setValue(response.body());
                } else {
                    Toast.makeText(MyApp.getContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Tweet>> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), "Connection Error!", Toast.LENGTH_LONG).show();
            }
        });

        return allTweets;
    }

    public void createTweet(String tweetMessage) {
        RequestCreateTweet requestCreateTweet = new RequestCreateTweet(tweetMessage);
        Call<Tweet> call = authTwitterService.createTweet(requestCreateTweet);

        call.enqueue(new Callback<Tweet>() {
            @Override
            public void onResponse(Call<Tweet> call, Response<Tweet> response) {
                if (response.isSuccessful()) {
                    List<Tweet> clonedList = new ArrayList<>();
                    clonedList.add(response.body());
                    for (int i=0; i<allTweets.getValue().size(); i++){
                        clonedList.add(new Tweet(allTweets.getValue().get(i)));
                    }

                    allTweets.setValue(clonedList);
                } else {
                    Toast.makeText(MyApp.getContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Tweet> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), "Connection Error!", Toast.LENGTH_LONG).show();
            }
        });
    }
}
