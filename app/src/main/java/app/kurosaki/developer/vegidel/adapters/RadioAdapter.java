package app.kurosaki.developer.vegidel.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import app.kurosaki.developer.vegidel.R;
import app.kurosaki.developer.vegidel.model.Variant;

public class RadioAdapter extends RecyclerView.Adapter<RadioAdapter.ViewHolder> {
    public int mSelectedItem = -1;
    public ArrayList<Variant> mItems;
    private Context mContext;
    private OnItemClickListener mItemClickListener;

    public RadioAdapter(Context context, ArrayList<Variant> mItems) {
        mContext = context;
        this.mItems = mItems;
    }

    @Override
    public void onBindViewHolder(@NotNull RadioAdapter.ViewHolder viewHolder, final int i) {
        Variant variant = mItems.get(i);
        viewHolder.mRadio.setText(variant.getName());
        viewHolder.mRadio.setChecked(i == mSelectedItem);
    }

    @Override
    public int getItemCount() {
        return (null != mItems ? mItems.size() : 0);
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        final View view = inflater.inflate(R.layout.view_item, viewGroup, false);
        return new ViewHolder(view);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public RadioButton mRadio;

        public ViewHolder(final View inflate) {
            super(inflate);
            mRadio = inflate.findViewById(R.id.radio);
            View.OnClickListener clickListener = v -> {
                mSelectedItem = getAdapterPosition();
                mItemClickListener.onOptionClick(inflate,mSelectedItem,mItems.get(mSelectedItem));
                notifyDataSetChanged();
            };
            itemView.setOnClickListener(clickListener);
            mRadio.setOnClickListener(clickListener);
        }
    }
    public interface OnItemClickListener {
        void onOptionClick(View view, int itemPosition, Variant model);
    }
}