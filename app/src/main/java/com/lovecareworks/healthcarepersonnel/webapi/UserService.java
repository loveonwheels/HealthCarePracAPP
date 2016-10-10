package com.lovecareworks.healthcarepersonnel.webapi;



import com.lovecareworks.healthcarepersonnel.classes.BasicHCP;
import com.lovecareworks.healthcarepersonnel.classes.HCP;
import com.lovecareworks.healthcarepersonnel.classes.HCPAuthentication;
import com.lovecareworks.healthcarepersonnel.classes.HCPtest;
import com.lovecareworks.healthcarepersonnel.classes.hcpinfo;
import com.lovecareworks.healthcarepersonnel.model.AppRequest;
import com.lovecareworks.healthcarepersonnel.model.Appiontments;

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

public interface UserService {

    @GET("Hcp_Users/GetRequests")
    Call<List<AppRequest>> GetRequests(@Query("hcp_id") int hcp_id);

    @GET("Hcp_Info/GetInfo")
    Call<hcpinfo> GetInformation(@Query("hcp_id") int hcp_id);

    @GET("Hcp_Schedule/getSchedule")
    Call<List<Appiontments>> GetSchedule(@Query("hcp_id") int hcp_id);

    @PUT("Hcp_Schedule/getcurrent")
    Call<List<Appiontments>> GetCurrent(@Query("hcp_id") int hcp_id);


    @POST("Hcp_Schedule/getpendingreports")
    Call<List<Appiontments>> GetPendingReports(@Query("hcp_id") int hcp_id);



    @GET("hcp_request/Acceptappointment")
    Call<Boolean> Appointmentaccepted (@Query("appointmentid") int appointmentid);

    @GET("hcp_request/Rejectappointment")
    Call<Boolean> Appointmentrejected (@Query("appointmentid") int appointmentid);


    @POST("hcp_request/updateRequests")
    Call<Boolean> updateRequests(@Body List<Integer> appointmentids);

    @Multipart
    @POST("Upload/PostFormdata")
    Call<String> uploadregisters(@Part MultipartBody.Part file, @Part("description") RequestBody description);



    @GET("Hcp_Users/Validate/{email}/{password}")
    Call<BasicHCP> validateLogin(@Path("email") String email, @Path("password") String password);

    @POST("Hcp_Users/register")
    Call<HCPtest> Register(@Body HCP user);

    @POST("Hcp_authentication/Authenticate")
    Call<HCPAuthentication> Authenticate(@Query("username") String username, @Query("password") String password);

    @PUT("Hcp_users/update/{user_id}")
    Call<Void> updateUser(@Path("user_id") int user_id, @Body HCP user);
}