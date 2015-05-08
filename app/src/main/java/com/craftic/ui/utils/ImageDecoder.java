package com.craftic.ui.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Created by keren on 5/2/15.
 */
public class ImageDecoder {
    public static String encodeImage(String path) { File imageFile = new File(path); Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath()); ByteArrayOutputStream stream = new ByteArrayOutputStream(); bitmap.compress(Bitmap.CompressFormat.JPEG, 10, stream); byte[] image = stream.toByteArray(); return Base64.encodeToString(image, 0); }
    public static Bitmap decodeImage(String image) { byte[] decodedString = Base64.decode(image, Base64.DEFAULT); return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }
}