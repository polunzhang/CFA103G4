package com.live_detail.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import jdbc.util.CompositeQuery.jdbcUtil_CompositeQuery_LiveDetail;

public class LiveDetailDAO implements LiveDetailDAO_interface {
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CFA103_G4");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

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

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setInt(1, LiveDetailVO.getLiveno());
			pstmt.setInt(2, LiveDetailVO.getMealno());
			pstmt.setInt(3, LiveDetailVO.getMeal_amount());
			pstmt.setInt(4, LiveDetailVO.getMeal_status());
			pstmt.setString(5, LiveDetailVO.getMeal_note());
			pstmt.setInt(6, LiveDetailVO.getMeal_set());
			pstmt.setInt(7, LiveDetailVO.getMeal_price());
//pstmt.executeUpdate("set auto_increment_offset=10;");
//pstmt.executeUpdate("set auto_increment_increment=10;");
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void update(LiveDetailVO LiveDetailVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
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

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);
			pstmt.setInt(1, liveno);
			pstmt.setInt(2, mealno);
			pstmt.setInt(2, meal_set);
			pstmt.executeUpdate();
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

			con = ds.getConnection();
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

			con = ds.getConnection();
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

	@Override
	public List<LiveDetailVO> getAll(Map<String, String[]> map) {
		List<LiveDetailVO> list = new ArrayList<LiveDetailVO>();
		LiveDetailVO liveDetailVO = null;
	
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
		try {
			
			con = ds.getConnection();
			String finalSQL = "select * from live_detail "
		          + jdbcUtil_CompositeQuery_LiveDetail.get_WhereCondition(map)
		          + "order by liveno";
			pstmt = con.prepareStatement(finalSQL);
			System.out.println("●●finalSQL(by DAO) = "+finalSQL);
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
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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