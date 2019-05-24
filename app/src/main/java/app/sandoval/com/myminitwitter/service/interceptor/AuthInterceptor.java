package app.sandoval.com.myminitwitter.service.interceptor;

import java.io.IOException;

import app.sandoval.com.myminitwitter.common.Constants;
import app.sandoval.com.myminitwitter.common.SharedPreferencesManager;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        String token = SharedPreferencesManager.getStringValue(Constants.PREF_TOKEN);
        Request request = chain.request().newBuilder().addHeader("Authorization", "Bearer " + token).build();
        return chain.proceed(request);
    }
}
