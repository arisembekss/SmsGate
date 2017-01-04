package com.dtech.myapplication;

/**
 * Created by dtech on 20/11/2016.
 */

public class ConfigUrl {

    public static final String URL_TEST = "http://samimi.web.id/dev/admin/update-log-transaksi.php";//here you can input location of your PHP file
                                             //for example : http://samimi.web.id/dev/add-code.php
                                             //my PHP file are -> add-code.php

    public static final String URL_TAGIHAN = "http://samimi.web.id/dev/admin/fbase-tagihan.php";
    //this parts are POST Request variables (take a look on my add-code.php in Documentation
    public static final String POST_TAGIHAN = "tagihan";//this is variable for POST Request: $_POST['name']
    public static final String KEY_NAME = "name";
    public static final String KEY_KODE = "kode";//this is variable for POST Request: $_POST['kode']
    public static final String KEY_MESSAGE = "message";
    public static final String POST_TOKEN = "token";
    public static final String POST_VOUCHER = "voucher";

    /*we only show example insert for 1 mysql table
    * if you want to insert for multiple tables you must preparing PHP file first,
    * then add variable URL here.
    * after that, register all POST Request variable here too
    * and finally make AsyncTask function to execute the data
    * (take a look function : sendToDbase in Receiver.java)*/
}
