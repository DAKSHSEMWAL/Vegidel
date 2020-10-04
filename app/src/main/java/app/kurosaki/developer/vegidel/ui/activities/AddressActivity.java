package app.kurosaki.developer.vegidel.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;

import app.kurosaki.developer.vegidel.R;
import app.kurosaki.developer.vegidel.adapters.AddressAdapter;
import app.kurosaki.developer.vegidel.core.BaseActivity;
import app.kurosaki.developer.vegidel.databinding.ActivityAddressBinding;
import app.kurosaki.developer.vegidel.model.CheckOutModel;
import app.kurosaki.developer.vegidel.utils.Common;

public class AddressActivity extends BaseActivity {

    ActivityAddressBinding binding;
    AddressAdapter addressAdapter;
    ArrayList<String> address = new ArrayList<>();
    CheckOutModel checkOutModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(mContext, R.layout.activity_address);
        initView();
        setEmptyAdapter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        address.clear();
        address.addAll(Common.getAddresses(sp));
        setAdapter();
    }

    private void initView() {
        Common.setToolbarWithBackAndTitle(mContext, "Selcet Address", false, R.drawable.ic_arrow);
        binding.mToolbar.toolbar.setNavigationOnClickListener(v-> onBackPressed());
        address.addAll(Common.getAddresses(sp));
        checkOutModel=Common.getCheckout(sp);
        binding.addaddress.setOnClickListener(v->{
            Intent intent = new Intent(mContext,AddAddressActivity.class);
            startActivity(intent);
        });
        binding.proceed.setOnClickListener(c->{
            checkOutModel.setAddress(address.get(addressAdapter.mSelectedItem));
            sp.setString(CHECKOUT, gson.toJson(checkOutModel));
            Intent intent = new Intent(mContext, CheckoutActivity.class);
            startActivity(intent);
        });
    }

    private void setEmptyAdapter() {
        addressAdapter = new AddressAdapter(mContext, address);

        binding.recyclerView.setHasFixedSize(true);

        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(mContext);
        binding.recyclerView.setLayoutManager(gridLayoutManager);


        binding.recyclerView.setAdapter(addressAdapter);
        addressAdapter.SetOnItemClickListener(new AddressAdapter.OnItemClickListener() {
            @Override
            public void onOptionClick(View view, int itemPosition, String model) {
                checkOutModel.setAddress(model);
            }

            @Override
            public void onDelete(View view, int itemPosition, String model) {
                address.remove(itemPosition);
                addressAdapter.notifyItemChanged(itemPosition);
                setAdapter();
            }
        });
        addressAdapter.mSelectedItem = 0;
        setAdapter();

    }


    private void setVisibilities(int noData, int recyclerView) {

        binding.recyclerView.setVisibility(recyclerView);
        binding.nodata.setVisibility(noData);

    }

    private void setAdapter() {
        if (address.size() > 0) {
            setVisibilities(View.GONE, View.VISIBLE);
            addressAdapter.notifyDataSetChanged();
        } else {
            setVisibilities(View.VISIBLE, View.GONE);
        }
    }


}