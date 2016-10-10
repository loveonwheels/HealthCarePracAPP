package com.lovecareworks.healthcarepersonnel.model;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by admin on 13-Aug-15.
 */
public class Helper {
    private SharedPreferences sprefs;
    private static Helper mInstance = null;

    public static Helper getInstance(Context ctx) {
        if (mInstance == null) {
            mInstance = new Helper(ctx.getApplicationContext());
        }
        return mInstance;
    }

    public Helper(Context ctx) {
        super();
    }

    public boolean hasRegistered(final Context mContext) {
        sprefs = mContext.getApplicationContext().getSharedPreferences(Keys._SHARED_PREFERENCES, Context.MODE_PRIVATE);
        if(sprefs.contains(Keys._register)) {
            return sprefs.getBoolean(Keys._register, false);
        }
        return false;
    }

    public void setRegister(boolean yes, Context mContext) {
        sprefs = mContext.getApplicationContext().getSharedPreferences(Keys._SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sprefs.edit();
        editor.putBoolean(Keys._register, yes);
        editor.commit();
    }

    public boolean hasAdmin(final Context mContext) {
        sprefs = mContext.getApplicationContext().getSharedPreferences(Keys._SHARED_PREFERENCES, Context.MODE_PRIVATE);
        if(sprefs.contains(Keys._admin)) {
            return sprefs.getBoolean(Keys._admin, false);
        }
        return false;
    }

    public void setAdmin(boolean yes, Context mContext) {
        sprefs = mContext.getApplicationContext().getSharedPreferences(Keys._SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sprefs.edit();
        editor.putBoolean(Keys._admin, yes);
        editor.commit();
    }

    public boolean hasName(final Context mContext) {
        sprefs = mContext.getApplicationContext().getSharedPreferences(Keys._SHARED_PREFERENCES, Context.MODE_PRIVATE);
        if(sprefs.contains(Keys._name)) {
            String str  = sprefs.getString(Keys._name, "");
            if(!str.equals("")) {
                return true;
            }
            return false;
        }
        return false;
    }

    public void setName(String name, Context mContext) {
        sprefs = mContext.getApplicationContext().getSharedPreferences(Keys._SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sprefs.edit();
        editor.putString(Keys._name, name);
        editor.commit();
    }

    public String getName(final Context mContext) {
        String name = "";
        sprefs = mContext.getApplicationContext().getSharedPreferences(Keys._SHARED_PREFERENCES, Context.MODE_PRIVATE);
        if(sprefs.contains(Keys._name)) {
            String str  = sprefs.getString(Keys._name, "");
            if(!str.equals("")) {
                name = str;
            }
        }
        return name;
    }

    public void setNRIC(String nric, Context mContext) {
        sprefs = mContext.getApplicationContext().getSharedPreferences(Keys._SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sprefs.edit();
        editor.putString(Keys._nric, nric);
        editor.commit();
    }

    public String getNRIC(final Context mContext) {
        String nric = "";
        sprefs = mContext.getApplicationContext().getSharedPreferences(Keys._SHARED_PREFERENCES, Context.MODE_PRIVATE);
        if(sprefs.contains(Keys._nric)) {
            String str  = sprefs.getString(Keys._nric, "");
            if(!str.equals("")) {
                nric = str;
            }
        }
        return nric;
    }

    public boolean hasMphone(final Context mContext) {
        sprefs = mContext.getApplicationContext().getSharedPreferences(Keys._SHARED_PREFERENCES, Context.MODE_PRIVATE);
        if(sprefs.contains(Keys._mphone)) {
            String str  = sprefs.getString(Keys._mphone, "");
            if(!str.equals("")) {
                return true;
            }
            return false;
        }
        return false;
    }

    public void setMphone(String mPhone, Context mContext) {
        sprefs = mContext.getApplicationContext().getSharedPreferences(Keys._SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sprefs.edit();
        editor.putString(Keys._mphone, mPhone);
        editor.commit();
    }

    public String getMphone(final Context mContext) {
        String mphone = "";
        sprefs = mContext.getApplicationContext().getSharedPreferences(Keys._SHARED_PREFERENCES, Context.MODE_PRIVATE);
        if(sprefs.contains(Keys._mphone)) {
            String str  = sprefs.getString(Keys._mphone, "");
            if(!str.equals("")) {
                mphone = str;
            }
        }
        return mphone;
    }

    public boolean hasHphone(final Context mContext) {
        sprefs = mContext.getApplicationContext().getSharedPreferences(Keys._SHARED_PREFERENCES, Context.MODE_PRIVATE);
        if(sprefs.contains(Keys._hphone)) {
            String str  = sprefs.getString(Keys._hphone, "");
            if(!str.equals("")) {
                return true;
            }
            return false;
        }
        return false;
    }

    public void setHphone(String hPhone, Context mContext) {
        sprefs = mContext.getApplicationContext().getSharedPreferences(Keys._SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sprefs.edit();
        editor.putString(Keys._hphone, hPhone);
        editor.commit();
    }

    public String getHphone(final Context mContext) {
        String hphone = "";
        sprefs = mContext.getApplicationContext().getSharedPreferences(Keys._SHARED_PREFERENCES, Context.MODE_PRIVATE);
        if(sprefs.contains(Keys._hphone)) {
            String str  = sprefs.getString(Keys._hphone, "");
            if(!str.equals("")) {
                hphone = str;
            }
        }
        return hphone;
    }

    public boolean hasAddress(final Context mContext) {
        sprefs = mContext.getApplicationContext().getSharedPreferences(Keys._SHARED_PREFERENCES, Context.MODE_PRIVATE);
        if(sprefs.contains(Keys._address)) {
            String str  = sprefs.getString(Keys._address, "");
            if(!str.equals("")) {
                return true;
            }
            return false;
        }
        return false;
    }

    public void setAddress(String address, Context mContext) {
        sprefs = mContext.getApplicationContext().getSharedPreferences(Keys._SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sprefs.edit();
        editor.putString(Keys._address, address);
        editor.commit();
    }

    public String getAddress(final Context mContext) {
        String address = "";
        sprefs = mContext.getApplicationContext().getSharedPreferences(Keys._SHARED_PREFERENCES, Context.MODE_PRIVATE);
        if(sprefs.contains(Keys._address)) {
            String str  = sprefs.getString(Keys._address, "");
            if(!str.equals("")) {
                address = str;
            }
        }
        return address;
    }
}
