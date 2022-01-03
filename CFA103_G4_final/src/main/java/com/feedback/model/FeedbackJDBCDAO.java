package com.feedback.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.meal_pic.model.MealPicVO;
import com.prom.model.PromJDBCDAO;
import com.prom.model.PromVO;

public class FeedbackJDBCDAO implements FeedbackDAO_interface {
	
	//宣告連線所需常數
		public static final String driver = "com.mysql.cj.jdbc.Driver";
		public static final String url = "jdbc:mysql://localhost:3306/CFA103_G4?serverTimezone=Asia/Taipei";
		public static final String userid = "David";
		public static final String passwd = "123456";
		//設定增刪改查之mysql指令
		private static final String INSERT_STMT = "INSERT INTO `CFA103_G4`.`FEEDBACK` (`FEEDBACK_PHONE`, `FEEDBACK_CONTENT`, `FEEDBACK_DATE`) VALUES ( ?, ?, ?);";	
		private static final String GET_ONE_STMT = "SELECT * FROM FEEDBACK where FEEDBACK_NO = ?";
		private static final String GET_ALL_STMT = "SELECT * FROM FEEDBACK order by FEEDBACK_NO";
		
		
		static {
			try {
				Class.forName(driver);
			}  catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

	@Override
	public void insert(FeedbackVO feedbackvo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		

		try {
			con=DriverManager.getConnection(url,userid,passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1, feedbackvo.getFeedback_phone());
			pstmt.setString(2, feedbackvo.getFeedback_content());
			pstmt.setDate(3, feedbackvo.getFeedback_date());
			
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
	public FeedbackVO findByPrimaryKey(Integer feedbackno) {
		
		FeedbackVO Feedbackvo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, feedbackno);
			rs = pstmt.executeQuery();

			while (rs.next()) {
	
				Feedbackvo = new FeedbackVO();
				Feedbackvo.setFeedback_no(feedbackno);
				Feedbackvo.setFeedback_phone(rs.getString("FEEDBACK_PHONE"));
				Feedbackvo.setFeedback_content(rs.getString("FEEDBACK_CONTENT"));
				Feedbackvo.setFeedback_date(rs.getDate("FEEDBACK_DATE"));
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
		return Feedbackvo;
	}

	@Override
	public List<FeedbackVO> getAll() {
		List<FeedbackVO> list = new ArrayList<FeedbackVO>();
		FeedbackVO feedbackVO = null;

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
				feedbackVO = new FeedbackVO();
				feedbackVO.setFeedback_no(rs.getInt("FEEDBACK_NO"));
				feedbackVO.setFeedback_phone(rs.getString("FEEDBACK_PHONE"));
				feedbackVO.setFeedback_content(rs.getString("FEEDBACK_CONTENT"));
				feedbackVO.setFeedback_date(rs.getDate("FEEDBACK_DATE"));
				list.add(feedbackVO); 
			}


		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	
	//使用main方法測試
//			public static void main(String[] args) {


//				FeedbackJDBCDAO dao = new FeedbackJDBCDAO();
				
//				===新增資料===
//				FeedbackVO Feedbackvo1 = new FeedbackVO();
//				Feedbackvo1.setFeedback_phone("0987654321");
//				Feedbackvo1.setFeedback_content("全部免費好爽喔");
//				Feedbackvo1.setFeedback_date(java.sql.Date.valueOf("2002-06-01"));
//				dao.insert(Feedbackvo1);
				
				
				
//				===查詢單筆資料===
//				FeedbackVO Feedbackvo1 = dao.findByPrimaryKey(1);
//				印出查詢結果
//				System.out.println("FEEDBACK_NO ="+Feedbackvo1.getFeedback_no());
//				System.out.println("FEEDBACK_PHONE ="+Feedbackvo1.getFeedback_phone());
//				System.out.println("FEEDBACK_CONTENT ="+Feedbackvo1.getFeedback_content());
//				System.out.println("FEEDBACK_DATE ="+Feedbackvo1.getFeedback_date());
//				System.out.println("=========");

				
				
//				===查詢整筆資料===
//				List<FeedbackVO> FeedbackList = dao.getAll();
//				for(FeedbackVO Feedbackvo1 : FeedbackList) {
				
	
//				查詢資料分筆列式
//					System.out.println("FEEDBACK_NO ="+Feedbackvo1.getFeedback_no());
//					System.out.println("FEEDBACK_PHONE ="+Feedbackvo1.getFeedback_phone());
//					System.out.println("FEEDBACK_CONTENT ="+Feedbackvo1.getFeedback_content());
//					System.out.println("FEEDBACK_DATE ="+Feedbackvo1.getFeedback_date());
//					System.out.println("==========");
//				}
//				
//
//			}	
}
	

