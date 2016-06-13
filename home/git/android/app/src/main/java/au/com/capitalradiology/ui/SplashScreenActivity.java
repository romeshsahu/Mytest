package au.com.capitalradiology.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import au.com.capitalradiology.R;
import au.com.capitalradiology.Utils.UserSession;
import au.com.capitalradiology.Utils.Utils;


public class SplashScreenActivity extends AppCompatActivity {
    private Button btnGetStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        initListeners();
    }

    private void initListeners() {
        btnGetStarted.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent intent = new Intent(SplashScreenActivity.this, WelcomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        UserSession.sharedInstance(this);
        if (UserSession.currentSession().userToken != null) {
            btnGetStarted.setVisibility(View.GONE);
            startHomeActivity();
            return;
        } else {
            btnGetStarted.setVisibility(View.VISIBLE);
            return;
        }
    }

    private void initUI() {
        btnGetStarted = (Button) findViewById(R.id.btnGetStarted);
        Utils.makeRoundedButton(btnGetStarted,getResources().getColor(R.color.white));
    }

    private void startHomeActivity() {
        (new Handler()).postDelayed(new Runnable() {

            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, HomeActivity.class);
                finish();
                startActivity(intent);
            }

        }, 1500L);
    }
}
