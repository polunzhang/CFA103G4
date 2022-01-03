package com.emper.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.emp.model.EmpVO;

public class EmperJDBCDAO implements EmperDAO_interface {

	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/CFA103_G4?serverTimezone=Asia/Taipei";
	String userid = "root";
	String passwd = "password";

	private static final String INSERT_STMT = "INSERT INTO `cfa103_g4`.`emper` (`EMPER_NAME`) VALUES (?)";
	private static final String GET_ALL_STMT = "SELECT * FROM `cfa103_g4`.`emper` order by emperno";
	private static final String GET_ONE_STMT = "SELECT * FROM `cfa103_g4`.`emper` where emperno = ?";
	private static final String DELETE_DETAIL = "DELETE FROM `cfa103_g4`.`emper_detail` where emperno = ?";
	private static final String DELETE_EMPER = "DELETE FROM `cfa103_g4`.`emper` where emperno = ?";
	private static final String UPDATE = "UPDATE `cfa103_g4`.`emper` set emper_name=? where emperno=?";

	@Override
	public void insert(EmperVO emperVO) {

		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, emperVO.getEmper_name());

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
	public void update(EmperVO emperVO) {

		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, emperVO.getEmper_name());
			pstmt.setInt(2, emperVO.getEmperno());

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
	public void delete(Integer emperno) {
		int updateCount_DETAILs = 0;

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);

			// 1●設定於 pstm.executeUpdate()之前
			con.setAutoCommit(false);
			// 先刪除明細
			pstmt = con.prepareStatement(DELETE_DETAIL);
			pstmt.setInt(1, emperno);
			updateCount_DETAILs = pstmt.executeUpdate();
			// 再刪除權限
			pstmt = con.prepareStatement(DELETE_EMPER);
			pstmt.setInt(1, emperno);
			pstmt.executeUpdate();

			// 2●設定於 pstm.executeUpdate()之後
			con.commit();
			con.setAutoCommit(true);
			System.out.println("刪除權限" + emperno + "時,共有" + updateCount_DETAILs + "筆明細同時被刪除");

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. " + excep.getMessage());
				}
			}
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
	public EmperVO findByPrimaryKey(Integer emperno) {

		EmperVO emperVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, emperno);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// emperVo 也稱為 Domain objects
				emperVO = new EmperVO();
				emperVO.setEmperno(rs.getInt("emperno"));
				emperVO.setEmper_name(rs.getString("emper_name"));

			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
		return emperVO;
	}

	@Override
	public List<EmperVO> getAll() {
		List<EmperVO> list = new ArrayList<EmperVO>();
		EmperVO emperVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				emperVO = new EmperVO();
				emperVO.setEmperno(rs.getInt("emperno"));
				emperVO.setEmper_name(rs.getString("emper_name"));
				list.add(emperVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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

	// 測試測試測試
	public static void main(String[] args) {

		EmperJDBCDAO dao = new EmperJDBCDAO();

		// 新增
//		EmperVO emperVO1 = new EmperVO();
//		emperVO1.setEmper_name("傳單人員");
//		dao.insert(emperVO1);

		// 修改
//		EmperVO emperVO2 = new EmperVO();
//		emperVO2.setEmperno(6);
//		emperVO2.setEmper_name("櫃台");
//		dao.update(emperVO2);

		// 刪除
//		dao.delete(5);

		// 查詢一個
//		EmperVO emperVO3 = dao.findByPrimaryKey(5);
//		System.out.print(emperVO3.getEmperno() + ",");
//		System.out.print(emperVO3.getEmper_name());

		// 查詢全部
//		List<EmperVO> list = dao.getAll();
//		for (EmperVO aEmper : list) {
//			System.out.print(aEmper.getEmperno() + ",");
//			System.out.println(aEmper.getEmper_name());
//		}

	}

}
