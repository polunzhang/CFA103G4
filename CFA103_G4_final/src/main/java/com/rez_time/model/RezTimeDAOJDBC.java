package com.rez_time.model;

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

import com.rez.model.RezDAOJDBC;
import com.rez.model.RezVO;
import com.rez.model.*;

public class RezTimeDAOJDBC implements RezTimeDAO_interface {
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/cfa103_g4?serverTimezone=Asia/Taipei";
	String userid = "root";
	String passwd = "password";
	
	private static final String INSERT_STMT =
			"INSERT INTO `cfa103_g4`.`rez_time`"
		+	"(`REZ_TIME_DATE`, `REZ_TIME_MID_LIMIT`, `REZ_TIME_MID`, `REZ_TIME_EVE_LIMIT`, `REZ_TIME_EVE`)"
		+	"VALUES (DATE_FORMAT(NOW() + INTERVAL 1 DAY,'%Y-%m-%d'), 24, ?, 24, ?)";
	private static final String GET_ALL_STMT =
			"SELECT `REZ_TIME_NO`, `REZ_TIME_DATE`, `REZ_TIME_MID_LIMIT`, `REZ_TIME_MID`, `REZ_TIME_EVE_LIMIT`, `REZ_TIME_EVE` FROM REZ_TIME";
	private static final String GET_ONE_BY_REZ_TIME_NO =
			"SELECT REZ_TIME_NO, `REZ_TIME_DATE`, REZ_TIME_MID_LIMIT, REZ_TIME_MID, REZ_TIME_EVE_LIMIT, REZ_TIME_EVE FROM `CFA103_G4`.`REZ_TIME` where REZ_TIME_NO = ?";
	private static final String GET_ONE_BY_REZ_TIME_DATE =
			"SELECT REZ_TIME_NO, `REZ_TIME_DATE`, REZ_TIME_MID_LIMIT, REZ_TIME_MID, REZ_TIME_EVE_LIMIT, REZ_TIME_EVE FROM `CFA103_G4`.`REZ_TIME` where REZ_TIME_DATE = ?";
	private static final String GET_ONE_BY_REZ_TIME_MID =
			"SELECT REZ_TIME_NO, `REZ_TIME_DATE`, REZ_TIME_MID_LIMIT, REZ_TIME_MID, REZ_TIME_EVE_LIMIT, REZ_TIME_EVE FROM `CFA103_G4`.`REZ_TIME` where REZ_TIME_MID = ?";
	private static final String GET_ONE_BY_REZ_TIME_EVE =
			"SELECT REZ_TIME_NO, `REZ_TIME_DATE`, REZ_TIME_MID_LIMIT, REZ_TIME_MID, REZ_TIME_EVE_LIMIT, REZ_TIME_EVE FROM `CFA103_G4`.`REZ_TIME` where REZ_TIME_EVE = ?";
	
	
	private static final String UPDATE =
			"UPDATE REZ_TIME set REZ_TIME_DATE=?, REZ_TIME_MID_LIMIT=?, REZ_TIME_MID=?, REZ_TIME_EVE_LIMIT=?, REZ_TIME_EVE=? where REZ_TIME_NO=?";	
	private static final String UPDATE2 = 
			"UPDATE REZ_TIME set REZ_TIME_MID=? where REZ_TIME_NO=?";
	private static final String UPDATE3 = 
			"UPDATE REZ_TIME set REZ_TIME_EVE=? where REZ_TIME_NO=?";
	
