package com.mSIHAT.hcp;


import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.firebase.iid.FirebaseInstanceId;
import com.mSIHAT.hcp.R;
import com.mSIHAT.hcp.Service.LocationService;
import com.mSIHAT.hcp.classes.LocationUpdate;
import com.mSIHAT.hcp.db.DBHelper;
import com.mSIHAT.hcp.fragements.AppRequestContain;
import com.mSIHAT.hcp.fragements.Home;
import com.mSIHAT.hcp.fragements.Schedule;
import com.mSIHAT.hcp.fragements.profile;
import com.mSIHAT.hcp.model.Utils;
import com.mSIHAT.hcp.util.Constants;
import com.mSIHAT.hcp.webapi.RestLocationService;
import com.mSIHAT.hcp.webapi.RestPractitionerService;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Central extends AppCompatActivity {
    Drawer.Result drawerResult = null;
    protected AccountHeader.Result headerResult = null;
    AppCompatActivity main;
    int hcpid = 0;
    String imageurl = "";
   CircularImageView drawerimage;
    private static boolean activityVisible = false;
    public RestLocationService restLocationService = new RestLocationService();
    private RestPractitionerService practService = new RestPractitionerService();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences editor4 = getSharedPreferences(Constants.SHARED_DB, MODE_PRIVATE);
        Boolean opened = editor4.getBoolean("opened",false);
        setContentView(R.layout.activity_central);


        if(!opened) {


           // getSupportActionBar().hide();
            SharedPreferences.Editor editor2 = getSharedPreferences(Constants.SHARED_DB, MODE_PRIVATE).edit();
            editor2.putBoolean("opened", true);
            editor2.commit();

            activityVisible = true;
            main = this;

           try {
                hcpid = getIntent().getExtras().getInt("hcp_id");
                imageurl = getIntent().getExtras().getString("imageurl");

                DBHelper dbhelper = new DBHelper(getApplicationContext());
                Intent ResquestServiceIntent = new Intent(this, LocationService.class);
                ResquestServiceIntent.putExtra("hcpid", 1);
                startService(ResquestServiceIntent);
                // stopService(ResquestServiceIntent);

                Log.e("sfdfdf", "Dfdfdf");
                String tkn = FirebaseInstanceId.getInstance().getToken();
                Log.d("Token oh24", "Token [" + tkn + "]");


                SharedPreferences prefs = getSharedPreferences(Constants.SHARED_DB, MODE_PRIVATE);
                int restoredUser = prefs.getInt("userid", 0);

                if (restoredUser == 0) {
                    Log.e("restoused ", "empty");
                    SharedPreferences.Editor editor = getSharedPreferences(Constants.SHARED_DB, MODE_PRIVATE).edit();
                    editor.putInt("userid", hcpid);
                    editor.commit();
                }

                if (tkn != null) {
                    String restoredText = prefs.getString("fireToken", null);
                    if (restoredText != null) {
                        if (tkn != restoredText) {
                            SharedPreferences.Editor editor = getSharedPreferences(Constants.SHARED_DB, MODE_PRIVATE).edit();
                            editor.putInt("userid", hcpid);
                            editor.putString("fireToken", tkn);
                            editor.commit();
                            updatetoken(hcpid, tkn);

                        }

                    } else {
                        SharedPreferences.Editor editor = getSharedPreferences(Constants.SHARED_DB, MODE_PRIVATE).edit();
                        editor.putInt("userid", hcpid);
                        editor.putString("fireToken", tkn);
                        editor.commit();
                        updatetoken(hcpid, tkn);
                    }
                    SharedPreferences.Editor editor = getSharedPreferences(Constants.SHARED_DB, MODE_PRIVATE).edit();
                    editor.putInt("userid", hcpid);
                    editor.putString("fireToken", tkn);
                    editor.commit();
                    updatetoken(hcpid, tkn);

                }

                drawerResult = Utils.createCommonDrawer(Central.this, headerResult);

                drawerimage = (CircularImageView) drawerResult.getHeader().findViewById(R.id.drawerimage);
                Picasso.with(main)
                        .load(imageurl)
                        .error(R.drawable.profile)
                        .placeholder(R.drawable.profile)
                        .fit().centerCrop()
                        .networkPolicy(NetworkPolicy.NO_CACHE)
                        .memoryPolicy(MemoryPolicy.NO_CACHE)
                        .into(drawerimage);

               drawerimage.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       getSupportFragmentManager().beginTransaction().replace(R.id.framei, new profile()).commit();
                   }
               });
                getSupportFragmentManager().beginTransaction().add(R.id.framei, new Home(), "home").setCustomAnimations(R.anim.slide_in_left, 0).addToBackStack("r1").commit();

            } catch (Exception ex) {

                SharedPreferences.Editor editor3 = getSharedPreferences(Constants.SHARED_DB, MODE_PRIVATE).edit();
                editor3.putBoolean("opened", false);
                editor3.commit();
                Intent intent = new Intent(this, FirstPage.class);
                startActivity(intent);
            }
        }else{


                    SharedPreferences editor5 = getSharedPreferences(Constants.SHARED_DB, MODE_PRIVATE);
                 hcpid = editor5.getInt("userid",0);

            getSupportActionBar().hide();
            drawerResult = Utils.createCommonDrawer(Central.this, headerResult);
            drawerimage = (CircularImageView) drawerResult.getHeader().findViewById(R.id.drawerimage);
            Picasso.with(main)
                    .load(imageurl)
                    .error(R.drawable.profile)
                    .placeholder(R.drawable.profile)
                    .fit().centerCrop()
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .into(drawerimage);

            drawerimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.framei, new profile()).commit();
                }
            });
         getSupportFragmentManager().beginTransaction().replace(R.id.framei, new Schedule()).commit();
        }
    }

    public void open(){
        drawerResult.openDrawer();

    }

    public static boolean isActivityVisible() {
        return activityVisible;
    }

    public static void activityResumed() {
        activityVisible = true;
    }

    public static void activityPaused() {
        activityVisible = false;
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
