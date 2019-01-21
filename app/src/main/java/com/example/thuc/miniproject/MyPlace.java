package com.example.thuc.miniproject;

import java.io.Serializable;

public class MyPlace implements Serializable {
    private String name;
    private String description;
    private String addres;
    private float longitude;
    private float latitude;
    private String website;
    private String email;
    private String sdt;
    private int image;

    public MyPlace()
    {
        name =null;
        description =null;
        addres =null;
        longitude=0;
        latitude=0;
        image=0;
        website=null;
        email=null;
        sdt=null;

    }
    public MyPlace(String name, String des, String addres)
    {
        this.name=name;
        this.description=des;
        this.addres=addres;
        addres =null;
        longitude=0;
        latitude=0;
        image=0;
        website=null;
        email=null;
        sdt=null;
    }
    public MyPlace(String ten, String mota, String diachi, float longitude, float latitude, int image, String website, String email, String sdt)
    {

        this.name =ten;
        this.description =mota;
        this.addres =diachi;
        this.longitude=longitude;
        this.latitude=latitude;
        this.image=image;
        this.website=website;
        this.email=email;
        this.sdt=sdt;
    }
    public  void setName(String name)
    {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAddres(String addres) {
        this.addres = addres;
    }

    public String getAddres() {
        return addres;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getImage() {
        return image;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getSdt() {
        return sdt;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getWebsite() {
        return website;
    }
}
