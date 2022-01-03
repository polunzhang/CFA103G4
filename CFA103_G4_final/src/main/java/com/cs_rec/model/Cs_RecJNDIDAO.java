package com.cs_rec.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Cs_RecJNDIDAO implements Cs_RecDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public static final String INSERT_STMT = 
			"INSERT INTO CS_REC(MEMNO, EMPNO, MSG_CONTEXT, MSG_DIRECT, MSG_TIME, MSG_IMAGE) values(?,?,?,?,?,?)";
	public static final String UPDATE_STMT = 
			"UPDATE CS_REC SET MEMNO=?, EMPNO=?, MSG_CONTEXT=?, MSG_DIRECT=?, MSG_TIME=?, MSG_IMAGE=? where CSNO=?";
	public static final String DELETE_STMT = "DELETE FROM CS_REC where CSNO=?";
	public static final String GETONE = 
			"SELECT * FROM CS_REC where CSNO=?";
	public static final String GETALL = 
			"SELECT * FROM CS_REC ORDER BY CSNO";
	
	public void insert(Cs_RecVO Cs_RecVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
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
			con = ds.getConnection();
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
			con = ds.getConnection();
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

			con = ds.getConnection();
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

			con = ds.getConnection();
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

}
