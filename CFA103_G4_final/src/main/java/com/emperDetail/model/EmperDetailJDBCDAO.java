package com.emperDetail.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.emp.model.EmpVO;
import com.emper.model.EmperVO;

public class EmperDetailJDBCDAO implements EmperDetailDAO_interface{

	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/CFA103_G4?serverTimezone=Asia/Taipei";
	String userid = "root";
	String passwd = "password";

	private static final String INSERT_STMT = "INSERT INTO `cfa103_g4`.`emper_detail` (`EMPERNO`,`EMPNO`) VALUES (?, ?)";
	private static final String GET_ALL_STMT = "SELECT * FROM `cfa103_g4`.`emper_detail` order by emperno";
	private static final String GET_EMPERNOS_BY_EMPNO = "SELECT * FROM `cfa103_g4`.`emper_detail` where empno = ?";
	private static final String GET_EMPS_BY_EMPERNO = "SELECT * FROM `cfa103_g4`.`emper_detail` where emperno = ?";
	private static final String DELETE = "DELETE FROM `cfa103_g4`.`emper_detail` where empno = ? and emperno = ?";
	private static final String UPDATE = "UPDATE `cfa103_g4`.`emper_detail` set emperno =? where empno=?";
	
	
	@Override
	public void insert(EmperDetailVO emperDetailVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, emperDetailVO.getEmperno());
			pstmt.setInt(2, emperDetailVO.getEmpno());

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
	public void update(EmperDetailVO emperDetailVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, emperDetailVO.getEmperno());
			pstmt.setInt(2, emperDetailVO.getEmpno());

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
	public void delete(Integer empno, Integer emperno) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, empno);
			pstmt.setInt(2, emperno);

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
	public List<EmperDetailVO> findByEmp(Integer empno) {
		
		List<EmperDetailVO> list = new ArrayList<EmperDetailVO>();
		EmperDetailVO emperDetailVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_EMPERNOS_BY_EMPNO);

			pstmt.setInt(1, empno);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				emperDetailVO = new EmperDetailVO();
				emperDetailVO.setEmperno(rs.getInt("emperno"));
				emperDetailVO.setEmpno(rs.getInt("empno"));
				list.add(emperDetailVO);
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

	@Override
	public List<EmperDetailVO> findByEmper(Integer emperno) {
		
		List<EmperDetailVO> list = new ArrayList<EmperDetailVO>();
		EmperDetailVO emperDetailVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_EMPS_BY_EMPERNO);

			pstmt.setInt(1, emperno);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				emperDetailVO = new EmperDetailVO();
				emperDetailVO.setEmperno(rs.getInt("emperno"));
				emperDetailVO.setEmpno(rs.getInt("empno"));
				list.add(emperDetailVO);
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

	@Override
	public List<EmperDetailVO> getAll() {
		List<EmperDetailVO> list = new ArrayList<EmperDetailVO>();
		EmperDetailVO emperDetailVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				emperDetailVO = new EmperDetailVO();
				emperDetailVO.setEmperno(rs.getInt("emperno"));
				emperDetailVO.setEmpno(rs.getInt("empno"));
				list.add(emperDetailVO); // Store the row in the list
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

	//測試測試測試
	public static void main(String []args) {
		
		EmperDetailJDBCDAO dao = new EmperDetailJDBCDAO();
		
		//新增
//		EmperDetailVO emperDetailVO1 = new EmperDetailVO();
//		emperDetailVO1.setEmperno(2);
//		emperDetailVO1.setEmpno(11);
//		dao.insert(emperDetailVO1);
		
		//修改
//		EmperDetailVO emperDetailVO2 = new EmperDetailVO();
//		emperDetailVO2.setEmperno(5);
//		emperDetailVO2.setEmpno(11);
//		dao.update(emperDetailVO2);
		
		//刪除
//		dao.delete(1,5);
		
//		用員工查詢權限
//		List<EmperDetailVO> list = dao.findByEmp(8);
//		for(EmperDetailVO aEmperDetail : list) {
//		System.out.print(aEmperDetail.getEmpno() + "號員工有");
//		System.out.print(aEmperDetail.getEmperno() + "權限");
//		}
		
		//用權限查詢員工
//		List<EmperDetailVO> list = dao.findByEmper(5);
//		for(EmperDetailVO aEmperDetail : list) {
//		System.out.print("員工權限是" + aEmperDetail.getEmperno() + "的有:");
//		System.out.println(aEmperDetail.getEmpno() + "號員工");
//		}
		
		//查詢全部
//		List<EmperDetailVO> list = dao.getAll();
//		for (EmperDetailVO aEmperDetail : list) {
//			System.out.print(aEmperDetail.getEmperno() + ",");
//			System.out.println(aEmperDetail.getEmpno());
//		}
		
	}
}
