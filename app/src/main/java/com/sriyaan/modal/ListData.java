package com.sriyaan.modal;

import java.util.List;

/**
 * Created by Akhtar on 25-08-2016.
 */
public class ListData {
    public String distance;
    public String hs_oid;
    public String hl_oid;
    public String hcp_user_oid;
    public String hcp_cat_oid;
    public String service_name;
    public String location_name;
    public String city;
    public String state;
    public String geo_lat;
    public String geo_long;
    public String photo_path;
    public String charges;
    public String final_rating;
    public String no_of_followers;
    public String no_of_likes;
    public boolean ischecked;

    public ListData(String distance,String hs_oid,String hl_oid,String hcp_user_oid,String hcp_cat_oid,
                    String service_name,String location_name,String city,String state,String geo_lat,
                    String geo_long,String photo_path,String charges,String final_rating,
                    String no_of_followers,String no_of_likes,boolean ischecked)
    {
        this.distance = distance;
        this.hs_oid = hs_oid;
        this.hl_oid = hl_oid;
        this.hcp_user_oid = hcp_user_oid;
        this.hcp_cat_oid = hcp_cat_oid;
        this.service_name = service_name;
        this.location_name = location_name;
        this.city = city;
        this.state = state;
        this.geo_lat = geo_lat;
        this.geo_long = geo_long;
        this.photo_path = photo_path;
        this.charges = charges;
        this.final_rating = final_rating;
        this.no_of_followers = no_of_followers;
        this.no_of_likes = no_of_likes;
        this.ischecked = ischecked;
    }
    public String getDistance(){
        return distance;
    }
    public void setDistance(String distance){
        this.distance = distance;
    }

    public String getHsOid(){
        return hs_oid;
    }
    public void setHsOid(String hs_oid){
        this.hs_oid = hs_oid;
    }

    public String getHlOid(){
        return hl_oid;
    }
    public void setHlOid(String hl_oid){
        this.hl_oid = hl_oid;
    }

    public String getHcpUserOid(){
        return hcp_user_oid;
    }
    public void setHcpUserOid(String hcp_user_oid){
        this.hcp_user_oid = hcp_user_oid;
    }

    public String getHcpCatId(){
        return hcp_cat_oid;
    }
    public void setHcpCatId(String hcp_cat_oid){
        this.hcp_cat_oid = hcp_cat_oid;
    }

    public String getServiceName(){
        return service_name;
    }
    public void setServiceName(String service_name){
        this.service_name = service_name;
    }

    public String getLocationName(){
        return location_name;
    }
    public void setLocationName(String location_name){
        this.location_name = location_name;
    }

    public String getCity(){
        return city;
    }
    public void setCity(String city){
        this.city = city;
    }

    public String getState(){
        return state;
    }
    public void setState(String state){
        this.state = state;
    }

    public String getGeoLat(){
        return geo_lat;
    }
    public void setGeoLat(String geo_lat){
        this.geo_lat = geo_lat;
    }

    public String getGeoLong(){
        return geo_long;
    }
    public void setGeoLong(String geo_long){
        this.geo_long = geo_long;
    }

    public String getPhotoPath(){
        return photo_path;
    }
    public void setPhotoPath(String photo_path){
        this.photo_path = photo_path;
    }

    public String getCharges(){
        return charges;
    }
    public void setCharges(String charges){
        this.charges = charges;
    }

    public String getFinalRating(){
        return final_rating;
    }
    public void setFinalRating(String final_rating){
        this.final_rating = final_rating;
    }

    public String getNoFollowers(){
        return no_of_followers;
    }
    public void setNoFollowers(String no_of_followers){
        this.no_of_followers = no_of_followers;
    }

    public String getLikes(){
        return no_of_likes;
    }
    public void setNoLikes(String no_of_likes){
        this.no_of_likes = no_of_likes;
    }

    public boolean getIsChecked(){
        return ischecked;
    }
    public void setIsChecked(boolean ischecked){
        this.ischecked = ischecked;
    }
}
