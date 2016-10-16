package mobi.gastronomica.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import mobi.gastronomica.R;
import mobi.gastronomica.utils.Session;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (!Session.SessionExists(this)) {
            goTo(new Intent(getApplicationContext(), LogInActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
        } else {
            goTo(new Intent(getApplicationContext(), MainActivity.class));
        }

    }

    private void goTo(final Intent i) {
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
                startActivity(i);
            }
        }, 2000);
    }
}//endColor
