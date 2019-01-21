package com.example.thuc.miniproject;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class callAndGoWebsite extends AppCompatActivity {

    MyPlace myPlace;
    TextView textViewName;
    TextView textViewAddres;
    TextView textViewWeb;
    TextView textViewEmail;
    TextView textViewSdt;

    ImageView imageViewOfPlace;
    ImageView imageViewCall;
    ImageView imageViewweb;
    ImageView imageViewEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_and_go_website);


        textViewName = (TextView)findViewById(R.id.text_name_place);
        textViewAddres = (TextView)findViewById(R.id.text_adrress_place);
        textViewWeb = (TextView)findViewById(R.id.text_web_place);
        textViewEmail = (TextView)findViewById(R.id.text_email_place);
        textViewSdt = (TextView)findViewById(R.id.text_telephone_place);
        imageViewCall= (ImageView)findViewById(R.id.viewOfTelephone);
        imageViewweb= (ImageView)findViewById(R.id.viewOfWeb);
        imageViewOfPlace= (ImageView)findViewById(R.id.image_of_place);
        imageViewEmail = (ImageView)findViewById(R.id.viewOfEmail);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }
        myPlace= (MyPlace)extras.get("data");
        createData();

        imageViewweb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri webpage = Uri.parse(myPlace.getWebsite());
                Intent webIntent = new Intent(Intent.ACTION_VIEW,webpage);
                startActivity(webIntent);
            }
        });
        imageViewCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                String calltele = "tel:" + myPlace.getSdt();
                intent.setData(Uri.parse(calltele));
                startActivity(intent);
            }
        });
        imageViewEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("mailto:" + myPlace.getEmail())
                        .buildUpon()
                        .appendQueryParameter("subject", "Test For App")
                        .appendQueryParameter("body", "Sorry for Annoying")
                        .build();

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, uri);
                startActivity(Intent.createChooser(emailIntent, "Chooser Title"));
            }
        });
    }

    private void createData() {
        textViewName.setText(myPlace.getName());
        textViewAddres.setText(myPlace.getAddres());
        textViewWeb.setText(myPlace.getWebsite());
        textViewEmail.setText(myPlace.getEmail());
        textViewSdt.setText(myPlace.getSdt());
        switch (myPlace.getName())
        {
            case "Đại học Khoa Học Tự Nhiên":
                imageViewOfPlace.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.khtn));
                break;
            case "Đại Học Sư Phạm":
                imageViewOfPlace.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.supham));
                break;
            case "Đại Học Y Dược":
                imageViewOfPlace.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.yduoc));
                break;
            case "Đại Học Bách Khoa":
                imageViewOfPlace.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.bk));
                break;
            case "Đại Học Ngoại Thương":
                imageViewOfPlace.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.ngoaithuong));
                break;
        }
    }

}
