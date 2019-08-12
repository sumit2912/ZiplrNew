package com.mage.ziplrdelivery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.recyclerview.widget.RecyclerView;

import com.mage.ziplrdelivery.databinding.RawNavigationMenuAdapterBinding;
import com.mage.ziplrdelivery.uc.CustomTextView;
import com.mage.ziplrdelivery.utils.Utils;
import com.mage.ziplrdelivery.viewmodelfactory.viewmodel.NavigationMenuViewModel;

import java.util.List;

public class NavigationMenuAdapter extends RecyclerView.Adapter<NavigationMenuAdapter.NavigationMenuHolder> {

    private Context context;
    private List<NavigationMenuViewModel.NavMenu> navMenuList;
    private View.OnClickListener onClickListener;

    public NavigationMenuAdapter(Context context, View.OnClickListener onClickListener, List<NavigationMenuViewModel.NavMenu> navMenuList) {
        this.context = context;
        this.onClickListener = onClickListener;
        this.navMenuList = navMenuList;
    }

    @NonNull
    @Override
    public NavigationMenuHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RawNavigationMenuAdapterBinding binding = RawNavigationMenuAdapterBinding.inflate(inflater, parent, false);
        return new NavigationMenuHolder(binding.getRoot(), binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NavigationMenuHolder holder, int position) {
        holder.binding.clMenuItem.setTag(position);
        holder.binding.clMenuItem.setOnClickListener(onClickListener);
        holder.binding.ivMenu.setImageResource(navMenuList.get(position).ItemDrawable.getValue());
        holder.binding.tvMenu.setText(navMenuList.get(position).ItemName.getValue());
    }

    @Override
    public int getItemCount() {
        return navMenuList.size();
    }

    @Override
    public void onViewAttachedToWindow(@NonNull NavigationMenuHolder holder) {
        super.onViewAttachedToWindow(holder);
        if (holder instanceof NavigationMenuHolder) {
            holder.markAttach();
        }
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull NavigationMenuHolder holder) {
        super.onViewDetachedFromWindow(holder);
        if (holder instanceof NavigationMenuHolder) {
            holder.markDetach();
        }
    }

    public class NavigationMenuHolder extends RecyclerView.ViewHolder implements LifecycleOwner {
        public LifecycleRegistry lifecycleRegistry = new LifecycleRegistry((LifecycleOwner) context);
        public RawNavigationMenuAdapterBinding binding;
        public ConstraintLayout clMenuItem;
        public AppCompatImageView ivMenu;
        public CustomTextView tvMenu;

        public NavigationMenuHolder(@NonNull View itemView, RawNavigationMenuAdapterBinding binding) {
            super(itemView);
            this.binding = binding;
            lifecycleRegistry.markState(Lifecycle.State.INITIALIZED);
            clMenuItem = this.binding.clMenuItem;
            ivMenu = this.binding.ivMenu;
            tvMenu = this.binding.tvMenu;
        }

        public void markAttach() {
            lifecycleRegistry.markState(Lifecycle.State.STARTED);
        }

        public void markDetach() {
            lifecycleRegistry.markState(Lifecycle.State.DESTROYED);
        }

        @NonNull
        @Override
        public Lifecycle getLifecycle() {
            return lifecycleRegistry;
        }
    }
}
