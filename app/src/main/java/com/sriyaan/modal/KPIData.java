package com.sriyaan.modal;

/**
 * Created by Akhtar on 25-08-2016.
 */
public class KPIData {
    public String hcp_rating_kpi_id;
    public String hcp_rating_kpi_title;

    public KPIData(String hcp_rating_kpi_id, String hcp_rating_kpi_title)
    {
        this.hcp_rating_kpi_id = hcp_rating_kpi_id;
        this.hcp_rating_kpi_title = hcp_rating_kpi_title;
    }
    public String getHCPRatingID(){
        return hcp_rating_kpi_id;
    }
    public void setHCPRatingID(String hcp_rating_kpi_id){
        this.hcp_rating_kpi_id = hcp_rating_kpi_id;
    }

    public String getHCPRatingTitle(){
        return hcp_rating_kpi_title;
    }
    public void setHCPRatingTitle(String hcp_rating_kpi_title){
        this.hcp_rating_kpi_title = hcp_rating_kpi_title;
    }
}
