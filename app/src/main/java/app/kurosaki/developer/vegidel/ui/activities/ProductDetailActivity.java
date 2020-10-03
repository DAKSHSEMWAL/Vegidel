package app.kurosaki.developer.vegidel.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;

import java.util.Locale;

import app.kurosaki.developer.vegidel.R;
import app.kurosaki.developer.vegidel.adapters.ProductsAdapter;
import app.kurosaki.developer.vegidel.adapters.RadioAdapter;
import app.kurosaki.developer.vegidel.core.BaseActivity;
import app.kurosaki.developer.vegidel.databinding.ActivityProductDetailBinding;
import app.kurosaki.developer.vegidel.model.ProductData;
import app.kurosaki.developer.vegidel.utils.Common;

public class ProductDetailActivity extends BaseActivity implements View.OnClickListener {

    ActivityProductDetailBinding binding;
    String title, image;
    TextView textView;
    int c = 0, co = 0;
    ProductData productData;
    RadioAdapter radioAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(mContext, R.layout.activity_product_detail);
        initView();
        implementListeners();
        setEmptyAdapter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        invalidateOptionsMenu();
    }

    private void setEmptyAdapter() {
        radioAdapter = new RadioAdapter(mContext, productData.getVariants());

        binding.content.recyclerView.setHasFixedSize(true);

        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(mContext);
        binding.content.recyclerView.setLayoutManager(gridLayoutManager);


        binding.content.recyclerView.setAdapter(radioAdapter);
        radioAdapter.SetOnItemClickListener(
                (view, itemPosition, model) -> {
                });

        setAdapter();
    }

    private void implementListeners() {
        binding.content.add.setOnClickListener(this);
        binding.content.subtract.setOnClickListener(this);
        binding.btnCart.setOnClickListener(this);
    }

    private void initView() {
        co = sp.getInt(BADGECOUNT);
        if (getIntent() != null) {
            productData = (ProductData) getIntent().getSerializableExtra("model");
        }
        if (productData != null) {
            Log.e("Model",productData.toString());
            Common.setToolbarWithBackAndTitle(mContext, productData.getName(), false, R.drawable.ic_arrow);
            Glide.with(this).load(productData.getImage()).placeholder(R.drawable.backgrounsplash).into(binding.imageview);
            binding.collapsing.setTitle(productData.getName());
        }
        binding.toolbar.setNavigationOnClickListener(v -> {
            onBackPressed();
        });
        binding.collapsing.setCollapsedTitleTextColor(getColor(R.color.White));
        binding.collapsing.setCollapsedTitleGravity(Gravity.CENTER);
    }

    @Override
    public void onClick(View view) {
        if (view == binding.content.add) {
            c = c + 1;
            binding.content.subtract.setVisibility(View.VISIBLE);
            binding.content.quantity.setText(String.format(Locale.getDefault(), "%d", c));
        }
        if (view == binding.content.subtract) {
            c = c - 1;
            if (c <= 0) {
                c = 0;
                binding.content.subtract.setVisibility(View.INVISIBLE);
            }
            binding.content.quantity.setText(String.format(Locale.getDefault(), "%d", c));
        }
        if (view == binding.btnCart) {
            co = c + co;
            if (co == 0) {
                textView.setVisibility(View.INVISIBLE);
            } else {
                textView.setVisibility(View.VISIBLE);
            }
            sp.setInt(BADGECOUNT, co);
            c = 0;
            binding.content.subtract.setVisibility(View.INVISIBLE);
            binding.content.quantity.setText(String.format(Locale.getDefault(), "%d", c));
            setCount();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cart, menu);
        MenuItem menuItem = menu.findItem(R.id.ic_group);
        FrameLayout rootView = (FrameLayout) menuItem.getActionView();
        co = sp.getInt(BADGECOUNT);
        if (rootView != null) {
            textView = rootView.findViewById(R.id.count);
            if (co == 0) {
                textView.setVisibility(View.INVISIBLE);
            } else {
                textView.setVisibility(View.VISIBLE);
            }
            rootView.setOnClickListener(v -> {
                co = 0;
                setCount();
                sp.setInt(BADGECOUNT, co);
                showToast("HI");
                textView.setVisibility(View.INVISIBLE);
            });
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

    private void setVisibilities(int noData, int recyclerView) {

        binding.content.recyclerView.setVisibility(recyclerView);
        binding.content.noproduct.setVisibility(noData);

    }

    private void setAdapter() {
        if (productData.getVariants().size() > 0) {
            setVisibilities(View.GONE, View.VISIBLE);
            radioAdapter.notifyDataSetChanged();
        } else {
            setVisibilities(View.VISIBLE, View.GONE);
        }
    }

}