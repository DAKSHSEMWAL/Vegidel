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

import java.util.Locale;

import app.kurosaki.developer.vegidel.R;
import app.kurosaki.developer.vegidel.core.BaseActivity;
import app.kurosaki.developer.vegidel.databinding.ActivityProductDetailBinding;
import app.kurosaki.developer.vegidel.interfaces.Constants;
import app.kurosaki.developer.vegidel.utils.Common;

public class ProductDetailActivity extends BaseActivity implements View.OnClickListener {

    ActivityProductDetailBinding binding;
    String title,image;
    TextView textView;
    int c=0,co=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(mContext,R.layout.activity_product_detail);
        initView();
        implementListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        co=sp.getInt(BADGECOUNT);
        invalidateOptionsMenu();
    }

    private void implementListeners() {
        binding.content.add.setOnClickListener(this);
        binding.content.subtract.setOnClickListener(this);
        binding.content.Addtocart.setOnClickListener(this);
        binding.btnCart.setOnClickListener(this);
    }

    private void initView() {
        co=sp.getInt(BADGECOUNT);
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
        if(view == binding.content.add)
        {
            c=c+1;
            binding.content.subtract.setVisibility(View.VISIBLE);
            binding.content.quantity.setText(String.format(Locale.getDefault(),"%d", c));
        }
        if(view == binding.content.subtract)
        {
            c=c-1;
            if(c<=0) {
                c=0;
                binding.content.subtract.setVisibility(View.INVISIBLE);
            }
            binding.content.quantity.setText(String.format(Locale.getDefault(),"%d", c));
        }
        if(view == binding.btnCart)
        {
            co=c+co;
            if(co==0)
            {
                textView.setVisibility(View.INVISIBLE);
            }
            else {
                textView.setVisibility(View.VISIBLE);
            }
            sp.setInt(BADGECOUNT,co);
            c=0;
            binding.content.subtract.setVisibility(View.INVISIBLE);
            binding.content.quantity.setText(String.format(Locale.getDefault(),"%d", c));
            setCount();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cart, menu);
        MenuItem menuItem = menu.findItem(R.id.ic_group);
        FrameLayout rootView = (FrameLayout) menuItem.getActionView();
        co=sp.getInt(BADGECOUNT);
        if (rootView != null) {
            textView = rootView.findViewById(R.id.count);
            if(co==0)
            {
                textView.setVisibility(View.INVISIBLE);
            }
            else {
                textView.setVisibility(View.VISIBLE);
            }
            setCount();
        }
        return super.onCreateOptionsMenu(menu);
    }

    public void setCount() {
        if (co > 20) {
            textView.setText(String.format(Locale.getDefault(), "%d+", 20));
        } else {
            textView.setText(String.format(Locale.getDefault(), "%d", co));
        }
    }

}