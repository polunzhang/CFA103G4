package com.rez.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.print.attribute.standard.DateTimeAtCreation;
import javax.sql.DataSource;

import com.rez_time.model.*;

public class RezDAO implements RezDAO_interface {
	
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
			"INSERT INTO `CFA103_G4`.`REZ` (`REZNO`, `MEMNO`, `PHONE`, NUM_OF_PPL, `RESERVATIONDATE`, `RESERVATIONTIME`, `EMAIL`, `LASTNAME`, `SEX`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String INSERT_STMT2 = 
			"INSERT INTO `CFA103_G4`.`REZ` (`REZNO`,          `PHONE`, NUM_OF_PPL, `RESERVATIONDATE`, `RESERVATIONTIME`, `EMAIL`, `LASTNAME`, `SEX`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String INSERT_STMT3 = 
			"INSERT INTO `CFA103_G4`.`REZ` (`REZNO`,          `PHONE`, NUM_OF_PPL, `RESERVATIONDATE`, `RESERVATIONTIME`,          `LASTNAME`, `SEX`) VALUES (?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = 
			"SELECT `REZNO`,`MEMNO`, `PHONE`, NUM_OF_PPL, `RESERVATIONDATE`, `RESERVATIONTIME`, `EMAIL`, `LASTNAME`, `SEX` FROM REZ";
	private static final String GET_ONE_BY_REZNO = 
			"SELECT REZNO, MEMNO, PHONE, NUM_OF_PPL, RESERVATIONDATE, RESERVATIONTIME, EMAIL, LASTNAME, SEX FROM REZ where REZNO = ?";
	private static final String DELETE =
			"DELETE FROM REZ where REZNO = ?";
	private static final String UPDATE =
			"UPDATE `cfa103_g4`.`rez` set MEMNO=?, PHONE=?, NUM_OF_PPL=?, RESERVATIONDATE=? , RESERVATIONTIME=?, EMAIL=?, LASTNAME=?, SEX=? where REZNO=?";
	private static final String UPDATE2 =
			"UPDATE `cfa103_g4`.`rez` set 		 PHONE=?, NUM_OF_PPL=?, RESERVATIONDATE=? , RESERVATIONTIME=?, EMAIL=?, LASTNAME=?, SEX=? where REZNO=?";
	private static final String UPDATE3 =
			"UPDATE `cfa103_g4`.`rez` set 		 PHONE=?, NUM_OF_PPL=?, RESERVATIONDATE=? , RESERVATIONTIME=?,          LASTNAME=?, SEX=? where REZNO=?";
	
