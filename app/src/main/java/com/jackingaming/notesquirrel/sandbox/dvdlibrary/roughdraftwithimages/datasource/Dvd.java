package com.jackingaming.notesquirrel.sandbox.dvdlibrary.roughdraftwithimages.datasource;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

public class Dvd {

    private Bitmap image;
    private String title;
    private boolean isFavorite = false;

    public Dvd(Bitmap image, String title) {
        this.image = image;
        this.title = title;
    }

    @NonNull
    @Override
    public String toString() {
        return title;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    public void toggleFavorite() {
        isFavorite = !isFavorite;
    }
}