package com.rez_detail.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.print.attribute.standard.DateTimeAtCreation;
import javax.sql.DataSource;

import com.rez.model.RezVO;

public class RezDetailDAOJDBC implements RezDetailDAO_interface {
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/cfa103_g4?serverTimezone=Asia/Taipei";
	String userid = "root";
	String passwd = "6071";
	
	private static final String INSERT_STMT = 
			"INSERT INTO `CFA103_G4`.`REZ_DETAIL` (`REZNO`, `TABLENO`) VALUES (?, ?);";
	private static final String GET_ALL_STMT =
			"SELECT `REZNO`, `TABLENO` FROM REZ_DETAIL";
	private static final String GET_ONE_BY_REZNO = 
			"SELECT REZNO, TABLENO FROM REZ_DETAIL where REZNO=?";
	private static final String UPDATE =
			"UPDATE REZ_DETAIL set TABLENO=? where REZNO=?";
			
	@Override
	public RezDetailVO findByTableno(Integer tableno) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(RezDetailVO rezdetailVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setInt(1, rezdetailVO.getRezno());
			pstmt.setInt(2, rezdetailVO.getTableno());
			
			pstmt.executeUpdate();
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
	public void update(RezDetailVO rezdetailVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, rezdetailVO.getTableno());
			pstmt.setInt(2, rezdetailVO.getRezno());

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
	public List<RezDetailVO> getAll() {
		
		List<RezDetailVO> list = new ArrayList<RezDetailVO>();
		RezDetailVO rezdetailVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				// RezDetailVO ÔøΩÔøΩ?ï¶ÔøΩÔÅ∏ÔøΩÓø¢?ï≠ËπåÔøΩ Domain objects
				rezdetailVO = new RezDetailVO();
				rezdetailVO.setRezno(rs.getInt("rezno"));
				rezdetailVO.setTableno(rs.getInt("tableno"));
				list.add(rezdetailVO);
			}
			
			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
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
	public RezDetailVO findByRezNo(Integer rezno) {
		
		RezDetailVO rezdetailVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_BY_REZNO);
			pstmt.setInt(1, rezno);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				
				rezdetailVO = new RezDetailVO();
				rezdetailVO.setRezno(rs.getInt("rezno"));
				rezdetailVO.setTableno(rs.getInt("tableno"));
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
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
		return rezdetailVO;
	}
	
	
	
	
	public static void main(String[] args) {
		RezDetailDAOJDBC dao = new RezDetailDAOJDBC();
		
		// ÔøΩÓ?üÔøΩÓπµÔøΩÔøΩÔøΩ
//		RezDetailVO rezDetailVO2 = new RezDetailVO();
//		rezDetailVO2.setRezno(10);
//		rezDetailVO2.setTableno(10);
		
		
		// ??ôË?êÔøΩÓºøÓ?áÂ?ôÔøΩ
		List<RezDetailVO> list = dao.getAll();
		for (RezDetailVO aRezDetail : list) {
			System.out.print(aRezDetail.getRezno() + ",");
			System.out.print(aRezDetail.getTableno() + ",");
			System.out.println();
		}
	}
}
