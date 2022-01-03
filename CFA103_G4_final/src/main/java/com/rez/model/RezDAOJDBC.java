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

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.print.attribute.standard.DateTimeAtCreation;
import javax.sql.DataSource;

import com.rez_time.model.*;

public class RezDAOJDBC implements RezDAO_interface {
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/cfa103_g4?serverTimezone=Asia/Taipei";
	String userid = "root";
	String passwd = "password";
	
	private static final String INSERT_STMT = 
			"INSERT INTO `CFA103_G4`.`REZ` (`MEMNO`, `PHONE`, NUM_OF_PPL, `RESERVATIONDATE`, `RESERVATIONTIME`, `EMAIL`, `LASTNAME`, `SEX`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = 
			"SELECT `REZNO`,`MEMNO`, `PHONE`, NUM_OF_PPL, `RESERVATIONDATE`, `RESERVATIONTIME`, `EMAIL`, `LASTNAME`, `SEX` FROM REZ";
	private static final String GET_ONE_BY_REZNO = 
			"SELECT REZNO, MEMNO, PHONE, NUM_OF_PPL, RESERVATIONDATE, `RESERVATIONTIME`, `EMAIL`, `LASTNAME`, `SEX` FROM `CFA103_G4`.`REZ` where REZNO = ?";
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
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
//			pstmt.setInt(1, RezVO.getRezno());
			
			pstmt.setInt(1, rezVO.getMemno());
			pstmt.setString(2, rezVO.getPhone());
			pstmt.setInt(3, rezVO.getNum_of_ppl());
			pstmt.setDate(4, rezVO.getReservationdate());
			pstmt.setTime(5, rezVO.getReservationtime());
			pstmt.setString(6, rezVO.getEmail());
			pstmt.setString(7, rezVO.getLastname());
			pstmt.setString(8, rezVO.getSex());
			
			pstmt.executeUpdate();
			
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("錯誤訊息" + se.getMessage());
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
	public void update(RezVO rezVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
				pstmt3.setInt(1, rezVO.getRezno());
				pstmt3.setString(2, rezVO.getPhone());
				pstmt3.setInt(3, rezVO.getNum_of_ppl());
				pstmt3.setDate(4, rezVO.getReservationdate());
				pstmt3.setTime(5, rezVO.getReservationtime());
				
				pstmt3.setString(6, rezVO.getLastname());
				pstmt3.setString(7, rezVO.getSex());
				pstmt3.executeUpdate();
			}
			
			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver."
					+ e.getMessage());
			// Clean up JDBC resources
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
	public List<RezVO> getAll() {
		
		List<RezVO> list = new ArrayList<RezVO>();
		RezVO RezVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				// RezVO 也稱為 Domain objects
				RezVO = new RezVO();
				RezVO.setRezno(rs.getInt("rezvo"));
				RezVO.setMemno(rs.getInt("memno"));
				RezVO.setPhone(rs.getString("phone"));
				RezVO.setNum_of_ppl(rs.getInt("num_of_ppl"));
				RezVO.setReservationdate(rs.getDate("reservationdate"));
				RezVO.setReservationtime(rs.getTime("reservationtime"));
				RezVO.setEmail(rs.getString("email"));
				RezVO.setLastname(rs.getString("lastname"));
				RezVO.setSex(rs.getString("sex"));
				list.add(RezVO);
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
	public RezVO findByRezNO(Integer rezno) {
		
		RezVO RezVO = null;
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
				
				RezVO = new RezVO();
				RezVO.setRezno(rs.getInt("rezno"));
				RezVO.setMemno(rs.getInt("memno"));
				RezVO.setPhone(rs.getString("phone"));
				RezVO.setNum_of_ppl(rs.getInt("num_of_ppl"));
				RezVO.setReservationdate(rs.getDate("reservationdate"));
				RezVO.setReservationtime(rs.getTime("reservationtime"));
				RezVO.setEmail(rs.getString("email"));
				RezVO.setLastname(rs.getString("lastname"));
				RezVO.setSex(rs.getString("sex"));
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
		return RezVO;
	}
	
	@Override
	public void delete(Integer rezno) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, rezno);

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
	
	public static void main(String[] args) {
		RezDAOJDBC dao = new RezDAOJDBC();
		
		// 新增
//		RezVO rezVO1 = new RezVO();
//		rezVO1.setMemno(null);
//		rezVO1.setPhone("0900123123");
//		rezVO1.setNum_of_ppl(6);
//		rezVO1.setReservationdate(java.sql.Date.valueOf("2021-12-20"));
//		rezVO1.setReservationtime(java.sql.Time.valueOf("11:30:00"));
//		rezVO1.setEmail("qqqwww@gmail.com");
//		rezVO1.setLastname("哭");
//		rezVO1.setSex("先生");
//	dao.insert(rezVO1);
		
		// 修改 
//		RezVO rezVO2 = new RezVO();
//		rezVO2.setRezno(1);
//		rezVO2.setMemno(1);
//		rezVO2.setPhone("0900123123");
//		rezVO2.setNum_of_ppl(1);
//		rezVO2.setReservationdate(java.sql.Date.valueOf("2021-12-15"));
//		rezVO2.setReservationtime(java.sql.Timestamp.valueOf("11:30:00"));
//		rezVO2.setEmail("qqqwww@gmail.com");
//		rezVO2.setLastname("哭阿");
//		rezVO2.setSex("先生");
//		dao.update(rezVO2);
		
		// 刪除 ok
//		dao.delete(13);
		
		// 單一查詢 ok
//		RezVO rezVO3 = dao.findByRezNO(1);
//		System.out.print(rezVO3.getRezno() + ",");
//		System.out.print(rezVO3.getMemno() + ",");
//		System.out.print(rezVO3.getPhone() + ",");
//		System.out.print(rezVO3.getNum_of_ppl() + ",");
//		System.out.print(rezVO3.getReservationdate() + ",");
//		System.out.print(rezVO3.getReservationtime() + ",");
//		System.out.print(rezVO3.getEmail() + ",");
//		System.out.print(rezVO3.getLastname() + ",");
//		System.out.print(rezVO3.getSex());
//		System.out.print("==================================");
		
		// 查詢 ok
//		List<RezVO> list = dao.getAll();
//		for (RezVO aRez : list) {
//			System.out.print(aRez.getRezno() + ",");
//			System.out.print(aRez.getMemno() + ",");
//			System.out.print(aRez.getPhone() + ",");
//			System.out.print(aRez.getNum_of_ppl() + ",");
//			System.out.print(aRez.getReservationdate() + ",");
//			System.out.print(rezVO3.getReservationtime() + ",");
//			System.out.print(rezVO3.getEmail() + ",");
//			System.out.print(rezVO3.getLastname() + ",");
//			System.out.print(rezVO3.getSex());
//			System.out.println();
//		}
	}
}
