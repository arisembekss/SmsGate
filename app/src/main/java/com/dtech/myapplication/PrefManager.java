package com.dtech.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by aris on 20/02/17.
 */

public class PrefManager {
    SharedPreferences pref;
    public SharedPreferences.Editor editor;
    String userDisplayName;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "app-welcome";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static final String IS_FIRST_TIME_TEMP = "IsFirstTimeTemp";
    /*private static final String IS_FIRST_TIME_ADD = "IsFirstTimeAdd";
    private static final String IS_FIRST_TIME_DOMPET = "IsFirstTimeDompet";*/
    private static final String IS_FIRST_TIME_TR = "IsFirstTimeTr";
    private static final String DISPLAY_NAME = "displayName";
    private static final String DISPLAY_NUMBER = "displayNumber";
    private static final String DISPLAY_EMAIL = "displayEmail";
    private static final String DISPLAY_POIN = "poin";
    private static final String DISPLAY_FIREBASE_ID = "firebaseId";
    private static final String DISPLAY_ID = "id";
    private static final String DISPLAY_IDUSR = "idUsr";
    private static final String DISPLAY_STATUS = "status";


    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();

         /*String userDisplayName = pref.getString(DISPLAY_NAME,"");
        this.userDisplayName=userDisplayName;*/
    }

    public void setTempFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_TEMP, isFirstTime);
        editor.commit();
    }

    public boolean isTempFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_TEMP, true);
    }

    /*public void setAddFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_ADD, isFirstTime);
        editor.commit();
    }

    public boolean isAddFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_ADD, true);
    }

    public void setDompetFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_DOMPET, isFirstTime);
        editor.commit();
    }

    public boolean isDompetFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_DOMPET, true);
    }*//*

    public void setTrFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_TR, isFirstTime);
        editor.commit();
    }

    public boolean isTrFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_TR, true);
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void setUserDisplay(String displayName) {
        editor.putString(DISPLAY_NAME, displayName);
        editor.commit();
        //userDisplayName = displayName;
    }

    public void setUserNumber(String displayNumber) {
        editor.putString(DISPLAY_NUMBER, displayNumber);
        editor.commit();
    }

    public void setUserEmail(String displayEmail) {
        editor.putString(DISPLAY_EMAIL, displayEmail);
        editor.commit();
    }
*/
    public void setFirebaseId(String firebaseId) {
        editor.putString(DISPLAY_FIREBASE_ID, firebaseId);
        editor.commit();
    }
/*
    public void setPoin(String poin) {
        editor.putString(DISPLAY_POIN, poin);
        editor.commit();
    }

    public void setUri(String uri) {
        editor.putString(DISPLAY_ID, uri);
        editor.commit();
    }

    public void setStatus(String status) {
        editor.putString(DISPLAY_STATUS, status);
        editor.commit();
    }

    public void setIdUsr(String idUsr) {
        editor.putString(DISPLAY_IDUSR, idUsr);
        editor.commit();
    }

    public String getUserDisplay() {
        userDisplayName = PreferenceManager.getDefaultSharedPreferences(_context).getString(DISPLAY_NAME, "");
        return userDisplayName;
    }

    public String getUserDisplayName() {
        return userDisplayName;
    }

    public void setUserDisplayName(String userDisplayName) {
        this.userDisplayName = userDisplayName;
    }*/
}
