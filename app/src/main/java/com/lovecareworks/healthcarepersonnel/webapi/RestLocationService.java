package com.lovecareworks.healthcarepersonnel.webapi;


import com.lovecareworks.healthcarepersonnel.util.Constants;

import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by alamchristian on 3/2/16.
 */
public class RestLocationService {

    private retrofit2.Retrofit retrofit;
    private LocationService locationService;

    public RestLocationService()
    {
        retrofit = new retrofit2.Retrofit.Builder().baseUrl(Constants.API_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();

        locationService = retrofit.create(LocationService.class);
    }

    public LocationService getService()
    {
        return locationService;
    }
}
