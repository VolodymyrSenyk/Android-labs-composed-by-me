package com.senyk.volodymyr.lab10.mappers;

import com.senyk.volodymyr.lab10.models.pojo.CurrencyPojo;
import com.senyk.volodymyr.lab10.models.response.ExchangeRate;

import java.util.ArrayList;
import java.util.List;

import static com.senyk.volodymyr.lab10.networking.PrivatbankAPI.API_MAIN_CURRENCY;

public class ResponseToPojoMapper {
    public List<CurrencyPojo> convertToPOJO(List<ExchangeRate> response) {
        List<CurrencyPojo> currencies = new ArrayList<>();
        for (ExchangeRate item : response) {
            if (item.getCurrency() != null && !item.getCurrency().equals("") && !item.getCurrency().equals(API_MAIN_CURRENCY)) {
                currencies.add(new CurrencyPojo(item.getCurrency(), item.getSaleRateNB()));
            }
        }
        return currencies;
    }
}
