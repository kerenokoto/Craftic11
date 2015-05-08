package com.craftic.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.craftic.DAL.UserRepository;
import com.craftic.Entities.User;
import com.craftic.HelperMethods.CameraHelper;
import com.craftic.R;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

public class ArtisanActivty extends Activity {


   // private UserProfileAdapter2 adapter;
    User thisUser = new User();
    private Context ctx = this;


    private Cursor cursor;
    private int columnIndex;

    private final int PHOTO_INTENT_WITH_FILENAME = 2000;
    private final int VIDEO_REQUEST = 3000;
    private Uri _photoFileUri = null;
    private Uri _videoFileUri = null;

    //@InjectView(R.id.myUserProfile)
    //RecyclerView myUserProfile;

   // @InjectView(R.id.ivUserProfilePhoto)
    ImageView ivUserProfilePhoto;

    //@InjectView(R.id.username)
    TextView username;

    //@InjectView(R.id.cat)
    TextView cat;
    GridView gridview;
    ImageButton btnCamera;

    @InjectView(R.id.btnFollow)
    TextView btnFollow;

    User userfromDBD = null;
    UserRepository userrepo = new UserRepository(ctx);
    List<User> listofUsers4mDB = new ArrayList<>();

   String value;

    File[] mediaFiles;
    File imageDir;
    static GridView gridView;
    ImageAdapter imageadapter;
    Intent in;
    ImageButton btncam;
    String name = null;
    ArrayList<Bitmap> bmpArray = new ArrayList<Bitmap>();
    ArrayList<String> fileName = new ArrayList<String>();
    public static final String TAG = "Album3Activity";


    @Override
   protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        imageDir = new File(Environment.getExternalStorageDirectory().toString()+
                "/CrafticPhotos");
        checkImagesInFolder();

        setContentView(R.layout.activity_artisan_activty);

       Bundle extras = getIntent().getExtras();

       if (extras != null) {
           value = extras.getString("uname");
       }


