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
	
	//�ŧi�s�u�һݱ`��
		public static final String driver = "com.mysql.cj.jdbc.Driver";
		public static final String url = "jdbc:mysql://localhost:3306/CFA103_G4?serverTimezone=Asia/Taipei";
		public static final String userid = "David";
		public static final String passwd = "123456";
		//�]�w�W�R��d��mysql���O
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
				// empVO �]�٬� Domain objects
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
	
	//�ϥ�main��k����
//			public static void main(String[] args) {


//				FeedbackJDBCDAO dao = new FeedbackJDBCDAO();
				
//				===�s�W���===
//				FeedbackVO Feedbackvo1 = new FeedbackVO();
//				Feedbackvo1.setFeedback_phone("0987654321");
//				Feedbackvo1.setFeedback_content("�����K�O�n�n��");
//				Feedbackvo1.setFeedback_date(java.sql.Date.valueOf("2002-06-01"));
//				dao.insert(Feedbackvo1);
				
				
				
//				===�d�߳浧���===
//				FeedbackVO Feedbackvo1 = dao.findByPrimaryKey(1);
//				�L�X�d�ߵ��G
//				System.out.println("FEEDBACK_NO ="+Feedbackvo1.getFeedback_no());
//				System.out.println("FEEDBACK_PHONE ="+Feedbackvo1.getFeedback_phone());
//				System.out.println("FEEDBACK_CONTENT ="+Feedbackvo1.getFeedback_content());
//				System.out.println("FEEDBACK_DATE ="+Feedbackvo1.getFeedback_date());
//				System.out.println("=========");

				
				
//				===�d�߾㵧���===
//				List<FeedbackVO> FeedbackList = dao.getAll();
//				for(FeedbackVO Feedbackvo1 : FeedbackList) {
				
	
//				�d�߸�Ƥ����C��
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
	

