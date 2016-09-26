package com.sriyaan.modal;

/**
 * Created by Akhtar on 25-08-2016.
 */
public class ReviewData {
    public String hs_oid;
    public String rating;
    public String rating_review;
    public String service_name;
    public String location_name;
    public String city;
    public String state;
    public String geo_lat;
    public String geo_long;
    public String mobile_no;

    public ReviewData(String hs_oid, String rating, String rating_review, String service_name, String location_name, String city, String state, String geo_lat,
                      String geo_long, String mobile_no) {
        this.hs_oid = hs_oid;
        this.rating = rating;
        this.rating_review = rating_review;
        this.service_name = service_name;
        this.location_name = location_name;
        this.city = city;
        this.state = state;
        this.geo_lat = geo_lat;
        this.geo_long = geo_long;
        this.mobile_no = mobile_no;

    }


    public String getHsOid() {
        return hs_oid;
    }

    public void setHsOid(String hs_oid) {
        this.hs_oid = hs_oid;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getRatingReview() {
        return rating_review;
    }

    public void setRatingReview(String rating_review) {
        this.rating_review = rating_review;
    }

    public String getServiceName() {
        return service_name;
    }

    public void setServiceName(String service_name) {
        this.service_name = service_name;
    }

    public String getLocationName() {
        return location_name;
    }

    public void setLocationName(String location_name) {
        this.location_name = location_name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getGeoLat() {
        return geo_lat;
    }

    public void setGeoLat(String geo_lat) {
        this.geo_lat = geo_lat;
    }

    public String getGeoLong() {
        return geo_long;
    }

    public void setGeoLong(String geo_long) {
        this.geo_long = geo_long;
    }

    public String getMobileNo() {
        return mobile_no;
    }

    public void setMobileNo(String mobile_no) {
        this.mobile_no = mobile_no;
    }
}
