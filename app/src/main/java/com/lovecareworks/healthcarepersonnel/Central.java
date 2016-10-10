package com.lovecareworks.healthcarepersonnel;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.lovecareworks.healthcarepersonnel.R;
import com.lovecareworks.healthcarepersonnel.Service.LocationService;
import com.lovecareworks.healthcarepersonnel.classes.LocationUpdate;
import com.lovecareworks.healthcarepersonnel.db.DBHelper;
import com.lovecareworks.healthcarepersonnel.fragements.AppRequestContain;
import com.lovecareworks.healthcarepersonnel.fragements.Home;
import com.lovecareworks.healthcarepersonnel.model.Utils;
import com.lovecareworks.healthcarepersonnel.webapi.RestLocationService;
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

}
