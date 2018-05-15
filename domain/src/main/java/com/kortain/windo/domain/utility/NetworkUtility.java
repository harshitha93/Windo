package com.kortain.windo.domain.utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import javax.inject.Inject;

/**
 * Created by satiswardash on 26/04/18.
 */

public class NetworkUtility {

    private Context mContext;

    @Inject
    public NetworkUtility(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * Network utility to check the network availability using {@link ConnectivityManager}
     *
     * @return
     * @throws Exception
     */
    public boolean hasNetworkAccess() {

        ConnectivityManager cm = (ConnectivityManager)
                mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = null;
        if (cm != null) {
            activeNetwork = cm.getActiveNetworkInfo();
        }
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }
}
