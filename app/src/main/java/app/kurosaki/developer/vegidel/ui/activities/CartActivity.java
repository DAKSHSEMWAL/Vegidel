package app.kurosaki.developer.vegidel.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.Locale;

import app.kurosaki.developer.vegidel.R;
import app.kurosaki.developer.vegidel.adapters.AddressAdapter;
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
                        invalidateOptionsMenu();
                    }

                    @Override
                    public void onAdd(View view, int itemPosition, CartData model) {
                        cartData.set(itemPosition,model);
                        sp.setString(CART,gson.toJson(cartData));
                        sp.setInt(Constants.BADGECOUNT,sp.getInt(Constants.BADGECOUNT) + 1);
                        invalidateOptionsMenu();
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
                        invalidateOptionsMenu();
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
        binding.continues.setOnClickListener(v->{
            Intent intent = new Intent(mContext, AddressActivity.class);
            startActivity(intent);
        });
    }

    private void setVisibilities(int noData, int recyclerView) {

        binding.productlist.setVisibility(recyclerView);
        binding.noproduct.setVisibility(noData);
        binding.btnCart.setVisibility(noData);
        binding.continues.setVisibility(recyclerView);

    }

    private void setAdapter() {
        if (cartData.size() > 0) {
            setVisibilities(View.GONE, View.VISIBLE);
            cartAdapter.notifyDataSetChanged();
        } else {
            setVisibilities(View.VISIBLE, View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.totalamount, menu);
        MenuItem menuItem = menu.findItem(R.id.carttotal);
        FrameLayout rootView = (FrameLayout) menuItem.getActionView();
        int total=0;
        if(cartData!=null) {
            for (CartData cartData : cartData) {
                total = total + cartData.getQuantity() * Integer.parseInt(cartData.getProductData().getVariants().get(cartData.getPos()).getPrice());
            }
        }
        if (rootView != null) {
            TextView textView = rootView.findViewById(R.id.count);
            if(total==0)
            {
                textView.setVisibility(View.GONE);
            }
            else {
                textView.setVisibility(View.VISIBLE);
                String totalprice = String.format(Locale.getDefault(),mContext.getString(R.string.symbol2),total);
                SpannableString spannableString = new SpannableString(totalprice);
                ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(mContext.getColor(R.color.DoveGrey));
                spannableString.setSpan(foregroundColorSpan, 0, totalprice.indexOf(":")+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                textView.setText(spannableString);
            }

        }
        return super.onCreateOptionsMenu(menu);
    }

}