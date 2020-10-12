package app.kurosaki.developer.vegidel.ui.activities;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

import app.kurosaki.developer.vegidel.R;
import app.kurosaki.developer.vegidel.adapters.CartAdapter;
import app.kurosaki.developer.vegidel.core.BaseActivity;
import app.kurosaki.developer.vegidel.databinding.ActivityCheckoutBinding;
import app.kurosaki.developer.vegidel.databinding.DialogConfirmBinding;
import app.kurosaki.developer.vegidel.interfaces.Constants;
import app.kurosaki.developer.vegidel.model.CartData;
import app.kurosaki.developer.vegidel.model.CheckOutModel;
import app.kurosaki.developer.vegidel.utils.Common;
import pl.aprilapps.easyphotopicker.EasyImage;

public class CheckoutActivity extends BaseActivity {

    ActivityCheckoutBinding binding;
    ArrayList<CartData> cartData=new ArrayList<>();
    CheckOutModel checkOutModel;
    CartAdapter cartAdapter;
    DialogConfirmBinding dialogConfirmBinding;


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
        float afterDiscount = checkOutModel.getTotal() - (checkOutModel.getTotal() * 10 / 100);
        binding.totalamount.setText(String.format(Locale.getDefault(),getString(R.string.symbol3), afterDiscount));
        binding.buy.setOnClickListener(v->{
            selectImageDialog();
        });
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
                        sp.setInt(Constants.BADGECOUNT,sp.getInt(Constants.BADGECOUNT)>=0?(Math.max(sp.getInt(Constants.BADGECOUNT) - (int)model.getQuantity(), 0)):0);
                        cartAdapter.notifyItemRemoved(itemPosition);
                        invalidateOptionsMenu();
                        setAdapter();
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

    private void selectImageDialog() {

        final Dialog dialog = new Dialog(mContext, R.style.MyAlertDialogStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogConfirmBinding = DataBindingUtil.inflate(LayoutInflater.from(dialog.getContext()), R.layout.dialog_confirm, null, false);
        dialog.setContentView(dialogConfirmBinding.getRoot());
        dialogConfirmBinding.tvTakePhoto.setOnClickListener(v -> {
            revealShowImage(dialogConfirmBinding.getRoot(), false, dialog);
            EasyImage.openCameraForImage(this, 0);

        });
        dialogConfirmBinding.buy.setOnClickListener(v -> {
            revealShowImage(dialogConfirmBinding.getRoot(), false, dialog);
            ArrayList<CartData> cartData = new ArrayList<>();
            sp.setString(CART,gson.toJson(cartData));
            sp.setInt(BADGECOUNT,0);
            Intent intent = new Intent(mContext,DashboardActivity.class);
            startActivity(intent);
        });

        dialog.setOnShowListener(dialogInterface -> revealShowImage(dialogConfirmBinding.getRoot(), true, null));

        dialog.setOnKeyListener((dialogInterface, i, keyEvent) -> {
            if (i == KeyEvent.KEYCODE_BACK) {

                revealShowImage(dialogConfirmBinding.getRoot(), false, dialog);
                return true;
            }

            return false;
        });


        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog.show();
    }

    private void revealShowImage(@NotNull View dialogView, boolean b, final Dialog dialog) {

        final View view = dialogView.findViewById(R.id.dialog);

        int w = view.getWidth();
        int h = view.getHeight();

        int endRadius = (int) Math.hypot(w, h);

        int cx = (int) (binding.getRoot().getX() + (binding.getRoot().getWidth() / 2));
        int cy = (int) (binding.getRoot().getY() + (binding.getRoot().getHeight() / 2));


        if (b) {
            Animator revealAnimator = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, endRadius);

            view.setVisibility(View.VISIBLE);
            revealAnimator.setDuration(500);
            revealAnimator.start();

        } else {

            Animator anim =
                    ViewAnimationUtils.createCircularReveal(view, cx, cy, endRadius, 0);

            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    dialog.dismiss();
                    view.setVisibility(View.INVISIBLE);

                }
            });
            anim.setDuration(500);
            anim.start();
        }

    }
}