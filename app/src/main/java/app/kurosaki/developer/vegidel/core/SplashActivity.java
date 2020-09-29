package app.kurosaki.developer.vegidel.core;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import app.kurosaki.developer.vegidel.R;
import app.kurosaki.developer.vegidel.ui.activities.WelcomeActivity;
import app.kurosaki.developer.vegidel.utils.Common;

public class SplashActivity extends BaseActivity {

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(0, 0);
        setContentView(R.layout.activity_splash);

        if (!isTaskRoot()
                && getIntent().hasCategory(Intent.CATEGORY_LAUNCHER)
                && getIntent().getAction() != null
                && getIntent().getAction().equals(Intent.ACTION_MAIN)) {
            finish();
            return;
        }

        if (getIntent() != null) {
            if (getIntent().getBooleanExtra("DEACTIVATE", false)) {
                sp.clearData();
                finishAffinity();
            }
        }

        initialize();
        if (sp.getBoolean(IS_USER_LOGGED_IN))
            gotoDashboard();
        else
            startTimer();
    }

    /**
     * method to initialize all the variables and views
     */
    private void initialize() {
        handler = new Handler();
    }

    /**
     * method to start splash timer
     */
    private void startTimer() {
        handler.postDelayed(this::gotoLandingScreen, SPLASH_TIMER);
    }

    private void gotoDashboard() {

    }

    private void gotoLandingScreen() {
        sp.clearData();
        startActivity(new Intent(this, WelcomeActivity.class));
        finishAffinity();
    }

    @Override
    public void onBackPressed() {
        if (handler != null)
            handler.removeCallbacksAndMessages(null);
        super.onBackPressed();
    }
}