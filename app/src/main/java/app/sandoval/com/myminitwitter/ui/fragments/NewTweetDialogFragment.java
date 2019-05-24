package app.sandoval.com.myminitwitter.ui.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;

import java.util.Objects;

import app.sandoval.com.myminitwitter.R;
import app.sandoval.com.myminitwitter.common.SharedPreferencesManager;
import app.sandoval.com.myminitwitter.data.viewmodel.TweetViewModel;
import de.hdodenhof.circleimageview.CircleImageView;

import static app.sandoval.com.myminitwitter.common.Constants.API_MINI_TWITTER_FILES_URL;
import static app.sandoval.com.myminitwitter.common.Constants.PREF_PHOTO_URL;


public class NewTweetDialogFragment extends DialogFragment implements View.OnClickListener {

    private ImageView imageViewClose;
    private CircleImageView imageViewAvatar;
    private Button buttonTweet;
    private EditText tweetMessage;
    private TweetViewModel tweetViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_new_tweet_dialog, container, false);

        imageViewClose = view.findViewById(R.id.imageViewClose);
        imageViewAvatar = view.findViewById(R.id.imageViewAvatar);
        buttonTweet = view.findViewById(R.id.buttonTweet);
        tweetMessage = view.findViewById(R.id.editTextMessage);

        buttonTweet.setOnClickListener(this);
        imageViewClose.setOnClickListener(this);

        String photoUrl = SharedPreferencesManager.getStringValue(PREF_PHOTO_URL);
        if (!photoUrl.isEmpty()) {
            Glide.with(this)
                    .load(API_MINI_TWITTER_FILES_URL + photoUrl)
                    .into(imageViewAvatar);
        }

        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        String message = tweetMessage.getText().toString();

        if (id == R.id.buttonTweet) {
            if (message.isEmpty()) {
                Toast.makeText(getActivity(), "You must writte a message", Toast.LENGTH_LONG).show();
            } else {
                tweetViewModel = ViewModelProviders.of(getActivity())
                        .get(TweetViewModel.class);
                tweetViewModel.createTweet(message);
                getDialog().dismiss();
            }
        } else if (id == R.id.imageViewClose) {
            if (!message.isEmpty()) {
                getShowsDialogConfirm();
            } else {
                getDialog().dismiss();
            }
        }

    }

    private void getShowsDialogConfirm() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage(Objects.requireNonNull(getActivity()).getString(R.string.cancel_tweet_message))
                .setTitle(getActivity().getString(R.string.cancel_tweet));

        builder.setPositiveButton(getActivity().getString(R.string.delete_tweet_button), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                getDialog().dismiss();

            }
        });

        builder.setNegativeButton(getActivity().getString(R.string.cancel_tweet_button), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();

        dialog.show();
    }
}
