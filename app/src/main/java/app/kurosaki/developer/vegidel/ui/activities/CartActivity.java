package app.kurosaki.developer.vegidel.ui.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;

import app.kurosaki.developer.vegidel.R;
import app.kurosaki.developer.vegidel.adapters.CartAdapter;
import app.kurosaki.developer.vegidel.core.BaseActivity;
import app.kurosaki.developer.vegidel.databinding.ActivityCartBinding;
import app.kurosaki.developer.vegidel.interfaces.Constants;
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
                        if(cartData.size()!=0) {
                            sp.setString(CART, gson.toJson(cartData));
                        }
                        else {
                            sp.setString(CART, gson.toJson(cartData));
                        }
                        sp.setInt(Constants.BADGECOUNT,sp.getInt(Constants.BADGECOUNT)>=0?(Math.max(sp.getInt(Constants.BADGECOUNT) - model.getQuantity(), 0)):0);
                        cartAdapter.notifyItemRemoved(itemPosition);
                        setAdapter();
                    }

                    @Override
                    public void onAdd(View view, int itemPosition, CartData model) {
                        cartData.set(itemPosition,model);
                        sp.setString(CART,gson.toJson(cartData));
                        sp.setInt(Constants.BADGECOUNT,sp.getInt(Constants.BADGECOUNT) + 1);
                        setAdapter();
                    }

                    @Override
                    public void onRemoved(View view, int itemPosition, CartData model) {
                        if(cartData.size()!=0) {
                            if (model.getQuantity() != 0) {
                                cartData.set(itemPosition, model);
                                sp.setInt(Constants.BADGECOUNT,sp.getInt(Constants.BADGECOUNT)>=0?(Math.max(sp.getInt(Constants.BADGECOUNT) - 1, 0)):0);
                            } else {
                                cartData.remove(model);
                                sp.setInt(Constants.BADGECOUNT,0);
                                cartAdapter.notifyItemRemoved(itemPosition);
                            }
                        }
                        else {
                            sp.setInt(BADGECOUNT,0);
                            sp.setString(CART,gson.toJson(cartData));
                        }
                        sp.setString(CART,gson.toJson(cartData));
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

        binding.productlist.setVisibility(recyclerView);
        binding.noproduct.setVisibility(noData);
        binding.btnCart.setVisibility(noData);

    }

    private void setAdapter() {
        if (cartData.size() > 0) {
            setVisibilities(View.GONE, View.VISIBLE);
            cartAdapter.notifyDataSetChanged();
        } else {
            setVisibilities(View.VISIBLE, View.GONE);
        }
    }
}