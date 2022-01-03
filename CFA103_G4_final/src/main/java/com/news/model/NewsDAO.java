package com.news.model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.news_pic.model.News_PicDAO;
import com.news_pic.model.News_PicVO;

public class NewsDAO  implements NewsDAO_interface {
	public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	public static final String URL = "jdbc:mysql://localhost:3306/CFA103_G4?serverTimezone=Asia/Taipei";
	public static final String USER = "root";
	public static final String PASSWORD = "password";
	

	public static final String INSERT_STMT = 
			"INSERT INTO NEWS (EMPNO,NEWS_TITLE,NEWS_CONTENT,NEWS_TIME) VALUES(?,?,?,?)";
	private static final String UPDATE = 
			"UPDATE NEWS set EMPNO=?,NEWS_TITLE=?,NEWS_CONTENT=?,NEWS_TIME=? WHERE NEWSNO=?";
	private static final String DELETE =
			"DELETE FROM NEWS WHERE NEWSNO=?";
	private static final String DELETEPic =
			"DELETE FROM NEWS_PIC WHERE NEWSNO=?";
	private static final String GET_ONE_STMT=
			"SELECT * FROM NEWS WHERE NEWSNO=?";
	private static final String GET_ALL_STMT=
			"SELECT * FROM NEWS ORDER BY NEWS_TIME DESC";
	private static final String Get_SAMEDAY_STMT=
			"SELECT * FROM NEWS WHERE NEWS_TIME=? ORDER BY NEWSNO;";
	
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CFA103_G4");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}


	public void insert(NewsVO newsVO)  {
		Connection con=null;
		PreparedStatement pstmt=null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setInt(1,newsVO.getEmpno());
			pstmt.setString(2,newsVO.getNews_title());
			pstmt.setString(3,newsVO.getNews_content());
			pstmt.setDate(4, newsVO.getNews_time());
			
			pstmt.executeUpdate();
			
		} catch (SQLException se) {
//	se.printStackTrace();
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}

