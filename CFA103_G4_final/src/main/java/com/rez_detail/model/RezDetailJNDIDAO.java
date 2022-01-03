package com.rez_detail.model;

import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class RezDetailJNDIDAO implements RezDetailDAO_interface {
	
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CFA103G4");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String INSERT_STMT =
			"INSERT INTO `CFA103_G4`.`REZ_DETAIL` (`REZNO`, `TABLENO`) VALUES (?, ?)";
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
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, rezdetailVO.getRezno());
			pstmt.setInt(2, rezdetailVO.getTableno());

			pstmt.executeUpdate();
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
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				// RezVO ??‹î?ƒè?‚ï¿½î¾? Domain objects
				rezdetailVO = new RezDetailVO();
				rezdetailVO.setRezno(rs.getInt("rezno"));
				rezdetailVO.setTableno(rs.getInt("tableno"));
				list.add(rezdetailVO);
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
	public void update(RezDetailVO rezdetailVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, rezdetailVO.getTableno());
			pstmt.setInt(2, rezdetailVO.getRezno());

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
public RezDetailVO findByRezNo(Integer rezno) {
		
		RezDetailVO rezdetailVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
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
}
