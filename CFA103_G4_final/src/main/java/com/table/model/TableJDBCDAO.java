package com.table.model;

import java.sql.*;
import java.util.*;

public class TableJDBCDAO implements TableDAO_interface {

	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/CFA103_G4?serverTimezone=Asia/Taipei";
	String userid = "root";
	String passwd = "6071";

	private static final String INSERT_STMT = "INSERT INTO CFA103_G4.table (table_nop, table_status) VALUES (?, ?)";
	private static final String GET_ALL_STMT = "SELECT tableno , table_nop, table_status FROM CFA103_G4.table";
	private static final String GET_ONE_STMT = "SELECT tableno , table_nop, table_status FROM CFA103_G4.table where tableno = ?";
	private static final String DELETE_REZ_DEATAIL = "DELETE FROM CFA103_G4.rez_deatail WHERE CFA103_G4.table.tableno = ?";
	private static final String DELETE_TABLE = "DELETE FROM CFA103_G4.table WHERE (tableno = ?)";
	private static final String UPDATE = "UPDATE CFA103_G4.table SET table.table_nop = ?, table.table_status = ? WHERE table.tableno = ?";
	private static final String findByTableStatus = "SELECT * FROM cfa103_g4.table WHERE table_status = ?";
	private static final String findByTableNop = "SELECT * FROM cfa103_g4.table WHERE table_nop = ?";
//	private static final String GET_RezDetail_ByTableNo_STMT = "SELECT rezno, tableno FROM CFA103_G4.rez_detail where tableno = ? order by reznp";

