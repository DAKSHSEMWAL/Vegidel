package app.kurosaki.developer.vegidel.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.graphics.Color;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.ArrayAdapter;

import app.kurosaki.developer.vegidel.R;
import app.kurosaki.developer.vegidel.core.BaseActivity;
import app.kurosaki.developer.vegidel.databinding.ActivityLoginBinding;
import app.kurosaki.developer.vegidel.databinding.ActivitySignUpBinding;
import app.kurosaki.developer.vegidel.databinding.ActivitySignUpBindingImpl;
import app.kurosaki.developer.vegidel.utils.Common;

public class SignUpActivity extends BaseActivity {

    public ActivitySignUpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(mContext,R.layout.activity_sign_up);
        initView();
    }


    private void initView() {
        Common.setToolbarWithBackAndTitle(mContext,"",false,R.drawable.ic_arrow);
        /*String text=getString(R.string.already_have_an_account_sign_in);
        binding.signup.setText(Common.clickabletext(mContext,LoginActivity.class,text,text.indexOf("Sign"),text.length()));
        binding.signup.setMovementMethod(LinkMovementMethod.getInstance());
        binding.signup.setHighlightColor(Color.TRANSPARENT);*/
        binding.mToolbar.toolbar.setNavigationOnClickListener(v->{
            onBackPressed();
        });

    }
}