/*
 *  1. 萬用複合查詢-可由客戶端隨意增減任何想查詢的欄位
 *  2. 為了避免影響效能:
 *     所以動態產生萬用SQL的部份,本範例無意採用MetaData的方式,也只針對個別的Table自行視需要而個別製作之
 * */


package jdbc.util.CompositeQuery;

import java.util.*;

import com.live_detail.model.LiveDetailService;
import com.live_detail.model.LiveDetailVO;

public class jdbcUtil_CompositeQuery_LiveDetail {

	public static String get_aCondition_For_myDB(String columnName, String value) {

		String aCondition = null;

		if ("liveno".equals(columnName) || "mealno".equals(columnName) || "meal_amount".equals(columnName) || "meal_price".equals(columnName)
				|| "meal_status".equals(columnName)|| "meal_set".equals(columnName)) // 用於其他
			aCondition = columnName + "=" + value;
//		else if ("ename".equals(columnName) || "job".equals(columnName)) // 用於varchar
//			aCondition = columnName + " like '%" + value + "%'";
//		else if ("hiredate".equals(columnName))                          // 用於date
//			aCondition = columnName + "=" + "'"+ value +"'";                          //for 其它DB  的 date
//		    aCondition = "to_char(" + columnName + ",'yyyy-mm-dd')='" + value + "'";  //for Oracle 的 date
		
		return aCondition + " ";
	}

	public static String get_WhereCondition(Map<String, String[]> map) {
		Set<String> keys = map.keySet();
		StringBuffer whereCondition = new StringBuffer();
		int count = 0;
		for (String key : keys) {
			String value = map.get(key)[0];
			if (value != null && value.trim().length() != 0	&& !"action".equals(key)) {
				count++;
				String aCondition = get_aCondition_For_myDB(key, value.trim());

				if (count == 1)
					whereCondition.append(" where " + aCondition);
				else
					whereCondition.append(" and " + aCondition);

				System.out.println("有送出查詢資料的欄位數count = " + count);
			}
		}
		
		return whereCondition.toString();
	}

	public static void main(String argv[]) {

		// 配合 req.getParameterMap()方法 回傳 java.util.Map<java.lang.String,java.lang.String[]> 之測試
		Map<String, String[]> map = new TreeMap<String, String[]>();
		map.put("meal_status", new String[] { "1" });
		LiveDetailService test = new LiveDetailService();
		List<LiveDetailVO> list = test.getAll(map);
		for (LiveDetailVO LiveOrderVO1 : list) {
			System.out.print(LiveOrderVO1.getLiveno() + ",");
			System.out.print(LiveOrderVO1.getMealno() + ",");
			System.out.print(LiveOrderVO1.getMeal_price() + ",");
			System.out.print(LiveOrderVO1.getMeal_note() + ",");
			System.out.print(LiveOrderVO1.getMeal_set() + ",");
			System.out.print(LiveOrderVO1.getMeal_status() + ",");
			System.out.print(LiveOrderVO1.getMeal_amount());
			System.out.println();
		}
		
		String finalSQL = "select * from live_detail "
				          + jdbcUtil_CompositeQuery_LiveDetail.get_WhereCondition(map)
				          + "order by liveno";
		System.out.println("●●finalSQL = " + finalSQL);

	}
}
