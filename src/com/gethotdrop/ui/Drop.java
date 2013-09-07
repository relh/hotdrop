package com.gethotdrop.ui;

public class Drop
{
    int image;
    String text;

    public Drop(int image, String name) {
        super();
        this.image = image;
        this.text = name;
        
        //GET DROP FROM SERVER IN CONSTRUCTOR
    }
    
    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return text;
    }

    public void setName(String name) {
        this.text = name;
    }

}