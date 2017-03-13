package com.mSIHAT.hcp.webapi;



import com.mSIHAT.hcp.model.ScheduleSlotHolder;
import com.mSIHAT.hcp.model.ScheduleSlotHolder2;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ScheduleService {

    @POST("Hcp_Schedule/SetSchedule")
    Call<Boolean> SetSchedule(@Body ScheduleSlotHolder2 schedule);


    @GET("Hcp_Schedule/GetSchedule2")
    Call<ScheduleSlotHolder> GetSchedule2(@Query("hcp_id") int hcp_id, @Query("startdate") String startdate, @Query("enddate") String enddate);
}