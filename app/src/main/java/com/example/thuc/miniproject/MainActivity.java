package com.example.thuc.miniproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;

import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ImageView imageViewSearch;
    ImageView imageViewFavourite;
    public static List<MyPlace> myPlacesMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set base data for program
        deleteDatabase(MyDataBase.DATABASE_NAME);
        myPlacesMain= MyDataBase.getInstance(this).getAllLocation();

        imageViewSearch = (ImageView)findViewById(R.id.imageview_search);
        imageViewFavourite = (ImageView)findViewById(R.id.imageview_favourite);
        imageViewSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSearch = new Intent(imageViewSearch.getContext(),searchBar.class);
                startActivity(intentSearch);
            }
        });

        imageViewFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSearch = new Intent(imageViewFavourite.getContext(),favouriteLocation.class);
                startActivity(intentSearch);
            }
        });
    }
   /* private void createDataFromJson()
    {
        myPlacesMain =JsonDataBase.getMyPlacesFromJSON(this);
        for(MyPlace myPlace : myPlacesMain)
        {
            db.addMyPlaceToMyMap(myPlace);
        }
    }*/
}
