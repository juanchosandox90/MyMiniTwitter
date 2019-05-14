package app.sandoval.com.myminitwitter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnLogin;
    private TextView goSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogin = findViewById(R.id.button_login);
        goSignUp = findViewById(R.id.textViewGoToRegiser);

        goSignUp.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

        getSupportActionBar().hide();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.button_login:
                break;
            case R.id.textViewGoToRegiser:
                goToSignUp();
                break;
        }
    }

    private void goToSignUp() {
        Intent intent = new Intent(MainActivity.this,SignUpActivity.class);
        startActivity(intent);
    }
}
