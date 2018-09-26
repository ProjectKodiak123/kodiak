package com.bhanuka.kodiak;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class PopularBrands extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;//Responsible for alligning single items in the list
    ArrayList<BrandItemsOnRecycleView> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_brands);

        addItemsToList();

        recyclerView = (RecyclerView)findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new AdapterForRecycleView(items);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    public void addItemsToList(){
        items=new ArrayList<>();
        items.add(new BrandItemsOnRecycleView(R.drawable.kfcnew));
        items.add(new BrandItemsOnRecycleView(R.drawable.foodcity));
        items.add(new BrandItemsOnRecycleView(R.drawable.dominoslogo));
        items.add(new BrandItemsOnRecycleView(R.drawable.shoping));
        items.add(new BrandItemsOnRecycleView(R.drawable.kfcnew));
        items.add(new BrandItemsOnRecycleView(R.drawable.foodcity));
        items.add(new BrandItemsOnRecycleView(R.drawable.dominoslogo));
        items.add(new BrandItemsOnRecycleView(R.drawable.shoping));
        items.add(new BrandItemsOnRecycleView(R.drawable.kfcnew));
        items.add(new BrandItemsOnRecycleView(R.drawable.foodcity));
        items.add(new BrandItemsOnRecycleView(R.drawable.dominoslogo));
        items.add(new BrandItemsOnRecycleView(R.drawable.shoping));

    }
}
