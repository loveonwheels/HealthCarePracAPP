package com.mSIHAT.hcp.Service;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;
import android.util.Log;

import com.mSIHAT.hcp.R;
import com.mSIHAT.hcp.db.DBAppRequest;
import com.mSIHAT.hcp.db.dbclasses.AppReqTra;
import com.mSIHAT.hcp.model.AppRequest;
import com.mSIHAT.hcp.model.Helper;
import com.mSIHAT.hcp.webapi.RestUserService;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestService extends Service {
    private RestUserService restUserService = new RestUserService();
    private static final String TAG = "HelloService";
    public int hcp_id;
    private boolean isRunning  = false;
    public int waitingtime = 10000;
    DBAppRequest dbhelper;

    Helper helper;

    Drawer.Result drawerResult = null;
    protected AccountHeader.Result headerResult = null;

    @Override
    public void onCreate() {
        Log.e(TAG, "Service onCreate");

        isRunning = true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
         hcp_id = intent.getIntExtra("hcpid",0);

       dbhelper = new DBAppRequest(getBaseContext());
        //Creating new thread for my service
        new Thread(new Runnable() {
            @Override
            public void run() {

                while(isRunning){
                    try {

                        Call<List<AppRequest>> call = restUserService.getService().GetRequests(hcp_id);
                        call.enqueue(new Callback<List<AppRequest>>() {
                            @Override
                            public void onResponse(Call<List<AppRequest>> call, Response<List<AppRequest>> response) {
                                int statusCode = response.code();
                                List<AppRequest> userAppiontments = response.body();
                                String msg = "here";

                                if (statusCode == 200) {
                                    waitingtime = 10000;
                                    List<Integer> appiontmentids = new ArrayList<>();

                                    for(int i =0;i< userAppiontments.size();i++){
                                        try{

                                            dbhelper.setRequest(new AppReqTra(userAppiontments.get(i)));
                                        }catch (Exception e){
                                        }

                                        appiontmentids.add(userAppiontments.get(i).appiontmentid);
                                    }


                                    if(userAppiontments.size() > 0){
                                        updateRequests(appiontmentids);
                                    }


                                    //  progress.dismiss();
                                }else{
                                    Log.e("dfdf2", msg);
                                    msg="error again";
                                    waitingtime = 100;
                                }
                                Log.e("dfdf1", msg);

                            }

                            @Override
                            public void onFailure(Call<List<AppRequest>> call, Throwable t) {
                                //   progress.dismiss();
                                waitingtime = 100;
                             //   Log.e("dfdf", t.toString());
                            }
                        });

                        Thread.sleep(waitingtime);
                    } catch (Exception e) {
                    }

                    Log.i(TAG, "Service running");
                }

                //Your logic that service will perform will be placed here
                //In this example we are just looping and waits for 1000 milliseconds in each loop.

                //Stop service once it finishes its task
                stopSelf();
            }
        }).start();

        return Service.START_STICKY;
    }


    public void updateRequests(List<Integer> appiontments){
        Call<Boolean> call = restUserService.getService().updateRequests(appiontments);
        Log.e("updaterequest value",String.valueOf(appiontments.size()));
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                int statusCode = response.code();
                String msg = "sucess";


                if (statusCode == 200) {
                    Cursor c = dbhelper.getRequests();
                    if(c.getCount() != 0){
                        drawerResult.updateBadge(""+c.getCount(), 4);
                        drawerResult.updateIcon(R.mipmap.ic_launcher, 4);
                    } else {
                        drawerResult.updateBadge("", 4);
                        drawerResult.updateIcon(R.mipmap.ic_launcher, 4);
                    }
                    //  progress.dismiss();
                }else{
                    msg="error again";
                }

            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                //   progress.dismiss();

            }
        });
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onDestroy() {

        isRunning = false;
    }
}