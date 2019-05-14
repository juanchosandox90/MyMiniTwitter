package app.sandoval.com.myminitwitter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnSignUp;
    private TextView textViewLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        btnSignUp = findViewById(R.id.button_signup);
        textViewLogin = findViewById(R.id.textViewGoToLogin);

        btnSignUp.setOnClickListener(this);
        textViewLogin.setOnClickListener(this);

        getSupportActionBar().hide();
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.button_signup:
                break;
            case R.id.textViewGoToLogin:
                goToLogin();
                break;
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
