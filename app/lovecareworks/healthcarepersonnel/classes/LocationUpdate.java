package com.mSIHAT.hcp.classes;

import java.util.Date;

/**
 * Created by ghost on 22/8/16.
 */
public class LocationUpdate {

    public int hcp_id;
    public Date update_date;
    public double location_lat;
    public double location_long;

    public LocationUpdate(int hcp_id, Date update_date, double location_lat, double location_long) {
        this.hcp_id = hcp_id;
        this.update_date = update_date;
        this.location_lat = location_lat;
        this.location_long = location_long;
    }



}
