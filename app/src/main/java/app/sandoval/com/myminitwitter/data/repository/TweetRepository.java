package app.sandoval.com.myminitwitter.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import android.widget.Toast;

import java.util.List;

import app.sandoval.com.myminitwitter.common.MyApp;
import app.sandoval.com.myminitwitter.data.Response.Tweet;
import app.sandoval.com.myminitwitter.service.AuthTwitterClient;
import app.sandoval.com.myminitwitter.service.AuthTwitterService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TweetRepository {

    private AuthTwitterService authTwitterService;
    private AuthTwitterClient authTwitterClient;
    private LiveData<List<Tweet>> allTweets;

    public TweetRepository(){
        authTwitterClient = AuthTwitterClient.getInstance();
        authTwitterService = AuthTwitterClient.getAuthTwitterService();
        allTweets = getAllTweets();
    }

    public LiveData<List<Tweet>> getAllTweets(){
        final MutableLiveData<List<Tweet>> data = new MutableLiveData<>();

        Call<List<Tweet>> call = authTwitterService.getAllTweets();
        call.enqueue(new Callback<List<Tweet>>() {
            @Override
            public void onResponse(Call<List<Tweet>> call, Response<List<Tweet>> response) {
                if (response.isSuccessful()) {
                   data.setValue(response.body());
                } else {
                    Toast.makeText(MyApp.getContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Tweet>> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), "Connection Error!", Toast.LENGTH_LONG).show();
            }
        });

        return data;
    }
}
