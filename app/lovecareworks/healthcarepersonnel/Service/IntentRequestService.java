package com.mSIHAT.hcp.Service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import com.mSIHAT.hcp.webapi.RestUserService;

public class IntentRequestService extends IntentService {
    private RestUserService restUserService = new RestUserService();
    public static final int STATUS_RUNNING = 0;
    public static final int STATUS_FINISHED = 1;
    public static final int STATUS_ERROR = 2;

    private static final String TAG = "ARequestService";

    public IntentRequestService() {
        super(IntentRequestService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Log.d(TAG, "Service Started!");

        final ResultReceiver receiver = intent.getParcelableExtra("receiver");
        String url = intent.getStringExtra("url");

       int hcpid = intent.getIntExtra("hcpid", 0);

        Bundle bundle = new Bundle();

        Log.d(TAG, "Service Stopping!");
        this.stopSelf();
    }




}