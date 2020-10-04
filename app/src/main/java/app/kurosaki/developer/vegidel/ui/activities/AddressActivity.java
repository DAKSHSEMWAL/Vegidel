package app.kurosaki.developer.vegidel.ui.activities;

import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;

import app.kurosaki.developer.vegidel.R;
import app.kurosaki.developer.vegidel.adapters.AddressAdapter;
import app.kurosaki.developer.vegidel.core.BaseActivity;
import app.kurosaki.developer.vegidel.databinding.ActivityAddressBinding;
import app.kurosaki.developer.vegidel.utils.Common;

public class AddressActivity extends BaseActivity {

    ActivityAddressBinding binding;
    AddressAdapter addressAdapter;
    ArrayList<String> address = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(mContext, R.layout.activity_address);
        initView();
        setEmptyAdapter();
        setData();
    }

    private void initView() {
        Common.setToolbarWithBackAndTitle(mContext, "", false, R.drawable.ic_arrow);
        binding.mToolbar.toolbar.setNavigationOnClickListener(v->{onBackPressed();});
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

    private void setData() {
        address.add("Shewrapara, Mirpur, Dhaka-1216 \nHouse no: 938 \nRoad no: 9");
        address.add("Chatkhil, Noakhali \nHouse no: 22 \nRoad no: 7");
        address.add("Shewrapara, Mirpur, Dhaka-1216 \nHouse no: 938 \nRoad no: 9");
        address.add("Chatkhil, Noakhali \nHouse no: 22 \nRoad no: 7");
        address.add("Shewrapara, Mirpur, Dhaka-1216 \nHouse no: 938 \nRoad no: 9");
        address.add("Chatkhil, Noakhali \nHouse no: 22 \nRoad no: 7");
        sp.setString(ADDRESS, gson.toJson(address));
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