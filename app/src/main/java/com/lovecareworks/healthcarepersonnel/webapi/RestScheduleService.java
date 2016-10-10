package com.lovecareworks.healthcarepersonnel.webapi;


import com.lovecareworks.healthcarepersonnel.util.Constants;

import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by alamchristian on 3/2/16.
 */
public class RestScheduleService {

    private retrofit2.Retrofit retrofit;
    private ScheduleService userService;

    public RestScheduleService()
    {
        retrofit = new retrofit2.Retrofit.Builder().baseUrl(Constants.API_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();

        userService = retrofit.create(ScheduleService.class);
    }

    public ScheduleService getService()
    {
        return userService;
    }
}
