package edu.uw.amaralmunnthorsteinson.porcelain;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Hiram on 3/8/2016.
 */
public class Place {
    private String name;
    private LatLng loc;
    private Double rating;
    private String descr;

    public Place(String n, LatLng ll, Double r, String d){
        this.name = n;
        this.loc = ll;
        this.rating = r;
        this.descr = d;
    }

    public String getName(){
        return this.name;
    }

    public LatLng getLatLng(){
        return this.loc;
    }

    public Double getRating(){
        return this.rating;
    }

    public String getDescr(){
        return this.descr;
    }

}
