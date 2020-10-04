package app.kurosaki.developer.vegidel.adapters;


import android.content.Context;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

import app.kurosaki.developer.vegidel.R;
import app.kurosaki.developer.vegidel.databinding.ItemCartitemBinding;
import app.kurosaki.developer.vegidel.model.CartData;
import app.kurosaki.developer.vegidel.utils.SharedPref;

/**
 * A custom adapter to use with the RecyclerView widget.
 */
public class CartAdapter extends RecyclerView.Adapter<CartAdapter.SingleItemRowHolder> {

    private ArrayList<CartData> itemsList;
    private Context mContext;
    private OnItemClickListener mItemClickListener;
    ItemCartitemBinding itemCartitemBinding;
    SharedPref sp;

    public CartAdapter(Context context, ArrayList<CartData> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
        sp = new SharedPref(mContext);
    }

    @NotNull
    @Override
    public SingleItemRowHolder onCreateViewHolder(@NotNull ViewGroup viewGroup, int i) {
        itemCartitemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(viewGroup.getContext()),
                R.layout.item_cartitem, viewGroup, false);

        return new SingleItemRowHolder(itemCartitemBinding);
    }

    @Override
    public void onBindViewHolder(@NotNull SingleItemRowHolder holder, int i) {

        CartData singleItem = itemsList.get(i);
        holder.itemRowBinding.productimage.setText(singleItem.getProductData().getName());
        holder.itemRowBinding.quantity.setText(String.format(Locale.getDefault(), "%d", singleItem.getQuantity()));
        if (singleItem.getQuantity() == 0) {
            holder.itemRowBinding.subtract.setVisibility(View.INVISIBLE);
        } else {
            holder.itemRowBinding.subtract.setVisibility(View.VISIBLE);
        }
        String priceper=String.format(Locale.getDefault(), mContext.getString(R.string.symbol1), singleItem.getProductData().getVariants().get(singleItem.getPos()).getPrice(),singleItem.getProductData().getVariants().get(singleItem.getPos()).getName());
        String totalprice=String.format(Locale.getDefault(), mContext.getString(R.string.symbol2), (Integer.parseInt(singleItem.getProductData().getVariants().get(singleItem.getPos()).getPrice())*singleItem.getQuantity()));

        SpannableStringBuilder stringBuilder = new SpannableStringBuilder();
        SpannableString spannableString = new SpannableString(priceper);
        SpannableString spannableString1 = new SpannableString(totalprice);
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(mContext.getColor(R.color.Merlot));
        spannableString1.setSpan(foregroundColorSpan, 0, totalprice.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        stringBuilder.append(spannableString);
        stringBuilder.append(spannableString1);

        holder.itemRowBinding.productprice.setText(stringBuilder);

        Glide.with(mContext).load(singleItem.getProductData().getImage()).placeholder(R.drawable.backgrounsplash).into(holder.itemRowBinding.imageview);
        holder.itemRowBinding.getRoot().setOnClickListener(v -> {
            mItemClickListener.onOptionClick(holder.itemRowBinding.getRoot(), i, singleItem);
        });

        holder.itemRowBinding.cancel.setOnClickListener(c -> {
            mItemClickListener.onDelete(holder.itemRowBinding.getRoot(), i, singleItem);
        });

        AtomicInteger c = new AtomicInteger(singleItem.getQuantity());

        holder.itemRowBinding.add.setOnClickListener(v -> {
            singleItem.setQuantity(c.incrementAndGet());
            mItemClickListener.onAdd(holder.itemRowBinding.getRoot(), i, singleItem);
        });

        holder.itemRowBinding.subtract.setOnClickListener(v -> {
            if (c.intValue() > 0) {
                singleItem.setQuantity(c.decrementAndGet());
            } else {
                singleItem.setQuantity(0);
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
        void onOptionClick(View view, int itemPosition, CartData model);

        void onDelete(View view, int itemPosition, CartData model);

        void onAdd(View view, int itemPosition, CartData model);

        void onRemoved(View view, int itemPosition, CartData model);
    }

    public static class SingleItemRowHolder extends RecyclerView.ViewHolder {


        ItemCartitemBinding itemRowBinding;

        public SingleItemRowHolder(@NotNull ItemCartitemBinding view) {
            super(view.getRoot());
            this.itemRowBinding = view;
        }

    }


}

