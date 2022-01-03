package com.meal.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.meal_pic.model.MealPicDAOJDBC;
import com.meal_pic.model.MealPicVO;

public class MealJDBCDAO implements MealDAO_interface{
	//宣告連線所需常數
	public static final String driver = "com.mysql.cj.jdbc.Driver";
	public static final String url = "jdbc:mysql://localhost:3306/CFA103_G4?serverTimezone=Asia/Taipei";
	public static final String userid = "David";
	public static final String passwd = "123456";
	//設定增刪改查之mysql指令
	private static final String INSERT_STMT = "INSERT INTO MEAL (meal_type_no,meal_price,meal_name,meal_intro) VALUES (?, ?, ?, ?)";
	private static final String INSERT_STMT1 = "INSERT INTO MEAL (meal_type_no,meal_price,meal_name,meal_intro) VALUES (?, ?, ?, ?)";
	private static final String DELETE = "DELETE FROM MEAL where mealno = ?";
	private static final String DELETEPIC = "DELETE FROM MEAL_pic where mealno = ?";
	private static final String UPDATE = "UPDATE MEAL set meal_type_no=?, meal_price=? ,meal_name=?, meal_intro=?where mealno = ?";
	private static final String GET_ONE_STMT_BY_MEALNO = "SELECT * FROM MEAL where mealno = ?";
	private static final String GET_ONE_STMT_BY_MEALNAME = "select * from MEAL where MEAL_NAME like '%' ? '%' ";
	private static final String GET_ALL_STMT = "SELECT * FROM MEAL order by mealno";
	private static final String GET_ONE_MEAL_TYPE = "SELECT * FROM MEAL where meal_type_no = ?";
	

	
	




	@Override
	public void insertPicWithMealNo(MealVO mealVO, byte[] mealPic) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			
			// 1●設定於 pstm.executeUpdate()之前
    		con.setAutoCommit(false);
			
    		// 先新增部門
			String cols[] = {"MEALNO"};
			pstmt = con.prepareStatement(INSERT_STMT , cols);			
			pstmt.setInt(1, mealVO.getMeal_type_no());
			pstmt.setInt(2, mealVO.getMeal_price());
			pstmt.setString(3, mealVO.getMeal_name());
			pstmt.setString(4, mealVO.getMeal_intro());
//Statement stmt=	con.createStatement();
//stmt.executeUpdate("set auto_increment_offset=10;");    //自增主鍵-初始值
//stmt.executeUpdate("set auto_increment_increment=10;"); //自增主鍵-遞增
			pstmt.executeUpdate();
			//掘取對應的自增主鍵值
			String next_mealno = null;
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				next_mealno = rs.getString(1);
				System.out.println("自增主鍵值= " + next_mealno +"(剛新增成功的部門編號)");
			} else {
				System.out.println("未取得自增主鍵值");
			}
			rs.close();
			// 再同時新增員工
			MealPicDAOJDBC dao = new MealPicDAOJDBC();
			MealPicVO mealpicvo = new MealPicVO();
//			System.out.println("list.size()-A="+list.size());
//			for (byte MealPicVO : mealPic) {
			mealpicvo.setMealno(new Integer(next_mealno)) ;
			mealpicvo.setMeal_pic(mealPic);
			dao.insert2(mealpicvo,con);
//			}

			// 2●設定於 pstm.executeUpdate()之後
			con.commit();
			con.setAutoCommit(true);
