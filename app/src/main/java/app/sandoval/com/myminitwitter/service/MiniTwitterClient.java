package app.sandoval.com.myminitwitter.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static app.sandoval.com.myminitwitter.common.Constants.API_MINI_TWITTER_BASE_URL;

public class MiniTwitterClient {

    private static MiniTwitterClient instance = null;
    private static MiniTwitterService miniTwitterService;
    private Retrofit retrofit;

    public MiniTwitterClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(API_MINI_TWITTER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        miniTwitterService = retrofit.create(MiniTwitterService.class);
    }

    // Singleton
    public static MiniTwitterClient getInstance(){
        if (instance == null){
            instance = new MiniTwitterClient();
        }
        return instance;
    }

    public static MiniTwitterService getMiniTwitterService(){
        return miniTwitterService;
    }

}
