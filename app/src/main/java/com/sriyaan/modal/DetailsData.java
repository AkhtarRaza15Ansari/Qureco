package com.sriyaan.modal;

/**
 * Created by Akhtar on 25-08-2016.
 */
public class DetailsData {

    public String hdo_oid;
    public String hl_oid;
    public String hcp_user_oid;
    public String hcp_cat_oid;

    public String offer_caption;
    public String deal_image;
    public String from_date;
    public String to_date;

    public String offer_flat;
    public String offer_discount;
    public String description;
    public String terms_condition;
    public String location_name;
    public String city;
    public String state;
    public String geo_lat;
    public String geo_longi;
    public String location_contacts;
    public String final_rating;
    public String no_of_followers;
    public String no_of_likes;

    public DetailsData(String hdo_oid, String hl_oid, String hcp_user_oid, String hcp_cat_oid, String offer_caption,
                       String deal_image, String from_date, String to_date, String offer_flat, String offer_discount,
                       String description, String terms_condition, String location_name, String city,
                       String state, String geo_lat,String geo_longi, String location_contacts,
                       String final_rating,String no_of_followers, String no_of_likes)
    {
        this.hdo_oid = hdo_oid;
        this.hl_oid = hl_oid;
        this.hcp_user_oid = hcp_user_oid;
        this.hcp_cat_oid = hcp_cat_oid;
        this.offer_caption = offer_caption;
        this.deal_image = deal_image;
        this.from_date = from_date;
        this.to_date = to_date;
        this.offer_flat = offer_flat;
        this.offer_discount = offer_discount;
        this.description = description;
        this.terms_condition = terms_condition;
        this.location_name = location_name;
        this.city = city;
        this.state = state;
        this.geo_lat = geo_lat;
        this.geo_longi = geo_longi;
        this.location_contacts = location_contacts;
        this.final_rating = final_rating;

        this.no_of_followers = no_of_followers;
        this.no_of_likes = no_of_likes;
    }


    public String getHdOid(){
        return hdo_oid;
    }
    public void setHdOid(String hdo_oid){
        this.hdo_oid = hdo_oid;
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

    public String getOfferCaption(){
        return offer_caption;
    }
    public void setOfferCaption(String offer_caption){
        this.offer_caption = offer_caption;
    }

    public String getDealImage(){
        return deal_image;
    }
    public void setDealImage(String deal_image){
        this.deal_image = deal_image;
    }

    public String getFromDate(){
        return from_date;
    }
    public void setFromDate(String from_date){
        this.from_date = from_date;
    }

    public String getToDate(){
        return to_date;
    }
    public void setToDate(String to_date){
        this.to_date = to_date;
    }

    public String getOfferFlat(){
        return offer_flat;
    }
    public void setOfferFlat(String offer_flat){
        this.offer_flat = offer_flat;
    }

    public String getOfferDiscount(){
        return offer_discount;
    }
    public void setOfferDiscount(String offer_discount){
        this.offer_discount = offer_discount;
    }


    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description = description;
    }

    public String getTermsCondition(){
        return terms_condition;
    }
    public void setTermsCondition(String description){
        this.terms_condition = terms_condition;
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
        return geo_longi;
    }
    public void setGeoLong(String geo_longi){
        this.geo_longi = geo_longi;
    }

    public String getLocationContacts(){
        return location_contacts;
    }
    public void setLocationContacts(String location_contacts){
        this.location_contacts = location_contacts;
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
}
