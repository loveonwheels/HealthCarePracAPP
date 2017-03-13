package com.mSIHAT.hcp.webapi;



import com.mSIHAT.hcp.classes.BasicHCP;
import com.mSIHAT.hcp.classes.HCP;
import com.mSIHAT.hcp.classes.HCPAuthentication;
import com.mSIHAT.hcp.classes.HCPtest;
import com.mSIHAT.hcp.classes.hcpinfo;
import com.mSIHAT.hcp.model.AppRequest;
import com.mSIHAT.hcp.model.Appiontments;
import com.mSIHAT.hcp.model.ScheduleSlotHolder;
import com.mSIHAT.hcp.model.ScheduleSlotHolder2;
import com.mSIHAT.hcp.model.myScheduleSlot;

import org.parceler.Generated;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ScheduleService {

    @POST("Hcp_Schedule/SetSchedule")
    Call<Boolean> SetSchedule(@Body ScheduleSlotHolder2 schedule);


    @GET("Hcp_Schedule/GetSchedule2")
    Call<ScheduleSlotHolder> GetSchedule2(@Query("hcp_id") int hcp_id, @Query("startdate") String startdate, @Query("enddate") String enddate);
}