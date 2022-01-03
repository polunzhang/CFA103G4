package com.meal_pic.model;

import java.io.BufferedInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class MealPicDAOJDBC implements MealPicDAO_interface {
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/CFA103_G4?serverTimezone=Asia/Taipei";
	String userid = "David";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO MEAL_PIC (mealno,meal_pic) VALUES (?, ?)";
	private static final String GET_ALL_STMT = "SELECT meal_pic_no,mealno,meal_pic FROM MEAL_PIC order by meal_pic_no";
	private static final String GET_ONE_STMT = "SELECT meal_pic_no,mealno,meal_pic FROM MEAL_PIC where meal_pic_no = ?";
	private static final String DELETE = "DELETE FROM MEAL_PIC where mealno = ?";
	private static final String UPDATE = "UPDATE MEAL_PIC set mealno=?, meal_pic=? where meal_pic_no = ?";
	private static final String UPDATE_BY_MEALNO = "UPDATE MEAL_PIC set meal_pic=? where mealno = ?";
	
	@Override
	public void insert(MealPicVO MealPicVO) {

		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setInt(1, MealPicVO.getMealno());
			pstmt.setBytes(2, MealPicVO.getMeal_pic());
//pstmt.executeUpdate("set auto_increment_offset=10;");
//pstmt.executeUpdate("set auto_increment_increment=10;");
			pstmt.executeUpdate();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void insert2(MealPicVO MealPicVO,Connection con) {


		PreparedStatement pstmt = null;
		try {
			Class.forName(driver);
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setInt(1, MealPicVO.getMealno());
			pstmt.setBytes(2, MealPicVO.getMeal_pic());
//pstmt.executeUpdate("set auto_increment_offset=10;");
//pstmt.executeUpdate("set auto_increment_increment=10;");
			pstmt.executeUpdate();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(MealPicVO MealPicVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setInt(1, MealPicVO.getMealno());
			pstmt.setBytes(2, MealPicVO.getMeal_pic());
			pstmt.setInt(3, MealPicVO.getMeal_pic_no());
//pstmt.executeUpdate("set auto_increment_offset=10;");
//pstmt.executeUpdate("set auto_increment_increment=10;");
			pstmt.executeUpdate();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void update2(MealPicVO MealPicVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE_BY_MEALNO);
			pstmt.setBytes(1, MealPicVO.getMeal_pic());
			pstmt.setInt(2, MealPicVO.getMealno());
//pstmt.executeUpdate("set auto_increment_offset=10;");
//pstmt.executeUpdate("set auto_increment_increment=10;");
			pstmt.executeUpdate();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Integer mealno) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);
			pstmt.setInt(1, mealno);
			pstmt.executeUpdate();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void delete2(Integer mealno,Connection con) {


		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			pstmt = con.prepareStatement(DELETE);
			pstmt.setInt(1, mealno);
			pstmt.executeUpdate();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	@Override
	public MealPicVO findByPrimaryKey(Integer meal_pic_no) {

		MealPicVO MealPicVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, meal_pic_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				MealPicVO = new MealPicVO();
				MealPicVO.setMeal_pic_no(rs.getInt("Meal_pic_NO"));
				MealPicVO.setMealno(rs.getInt("Mealno"));
				MealPicVO.setMeal_pic(rs.getBytes("Meal_pic"));
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
		return MealPicVO;
	}

	@Override
	public List<MealPicVO> getAll() {
		List<MealPicVO> list = new ArrayList<MealPicVO>();
		MealPicVO MealPicVO = null;

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
				MealPicVO = new MealPicVO();
				MealPicVO.setMeal_pic_no(rs.getInt("Meal_pic_NO"));
				MealPicVO.setMealno(rs.getInt("Mealno"));
				MealPicVO.setMeal_pic(rs.getBytes("Meal_pic"));
				list.add(MealPicVO); // Store the row in the list
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

//	public static void main(String[] args) {
//		MealPicDAOJDBC dao = new MealPicDAOJDBC();
//		dao.delete(3);
//
//		List<MealPicVO> list = dao.getAll();
//		for (MealPicVO LiveOrderVO1 : list) {
//			System.out.print(LiveOrderVO1.getMeal_pic_no() + ",");
//			System.out.print(LiveOrderVO1.getMealno() + ",");
//			System.out.print(LiveOrderVO1.getMeal_pic());
//			System.out.println();
//		}
//
//		MealPicVO LiveOrderVO2 = dao.findByPrimaryKey(1);
//		System.out.print(LiveOrderVO2.getMeal_pic_no() + ",");
//		System.out.print(LiveOrderVO2.getMealno() + ",");
//		System.out.print(LiveOrderVO2.getMeal_pic());
//		System.out.println();
//		
//		LiveOrderVO2.setMeal_pic_no(25);
//		dao.insert(LiveOrderVO2);
//		
//		MealPicVO LiveOrderVO3 = dao.findByPrimaryKey(18);
//		LiveOrderVO3.setMeal_pic_no(19);
//		LiveOrderVO3.setMeal_pic(null);
//		dao.update(LiveOrderVO3);
//
//	}
}
