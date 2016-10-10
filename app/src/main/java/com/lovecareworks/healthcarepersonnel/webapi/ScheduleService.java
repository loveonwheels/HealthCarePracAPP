package com.lovecareworks.healthcarepersonnel.webapi;



import com.lovecareworks.healthcarepersonnel.classes.BasicHCP;
import com.lovecareworks.healthcarepersonnel.classes.HCP;
import com.lovecareworks.healthcarepersonnel.classes.HCPAuthentication;
import com.lovecareworks.healthcarepersonnel.classes.HCPtest;
import com.lovecareworks.healthcarepersonnel.classes.hcpinfo;
import com.lovecareworks.healthcarepersonnel.model.AppRequest;
import com.lovecareworks.healthcarepersonnel.model.Appiontments;
import com.lovecareworks.healthcarepersonnel.model.ScheduleSlotHolder;
import com.lovecareworks.healthcarepersonnel.model.myScheduleSlot;

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
    Call<Boolean> SetSchedule(@Body ScheduleSlotHolder schedule);


    @GET("Hcp_Schedule/GetSchedule2")
    Call<ScheduleSlotHolder> GetSchedule2(@Query("hcp_id") int hcp_id, @Query("date") String date);
}