package com.lovecareworks.healthcarepersonnel.webapi;


import com.lovecareworks.healthcarepersonnel.util.Constants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by alamchristian on 3/2/16.
 */
public class RestScheduleService {

    private retrofit2.Retrofit retrofit;
    private ScheduleService userService;

    final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(600, TimeUnit.SECONDS)
            .connectTimeout(600, TimeUnit.SECONDS)
            .build();

    public RestScheduleService()
    {
        retrofit = new retrofit2.Retrofit.Builder().baseUrl(Constants.API_URL)
                .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build();

        userService = retrofit.create(ScheduleService.class);
    }

    public ScheduleService getService()
    {
        return userService;
    }
}
