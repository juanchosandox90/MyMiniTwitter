package app.sandoval.com.myminitwitter.ui.fragments;

import android.content.Context;
import android.graphics.Typeface;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import app.sandoval.com.myminitwitter.R;
import app.sandoval.com.myminitwitter.common.SharedPreferencesManager;
import app.sandoval.com.myminitwitter.data.Response.Like;
import app.sandoval.com.myminitwitter.data.Response.Tweet;

import static app.sandoval.com.myminitwitter.common.Constants.PREF_USERNAME;

public class MyTweetRecyclerViewAdapter extends RecyclerView.Adapter<MyTweetRecyclerViewAdapter.ViewHolder> {

    private Context ctx;
    private List<Tweet> mValues;
    private String username;

    public MyTweetRecyclerViewAdapter(Context context, List<Tweet> items) {
        ctx = context;
        mValues = items;
        username = SharedPreferencesManager.getStringValue(PREF_USERNAME);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_tweet, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        if (mValues != null) {

            holder.mItem = mValues.get(position);

            holder.textViewUserName.setText(holder.mItem.getUser().getUsername());
            holder.textViewContentMessage.setText(holder.mItem.getMensaje());
            holder.textViewLikesCount.setText(String.valueOf(holder.mItem.getLikes().size()));

            String photo = holder.mItem.getUser().getPhotoUrl();
            if (!photo.equals("")) {
                Glide.with(ctx)
                        .load("https://www.minitwitter.com/apiv1/uploads/photos/" + photo)
                        .into(holder.imageViewAvatar);
            }


            for (Like like : holder.mItem.getLikes()) {
                if (like.getUsername().equals(username)) {
                    Glide.with(ctx)
                            .load(R.drawable.ic_like_red_24dp)
                            .into(holder.imageLike);
                    holder.textViewLikesCount.setTextColor(ctx.getResources().getColor(R.color.colorRed));
                    holder.textViewLikesCount.setTypeface(null, Typeface.BOLD);
                    break;
                }
            }
        }

    }

    public void setData(List<Tweet> tweetList) {
        this.mValues = tweetList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mValues != null)
            return mValues.size();
        else return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView imageViewAvatar;
        public final ImageView imageLike;
        public final TextView textViewUserName;
        public final TextView textViewContentMessage;
        public final TextView textViewLikesCount;
        public Tweet mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            imageViewAvatar = view.findViewById(R.id.imageViewAvatar);
            imageLike = view.findViewById(R.id.imageViewLike);
            textViewUserName = view.findViewById(R.id.textViewUsername);
            textViewContentMessage = view.findViewById(R.id.textViewContentMessage);
            textViewLikesCount = view.findViewById(R.id.textViewLikeCount);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + textViewUserName.getText() + "'";
        }
    }
}
