package com.dtech.myapplication;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URI;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String message;
    private static MainActivity inst;
    ArrayAdapter arrayAdapter;
    ArrayList<String> smsMessageList = new ArrayList<>();
    ListView smsListview;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
