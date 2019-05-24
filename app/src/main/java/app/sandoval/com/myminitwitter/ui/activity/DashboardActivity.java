package app.sandoval.com.myminitwitter.ui.activity;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.MenuItem;
import android.view.View;

import app.sandoval.com.myminitwitter.R;
import app.sandoval.com.myminitwitter.common.SharedPreferencesManager;
import app.sandoval.com.myminitwitter.ui.fragments.NewTweetDialogFragment;
import app.sandoval.com.myminitwitter.ui.fragments.TweetListFragment;
import de.hdodenhof.circleimageview.CircleImageView;

import static app.sandoval.com.myminitwitter.common.Constants.API_MINI_TWITTER_FILES_URL;
import static app.sandoval.com.myminitwitter.common.Constants.PREF_PHOTO_URL;
import static app.sandoval.com.myminitwitter.common.Constants.TWEET_LIST_ALL;
import static app.sandoval.com.myminitwitter.common.Constants.TWEET_LIST_FAVS;

public class DashboardActivity extends AppCompatActivity {

    FloatingActionButton fab;
    CircleImageView imageViewAvatar;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment fragment = null;

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragment = TweetListFragment.newInstance(TWEET_LIST_ALL);
                    break;
                case R.id.navigation_tweets_like:
                    fragment = TweetListFragment.newInstance(TWEET_LIST_FAVS);
                    break;
                case R.id.navigation_profile:
                    break;
            }

            if (fragment!=null){
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit();

                return true;
            }

            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        fab = findViewById(R.id.fab);
        imageViewAvatar = findViewById(R.id.imageViewToolbarPhoto);
        getSupportActionBar().hide();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, TweetListFragment.newInstance(TWEET_LIST_ALL))
                .commit();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewTweetDialogFragment dialogFragment = new NewTweetDialogFragment();
                dialogFragment.show(getSupportFragmentManager(), "NewTweetDialogFragment");
            }
        });

        String photoUrl = SharedPreferencesManager.getStringValue(PREF_PHOTO_URL);
        if (!photoUrl.isEmpty()) {
            Glide.with(this)
                    .load(API_MINI_TWITTER_FILES_URL + photoUrl)
                    .into(imageViewAvatar);
        }


    }

}
