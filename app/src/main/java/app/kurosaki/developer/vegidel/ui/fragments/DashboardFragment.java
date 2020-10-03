package app.kurosaki.developer.vegidel.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marcok.stepprogressbar.StepProgressBar;

import app.kurosaki.developer.vegidel.R;
import app.kurosaki.developer.vegidel.core.BaseFragment;
import app.kurosaki.developer.vegidel.databinding.FragmentDashbardBinding;
import app.kurosaki.developer.vegidel.databinding.FragmentForm2Binding;
import app.kurosaki.developer.vegidel.ui.activities.DairyProductActivity;
import app.kurosaki.developer.vegidel.ui.activities.PickAddress;
import app.kurosaki.developer.vegidel.ui.activities.SignUpActivity;
import app.kurosaki.developer.vegidel.ui.activities.VegetableActivity;

public class DashboardFragment extends BaseFragment implements View.OnClickListener{

    FragmentDashbardBinding binding;
    private static DashboardFragment devicesFragment;
    private LOADER_TYPE loader_type = LOADER_TYPE.NORMAL;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashbard, null, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize();
        implementListeners();
    }

    private void implementListeners() {
        binding.dairy.setOnClickListener(this);
        binding.fruit.setOnClickListener(this);
        binding.grocery.setOnClickListener(this);
    }

    public DashboardFragment getInstance() {

        if (devicesFragment == null)
            devicesFragment = new DashboardFragment();
        return devicesFragment;

    }

    @Override
    public void onDetach() {

        super.onDetach();

    }

    private void initialize() {

    }

    @Override
    public void onClick(View v) {
        if(v == binding.dairy)
        {
           startActivity(new Intent(mContext, DairyProductActivity.class));
        }
        if(v == binding.fruit)
        {
           startActivity(new Intent(mContext, VegetableActivity.class));
        }
        if(v == binding.grocery)
        {
           showToast("Comming Soon !!!");
        }
    }
}