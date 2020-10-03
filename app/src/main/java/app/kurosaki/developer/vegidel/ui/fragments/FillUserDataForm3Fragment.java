package app.kurosaki.developer.vegidel.ui.fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.marcok.stepprogressbar.StepProgressBar;

import app.kurosaki.developer.vegidel.R;
import app.kurosaki.developer.vegidel.core.BaseFragment;
import app.kurosaki.developer.vegidel.databinding.FragmentForm2Binding;
import app.kurosaki.developer.vegidel.databinding.FragmentForm3Binding;
import app.kurosaki.developer.vegidel.ui.activities.PickAddress;
import app.kurosaki.developer.vegidel.ui.activities.SignUpActivity;

public class FillUserDataForm3Fragment extends BaseFragment implements View.OnClickListener {

    FragmentForm3Binding binding;
    private static FillUserDataForm3Fragment devicesFragment;
    private LOADER_TYPE loader_type = LOADER_TYPE.NORMAL;

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

        }
        ArrayAdapter<CharSequence> arrayAdapter= ArrayAdapter.createFromResource(mContext, R.array.kyctype,R.layout.dropdown_menu_popup_item);
        binding.selectfiledropdown.setAdapter(arrayAdapter);
    }

    @Override
    public void onClick(View v) {
    }

}
