package app.kurosaki.developer.vegidel.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import java.util.ArrayList;
import java.util.Locale;

import app.kurosaki.developer.vegidel.R;
import app.kurosaki.developer.vegidel.adapters.ProductsAdapter;
import app.kurosaki.developer.vegidel.core.BaseActivity;
import app.kurosaki.developer.vegidel.databinding.ActivityDairyProductBinding;
import app.kurosaki.developer.vegidel.model.ProductData;
import app.kurosaki.developer.vegidel.utils.Common;

public class DairyProductActivity extends BaseActivity implements View.OnClickListener {

    ActivityDairyProductBinding binding;
    ProductsAdapter productsAdapter;
    ArrayList<ProductData> dairyData = new ArrayList<>();
    TextView textView;
    int co;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(mContext, R.layout.activity_dairy_product);
        initView();
        setEmptyAdapter();
        setData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        co=sp.getInt(BADGECOUNT);
        invalidateOptionsMenu();
    }

    private void setData() {
        dairyData.add(new ProductData("https://images.pexels.com/photos/1435706/pexels-photo-1435706.jpeg", "Milk", "80",Common.getLitrevariants()));
        dairyData.add(new ProductData("https://images.pexels.com/photos/4109943/pexels-photo-4109943.jpeg", "Cheese", "70",Common.getWeightvariants()));
        dairyData.add(new ProductData("https://images.pexels.com/photos/3212808/pexels-photo-3212808.jpeg", "Yogurt", "60",Common.getWeightvariants()));
        dairyData.add(new ProductData("https://images.pexels.com/photos/3821250/pexels-photo-3821250.jpeg", "Butter", "70",Common.getWeightvariants()));
        dairyData.add(new ProductData("https://images.pexels.com/photos/1435706/pexels-photo-1435706.jpeg", "Milk", "80",Common.getLitrevariants()));
        dairyData.add(new ProductData("https://images.pexels.com/photos/1435706/pexels-photo-1435706.jpeg", "Milk", "80",Common.getLitrevariants()));
        dairyData.add(new ProductData("https://images.pexels.com/photos/4109943/pexels-photo-4109943.jpeg", "Cheese", "70",Common.getWeightvariants()));
        dairyData.add(new ProductData("https://images.pexels.com/photos/3212808/pexels-photo-3212808.jpeg", "Yogurt", "60",Common.getWeightvariants()));
        dairyData.add(new ProductData("https://images.pexels.com/photos/3821250/pexels-photo-3821250.jpeg", "Butter", "70",Common.getWeightvariants()));
        dairyData.add(new ProductData("https://images.pexels.com/photos/1435706/pexels-photo-1435706.jpeg", "Milk", "80",Common.getLitrevariants()));
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
                    intent.putExtra("model",model);
                    startActivity(intent);
                });

    }

    private void initView() {
        co=sp.getInt(BADGECOUNT);
        invalidateOptionsMenu();

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
            rootView.setOnClickListener(v->{
                co=0;
                setCount();
                sp.setInt(BADGECOUNT,co);
                showToast("HI");
                textView.setVisibility(View.INVISIBLE);
            });
        }
        return super.onCreateOptionsMenu(menu);
    }

    public void setCount() {
        if (co > 9999) {
            textView.setText(String.format(Locale.getDefault(), "%d+", 9999));
        } else {
            textView.setText(String.format(Locale.getDefault(), "%d", co));
        }
    }

}