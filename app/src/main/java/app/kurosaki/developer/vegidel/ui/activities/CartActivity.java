package app.kurosaki.developer.vegidel.ui.activities;

import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;

import app.kurosaki.developer.vegidel.R;
import app.kurosaki.developer.vegidel.adapters.CartAdapter;
import app.kurosaki.developer.vegidel.core.BaseActivity;
import app.kurosaki.developer.vegidel.databinding.ActivityCartBinding;
import app.kurosaki.developer.vegidel.model.CartData;
import app.kurosaki.developer.vegidel.utils.Common;

public class CartActivity extends BaseActivity {

    ActivityCartBinding binding;
    CartAdapter cartAdapter;
    ArrayList<CartData> cartData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(mContext, R.layout.activity_cart);
        initView();
        setEmptyAdapter();
    }


    private void setEmptyAdapter() {
        cartAdapter = new CartAdapter(mContext, cartData);

        binding.productlist.setHasFixedSize(true);

        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(mContext);
        binding.productlist.setLayoutManager(gridLayoutManager);


        binding.productlist.setAdapter(cartAdapter);
        cartAdapter.SetOnItemClickListener(
                new CartAdapter.OnItemClickListener() {
                    @Override
                    public void onOptionClick(View view, int itemPosition, CartData model) {

                    }

                    @Override
                    public void onDelete(View view, int itemPosition, CartData model) {
                        cartData.remove(itemPosition);
                        cartAdapter.notifyItemRemoved(itemPosition);
                        setAdapter();
                    }
                });

        setAdapter();

    }

    private void initView() {

        Common.setToolbarWithBackAndTitle(mContext, "Cart", false, R.drawable.ic_arrow);
        binding.mToolbar.toolbar.setNavigationOnClickListener(v -> {
            onBackPressed();
        });
        cartData.addAll(Common.getCart(sp));
    }

    private void setVisibilities(int noData, int recyclerView) {

        binding.noproduct.setVisibility(noData);
        binding.productlist.setVisibility(recyclerView);
        binding.btnCart.setVisibility(noData);

    }

    private void setAdapter() {
        if (cartData.size() > 0) {
            setVisibilities(View.GONE, View.VISIBLE);
            cartAdapter.notifyDataSetChanged();
        } else {
            sp.setInt(BADGECOUNT,0);
            cartData.clear();
            sp.setString(CART,gson.toJson(cartData));
            setVisibilities(View.VISIBLE, View.GONE);
        }
    }
}