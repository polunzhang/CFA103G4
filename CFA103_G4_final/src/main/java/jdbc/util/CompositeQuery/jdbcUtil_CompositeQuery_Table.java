/*
 *  1. �U�νƦX�d��-�i�ѫȤ���H�N�W�����Q�d�ߪ����
 *  2. ���F�קK�v�T�į�:
 *     �ҥH�ʺA���͸U��SQL������,���d�ҵL�N�ĥ�MetaData���覡,�]�u�w��ӧO��Table�ۦ���ݭn�ӭӧO�s�@��
 * */

package jdbc.util.CompositeQuery;

import java.util.*;

import com.table.model.*;

public class jdbcUtil_CompositeQuery_Table {

	public static String get_aCondition_For_myDB(String columnName, String value) {

		String aCondition = null;

		if ("tableno".equals(columnName) || "table_nop".equals(columnName) || "table_status".equals(columnName)) // �Ω��L
			aCondition = columnName + "=" + value;
//		else if ("address".equals(columnName)) // �Ω�varchar
//			aCondition = columnName + " like '%" + value + "%'";
//		else if ("set_time".equals(columnName) || "create_time".equals(columnName))                          // �Ω�date
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
		map.put("tableno", new String[] { "1" });
		TableService test = new TableService();
//		List<LiveOrderVO> list2 = test.getAll("pay_status","0");
		List<TableVO> list2 = test.getAll(map);
		
		for (TableVO tableVO3 : list2) {
		System.out.print(tableVO3.getTableno() + ",");
		System.out.print(tableVO3.getTable_nop() + ",");
		System.out.print(tableVO3.getTable_status() + ",");
		}
		
		String finalSQL = "select * from online_order "
				          + jdbcUtil_CompositeQuery_Table.get_WhereCondition(map)
				          + "order by olno";
		System.out.println("����finalSQL = " + finalSQL);

	}
}
