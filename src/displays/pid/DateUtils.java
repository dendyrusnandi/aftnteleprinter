package displays.pid;

import java.util.Calendar;
import java.text.SimpleDateFormat;

public class DateUtils {
  String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";

  public String now() {
    Calendar cal = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
    System.out.println("LastTime:"+sdf.format(cal.getTime()));
    return sdf.format(cal.getTime());
  }
}
