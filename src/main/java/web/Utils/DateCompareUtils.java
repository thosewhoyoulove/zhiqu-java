package web.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateCompareUtils {
    public static boolean compare(String date1Str, String date2Str){
        Date date1 = null;
        Date date2 = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日HH:mm");
        try {
            date1 = format.parse(date1Str);
            date2 = format.parse(date2Str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int res = date1.compareTo(date2);
        if (res > 0){
            return true;
        }
        return false;
    }
}
