package com.mSIHAT.hcp.webapi;


import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * Created by alamchristian on 3/22/16.
 */
public interface PractitionerService {



    @PUT("Notification/UpdateTokenhcp")
    Call<Boolean> Updatetokenhcp(@Query("userid") int userid,
                                 @Query("token") String token
    );

    @PUT("NotificationHub/Readnotification")
    Call<Boolean> readnotification(@Query("noteid") int noteid
    );

    @DELETE("NotificationHub/Deletenotification")
    Call<Boolean> deletenotification(@Query("noteid") int noteid
    );


    @DELETE("AppCom/CancelEmptyappointment")
    Call<Boolean> DeleteemptySlot(@Query("slot_id") int slot_id);

}
