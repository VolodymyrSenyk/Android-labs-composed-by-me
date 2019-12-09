package com.senyk.volodymyr.lab10.helpers;

import java.util.Calendar;

public class PrivatbankApiDateFormatter {
    private static final String DATE_DIVIDERS_FOR_PRIVATBANK_API = ".";

    public String getDateString() {
        Calendar calendar = Calendar.getInstance();
        return numberFormatCheck(calendar.get(Calendar.DAY_OF_MONTH)) + DATE_DIVIDERS_FOR_PRIVATBANK_API +
                numberFormatCheck(calendar.get(Calendar.MONTH) + 1) + DATE_DIVIDERS_FOR_PRIVATBANK_API +
                calendar.get(Calendar.YEAR);
    }

    private String numberFormatCheck(int digit) {
        String res = String.valueOf(digit);
        if (res.length() == 1) {
            res = "0" + res;
        }
        return res;
    }
}