	@Override
	public void insert(TableVO tableVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, tableVO.getTable_nop());
			pstmt.setInt(2, tableVO.getTable_status());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public List<TableVO> getAll(Map<String, String[]> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(TableVO tableVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, tableVO.getTable_nop());
			pstmt.setInt(2, tableVO.getTable_status());
			pstmt.setInt(3, tableVO.getTableno());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public void delete(Integer tableno) {
		
		int updateCount_EMPs = 0;

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);

//			// 1●設定於 pstm.executeUpdate()之前
			con.setAutoCommit(false);

			// 先刪除訂位明細
			if (DELETE_REZ_DEATAIL != null) {
				pstmt = con.prepareStatement(DELETE_REZ_DEATAIL);
				pstmt.setInt(1, tableno);
				updateCount_EMPs = pstmt.executeUpdate();
			}
			// 再刪除桌位
			pstmt = con.prepareStatement(DELETE_TABLE);
			pstmt.setInt(1, tableno);
			pstmt.executeUpdate();

			// 2●設定於 pstm.executeUpdate()之後
			con.commit();
			con.setAutoCommit(true);
			System.out.println("刪除桌號" + tableno + "時,共有幾個" + updateCount_EMPs
					+ "訂位明細被刪除");
			
			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. "
							+ excep.getMessage());
				}
			}
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public TableVO findByPrimaryKey(Integer tableno) {
		
		TableVO tableVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, tableno);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// tableVO 也稱為 Domain objects
				tableVO = new TableVO();
				tableVO.setTableno(rs.getInt("tableno"));
				tableVO.setTable_nop(rs.getInt("table_nop"));
				tableVO.setTable_status(rs.getInt("table_status"));
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return tableVO;
		
	}
	
	@Override
	public List<TableVO> findByTableNop(Integer table_nop) {
		List<TableVO> list = new ArrayList<TableVO>();
		TableVO tableVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(findByTableNop);
			pstmt.setInt(1, table_nop);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				tableVO = new TableVO();
				tableVO.setTableno(rs.getInt("tableno"));
				tableVO.setTable_nop(rs.getInt("table_nop"));
				tableVO.setTable_status(rs.getInt("table_status"));
				list.add(tableVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}
	
	
	@Override
	public List<TableVO> findByTableStatus(Integer table_status) {
		List<TableVO> list = new ArrayList<TableVO>();
		TableVO tableVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(findByTableStatus);
			pstmt.setInt(1, table_status);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				tableVO = new TableVO();
				tableVO.setTableno(rs.getInt("tableno"));
				tableVO.setTable_nop(rs.getInt("table_nop"));
				tableVO.setTable_status(rs.getInt("table_status"));
				list.add(tableVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}

	@Override
	public List<TableVO> getAll() {
		
		List<TableVO> list = new ArrayList<TableVO>();
		TableVO tableVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				tableVO = new TableVO();
				tableVO.setTableno(rs.getInt("tableno"));
				tableVO.setTable_nop(rs.getInt("table_nop"));
				tableVO.setTable_status(rs.getInt("table_status"));
				list.add(tableVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}

//	@Override
//	public Set<RezVO> getReznoByTableno(Integer tableno) {
//		
//		Set<RezVO> set = new LinkedHashSet<RezVO>();
//		TableVO tableVO = null;
//	
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//	
//		try {
//	
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
//			pstmt = con.prepareStatement(GET_RezDetail_ByTableNo_STMT);
//			pstmt.setInt(1, tableno);
//			rs = pstmt.executeQuery();
//	
//			while (rs.next()) {
//				rezVO = new EmpVO();
//				rezVO.setRezno(rs.getInt("empno"));
//				rezVO.setTableno(rs.getInt("tableno"));
//				set.add(rezVO); // Store the row in the vector
//			}
//	
//			// Handle any driver errors
//		} catch (ClassNotFoundException e) {
//			throw new RuntimeException("Couldn't load database driver. "
//					+ e.getMessage());
//			// Handle any SQL errors
//		} catch (SQLException se) {
//			throw new RuntimeException("A database error occured. "
//					+ se.getMessage());
//		} finally {
//			if (rs != null) {
//				try {
//					rs.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (pstmt != null) {
//				try {
//					pstmt.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (con != null) {
//				try {
//					con.close();
//				} catch (Exception e) {
//					e.printStackTrace(System.err);
//				}
//			}
//		}
//		return set;
//		
//	}
	
	public static void main(String[] args) {

		TableJDBCDAO dao = new TableJDBCDAO();

//		新增
//		TableVO tablevo1 = new TableVO();
//		tablevo1.setTable_nop(4);
//		tablevo1.setTable_status(1);
//		dao.insert(tablevo1);

//		 修改
//		TableVO tableVO2 = new TableVO();
//		tableVO2.setTableno(11);
//		tableVO2.setTable_nop(5);
//		tableVO2.setTable_status(2);
//		dao.update(tableVO2);

		// 刪除
//		dao.delete(11);

		// 查詢
//		TableVO tableVO3 = dao.findByPrimaryKey(2);
//		System.out.print(tableVO3.getTableNo() + ",");
//		System.out.print(tableVO3.getTable_nop() + ",");
//		System.out.println(tableVO3.getTable_status());
//		System.out.println("---------------------");

//		// 查詢桌位
//		List<TableVO> list = dao.getAll();
//		for (TableVO aTable : list) {
//			System.out.print(aTable.getTableNo() + ",");
//			System.out.print(aTable.getTable_nop() + ",");
//			System.out.print(aTable.getTable_status());
//			System.out.println();
//		}
		// 以桌位狀態查詢
//		List<TableVO> list = dao.findByTableStatus(1);
//		for (TableVO aTable : list) {
//			System.out.print(aTable.getTableno() + ",");
//			System.out.print(aTable.getTable_nop() + ",");
//			System.out.print(aTable.getTable_status());
//			System.out.println();
//		}
		
		// 以桌位人數查詢
		List<TableVO> list = dao.findByTableNop(2);
		for (TableVO aTable : list) {
			System.out.print(aTable.getTableno() + ",");
			System.out.print(aTable.getTable_nop() + ",");
			System.out.print(aTable.getTable_status());
			System.out.println();
		}
		
//		查詢某桌的訂位明細
//		Set<TableVO> set = dao.getReznoByTableno(10);
//		for (TableVO aRezno : set) {
//			System.out.print(aRezno.getRezno() + ",");
//			System.out.print(aRezno.getTableno() + ",");
//			System.out.println();
//		}
	}
}
