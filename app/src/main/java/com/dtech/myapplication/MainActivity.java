package com.dtech.myapplication;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "SignInActivity";
    String message;
    private static MainActivity inst;
    ArrayAdapter arrayAdapter;
    ArrayList<String> smsMessageList = new ArrayList<>();
    ListView smsListview;
    PrefManager prefManager;
    SharedPreferences sharedPreferences;
    FirebaseAuth mAuth;
    // [END declare_auth]

    // [START declare_auth_listener]
    FirebaseAuth.AuthStateListener mAuthListener;

    //FirebaseOptions options;

    public static MainActivity instance() {
        return inst;
    }

    TextView tes;
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            message=bundle.getString("message");
        }
    };

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        prefManager = new PrefManager(this);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // [START_EXCLUDE]
                //updateUI(user);
                // [END_EXCLUDE]
            }
        };
        String tokenfbase = FirebaseInstanceId.getInstance().getToken();
        prefManager.setFirebaseId(tokenfbase);
/*
        if (prefManager.isTempFirstTimeLaunch()) {
            String tokenfbase = FirebaseInstanceId.getInstance().getToken();
            prefManager.setFirebaseId(tokenfbase);
            Log.d("firebase id:", ""+tokenfbase);

            prefManager.setTempFirstTimeLaunch(false);
        }
*/

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });



        smsListview = (ListView) findViewById(R.id.listmsg);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, smsMessageList);
        smsListview.setAdapter(arrayAdapter);

        sharedPreferences = getSharedPreferences(ConfigUrl.PREF_NAME, MODE_PRIVATE);
        String fbaseid = (sharedPreferences.getString(ConfigUrl.DISPLAY_FIREBASE_ID, ""));
        Log.d(TAG, fbaseid);
        Toast.makeText(this, fbaseid, Toast.LENGTH_LONG).show();
        refreshSmsList();
    }

    private void refreshSmsList() {
        ContentResolver contentResolver = getContentResolver();
        Cursor smsCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null);
        int indexBody = smsCursor.getColumnIndex("body");
        int indexAddress = smsCursor.getColumnIndex("address");
        if (indexBody < 0 || !smsCursor.moveToFirst()) {
            return;
        }
        arrayAdapter.clear();
        do {
            String str = "Sms From: " + smsCursor.getString(indexAddress) + "\n"+smsCursor.getString(indexBody);
            arrayAdapter.add(str);
        } while (smsCursor.moveToNext());
    }


    public void updateList(final String smsMessage){
        arrayAdapter.insert(smsMessage, 0);
        arrayAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        inst = this;
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

        return super.onOptionsItemSelected(item);
    }
}
