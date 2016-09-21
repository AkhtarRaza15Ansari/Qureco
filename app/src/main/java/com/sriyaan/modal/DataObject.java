package com.sriyaan.modal;

/**
 * Created by ansariakhtar on 18/09/16.
 */
public class DataObject {
    public String distance;
    public String hcp_cust_id;
    public String hcp_cust_name;
    public String hcp_cust_mobile_no;
    public String hcp_cust_map_lat;
    public String hcp_cust_map_long;
    public String hcp_cust_blood_group;

    public DataObject(String distance, String hcp_cust_id,String hcp_cust_name, String hcp_cust_mobile_no,String hcp_cust_map_lat, String hcp_cust_map_long,String hcp_cust_blood_group)
    {
        this.distance = distance;
        this.hcp_cust_id = hcp_cust_id;
        this.hcp_cust_name = hcp_cust_name;
        this.hcp_cust_mobile_no = hcp_cust_mobile_no;
        this.hcp_cust_map_lat = hcp_cust_map_lat;
        this.hcp_cust_map_long = hcp_cust_map_long;
        this.hcp_cust_blood_group = hcp_cust_blood_group;
    }
    public String getDistance()
    {
        return this.distance;
    }
    public void setDistance(String distance)
    {
        this.distance = distance;
    }
    public String getHcpCustId()
    {
        return this.hcp_cust_id;
    }
    public void setHcpCustId(String hcp_cust_id)
    {
        this.hcp_cust_id = hcp_cust_id;
    }
    public String getHcpCustName()
    {
        return this.hcp_cust_name;
    }
    public void setHcpCustName(String hcp_cust_name)
    {
        this.hcp_cust_name = hcp_cust_name;
    }
    public String getHcpCustMob()
    {
        return this.hcp_cust_mobile_no;
    }
    public void setHcpCustMob(String hcp_cust_mobile_no)
    {
        this.hcp_cust_mobile_no = hcp_cust_mobile_no;
    }
    public String getHcpCustLat()
    {
        return this.hcp_cust_map_lat;
    }
    public void setHcpCustLat(String hcp_cust_map_lat)
    {
        this.hcp_cust_map_lat = hcp_cust_map_lat;
    }
    public String getHcpCustLong()
    {
        return this.hcp_cust_map_long;
    }
    public void setHcpCustLong(String hcp_cust_map_long)
    {
        this.hcp_cust_map_long = hcp_cust_map_long;
    }
    public String getHcpCustBlood()
    {
        return this.hcp_cust_blood_group;
    }
    public void setHcpCustBlood(String hcp_cust_blood_group)
    {
        this.hcp_cust_blood_group = hcp_cust_blood_group;
    }

}
