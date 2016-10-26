package com.lovecareworks.healthcarepersonnel;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.lovecareworks.healthcarepersonnel.R;
import com.lovecareworks.healthcarepersonnel.Service.LocationService;
import com.lovecareworks.healthcarepersonnel.classes.LocationUpdate;
import com.lovecareworks.healthcarepersonnel.db.DBHelper;
import com.lovecareworks.healthcarepersonnel.fragements.AppRequestContain;
import com.lovecareworks.healthcarepersonnel.fragements.Home;
import com.lovecareworks.healthcarepersonnel.model.Utils;
import com.lovecareworks.healthcarepersonnel.util.Constants;
import com.lovecareworks.healthcarepersonnel.webapi.RestLocationService;
import com.lovecareworks.healthcarepersonnel.webapi.RestPractitionerService;
import com.lovecareworks.healthcarepersonnel.webapi.RestUserService;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Central extends AppCompatActivity {
    Drawer.Result drawerResult = null;
    protected AccountHeader.Result headerResult = null;
    AppCompatActivity main;
    int hcpid = 0;
    public RestLocationService restLocationService = new RestLocationService();
    private RestPractitionerService practService = new RestPractitionerService();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_central);
       getSupportActionBar().hide();

        main = this;
        hcpid= getIntent().getExtras().getInt("hcp_id");
        DBHelper dbhelper = new DBHelper(getApplicationContext());
        Intent ResquestServiceIntent = new Intent(this,LocationService.class);
        ResquestServiceIntent.putExtra("hcpid", 1);
        startService(ResquestServiceIntent);
       // stopService(ResquestServiceIntent);

        Log.e("sfdfdf", "Dfdfdf");
        String tkn = FirebaseInstanceId.getInstance().getToken();
        Log.d("Token oh24","Token ["+tkn+"]");


        SharedPreferences prefs = getSharedPreferences(Constants.SHARED_DB, MODE_PRIVATE);
        int restoredUser = prefs.getInt("userid", 0);

        if(restoredUser == 0){
            Log.e("restoused ","empty");
            SharedPreferences.Editor editor = getSharedPreferences(Constants.SHARED_DB, MODE_PRIVATE).edit();
            editor.putInt("userid", hcpid);
            editor.commit();
        }

        if(tkn != null){
            String restoredText = prefs.getString("fireToken", null);
            if (restoredText != null) {
                if(tkn != restoredText){
                    SharedPreferences.Editor editor = getSharedPreferences(Constants.SHARED_DB, MODE_PRIVATE).edit();
                    editor.putInt("userid", hcpid);
                    editor.putString("fireToken", tkn);
                    editor.commit();
                    updatetoken(hcpid,tkn);

                }

            }else{
                SharedPreferences.Editor editor = getSharedPreferences(Constants.SHARED_DB, MODE_PRIVATE).edit();
                editor.putInt("userid", hcpid);
                editor.putString("fireToken", tkn);
                editor.commit();
                updatetoken(hcpid,tkn);
            }
            SharedPreferences.Editor editor = getSharedPreferences(Constants.SHARED_DB, MODE_PRIVATE).edit();
            editor.putInt("userid", hcpid);
            editor.putString("fireToken", tkn);
            editor.commit();
            updatetoken(hcpid,tkn);

        }

        drawerResult = Utils.createCommonDrawer(Central.this, headerResult);
        getSupportFragmentManager().beginTransaction().add(R.id.framei, new Home(), "home").setCustomAnimations(R.anim.slide_in_left, 0).addToBackStack("r1").commit();

    }

    public void open(){
        drawerResult.openDrawer();

    }

    public int gethcp_id(){
        return hcpid;
    }

    public void removefragment(){
        getSupportFragmentManager().beginTransaction().replace(R.id.framei, new AppRequestContain(),"apprequestcontainer").commit();
    }


    public void updatelocation(LocationUpdate Location){
        Call<Boolean> call = restLocationService.getService().updateLocation(Location);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                int statusCode = response.code();
                String msg = "sucess";


                if (statusCode == 200) {
                   msg="update place";
                    Log.e("update location",msg);

                }else{
                    msg="error again";
                    Log.e("update location",msg+statusCode);
                }

            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                //   progress.dismiss();
                Log.e("update location",t.toString());

            }
        });



    }


    public static class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context arg0, Intent arg1) {
            // TODO Auto-generated method stub

            Log.e("confiremed","sdsd");

        }
    }




    public void updatetoken(int userid,String token){

        Log.e(String.valueOf(userid),String.valueOf(token));
        Call<Boolean> practSer = practService.getService().Updatetokenhcp(userid,token);
        practSer.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.code() == 201){



                }
                else {

                    Log.e("completed token2","completed");
                }
                Log.e("response",String.valueOf(response.code()));

            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });
    }

}
