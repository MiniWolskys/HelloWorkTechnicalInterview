package main.com.samuel.hellowork.technicalinterview.helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class XsltExtension {
    public static String toUpper(String str) {
        return str.toUpperCase();
    }

    public static String frenchDateToUTC(String date) throws Exception {
        String value = "";
        if (date != null && !date.isEmpty() && !date.equals("00/00/0000 00:00:00")) {
            SimpleDateFormat frenchFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.FRANCE);
            SimpleDateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
            utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            try {
                Date dateValue = frenchFormat.parse(date);
                value = utcFormat.format(dateValue);
            } catch (ParseException e) {
                throw new Exception(e);
            }
        }
        return value;
    }
}
