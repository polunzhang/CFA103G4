/*
 *  1. 萬用複合查詢-可由客戶端隨意增減任何想查詢的欄位
 *  2. 為了避免影響效能:
 *     所以動態產生萬用SQL的部份,本範例無意採用MetaData的方式,也只針對個別的Table自行視需要而個別製作之
 * */

package jdbc.util.CompositeQuery;

import java.util.*;

import com.online_detail.model.*;
import com.online_order.model.*;

public class jdbcUtil_CompositeQuery_OnlineOrder {

	public static String get_aCondition_For_myDB(String columnName, String value) {

		String aCondition = null;

		if ("olno".equals(columnName) || "empno".equals(columnName) || "memno".equals(columnName) || "pay_status".equals(columnName) || "total".equals(columnName) || "pay_way".equals(columnName)) // 用於其他
			aCondition = columnName + "=" + value;
		else if ("address".equals(columnName)) // 用於varchar
			aCondition = columnName + " like '%" + value + "%'";
		else if ("set_time".equals(columnName) || "create_time".equals(columnName))                          // 用於date
			aCondition = columnName + "=" + "'"+ value +"'";                          //for 其它DB  的 date
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
		map.put("olno", new String[] { "1" });
		OnlineOrderService test = new OnlineOrderService();
//		List<LiveOrderVO> list2 = test.getAll("pay_status","0");
		List<OnlineOrderVO> list2 = test.getAll(map);
		
		for (OnlineOrderVO onlineorderVO3 : list2) {
		System.out.print(onlineorderVO3.getOlno() + ",");
		System.out.print(onlineorderVO3.getEmpno() + ",");
		System.out.print(onlineorderVO3.getMemno() + ",");
		System.out.print(onlineorderVO3.getPay_status() + ",");
		System.out.print(onlineorderVO3.getSet_time() + ",");
		System.out.print(onlineorderVO3.getCreate_time() + ",");
		System.out.print(onlineorderVO3.getTotal() + ",");
		System.out.println(onlineorderVO3.getPay_way());
		}
		
		String finalSQL = "select * from online_order "
				          + jdbcUtil_CompositeQuery_OnlineOrder.get_WhereCondition(map)
				          + "order by olno";
		System.out.println("●●finalSQL = " + finalSQL);

	}
}
