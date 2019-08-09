package com.mage.ziplrdelivery.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NavigationMenuAdapter extends RecyclerView.Adapter<NavigationMenuAdapter.NavigationMenuHolder> {


    @NonNull
    @Override
    public NavigationMenuHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull NavigationMenuHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class NavigationMenuHolder extends RecyclerView.ViewHolder{
        public NavigationMenuHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
