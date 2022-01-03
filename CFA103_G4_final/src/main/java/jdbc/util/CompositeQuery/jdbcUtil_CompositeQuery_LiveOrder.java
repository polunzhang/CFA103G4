/*
 *  1. �U�νƦX�d��-�i�ѫȤ���H�N�W�����Q�d�ߪ����
 *  2. ���F�קK�v�T�į�:
 *     �ҥH�ʺA���͸U��SQL������,���d�ҵL�N�ĥ�MetaData���覡,�]�u�w��ӧO��Table�ۦ���ݭn�ӭӧO�s�@��
 * */


package jdbc.util.CompositeQuery;

import java.util.*;

import com.live_detail.model.LiveDetailService;
import com.live_detail.model.LiveDetailVO;
import com.live_order.model.LiveOrderService;
import com.live_order.model.LiveOrderVO;

public class jdbcUtil_CompositeQuery_LiveOrder {

	public static String get_aCondition_For_myDB(String columnName, String value) {

		String aCondition = null;

		if ("liveno".equals(columnName) || "empno".equals(columnName) || "tableno".equals(columnName) || "pay_status".equals(columnName)
				|| "pay_way".equals(columnName)|| "total".equals(columnName)) // �Ω��L
			aCondition = columnName + "=" + value;
//		else if ("ename".equals(columnName) || "job".equals(columnName)) // �Ω�varchar
//			aCondition = columnName + " like '%" + value + "%'";
		else if ("create_time".equals(columnName))                          // �Ω�date
			aCondition = columnName + "=" + "'"+ value +"'";                          //for �䥦DB  �� date
//		    aCondition = "to_char(" + columnName + ",'yyyy-mm-dd')='" + value + "'";  //for Oracle �� date

		else if ("DATE(create_time)".equals(columnName))                          // �Ω�date
			aCondition = columnName + "=" + "'"+ value +"'";                          //for �䥦DB  �� date
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
		map.put("liveno", new String[] { "1" });
		LiveOrderService test = new LiveOrderService();
//		List<LiveOrderVO> list2 = test.getAll("pay_status","0");
		List<LiveOrderVO> list2 = test.getAll(map);
		
		for (LiveOrderVO LiveOrderVO3 : list2) {
		System.out.print(LiveOrderVO3.getLiveno() + ",");
		System.out.print(LiveOrderVO3.getEmpno() + ",");
		System.out.print(LiveOrderVO3.getTableno() + ",");
		System.out.print(LiveOrderVO3.getPay_status() + ",");
		System.out.print(LiveOrderVO3.getCreate_time() + ",");
		System.out.print(LiveOrderVO3.getTotal() + ",");
		System.out.println(LiveOrderVO3.getPay_way());
		}
		
		String finalSQL = "select * from live_order "
				          + jdbcUtil_CompositeQuery_LiveOrder.get_WhereCondition(map)
				          + "order by liveno";
		System.out.println("����finalSQL = " + finalSQL);

	}
}
