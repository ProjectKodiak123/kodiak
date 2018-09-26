package com.bhanuka.kodiak;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterForRecycleView extends RecyclerView.Adapter<AdapterForRecycleView.ViewHolder> {

    private ArrayList<BrandItemsOnRecycleView> brandItemsOnRecycleViewArrayList;

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public CircleImageView circleImageView;
        public Button subscribsBtn;
        public Button viewOfferBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.brandImageCardView);
            //subscribsBtn = itemView.findViewById(R.id.subscribedBtnCardView);
            //viewOfferBtn = itemView.findViewById(R.id.viewOdderBtnCardView);
        }
    }

    public AdapterForRecycleView(ArrayList<BrandItemsOnRecycleView> brandItemsOnRecycleViewsArrayList){
        this.brandItemsOnRecycleViewArrayList=brandItemsOnRecycleViewsArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_for_brands,parent,false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BrandItemsOnRecycleView currentItem = brandItemsOnRecycleViewArrayList.get(position);
        holder.circleImageView.setImageResource(currentItem.getBrandImage());
    }

    @Override
    public int getItemCount() {
        return brandItemsOnRecycleViewArrayList.size();
    }


}
