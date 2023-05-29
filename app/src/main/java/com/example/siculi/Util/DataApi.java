package com.example.siculi.Util;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataApi {
    public static final String IP_ADDRESS = "192.168.100.6"; // isi ip address
    public static final String BASE_URL = "http://" + IP_ADDRESS + "/siculi/api/";
    public static Retrofit retrofit = null;
    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
