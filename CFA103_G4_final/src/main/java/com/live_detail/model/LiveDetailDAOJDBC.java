package com.live_detail.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jdbc.util.CompositeQuery.jdbcUtil_CompositeQuery_LiveDetail;

public class LiveDetailDAOJDBC implements LiveDetailDAO_interface {
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/CFA103_G4?serverTimezone=Asia/Taipei";
	String userid = "root";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO `cfa103_g4`.`live_detail` (`LIVENO`, `MEALNO`, `MEAL_AMOUNT`, `MEAL_STATUS`, `MEAL_NOTE`, `MEAL_SET`, `MEAL_PRICE`) VALUES (?,?,?,?,?,?,?)";
	private static final String GET_ALL_STMT = "SELECT `LIVENO`, `MEALNO`, `MEAL_AMOUNT`,`MEAL_PRICE`, `MEAL_STATUS`, `MEAL_NOTE`, `MEAL_SET` FROM live_detail";
	private static final String GET_ONE_STMT = "SELECT `LIVENO`, `MEALNO`, `MEAL_AMOUNT`,`MEAL_PRICE`, `MEAL_STATUS`, `MEAL_NOTE`, `MEAL_SET` FROM live_detail WHERE LIVENO = ?";
	private static final String DELETE = "DELETE FROM live_detail where LIVENO=? and MEALNO=?";
	private static final String UPDATE = "UPDATE live_detail set MEAL_AMOUNT= ? ,MEAL_PRICE=?, MEAL_STATUS= ?, MEAL_NOTE=? where LIVENO = ? and MEALNO=? and MEAL_SET=?";

	@Override
	public void insert(LiveDetailVO LiveDetailVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setInt(1, LiveDetailVO.getLiveno());
			pstmt.setInt(2, LiveDetailVO.getMealno());
			pstmt.setInt(3, LiveDetailVO.getMeal_amount());
			pstmt.setInt(4, LiveDetailVO.getMeal_status());
			pstmt.setString(5, LiveDetailVO.getMeal_note());
			pstmt.setInt(6, LiveDetailVO.getMeal_set());
			pstmt.setInt(7, LiveDetailVO.getMeal_price());
			
			System.out.println(LiveDetailVO.getMeal_note());
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
	public void update(LiveDetailVO LiveDetailVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, LiveDetailVO.getMeal_amount());
			pstmt.setInt(2, LiveDetailVO.getMeal_price());
			pstmt.setInt(3, LiveDetailVO.getMeal_status());
			pstmt.setString(4, LiveDetailVO.getMeal_note());
			pstmt.setInt(5, LiveDetailVO.getLiveno());
			pstmt.setInt(6, LiveDetailVO.getMealno());
			pstmt.setInt(7, LiveDetailVO.getMeal_set());
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
	public void delete(Integer liveno, Integer mealno, Integer meal_set) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);
			pstmt.setInt(1, liveno);
			pstmt.setInt(2, mealno);
			pstmt.setInt(2, meal_set);
			pstmt.executeUpdate();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<LiveDetailVO> findByPrimaryKey(Integer liveno) {
		List<LiveDetailVO> list = new ArrayList<LiveDetailVO>();
		LiveDetailVO liveDetailVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setInt(1, liveno);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				liveDetailVO = new LiveDetailVO();
				liveDetailVO.setLiveno(rs.getInt("Liveno"));
				liveDetailVO.setMealno(rs.getInt("Mealno"));
				liveDetailVO.setMeal_amount(rs.getInt("Meal_amount"));
				liveDetailVO.setMeal_price(rs.getInt("Meal_price"));
				liveDetailVO.setMeal_status(rs.getInt("Meal_status"));
				liveDetailVO.setMeal_note(rs.getString("Meal_note"));
				liveDetailVO.setMeal_set(rs.getInt("Meal_set"));
				list.add(liveDetailVO);
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

	@Override
	public List<LiveDetailVO> getAll() {
		List<LiveDetailVO> list = new ArrayList<LiveDetailVO>();
		LiveDetailVO liveDetailVO = null;

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
				liveDetailVO = new LiveDetailVO();
				liveDetailVO.setLiveno(rs.getInt("Liveno"));
				liveDetailVO.setMealno(rs.getInt("Mealno"));
				liveDetailVO.setMeal_amount(rs.getInt("Meal_amount"));
				liveDetailVO.setMeal_price(rs.getInt("Meal_price"));
				liveDetailVO.setMeal_status(rs.getInt("Meal_status"));
				liveDetailVO.setMeal_note(rs.getString("Meal_note"));
				liveDetailVO.setMeal_set(rs.getInt("Meal_set"));
				list.add(liveDetailVO); // Store the row in the list
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

	@Override
	public void insert2(LiveDetailVO liveDetailVO, Connection con) {

		PreparedStatement pstmt = null;

		try {

			
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setInt(1, liveDetailVO.getLiveno());
			pstmt.setInt(2, liveDetailVO.getMealno());
			pstmt.setInt(3, liveDetailVO.getMeal_amount());
			pstmt.setInt(4, liveDetailVO.getMeal_status());
			pstmt.setString(5, liveDetailVO.getMeal_note());
			pstmt.setInt(6, liveDetailVO.getMeal_set());
			pstmt.setInt(7, liveDetailVO.getMeal_price());
//pstmt.executeUpdate("set auto_increment_offset=10;");
//pstmt.executeUpdate("set auto_increment_increment=10;");
			System.out.println(liveDetailVO.getMeal_note());
			pstmt.executeUpdate();
		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					System.err.print("Transaction is being ");
					System.err.println("rolled back-由-emp");
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. " + excep.getMessage());
				}
			}
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
		}
	}