        initializeView(value);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    startCamera();
            }
        });


    }

    void checkImagesInFolder()
    {
        if((imageDir.exists()))
        {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            mediaFiles = imageDir.listFiles();
            //Log.d("Length of images",""+mediaFiles.length);
            for(File file : mediaFiles)
            {
                bmpArray.add(convertToBitmap(file));
                fileName.add(readFileName(file));
                //Log.d(TAG + "bmpArray Size", ""+bmpArray.size());
                //Log.d(TAG, "call to convertToBitmap");
            }//for

            // request only the image ID to be returned
            String[] projection = {MediaStore.Images.Media._ID};
// Create the cursor pointing to the SDCard
            cursor = managedQuery( MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    projection,
                    MediaStore.Images.Media.DATA + " like ? ",
                    new String[] {"%CrafticPhotos%"},
                    null);
// Get the column index of the image ID
            columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
            gridview.setAdapter(new ImageAdapter(this));


           // imageadapter = new  ImageAdapter(ctx);
           // gridView = (GridView)findViewById(R.id.gridview);
          //  gridView.setAdapter(imageadapter);
           // sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED
            //        , Uri.parse(Environment.getExternalStorageDirectory().toString()+"/CrafticPhotos")));

           /* gridView.setOnItemClickListener(new OnItemClickListener() {

                public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                        long arg3) {
                    in = new Intent(getApplicationContext(), FullScreen.class);
                    in.putExtra("id", position);
                    startActivity(in);
                }
            });*/
        }

    }

    public static Bitmap convertToBitmap(File file)
    {
        URL url = null;
        try
        {
            url = file.toURL();
        } catch (MalformedURLException e1)
        {
            //Log.d(TAG, e1.toString());
        }//catch

        Bitmap bmp = null;
        try
        {
            bmp = BitmapFactory.decodeStream(url.openStream());
            //bmp.recycle();
        }catch(Exception e)
        {
            //Log.d(TAG, "Exception: "+e.toString());
        }//catch
        return bmp;
    }//convertToBitmap

    public String readFileName(File file){
        String name = file.getName();
        return name;
    }//readFileName
    private void setupUserProfileGrid()
    {
       // myUserProfile.setVisibility(View.INVISIBLE);
      /*  final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        myUserProfile.setLayoutManager(layoutManager);
        myUserProfile.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView myUserProfile, int newState) {
                userPhotosAdapter2.setLockedAnimations(true);
            }
        });*/
    }

    public void initializeView(String val)
    {
        ivUserProfilePhoto = (ImageView) findViewById(R.id.ivUserProfilePhoto);
        username = (TextView) findViewById(R.id.username);
        cat = (TextView) findViewById(R.id.cat);
        btnCamera = (ImageButton) findViewById(R.id.btnCamera);

        try {

            listofUsers4mDB = userrepo.getUser(val);
            userfromDBD = listofUsers4mDB.get(0);
        } catch (Exception e) {
            // e.printStackTrace();
        }
        if (userfromDBD != null)
        {
            username.setText(userfromDBD.getFname()+" "+userfromDBD.getLname());
            cat.setText(userfromDBD.getCategorytype());

        }

        gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(ArtisanActivty.this, "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent resultIntent) {

        Bundle extras = null;
        Bitmap imageBitmap = null;
        ImageView imageView = (ImageView) findViewById(R.id.imageView);

        if (requestCode == RESULT_CANCELED)
        {
            Toast.makeText(ArtisanActivty.this, "User Canceled", Toast.LENGTH_LONG).show();
            return;
        }
        switch (requestCode)
        {
            case PHOTO_INTENT_WITH_FILENAME:
                Toast.makeText(ArtisanActivty.this, "Photo file uri-"+_photoFileUri.getPath(),
                        Toast.LENGTH_LONG).show();


                break;

        }

    }




/*    public  List<UserProfileModule> getData()
    {
        //load only static data inside a drawer
        List<UserProfileModule> data = new ArrayList<>();
        int[] icons = {R.drawable.ic_man};
        *//*String[] titles = {"My Profile", "About Us","Contact Us"};
        for (int i=0;i < titles.length;i++)
        {
            UserProfileModule item = new UserProfileModule();
            item.iconId = icons[i];
            item.title = titles[i];
            data.add(item);
        }*//*

        User userfromDBD = null;
        try {

            UserRepository userrepo = new UserRepository(ctx);

            List<User> listofUsers4mDB = userrepo.getUser("k");
            userfromDBD = listofUsers4mDB.get(0);
        } catch (Exception e) {

            // e.printStackTrace();
        }

        if (userfromDBD != null)
        {

            UserProfileModule userData = new UserProfileModule();

            userData.setIconId(R.drawable.ic_man);
            userData.setUsername(userfromDBD.getUsername());
            userData.setFname(userfromDBD.getFname());
            userData.setLname(userfromDBD.getLname());
            userData.setCategorytype(userfromDBD.getCategorytype());
            data.add(userData);
            return data;

        }

        return null;
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_artisan_activty, menu);
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

    public class ImageAdapter extends BaseAdapter {
        private Context mContext;

        public ImageAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return bmpArray.size();
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent)
        {
            ImageView i = new ImageView(ctx);
            // Move cursor to current position
            cursor.moveToPosition(position);
            // Get the current value for the requested column
            int imageID = cursor.getInt(columnIndex);
            // obtain the image URI
            Uri uri = Uri.withAppendedPath( MediaStore.Images.Media.EXTERNAL_CONTENT_URI, Integer.toString(imageID) );
            String url = uri.toString();
            // Set the content of the image based on the image URI
            int originalImageId = Integer.parseInt(url.substring(url.lastIndexOf("/") + 1, url.length()));
            Bitmap b = MediaStore.Images.Thumbnails.getThumbnail(getContentResolver(),
                    originalImageId, MediaStore.Images.Thumbnails.MINI_KIND, null);
            i.setImageBitmap(b);
            i.setLayoutParams(new Gallery.LayoutParams(150, 100));
            i.setScaleType(ImageView.ScaleType.FIT_XY);
           // i.setBackgroundResource(mGalleryItemBackground);
            return i;
        }

        // references to our images
      /*  private Integer[] mThumbIds = {
                R.drawable.sample_2, R.drawable.sample_3,
                R.drawable.sample_4, R.drawable.sample_5,
                R.drawable.sample_6, R.drawable.sample_7,
                R.drawable.sample_0, R.drawable.sample_1,
                R.drawable.sample_2, R.drawable.sample_3,
                R.drawable.sample_4, R.drawable.sample_5,
                R.drawable.sample_6, R.drawable.sample_7,
                R.drawable.sample_0, R.drawable.sample_1,
                R.drawable.sample_2, R.drawable.sample_3,
                R.drawable.sample_4, R.drawable.sample_5,
                R.drawable.sample_6, R.drawable.sample_7
        };*/
    }

}