	@Override
	public void insert(RezVO rezVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		
		try {
			
			con = ds.getConnection();
if(rezVO.getMemno()!=null) {		
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setInt(1, rezVO.getRezno());
			pstmt.setInt(2, rezVO.getMemno());
			pstmt.setString(3, rezVO.getPhone());
			pstmt.setInt(4, rezVO.getNum_of_ppl());
			pstmt.setDate(5, rezVO.getReservationdate());
			pstmt.setTime(6, rezVO.getReservationtime());
			pstmt.setString(7, rezVO.getEmail());
			pstmt.setString(8, rezVO.getLastname());
			pstmt.setString(9, rezVO.getSex());
			pstmt.executeUpdate();
}else if(rezVO.getEmail()!=null){
	pstmt2 = con.prepareStatement(INSERT_STMT2);
	pstmt2.setInt(1, rezVO.getRezno());
	pstmt2.setString(2, rezVO.getPhone());
	pstmt2.setInt(3, rezVO.getNum_of_ppl());
	pstmt2.setDate(4, rezVO.getReservationdate());
	pstmt2.setTime(5, rezVO.getReservationtime());
	pstmt2.setString(6, rezVO.getEmail());
	pstmt2.setString(7, rezVO.getLastname());
	pstmt2.setString(8, rezVO.getSex());
	pstmt2.executeUpdate();
	
}else {
	pstmt3 = con.prepareStatement(INSERT_STMT3);
	pstmt3.setInt(1, rezVO.getRezno());
	pstmt3.setString(2, rezVO.getPhone());
	pstmt3.setInt(3, rezVO.getNum_of_ppl());
	pstmt3.setDate(4, rezVO.getReservationdate());
	pstmt3.setTime(5, rezVO.getReservationtime());
	
	pstmt3.setString(6, rezVO.getLastname());
	pstmt3.setString(7, rezVO.getSex());
	pstmt3.executeUpdate();
}

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
	public void update(RezVO rezVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		
		try {
			
			con = ds.getConnection();
if(rezVO.getMemno()!=null) {			
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, rezVO.getMemno());
			pstmt.setString(2, rezVO.getPhone());
			pstmt.setInt(3, rezVO.getNum_of_ppl());
			pstmt.setDate(4, rezVO.getReservationdate());
			pstmt.setTime(5, rezVO.getReservationtime());
			pstmt.setString(6, rezVO.getEmail());
			pstmt.setString(7, rezVO.getLastname());
			pstmt.setString(8, rezVO.getSex());
			pstmt.setInt(9, rezVO.getRezno());
			
			pstmt.executeUpdate();
}else if(rezVO.getEmail()!=null) {			
	pstmt2 = con.prepareStatement(UPDATE2);
	pstmt2.setString(1, rezVO.getPhone());
	pstmt2.setInt(2, rezVO.getNum_of_ppl());
	pstmt2.setDate(3, rezVO.getReservationdate());
	pstmt2.setTime(4, rezVO.getReservationtime());
	pstmt2.setString(5, rezVO.getEmail());
	pstmt2.setString(6, rezVO.getLastname());
	pstmt2.setString(7, rezVO.getSex());
	pstmt2.setInt(8, rezVO.getRezno());
	pstmt2.executeUpdate();
}else {
	pstmt3 = con.prepareStatement(UPDATE3);
	pstmt3.setString(1, rezVO.getPhone());
	pstmt3.setInt(2, rezVO.getNum_of_ppl());
	pstmt3.setDate(3, rezVO.getReservationdate());
	pstmt3.setTime(4, rezVO.getReservationtime());
	
	pstmt3.setString(5, rezVO.getLastname());
	pstmt3.setString(6, rezVO.getSex());
	pstmt3.setInt(7, rezVO.getRezno());
	pstmt3.executeUpdate();
}

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
	public List<RezVO> getAll() {
		
		List<RezVO> list = new ArrayList<RezVO>();
		RezVO rezVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				
				rezVO = new RezVO();
				rezVO.setRezno(rs.getInt("rezno"));
				rezVO.setMemno(rs.getInt("memno"));
				rezVO.setPhone(rs.getString("phone"));
				rezVO.setNum_of_ppl(rs.getInt("num_of_ppl"));
				rezVO.setReservationdate(rs.getDate("reservationdate"));
				rezVO.setReservationtime(rs.getTime("reservationtime"));
				rezVO.setEmail(rs.getString("email"));
				rezVO.setLastname(rs.getString("lastname"));
				rezVO.setSex(rs.getString("sex"));
				list.add(rezVO);
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
	public RezVO findByRezNO(Integer rezno) {
		
		RezVO rezVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
try {
			
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_BY_REZNO);
			pstmt.setInt(1, rezno);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				
				rezVO = new RezVO();
				rezVO.setRezno(rs.getInt("rezno"));
				rezVO.setMemno(rs.getInt("memno"));
				rezVO.setPhone(rs.getString("phone"));
				rezVO.setNum_of_ppl(rs.getInt("num_of_ppl"));
				rezVO.setReservationdate(rs.getDate("reservationdate"));
				rezVO.setReservationtime(rs.getTime("reservationtime"));
				rezVO.setEmail(rs.getString("email"));
				rezVO.setLastname(rs.getString("lastname"));
				rezVO.setSex(rs.getString("sex"));
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
		return rezVO;
	}
	
	@Override
	public void delete(Integer rezno) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, rezno);

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
}
