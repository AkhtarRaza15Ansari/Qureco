package com.sriyaan.modal;

/**
 * Created by Akhtar on 25-08-2016.
 */
public class NotificationsData {

    public String msg;
    public String msg_type;
    public String msg_date;

    public NotificationsData(String msg, String msg_type, String msg_date)
    {
        this.msg = msg;
        this.msg_type = msg_type;
        this.msg_date = msg_date;
    }


    public String getMsg(){
        return msg;
    }
    public void setMsg(String msg){
        this.msg = msg;
    }

    public String getMsgType(){
        return msg_type;
    }
    public void setMsgType(String msg_type){
        this.msg_type = msg_type;
    }

    public String getMsgDate(){
        return msg_date;
    }
    public void setMsgDate(String msg_date){
        this.msg_date = msg_date;
    }
}
