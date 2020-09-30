package app.kurosaki.developer.vegidel.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import app.kurosaki.developer.vegidel.R;
import app.kurosaki.developer.vegidel.core.BaseActivity;
import app.kurosaki.developer.vegidel.databinding.ActivityWelcomeBinding;
import app.kurosaki.developer.vegidel.utils.Common;

public class WelcomeActivity extends BaseActivity {

    ActivityWelcomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_welcome);
        initView();
    }

    private void initView() {
        binding.login.setOnClickListener(v->{
            startActivity(new Intent(mContext,LoginActivity.class));
        });
        binding.signup.setOnClickListener(v->{
            startActivity(new Intent(mContext,SignUpActivity.class));
        });
    }
}