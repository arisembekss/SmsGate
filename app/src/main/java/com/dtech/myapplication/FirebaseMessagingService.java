package com.dtech.myapplication;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

import java.util.List;

/**
 * Created by aris on 20/02/17.
 */

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";
    String formatTrx;
    private static final String SMS_SENT = "my.app";
    private static final String SMS_DELIVERED = "my.appp";
    IntentFilter filter;
    String nomor = "089678382795";

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            /*String action = intent.getAction();

            if(action.equals(FirebaseMessaging.getInstance())){
                String nomor = "089678382795";
                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage(nomor, null, formatTrx, null, null);
            }
            else if(action.equals(android.telephony.TelephonyManager.ACTION_PHONE_STATE_CHANGED)){

            }*/
            String message = null;

            switch (getResultCode()) {
                case Activity.RESULT_OK:
                    message = "Message sent!";
                    break;
                case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                    message = "Error. Message not sent.";
                    break;
                case SmsManager.RESULT_ERROR_NO_SERVICE:
                    message = "Error: No service.";
                    break;
                case SmsManager.RESULT_ERROR_NULL_PDU:
                    message = "Error: Null PDU.";
                    break;
                case SmsManager.RESULT_ERROR_RADIO_OFF:
                    message = "Error: Radio off.";
                    break;
            }


        }
    };
    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]


        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            //formatTrx = remoteMessage.getData().get("message");
            //sendSms(formatTrx);
            //sendData(remoteMessage);
            //sendSms(remoteMessage);
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());

        }

        formatTrx = remoteMessage.getNotification().getBody();
        PendingIntent piSend = PendingIntent.getBroadcast(this, 0, new Intent(SMS_SENT), 0);
        PendingIntent piDelivered = PendingIntent.getBroadcast(this, 0, new Intent(SMS_DELIVERED), 0);



        sendNotification(remoteMessage);



        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private void sendNotification(RemoteMessage messageBody) {
        Intent intent;
        String click = messageBody.getNotification().getClickAction();

        /*SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(nomor, null, formatTrx, null, null);*/
        //sendSms(formatTrx);

        //formatTrx = messageBody.getData().get("message");
        //Log.d(TAG, formatTrx);
        if (TextUtils.isEmpty(click)) {
            intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        } else {
            intent = new Intent(click);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);


        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        android.support.v4.app.NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_grain_black_24dp)
                .setContentTitle("Smart Pulsa")
                .setContentText(messageBody.getNotification().getBody())
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
        //registerReceiver(receiver, filter);
        //
    }

    public void sendSms(final String formatTrx) {
        final String nomorr = "089634558000";
        final SmsManager smss = SmsManager.getDefault();
        //final String fformatTrx = formatTrx.getData().get("message");
        // if message length is too long messages are divided
        //List<String> messages = sms.divideMessage(formatTrx);
        //for (String msg : messages) {
        /*Intent in = new Intent(this, SmsSentReceiver.class);
        in.putExtra("format", formatTrx);
        startService(in);*/
        Log.d(TAG, "intent in");
        new Thread(new Runnable() {
            @Override
            public void run() {
                smss.sendTextMessage(nomorr, null, formatTrx, null, null);
            }
        }).start();

        /*PendingIntent pi = PendingIntent.getActivities(this, 0, new Intent[]{in}, PendingIntent.FLAG_ONE_SHOT);

        try {
            pi.send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }*/
        /*PendingIntent sentIntent =  PendingIntent.getBroadcast(getApplicationContext(), 0,
                    new Intent(getApplicationContext(), SmsSentReceiver.class), 0);
            PendingIntent deliveredIntent = PendingIntent.getBroadcast(getApplicationContext(), 0,
                    new Intent(getApplicationContext(), SmsDeliveredReceiver.class), 0);*/
            //sms.sendTextMessage(nomor, null, msg, sentIntent, deliveredIntent);

        //}
       /* try {
            sms.sendTextMessage(nomor, null, formatTrx, sentIntent, deliveredIntent);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Failed to send sms", Toast.LENGTH_SHORT).show();
        }*/
        //sms.sendTextMessage(nomor, null, formatTrx, null, null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        filter = new IntentFilter(SMS_SENT);
        //filter.addAction("com.google.firebase.MESSAGING_EVENT");
        //filter.addAction(formatTrx);
        registerReceiver(receiver, filter);
    }
}
