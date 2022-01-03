package com.prom_detail.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.meal.model.MealJDBCDAO;
import com.meal.model.MealVO;
import com.prom.model.PromVO;

public class PromDetailJDBCDAO implements PromDetailDAO_interface{
	//宣告連線所需常數
	public static final String driver = "com.mysql.cj.jdbc.Driver";
	public static final String url = "jdbc:mysql://localhost:3306/CFA103_G4?serverTimezone=Asia/Taipei";
	public static final String userid = "David";
	public static final String passwd = "123456";
	//設定增刪改查之mysql指令
	private static final String INSERT_STMT = "INSERT INTO `CFA103_G4`.`prom_detail` (`PROMNO`, `MEALNO`, `PROM_VALUE`, `RROM_TIME_START`, `PROM_TIME_END`, `PROM_STATE`) VALUES (?,?,?,?,?,?)";
	private static final String DELETE = "DELETE FROM prom_detail WHERE (`PROMNO` = ?) and (`MEALNO` = ?)";
	private static final String UPDATE = "UPDATE `CFA103_G4`.`prom_detail` SET `MEALNO` = ?, `PROM_VALUE` = ?, `RROM_TIME_START` = ?, `PROM_TIME_END` = ?, `PROM_STATE` = ? WHERE (`PROMNO` = ? and mealno = ?)";
	private static final String GET_ONE_STMT = "SELECT * FROM prom_detail where PROMNO = ?";
	private static final String GET_ALL_STMT = "SELECT * FROM prom_detail order by PROMNO";
	private static final String GET_ONE_MEALNO = "SELECT * FROM prom_detail d where MEALNO = ?";
	private static final String GET_ONE_STMT_LIMITONE = "SELECT * FROM prom_detail where PROMNO = ? limit 1";
	

	
	static {
		try {
			Class.forName(driver);
		}  catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void insert(PromDetailVO promdetailvo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		

		try {
			con=DriverManager.getConnection(url,userid,passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setInt(1, promdetailvo.getPromno());
			pstmt.setInt(2, promdetailvo.getMealno());
			pstmt.setDouble(3, promdetailvo.getProm_value());
			pstmt.setDate(4, promdetailvo.getProm_time_start());
			pstmt.setDate(5, promdetailvo.getProm_time_end());
			pstmt.setInt(6, promdetailvo.getProm_state());
			
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
	public void update(PromDetailVO promdetailvo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con=DriverManager.getConnection(url,userid,passwd);
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setInt(1, promdetailvo.getMealno());
			pstmt.setDouble(2, promdetailvo.getProm_value());
			pstmt.setDate(3, promdetailvo.getProm_time_start());
			pstmt.setDate(4, promdetailvo.getProm_time_end());
			pstmt.setInt(5, promdetailvo.getProm_state());
			pstmt.setInt(6, promdetailvo.getPromno());
			pstmt.setInt(7, promdetailvo.getMealno());
			
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
	public void delete(PromDetailVO promdetailvo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con=DriverManager.getConnection(url,userid,passwd);
			pstmt = con.prepareStatement(DELETE);
			pstmt.setInt(1, promdetailvo.getPromno());
			pstmt.setInt(2, promdetailvo.getMealno());
			
			
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
	public List<PromDetailVO> findByPrimaryKey(Integer promno) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<PromDetailVO> findByPrimaryKeyList = new ArrayList<>();
	
			try {
				con=DriverManager.getConnection(url,userid,passwd);
				pstmt = con.prepareStatement(GET_ONE_STMT);
				pstmt.setInt(1, promno);
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					PromDetailVO promdetailvo = new PromDetailVO();
					promdetailvo.setPromno(promno);
					promdetailvo.setMealno(rs.getInt("MEALNO"));
					promdetailvo.setProm_value(rs.getDouble("PROM_VALUE"));
					promdetailvo.setProm_time_start(rs.getDate("RROM_TIME_START"));
					promdetailvo.setProm_time_end(rs.getDate("PROM_TIME_END"));
					promdetailvo.setProm_state(rs.getInt("PROM_STATE"));
					findByPrimaryKeyList.add(promdetailvo);
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
		return findByPrimaryKeyList;
	}


	@Override
	public List<PromDetailVO> findByMealNo(Integer mealno) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<PromDetailVO> findByMealNoList = new ArrayList<>();
		
	
			try {
				con=DriverManager.getConnection(url,userid,passwd);
				pstmt = con.prepareStatement(GET_ONE_MEALNO);
				pstmt.setInt(1, mealno);
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					PromDetailVO promdetailvo = new PromDetailVO();
					promdetailvo.setPromno(rs.getInt("PROMNO"));
					promdetailvo.setMealno(rs.getInt("MEALNO"));
					promdetailvo.setProm_value(rs.getDouble("PROM_VALUE"));
					promdetailvo.setProm_time_start(rs.getDate("RROM_TIME_START"));
					promdetailvo.setProm_time_end(rs.getDate("PROM_TIME_END"));
					promdetailvo.setProm_state(rs.getInt("PROM_STATE"));
					findByMealNoList.add(promdetailvo);
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
		return findByMealNoList;
	
	
}

	@Override
	public List<PromDetailVO> getAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<PromDetailVO> promdetailList = new ArrayList<>();
		
	
			try {
				con=DriverManager.getConnection(url,userid,passwd);
				pstmt = con.prepareStatement(GET_ALL_STMT);
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					PromDetailVO promdetailvo = new PromDetailVO();
					
					promdetailvo.setPromno(rs.getInt("PROMNO"));
					promdetailvo.setMealno(rs.getInt("MEALNO"));
					promdetailvo.setProm_value(rs.getDouble("PROM_VALUE"));
					promdetailvo.setProm_time_start(rs.getDate("RROM_TIME_START"));
					promdetailvo.setProm_time_end(rs.getDate("PROM_TIME_END"));
					promdetailvo.setProm_state(rs.getInt("PROM_STATE"));
					promdetailList.add(promdetailvo);
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
		return promdetailList;
	}
	
	@Override
	public PromDetailVO findByPrimaryKeyLimitOne(Integer Promno) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		PromDetailVO PromDetailvo = null;
		
	
			try {
				con=DriverManager.getConnection(url,userid,passwd);
				pstmt = con.prepareStatement(GET_ONE_STMT_LIMITONE);
				pstmt.setInt(1, Promno);
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					PromDetailvo = new PromDetailVO();
					PromDetailvo.setPromno(Promno);
					PromDetailvo.setMealno(rs.getInt("MEALNO"));
					PromDetailvo.setProm_value(rs.getDouble("PROM_VALUE"));
					PromDetailvo.setProm_time_start(rs.getDate("RROM_TIME_START"));
					PromDetailvo.setProm_time_end(rs.getDate("PROM_TIME_END"));
					PromDetailvo.setProm_state(rs.getInt("PROM_STATE"));
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
		return PromDetailvo;
	}

	//使用main方法測試
		public static void main(String[] args) {


			PromDetailJDBCDAO dao = new PromDetailJDBCDAO();
			
//			===新增資料===
			PromDetailVO promdetailvo1 = new PromDetailVO();
			promdetailvo1.setPromno(35);
			promdetailvo1.setMealno(1);
			promdetailvo1.setProm_value(20.0);
			promdetailvo1.setProm_time_start(java.sql.Date.valueOf("2002-01-01"));
			promdetailvo1.setProm_time_end(java.sql.Date.valueOf("2002-02-01"));
			promdetailvo1.setProm_state(1);
			dao.insert(promdetailvo1);
			
			
//			===修改資料===
//			PromDetailVO promdetailvo1 = new PromDetailVO();
//			promdetailvo1.setPromno(4);
//			promdetailvo1.setMealno(23);
//			promdetailvo1.setProm_value(-200);
//			promdetailvo1.setProm_time_start(java.sql.Date.valueOf("2002-03-01"));
//			promdetailvo1.setProm_time_end(java.sql.Date.valueOf("2002-06-01"));
//			promdetailvo1.setProm_state(0);
//			dao.update(promdetailvo1);
			
//			===刪除===
//			PromDetailVO promdetailvo3 = new PromDetailVO();
//			promdetailvo3.setPromno(3);
//			promdetailvo3.setMealno(8);
//			dao.delete(promdetailvo3);

			
//			===查詢食物類別資料===
//			List<PromDetailVO> findByMealNoList = dao.findByMealNo(4);
//			for(PromDetailVO promdetailvo3 : findByMealNoList) {	
//			印出查詢結果
//			System.out.println("PROMNO ="+promdetailvo3.getPromno());
//			System.out.println("MEALNO ="+promdetailvo3.getMealno());
//			System.out.println("PROM_VALUE ="+promdetailvo3.getProm_value());
//			System.out.println("RROM_TIME_START ="+promdetailvo3.getProm_time_start());
//			System.out.println("PROM_TIME_END ="+promdetailvo3.getProm_time_end());
//			System.out.println("PROM_STATE ="+promdetailvo3.getProm_state());
//			System.out.println("=========");
//			}
			
//			===查詢促銷資料==
//			List<PromDetailVO> findByPrimaryKeyList = dao.findByPrimaryKey(1);
//			Date s ;
//			for(PromDetailVO promdetailvo3: findByPrimaryKeyList) {	
////			查詢資料分筆列式
//				System.out.println("PROMNO ="+promdetailvo3.getPromno());
//				System.out.println("MEALNO ="+promdetailvo3.getMealno());
//				System.out.println("PROM_VALUE ="+promdetailvo3.getProm_value());
//				System.out.println("RROM_TIME_START ="+promdetailvo3.getProm_time_start());
//				System.out.println("PROM_TIME_END ="+promdetailvo3.getProm_time_end());
//				System.out.println("PROM_STATE ="+promdetailvo3.getProm_state());	
//				System.out.println("=========");
//							}
			
//			PromDetailVO findByPrimaryKeyLimitOne = dao.findByPrimaryKeyLimitOne(1);
//			System.out.println("PROMNO ="+Promno());
//			System.out.println("MEALNO ="+findByPrimaryKeyLimitOne.getMealno());
//			System.out.println("PROM_VALUE ="+findByPrimaryKeyLimitOne.getProm_value());
//			System.out.println("RROM_TIME_START ="+findByPrimaryKeyLimitOne.getProm_time_start());
//			System.out.println("PROM_TIME_END ="+findByPrimaryKeyLimitOne.getProm_time_end());
//			System.out.println("PROM_STATE ="+findByPrimaryKeyLimitOne.getProm_state());	
//			System.out.println("=========");

			
//			===查詢整筆資料===
//			List<PromDetailVO> promdetailList = dao.getAll();
//			for(PromDetailVO promdetailvo : promdetailList) {
			
//			1、查詢資料以字串列式(因MealVO有寫toString方法)
//				System.out.println(mealvo);
			
//			2、查詢資料分筆列式
//				System.out.println("PROMNO ="+promdetailvo.getPromno());
//				System.out.println("MEALNO ="+promdetailvo.getMealno());
//				System.out.println("PROM_VALUE ="+promdetailvo.getProm_value());
//				System.out.println("RROM_TIME_START ="+promdetailvo.getProm_time_start());
//				System.out.println("PROM_TIME_END ="+promdetailvo.getProm_time_end());
//				System.out.println("PROM_STATE ="+promdetailvo.getProm_state());
//				System.out.println("=========");
//			}
//			
//			3、查詢資料以字串列式(偷懶法)
//			mealvoList.forEach(System.out::println);
		}	
}
	
	
	

