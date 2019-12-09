package com.senyk.volodymyr.lab10.repository;

import com.senyk.volodymyr.lab10.helpers.PrivatbankApiDateFormatter;
import com.senyk.volodymyr.lab10.mappers.ResponseToPojoMapper;
import com.senyk.volodymyr.lab10.models.pojo.CurrencyPojo;
import com.senyk.volodymyr.lab10.models.response.ExchangeRate;
import com.senyk.volodymyr.lab10.models.response.PrivatbankCurrency;
import com.senyk.volodymyr.lab10.networking.PrivatbankAPI;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class PrivatbankApiRepository {
    private static final String BASE_URL = "https://api.privatbank.ua/p24api/";
    private static final int TIMEOUT = 20;

    private static PrivatbankApiRepository repository;

    private PrivatbankApiRepository() {
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        this.api = retrofit.create(PrivatbankAPI.class);
        this.dateFormatter = new PrivatbankApiDateFormatter();
        this.mapper = new ResponseToPojoMapper();
    }

    public static PrivatbankApiRepository getInstance() {
        if (repository == null) repository = new PrivatbankApiRepository();
        return repository;
    }

    private final PrivatbankAPI api;
    private final PrivatbankApiDateFormatter dateFormatter;
    private final ResponseToPojoMapper mapper;

    public List<CurrencyPojo> getExchangeRates() {
        return this.mapper.convertToPOJO(getExchangeRates(this.api, this.dateFormatter.getDateString()));
    }

    private List<ExchangeRate> getExchangeRates(PrivatbankAPI api, String date) {
        Call<PrivatbankCurrency> reposCall = api.getExchangeRates(date);
        Response<PrivatbankCurrency> response = null;
        try {
            response = reposCall.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response == null || response.body() == null) {
            return Collections.emptyList();
        }
        return response.body().getExchangeRate();
    }
}
