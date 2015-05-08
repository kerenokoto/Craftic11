package com.craftic.HelperMethods;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by keren on 4/29/15.
 */
public class CameraHelper {



    public CameraHelper() {
    }



    public static File getPhotoDirectory()
    {
        File outputDir = null;
        String externalStorageState = Environment.getExternalStorageState();
        if (externalStorageState.equals(Environment.MEDIA_MOUNTED))
        {
            File pictureDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            outputDir = new File(pictureDir,"CrafticPhotos");
            if (!outputDir.exists())
            {
                if(!outputDir.mkdirs())
                {
                    Log.i("CameraHelper", "Could not create CrafticPhotos Directory");
                    outputDir = null;
                }
            }
        }
        return outputDir;
    }
    public static File getVideoDirectory()
    {
        File outputDir = null;
        String externalStorageState = Environment.getExternalStorageState();
        if (externalStorageState.equals(Environment.MEDIA_MOUNTED))
        {
            File videoDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
            outputDir = new File(videoDir,"CrafticPhotos");
            if (!outputDir.exists())
            {
                if(!outputDir.mkdirs())
                {
                    Log.i("CameraHelper","Could not create CrafticPhotos Directory");
                    outputDir = null;
                }
            }
        }
        return outputDir;
    }

    public static Uri generateTimeStampPhotoFileUri()
    {
        Uri photoFileUri = null;
        File outputDir = getPhotoDirectory();
        if (outputDir != null)
        {
            String timeStamp = new SimpleDateFormat("yyyyMMDD_HHmmss").format(new Date());
            String photoFileName = "ct_"+timeStamp+".jpg";

            File photoFile = new File(outputDir,photoFileName);
            photoFileUri = Uri.fromFile(photoFile);
        }
        return photoFileUri;
    }

    public static Uri generateTimeStampVideoFileUri()
    {
        Uri videoFileUri = null;
        File outputDir = getVideoDirectory();
        if (outputDir != null)
        {
            String timeStamp = new SimpleDateFormat("yyyyMMDD_HHmmss").format(new Date());
            String videoFileName = "vid_tc_"+timeStamp+".mp4";

            File videoFile = new File(outputDir,videoFileName);
            videoFileUri = Uri.fromFile(videoFile);
        }
        return videoFileUri;
    }

    /** Check if this device has a camera */
    private boolean checkCameraHardware(Context context)
    {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    /** A safe way to get an instance of the Camera object. */
    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }
}
