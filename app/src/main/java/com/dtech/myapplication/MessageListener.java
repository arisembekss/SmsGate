package com.dtech.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import static android.app.Activity.RESULT_OK;

/**
 * Created by aris on 07/04/17.
 */

public class MessageListener extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int resultCode = this.getResultCode();
        boolean successfullySent = resultCode == RESULT_OK;
        //That boolean up there indicates the status of the message
        MyApp.getInstance().unregisterReceiver(this);
    }
}
