package com.senyk.volodymyr.lab10.networking;

import com.senyk.volodymyr.lab10.models.response.PrivatbankCurrency;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface PrivatbankAPI {
    String API_MAIN_CURRENCY = "UAH";

    @Headers("Content-Type: application/json")
    @GET("exchange_rates?json")
    Call<PrivatbankCurrency> getExchangeRates(@Query("date") String date);
}
