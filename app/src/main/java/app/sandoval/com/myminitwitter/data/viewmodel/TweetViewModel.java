package app.sandoval.com.myminitwitter.data.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import java.util.List;

import app.sandoval.com.myminitwitter.data.Response.Tweet;
import app.sandoval.com.myminitwitter.data.repository.TweetRepository;
import app.sandoval.com.myminitwitter.ui.fragments.BottonModalTweetFragment;

public class TweetViewModel extends AndroidViewModel {

    private TweetRepository tweetRepository;
    private LiveData<List<Tweet>> allTweets;
    private LiveData<List<Tweet>> favTweets;

    public TweetViewModel(@NonNull Application application) {
        super(application);
        tweetRepository = new TweetRepository();
        allTweets = tweetRepository.getAllTweets();
    }

    public void openTweetDialogMenu(Context context, int idTweet){
        BottonModalTweetFragment bottonModalTweetFragment = BottonModalTweetFragment.newInstance(idTweet);
        bottonModalTweetFragment.show(((AppCompatActivity) context).getSupportFragmentManager(),"BottonModalTweetFragment");
    }

    public LiveData<List<Tweet>> getAllTweets() {
        return allTweets;
    }

    public LiveData<List<Tweet>> getFavTweets() {
        favTweets = tweetRepository.getFavTweets();
        return favTweets;
    }

    public LiveData<List<Tweet>> getNewTweets() {
        allTweets = tweetRepository.getAllTweets();
        return allTweets;
    }

    public LiveData<List<Tweet>> getNewFavTweets() {
        getNewTweets();
        return getFavTweets();
    }

    public void createTweet(String tweetMessage) {
        tweetRepository.createTweet(tweetMessage);
    }

    public void likeTweet(int idTweet) {
        tweetRepository.likeTweet(idTweet);
    }

    public void deleteTweet(int idTweet) {
        tweetRepository.deleteTweet(idTweet);
    }

}
