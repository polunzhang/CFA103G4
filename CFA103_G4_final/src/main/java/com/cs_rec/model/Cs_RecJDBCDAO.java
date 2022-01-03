package com.cs_rec.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.DriverManager;

public class Cs_RecJDBCDAO implements Cs_RecDAO_interface {

	public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	public static final String URL = "jdbc:mysql://localhost:3306/CFA103_G4?serverTimezone=Asia/Taipei";
	public static final String USER = "root";
	public static final String PASSWORD = "123456";

	public static final String INSERT_STMT = "INSERT INTO CS_REC(MEMNO, EMPNO, MSG_CONTEXT, MSG_DIRECT, MSG_TIME, MSG_IMAGE) values(?,?,?,?,?,?)";
	public static final String UPDATE_STMT = "UPDATE CS_REC SET MEMNO=?, EMPNO=?, MSG_CONTEXT=?, MSG_DIRECT=?, MSG_TIME=?, MSG_IMAGE=? where CSNO=?";
	public static final String DELETE_STMT = "DELETE FROM CS_REC where CSNO=?";
	public static final String GETONE = "SELECT * FROM CS_REC where CSNO=?";
	public static final String GETALL = "SELECT * FROM CS_REC ORDER BY CSNO";
	
	static {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void insert(Cs_RecVO Cs_RecVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, Cs_RecVO.getMemno());
			pstmt.setInt(2, Cs_RecVO.getEmpno());
			pstmt.setString(3, Cs_RecVO.getMsg_context());
			pstmt.setInt(4, Cs_RecVO.getMsg_direct());
			pstmt.setTimestamp(5, Cs_RecVO.getMsg_time());
			pstmt.setBytes(6, Cs_RecVO.getMsg_image());
			pstmt.executeUpdate();

		} catch (SQLException se) {
			se.printStackTrace();
		}

		finally {
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

	@Override
	public void update(Cs_RecVO Cs_RecVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(UPDATE_STMT);

			pstmt.setInt(1, Cs_RecVO.getMemno());
			pstmt.setInt(2, Cs_RecVO.getEmpno());
			pstmt.setString(3, Cs_RecVO.getMsg_context());
			pstmt.setInt(4, Cs_RecVO.getMsg_direct());
			pstmt.setTimestamp(5, Cs_RecVO.getMsg_time());
			pstmt.setBytes(6, Cs_RecVO.getMsg_image());
			pstmt.setInt(7, Cs_RecVO.getCsno());
			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		}

		finally {
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

	@Override
	public void delete(Integer csno) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(DELETE_STMT);
			pstmt.setInt(1, csno);
			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		}

		finally {
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

	@Override
	public Cs_RecVO findByPrimaryKey(Integer csno) {

		Cs_RecVO Cs_RecVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(GETONE);

			pstmt.setInt(1, csno);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				Cs_RecVO = new Cs_RecVO();
				Cs_RecVO.setCsno(rs.getInt("CSNO"));
				Cs_RecVO.setEmpno(rs.getInt("EMPNO"));
				Cs_RecVO.setMemno(rs.getInt("MEMNO"));
				Cs_RecVO.setMsg_context(rs.getString("MSG_CONTEXT"));
				Cs_RecVO.setMsg_direct(rs.getInt("MSG_DIRECT"));
				Cs_RecVO.setMsg_image(rs.getBytes("MSG_IMAGE"));
				Cs_RecVO.setMsg_time(rs.getTimestamp("MSG_TIME"));
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
		return Cs_RecVO;
	}

	@Override
	public List<Cs_RecVO> getAll() {
		
		List<Cs_RecVO> D = new ArrayList<Cs_RecVO>();
		Cs_RecVO Cs_RecVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(GETALL);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				Cs_RecVO = new Cs_RecVO();
				Cs_RecVO.setCsno(rs.getInt("CSNO"));
				Cs_RecVO.setEmpno(rs.getInt("EMPNO"));
				Cs_RecVO.setMemno(rs.getInt("MEMNO"));
				Cs_RecVO.setMsg_context(rs.getString("MSG_CONTEXT"));
				Cs_RecVO.setMsg_direct(rs.getInt("MSG_DIRECT"));
				Cs_RecVO.setMsg_image(rs.getBytes("MSG_IMAGE"));
				Cs_RecVO.setMsg_time(rs.getTimestamp("MSG_TIME"));
				
				D.add(Cs_RecVO);
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
		return D;
	}

	public static byte[] getPictureByteArray(String path) throws IOException {
		FileInputStream fis = new FileInputStream(path);
		byte[] buffer = new byte[fis.available()];
		fis.read(buffer);
		fis.close();
		return buffer;
	}

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		Cs_RecDAO_interface dao = new Cs_RecJDBCDAO();
//		//新增
//		Cs_RecVO A = new Cs_RecVO();
//		A.setMemno(1);
//		A.setEmpno(2);
//		A.setCsno(3);
//		A.setMsg_context("1111");
//		A.setMsg_direct(4);
//		A.setMsg_time(java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
//		byte[] pic = getPictureByteArray("items/FC_Barcelona.png");
//		A.setMsg_image(pic);

//		dao.insert(A);
//		System.out.println("新增成功");
//	}

//		// 修改
//		Cs_RecVO B = new Cs_RecVO();
//		B.setCsno(1);
//		B.setMemno(1);
//		B.setEmpno(2);
//		B.setMsg_context("1111");
//		B.setMsg_direct(4);
//		B.setMsg_time(java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
//		byte[] pic = getPictureByteArray("items/FC_Barcelona.png");
//		B.setMsg_image(pic);
//
//		dao.update(B);
//		System.out.println("修改成功");
//
//		//刪除
//		dao.delete(12);
//		System.out.println("刪除完成");

//		//單一查詢
//		Cs_RecVO C = dao.findByPrimaryKey(2);
//		System.out.println(C.getEmpno());
		
		//查詢多筆
//		List<Cs_RecVO> list = dao.getAll();
//		for(Cs_RecVO E:list) {
//			System.out.println(E.getMsg_context());
//		}
	
	}
}

	
