package com.online_detail.model;

import java.util.*;
import java.sql.*;

public class OnlineDetailJDBCDAO implements OnlineDetailDAO_interface{
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/CFA103_G4?serverTimezone=Asia/Taipei";
	String userid = "David";
	String passwd = "123456";

	private static final String INSERT_STMT = 
			"INSERT INTO online_detail (olno, mealno, meal_amount, meal_price, meal_status, meal_note, meal_set) VALUES (?,?,?,?,?,?,?)";
	private static final String GET_ALL_STMT = 
			"SELECT olno, mealno, meal_amount, meal_price, meal_status, meal_note, meal_set FROM online_detail";
	private static final String GET_ONE_BY_OLNO = 
			"SELECT olno, mealno, meal_amount, meal_price, meal_status, meal_note, meal_set FROM online_detail where olno = ?";
	private static final String GET_ONE_BY_MEAL_STATUS = 
			"SELECT olno, mealno, meal_amount, meal_price, meal_status, meal_note, meal_set FROM online_detail where meal_status= ?";
	private static final String UPDATE = 
			"UPDATE online_detail set meal_amount = ? ,meal_price = ?, meal_status = ?, meal_note = ?,meal_set = ? where olno = ? and mealno=?";
	private static final String DELETE = 
			"DELETE FROM online_detail where mealno = ? ";

