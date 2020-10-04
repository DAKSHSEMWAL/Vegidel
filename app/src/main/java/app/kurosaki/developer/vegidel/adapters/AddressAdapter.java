package app.kurosaki.developer.vegidel.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;

import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import app.kurosaki.developer.vegidel.R;
import app.kurosaki.developer.vegidel.model.CartData;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {
    public int mSelectedItem = -1;
    public ArrayList<String> mItems;
    private Context mContext;
    private OnItemClickListener mItemClickListener;

    public AddressAdapter(Context context, ArrayList<String> mItems) {
        mContext = context;
        this.mItems = mItems;
    }

    @Override
    public void onBindViewHolder(@NotNull AddressAdapter.ViewHolder viewHolder, final int i) {
        String string = mItems.get(i);
        viewHolder.mRadio.setText(string);
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
        final View view = inflater.inflate(R.layout.address_item, viewGroup, false);
        return new ViewHolder(view);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public RadioButton mRadio;
        public ImageView imageView;

        public ViewHolder(final View inflate) {
            super(inflate);
            mRadio = inflate.findViewById(R.id.radio);
            imageView = inflate.findViewById(R.id.cancel);
            View.OnClickListener clickListener = v -> {
                mSelectedItem = getAdapterPosition();
                mItemClickListener.onOptionClick(inflate,mSelectedItem,mItems.get(mSelectedItem));
                notifyDataSetChanged();
            };
            imageView.setOnClickListener(v-> mItemClickListener.onDelete(inflate,mSelectedItem,mItems.get(mSelectedItem)));
            itemView.setOnClickListener(clickListener);
            mRadio.setOnClickListener(clickListener);
        }
    }

    public interface OnItemClickListener {
        void onOptionClick(View view, int itemPosition, String model);
        void onDelete(View view, int itemPosition, String model);
    }
}