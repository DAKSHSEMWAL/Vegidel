package app.kurosaki.developer.vegidel.ui.activities;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;

import app.kurosaki.developer.vegidel.R;
import app.kurosaki.developer.vegidel.adaoters.ProductsAdapter;
import app.kurosaki.developer.vegidel.core.BaseActivity;
import app.kurosaki.developer.vegidel.databinding.ActivityVegetableBinding;
import app.kurosaki.developer.vegidel.model.ProductData;
import app.kurosaki.developer.vegidel.utils.Common;

public class VegetableActivity extends BaseActivity implements View.OnClickListener {

    ActivityVegetableBinding binding;
    ProductsAdapter productsAdapter;
    ArrayList<ProductData> dairyData = new ArrayList<>();
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(mContext, R.layout.activity_vegetable);
        initView();
        setEmptyAdapter();
        setData();
    }

    private void setData() {
        dairyData.add(new ProductData("https://images.pexels.com/photos/70746/strawberries-red-fruit-royalty-free-70746.jpeg", "Strawberry", "80"));
        dairyData.add(new ProductData("https://images.pexels.com/photos/760281/pexels-photo-760281.jpeg", "Grapes", "70"));
        dairyData.add(new ProductData("https://images.pexels.com/photos/952360/pexels-photo-952360.jpeg", "Lemon", "60"));
        dairyData.add(new ProductData("https://images.pexels.com/photos/61127/pexels-photo-61127.jpeg", "Banana", "70"));
        dairyData.add(new ProductData("https://images.pexels.com/photos/51958/oranges-fruit-vitamins-healthy-eating-51958.jpeg", "Oranges", "80"));
        dairyData.add(new ProductData("https://images.pexels.com/photos/59999/raspberries-fruits-fruit-berries-59999.jpeg", "Raspberry", "70"));
        dairyData.add(new ProductData("https://images.pexels.com/photos/867349/pexels-photo-867349.jpeg", "Kiwi", "60"));
        dairyData.add(new ProductData("https://images.pexels.com/photos/326005/pexels-photo-326005.jpeg", "Apple", "70"));
        dairyData.add(new ProductData("https://images.pexels.com/photos/27269/pexels-photo-27269.jpg", "Pineapple", "80"));
        dairyData.add(new ProductData("https://images.pexels.com/photos/35629/bing-cherries-ripe-red-fruit.jpg", "Cherry", "70"));
        dairyData.add(new ProductData("https://images.pexels.com/photos/1395958/pexels-photo-1395958.jpeg", "Blueberry", "60"));
        dairyData.add(new ProductData("https://images.pexels.com/photos/1029594/pexels-photo-1029594.jpeg", "Watermelon", "70"));
        setAdapter();
    }

    private void setEmptyAdapter() {
        productsAdapter = new ProductsAdapter(mContext, dairyData);

        binding.productlist.setHasFixedSize(true);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
        binding.productlist.setLayoutManager(gridLayoutManager);


        binding.productlist.setAdapter(productsAdapter);
        productsAdapter.SetOnItemClickListener(
                (view, itemPosition, model) -> {
                    Intent intent = new Intent(mContext,ProductDetailActivity.class);
                    intent.putExtra("title",model.getName());
                    intent.putExtra("image",model.getImage());
                    startActivity(intent);
                });

    }

    private void initView() {

        Common.setToolbarWithBackAndTitle(mContext, "Dairy Products", false, R.drawable.ic_arrow);
        binding.mToolbar.toolbar.setNavigationOnClickListener(v -> {
            onBackPressed();
        });

    }

    @Override
    public void onClick(View view) {

    }

    private void setVisibilities(int noData, int recyclerView) {

        binding.noproduct.setVisibility(noData);
        binding.productlist.setVisibility(recyclerView);

    }

    private void setAdapter() {
        if (dairyData.size() > 0) {
            setVisibilities(View.GONE, View.VISIBLE);
            productsAdapter.notifyDataSetChanged();
        } else {
            setVisibilities(View.VISIBLE, View.GONE);
        }
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