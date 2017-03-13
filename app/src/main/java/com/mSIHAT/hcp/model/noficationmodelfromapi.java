package com.mSIHAT.hcp.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by admin on 14-Sep-15.
 */
public class noficationmodelfromapi implements Serializable {
    public int id;
    public int status;
    public int type;
    public int hcp_id;
    public String notification_date;
    public String day;
    public String header;
    public String summary;
    public String time;
    public String details;



}