	public static void main(String args[]) {
		LiveDetailDAO dao = new LiveDetailDAO();

		LiveDetailService Svc = new LiveDetailService();
//		LiveDetailVO vo1 = new LiveDetailVO();
//		vo1.setLiveno(3);
//		vo1.setMealno(12);
//		vo1.setMeal_amount(2);
//		vo1.setMeal_status(1);
//		vo1.setMeal_note("服務員的微笑");
//		vo1.setMeal_set(0);
//
//		dao.insert(vo1);
//		
//		dao.delete(3, 12);
//		
//
//		
//		LiveDetailVO LiveOrderVO2 = new LiveDetailVO();
//		LiveOrderVO2.setLiveno(5);
//		LiveOrderVO2.setMealno(12);
//		LiveOrderVO2.setMeal_amount(3);
//		LiveOrderVO2.setMeal_price(100);
//		LiveOrderVO2.setMeal_status(2);
//		LiveOrderVO2.setMeal_note("好累好累");
//		LiveOrderVO2.setMeal_set(0);
//		dao.update(LiveOrderVO2);
//		
		List<LiveDetailVO> list = Svc.getAllUncooked();
		for (LiveDetailVO LiveOrderVO1 : list) {
			System.out.print(LiveOrderVO1.getLiveno() + ",");
			System.out.print(LiveOrderVO1.getMealno() + ",");
			System.out.print(LiveOrderVO1.getMeal_price() + ",");
			System.out.print(LiveOrderVO1.getMeal_note() + ",");
			System.out.print(LiveOrderVO1.getMeal_set() + ",");
			System.out.print(LiveOrderVO1.getMeal_status() + ",");
			System.out.print(LiveOrderVO1.getMeal_amount());
			System.out.println();
		}
//	
//		List<LiveDetailVO> list = dao.findByPrimaryKey(3);
//		for (LiveDetailVO LiveOrderVO1 : list) {
//			System.out.print(LiveOrderVO1.getLiveno() + ",");
//			System.out.print(LiveOrderVO1.getMealno() + ",");
//			System.out.print(LiveOrderVO1.getMeal_price() + ",");
//			System.out.print(LiveOrderVO1.getMeal_note() + ",");
//			System.out.print(LiveOrderVO1.getMeal_set() + ",");
//			System.out.print(LiveOrderVO1.getMeal_status() + ",");
//			System.out.print(LiveOrderVO1.getMeal_amount());
//			System.out.println();
//		}
//			
	}

	@Override
	public List<LiveDetailVO> getAll(Map<String, String[]> map) {
		List<LiveDetailVO> list = new ArrayList<LiveDetailVO>();
		LiveDetailVO liveDetailVO = null;

		ResultSet rs = null;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			String finalSQL = "select * from live_detail " + jdbcUtil_CompositeQuery_LiveDetail.get_WhereCondition(map)
					+ "order by liveno";
			pstmt = con.prepareStatement(finalSQL);
			System.out.println("●●finalSQL(by DAO) = " + finalSQL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				liveDetailVO = new LiveDetailVO();
				liveDetailVO.setLiveno(rs.getInt("Liveno"));
				liveDetailVO.setMealno(rs.getInt("Mealno"));
				liveDetailVO.setMeal_amount(rs.getInt("Meal_amount"));
				liveDetailVO.setMeal_price(rs.getInt("Meal_price"));
				liveDetailVO.setMeal_status(rs.getInt("Meal_status"));
				liveDetailVO.setMeal_note(rs.getString("Meal_note"));
				liveDetailVO.setMeal_set(rs.getInt("Meal_set"));
				list.add(liveDetailVO); // Store the row in the list
			}

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
