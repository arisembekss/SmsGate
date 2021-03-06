package com.dtech.myapplication;

import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    public void onReceive(final Context context, Intent intent) {
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

        String action = intent.getAction();
        if(action.equals("com.myapp.myaction")){
            String state = intent.getExtras().getString("extra");
            Toast.makeText(context, state, Toast.LENGTH_LONG).show();
            //MyApp.getInstance().unregisterReceiver(this);
        }

    }

    public void sendSmsTrx(Context context, String trx) {
        String SENT_SMS_FLAG = "thus";
        Toast.makeText(context, trx, Toast.LENGTH_LONG).show();
        String nomorr = "089678382795";
        SmsManager smss = SmsManager.getDefault();
        Intent intent = new Intent();
        PendingIntent sentIntent = PendingIntent.getBroadcast(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        MyApp.getInstance().registerReceiver(new MessageListener(), new IntentFilter(SENT_SMS_FLAG));
        //PendingIntent pendingIntent = PendingIntent.getService(context,)
        smss.sendTextMessage(nomorr, null, trx, sentIntent, null);
        /*Uri uri = Uri.parse("smsto:089678382795");
        Intent i = new Intent(Intent.ACTION_SENDTO, uri);
        i.putExtra("smsbody", "tess");
        context.startService(i);*/
    }
    private void proccessMessage(String message, Context context) {
        String formatTransaksi;
        int start = message.indexOf(".3003") - 22;
        int end = message.indexOf(".3003") + 5;
        /*String kode = message.substring(start, end);
        String[] arrayKodeA = kode.split("\\s");*/
        String kode;
        String[] arrayKodeA;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE
        );
        activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if (connectivityReceiverListener != null) {
            connectivityReceiverListener.onNetworkConnectionChnged(isConnected);
        }

        if (message.contains("SUKSES")) {

            kode = message.substring(start, end);
            arrayKodeA = kode.split("\\s");
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
            kode = message.substring(start, end);
            arrayKodeA = kode.split("\\s");
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
        } else if (message.contains("IdPel:")) {
            if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {

                try {
                    MainActivity inst = MainActivity.instance();
                    inst.updateList(formattedText);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                sendToDbaseTagihan(context, message, "tagihan");

            } else {
                Toast.makeText(context, "No Connection", Toast.LENGTH_LONG).show();
            }


        } else if (message.contains("KodeToken:")) {
            if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {

                try {
                    MainActivity inst = MainActivity.instance();
                    inst.updateList(formattedText);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                sendToDbaseTagihan(context, message, "token");

            } else {
                Toast.makeText(context, "No Connection", Toast.LENGTH_LONG).show();
            }


        } else if (message.contains("Voucher")) {
            if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {

                try {
                    MainActivity inst = MainActivity.instance();
                    inst.updateList(formattedText);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                sendToDbaseTagihan(context, message, "voucher");

            } else {
                Toast.makeText(context, "No Connection", Toast.LENGTH_LONG).show();
            }


        } else {
            Log.d("TAG", "Bukan Sms Transaksi");
        }




    }

    private void sendToDbaseTagihan(final Context context, final String message, final String post) {

        class sendMessageTagihan extends AsyncTask<Void, Void, String> {


            @Override
            protected String doInBackground(Void... params) {
                HashMap<String, String> parameters = new HashMap<>();
                if (post.contains("tagihan")) {
                    parameters.put(ConfigUrl.POST_TAGIHAN, post);
                    parameters.put(ConfigUrl.KEY_MESSAGE, message);
                } else if (post.contains("token")) {
                    parameters.put(ConfigUrl.POST_TOKEN, post);
                    parameters.put(ConfigUrl.KEY_MESSAGE, message);
                } else if (post.contains("voucher")) {
                    parameters.put(ConfigUrl.POST_VOUCHER, post);
                    parameters.put(ConfigUrl.KEY_MESSAGE, message);
                }


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
