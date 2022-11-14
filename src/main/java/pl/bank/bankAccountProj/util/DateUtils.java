package pl.bank.bankAccountProj.util;


import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class DateUtils {
    public static Date getCurrTime() {
        return Calendar.getInstance().getTime();
    }

    public static Date convertStringDate(String date) {
        List<String> tmp = List.of(date.split("-"));
        return  new Date(Integer.valueOf(tmp.get(0)), Integer.valueOf(tmp.get(1)) - 1, Integer.valueOf(tmp.get(2)));

    }
}
