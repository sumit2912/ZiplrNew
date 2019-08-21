package com.mage.ziplrdelivery.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.mage.ziplrdelivery.databinding.RawSettingsMenuAdapterItemBinding;
import com.mage.ziplrdelivery.uc.CustomTextView;

import java.util.List;

public class SettingsMenuAdapter extends RecyclerView.Adapter<SettingsMenuAdapter.SettingsMenuHolder> {

    private List<String> settingMenuList;
    private View.OnClickListener onClickListener;

    public SettingsMenuAdapter(List<String> settingMenuList, View.OnClickListener onClickListener) {
        this.settingMenuList = settingMenuList;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public SettingsMenuHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RawSettingsMenuAdapterItemBinding binding = RawSettingsMenuAdapterItemBinding.inflate(inflater,parent,false);
        return new SettingsMenuHolder(binding.getRoot(),binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SettingsMenuHolder holder, int position) {
        holder.binding.tvSettingItem.setText(settingMenuList.get(position));
        holder.binding.clMenuItem.setTag(position);
        holder.binding.clMenuItem.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        return settingMenuList.size();
    }

    public class SettingsMenuHolder extends RecyclerView.ViewHolder {
        RawSettingsMenuAdapterItemBinding binding;
        ConstraintLayout clMenuItem;
        CustomTextView tvSettingItem;
        public SettingsMenuHolder(@NonNull View itemView, RawSettingsMenuAdapterItemBinding binding) {
            super(itemView);
            this.binding = binding;
            this.tvSettingItem = this.binding.tvSettingItem;
            this.clMenuItem = this.binding.clMenuItem;
        }
    }
}
