package app.sandoval.com.myminitwitter.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import app.sandoval.com.myminitwitter.R;
import app.sandoval.com.myminitwitter.data.Request.RequestLogin;
import app.sandoval.com.myminitwitter.data.Response.ResponseAuth;
import app.sandoval.com.myminitwitter.service.MiniTwitterClient;
import app.sandoval.com.myminitwitter.service.MiniTwitterService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnLogin;
    private TextView goSignUp;
    private EditText editTextEmail, editTextPassword;
    private MiniTwitterClient miniTwitterClient;
    private MiniTwitterService miniTwitterService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        events();

        retrofitInit();

        getSupportActionBar().hide();
    }

    private void findViews() {
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.button_login);
        goSignUp = findViewById(R.id.textViewGoToRegiser);
    }

    private void events() {
        goSignUp.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    private void retrofitInit() {
        miniTwitterClient = MiniTwitterClient.getInstance();
        miniTwitterService = MiniTwitterClient.getMiniTwitterService();
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.button_login:
                goToLogin();
                break;
            case R.id.textViewGoToRegiser:
                goToSignUp();
                break;
        }
    }

    private void goToLogin() {
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        if (email.isEmpty()) {
            editTextEmail.setError("Email required!");
        } else if (password.isEmpty()) {
            editTextPassword.setError("Password required!");
        } else {
            RequestLogin requestLogin = new RequestLogin(email, password);

            Call<ResponseAuth> call = miniTwitterService.doLogin(requestLogin);

            call.enqueue(new Callback<ResponseAuth>() {
                @Override
                public void onResponse(Call<ResponseAuth> call, Response<ResponseAuth> response) {
                    if (response.isSuccessful()){
                        Toast.makeText(MainActivity.this, "Correct Login", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseAuth> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Connection problems, try again", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void goToSignUp() {
        Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
        startActivity(intent);
    }
}
