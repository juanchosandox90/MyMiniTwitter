package app.sandoval.com.myminitwitter.data.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import java.util.List;

import app.sandoval.com.myminitwitter.data.Response.Tweet;
import app.sandoval.com.myminitwitter.data.repository.TweetRepository;

public class TweetViewModel extends AndroidViewModel {

    private TweetRepository tweetRepository;
    private LiveData<List<Tweet>> allTweets;

    public TweetViewModel(@NonNull Application application) {
        super(application);
        tweetRepository = new TweetRepository();
        allTweets = tweetRepository.getAllTweets();
    }

    public LiveData<List<Tweet>> getAllTweets() { return allTweets;}
}
