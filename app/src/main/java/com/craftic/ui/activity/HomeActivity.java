package com.craftic.ui.activity;

/**
 * Created by keren on 5/2/15.
 */

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.craftic.DAL.UserRepository;
import com.craftic.Entities.User;
import com.craftic.R;
import com.craftic.ui.adapter.FragmentDrawer;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

public class HomeActivity extends ActionBarActivity implements FragmentDrawer.FragmentDrawerListener {

   // private static String TAG = MainActivity.class.getSimpleName();

    // private UserProfileAdapter2 adapter;

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    // private UserProfileAdapter2 adapter;
    User thisUser = new User();
    private Context ctx = this;


    private Cursor cursor;
    private int columnIndex;
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            value = extras.getString("uname");
            Log.i("gotusernameas>>",value);
        }





        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        // display the first navigation drawer view on app launch
        displayView(0);

       // gridview.setAdapter(new HomeAdapter(this));
    }

    public String getMyData() {
        return value;
    }

    public String getUserName()
    {
        return  value;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

        if(id == R.id.action_settings){
            Toast.makeText(getApplicationContext(), "Search action is selected!", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                title = getString(R.string.title_home);
                break;
            case 1:
                fragment = new HomeFragment();
                title = getString(R.string.title_friends);
                break;
            case 2:
                fragment = new HomeFragment();
                title = getString(R.string.title_messages);
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }
}


