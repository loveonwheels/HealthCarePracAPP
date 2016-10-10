package com.lovecareworks.healthcarepersonnel.webapi;



import com.lovecareworks.healthcarepersonnel.classes.AppointmentIDs;
import com.lovecareworks.healthcarepersonnel.classes.BasicHCP;
import com.lovecareworks.healthcarepersonnel.classes.HCP;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AppiontmentService {

    @GET("Hcp_Users/{user_id}")
    Call<BasicHCP> GetRequests(@Path("user_id") int user_id);


    @GET("Hcp_Users/Validate/{email}/{password}")
    Call<BasicHCP> validateLogin(@Path("email") String email, @Path("password") String password);

    @POST("Hcp_Users/Signup")
    Call<HCP> createUser(@Body HCP user);


    @POST("Hcp_Users/requestSystem")
    Call<AppointmentIDs> UpdateRequests(@Body HCP user);


    @PUT("Hcp_users/update/{user_id}")
    Call<Void> updateUser(@Path("user_id") int user_id, @Body HCP user);
}