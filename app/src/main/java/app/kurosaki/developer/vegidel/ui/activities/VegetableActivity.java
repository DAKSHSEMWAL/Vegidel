package app.kurosaki.developer.vegidel.ui.activities;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import app.kurosaki.developer.vegidel.R;
import app.kurosaki.developer.vegidel.adapters.ProductsAdapter;
import app.kurosaki.developer.vegidel.core.BaseActivity;
import app.kurosaki.developer.vegidel.databinding.ActivityVegetableBinding;
import app.kurosaki.developer.vegidel.interfaces.Constants;
import app.kurosaki.developer.vegidel.model.CartData;
import app.kurosaki.developer.vegidel.model.ProductData;
import app.kurosaki.developer.vegidel.utils.Common;

public class VegetableActivity extends BaseActivity implements View.OnClickListener {

    ActivityVegetableBinding binding;
    ProductsAdapter productsAdapter;
    ArrayList<ProductData> dairyData = new ArrayList<>();
    TextView textView;
    int co;
    ArrayList<CartData> cartData = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(mContext, R.layout.activity_vegetable);
        initView();
        setEmptyAdapter();
        setData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        invalidateOptionsMenu();
    }

    private void setData() {
        dairyData.add(new ProductData("https://images.pexels.com/photos/70746/strawberries-red-fruit-royalty-free-70746.jpeg", "Strawberry", "80",40,0,1,Common.getWeightvariants()));
        dairyData.add(new ProductData("https://images.pexels.com/photos/760281/pexels-photo-760281.jpeg", "Grapes", "70",40,0,1,Common.getWeightvariants()));
        dairyData.add(new ProductData("https://images.pexels.com/photos/952360/pexels-photo-952360.jpeg", "Lemon", "60",40,0,1,Common.getWeightvariants()));
        dairyData.add(new ProductData("https://images.pexels.com/photos/61127/pexels-photo-61127.jpeg", "Banana", "70",40,12,3,Common.getDozenvariants()));
        dairyData.add(new ProductData("https://images.pexels.com/photos/51958/oranges-fruit-vitamins-healthy-eating-51958.jpeg", "Oranges", "80",40,0,1,Common.getWeightvariants()));
        dairyData.add(new ProductData("https://images.pexels.com/photos/59999/raspberries-fruits-fruit-berries-59999.jpeg", "Raspberry", "70",40,0,1,Common.getWeightvariants()));
        dairyData.add(new ProductData("https://images.pexels.com/photos/867349/pexels-photo-867349.jpeg", "Kiwi", "60",40,0,1,Common.getWeightvariants()));
        dairyData.add(new ProductData("https://images.pexels.com/photos/326005/pexels-photo-326005.jpeg", "Apple", "70",40,0,1,Common.getWeightvariants()));
        dairyData.add(new ProductData("https://images.pexels.com/photos/27269/pexels-photo-27269.jpg", "Pineapple", "80",40,0,1,Common.getWeightvariants()));
        dairyData.add(new ProductData("https://images.pexels.com/photos/35629/bing-cherries-ripe-red-fruit.jpg", "Cherry", "70",40,0,1,Common.getWeightvariants()));
        dairyData.add(new ProductData("https://images.pexels.com/photos/1395958/pexels-photo-1395958.jpeg", "Blueberry", "60",40,0,1,Common.getWeightvariants()));
        dairyData.add(new ProductData("https://images.pexels.com/photos/1029594/pexels-photo-1029594.jpeg", "Watermelon", "70",40,0,2,Common.getWeightvariants()));
        setAdapter();
    }

    private void setEmptyAdapter() {
        productsAdapter = new ProductsAdapter(mContext, dairyData);

        binding.productlist.setHasFixedSize(true);

        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(mContext);
        binding.productlist.setLayoutManager(gridLayoutManager);


        binding.productlist.setAdapter(productsAdapter);
        productsAdapter.SetOnItemClickListener(new ProductsAdapter.OnItemClickListener() {
            @Override
            public void onOptionClick(View view, int itemPosition, ProductData model) {
                Intent intent = new Intent(mContext, ProductDetailActivity.class);
                intent.putExtra("model", model);
                startActivity(intent);
            }

            @Override
            public void onAdd(View view, int itemPosition, ProductData model) {
                dairyData.set(itemPosition, model);
                sp.setString(CART, gson.toJson(dairyData));
                sp.setInt(Constants.BADGECOUNT, sp.getInt(Constants.BADGECOUNT) + 1);
                invalidateOptionsMenu();
                setAdapter();
            }

            @Override
            public void onRemoved(View view, int itemPosition, ProductData model) {
                sp.setString(CART, gson.toJson(dairyData));
                sp.setInt(Constants.BADGECOUNT, sp.getInt(Constants.BADGECOUNT) >= 0 ? (sp.getInt(Constants.BADGECOUNT) - 1) : 0);
                productsAdapter.notifyItemRemoved(itemPosition);
                invalidateOptionsMenu();
                setAdapter();
            }
        });
        setAdapter();

    }

    private void initView() {

        invalidateOptionsMenu();
        Common.setToolbarWithBackAndTitle(mContext, "Dairy Products", false, R.drawable.ic_arrow);
        binding.mToolbar.toolbar.setNavigationOnClickListener(v -> {
            onBackPressed();
        });
        if (Common.getCart(sp).size() != 0) {
            cartData.addAll(Common.getCart(sp));
        } else {
            cartData.clear();
        }

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
        co = sp.getInt(BADGECOUNT);
        if (rootView != null) {
            textView = rootView.findViewById(R.id.count);
            if (co == 0) {
                textView.setVisibility(View.INVISIBLE);
            } else {
                textView.setVisibility(View.VISIBLE);
            }
            setCount();
            rootView.setOnClickListener(v -> {
                performAction();
            });
        }
        return super.onCreateOptionsMenu(menu);
    }

    private void performAction() {
        for (ProductData productData : dairyData) {
            if (productData.getSelectedQuantity() > 0) {
                cartData.add(new CartData(productData, productData.getSelectedQuantity(), 0));
            }
        }
        if (cartData != null) {
            sp.setString(CART, gson.toJson(cartData));
            Intent intent = new Intent(mContext, CartActivity.class);
            startActivity(intent);
        }
    }

    public void setCount() {
        if (co > 20) {
            textView.setText(String.format(Locale.getDefault(), "%d+", 20));
        } else {
            textView.setText(String.format(Locale.getDefault(), "%d", co));
        }
    }

}