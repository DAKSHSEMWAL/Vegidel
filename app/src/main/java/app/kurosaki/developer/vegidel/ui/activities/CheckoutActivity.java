package app.kurosaki.developer.vegidel.ui.activities;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.View;

import java.util.ArrayList;
import java.util.Locale;

import app.kurosaki.developer.vegidel.R;
import app.kurosaki.developer.vegidel.adapters.CartAdapter;
import app.kurosaki.developer.vegidel.core.BaseActivity;
import app.kurosaki.developer.vegidel.databinding.ActivityCheckoutBinding;
import app.kurosaki.developer.vegidel.interfaces.Constants;
import app.kurosaki.developer.vegidel.model.CartData;
import app.kurosaki.developer.vegidel.model.CheckOutModel;
import app.kurosaki.developer.vegidel.utils.Common;

public class CheckoutActivity extends BaseActivity {

    ActivityCheckoutBinding binding;
    ArrayList<CartData> cartData=new ArrayList<>();
    CheckOutModel checkOutModel;
    CartAdapter cartAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(mContext,R.layout.activity_checkout);
        initView();
        setEmptyAdapter();
    }

    private void initView() {
        Common.setToolbarWithBackAndTitle(mContext,"",false,R.drawable.ic_arrow);
        binding.mToolbar.toolbar.setNavigationOnClickListener(v->onBackPressed());
        checkOutModel = Common.getCheckout(sp);
        cartData.clear();
        cartData.addAll(checkOutModel.getCartData());
        SpannableString txtSpannable= new SpannableString(checkOutModel.getAddress());
        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
        StyleSpan boldSpan1 = new StyleSpan(Typeface.BOLD);
        txtSpannable.setSpan(boldSpan, 0, checkOutModel.getAddress().indexOf("\n"), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        txtSpannable.setSpan(boldSpan1, checkOutModel.getAddress().indexOf("Phone"), checkOutModel.getAddress().indexOf(":"), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        binding.address.setText(txtSpannable);
        binding.subtotalamount.setText(String.format(Locale.getDefault(),getString(R.string.symbol3), checkOutModel.getTotal()));
        binding.shippingcharge.setText(String.format(Locale.getDefault(), getString(R.string.symbol4), 10));
        binding.discountamount.setText(String.format("%s%%", String.format(Locale.getDefault(), getString(R.string.symbol4), 5)));
        int afterDiscount = checkOutModel.getTotal() - (checkOutModel.getTotal() * 10 / 100);
        binding.totalamount.setText(String.format(Locale.getDefault(),getString(R.string.symbol3), afterDiscount));
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

    private void setVisibilities(int noData, int recyclerView) {

        binding.productlist.setVisibility(recyclerView);

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