			if (con != null) {
				try {
					con.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
		}
	}
	public static byte[] getPictureByteArray(String path) throws IOException {
		FileInputStream fis = new FileInputStream(path);
		byte[] buffer = new byte[fis.available()];
		fis.read(buffer);
		fis.close();
		return buffer;
	}
	public static String getLongString(String path) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(path));
		StringBuilder sb = new StringBuilder(); // StringBuffer is thread-safe!
		String str;
		while ((str = br.readLine()) != null) {
			sb.append(str);
			sb.append("\n");
		}
		br.close();

		return sb.toString();
	}
	@Override
	public void update(NewsVO newsVO) {
		Connection con=null;
		PreparedStatement pstmt = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			 
			pstmt.setInt(1,newsVO.getEmpno());
			pstmt.setString(2,newsVO.getNews_title());
			pstmt.setString(3,newsVO.getNews_content());
			pstmt.setDate(4, newsVO.getNews_time());
			pstmt.setInt(5,newsVO.getNewsno());
			pstmt.executeUpdate();

			// Handle any driver errors
		}  catch (SQLException se) {
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
	public void delete(Integer newsno) {
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, newsno);
					
			pstmt2 = con.prepareStatement(DELETEPic);

			pstmt2.setInt(1, newsno);
			
			pstmt2.executeUpdate();
			
			pstmt.executeUpdate();

			// Handle any driver errors
		}  catch (SQLException se) {
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
	public NewsVO findByPrimaryKey(Integer newsno) {
		NewsVO newsVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, newsno);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				newsVO = new NewsVO();
				newsVO.setNewsno(rs.getInt("NEWSNO"));
				newsVO.setEmpno(rs.getInt("EMPNO"));
				newsVO.setNews_title(rs.getString("NEWS_TITLE"));
				newsVO.setNews_content(rs.getString("NEWS_CONTENT"));
				newsVO.setNews_time(rs.getDate("NEWS_TIME"));
			}

			// Handle any driver errors
		}  catch (SQLException se) {
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
		return newsVO;
	}

	@Override
	public List<NewsVO> getAll() {
		List<NewsVO> list = new ArrayList<NewsVO>();
		NewsVO newsVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				newsVO = new NewsVO();
				newsVO.setNewsno(rs.getInt("NEWSNO"));
				newsVO.setEmpno(rs.getInt("EMPNO"));
				newsVO.setNews_title(rs.getString("NEWS_TITLE"));
				newsVO.setNews_content(rs.getString("NEWS_CONTENT"));
				newsVO.setNews_time(rs.getDate("NEWS_TIME"));
				
				list.add(newsVO); // Store the row in the list
			}

			// Handle any driver errors
		}  catch (SQLException se) {
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
	public List<NewsVO> getSameDay(Date news_time) {
		List<NewsVO> list = new ArrayList<NewsVO>();
		NewsVO newsVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(Get_SAMEDAY_STMT);
			pstmt.setDate(1, news_time);
			rs = pstmt.executeQuery();
			
			
			while (rs.next()) {
				
				newsVO = new NewsVO();
				newsVO.setNewsno(rs.getInt("NEWSNO"));
				newsVO.setEmpno(rs.getInt("EMPNO"));
				newsVO.setNews_title(rs.getString("NEWS_TITLE"));
				newsVO.setNews_content(rs.getString("NEWS_CONTENT"));
				newsVO.setNews_time(rs.getDate("NEWS_TIME"));
	//			newsVO.setNews_image(rs.getBytes("NEWS_IMAGE"));
				
				list.add(newsVO); // Store the row in the list
			}

			// Handle any driver errors
		}  catch (SQLException se) {
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
	public void insertPicWithNewsNo(NewsVO newsVO, byte[] newsPic) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {

			con = ds.getConnection();
			con.setAutoCommit(false);
			
			String cols[] = {"NEWSNO"};
			pstmt = con.prepareStatement(INSERT_STMT,cols);
			
			
			pstmt.setInt(1,newsVO.getEmpno());
			pstmt.setString(2,newsVO.getNews_title());
			pstmt.setString(3,newsVO.getNews_content());
			pstmt.setDate(4, newsVO.getNews_time());
			
			pstmt.executeUpdate();
			
			String next_newsno = null;
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				next_newsno = rs.getString(1);
				System.out.println("自增主鍵值= " + next_newsno +"(剛新增成功的消息編號)");
			} else {
				System.out.println("未取得自增主鍵值");
			}
			rs.close();
			
			News_PicDAO dao=new News_PicDAO();
			News_PicVO news_picVO=new News_PicVO();
			
			news_picVO.setNewsno(new Integer(next_newsno));
			news_picVO.setNews_image(newsPic);
			
			dao.insert2(news_picVO,con);
			
			con.commit();
			con.setAutoCommit(true);
			
			}catch (SQLException se) {
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
	@Override
	public void updatePicWithNewsNo(NewsVO newsVO, byte[] newsPic) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {

			con = ds.getConnection();
			con.setAutoCommit(false);
			
			String cols[] = {"NEWSNO"};
			pstmt = con.prepareStatement(UPDATE,cols);
			
			pstmt.setInt(1,newsVO.getEmpno());
			pstmt.setString(2,newsVO.getNews_title());
			pstmt.setString(3,newsVO.getNews_content());
			pstmt.setDate(4, newsVO.getNews_time());
			pstmt.setInt(5,newsVO.getNewsno());
			pstmt.executeUpdate();
			
			
			String next_newsno = null;
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				next_newsno = rs.getString(1);
				System.out.println("自增主鍵值= " + next_newsno +"(剛新增成功的消息編號)");
			} else {
				System.out.println("未取得自增主鍵值");
			}
			rs.close();
			
			News_PicDAO dao=new News_PicDAO();
			News_PicVO news_picVO=new News_PicVO();
			
			news_picVO.setNewsno(new Integer(next_newsno));
			news_picVO.setNews_image(newsPic);
			
			dao.update2(news_picVO);
			
			con.commit();
			con.setAutoCommit(true);
			
			
			
			}catch (SQLException se) {
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

}
