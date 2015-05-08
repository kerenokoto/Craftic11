package com.craftic.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.craftic.R;

import java.util.List;


/**
 * Created by keren on 5/3/15.
 */
public class GalleryAdapter extends ArrayAdapter<GalleryItem> {

    public GalleryAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public GalleryAdapter(Context context, int resource, List<GalleryItem> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {

            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.gallery_item, null);

        }

        GalleryItem p = getItem(position);

        if (p != null) {

            ImageView imageView = (ImageView) v.findViewById(R.id.imageView);
            try {
                imageView.setImageURI(p.getImagePath());
            } catch (Exception e) {
               // e.printStackTrace();

            }


        }

        return v;

    }
}