package com.news_pic.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.DriverManager;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.news.model.NewsDAO_interface;
import com.news.model.NewsVO;

public class News_PICJDBCDAO implements News_PicDAO_interface {

	public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	public static final String URL = "jdbc:mysql://localhost:3306/CFA103_G4?serverTimezone=Asia/Taipei";
	public static final String USER = "root";
	public static final String PASSWORD = "password";

	public static final String INSERT_STMT = "INSERT INTO NEWS_PIC (NEWSNO,NEWS_IMAGE) VALUES(?,?)";
	private static final String UPDATE = "UPDATE NEWS_PIC set NEWSNO=?,NEWS_IMAGE=? WHERE NEWS_PIC_NO=?";
	private static final String DELETE = "DELETE FROM NEWS_PIC WHERE NEWS_PIC_NO=?";
	private static final String GET_ONE_STMT = "SELECT * FROM NEWS_PIC WHERE NEWS_PIC_NO=? AND NEWSNO=?";
	private static final String GET_ALL_STMT = "SELECT * FROM NEWS_PIC ORDER BY NEWS_PIC_NO";
	private static final String GET_NEWSNO_PIC_STMT = "SELECT * FROM NEWS_PIC WHERE NEWSNO=?";

	static {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		}
	}

	public void insert(News_PicVO news_picVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, news_picVO.getNewsno());
			pstmt.setBytes(2, news_picVO.getNews_image());

			pstmt.executeUpdate();

		} catch (SQLException se) {
//	se.printStackTrace();
			throw new RuntimeException("A database error occured. " + se.getMessage());
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

	public void update(News_PicVO news_picVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setInt(1, news_picVO.getNewsno());
			pstmt.setBytes(2, news_picVO.getNews_image());
			pstmt.setInt(3, news_picVO.getNews_pic_no());

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
	public void delete(Integer news_pic_no) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, news_pic_no);

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
	public News_PicVO findByPrimaryKey(Integer news_pic_no,Integer newsno) {
		News_PicVO news_picVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, news_pic_no);
			pstmt.setInt(2, newsno);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				news_picVO = new News_PicVO();
				news_picVO.setNews_pic_no(rs.getInt("NEWS_PIC_NO"));
				news_picVO.setNewsno(rs.getInt("NEWSNO"));
				news_picVO.setNews_image(rs.getBytes("NEWS_IMAGE"));
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
		return news_picVO;
	}

	@Override
	public List<News_PicVO> getAll() {
		List<News_PicVO> list = new ArrayList<News_PicVO>();
		News_PicVO news_picVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				news_picVO = new News_PicVO();
				news_picVO.setNews_pic_no(rs.getInt("NEWS_PIC_NO"));
				news_picVO.setNewsno(rs.getInt("NEWSNO"));
				news_picVO.setNews_image(rs.getBytes("NEWS_IMAGE"));

				list.add(news_picVO); // Store the row in the list
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

	public List<News_PicVO> getNewsnoPic(Integer newsno) {

		List<News_PicVO> list = new ArrayList<News_PicVO>();
		News_PicVO news_picVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(GET_NEWSNO_PIC_STMT);
			
			pstmt.setInt(1, newsno);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				news_picVO = new News_PicVO();
				news_picVO.setNews_pic_no(rs.getInt("NEWS_PIC_NO"));
				news_picVO.setNewsno(rs.getInt("NEWSNO"));
				news_picVO.setNews_image(rs.getBytes("NEWS_IMAGE"));

				list.add(news_picVO); // Store the row in the list
			}

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

	public static void main(String[] args) throws IOException {
		News_PicDAO_interface dao = new News_PICJDBCDAO();

//		News_PicVO news_picVO = new News_PicVO();
//
//		news_picVO.setNewsno(1);
//		byte[] pic = getPictureByteArray(
//				"C:\\CFA103_WebApp\\eclipse_WTP_workspace1\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\WebApp_ch04_FileUpload\\items\\2.png");
//		news_picVO.setNews_image(pic);
//		dao.insert(news_picVO);
//		System.out.print("新增成功");

//		News_PicVO news_picVO=new News_PicVO();
//		news_picVO.setNews_pic_no(3);
//		news_picVO.setNewsno(2);
//		dao.update(news_picVO);

//		dao.delete(4);

//		News_PicVO test3=dao.findByPrimaryKey(11,1);
//		System.out.println(test3.getNewsno());

//		List<News_PicVO> list=dao.getAll();
//		for(News_PicVO A:list) {
//			System.out.println(A.getNews_pic_no());
//			
//		}
		
		List<News_PicVO> list=dao.getNewsnoPic(1);
		for(News_PicVO A:list) {
			System.out.println(A.getNews_pic_no());
			
		}
	}

	@Override
	public void insert2(News_PicVO news_picVO, Connection con) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update2(News_PicVO news_picVO) {
		// TODO Auto-generated method stub
		
	}



}