	@Override
	public void insert(RezTimeVO reztimeVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setDate(1, reztimeVO.getRez_time_date());
			pstmt.setInt(2, reztimeVO.getRez_time_mid_limit());
			pstmt.setInt(3, reztimeVO.getRez_time_mid());
			pstmt.setInt(4, reztimeVO.getRez_time_eve_limit());
			pstmt.setInt(5, reztimeVO.getRez_time_eve());
			
			pstmt.executeUpdate();
			
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
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
	public void update2(RezTimeVO reztimeVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE2);
			
			pstmt.setInt(1, reztimeVO.getRez_time_mid());
			pstmt.setInt(2, reztimeVO.getRez_time_no());
			pstmt.executeUpdate();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
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
	public void update3(RezTimeVO reztimeVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE3);
			
			pstmt.setInt(1, reztimeVO.getRez_time_eve());
			pstmt.setInt(2, reztimeVO.getRez_time_no());
			pstmt.executeUpdate();
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
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
	public void update(RezTimeVO reztimeVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setDate(1, reztimeVO.getRez_time_date());
			pstmt.setInt(2, reztimeVO.getRez_time_mid_limit());
			pstmt.setInt(3, reztimeVO.getRez_time_mid());
			pstmt.setInt(4, reztimeVO.getRez_time_eve_limit());
			pstmt.setInt(5, reztimeVO.getRez_time_eve());
			pstmt.setInt(6, reztimeVO.getRez_time_no());
			
			pstmt.executeUpdate();
			
			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
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
	public List<RezTimeVO> getAll() {
		
		List<RezTimeVO> list = new ArrayList<RezTimeVO>();
		RezTimeVO reztimeVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				// RezTimeVO 也稱為 Domain objects
				reztimeVO = new RezTimeVO();
				reztimeVO.setRez_time_no(rs.getInt("rez_time_no"));
				reztimeVO.setRez_time_date(rs.getDate("rez_time_date"));
				reztimeVO.setRez_time_mid_limit(rs.getInt("rez_time_mid_limit"));
				reztimeVO.setRez_time_mid(rs.getInt("rez_time_mid"));
				reztimeVO.setRez_time_eve_limit(rs.getInt("rez_time_eve_limit"));
				reztimeVO.setRez_time_eve(rs.getInt("rez_time_eve"));
				list.add(reztimeVO);
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
	public RezTimeVO findByRez_Time_NO(Integer rez_time_no) {
		
		RezTimeVO reztimeVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_BY_REZ_TIME_NO);
			pstmt.setInt(1, rez_time_no);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				
				reztimeVO = new RezTimeVO();
				reztimeVO.setRez_time_no(rs.getInt("rez_time_no"));
				reztimeVO.setRez_time_date(rs.getDate("rez_time_date"));
				reztimeVO.setRez_time_mid_limit(rs.getInt("rez_time_mid_limit"));
				reztimeVO.setRez_time_mid(rs.getInt("rez_time_mid"));
				reztimeVO.setRez_time_eve_limit(rs.getInt("rez_time_eve_limit"));
				reztimeVO.setRez_time_eve(rs.getInt("rez_time_eve"));
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
		return reztimeVO;
	}
	
	@Override
	public RezTimeVO findByRez_Time_Mid(Integer rez_time_mid) {
		
		RezTimeVO reztimeVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_BY_REZ_TIME_MID);
			pstmt.setInt(1, rez_time_mid);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				
				reztimeVO = new RezTimeVO();
				reztimeVO.setRez_time_no(rs.getInt("rez_time_no"));
				reztimeVO.setRez_time_date(rs.getDate("rez_time_date"));
				reztimeVO.setRez_time_mid_limit(rs.getInt("rez_time_mid_limit"));
				reztimeVO.setRez_time_mid(rs.getInt("rez_time_mid"));
				reztimeVO.setRez_time_eve_limit(rs.getInt("rez_time_eve_limit"));
				reztimeVO.setRez_time_eve(rs.getInt("rez_time_eve"));
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
		return reztimeVO;
	}
	
	@Override
	public RezTimeVO findByRez_Time_Eve(Integer rez_time_eve) {
		
		RezTimeVO reztimeVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_BY_REZ_TIME_EVE);
			pstmt.setInt(1, rez_time_eve);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				
				reztimeVO = new RezTimeVO();
				reztimeVO.setRez_time_no(rs.getInt("rez_time_no"));
				reztimeVO.setRez_time_date(rs.getDate("rez_time_date"));
				reztimeVO.setRez_time_mid_limit(rs.getInt("rez_time_mid_limit"));
				reztimeVO.setRez_time_mid(rs.getInt("rez_time_mid"));
				reztimeVO.setRez_time_eve_limit(rs.getInt("rez_time_eve_limit"));
				reztimeVO.setRez_time_eve(rs.getInt("rez_time_eve"));
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
		return reztimeVO;
	}
	
	
	public static void main(String[] args) {
		RezTimeDAOJDBC dao = new RezTimeDAOJDBC();
		
		// 新增
//		RezTimeVO reztimeVO1 = new RezTimeVO();
//		reztimeVO1.setRez_time_no(8);
//		reztimeVO1.setRez_time_mid_limit(20);
//		reztimeVO1.setRez_time_mid(2);
//		reztimeVO1.setRez_time_eve_limit(20);
//		reztimeVO1.setRez_time_eve(2);
//		dao.insert(reztimeVO1);
		
		// 修改
//		RezTimeVO reztimeVO2 = new RezTimeVO();
//		reztimeVO2.setRez_time_no(8);
//		reztimeVO2.setRez_time_mid_limit(21);
//		reztimeVO2.setRez_time_mid(3);
//		reztimeVO2.setRez_time_eve_limit(21);
//		reztimeVO2.setRez_time_eve(3);
//		dao.update(reztimeVO2);
//		
		// 刪除
//		dao.delete(1);
		
		// 單一查詢
//		RezTimeVO reztimeVO3 = dao.findByRez_Time_NO(1);
//		System.out.print(reztimeVO3.getRez_time_no() + ",");
//		System.out.print(reztimeVO3.getRez_time_mid_limit() + ",");
//		System.out.print(reztimeVO3.getRez_time_mid() + ",");
//		System.out.print(reztimeVO3.getRez_time_eve_limit() + ",");
//		System.out.print(reztimeVO3.getRez_time_eve());
//		System.out.print("==================================");
		
		// 查詢
		List<RezTimeVO> list = dao.getAll();
		for (RezTimeVO areztime : list) {
			System.out.print(areztime.getRez_time_no() + ",");
			System.out.print(areztime.getRez_time_mid_limit() + ",");
			System.out.print(areztime.getRez_time_mid() + ",");
			System.out.print(areztime.getRez_time_eve_limit() + ",");
			System.out.print(areztime.getRez_time_eve());
			System.out.println();
		}
	}

	@Override
	public RezTimeVO findByRez_Time_Date(Date rez_time_date) {
		// TODO Auto-generated method stub
		return null;
	}

}