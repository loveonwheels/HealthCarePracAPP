package com.mSIHAT.hcp.model;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;

import com.mSIHAT.hcp.R;
import com.mSIHAT.hcp.dialogs.MySchedule;
import com.mSIHAT.hcp.fragements.AppRequestContain;
import com.mSIHAT.hcp.fragements.Contact_Us;
import com.mSIHAT.hcp.fragements.Home;
import com.mSIHAT.hcp.fragements.PendingReport;
import com.mSIHAT.hcp.fragements.Schedule;
import com.mSIHAT.hcp.fragements.notificationhub;
import com.mSIHAT.hcp.fragements.profile;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

/*import com.asar.my.main.fragments.FragmentAlerts;
import com.asar.my.main.fragments.FragmentHelp;
import com.asar.my.main.fragments.FragmentProfileManagement;
import com.asar.my.main.fragments.FragmentReport;
import com.asar.my.main.fragments.FragmentSchedule;
import com.asar.my.main.fragments.FragmentSettings;*/

public class Utils {

    public static final int ACCOUNTS_MANAGE_ID = 111;

    public static void hideSoftKeyboard(Activity activity) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            //
        }

    }


    public static Drawer.OnDrawerItemClickListener handlerOnClick(final Drawer.Result drawerResult, final FragmentActivity activity) {
        return new Drawer.OnDrawerItemClickListener() {
            Helper h = new Helper(activity.getApplicationContext());

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                //check if the drawerItem is set.
                //there are different reasons for the drawerItem to be null
                //--> click on the header
                //--> click on the footer
                //those items don't contain a drawerItem

                if (drawerItem != null) {

                    if (drawerItem.getIdentifier() == 1) {
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.framei, new Home()).commit();
                    } else if (drawerItem.getIdentifier() == 2) {
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.framei, new notificationhub()).commit();
                    } else if (drawerItem.getIdentifier() == 3) {
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.framei, new Schedule()).commit();
                          } else if (drawerItem.getIdentifier() == 4) {
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.framei, new PendingReport(),"apprequestcontainer").commit();
                    } else if (drawerItem.getIdentifier() == 5) {
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.framei, new AppRequestContain()).commit();
                    }else if (drawerItem.getIdentifier() == 6) {
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.framei, new profile()).commit();
                    }
                    else if (drawerItem.getIdentifier() == 7) {
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.framei, new Contact_Us()).commit();
                    } else if (drawerItem.getIdentifier() == 8) {
                        activity.finish();
                    }
                }
            }
        };
    }



    public static Drawer.Result createCommonDrawer(final FragmentActivity activity, AccountHeader.Result headerResult) {

        Helper helper = new Helper(activity.getApplicationContext());
        Drawer.Result drawerResult;




            drawerResult = new Drawer()
                    .withActivity(activity)
                    .withHeader(R.layout.drawer_header)
                    .withTranslucentStatusBar(true)
                    .withActionBarDrawerToggle(false)
                    .addDrawerItems(
                            new PrimaryDrawerItem().withName("Home").withIdentifier(1),
                            new PrimaryDrawerItem().withName("Notifications").withIdentifier(2),
                            new PrimaryDrawerItem().withName("Schedule").withIdentifier(3),
                            new PrimaryDrawerItem().withName("Pending Reports").withIdentifier(4),
                            new PrimaryDrawerItem().withName("Profile").withIdentifier(6),
                            new PrimaryDrawerItem().withName("Contact Us").withIdentifier(7),
                            new PrimaryDrawerItem().withName("Log Out").withIdentifier(8)
/*
        new PrimaryDrawerItem().withName("Home").withIcon(GoogleMaterial.Icon.gmd_home).withIdentifier(1),
                new PrimaryDrawerItem().withName("Schedule").withIcon(GoogleMaterial.Icon.gmd_schedule).withIdentifier(3),
                new PrimaryDrawerItem().withName("Pending Reports").withIcon(GoogleMaterial.Icon.gmd_receipt).withIdentifier(4),
                new PrimaryDrawerItem().withName("Profile").withIcon(GoogleMaterial.Icon.gmd_account_circle).withIdentifier(6),
                new PrimaryDrawerItem().withName("Contact Us").withIcon(GoogleMaterial.Icon.gmd_mail).withIdentifier(7),
                new PrimaryDrawerItem().withName("Log Out").withIcon(R.drawable.ic_logout_24dp_color_text).withIdentifier(8)

                */
                    ).withOnDrawerListener(new Drawer.OnDrawerListener() {
                        @Override
                        public boolean equals(Object o) {
                            return super.equals(o);
                        }

                        @Override
                        public void onDrawerOpened(View drawerView) {
                            hideSoftKeyboard(activity);
                        }

                        @Override
                        public void onDrawerClosed(View drawerView) {
                        }
                        @Override
                        public void onDrawerSlide(View drawerView, float input) {}

                    }).build();



        drawerResult.setOnDrawerItemClickListener(handlerOnClick(drawerResult, activity));

        return drawerResult;
    }
}
