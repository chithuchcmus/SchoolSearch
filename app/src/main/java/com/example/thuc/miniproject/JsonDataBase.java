package com.example.thuc.miniproject;
import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class JsonDataBase {

    public static List<MyPlace> getMyPlacesFromJSON(Context context) {
        List<MyPlace> myPlaceArrayList = new ArrayList<>();

        String stringJSON = null;
        //source https://stackoverflow.com/questions/19945411/android-java-how-can-i-parse-a-local-json-file-from-assets-folder-into-a-listvi
        try {
            InputStream inputStream = context.getAssets().open("data.json");
            int size = inputStream.available();
            // copy to buffer
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            stringJSON = new String(buffer, "UTF-8");
        } catch (Exception e) {
            Log.e("JSON", e.toString());
            // empty my places
            return myPlaceArrayList;
        }

        try {
            // get json object from full file
            JSONObject jsonObjectRoot = new JSONObject(stringJSON);
            // get myplaces array
            MyPlace myPlace;
            JSONArray jsonArray = jsonObjectRoot.getJSONArray("myPlace");
            for (int i = 0; i < jsonArray.length(); i++)
            {

                myPlace= new MyPlace();

                myPlace.setName(jsonArray.getJSONObject(i).getString("name"));
                myPlace.setDescription(jsonArray.getJSONObject(i).getString("descripstion"));
                myPlace.setAddres(jsonArray.getJSONObject(i).getString("addres"));
                myPlace.setLongitude(Float.parseFloat(jsonArray.getJSONObject(i).getString("longitude")));
                myPlace.setLatitude(Float.parseFloat(jsonArray.getJSONObject(i).getString("latitude")));
                myPlace.setWebsite(jsonArray.getJSONObject(i).getString("website"));
                myPlace.setEmail(jsonArray.getJSONObject(i).getString("email"));
                myPlace.setSdt(jsonArray.getJSONObject(i).getString("sdt"));
                myPlace.setImage(Integer.parseInt(jsonArray.getJSONObject(i).getString("image")));

                myPlaceArrayList.add(myPlace);
            }

        } catch (Exception e) {
            Log.e("JSON", e.toString());
        }
        return myPlaceArrayList;
    }
}
