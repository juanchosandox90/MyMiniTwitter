package app.sandoval.com.myminitwitter.data.repository;

import androidx.lifecycle.MutableLiveData;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import app.sandoval.com.myminitwitter.common.MyApp;
import app.sandoval.com.myminitwitter.common.SharedPreferencesManager;
import app.sandoval.com.myminitwitter.data.Request.RequestCreateTweet;
import app.sandoval.com.myminitwitter.data.Response.Like;
import app.sandoval.com.myminitwitter.data.Response.Tweet;
import app.sandoval.com.myminitwitter.service.tweets.AuthTwitterClient;
import app.sandoval.com.myminitwitter.service.tweets.AuthTwitterService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static app.sandoval.com.myminitwitter.common.Constants.PREF_USERNAME;

public class TweetRepository {

    private AuthTwitterService authTwitterService;
    private AuthTwitterClient authTwitterClient;
    private MutableLiveData<List<Tweet>> allTweets;
    private MutableLiveData<List<Tweet>> favTweets;
    private String userName;

    public TweetRepository() {
        authTwitterClient = AuthTwitterClient.getInstance();
        authTwitterService = AuthTwitterClient.getAuthTwitterService();
        allTweets = getAllTweets();
        userName = SharedPreferencesManager.getStringValue(PREF_USERNAME);
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

    public MutableLiveData<List<Tweet>> getFavTweets() {
        if (favTweets == null) {
            favTweets = new MutableLiveData<>();
        }

        List<Tweet> newFavList = new ArrayList<>();
        Iterator iteTweets = allTweets.getValue().iterator();

        while (iteTweets.hasNext()) {
            Tweet current = (Tweet) iteTweets.next();
            Iterator iteLikes = current.getLikes().iterator();
            boolean find = false;
            while (iteLikes.hasNext() && !find) {
                Like like = (Like) iteLikes.next();
                if (like.getUsername().equals(userName)) {
                    find = true;
                    newFavList.add(current);
                }
            }
        }

        favTweets.setValue(newFavList);

        return favTweets;
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
                    for (int i = 0; i < allTweets.getValue().size(); i++) {
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

    public void likeTweet(final int idTweet) {
        Call<Tweet> call = authTwitterService.likeTweet(idTweet);

        call.enqueue(new Callback<Tweet>() {
            @Override
            public void onResponse(Call<Tweet> call, Response<Tweet> response) {
                if (response.isSuccessful()) {
                    List<Tweet> clonedList = new ArrayList<>();
                    for (int i = 0; i < allTweets.getValue().size(); i++) {
                        if (allTweets.getValue().get(i).getId() == idTweet) {
                            clonedList.add(response.body());
                        } else
                            clonedList.add(new Tweet(allTweets.getValue().get(i)));
                    }

                    allTweets.setValue(clonedList);

                    getFavTweets();
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
