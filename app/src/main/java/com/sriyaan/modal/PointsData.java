package com.sriyaan.modal;

/**
 * Created by Akhtar on 25-08-2016.
 */
public class PointsData {
    public String points;
    public String action_name;
    public String amount;
    public String date;

    public PointsData(String points, String action_name, String amount, String date)
    {
        this.points = points;
        this.action_name = action_name;
        this.amount = amount;
        this.date = date;
    }
    public String getPoints(){
        return points;
    }
    public void setPoints(String points){
        this.points = points;
    }

    public String getActionName(){
        return action_name;
    }
    public void setActionName(String action_name){
        this.action_name = action_name;
    }

    public String getAmount(){
        return amount;
    }
    public void setAmount(String amount){
        this.amount= amount;
    }

    public String getDate(){
        return date;
    }
    public void setDate(String date){
        this.date = date;
    }
}
