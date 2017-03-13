package com.mSIHAT.hcp.Notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.mSIHAT.hcp.FirstPage;
import com.mSIHAT.hcp.MainActivity;
import com.mSIHAT.hcp.R;
import com.mSIHAT.hcp.Central;
import com.mSIHAT.hcp.util.Constants;

import java.util.Date;

public class FireMsgService extends FirebaseMessagingService {


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
 
        Log.d("Msg", "Message received ["+remoteMessage.getMessageId()+"]");
        Intent intent = new Intent(this, FirstPage.class);
        PendingIntent pendingIntent;
        // Create Notification
        SharedPreferences editor2 = getSharedPreferences(Constants.SHARED_DB, MODE_PRIVATE);
       Boolean opened = editor2.getBoolean("opened",false);


            if(opened){
                intent = new Intent(this, Central.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                pendingIntent = PendingIntent.getActivity(this, 1410,
                        intent, PendingIntent.FLAG_ONE_SHOT);
            }else{

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                pendingIntent = PendingIntent.getActivity(this, 1410,
                        intent, PendingIntent.FLAG_ONE_SHOT);
            }










 
        NotificationCompat.Builder notificationBuilder = new
                 NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.hcpicon2)
                .setContentTitle(remoteMessage.getNotification().getTitle())
                .setContentText(remoteMessage.getNotification().getBody())
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);
 
        NotificationManager notificationManager =
                (NotificationManager) 
                getSystemService(Context.NOTIFICATION_SERVICE);

        int m = (int)((new Date().getTime()/ 1000L) % Integer.MAX_VALUE);
        notificationManager.notify(m, notificationBuilder.build());

        Intent intent2 = new Intent();
        intent2.setAction("UpdateLocation");
        intent2.putExtra("DATAPASSED", 3);
        sendBroadcast(intent2);
        Log.d("msg1","sent");
    }
}