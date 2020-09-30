package app.kurosaki.developer.vegidel.ui.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import app.kurosaki.developer.vegidel.R;
import app.kurosaki.developer.vegidel.core.BaseActivity;
import app.kurosaki.developer.vegidel.databinding.ActivityDashboardBinding;
import app.kurosaki.developer.vegidel.databinding.DialogLogoutBinding;

import static app.kurosaki.developer.vegidel.utils.Common.setToolbarWithBackAndTitle;

public class DashboardActivity extends BaseActivity implements View.OnClickListener {

    ActivityDashboardBinding binding;
    DialogLogoutBinding dialogLogoutBinding;

    DrawerLayout drawerLayout;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(mContext, R.layout.activity_dashboard);
        initView();
        implementListeners();
    }

    private void implementListeners() {
        binding.tvBack.setOnClickListener(this);
        binding.shop.setOnClickListener(this);
        binding.profile.setOnClickListener(this);
        binding.notification.setOnClickListener(this);
        binding.logout.setOnClickListener(this);
        binding.about.setOnClickListener(this);
    }

    private void initView() {
        setToolbarWithBackAndTitle(mContext, "", false, R.drawable.ic_hamburger);
        drawerLayout = binding.drawerLayout;
        binding.contentdash.mToolbar.toolbar.setNavigationOnClickListener(v -> {
            drawerLayout.openDrawer(GravityCompat.START);
        });
    }


    @Override
    public void onClick(View view) {
        if (view == binding.tvBack) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        if (view == binding.shop) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        if (view == binding.profile) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        if (view == binding.notification) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        if (view == binding.about) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        if (view == binding.logout) {
            showLogOutDialog();
            drawerLayout.closeDrawer(GravityCompat.START);
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

    private void showLogOutDialog() {
        final Dialog dialog = new Dialog(mContext, R.style.MyAlertDialogStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogLogoutBinding = DataBindingUtil.inflate(LayoutInflater.from(dialog.getContext()), R.layout.dialog_logout, null, false);
        dialog.setContentView(dialogLogoutBinding.getRoot());
        dialog.setOnShowListener(dialogInterface -> revealShowImage(dialogLogoutBinding.getRoot(), true, null));

        dialogLogoutBinding.ok.setOnClickListener(v -> {
            revealShowImage(dialogLogoutBinding.getRoot(), false, dialog);
            sp.setBoolean(IS_USER_LOGGED_IN, false);
            Intent intent = new Intent(mContext, LoginActivity.class);
            finishAffinity();
            startActivity(intent);
        });
        dialogLogoutBinding.tvBack.setOnClickListener(v -> {
            revealShowImage(dialogLogoutBinding.getRoot(), false, dialog);
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