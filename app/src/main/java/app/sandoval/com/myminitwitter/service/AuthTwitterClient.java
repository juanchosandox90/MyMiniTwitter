package app.sandoval.com.myminitwitter.service;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static app.sandoval.com.myminitwitter.common.Constants.API_MINI_TWITTER_BASE_URL;

public class AuthTwitterClient {

    private static AuthTwitterClient instance = null;
    private static AuthTwitterService authTwitterService;
    private Retrofit retrofit;

    public AuthTwitterClient() {

        OkHttpClient.Builder okhttpClientBuilder = new OkHttpClient.Builder();
        okhttpClientBuilder.addInterceptor(new AuthInterceptor());

        OkHttpClient client = okhttpClientBuilder.build();

        retrofit = new Retrofit.Builder()
                .baseUrl(API_MINI_TWITTER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        authTwitterService = retrofit.create(AuthTwitterService.class);
    }

    // Singleton
    public static AuthTwitterClient getInstance(){
        if (instance == null){
            instance = new AuthTwitterClient();
        }
        return instance;
    }

    public static AuthTwitterService getAuthTwitterService(){
        return authTwitterService;
    }

}