//			System.out.println("list.size()-B="+list.size());
//			System.out.println("新增部門編號" + next_mealno + "時,共有員工" + list.size()
//					+ "人同時被新增");
			
			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					System.err.print("Transaction is being ");
					System.err.println("rolled back-由-dept");
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. "
							+ excep.getMessage());
				}
			}
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
	static {
		try {
			Class.forName(driver);
		}  catch (ClassNotFoundException e) {

			e.printStackTrace();
		}
	}

	@Override
	public void insert(MealVO mealvo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		

		try {
			con=DriverManager.getConnection(url,userid,passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setInt(1, mealvo.getMeal_type_no());
			pstmt.setInt(2, mealvo.getMeal_price());
			pstmt.setString(3, mealvo.getMeal_name());
			pstmt.setString(4, mealvo.getMeal_intro());

			pstmt.executeUpdate();
		
		} catch (SQLException e) {
			e.printStackTrace();
			
			//關閉連線及pstmt
		}finally {
			if(pstmt !=null) {
					try {
						pstmt.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}		
			}
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	

	@Override
	public void update(MealVO mealvo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con=DriverManager.getConnection(url,userid,passwd);
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setInt(1, mealvo.getMeal_type_no());
			pstmt.setInt(2, mealvo.getMeal_price());
			pstmt.setString(3, mealvo.getMeal_name());
			pstmt.setString(4, mealvo.getMeal_intro());
			pstmt.setInt(5, mealvo.getMealno());
			
			pstmt.executeUpdate();
		
		} catch (SQLException e) {
			e.printStackTrace();
			
			//關閉連線及pstmt
		}finally {
			if(pstmt !=null) {
					try {
						pstmt.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}		
			}
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	

	@Override
	public void delete(Integer mealno) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con=DriverManager.getConnection(url,userid,passwd);
			pstmt = con.prepareStatement(DELETE);
			pstmt.setInt(1, mealno);
			
			pstmt.executeUpdate();
		
		} catch (SQLException e) {
			e.printStackTrace();
			
			//關閉連線及pstmt
		}finally {
			if(pstmt !=null) {
					try {
						pstmt.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}		
			}
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	@Override
	public void deleteDataAndPic(Integer mealno) {
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		
		try {
			con=DriverManager.getConnection(url,userid,passwd);
//			con.setAutoCommit(false);
			
//			MealPicDAOJDBC dao = new MealPicDAOJDBC();
//			MealPicVO mealpicvo = new MealPicVO();
//			mealpicvo.setMealno(new Integer(mealno)) ;
//			dao.delete2(mealno,con);
//			pstmt.executeUpdate();
			
			
			pstmt = con.prepareStatement(DELETEPIC);
			pstmt.setInt(1, mealno);
			pstmt2 = con.prepareStatement(DELETE);
			pstmt2.setInt(1, mealno);
			pstmt.executeUpdate();
			pstmt2.executeUpdate();
			



			// 2●設定於 pstm.executeUpdate()之後
//			con.commit();
//			con.setAutoCommit(true);
		
		} catch (SQLException e) {
			e.printStackTrace();
			
			//關閉連線及pstmt
		}finally {
			if(pstmt !=null) {
					try {
						pstmt.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}		
			}
			if(pstmt2 !=null) {
				try {
					pstmt2.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}		
		}
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}	

	@Override
	public MealVO findByPrimaryKey(Integer mealno) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MealVO mealvo = null;
		
	
			try {
				con=DriverManager.getConnection(url,userid,passwd);
				pstmt = con.prepareStatement(GET_ONE_STMT_BY_MEALNO);
				pstmt.setInt(1, mealno);
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					mealvo = new MealVO();
					mealvo.setMealno(mealno);
					mealvo.setMeal_type_no(rs.getInt("MEAL_TYPE_NO"));
					mealvo.setMeal_price(rs.getInt("Meal_price"));
					mealvo.setMeal_name(rs.getString("Meal_name"));
					mealvo.setMeal_intro(rs.getString("Meal_intro"));
				}		
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
			if(pstmt !=null) {
					try {
						pstmt.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}		
			}
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return mealvo;
	}

	@Override
	public List<MealVO> findByMealName(String meal_name) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<MealVO> findByMealNameList = new ArrayList<>();
		
	
			try {
				con=DriverManager.getConnection(url,userid,passwd);
				pstmt = con.prepareStatement(GET_ONE_STMT_BY_MEALNAME);
				pstmt.setString(1, meal_name);
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					MealVO mealvo = new MealVO();
					mealvo.setMealno(rs.getInt("MEALNO"));
					mealvo.setMeal_type_no(rs.getInt("MEAL_TYPE_NO"));
					mealvo.setMeal_price(rs.getInt("MEAL_PRICE"));
					mealvo.setMeal_name(rs.getString("MEAL_NAME"));
					mealvo.setMeal_intro(rs.getString("MEAL_INTRO"));
					findByMealNameList.add(mealvo);
				}		
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
			if(pstmt !=null) {
					try {
						pstmt.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}		
			}
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return findByMealNameList;
	}

	@Override
	public List<MealVO> findByMealType(Integer meal_type_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<MealVO> findByMealTypeList = new ArrayList<>();
		
	
			try {
				con=DriverManager.getConnection(url,userid,passwd);
				pstmt = con.prepareStatement(GET_ONE_MEAL_TYPE);
				pstmt.setInt(1, meal_type_no);
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					MealVO mealvo = new MealVO();
					mealvo.setMealno(rs.getInt("mealno"));
					mealvo.setMeal_type_no(rs.getInt("meal_type_no"));
					mealvo.setMeal_price(rs.getInt("Meal_price"));
					mealvo.setMeal_name(rs.getString("Meal_name"));
					mealvo.setMeal_intro(rs.getString("Meal_intro"));
					findByMealTypeList.add(mealvo);
				}		
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
			if(pstmt !=null) {
					try {
						pstmt.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}		
			}
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return findByMealTypeList;
	}

	@Override
	public List<MealVO> getAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<MealVO> mealvoList = new ArrayList<>();
		
	
			try {
				con=DriverManager.getConnection(url,userid,passwd);
				pstmt = con.prepareStatement(GET_ALL_STMT);
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					MealVO mealvo = new MealVO();
					mealvo.setMealno(rs.getInt("MEALNO"));
					mealvo.setMeal_type_no(rs.getInt("MEAL_TYPE_NO"));
					mealvo.setMeal_price(rs.getInt("Meal_price"));
					mealvo.setMeal_name(rs.getString("Meal_name"));
					mealvo.setMeal_intro(rs.getString("Meal_intro"));
					mealvoList.add(mealvo);
				}		
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
			if(pstmt !=null) {
					try {
						pstmt.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}		
			}
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return mealvoList;
	}
	
	//使用main方法測試
	public static void main(String[] args) {



		MealJDBCDAO dao = new MealJDBCDAO();
		
//		===新增資料===
//		MealVO mealvo1 = new MealVO();
//		mealvo1.setMeal_type_no(1);
//		mealvo1.setMeal_price(20);
//		mealvo1.setMeal_name("柳橙汁");
//		mealvo1.setMeal_intro("新鮮現榨的100%柳橙原汁");
//		dao.insert(mealvo1);
		
		
//		===修改資料===
//		MealVO mealvo2 = new MealVO();
//		mealvo2.setMealno(24);
//		mealvo2.setMeal_type_no(2);
//		mealvo2.setMeal_price(200);
//		mealvo2.setMeal_name("柳橙汁燉飯");
//		mealvo2.setMeal_intro("新鮮現榨的100%柳橙原汁製成的燉飯");
//		dao.update(mealvo2);
		
//		===刪除===
//		dao.delete(24);

		
//		===查詢單筆資料===
		MealVO mealvo3 = dao.findByPrimaryKey(4);
//		印出查詢結果
		System.out.println("MEALNO ="+mealvo3.getMealno());
		System.out.println("MEAL_TYPE_NO ="+mealvo3.getMeal_type_no());
		System.out.println("MEAL_PRICE ="+mealvo3.getMeal_price());
		System.out.println("MEAL_NAME ="+mealvo3.getMeal_name());
		System.out.println("MEAL_INTRO ="+mealvo3.getMeal_intro());
		System.out.println("=========");

//		===查詢食物名稱資料==
//		List<MealVO> findByMealNameList = dao.findByMealName("漢");
//		for(MealVO mealvo4 : findByMealNameList) {	
//		查詢資料分筆列式
//			System.out.println("MEALNO ="+mealvo4.getMealno());
//			System.out.println("MEAL_TYPE_NO ="+mealvo4.getMeal_type_no());
//			System.out.println("MEAL_PRICE ="+mealvo4.getMeal_price());
//			System.out.println("MEAL_NAME ="+mealvo4.getMeal_name());
//			System.out.println("MEAL_INTRO ="+mealvo4.getMeal_intro());
//			System.out.println("==========");
//		}
		
//		===查詢食物類別資料==
//		List<MealVO> findByMealTypeList = dao.findByMealType(2);
//		for(MealVO mealvo4 : findByMealTypeList) {	
//		查詢資料分筆列式
//			System.out.println("MEALNO ="+mealvo4.getMealno());
//			System.out.println("MEAL_PRICE ="+mealvo4.getMeal_price());
//			System.out.println("MEAL_NAME ="+mealvo4.getMeal_name());
//			System.out.println("MEAL_INTRO ="+mealvo4.getMeal_intro());
//			System.out.println("==========");
//		}
	
		
		
//		===查詢整筆資料===
//		List<MealVO> mealvoList = dao.getAll();
//		for(MealVO mealvo : mealvoList) {
		
//		1、查詢資料以字串列式(因MealVO有寫toString方法)
//			System.out.println(mealvo);
		
//		2、查詢資料分筆列式
//			System.out.println("MEALNO ="+mealvo.getMealno());
//			System.out.println("MEAL_TYPE_NO ="+mealvo.getMeal_type_no());
//			System.out.println("MEAL_PRICE ="+mealvo.getMeal_price());
//			System.out.println("MEAL_NAME ="+mealvo.getMeal_name());
//			System.out.println("MEAL_INTRO ="+mealvo.getMeal_intro());
//			System.out.println("==========");
//		}
		
//		3、查詢資料以字串列式(偷懶法)
//		mealvoList.forEach(System.out::println);
	}
	
}
