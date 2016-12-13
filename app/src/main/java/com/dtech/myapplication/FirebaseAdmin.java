package com.dtech.myapplication;

import android.annotation.TargetApi;
import android.os.Build;

import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by aris on 12/12/16.
 */

public class FirebaseAdmin {

    private FirebaseOptions options;
    private DatabaseReference ref;
    private String serviceAccountJSON = "smart-pulsa-firebase-adminsdk-745ry-fe5f7e0bd8";

    // create firebase instance if need be
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void connectToFirebase(){
        /*if (options == null) {
            options = null;
            options = new FirebaseOptions.Builder()
                    .setServiceAccount(new ByteArrayInputStream(serviceAccountJSON.getBytes(StandardCharsets.UTF_8)))
                    .setDatabaseUrl("https://smart-pulsa.firebaseio.com/")
                    .build();
            FirebaseApp.initializeApp(options);
        }
        if(ref == null) {
            ref = FirebaseDatabase.getInstance().getReference();
        }*/
    }

    /** A simple endpoint method that takes a name and says Hi back */
    //@ApiMethod(name = "sayHi")
    /*public MyBean sayHi(@Named("name") String name) {

        // always do this first
        connectToFirebase();

        MyBean mModelClassObject = null;

        final TaskCompletionSource<MyBean> tcs = new TaskCompletionSource<>();
        Task<MyBean> tcsTask = tcs.getTask();

        // get the info
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                MyBean result = dataSnapshot.getValue(MyBean.class);
                if(result != null){
                    tcs.setResult(result);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError){
                //handle error
            }
        });

        // wait for it
        try {
            mModelClassObject = Tasks.await(tcsTask);
        }catch(ExecutionException e){
            //handle exception
        }catch (InterruptedException e){
            //handle exception
        }

        mModelClassObject.setData(mModelClassObject.getData() + name);

        return mModelClassObject;
    }*/
/*
    @Override
    public void onCreate() {
        //File file = new File("src/main/smartpulsa_ba8b5d9f4e06.json");
        *//*String serviceAccount = "smart-pulsa-firebase-adminsdk-745ry-fe5f7e0bd8";
        InputStream stream = new ByteArrayInputStream(serviceAccount.getBytes(StandardCharsets.UTF_8));*//*
        *//*String text = "";

        try {
            String line = "";
            StringBuilder builder = new StringBuilder();
            URL url = new URL("http://samimi.web.id/dev/smart-pulsa-firebase-adminsdk-745ry-fe5f7e0bd8.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));


            while ((line = reader.readLine()) != null) {
                // ...
                builder.append(line);
            }
            reader.close();

            text = builder.toString();
        } catch (MalformedURLException e) {
            // ...
        } catch (IOException e) {
            // ...
        }

        InputStream stream = new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8));*//*



       *//* try {
            options = new FirebaseOptions.Builder()
                    .setServiceAccount()
                    .setDatabaseUrl("https://smart-pulsa.firebaseio.com/")
                    .build();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        FirebaseApp.initializeApp(options);*//*

        FirebaseOptions options = null;

        try {
            options = new FirebaseOptions.Builder()
                    .setServiceAccount(new FileInputStream("webapp/WEB-INF/smart-pulsa-firebase-adminsdk-745ry-fe5f7e0bd8.json"))
                    .setDatabaseUrl("https://smart-pulsa.firebaseio.com")
                    .build();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        FirebaseApp.initializeApp(options);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }*/
}
