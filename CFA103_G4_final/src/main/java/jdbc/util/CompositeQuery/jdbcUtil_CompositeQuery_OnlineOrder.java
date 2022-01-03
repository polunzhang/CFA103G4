/*
 *  1. �U�νƦX�d��-�i�ѫȤ���H�N�W�����Q�d�ߪ����
 *  2. ���F�קK�v�T�į�:
 *     �ҥH�ʺA���͸U��SQL������,���d�ҵL�N�ĥ�MetaData���覡,�]�u�w��ӧO��Table�ۦ���ݭn�ӭӧO�s�@��
 * */

package jdbc.util.CompositeQuery;

import java.util.*;

import com.online_detail.model.*;
import com.online_order.model.*;

public class jdbcUtil_CompositeQuery_OnlineOrder {

	public static String get_aCondition_For_myDB(String columnName, String value) {

		String aCondition = null;

		if ("olno".equals(columnName) || "empno".equals(columnName) || "memno".equals(columnName) || "pay_status".equals(columnName) || "total".equals(columnName) || "pay_way".equals(columnName)) // �Ω��L
			aCondition = columnName + "=" + value;
		else if ("address".equals(columnName)) // �Ω�varchar
			aCondition = columnName + " like '%" + value + "%'";
		else if ("set_time".equals(columnName) || "create_time".equals(columnName))                          // �Ω�date
			aCondition = columnName + "=" + "'"+ value +"'";                          //for �䥦DB  �� date
//		    aCondition = "to_char(" + columnName + ",'yyyy-mm-dd')='" + value + "'";  //for Oracle �� date
		
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

				System.out.println("���e�X�d�߸�ƪ�����count = " + count);
			}
		}
		
		return whereCondition.toString();
	}

	public static void main(String argv[]) {

		// �t�X req.getParameterMap()��k �^�� java.util.Map<java.lang.String,java.lang.String[]> ������
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
		System.out.println("����finalSQL = " + finalSQL);

	}
}
