package com.mage.ziplrdelivery.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.mage.ziplrdelivery.databinding.RawParcelAdapterItemBinding;
import com.mage.ziplrdelivery.model.param.ParcelBean;
import com.mage.ziplrdelivery.uc.CustomTextView;

import java.util.List;

public class ParcelAdapter extends RecyclerView.Adapter<ParcelAdapter.ParcelHolder> {

    private List<ParcelBean> parcelList;
    private ParcelClickListener parcelClickListener;

    public ParcelAdapter(List<ParcelBean> parcelList, ParcelClickListener parcelClickListener) {
        this.parcelList = parcelList;
        this.parcelClickListener = parcelClickListener;
    }

    @NonNull
    @Override
    public ParcelHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RawParcelAdapterItemBinding binding = RawParcelAdapterItemBinding.inflate(inflater,parent,false);
        return new ParcelHolder(binding.getRoot(),binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ParcelHolder holder, int position) {
        holder.ivParcel.setImageResource(parcelList.get(position).getDrawable());
        holder.tvParcelType.setText(parcelList.get(position).getParcelType());
        holder.tvWeightRange.setText(parcelList.get(position).getParcelWeightRange());
        holder.tvQuantity.setText(""+parcelList.get(position).getParcelQuantity());
    }

    @Override
    public int getItemCount() {
        return parcelList.size();
    }

    public List<ParcelBean> getParcelList(){
        return this.parcelList;
    }

    public class ParcelHolder extends RecyclerView.ViewHolder{
        public RawParcelAdapterItemBinding binding;
        public AppCompatImageView ivParcel,ivMinus, ivPlus;
        public CustomTextView tvParcelType, tvWeightRange, tvQuantity;
        public ParcelHolder(@NonNull View itemView, RawParcelAdapterItemBinding binding) {
            super(itemView);
            this.binding = binding;
            this.ivParcel = this.binding.ivParcel;
            this.tvParcelType = this.binding.tvParcelType;
            this.tvWeightRange = this.binding.tvWeightRange;
            this.tvQuantity = this.binding.tvQuantity;
            this.ivMinus = this.binding.ivMinus;
            this.ivPlus = this.binding.ivPlus;
            this.ivMinus.setOnClickListener(view -> parcelClickListener.onParcelClicked(getAdapterPosition(),ivMinus));
            this.ivPlus.setOnClickListener(view -> parcelClickListener.onParcelClicked(getAdapterPosition(),ivPlus));

        }
    }

    public interface ParcelClickListener{
        void onParcelClicked(int pos, View view);
    }
}
