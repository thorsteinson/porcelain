package edu.uw.amaralmunnthorsteinson.porcelain;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Hiram on 3/8/2016.
 */
public class Place {
    private String name;
    private LatLng loc;
    private Long rating;
    private String descr;
    private String guid;

    public Place(String n, LatLng ll, Long r, String d, String g){
        this.name = n;
        this.loc = ll;
        this.rating = r;
        this.descr = d;
        this.guid = g;
    }

    public String getName(){
        return this.name;
    }

    public LatLng getLatLng(){
        return this.loc;
    }

    public Long getRating(){
        return this.rating;
    }

    public String getDescr(){
        return this.descr;
    }

    public String getGuid() { return this.guid; }

}
