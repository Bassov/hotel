package app.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Date;

public class SqlUtil {

    public static Date parseDate(String date) {
        try {
            date = URLDecoder.decode(date, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String[] temp = date.split("/");
        date = String.format("%s-%s-%s", temp[2], temp[0], temp[1]);
        return Date.valueOf(date);
    }

}
