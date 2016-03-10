package edu.uw.amaralmunnthorsteinson.porcelain;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Hiram on 3/8/2016.
 */
// It's a toilet with a GUID, yah style
public class Place {
    public String name;
    public LatLng latLng;
    public Long rating;
    public String descr;
    public Boolean isHandicapAccessible;
    public Boolean isGenderNeutral;
    public Boolean isFamilyFriendly;
    public String review;
    public String guid;

    public Place(String n, LatLng ll, Long r, String d, Boolean isFamilyFriendly, Boolean isGenderNeutral, Boolean isHandicapAccessible, String Review, String guid){
        this.name = n;
        this.latLng = ll;
        this.rating = r;
        this.descr = d;
        this.review = "  ";
        this.isFamilyFriendly = isFamilyFriendly;
        this.isGenderNeutral = isGenderNeutral;
        this.isHandicapAccessible = isHandicapAccessible;
        this.guid = guid;
    }
}
