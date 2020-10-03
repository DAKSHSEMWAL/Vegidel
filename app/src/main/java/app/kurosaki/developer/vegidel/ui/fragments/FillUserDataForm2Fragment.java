package app.kurosaki.developer.vegidel.ui.fragments;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.marcok.stepprogressbar.StepProgressBar;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.util.List;

import app.kurosaki.developer.vegidel.R;
import app.kurosaki.developer.vegidel.core.BaseFragment;
import app.kurosaki.developer.vegidel.databinding.FragmentForm2Binding;
import app.kurosaki.developer.vegidel.interfaces.Constants;
import app.kurosaki.developer.vegidel.ui.activities.PickAddress;
import app.kurosaki.developer.vegidel.ui.activities.SignUpActivity;
import app.kurosaki.developer.vegidel.utils.Common;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

import static android.app.Activity.RESULT_OK;

public class FillUserDataForm2Fragment extends BaseFragment implements View.OnClickListener {

    FragmentForm2Binding binding;
    private static FillUserDataForm2Fragment devicesFragment;
    private LOADER_TYPE loader_type = LOADER_TYPE.NORMAL;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_form2, null, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize();
        implementListeners();
    }

    private void implementListeners() {
        binding.location.setOnClickListener(this);
    }

    public FillUserDataForm2Fragment getInstance() {

        if (devicesFragment == null)
            devicesFragment = new FillUserDataForm2Fragment();
        return devicesFragment;

    }

    @Override
    public void onDetach() {

        super.onDetach();

    }

    private void initialize() {
        if (getActivity() instanceof SignUpActivity) {
            StepProgressBar stepProgressBar = ((SignUpActivity) getActivity()).findViewById(R.id.stepProgressBar);
            ((SignUpActivity) getActivity()).binding.prev.setVisibility(View.VISIBLE);
            stepProgressBar.setCurrentProgressDot(1);
            ((SignUpActivity) getActivity()).binding.prev.setOnClickListener(v -> {
                NavController navController = Navigation.findNavController(mContext, R.id.nav_host_fragment1);
                navController.navigate(FillUserDataForm2FragmentDirections.actionRegisterformToRegister1());
            });
        }
    }

    @Override
    public void onClick(View v) {
        if (v == binding.location) {
            startActivityForResult(new Intent(mContext, PickAddress.class), 12);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("klkl", "" + requestCode + "," + resultCode);
        if (resultCode == Activity.RESULT_OK && data != null) {
            String message = data.getStringExtra("ADDRESS");
            String postcode = data.getStringExtra("postcode");
            String state = data.getStringExtra("state");
            String city = data.getStringExtra("city");
            String country = data.getStringExtra("country");
            String feature = data.getStringExtra("feature");
            binding.location.setText(feature);
            binding.state.setText(state);
            binding.pincode.setText(postcode);
            binding.city.setText(city);
        }
    }

}
