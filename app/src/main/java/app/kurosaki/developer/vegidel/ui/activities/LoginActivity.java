package app.kurosaki.developer.vegidel.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import app.kurosaki.developer.vegidel.R;
import app.kurosaki.developer.vegidel.core.BaseActivity;
import app.kurosaki.developer.vegidel.databinding.ActivityLoginBinding;
import app.kurosaki.developer.vegidel.utils.Common;

public class LoginActivity extends BaseActivity {

    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(mContext,R.layout.activity_login);
        initView();
    }

    private void initView() {
        Common.setToolbarWithBackAndTitle(mContext,"",false,R.drawable.ic_arrow);
        String text=getString(R.string.don_t_have_an_acoount_sign_up);
        binding.signup.setText(Common.clickabletext(mContext,SignUpActivity.class,text,text.indexOf("Sign"),text.length()));
        binding.signup.setMovementMethod(LinkMovementMethod.getInstance());
        binding.signup.setHighlightColor(Color.TRANSPARENT);
        binding.mToolbar.toolbar.setNavigationOnClickListener(v->{
            onBackPressed();
        });
        binding.login.setOnClickListener(v->{
            startActivity(new Intent(mContext,DashboardActivity.class));
        });
    }


}