package com.craftic.ui.adapter;

import android.net.Uri;

/**
 * Created by keren on 5/3/15.
 */
public class GalleryItem {

    private Uri imagePath;

    public  GalleryItem(Uri uri){
        this.imagePath = uri;

    }

    public Uri getImagePath() {
        return imagePath;
    }
}
