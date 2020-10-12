package app.kurosaki.developer.vegidel.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;

import app.kurosaki.developer.vegidel.R;
import app.kurosaki.developer.vegidel.databinding.ItemProductBinding;
import app.kurosaki.developer.vegidel.model.ProductData;
import app.kurosaki.developer.vegidel.utils.SharedPref;

/**
 * A custom adapter to use with the RecyclerView widget.
 */
public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.SingleItemRowHolder> {

    private ArrayList<ProductData> itemsList;
    private Context mContext;
    private OnItemClickListener mItemClickListener;
    ItemProductBinding itemReviewlistBinding;
    SharedPref sp;

    public ProductsAdapter(Context context, ArrayList<ProductData> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
        sp = new SharedPref(mContext);
    }

    @NotNull
    @Override
    public SingleItemRowHolder onCreateViewHolder(@NotNull ViewGroup viewGroup, int i) {
        itemReviewlistBinding = DataBindingUtil.inflate(
                LayoutInflater.from(viewGroup.getContext()),
                R.layout.item_product, viewGroup, false);

        return new SingleItemRowHolder(itemReviewlistBinding);
    }

    @Override
    public void onBindViewHolder(@NotNull SingleItemRowHolder holder, int i) {

        ProductData singleItem = itemsList.get(i);
        holder.itemRowBinding.productimage.setText(singleItem.getName());
        holder.itemRowBinding.quantity.setText(String.format("%s", singleItem.getSelectedQuantity()));
        if (singleItem.getSelectedQuantity() == 0) {
            holder.itemRowBinding.subtract.setVisibility(View.INVISIBLE);
        } else {
            holder.itemRowBinding.subtract.setVisibility(View.VISIBLE);
        }
        if(singleItem.getType()==1)
        {
            holder.itemRowBinding.quante.setText(String.format(Locale.getDefault(),mContext.getString(R.string.Weightqquant), singleItem.getTotalQuantity()));
            holder.itemRowBinding.productprice.setText(String.format(Locale.getDefault(),mContext.getString(R.string.symbol5),singleItem.getPrice()));
        }
        else if(singleItem.getType()==2)
        {
            holder.itemRowBinding.quante.setText(String.format(Locale.getDefault(),mContext.getString(R.string.LiterQuant), singleItem.getTotalQuantity()));
            holder.itemRowBinding.productprice.setText(String.format(Locale.getDefault(),mContext.getString(R.string.symbol6),singleItem.getPrice()));
        }else if(singleItem.getType()==3)
        {
            holder.itemRowBinding.quante.setText(String.format(Locale.getDefault(),mContext.getString(R.string.DozenQuant), singleItem.getTotalQuantity()));
            holder.itemRowBinding.productprice.setText(String.format(Locale.getDefault(),mContext.getString(R.string.symbol7),singleItem.getPrice()));
        }

        Glide.with(mContext).load(singleItem.getImage()).placeholder(R.drawable.backgrounsplash).into(holder.itemRowBinding.imageview);
        holder.itemRowBinding.getRoot().setOnClickListener(v -> {
            mItemClickListener.onOptionClick(holder.itemRowBinding.getRoot(), i, singleItem);
        });

        AtomicReference<Float> c = new AtomicReference<>(singleItem.getSelectedQuantity());
        AtomicReference<Float> c1 = new AtomicReference<>(singleItem.getTotalQuantity());

        holder.itemRowBinding.add.setOnClickListener(v -> {
            if (c1.get() > 0) {
                singleItem.setTotalQuantity(c1.get()-1);
            } else {
                singleItem.setTotalQuantity(0);
            }
            singleItem.setSelectedQuantity(c.get()+1);
            mItemClickListener.onAdd(holder.itemRowBinding.getRoot(), i, singleItem);
        });

        holder.itemRowBinding.subtract.setOnClickListener(v -> {
            singleItem.setTotalQuantity(c1.get()+1);
            if (c.get() > 0) {
                singleItem.setSelectedQuantity(c.get()-1);
            } else {
                singleItem.setSelectedQuantity(0);
            }
            mItemClickListener.onRemoved(holder.itemRowBinding.getRoot(), i, singleItem);
        });

    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface OnItemClickListener {
        void onOptionClick(View view, int itemPosition, ProductData model);
        void onAdd(View view, int itemPosition, ProductData model);
        void onRemoved(View view, int itemPosition, ProductData model);
    }

    public static class SingleItemRowHolder extends RecyclerView.ViewHolder {


        ItemProductBinding itemRowBinding;

        public SingleItemRowHolder(@NotNull ItemProductBinding view) {
            super(view.getRoot());
            this.itemRowBinding = view;
        }

    }


}

