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

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by dtech on 20/11/2016.
 */

public class Receiver extends BroadcastReceiver {
    private String TAG = Receiver.class.getSimpleName();

    //ConnectivityManager cm;
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
                    /*cm = (ConnectivityManager) context.getSystemService(
                            Context.CONNECTIVITY_SERVICE
                    );
                    activeNetwork = cm.getActiveNetworkInfo();
                    boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

                    if (connectivityReceiverListener != null) {
                        connectivityReceiverListener.onNetworkConnectionChnged(isConnected);
                    }*/

                    proccessMessage(message, context);


                }
            } else {
                Log.e(TAG, "null array");
            }



        }
    }

    private void proccessMessage(String message, Context context) {
        String formatTransaksi;
        int start = message.indexOf(".3003") - 18;
        int end = message.indexOf(".3003") + 5;
        String kode = message.substring(start, end);
        String[] arrayKodeA = kode.split("\\s");

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE
        );
        activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if (connectivityReceiverListener != null) {
            connectivityReceiverListener.onNetworkConnectionChnged(isConnected);
        }

        if (message.contains("SUKSES")) {

            if (arrayKodeA.length > 1) {
                formatTransaksi = arrayKodeA[1];
            } else {
                formatTransaksi = arrayKodeA[0];
            }

            if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {

                try {
                    MainActivity inst = MainActivity.instance();
                    inst.updateList(formattedText);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                sendToDbase(context, sender, formatTransaksi);

            } else {
                Toast.makeText(context, "No Connection", Toast.LENGTH_LONG).show();
            }
        } else if (message.contains("tipe kartu")) {
            //formatTransaksi = "";
            if (arrayKodeA.length > 1) {
                formatTransaksi = arrayKodeA[1]+" transaksi gagal";
            } else {
                formatTransaksi = arrayKodeA[0]+" transaksi gagal";
            }

            if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {

                try {
                    MainActivity inst = MainActivity.instance();
                    inst.updateList(formattedText);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                sendToDbase(context, sender, formatTransaksi);

            } else {
                Toast.makeText(context, "No Connection", Toast.LENGTH_LONG).show();
            }
        } else if (message.contains("idPel:")) {
            sendToDbaseTagihan(context, message);

        } else {
            Log.d("TAG", "Bukan Sms Transaksi");
        }




    }

    private void sendToDbaseTagihan(final Context context, final String message) {

        class sendMessageTagihan extends AsyncTask<Void, Void, String> {


            @Override
            protected String doInBackground(Void... params) {
                HashMap<String, String> parameters = new HashMap<>();
                parameters.put(ConfigUrl.KEY_NAME, sender);
                parameters.put(ConfigUrl.KEY_KODE, message);

                RequestHandler requestHandler = new RequestHandler();
                String resultsend = requestHandler.sendPostRequest(ConfigUrl.URL_TAGIHAN, parameters);
                return resultsend;
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
            sendMessageTagihan sendmTagihan = new sendMessageTagihan();
            sendmTagihan.execute();
        } else {
            Toast.makeText(context, "No Connection", Toast.LENGTH_LONG).show();
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
                String resultsend = requestHandler.sendPostRequest(ConfigUrl.URL_TEST, parameters);
                return resultsend;
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
