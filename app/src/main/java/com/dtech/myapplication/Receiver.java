package com.dtech.myapplication;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;

/**
 * Created by dtech on 20/11/2016.
 */

public class Receiver extends BroadcastReceiver {
    private String TAG = Receiver.class.getSimpleName();

    String formattedText;
    String sender;
    String phoneNumber;
    String message;
    NetworkInfo activeNetwork;
    public static ConnectivityReceiverListener connectivityReceiverListener;
    public Receiver() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle intentExtras = intent.getExtras();
        if (intentExtras != null) {
            Object[] sms = (Object[]) intentExtras.get("pdus");
            String smsMessageStr = "";
            if (sms != null) {
                for (int i = 0; i < sms.length; ++i) {
                    SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sms[i]);

                    phoneNumber = smsMessage.getDisplayOriginatingAddress();
                    sender = phoneNumber;
                    message = smsMessage.getDisplayMessageBody();

                    formattedText = "Sms From : " + sender + "\n" + message;

                    smsMessageStr += "SMS From: " + sender + "\n";
                    smsMessageStr += message + "\n";

                    Toast.makeText(context, formattedText, Toast.LENGTH_LONG).show();

                    //this will update the UI with message


                    //this function below will execute after message come,
                    //inserting sender number and message body
                    //to MySql Database
                    ConnectivityManager cm = (ConnectivityManager) context.getSystemService(
                            Context.CONNECTIVITY_SERVICE
                    );
                    activeNetwork = cm.getActiveNetworkInfo();
                    boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

                    if (connectivityReceiverListener != null) {
                        connectivityReceiverListener.onNetworkConnectionChnged(isConnected);
                    }

                    if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
                        //if (sender.equals("+6289655720330") || sender.equals("089655720330")) {
                        try {
                            MainActivity inst = MainActivity.instance();
                            inst.updateList(formattedText);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        sendToDbase(context, sender, message);
                        //}

                    } else {
                        Toast.makeText(context, "No Connection", Toast.LENGTH_LONG).show();
                    }
                }
            } else {
                Log.e(TAG, "null array");
            }



        }
    }

    //function for send message info to MySql Database
    //keep in your mind that this function only execute 1 PHP file
    public void sendToDbase(final Context context, final String sender, final String message) {

        class sendMessage extends AsyncTask<Void, Void, String> {


            @Override
            protected String doInBackground(Void... params) {
                HashMap<String, String> parameters = new HashMap<>();
                parameters.put(ConfigUrl.KEY_NAME, sender);
                parameters.put(ConfigUrl.KEY_KODE, message);

                RequestHandler requestHandler = new RequestHandler();
                String result = requestHandler.sendPostRequest(ConfigUrl.URL_TEST, parameters);
                return result;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();


            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                Toast.makeText(context, "Success adding to database", Toast.LENGTH_SHORT).show();
            }
        }

        if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
            sendMessage sendNow = new sendMessage();
            sendNow.execute();
        } else {
            Toast.makeText(context, "No Connection", Toast.LENGTH_LONG).show();
        }


    }

    public static boolean isConnected() {
        /*ConnectivityManager cm = (ConnectivityManager) MainActivity.instance().getSystemService(
                Context.get
        )*/
        return true;

    }

    public interface ConnectivityReceiverListener{
        void onNetworkConnectionChnged(boolean isConnected);
    }
}
