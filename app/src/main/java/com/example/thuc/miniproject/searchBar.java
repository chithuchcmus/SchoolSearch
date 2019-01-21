package com.example.thuc.miniproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;

import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

public class searchBar extends AppCompatActivity implements SearchAdapter.OnListItemClickListener {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    SearchAdapter adapter;

    MyDataBase database;
    MaterialSearchBar materialSearchBar;
    List<String> suggestListName = new ArrayList<>();
    private List<MyPlace> myPlacesMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_bar);

        //init View
        recyclerView = (RecyclerView)findViewById(R.id.recycler_search);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize( true );

        materialSearchBar = (MaterialSearchBar)findViewById( R.id.search_bar );

        //init DB

        database = MyDataBase.getInstance(this);
        //createDataFromJson();
        //setup searchbar
        materialSearchBar.setHint( "Search" );
        materialSearchBar.setCardViewElevation( 10 );

        loadSuggestList();

        materialSearchBar.addTextChangeListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<String> suggest = new ArrayList<>(  );
                for(String search:suggestListName)
                {
                    if(search.toLowerCase().contains( materialSearchBar.getText().toLowerCase() ))
                        suggest.add(search);
                }
                materialSearchBar.setLastSuggestions( suggest );
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        } );
        materialSearchBar.setOnSearchActionListener( new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if(!enabled)
                {
                    adapter = new SearchAdapter(getBaseContext(),database.getAllLocation());
                    recyclerView.setAdapter( adapter );
                }
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                startSearch( text.toString() );
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        } );

        //init adapter default set all result
        adapter = new SearchAdapter( this,database.getAllLocation() );
        recyclerView.setAdapter( adapter );
    }
    private void startSearch(String text)
    {
        adapter = new SearchAdapter( this,database.getLocationByName(text ) );
        recyclerView.setAdapter( adapter );
    }

    private void loadSuggestList() {
        suggestListName = database.getNameAllLocation();
        materialSearchBar.setLastSuggestions(suggestListName);
    }

    @Override
    public void onListItemClickListener(MyPlace myPlace) {
        try
        {
            Intent intent =  new Intent(this, MapsActivity.class);
            intent.putExtra("data",myPlace);
            startActivityForResult(intent,1);
        }catch (Exception e)
        {
            return;
        }

    }
}
