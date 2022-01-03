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
	//�ŧi�s�u�һݱ`��
	public static final String driver = "com.mysql.cj.jdbc.Driver";
	public static final String url = "jdbc:mysql://localhost:3306/CFA103_G4?serverTimezone=Asia/Taipei";
	public static final String userid = "David";
	public static final String passwd = "123456";
	//�]�w�W�R��d��mysql���O
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
			
			// 1���]�w�� pstm.executeUpdate()���e
    		con.setAutoCommit(false);
			
    		// ���s�W����
			String cols[] = {"MEALNO"};
			pstmt = con.prepareStatement(INSERT_STMT , cols);			
			pstmt.setInt(1, mealVO.getMeal_type_no());
			pstmt.setInt(2, mealVO.getMeal_price());
			pstmt.setString(3, mealVO.getMeal_name());
			pstmt.setString(4, mealVO.getMeal_intro());
//Statement stmt=	con.createStatement();
//stmt.executeUpdate("set auto_increment_offset=10;");    //�ۼW�D��-��l��
//stmt.executeUpdate("set auto_increment_increment=10;"); //�ۼW�D��-���W
			pstmt.executeUpdate();
			//�����������ۼW�D���
			String next_mealno = null;
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				next_mealno = rs.getString(1);
				System.out.println("�ۼW�D���= " + next_mealno +"(��s�W���\�������s��)");
			} else {
				System.out.println("�����o�ۼW�D���");
			}
			rs.close();
			// �A�P�ɷs�W���u
			MealPicDAOJDBC dao = new MealPicDAOJDBC();
			MealPicVO mealpicvo = new MealPicVO();
//			System.out.println("list.size()-A="+list.size());
//			for (byte MealPicVO : mealPic) {
			mealpicvo.setMealno(new Integer(next_mealno)) ;
			mealpicvo.setMeal_pic(mealPic);
			dao.insert2(mealpicvo,con);
//			}

			// 2���]�w�� pstm.executeUpdate()����
			con.commit();
			con.setAutoCommit(true);
//			System.out.println("list.size()-B="+list.size());
//			System.out.println("�s�W�����s��" + next_mealno + "��,�@�����u" + list.size()
//					+ "�H�P�ɳQ�s�W");
			
			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3���]�w���exception�o�ͮɤ�catch�϶���
					System.err.print("Transaction is being ");
					System.err.println("rolled back-��-dept");
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
			
			//�����s�u��pstmt
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
			
			//�����s�u��pstmt
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
			
			//�����s�u��pstmt
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
			



			// 2���]�w�� pstm.executeUpdate()����
//			con.commit();
//			con.setAutoCommit(true);
		
		} catch (SQLException e) {
			e.printStackTrace();
			
			//�����s�u��pstmt
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
	
	//�ϥ�main��k����
	public static void main(String[] args) {



		MealJDBCDAO dao = new MealJDBCDAO();
		
//		===�s�W���===
//		MealVO mealvo1 = new MealVO();
//		mealvo1.setMeal_type_no(1);
//		mealvo1.setMeal_price(20);
//		mealvo1.setMeal_name("�h���");
//		mealvo1.setMeal_intro("�s�A�{�^��100%�h����");
//		dao.insert(mealvo1);
		
		
//		===�ק���===
//		MealVO mealvo2 = new MealVO();
//		mealvo2.setMealno(24);
//		mealvo2.setMeal_type_no(2);
//		mealvo2.setMeal_price(200);
//		mealvo2.setMeal_name("�h��ĿL��");
//		mealvo2.setMeal_intro("�s�A�{�^��100%�h���Ļs�����L��");
//		dao.update(mealvo2);
		
//		===�R��===
//		dao.delete(24);

		
//		===�d�߳浧���===
		MealVO mealvo3 = dao.findByPrimaryKey(4);
//		�L�X�d�ߵ��G
		System.out.println("MEALNO ="+mealvo3.getMealno());
		System.out.println("MEAL_TYPE_NO ="+mealvo3.getMeal_type_no());
		System.out.println("MEAL_PRICE ="+mealvo3.getMeal_price());
		System.out.println("MEAL_NAME ="+mealvo3.getMeal_name());
		System.out.println("MEAL_INTRO ="+mealvo3.getMeal_intro());
		System.out.println("=========");

//		===�d�߭����W�ٸ��==
//		List<MealVO> findByMealNameList = dao.findByMealName("�~");
//		for(MealVO mealvo4 : findByMealNameList) {	
//		�d�߸�Ƥ����C��
//			System.out.println("MEALNO ="+mealvo4.getMealno());
//			System.out.println("MEAL_TYPE_NO ="+mealvo4.getMeal_type_no());
//			System.out.println("MEAL_PRICE ="+mealvo4.getMeal_price());
//			System.out.println("MEAL_NAME ="+mealvo4.getMeal_name());
//			System.out.println("MEAL_INTRO ="+mealvo4.getMeal_intro());
//			System.out.println("==========");
//		}
		
//		===�d�߭������O���==
//		List<MealVO> findByMealTypeList = dao.findByMealType(2);
//		for(MealVO mealvo4 : findByMealTypeList) {	
//		�d�߸�Ƥ����C��
//			System.out.println("MEALNO ="+mealvo4.getMealno());
//			System.out.println("MEAL_PRICE ="+mealvo4.getMeal_price());
//			System.out.println("MEAL_NAME ="+mealvo4.getMeal_name());
//			System.out.println("MEAL_INTRO ="+mealvo4.getMeal_intro());
//			System.out.println("==========");
//		}
	
		
		
//		===�d�߾㵧���===
//		List<MealVO> mealvoList = dao.getAll();
//		for(MealVO mealvo : mealvoList) {
		
//		1�B�d�߸�ƥH�r��C��(�]MealVO���gtoString��k)
//			System.out.println(mealvo);
		
//		2�B�d�߸�Ƥ����C��
//			System.out.println("MEALNO ="+mealvo.getMealno());
//			System.out.println("MEAL_TYPE_NO ="+mealvo.getMeal_type_no());
//			System.out.println("MEAL_PRICE ="+mealvo.getMeal_price());
//			System.out.println("MEAL_NAME ="+mealvo.getMeal_name());
//			System.out.println("MEAL_INTRO ="+mealvo.getMeal_intro());
//			System.out.println("==========");
//		}
		
//		3�B�d�߸�ƥH�r��C��(���i�k)
//		mealvoList.forEach(System.out::println);
	}
	
}
