package com.sriyaan.modal;

/**
 * Created by ansariakhtar on 18/09/16.
 */
public class DataObject {
    public String name;
    public String image;
    public DataObject(String name, String image)
    {
        this.name = name;
        this.image = image;
    }
    public String getName()
    {
        return this.name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public String getImage()
    {
        return this.image;
    }
    public void setImage(String image)
    {
        this.image = image;
    }

}
