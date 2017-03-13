package com.mSIHAT.hcp.webapi;



import com.mSIHAT.hcp.classes.BasicHCP;
import com.mSIHAT.hcp.classes.HCP;
import com.mSIHAT.hcp.classes.HCPAuthentication;
import com.mSIHAT.hcp.classes.HCPtest;
import com.mSIHAT.hcp.classes.LocationUpdate;
import com.mSIHAT.hcp.model.AppRequest;
import com.mSIHAT.hcp.model.Appiontments;

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

public interface LocationService {


    @POST("Hcp_Location/UpdateLocation")
    Call<Boolean> updateLocation(@Body LocationUpdate location);

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