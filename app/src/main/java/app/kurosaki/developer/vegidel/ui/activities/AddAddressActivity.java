package app.kurosaki.developer.vegidel.ui.activities;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import java.util.ArrayList;

import app.kurosaki.developer.vegidel.R;
import app.kurosaki.developer.vegidel.core.BaseActivity;
import app.kurosaki.developer.vegidel.databinding.ActivityAddAddressBinding;
import app.kurosaki.developer.vegidel.utils.Common;

public class AddAddressActivity extends BaseActivity {

    ActivityAddAddressBinding binding;
    ArrayList<String> address = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(mContext, R.layout.activity_add_address);
        initView();
        implementlisteners();
    }

    private void implementlisteners() {

        binding.proceed.setOnClickListener(v->{
            String add = binding.name.getText().toString().trim() + "\n" +
                         binding.address.getText().toString().trim() + "\n" +
                         binding.city.getText().toString().trim() + "\n" +
                         binding.phonecode.getText().toString().trim() + "\n" +
                         binding.phone.getText().toString().trim() ;
            address.add(add);
            sp.setString(ADDRESS, gson.toJson(address));
            finish();
        });

    }

    private void initView() {

        Common.setToolbarWithBackAndTitle(mContext,"",false,R.drawable.ic_arrow);
        binding.mToolbar.toolbar.setNavigationOnClickListener(v->{
            onBackPressed();
        });

        address.addAll(Common.getAddresses(sp));

    }



}