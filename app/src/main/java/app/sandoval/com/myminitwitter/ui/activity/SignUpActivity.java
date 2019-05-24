package app.sandoval.com.myminitwitter.ui.activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import app.sandoval.com.myminitwitter.R;
import app.sandoval.com.myminitwitter.common.SharedPreferencesManager;
import app.sandoval.com.myminitwitter.data.Request.RequestSignUp;
import app.sandoval.com.myminitwitter.data.Response.ResponseAuth;
import app.sandoval.com.myminitwitter.service.MiniTwitterClient;
import app.sandoval.com.myminitwitter.service.MiniTwitterService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static app.sandoval.com.myminitwitter.common.Constants.CODE_SIGN_UP;
import static app.sandoval.com.myminitwitter.common.Constants.PREF_ACTIVE;
import static app.sandoval.com.myminitwitter.common.Constants.PREF_CREATED;
import static app.sandoval.com.myminitwitter.common.Constants.PREF_EMAIL;
import static app.sandoval.com.myminitwitter.common.Constants.PREF_PHOTO_URL;
import static app.sandoval.com.myminitwitter.common.Constants.PREF_TOKEN;
import static app.sandoval.com.myminitwitter.common.Constants.PREF_USERNAME;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnSignUp;
    private TextView textViewLogin;
    private EditText editTextUsername, editTextEmail, editTextPassword;
    private MiniTwitterClient miniTwitterClient;
    private MiniTwitterService miniTwitterService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        findViews();

        events();

        retrofitInit();

        getSupportActionBar().hide();
    }

    private void findViews() {
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        btnSignUp = findViewById(R.id.button_signup);
        textViewLogin = findViewById(R.id.textViewGoToLogin);
    }

    private void events() {
        btnSignUp.setOnClickListener(this);
        textViewLogin.setOnClickListener(this);
    }

    private void retrofitInit() {
        miniTwitterClient = MiniTwitterClient.getInstance();
        miniTwitterService = MiniTwitterClient.getMiniTwitterService();
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.button_signup:
                goToSignUp();
                break;
            case R.id.textViewGoToLogin:
                goToLogin();
                break;
        }
    }

    private void goToSignUp() {
        String username = editTextUsername.getText().toString();
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        if (username.isEmpty()){
            editTextUsername.setError("Username required!");
        }else if (email.isEmpty()){
            editTextEmail.setError("Email required!");
        }else if (password.isEmpty() || password.length()<4){
            editTextPassword.setError("Password required and must have 4 characters at least!");
        }else {
            RequestSignUp requestSignUp = new RequestSignUp(username,email,password, CODE_SIGN_UP);

            Call<ResponseAuth> call = miniTwitterService.doSignUp(requestSignUp);

            call.enqueue(new Callback<ResponseAuth>() {
                @Override
                public void onResponse(Call<ResponseAuth> call, Response<ResponseAuth> response) {
                    if (response.isSuccessful()){
                        Toast.makeText(SignUpActivity.this, "Correct Login", Toast.LENGTH_LONG).show();

                        SharedPreferencesManager.setStringValue(PREF_TOKEN, response.body() != null ? response.body().getToken() : null);
                        SharedPreferencesManager.setStringValue(PREF_USERNAME, response.body() != null ? response.body().getUsername() : null);
                        SharedPreferencesManager.setStringValue(PREF_EMAIL, response.body() != null ? response.body().getEmail() : null);
                        SharedPreferencesManager.setStringValue(PREF_PHOTO_URL, response.body() != null ? response.body().getPhotoUrl() : null);
                        SharedPreferencesManager.setStringValue(PREF_CREATED, response.body() != null ? response.body().getCreated() : null);
                        if (response.body() != null) {
                            SharedPreferencesManager.setBooleanValue(PREF_ACTIVE, response.body().getActive());
                        }

                        Intent intent = new Intent(SignUpActivity.this, DashboardActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(SignUpActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseAuth> call, Throwable t) {
                    Toast.makeText(SignUpActivity.this, "Connection problems, try again", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void goToLogin() {
        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
