package app.sandoval.com.myminitwitter.ui.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import app.sandoval.com.myminitwitter.R;
import app.sandoval.com.myminitwitter.data.Response.Tweet;
import app.sandoval.com.myminitwitter.data.viewmodel.TweetViewModel;
import app.sandoval.com.myminitwitter.ui.adapters.MyTweetRecyclerViewAdapter;

import static app.sandoval.com.myminitwitter.common.Constants.TWEET_LIST_ALL;
import static app.sandoval.com.myminitwitter.common.Constants.TWEET_LIST_FAVS;
import static app.sandoval.com.myminitwitter.common.Constants.TWEET_LIST_TYPE;

public class TweetListFragment extends Fragment {

    private int tweetListType = 1;
    private RecyclerView recyclerView;
    private MyTweetRecyclerViewAdapter adapter;
    private List<Tweet> tweetList;
    private TweetViewModel tweetViewModel;
    private SwipeRefreshLayout swipeRefreshLayout;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TweetListFragment() {
    }

    public static TweetListFragment newInstance(int tweetListType) {
        TweetListFragment fragment = new TweetListFragment();
        Bundle args = new Bundle();
        args.putInt(TWEET_LIST_TYPE, tweetListType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            tweetListType = getArguments().getInt(TWEET_LIST_TYPE);
        }

        tweetViewModel = ViewModelProviders.of(getActivity())
                .get(TweetViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tweet_list, container, false);

        // Set the adapter

        Context context = view.getContext();
        recyclerView = view.findViewById(R.id.list);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAzul));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                if (tweetListType == TWEET_LIST_ALL) {
                    loadNewTweetData();
                } else if (tweetListType == TWEET_LIST_FAVS){
                    loadNewFavTweetData();
                }
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        adapter = new MyTweetRecyclerViewAdapter(
                getActivity(),
                tweetList
        );

        recyclerView.setAdapter(adapter);

        if (tweetListType == TWEET_LIST_ALL) {
            loadTweetData();
        } else if (tweetListType == TWEET_LIST_FAVS){
            loadFavTweetData();
        }

        return view;
    }

    private void loadFavTweetData() {
        //TODO: Implement fav tweets call
    }

    private void loadNewFavTweetData() {
        //TODO: Implement new fav tweets call
    }

    private void loadTweetData() {
        tweetViewModel.getAllTweets().observe(getActivity(), new Observer<List<Tweet>>() {
            @Override
            public void onChanged(List<Tweet> tweets) {
                tweetList = tweets;
                adapter.setData(tweetList);
            }
        });
    }

    private void loadNewTweetData() {
        tweetViewModel.getNewTweets().observe(getActivity(), new Observer<List<Tweet>>() {
            @Override
            public void onChanged(List<Tweet> tweets) {
                tweetList = tweets;
                swipeRefreshLayout.setRefreshing(false);
                adapter.setData(tweetList);
                tweetViewModel.getNewTweets().removeObserver(this);
            }
        });
    }
}
