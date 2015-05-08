package com.craftic.ui.activity;

/**
 * Created by keren on 5/2/15.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.craftic.DAL.UserRepository;
import com.craftic.Entities.User;
import com.craftic.R;
import com.craftic.ui.adapter.GalleryAdapter;
import com.craftic.ui.adapter.GalleryItem;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class HomeFragment extends Fragment {
    private ImageButton imageButton;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    private static final String IMAGE_DIRECTORY_NAME = "craftic";

    private Uri fileUri;

    private GalleryItem galleryItem;
    private GalleryAdapter galleryAdapter;
    public static final String TAG = "Album3Activity";
    private GridView gridView;
    private Context context;
    private ArrayList<GalleryItem> arrayList;


    User userfromDBD = null;
    UserRepository userrepo = new UserRepository(context);
    List<User> listofUsers4mDB = new ArrayList<>();
    private String uname;
    private TextView username;
    private TextView cat;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);



        context = getActivity();

      //  uname = getArguments().getString("uname");
        HomeActivity activity = (HomeActivity) getActivity();
        String myDataFromActivity = activity.getMyData();
        Log.i("myDataFromActivity", myDataFromActivity);

       // ImageView   ivUserProfilePhoto = (ImageView) rootView.findViewById(R.id.ivUserProfilePhoto);
        username = (TextView) rootView.findViewById(R.id.username);
         cat = (TextView) rootView.findViewById(R.id.cat);
       // btnCamera = (ImageButton) findViewById(R.id.btnCamera);

        try {

            listofUsers4mDB = userrepo.getUser(myDataFromActivity);
            userfromDBD = listofUsers4mDB.get(0);
        } catch (Exception e) {
            // e.printStackTrace();
        }
        if (userfromDBD != null)
        {
            username.setText(userfromDBD.getFname()+" "+userfromDBD.getLname());
            cat.setText(userfromDBD.getCategorytype());

        }





        imageButton = (ImageButton)rootView.findViewById(R.id.btnCamera);
        gridView = (GridView)rootView.findViewById(R.id.gridview);
        arrayList = new ArrayList<>();
        galleryAdapter = new GalleryAdapter(context,R.layout.gallery_item,arrayList);
        gridView.setAdapter(galleryAdapter);
        getAlbumStorageDir();
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                                    startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);


            }
        });


        // Inflate the layout for this fragment
        return rootView;
    }



    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent result) {
        super.onActivityResult(requestCode,resultCode,result);
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == getActivity().RESULT_OK) {
                String path = fileUri.getPath();
                arrayList.add(new GalleryItem(Uri.parse(path)));
                galleryAdapter.notifyDataSetChanged();
                //String image = ImageDecoder.encodeImage(path);
                Toast.makeText(getActivity(),"Taken successfully",Toast.LENGTH_LONG).show();
            } else if (resultCode == getActivity().RESULT_CANCELED) {
                Toast.makeText(getActivity(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(getActivity(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        }

    }


    private static File getOutputMediaFile(int type) {
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator+ "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }
        return mediaFile;
    }


    public File getAlbumStorageDir() {
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), IMAGE_DIRECTORY_NAME);
        if (file.exists()) {
            File list[] = file.listFiles();
            for (int i=0; i < list.length; i++)
            {
                String name = list[i].getName();
                String path = list[i].getAbsolutePath();
                Uri uri = Uri.parse(path);
                arrayList.add(new GalleryItem(uri));
            }
            galleryAdapter.notifyDataSetChanged();
        }else{
            Toast.makeText(context,"Not Found ",Toast.LENGTH_LONG).show();
        }
        return file;
    }
}
