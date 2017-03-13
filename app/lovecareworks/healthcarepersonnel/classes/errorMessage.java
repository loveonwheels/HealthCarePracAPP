package com.mSIHAT.hcp.classes;

import android.text.Html;
import android.text.Spanned;

public class errorMessage {

    private static boolean satus = false;
    private static String message = "";


    public errorMessage(String Message) {
        satus = true;
        message = message + "<br>" + Message;
    }

    public errorMessage(){}


    public static void clear(){
        message = "";
        satus = false;
    }

    public boolean isSatus() {return satus;}

    public Spanned getMessage(){return Html.fromHtml(message);}

}
