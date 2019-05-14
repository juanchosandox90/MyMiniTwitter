package app.sandoval.com.myminitwitter.service;

import app.sandoval.com.myminitwitter.model.Request.RequestLogin;
import app.sandoval.com.myminitwitter.model.Request.RequestSignUp;
import app.sandoval.com.myminitwitter.model.Response.ResponseAuth;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface MiniTwitterService {

    @POST("/auth/login")
    Call<ResponseAuth> doLogin(@Body RequestLogin requestLogin);

    @POST("/auth/signup")
    Call<ResponseAuth> doSignUp(@Body RequestSignUp requestSignUp);

}
