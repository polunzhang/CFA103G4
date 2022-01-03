package com.emperDetail.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class EmperDetailJNDIDAO implements EmperDetailDAO_interface{

	// 一個應用程式中,針對一個資料庫 ,共用一個DataSource即可
				private static DataSource ds = null;
				static {
					try {
						Context ctx = new InitialContext();
						ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB2");
					} catch (NamingException e) {
						e.printStackTrace();
					}
				}
	
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


			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, emperDetailVO.getEmperno());
			pstmt.setInt(2, emperDetailVO.getEmpno());

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
	public void update(EmperDetailVO emperDetailVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, emperDetailVO.getEmperno());
			pstmt.setInt(2, emperDetailVO.getEmpno());

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
	public void delete(Integer empno, Integer emperno) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, empno);
			pstmt.setInt(2, emperno);

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
	public List<EmperDetailVO> findByEmp(Integer empno) {
		
		List<EmperDetailVO> list = new ArrayList<EmperDetailVO>();
		EmperDetailVO emperDetailVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
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

			con = ds.getConnection();
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

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				emperDetailVO = new EmperDetailVO();
				emperDetailVO.setEmperno(rs.getInt("emperno"));
				emperDetailVO.setEmpno(rs.getInt("empno"));
				list.add(emperDetailVO); // Store the row in the list
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
	
}
