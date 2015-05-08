package com.craftic.ui.Fragments;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.craftic.R;
import com.craftic.ui.activity.ArtisanActivty;
import com.craftic.ui.activity.CrafticMenuItems;
import com.craftic.ui.adapter.CrafticMenuAdapter;

import java.util.ArrayList;
import java.util.List;


public class NavigationDrawerFragment  extends Fragment {

    /*
    STEPS TO HANDLE THE RECYCLER CLICK

    1 Create a class that EXTENDS RecylcerView.OnItemTouchListener

    2 Create an interface inside that class that supports click and long click and indicates the View that was clicked and the position where it was clicked

    3 Create a GestureDetector to detect ACTION_UP single tap and Long Press events

    4 Return true from the singleTap to indicate your GestureDetector has consumed the event.

    5 Find the childView containing the coordinates specified by the MotionEvent and if the childView is not null and the listener is not null either, fire a long click event

    6 Use the onInterceptTouchEvent of your RecyclerView to check if the childView is not null, the listener is not null and the gesture detector consumed the touch event

    7 if above condition holds true, fire the click event

    8 return false from the onInterceptedTouchEvent to give a chance to the childViews of the RecyclerView to process touch events if any.

    9 Add the onItemTouchListener object for our RecyclerView that uses our class created in step 1
     */
    public static final String PREF_FILE_NAME = "testpref";
    public static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";
    private RecyclerView recyclerView;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private CrafticMenuAdapter adapter;
    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstanceState;
    private View containerView;
    private boolean isDrawerOpened = false;

    public NavigationDrawerFragment() {
        // Required empty public constructor
    }

    public static List<CrafticMenuItems> getData() {
        //load only static data inside a drawer
        List<CrafticMenuItems> data = new ArrayList<>();
        int[] icons = {R.drawable.ic_profile,R.drawable.ic_about, R.drawable.ic_contact};
        String[] titles = {"My Profile", "About Us","Contact Us"};
        for (int i=0;i < titles.length;i++)
        {
            CrafticMenuItems item = new CrafticMenuItems();
            item.iconId = icons[i];
            item.title = titles[i];
            data.add(item);
        }
        return data;
    }


    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static void saveToPreferences(Context context, String preferenceName, String preferenceValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferenceName, preferenceValue);
        editor.apply();
    }

    public static String readFromPreferences(Context context, String preferenceName, String defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(preferenceName, defaultValue);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserLearnedDrawer = Boolean.valueOf(readFromPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, "false"));
        if (savedInstanceState != null) {
            mFromSavedInstanceState = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        recyclerView = (RecyclerView) layout.findViewById(R.id.drawerList);
        adapter = new CrafticMenuAdapter(getActivity(), getData());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView,new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                //   Toast.makeText(getActivity(), "onClick " + position, Toast.LENGTH_SHORT).show();
                startTheActivity(position);
            }

            @Override
            public void onLongClick(View view, int position) {
                //  Toast.makeText(getActivity() ,"onLongClick "+position,Toast.LENGTH_SHORT).show();
            }
        }));
        return layout;
    }

    private void startTheActivity(int position) {
        int pos = position;
        switch (pos)
        {
            case 0:
                startActivity(new Intent(getActivity(),ArtisanActivty.class));
                break;
            case 1:
             //   startActivity(new Intent(getActivity(),CheckTyreCodeActivity.class));
                break;
            case 2:

                break;
            case 3:

                break;
        }
    }

    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                if (!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                    saveToPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, mUserLearnedDrawer + "");
                }
                if (Build.VERSION_CODES.GINGERBREAD_MR1 == 10)
                {
                    // getActivity().invalidateOptionsMenu();
                    return;
                }
                else
                {
                    getActivity().invalidateOptionsMenu();
                }

            }

            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onDrawerClosed(View drawerView) {

                super.onDrawerClosed(drawerView);
                if (Build.VERSION_CODES.GINGERBREAD_MR1 == 10)
                {
                    // getActivity().invalidateOptionsMenu();
                    return;
                }
                else
                {
                    getActivity().invalidateOptionsMenu();
                }

            }

            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (Build.VERSION_CODES.GINGERBREAD_MR1 == 10)
                {
                    //  toolbar.setAlpha(1 - slideOffset / 2);
                    return;
                }
                else
                {
                    toolbar.setAlpha(1 - slideOffset / 2);
                }

            }


        };
        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(containerView);
        }
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }
    public static interface ClickListener{
        public void onClick(View view, int position);
        public void onLongClick(View view, int position);
    }

    static class RecyclerTouchListener implements  RecyclerView.OnItemTouchListener{

        private GestureDetector gestureDetector;
        private ClickListener clickListener;
        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener){
            Log.d("VIVZ", "constructor invoked ");
            this.clickListener=clickListener;
            gestureDetector=new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    Log.d("VIVZ","onSingleTapUp "+e);
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child=recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if(child!=null && clickListener!=null)
                    {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                    Log.d("VIVZ","onLongPress "+e);
                }
            });
        }
        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child=rv.findChildViewUnder(e.getX(), e.getY());
            if(child!=null && clickListener!=null && gestureDetector.onTouchEvent(e))
            {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            Log.d("VIVZ","onTouchEvent "+e);
        }
    }


}