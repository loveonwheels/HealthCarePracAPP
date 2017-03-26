package com.mSIHAT.hcp.webapi;



import com.mSIHAT.hcp.classes.Equipmentdetails;
import com.mSIHAT.hcp.classes.HCPAuthentication;
import com.mSIHAT.hcp.model.ScheduleSlotHolder;
import com.mSIHAT.hcp.model.ScheduleSlotHolder2;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface ScheduleService {

    @POST("Hcp_Schedule/SetSchedule")
    Call<Boolean> SetSchedule(@Body ScheduleSlotHolder2 schedule);


    @GET("Hcp_Schedule/GetSchedule2")
    Call<ScheduleSlotHolder> GetSchedule2(@Query("hcp_id") int hcp_id, @Query("startdate") String startdate, @Query("enddate") String enddate);

    @GET("Hcp_Servicearea/getservicearea")
    Call<List<HCPAuthentication>> Getservicearea();

    @GET("Hcp_Equipments/getequipments")
    Call<List<Equipmentdetails>> Getequipments();

    @POST("Manage_Equipments/setmyequipments")
    Call<Boolean> setmyequipments(@Query("hcp_id") int hcp_id, @Query("equipid") int equipid, @Query("price") String price);


    @GET("Manage_Servicearea/getmyservicearea")
    Call<List<HCPAuthentication>> Getmyservicearea(@Query("hcp_id") int hcp_id);


    @GET("Manage_Equipments/getmyequipments")
    Call<List<Equipmentdetails>> Getmyequipments(@Query("hcp_id") int hcp_id);

    @DELETE("Manage_Equipments/stopequipments")
    Call<Boolean> stopequipments(@Query("equipment_id") int equipment_id,@Query("hcp_id") int hcp_id);




    @DELETE("Manage_Servicearea/stopservicearea")
    Call<Boolean> stopservicearea(@Query("servicearea_id") int servicearea_id,@Query("hcp_id") int hcp_id);



    @POST("Manage_Servicearea/addservicearea")
    Call<Boolean> addservicearea(@Query("servicearea") String servicearea,@Query("hcp_id") int hcp_id);


}