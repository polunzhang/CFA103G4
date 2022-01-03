package datetime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DateTimeConverter {

	public static void main(String[] args) throws ParseException {
		// util.Date → util.Calendar
		java.util.Date date = new java.util.Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		// util.Calendar → util.Date
		Calendar cal2 = Calendar.getInstance();
		java.util.Date date2 = cal2.getTime();
		
		// String → util.Date (字串內容需符合SimpleDateFormat定義的格式才能正常轉換)
		String dateTimeStr = "2018-11-29 20:25:58";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Date date3 = sdf.parse(dateTimeStr);
		
		// java.util.Date → String
		java.util.Date date4 = new java.util.Date();
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formatStr = sdf2.format(date4);
		
		// sql.Date → util.Date
		java.sql.Date sqlDate = new java.sql.Date(System.currentTimeMillis());
		java.util.Date utilDate = new java.util.Date(sqlDate.getTime());
		
		// util.Date → sql.Date
		java.util.Date date5 = new java.util.Date();
		java.sql.Date sqlDate2 = new java.sql.Date(date5.getTime());
		
		// String → sql.Date
        String str = "2018-11-29";
        java.sql.Date sqlDate3 = java.sql.Date.valueOf(str);
        
        // sql.Date → String
        java.sql.Date sqlDate4 = new java.sql.Date(System.currentTimeMillis());
        String str2 = sqlDate4.toString();
        
        // String → sql.Timestamp
        String dateTimeStr2 = "2018-11-29 20:25:58";
		java.sql.Timestamp ts = java.sql.Timestamp.valueOf(dateTimeStr2);
		
		// sql.Timestamp → String (最簡單的就是直接呼叫toString()即可, 配合SimpleDateFormat就可以有更多取法)
		java.sql.Timestamp ts2 = new java.sql.Timestamp(System.currentTimeMillis());
		SimpleDateFormat sdf3 = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		SimpleDateFormat sdf4 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf5 = new SimpleDateFormat("HH:mm:ss");
		
		System.out.println(sdf3.format(ts2));
		System.out.println(sdf4.format(ts2));
		System.out.println(sdf5.format(ts2));
		
	}

}
