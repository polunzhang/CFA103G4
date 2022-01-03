package com.meal_type.model;

import java.util.*;
import java.sql.*;

public class MealTypeJDBCDAO implements MealTypeDAO_interface{
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/CFA103_G4?serverTimezone=Asia/Taipei";
	String userid = "David";
	String passwd = "123456";
	
	private static final String INSERT_STMT = 
			"INSERT INTO meal_type set meal_type_name = ?";
	private static final String GET_ALL_STMT =
			"SELECT meal_type_no,meal_type_name FROM meal_type order by meal_type_no";
	private static final String GET_ONE_STMT =
			"SELECT meal_type_no,meal_type_name FROM meal_type where meal_type_no = ?";
	private static final String UPDATE =
			"UPDATE meal_type set meal_type_name = ? where meal_type_no = ?";
	private static final String DELETE =
			"DELETE FROM meal_type where meal_type_no = ?";
	
	@Override
	public void insert(MealTypeVO mealTypeVO) {
		
		Connection con  = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1, mealTypeVO.getMeal_type_name());

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
	public void update(MealTypeVO mealTypeVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setString(1, mealTypeVO.getMeal_type_name());
			pstmt.setInt(2, mealTypeVO.getMeal_type_no());
			pstmt.executeUpdate();
			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured." + se.getMessage());
	
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
	public void delete(Integer meal_type_no) {
		
		Connection con  = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver); //載入驅動
			con = DriverManager.getConnection(url, userid, passwd);//建立連線
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setInt(1, meal_type_no);
			pstmt.executeUpdate();
		}catch(SQLException se) {
			throw new RuntimeException("A database error occured." + se.getMessage());
		
		}catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		}finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if(con != null) {
				try {
					con.close();
				}catch(Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}
	
	@Override
	public MealTypeVO findByPrimaryKey(Integer meal_type_no) {
		MealTypeVO mealTypeVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setInt(1, meal_type_no);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				// empVO 也稱為 Domain objects
				mealTypeVO = new MealTypeVO();
				mealTypeVO.setMeal_type_no(rs.getInt("meal_type_no"));
				mealTypeVO.setMeal_type_name(rs.getString("meal_type_name"));
			}
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
		return mealTypeVO;
	}

	
	public List<MealTypeVO> getAll(){
		List<MealTypeVO> list = new ArrayList<MealTypeVO>();
		MealTypeVO mealTypeVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// mealtypeVO 也稱為 Domain objects
				mealTypeVO = new MealTypeVO();
				mealTypeVO.setMeal_type_no(rs.getInt("meal_type_no"));
				mealTypeVO.setMeal_type_name(rs.getString("Meal_type_name"));
				list.add(mealTypeVO); // Store the row in the list
			}
			
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
	
	public static void main(String[] args) {
		MealTypeJDBCDAO dao = new MealTypeJDBCDAO();
		
//		新增
		MealTypeVO mealTypeVO1 = new MealTypeVO();
		mealTypeVO1.setMeal_type_name("本日促銷");
		dao.insert(mealTypeVO1);
		
		//修改
//		MealTypeVO mealTypeVO2 = new MealTypeVO();
//		mealTypeVO2.setMeal_type_no(11);
//		mealTypeVO2.setMeal_type_name("本日精選");
//		dao.update(mealTypeVO2);
		
		//刪除
//		dao.delete(5);		
		
		//用pk查詢
//		MealTypeVO mealTypeVO3 = dao.findByPrimaryKey(5);
//		System.out.print(mealTypeVO3.getMeal_type_no() + ",");
//		System.out.print(mealTypeVO3.getMeal_type_name());

		//查全部
//		List<MealTypeVO> list = dao.getAll();
//		for(MealTypeVO mealTypeVO4:list) {
//			System.out.print(mealTypeVO4.getMeal_type_no() + ",");
//			System.out.print(mealTypeVO4.getMeal_type_name());
//			System.out.println();
//		}
	}
}
