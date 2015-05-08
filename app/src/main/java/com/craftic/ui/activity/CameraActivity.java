package com.craftic.ui.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.craftic.HelperMethods.CameraHelper;
import com.craftic.R;

public class CameraActivity extends ActionBarActivity {

    private final int PHOTO_INTENT_WITH_FILENAME = 2000;
    private final int VIDEO_REQUEST = 3000;
    private Uri _photoFileUri = null;
    private Uri _videoFileUri = null;

    final String LOG_TAG = "Camera Direct Access";
    final String SELECTED_CAMERA_ID_KEY  = "_selectedCameraId";
    final int CAMERA_ID_NOT_SET = -1;

    boolean _hasCamera = false;
    boolean _hasFrontCamera = false;
    int _frontFacingCameraId = CAMERA_ID_NOT_SET;
    int _backFacingCameraId = CAMERA_ID_NOT_SET;
    private ImageView imageView;

    int _selectedCameraId;
   // Camera _selectedCamera;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_camera);

            PackageManager pm = getPackageManager();
            _hasCamera = pm.hasSystemFeature(PackageManager.FEATURE_CAMERA);
            _hasFrontCamera = pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT);

            //   _selectedCameraId = getFacingCameraId(android.hardware.Camera.CameraInfo.CAMERA_FACING_BACK);

            //  Camera.getParameters(); //to check the capabilities of the camera
            // Camera.getCameraInfo() to determine if a camera is on the front or back of the device, and the orientation of the image

            startCamera();
        }

        private void startCamera() {
            _photoFileUri = CameraHelper.generateTimeStampPhotoFileUri();
            if (_photoFileUri != null)
            {
                Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,_photoFileUri);
                startActivityForResult(openCameraIntent,PHOTO_INTENT_WITH_FILENAME);
            }
        }
        private void startVideo() {
            _videoFileUri = CameraHelper.generateTimeStampVideoFileUri();
            if (_videoFileUri != null)
            {
                Intent openVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                openVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT,_videoFileUri);
                openVideoIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY,0);
                startActivityForResult(openVideoIntent,VIDEO_REQUEST);
            }
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent resultIntent) {
            Bundle extras = null;
            Bitmap imageBitmap = null;
            ImageView imageView = (ImageView) findViewById(R.id.imageView);

            if (requestCode == RESULT_CANCELED){
                Toast.makeText(CameraActivity.this, "User Canceled", Toast.LENGTH_LONG).show();
                return;
            }
            switch (requestCode)
            {
                case PHOTO_INTENT_WITH_FILENAME:
                    Toast.makeText(CameraActivity.this, "Photo file uri"+_photoFileUri.getPath(),Toast.LENGTH_LONG).show();
                    imageBitmap = BitmapFactory.decodeFile(_photoFileUri.getPath());

                    break;
                case VIDEO_REQUEST:
                    //imageBitmap = BitmapFactory.decodeFile(_photoFileUri.getPath());
                    break;
            }
      /*  if (imageBitmap != null)
        {
            imageView.setImageBitmap(imageBitmap);
        }*/

        }

        public int getFacingCameraId(int facing)
        {
            int cameraId = CAMERA_ID_NOT_SET;
            int nCameras = android.hardware.Camera.getNumberOfCameras();
            android.hardware.Camera.CameraInfo camerainfo;

            return cameraId;
        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_camera, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
