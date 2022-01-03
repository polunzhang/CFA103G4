package com.rez_time.model;

import java.util.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.sql.Date;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.rez.model.*;

public class RezTimeJNDIDAO implements RezTimeDAO_interface {
	
	
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CFA103_G4");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
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
			
			con = ds.getConnection();
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
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE2);
			
			pstmt.setInt(1, reztimeVO.getRez_time_mid());
			pstmt.setInt(2, reztimeVO.getRez_time_no());
			pstmt.executeUpdate();
		} catch (SQLException se) {
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
	public void update3(RezTimeVO reztimeVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE3);
			
			pstmt.setInt(1, reztimeVO.getRez_time_eve());
			pstmt.setInt(2, reztimeVO.getRez_time_no());
			pstmt.executeUpdate();
		} catch (SQLException se) {
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
	public void update(RezTimeVO reztimeVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			con = ds.getConnection();
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
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				
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
			
			con = ds.getConnection();
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
	
	
	public RezTimeVO findByRez_Time_DATE(Date rez_time_date) {
		
		RezTimeVO reztimeVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_BY_REZ_TIME_DATE);
			pstmt.setDate(1, rez_time_date);
			
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
			
			con = ds.getConnection();
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
			
			con = ds.getConnection();
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
	public RezTimeVO findByRez_Time_Date(Date rez_time_date) {
		return null;
	}

	}
	

