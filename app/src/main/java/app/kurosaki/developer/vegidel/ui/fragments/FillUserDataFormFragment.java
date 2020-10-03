package app.kurosaki.developer.vegidel.ui.fragments;


import android.os.Bundle;
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
import app.kurosaki.developer.vegidel.databinding.FragmentForm1Binding;
import app.kurosaki.developer.vegidel.ui.activities.SignUpActivity;

public class FillUserDataFormFragment extends BaseFragment implements View.OnClickListener {

    FragmentForm1Binding binding;
    private static FillUserDataFormFragment devicesFragment;
    private LOADER_TYPE loader_type = LOADER_TYPE.NORMAL;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_form1, null, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize();
        implementListeners();
    }

    private void implementListeners() {

    }

    public FillUserDataFormFragment getInstance() {

        if (devicesFragment == null)
            devicesFragment = new FillUserDataFormFragment();
        return devicesFragment;

    }

    @Override
    public void onDetach() {

        super.onDetach();

    }

    private void initialize() {
        if (getActivity() instanceof SignUpActivity) {
            StepProgressBar stepProgressBar = ((SignUpActivity) getActivity()).findViewById(R.id.stepProgressBar);
            ((SignUpActivity) getActivity()).binding.prev.setVisibility(View.INVISIBLE);
            ((SignUpActivity) getActivity()).binding.next.setVisibility(View.VISIBLE);
            stepProgressBar.setCurrentProgressDot(0);
            ((SignUpActivity) getActivity()).binding.next.setOnClickListener(v->{
                NavController navController = Navigation.findNavController(mContext, R.id.nav_host_fragment1);
                navController.navigate(FillUserDataFormFragmentDirections.actionRegister1ToRegisterform());
            });
        }

        ArrayAdapter<CharSequence> arrayAdapter= ArrayAdapter.createFromResource(mContext, R.array.location,R.layout.dropdown_menu_popup_item);
        ArrayAdapter<CharSequence> arrayAdapter1= ArrayAdapter.createFromResource(mContext, R.array.customertype,R.layout.dropdown_menu_popup_item);
        binding.locationdropdown.setAdapter(arrayAdapter);
        binding.customertypedropdown.setAdapter(arrayAdapter1);
    }

    @Override
    public void onClick(View v) {

    }

    private void validate() {
        boolean validated = true;
        String fullname = binding.name.getText().toString().trim();
        String email = binding.email.getText().toString().trim();
        String address = binding.locationdropdown.getText().toString().trim();
        String postcode = binding.customertypedropdown.getText().toString().trim();
        String dob = binding.password.getText().toString().trim();


    }

}
