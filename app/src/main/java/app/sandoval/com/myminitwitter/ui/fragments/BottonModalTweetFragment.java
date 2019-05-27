package app.sandoval.com.myminitwitter.ui.fragments;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.navigation.NavigationView;

import app.sandoval.com.myminitwitter.R;
import app.sandoval.com.myminitwitter.data.viewmodel.TweetViewModel;

import static app.sandoval.com.myminitwitter.common.Constants.ARG_TWEET_ID;

public class BottonModalTweetFragment extends BottomSheetDialogFragment {

    private TweetViewModel tweetViewModel;
    private int idTweetDelete;

    public static BottonModalTweetFragment newInstance(int idTweet) {
        BottonModalTweetFragment bottomSheetDialogFragment = new BottonModalTweetFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TWEET_ID, idTweet );
        bottomSheetDialogFragment.setArguments(args);
        return bottomSheetDialogFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null){
            idTweetDelete = getArguments().getInt(ARG_TWEET_ID);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.botton_modal_tweet_fragment, container, false);

        final NavigationView navigationView = view.findViewById(R.id.navigationViewBottomTweet);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.action_delete_tweet){
                    tweetViewModel.deleteTweet(idTweetDelete);
                    getDialog().dismiss();
                    return true;
                }
                return false;
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tweetViewModel = ViewModelProviders.of(getActivity()).get(TweetViewModel.class);
    }

}
