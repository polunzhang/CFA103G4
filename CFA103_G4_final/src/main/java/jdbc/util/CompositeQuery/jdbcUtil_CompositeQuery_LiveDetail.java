/*
 *  1. �U�νƦX�d��-�i�ѫȤ���H�N�W�����Q�d�ߪ����
 *  2. ���F�קK�v�T�į�:
 *     �ҥH�ʺA���͸U��SQL������,���d�ҵL�N�ĥ�MetaData���覡,�]�u�w��ӧO��Table�ۦ���ݭn�ӭӧO�s�@��
 * */


package jdbc.util.CompositeQuery;

import java.util.*;

import com.live_detail.model.LiveDetailService;
import com.live_detail.model.LiveDetailVO;

public class jdbcUtil_CompositeQuery_LiveDetail {

	public static String get_aCondition_For_myDB(String columnName, String value) {

		String aCondition = null;

		if ("liveno".equals(columnName) || "mealno".equals(columnName) || "meal_amount".equals(columnName) || "meal_price".equals(columnName)
				|| "meal_status".equals(columnName)|| "meal_set".equals(columnName)) // �Ω��L
			aCondition = columnName + "=" + value;
//		else if ("ename".equals(columnName) || "job".equals(columnName)) // �Ω�varchar
//			aCondition = columnName + " like '%" + value + "%'";
//		else if ("hiredate".equals(columnName))                          // �Ω�date
//			aCondition = columnName + "=" + "'"+ value +"'";                          //for �䥦DB  �� date
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
		System.out.println("����finalSQL = " + finalSQL);

	}
}
