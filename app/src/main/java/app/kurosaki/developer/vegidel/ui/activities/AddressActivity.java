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
    }

    private void initView() {
        Common.setToolbarWithBackAndTitle(mContext, "", false, R.drawable.ic_arrow);
    }

    private void setEmptyAdapter() {
        addressAdapter = new AddressAdapter(mContext, address);

        binding.recyclerView.setHasFixedSize(true);

        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(mContext);
        binding.recyclerView.setLayoutManager(gridLayoutManager);


        binding.recyclerView.setAdapter(addressAdapter);
        addressAdapter.SetOnItemClickListener(
                (view, itemPosition, model) -> {

                });

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