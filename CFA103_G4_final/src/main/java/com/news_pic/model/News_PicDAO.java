package com.news_pic.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.news.model.NewsDAO_interface;
import com.news.model.NewsVO;

public class News_PicDAO implements News_PicDAO_interface {

	public static final String INSERT_STMT = "INSERT INTO NEWS_PIC (NEWSNO,NEWS_IMAGE) VALUES(?,?)";
	private static final String UPDATE = "UPDATE NEWS_PIC set NEWSNO=?,NEWS_IMAGE=? WHERE NEWS_PIC_NO=?";
	private static final String DELETE = "DELETE FROM NEWS_PIC WHERE NEWS_PIC_NO=?";
	private static final String GET_ONE_STMT = "SELECT * FROM NEWS_PIC WHERE NEWS_PIC_NO=? AND NEWSNO=?";
	private static final String GET_ALL_STMT = "SELECT * FROM NEWS_PIC ORDER BY NEWS_PIC_NO";
	private static final String GET_NEWSNO_PIC_STMT = "SELECT * FROM NEWS_PIC WHERE NEWSNO=?";
	private static final String UPDATE_BY_NEWSNO = "UPDATE NEWS_PIC SET NEWS_IMAGE=? where NEWSNO = ?";

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CFA103_G4");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	public void insert(News_PicVO news_picVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
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

	public void update(News_PicVO news_picVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {

			con = ds.getConnection();
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

			con = ds.getConnection();
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
	public News_PicVO findByPrimaryKey(Integer news_pic_no, Integer newsno) {
		News_PicVO news_picVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
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

			con = ds.getConnection();
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
			con = ds.getConnection();
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

	@Override
	public void insert2(News_PicVO news_picVO, Connection con) {
		PreparedStatement pstmt = null;

		try {
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, news_picVO.getNewsno());
			pstmt.setBytes(2, news_picVO.getNews_image());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void update2(News_PicVO news_picVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_BY_NEWSNO);
			pstmt.setBytes(1, news_picVO.getNews_image());
			pstmt.setInt(2, news_picVO.getNewsno());
//pstmt.executeUpdate("set auto_increment_offset=10;");
//pstmt.executeUpdate("set auto_increment_increment=10;");
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
