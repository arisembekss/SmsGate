package com.dtech.myapplication;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by aris on 20/02/17.
 */

public class InstanceIdService extends FirebaseInstanceIdService {

    private static final String TAG = "FirebaseId";

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);


        //prefManager.setFirebaseId(refreshedToken);
    }
}
