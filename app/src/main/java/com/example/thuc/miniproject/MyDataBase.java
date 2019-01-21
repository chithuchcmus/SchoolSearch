package com.example.thuc.miniproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

import java.util.ArrayList;
import java.util.List;

public class MyDataBase extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "MYPLACE.db";
    private static final String TABLE_NAME = "PLACES";
    private static final String KEY_NAME = "name";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_ADDRES = "addres";
    private static final String KEY_LAT = "latitude";
    private static final String KEY_LONG= "longitude";
    private static final String KEY_WEB= "website";
    private static final String KEY_EMAIL= "email";
    private static final String KEY_sdt= "sdt";
    private static final String KEY_IDIMAGE= "image";
    private static MyDataBase sInstance;
    public static synchronized MyDataBase getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new MyDataBase(context.getApplicationContext());
        }
        return sInstance;
    }

    private static final String[] COLUMNS = {  KEY_NAME,KEY_DESCRIPTION,KEY_ADDRES, KEY_LAT,
            KEY_LONG ,KEY_WEB,KEY_EMAIL,KEY_sdt,KEY_IDIMAGE};
    public MyDataBase(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);

        List<MyPlace> myPlacesMain =JsonDataBase.getMyPlacesFromJSON(context);
        for(MyPlace myPlace : myPlacesMain)
        {
            this.addMyPlaceToMyMap(myPlace);
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATION_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                KEY_NAME + " TEXT, "  + KEY_DESCRIPTION + " TEXT, " + KEY_ADDRES + " TEXT, "+ KEY_LAT + " REAL, " + KEY_LONG + " REAL, " + KEY_WEB + " TEXT, "  + KEY_EMAIL + " TEXT, "
                + KEY_sdt +" TEXT, " +  KEY_IDIMAGE + " INTERGER)";

        db.execSQL(CREATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }
    public List<String> getNameAllLocation()
    {
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect={KEY_NAME};
        qb.setTables(TABLE_NAME);

        Cursor cursor = qb.query(db,sqlSelect,null,null,null,null,null);
        List<String> result =  new ArrayList<String>();

        while (cursor.moveToNext())
        {
           result.add(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
        }
        db.close();
        return result;
    }
    public   List<MyPlace> getLocationByName(String name) {
        SQLiteDatabase db = getReadableDatabase();

        String selection = KEY_NAME + " like ? or "
                + KEY_DESCRIPTION + " like ? or "
                + KEY_ADDRES + " like ?";
        String[] selectionArgs= {"%" +name + "%"
                ,"%" +name + "%"
                ,"%" +name + "%"};

        Cursor cursor = db.query(
                TABLE_NAME, // a. table
                COLUMNS, // b. column names
                selection,
                selectionArgs,
                null,
                null,
                null);
        List<MyPlace> myPlaces = new ArrayList<MyPlace>();
        MyPlace myPlace=null;

        while (cursor.moveToNext())
        {
            myPlace = new MyPlace();
            myPlace.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            myPlace.setDescription(cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION)));
            myPlace.setAddres(cursor.getString(cursor.getColumnIndex(KEY_ADDRES)));
            myPlace.setLatitude(cursor.getFloat(cursor.getColumnIndex(KEY_LAT)));
            myPlace.setLongitude(cursor.getFloat(cursor.getColumnIndex(KEY_LONG)));
            myPlace.setWebsite(cursor.getString(cursor.getColumnIndex(KEY_WEB)));
            myPlace.setEmail(cursor.getString(cursor.getColumnIndex(KEY_EMAIL)));
            myPlace.setSdt(cursor.getString(cursor.getColumnIndex(KEY_sdt)));
            myPlace.setImage(cursor.getInt(cursor.getColumnIndex(KEY_IDIMAGE)));
            myPlaces.add(myPlace);
        }
        db.close();
        return myPlaces;
    }

    void addMyPlaceToMyMap(MyPlace myPlace)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_NAME, myPlace.getName());
        values.put(KEY_DESCRIPTION, myPlace.getDescription());
        values.put(KEY_ADDRES, myPlace.getAddres());
        values.put(KEY_LAT, myPlace.getLatitude());
        values.put(KEY_LONG, myPlace.getLongitude());
        values.put(KEY_WEB, myPlace.getWebsite());
        values.put(KEY_EMAIL, myPlace.getEmail());
        values.put(KEY_sdt, myPlace.getSdt());
        values.put(KEY_IDIMAGE, myPlace.getImage());

        // insert
        db.insert(TABLE_NAME,null, values);
        db.close();
    }
    public List<MyPlace> getAllLocation()
    {
        List<MyPlace> myPlaces =  new ArrayList<MyPlace>();
        String query = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        MyPlace myPlace= null;
        while(cursor.moveToNext())
        {
            myPlace = new MyPlace();
            myPlace.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            myPlace.setLatitude(cursor.getFloat(cursor.getColumnIndex(KEY_LAT)));
            myPlace.setAddres(cursor.getString(cursor.getColumnIndex(KEY_ADDRES)));
            myPlace.setLongitude(cursor.getFloat(cursor.getColumnIndex(KEY_LONG)));
            myPlace.setWebsite(cursor.getString(cursor.getColumnIndex(KEY_WEB)));
            myPlace.setEmail(cursor.getString(cursor.getColumnIndex(KEY_EMAIL)));
            myPlace.setSdt(cursor.getString(cursor.getColumnIndex(KEY_sdt)));
            myPlace.setImage((cursor.getInt(cursor.getColumnIndex(KEY_IDIMAGE))));
            myPlaces.add(myPlace);
        };

        return myPlaces;
    }
    public void updateFavourite(MyPlace myPlace, int newFavorite)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        //new value
        ContentValues values = new ContentValues();
        values.put(KEY_IDIMAGE,newFavorite);
        // Which row to update, based on the title
        db.update(TABLE_NAME,values, KEY_NAME + " like ?",new String[] { myPlace.getName() });
        db.close();
    }



}