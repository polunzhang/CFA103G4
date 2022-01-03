package com.table.model;

import java.sql.*;
import java.util.*;

import javax.naming.*;
import javax.sql.DataSource;

import com.online_order.model.OnlineOrderVO;

import jdbc.util.CompositeQuery.jdbcUtil_CompositeQuery_Table;

public class TableDAO implements TableDAO_interface {
	
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CFA103_G4");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String INSERT_STMT = "INSERT INTO CFA103_G4.table (table_nop, table_status) VALUES (?, ?)";
	private static final String GET_ALL_STMT = "SELECT tableno , table_nop, table_status, table_left, table_top FROM CFA103_G4.table";
	private static final String GET_ONE_STMT = "SELECT tableno , table_nop, table_status, table_left, table_top FROM CFA103_G4.table where tableno = ?";
	private static final String DELETE_REZ_DEATAIL = "DELETE FROM cfa103_g4.rez_detail WHERE tableno = ?";
	private static final String DELETE_TABLE = "DELETE FROM CFA103_G4.table WHERE tableno = ?";
	private static final String UPDATE = "UPDATE CFA103_G4.table SET table.table_nop = ?, table.table_status = ?, table.table_left = ?, table.table_top = ? WHERE table.tableno = ?";
	private static final String findByTableStatus = "SELECT * FROM cfa103_g4.table WHERE table_status = ?";
	private static final String findByTableNop = "SELECT * FROM cfa103_g4.table WHERE table_nop = ?";
//	private static final String GET_RezDetail_ByTableNo_STMT = "SELECT rezno, tableno FROM CFA103_G4.rez_detail where tableno = ? order by reznp";
	
	@Override
	public List<TableVO> getAll(Map<String, String[]> map) {
		List<TableVO> list = new ArrayList<TableVO>();
		TableVO tableVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			String finalSQL = "select * from cfa103_g4.table " + jdbcUtil_CompositeQuery_Table.get_WhereCondition(map)
			+ "order by tableno";
			pstmt = con.prepareStatement(finalSQL);
			System.out.println("●●finalSQL(by DAO) = " + finalSQL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				tableVO = new TableVO();
				tableVO.setTableno(rs.getInt("tableno"));
				tableVO.setTable_nop(rs.getInt("table_nop"));
				tableVO.setTable_status(rs.getInt("table_status"));
				list.add(tableVO); // Store the row in the list
			}

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public void insert(TableVO tableVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, tableVO.getTable_nop());
			pstmt.setInt(2, tableVO.getTable_status());

			pstmt.executeUpdate();

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
	public void update(TableVO tableVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {

			con = ds.getConnection();
			System.out.println(UPDATE);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, tableVO.getTable_nop());
			pstmt.setInt(2, tableVO.getTable_status());
			pstmt.setInt(3, tableVO.getTable_left());
			pstmt.setInt(4, tableVO.getTable_top());
			pstmt.setInt(5, tableVO.getTableno());
			pstmt.executeUpdate();

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
		
		int updateCount_RezDetails = 0;

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();

			// 1●設定於 pstm.executeUpdate()之前
			con.setAutoCommit(false);

			// 先刪除員工
			pstmt = con.prepareStatement(DELETE_REZ_DEATAIL);
			pstmt.setInt(1, tableno);
			updateCount_RezDetails = pstmt.executeUpdate();
			// 再刪除部門
			pstmt = con.prepareStatement(DELETE_TABLE);
			pstmt.setInt(1, tableno);
			pstmt.executeUpdate();

			// 2●設定於 pstm.executeUpdate()之後
			con.commit();
			con.setAutoCommit(true);
			System.out.println("刪除桌位編號" + tableno + "時,共有訂位明細" + updateCount_RezDetails
					+ "筆同時被刪除");
			
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

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, tableno);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// deptVO 也稱為 Domain objects
				tableVO = new TableVO();
				tableVO.setTableno(rs.getInt("tableno"));
				tableVO.setTable_nop(rs.getInt("table_nop"));
				tableVO.setTable_status(rs.getInt("table_status"));
				tableVO.setTable_left(rs.getInt("table_left"));
				tableVO.setTable_top(rs.getInt("table_top"));
			}

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
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(findByTableNop);
			
			pstmt.setInt(1, table_nop);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				// TableVO 也稱為 Domain objects
				tableVO = new TableVO();
				tableVO.setTableno(rs.getInt("tableno"));
				tableVO.setTable_nop(rs.getInt("table_nop"));
				tableVO.setTable_status(rs.getInt("table_status"));
				list.add(tableVO);
			}
			
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
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(findByTableStatus);
			
			pstmt.setInt(1, table_status);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				// TableVO 也稱為 Domain objects
				tableVO = new TableVO();
				tableVO.setTableno(rs.getInt("tableno"));
				tableVO.setTable_nop(rs.getInt("table_nop"));
				tableVO.setTable_status(rs.getInt("table_status"));
				list.add(tableVO);
			}
			
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

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				tableVO = new TableVO();
				tableVO.setTableno(rs.getInt("tableno"));
				tableVO.setTable_nop(rs.getInt("table_nop"));
				tableVO.setTable_status(rs.getInt("table_status"));
				tableVO.setTable_left(rs.getInt("table_left"));
				tableVO.setTable_top(rs.getInt("table_top"));
				list.add(tableVO); // Store the row in the list
			}

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
//	public Set<TableVO> getReznoByTableno(Integer tableno) {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
