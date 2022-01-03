package com.emp.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class EmpJNDIDAO implements EmpDAO_interface{

	// �@�����ε{����,�w��@�Ӹ�Ʈw ,�@�Τ@��DataSource�Y�i
		private static DataSource ds = null;
		static {
			try {
				Context ctx = new InitialContext();
				ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB2");
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}
	
	private static final String INSERT_STMT = "INSERT INTO `cfa103_g4`.`emp` (`JOB`, `SAL`, `ENAME`, `HIREDATE`, `EACCOUNT`, `EPASSWORD`, `JOB_STATUS`) VALUES (?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT * FROM `cfa103_g4`.`emp` order by empno";
	private static final String GET_ONE_STMT = "SELECT * FROM `cfa103_g4`.`emp` where empno = ?";
	private static final String GET_ONE_BY_EACCOUNT = "SELECT * FROM `cfa103_g4`.`emp` where eaccount = ?";
	private static final String DELETE_DETAIL = "DELETE FROM `cfa103_g4`.`emper_detail` where empno = ?";
	private static final String DELETE_EMP = "DELETE FROM `cfa103_g4`.`emp` where empno = ?";
	private static final String UPDATE = "UPDATE `cfa103_g4`.`emp` set job=?, sal=?, ename=?, hiredate=?, epassword=? ,job_status=? where empno = ?";

	@Override
	public void insert(EmpVO empVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, empVO.getJob());
			pstmt.setDouble(2, empVO.getSal());
			pstmt.setString(3, empVO.getEname());
			pstmt.setDate(4, empVO.getHiredate());
			pstmt.setString(5, empVO.getEaccount());
			pstmt.setString(6, empVO.getEpassword());
			pstmt.setInt(7, empVO.getJob_status());

			pstmt.executeUpdate();

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage() +" [�i��O�b�����Ƴ�]");
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

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, empVO.getJob());
			pstmt.setDouble(2, empVO.getSal());
			pstmt.setString(3, empVO.getEname());
			pstmt.setDate(4, empVO.getHiredate());
	
			pstmt.setString(5, empVO.getEpassword());
			pstmt.setInt(6, empVO.getJob_status());
			pstmt.setInt(7, empVO.getEmpno());

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
	public void delete(Integer empno) {
		int updateCount_DETAILs = 0;

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			
			con = ds.getConnection();

			// 1���]�w�� pstm.executeUpdate()���e
			con.setAutoCommit(false);

			// ���R������
			pstmt = con.prepareStatement(DELETE_DETAIL);
			pstmt.setInt(1, empno);
			updateCount_DETAILs = pstmt.executeUpdate();

			// �A�R�����u
			pstmt = con.prepareStatement(DELETE_EMP);
			pstmt.setInt(1, empno);
			pstmt.executeUpdate();

			// 2���]�w�� pstm.executeUpdate()����
			con.commit();
			con.setAutoCommit(true);
			System.out.println("�R�����u��" + empno + "��,�@��" + updateCount_DETAILs + "�����ӦP�ɳQ�R��");

			
			// Handle any SQL errors
		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3���]�w���exception�o�ͮɤ�catch�϶���
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

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, empno);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo �]�٬� Domain objects
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
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO �]�٬� Domain objects
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
	public EmpVO findByEaccount(String eaccount) {
		EmpVO empVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
		try {
	
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_BY_EACCOUNT);
	
			pstmt.setString(1, eaccount);
	
			rs = pstmt.executeQuery();
	
			while (rs.next()) {
				// empVo �]�٬� Domain objects
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
}
