package com.gethotdrop.ui;

public class Drop
{
    int image;
    String message;

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return message;
    }

    public void setName(String name) {
        this.message = name;
    }

    public Drop(int image, String name) {
        super();
        this.image = image;
        this.message = name;
    }
}