	@Override
	public void insert(OnlineDetailVO onlineDetailVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setInt(1, onlineDetailVO.getOlno());
			pstmt.setInt(2, onlineDetailVO.getMealno());
			pstmt.setInt(3, onlineDetailVO.getMeal_amount());
			pstmt.setInt(4, onlineDetailVO.getMeal_price());
			pstmt.setInt(5, onlineDetailVO.getMeal_status());
			pstmt.setString(6, onlineDetailVO.getMeal_note());
			pstmt.setInt(7, onlineDetailVO.getMeal_set());

			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public void update(OnlineDetailVO onlineDetailVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setInt(6, onlineDetailVO.getOlno());
			pstmt.setInt(7, onlineDetailVO.getMealno());
			pstmt.setInt(1, onlineDetailVO.getMeal_amount());
			pstmt.setInt(2, onlineDetailVO.getMeal_price());
			pstmt.setInt(3, onlineDetailVO.getMeal_status());
			pstmt.setString(4, onlineDetailVO.getMeal_note());
			pstmt.setInt(5, onlineDetailVO.getMeal_set());

			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public void delete(Integer mealno) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
	
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setInt(1, mealno);
			pstmt.executeUpdate();
			
		}catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		}catch(SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		}finally {
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
	public List<OnlineDetailVO> findByOlno(Integer olno){
		List<OnlineDetailVO> list = new ArrayList<OnlineDetailVO>();
		OnlineDetailVO onlineDetailVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_BY_OLNO);
			pstmt.setInt(1,olno);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				// empVO 也稱為 Domain objects
				onlineDetailVO = new OnlineDetailVO();
				onlineDetailVO.setOlno(rs.getInt("olno"));
				onlineDetailVO.setMealno(rs.getInt("mealno"));
				onlineDetailVO.setMeal_amount(rs.getInt("meal_amount"));
				onlineDetailVO.setMeal_price(rs.getInt("meal_price"));
				onlineDetailVO.setMeal_status(rs.getInt("meal_status"));
				onlineDetailVO.setMeal_note(rs.getString("meal_note"));
				onlineDetailVO.setMeal_set(rs.getInt("meal_set"));
				list.add(onlineDetailVO);// Store the row in the vector
			}
			
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
	public List<OnlineDetailVO> findByMealStatus(Integer meal_status){
		List<OnlineDetailVO> list = new ArrayList<OnlineDetailVO>();
		OnlineDetailVO onlineDetailVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_BY_MEAL_STATUS);
			pstmt.setInt(1,meal_status);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				// onlineDetailVO 也稱為 Domain objects
				onlineDetailVO = new OnlineDetailVO();
				onlineDetailVO.setOlno(rs.getInt("olno"));
				onlineDetailVO.setMealno(rs.getInt("mealno"));
				onlineDetailVO.setMeal_amount(rs.getInt("meal_amount"));
				onlineDetailVO.setMeal_price(rs.getInt("meal_price"));
				onlineDetailVO.setMeal_status(rs.getInt("meal_status"));
				onlineDetailVO.setMeal_note(rs.getString("meal_note"));
				onlineDetailVO.setMeal_set(rs.getInt("meal_set"));
				list.add(onlineDetailVO);// Store the row in the vector
			}
			
			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured." +se.getMessage());
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
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
	public List<OnlineDetailVO> getAll(){
		List<OnlineDetailVO> list = new ArrayList<OnlineDetailVO>();
		OnlineDetailVO onlineDetailVO = null;
		
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
				onlineDetailVO = new OnlineDetailVO();
				onlineDetailVO.setOlno(rs.getInt("olno"));
				onlineDetailVO.setMealno(rs.getInt("mealno"));
				onlineDetailVO.setMeal_amount(rs.getInt("meal_amount"));
				onlineDetailVO.setMeal_price(rs.getInt("meal_price"));
				onlineDetailVO.setMeal_status(rs.getInt("meal_status"));
				onlineDetailVO.setMeal_note(rs.getString("meal_note"));
				onlineDetailVO.setMeal_set(rs.getInt("meal_set"));
				list.add(onlineDetailVO); // Store the row in the list
			}
			
			// Handle any driver errors
		}catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public void insert2 (OnlineDetailVO onlineDetailVO, Connection con) {
		
		PreparedStatement pstmt = null;
		
		try {
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setInt(1, onlineDetailVO.getOlno());
			pstmt.setInt(2, onlineDetailVO.getMealno());
			pstmt.setInt(3, onlineDetailVO.getMeal_amount());
			pstmt.setInt(4, onlineDetailVO.getMeal_price());
			pstmt.setInt(5, onlineDetailVO.getMeal_status());
			pstmt.setString(6, onlineDetailVO.getMeal_note());
			pstmt.setInt(7, onlineDetailVO.getMeal_set());
			pstmt.executeUpdate();
			
			// Handle any SQL errors
		} catch(SQLException se) {
			if(con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					System.err.print("Transaction is being ");
					System.err.println("rolled back-由-onlineDetail");
					con.rollback();
				} catch(SQLException excep) {
					throw new RuntimeException("rollback error occured. "
							+ excep.getMessage());
				}
			}
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}
		
	}
	
	public static void main(String[] args) {
		OnlineDetailJDBCDAO dao = new OnlineDetailJDBCDAO();
		
		OnlineDetailService Svc = new OnlineDetailService();
		//新增
//		OnlineDetailVO onlineDetailVO1 = new OnlineDetailVO();
//		onlineDetailVO1.setOlno(5);
//		onlineDetailVO1.setMealno(23);
//		onlineDetailVO1.setMeal_amount(2);
//		onlineDetailVO1.setMeal_price(160);
//		onlineDetailVO1.setMeal_status(1);
//		onlineDetailVO1.setMeal_note("不要微波");
//		onlineDetailVO1.setMeal_set(0);
//		dao.insert(onlineDetailVO1);
		//刪除
//		dao.delete(12);
		
		//修改
//		OnlineDetailVO onlineDetailVO2 = new OnlineDetailVO();
//		onlineDetailVO2.setOlno(5);
//		onlineDetailVO2.setMealno(23);
//		onlineDetailVO2.setMeal_amount(3);
//		onlineDetailVO2.setMeal_price(90);
//		onlineDetailVO2.setMeal_status(1);
//		onlineDetailVO2.setMeal_note("不要冰塊");
//		onlineDetailVO2.setMeal_set(0);
//		dao.update(onlineDetailVO2);
		
		//用訂單編號查詢
//		List<OnlineDetailVO> list1 = dao.findByOlno(2);
//		for(OnlineDetailVO onlineDetailVO3:list1) {
//			System.out.print(onlineDetailVO3.getOlno() + ",");
//			System.out.print(onlineDetailVO3.getMealno() + ",");
//			System.out.print(onlineDetailVO3.getMeal_amount() + ",");
//			System.out.print(onlineDetailVO3.getMeal_price() + ",");
//			System.out.print(onlineDetailVO3.getMeal_status() + ",");
//			System.out.print(onlineDetailVO3.getMeal_note() + ",");
//			System.out.print(onlineDetailVO3.getMeal_set());
//			System.out.println();
//		}
		
		//用餐點狀態查詢
		List<OnlineDetailVO> list2 = dao.findByMealStatus(1);
		for(OnlineDetailVO onlineDetailVO4:list2) {
			System.out.print(onlineDetailVO4.getOlno() + ",");
			System.out.print(onlineDetailVO4.getMealno() + ",");
			System.out.print(onlineDetailVO4.getMeal_amount() + ",");
			System.out.print(onlineDetailVO4.getMeal_price() + ",");
			System.out.print(onlineDetailVO4.getMeal_status() + ",");
			System.out.print(onlineDetailVO4.getMeal_note() + ",");
			System.out.print(onlineDetailVO4.getMeal_set());
			System.out.println();
		}
		
		//查全部
//		List<OnlineDetailVO> list2 = dao.getAll();
//		for(OnlineDetailVO onlineDetailVO5:list2) {
//			System.out.print(onlineDetailVO5.getOlno() + ",");
//			System.out.print(onlineDetailVO5.getMealno() + ",");
//			System.out.print(onlineDetailVO5.getMeal_amount() + ",");
//			System.out.print(onlineDetailVO5.getMeal_price() + ",");
//			System.out.print(onlineDetailVO5.getMeal_status() + ",");
//			System.out.print(onlineDetailVO5.getMeal_note() + ",");
//			System.out.print(onlineDetailVO5.getMeal_set());
//			System.out.println();
//		}
		
	}
}
