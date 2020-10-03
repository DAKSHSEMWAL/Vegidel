package app.kurosaki.developer.vegidel.ui.activities;

import androidx.databinding.DataBindingUtil;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;

import app.kurosaki.developer.vegidel.R;
import app.kurosaki.developer.vegidel.core.BaseActivity;
import app.kurosaki.developer.vegidel.databinding.ActivityProductDetailBinding;
import app.kurosaki.developer.vegidel.utils.Common;

public class ProductDetailActivity extends BaseActivity implements View.OnClickListener {

    ActivityProductDetailBinding binding;
    String title,image;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(mContext,R.layout.activity_product_detail);
        initView();
    }

    private void initView() {
        if(getIntent()!=null)
        {
            title=getIntent().getStringExtra("title");
            image=getIntent().getStringExtra("image");
        }
        Common.setToolbarWithBackAndTitle(mContext,title,false,R.drawable.ic_arrow);
        Glide.with(this).load(image).placeholder(R.drawable.backgrounsplash).into(binding.imageview);
        binding.collapsing.setTitle(title);
        binding.toolbar.setNavigationOnClickListener(v->{
            onBackPressed();
        });
        binding.collapsing.setCollapsedTitleTextColor(getColor(R.color.White));
        binding.collapsing.setCollapsedTitleGravity(Gravity.CENTER);

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cart, menu);
        MenuItem menuItem = menu.findItem(R.id.ic_group);
        FrameLayout rootView = (FrameLayout) menuItem.getActionView();
        if (rootView != null) {
            textView = rootView.findViewById(R.id.count);
            int co = 0;
        }
        return super.onCreateOptionsMenu(menu);
    }
}