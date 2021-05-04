package com.example.adapters;

import android.net.Uri;

public class Post {
    private String header;
    private Uri image;
    private String description;

    public Post(String header, Uri image, String description){
        this.header = header;
        this.image = image;

        this.description = description;
    }

    public String getHeader(){
        return header;
    }

    public Uri getImage(){
        return image;
    }

    public String getDescription(){
        return description;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setImage(Uri image) {
        this.image = image;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
