package com.emp.model;

import java.sql.*;
import java.util.*;


public class EmpJDBCDAO implements EmpDAO_interface {

	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/CFA103_G4?serverTimezone=Asia/Taipei";
	String userid = "root";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO `cfa103_g4`.`emp` (`JOB`, `SAL`, `ENAME`, `HIREDATE`, `EACCOUNT`, `EPASSWORD`, `JOB_STATUS`) VALUES (?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT * FROM `cfa103_g4`.`emp` order by empno";
	private static final String GET_ONE_STMT = "SELECT * FROM `cfa103_g4`.`emp` where empno = ?";
	private static final String GET_ONE_BY_EACCOUNT = "SELECT * FROM `cfa103_g4`.`emp` where eaccount = ?";
	private static final String DELETE_DETAIL = "DELETE FROM `cfa103_g4`.`emper_detail` where empno = ?";
	private static final String DELETE_EMP = "DELETE FROM `cfa103_g4`.`emp` where empno = ?";
	private static final String UPDATE = "UPDATE `cfa103_g4`.`emp` set job=?, sal=?, ename=?, hiredate=?, eaccount=?, epassword=? ,job_status=? where empno = ?";

	@Override
	public void insert(EmpVO empVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, empVO.getJob());
			pstmt.setDouble(2, empVO.getSal());
			pstmt.setString(3, empVO.getEname());
			pstmt.setDate(4, empVO.getHiredate());
			pstmt.setString(5, empVO.getEaccount());
			pstmt.setString(6, empVO.getEpassword());
			pstmt.setInt(7, empVO.getJob_status());

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
	public void update(EmpVO empVO) {

		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, empVO.getJob());
			pstmt.setDouble(2, empVO.getSal());
			pstmt.setString(3, empVO.getEname());
			pstmt.setDate(4, empVO.getHiredate());
			pstmt.setString(5, empVO.getEaccount());
			pstmt.setString(6, empVO.getEpassword());
			pstmt.setInt(7, empVO.getJob_status());
			pstmt.setInt(8, empVO.getEmpno());

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
	public void delete(Integer empno) {
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
			pstmt.setInt(1, empno);
			updateCount_DETAILs = pstmt.executeUpdate();

			// 再刪除員工
			pstmt = con.prepareStatement(DELETE_EMP);
			pstmt.setInt(1, empno);
			pstmt.executeUpdate();

			// 2●設定於 pstm.executeUpdate()之後
			con.commit();
			con.setAutoCommit(true);
			System.out.println("刪除員工時" + empno + "時,共有" + updateCount_DETAILs + "筆明細同時被刪除");

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
	public EmpVO findByPrimaryKey(Integer empno) {

		EmpVO empVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, empno);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				empVO = new EmpVO();
				empVO.setEmpno(rs.getInt("empno"));
				empVO.setJob(rs.getString("job"));
				empVO.setSal(rs.getInt("sal"));
				empVO.setEname(rs.getString("ename"));
				empVO.setHiredate(rs.getDate("hiredate"));
				empVO.setEaccount(rs.getString("eaccount"));
				empVO.setEpassword(rs.getString("epassword"));
				empVO.setJob_status(rs.getInt("job_status"));
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
		return empVO;
	}

	@Override
	public EmpVO findByEaccount(String eaccount) {
		EmpVO empVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_BY_EACCOUNT);

			pstmt.setString(1, eaccount);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				empVO = new EmpVO();
				empVO.setEmpno(rs.getInt("empno"));
				empVO.setJob(rs.getString("job"));
				empVO.setSal(rs.getInt("sal"));
				empVO.setEname(rs.getString("ename"));
				empVO.setHiredate(rs.getDate("hiredate"));
				empVO.setEaccount(rs.getString("eaccount"));
				empVO.setEpassword(rs.getString("epassword"));
				empVO.setJob_status(rs.getInt("job_status"));
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
		return empVO;
	}
	
	
	@Override
	public List<EmpVO> getAll() {

		List<EmpVO> list = new ArrayList<EmpVO>();
		EmpVO empVO = null;

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
				empVO = new EmpVO();
				empVO.setEmpno(rs.getInt("empno"));
				empVO.setJob(rs.getString("job"));
				empVO.setSal(rs.getInt("sal"));
				empVO.setEname(rs.getString("ename"));
				empVO.setHiredate(rs.getDate("hiredate"));
				empVO.setEaccount(rs.getString("eaccount"));
				empVO.setEpassword(rs.getString("epassword"));
				empVO.setJob_status(rs.getInt("job_status"));
				list.add(empVO); // Store the row in the list
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

		EmpJDBCDAO dao = new EmpJDBCDAO();

		// 新增
//		EmpVO empVO1 = new EmpVO();
//		empVO1.setJob("主管");
//		empVO1.setSal(new Integer(80000));
//		empVO1.setEname("王小明");
//		empVO1.setHiredate(java.sql.Date.valueOf("2021-09-06"));
//		empVO1.setEaccount("W10000");
//		empVO1.setEpassword("Ab123456");
//		empVO1.setJob_status(new Integer(1));
//		dao.insert(empVO1);

		// 修改
//		EmpVO empVO2 = new EmpVO();
//		empVO2.setEmpno(16);
//		empVO2.setJob("主管2");
//		empVO2.setSal(new Integer(70000));
//		empVO2.setEname("王大明");
//		empVO2.setHiredate(java.sql.Date.valueOf("2000-12-31"));
//		empVO2.setEaccount("W12345");
//		empVO2.setEpassword("Ab654321");
//		empVO2.setJob_status(new Integer(0));
//		dao.update(empVO2);

		// 刪除
//		dao.delete(9);

		// 用員工編號查詢
//		EmpVO empVO3 = dao.findByPrimaryKey(8);
//		System.out.print(empVO3.getEmpno() + ",");
//		System.out.print(empVO3.getJob() + ",");
//		System.out.print(empVO3.getSal() + ",");
//		System.out.print(empVO3.getEname() + ",");
//		System.out.print(empVO3.getHiredate() + ",");
//		System.out.print(empVO3.getEaccount() + ",");
//		System.out.print(empVO3.getEpassword() + ",");
//		System.out.println(empVO3.getJob_status());
//		System.out.println("---------------------");
		
		// 用員工帳號查詢
		EmpVO empVO4 = dao.findByEaccount("F12345");
		System.out.print(empVO4.getEmpno() + ",");
		System.out.print(empVO4.getJob() + ",");
		System.out.print(empVO4.getSal() + ",");
		System.out.print(empVO4.getEname() + ",");
		System.out.print(empVO4.getHiredate() + ",");
		System.out.print(empVO4.getEaccount() + ",");
		System.out.print(empVO4.getEpassword() + ",");
		System.out.println(empVO4.getJob_status());
		System.out.println("---------------------");

		// 查詢全部
//		List<EmpVO> list = dao.getAll();
//		for (EmpVO aEmp : list) {
//			System.out.print(aEmp.getEmpno() + ",");
//			System.out.print(aEmp.getJob() + ",");
//			System.out.print(aEmp.getSal() + ",");
//			System.out.print(aEmp.getEname() + ",");
//			System.out.print(aEmp.getHiredate() + ",");
//			System.out.print(aEmp.getEaccount() + ",");
//			System.out.print(aEmp.getEpassword() + ",");
//			System.out.println(aEmp.getJob_status());
//		}
	}

	

}
