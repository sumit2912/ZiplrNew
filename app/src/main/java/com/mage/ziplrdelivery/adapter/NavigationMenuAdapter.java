package com.mage.ziplrdelivery.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mage.ziplrdelivery.R;

import java.util.List;

public class NavigationMenuAdapter extends RecyclerView.Adapter<NavigationMenuAdapter.NavigationMenuHolder> {

    private List<String> menuNameList;

    public NavigationMenuAdapter(List<String> menuNameList) {
        this.menuNameList = menuNameList;
    }

    @NonNull
    @Override
    public NavigationMenuHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_navigation_menu_adapter,parent,false);
        return new NavigationMenuHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NavigationMenuHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return menuNameList.size();
    }

    public class NavigationMenuHolder extends RecyclerView.ViewHolder {

        public NavigationMenuHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
