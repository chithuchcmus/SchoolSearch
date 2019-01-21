package com.example.thuc.miniproject;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

public class favouriteLocation extends AppCompatActivity implements SearchAdapter.OnListItemClickListener {


    TextView textViewSdt;
    TextView textViewWebSite;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    List<MyPlace> favouritePlace;
    MyDataBase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_location);
        SearchAdapter adapter;

        //init View
        recyclerView = (RecyclerView)findViewById(R.id.recycler_search);
        layoutManager = new LinearLayoutManager(this);
        TextView textViewSdt = (TextView)findViewById(R.id.sdt);
        TextView textViewWebSite = (TextView)findViewById(R.id.Website);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize( true );
        favouritePlace = new ArrayList<MyPlace>();
        //init DB
        database = MyDataBase.getInstance(this);

        getFavouritePlaceFromDataBase();
        adapter = new SearchAdapter( this,favouritePlace );
        recyclerView.setAdapter( adapter );
    }

    private void getFavouritePlaceFromDataBase() {
        List<MyPlace> myPlaces = database.getAllLocation();
        for(MyPlace myPlace : myPlaces)
        {
            if(myPlace.getImage() > 0)
                favouritePlace.add(myPlace);
        }
    }

    @Override
    public void onListItemClickListener(MyPlace myPlace) {
        Intent intent =  new Intent(this, callAndGoWebsite.class);
        intent.putExtra("data",myPlace);
        startActivityForResult(intent,1);
    }
}
