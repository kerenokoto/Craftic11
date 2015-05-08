package com.craftic.HelperMethods;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by keren on 4/29/15.
 */

    public class InternetConnection {

        private Context context;

        public InternetConnection(Context context)
        {
            this.context = context;
        }

        public boolean isConnectingToInternet()
        {
            ConnectivityManager connMgr = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected())
            {
                return true;
            }
            return false;
        }
    }

