package com.lovecareworks.healthcarepersonnel.webapi;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by alamchristian on 3/22/16.
 */
public interface PractitionerService {



    @PUT("Notification/UpdateTokenhcp")
    Call<Boolean> Updatetokenhcp(@Query("userid") int userid,
                              @Query("token") String token
    );


}
