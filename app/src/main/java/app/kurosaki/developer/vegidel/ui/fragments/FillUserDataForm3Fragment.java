package app.kurosaki.developer.vegidel.ui.fragments;


import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.marcok.stepprogressbar.StepProgressBar;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;
import java.util.Objects;

import app.kurosaki.developer.vegidel.R;
import app.kurosaki.developer.vegidel.core.BaseFragment;
import app.kurosaki.developer.vegidel.core.CropImage;
import app.kurosaki.developer.vegidel.databinding.DialogSelectImageBinding;
import app.kurosaki.developer.vegidel.databinding.FragmentForm2Binding;
import app.kurosaki.developer.vegidel.databinding.FragmentForm3Binding;
import app.kurosaki.developer.vegidel.interfaces.Constants;
import app.kurosaki.developer.vegidel.ui.activities.LoginActivity;
import app.kurosaki.developer.vegidel.ui.activities.PickAddress;
import app.kurosaki.developer.vegidel.ui.activities.SignUpActivity;
import app.kurosaki.developer.vegidel.utils.Common;
import at.favre.lib.dali.Dali;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

import static android.app.Activity.RESULT_OK;
import static app.kurosaki.developer.vegidel.utils.Common.hasPermissions;

public class FillUserDataForm3Fragment extends BaseFragment implements View.OnClickListener {

    FragmentForm3Binding binding;
    private static FillUserDataForm3Fragment devicesFragment;
    private LOADER_TYPE loader_type = LOADER_TYPE.NORMAL;
    private String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    File profilepic;
    DialogSelectImageBinding dialogSelectImageBinding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_form3, null, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize();
        implementListeners();
    }

    private void implementListeners() {
        binding.choosefile.setOnClickListener(this);
        binding.sumbit.setOnClickListener(this);
    }

    public FillUserDataForm3Fragment getInstance() {

        if (devicesFragment == null)
            devicesFragment = new FillUserDataForm3Fragment();
        return devicesFragment;

    }

    @Override
    public void onDetach() {

        super.onDetach();

    }

    private void initialize() {
        if (getActivity() instanceof SignUpActivity) {
            StepProgressBar stepProgressBar = ((SignUpActivity) getActivity()).findViewById(R.id.stepProgressBar);

            ((SignUpActivity) getActivity()).binding.next.setVisibility(View.INVISIBLE);

            stepProgressBar.setCurrentProgressDot(2);

            ((SignUpActivity) getActivity()).binding.prev.setOnClickListener(v -> {
                NavController navController = Navigation.findNavController(mContext, R.id.nav_host_fragment1);
                navController.navigate(FillUserDataForm3FragmentDirections.actionRegisterform3ToRegisterform());
            });

            ((SignUpActivity) getActivity()).binding.mToolbar.toolbar.setNavigationOnClickListener(v -> {
                NavController navController = Navigation.findNavController(mContext, R.id.nav_host_fragment1);
                navController.navigate(FillUserDataForm3FragmentDirections.actionRegisterform3ToRegisterform());
            });

        }
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(mContext, R.array.kyctype, R.layout.dropdown_menu_popup_item);
        binding.selectfiledropdown.setAdapter(arrayAdapter);
    }

    @Override
    public void onClick(View v) {
        if (binding.choosefile == v) {
            if (!hasPermissions(mContext, PERMISSIONS)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(PERMISSIONS, PERMISSION_CAMERA);
                }
            } else {
                selectImageDialog();
            }
        }
        if(binding.sumbit == v){
            Intent intent = new Intent(mContext, LoginActivity.class);
            startActivity(intent);
            mContext.finishAffinity();
        }
    }

    private void selectImageDialog() {

        final Dialog dialog = new Dialog(mContext, R.style.MyAlertDialogStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogSelectImageBinding = DataBindingUtil.inflate(LayoutInflater.from(dialog.getContext()), R.layout.dialog_select_image, null, false);
        dialog.setContentView(dialogSelectImageBinding.getRoot());
        if (getActivity() instanceof SignUpActivity)
            Dali.create(mContext).load(((SignUpActivity) requireActivity()).binding.getRoot()).blurRadius(25).into(dialogSelectImageBinding.imgBg);

        dialogSelectImageBinding.tvSelectFromGallery.setOnClickListener(v -> {
            revealShowImage(dialogSelectImageBinding.getRoot(), false, dialog);
            EasyImage.openGallery(this, 0);

        });
        dialogSelectImageBinding.tvTakePhoto.setOnClickListener(v -> {
            revealShowImage(dialogSelectImageBinding.getRoot(), false, dialog);
            EasyImage.openCameraForImage(this, 0);

        });
        dialogSelectImageBinding.cancel.setOnClickListener(v -> {
            revealShowImage(dialogSelectImageBinding.getRoot(), false, dialog);
        });

        dialog.setOnShowListener(dialogInterface -> revealShowImage(dialogSelectImageBinding.getRoot(), true, null));

        dialog.setOnKeyListener((dialogInterface, i, keyEvent) -> {
            if (i == KeyEvent.KEYCODE_BACK) {

                revealShowImage(dialogSelectImageBinding.getRoot(), false, dialog);
                return true;
            }

            return false;
        });


        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CAMERA || requestCode == PERMISSION_GALLERY) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED /*&& grantResults[2] == PackageManager.PERMISSION_GRANTED*/) {
                selectImageDialog();
            } else {
                showToast(getString(R.string.camera));
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_CROP_INTENT) {
            if (resultCode == RESULT_OK) {
                profilepic = Common.BitmapToFile(Common.Photo, mContext).getAbsoluteFile();
                binding.documentchoose.setText(profilepic.getName());
            }
        } else
            EasyImage.handleActivityResult(requestCode, resultCode, data, mContext, new DefaultCallback() {
                @Override
                public void onImagesPicked(@NonNull List<File> imageFiles, EasyImage.ImageSource source, int type) {
                    File file = imageFiles.get(0);
                    if (file != null) {
                        Uri imageUri = Uri.fromFile(file);
                        startActivityForResult(new Intent(mContext, CropImage.class)
                                .putExtra("imageUri", imageUri.toString())
                                .putExtra("cropType", SQUARE), Constants.RC_CROP_INTENT);

                    }
                }
            });
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
