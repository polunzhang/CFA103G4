package com.cs_rec.model;

import java.io.FileInputStream;
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

public class Cs_RecDAO  implements Cs_RecDAO_interface {
	public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	public static final String URL = "jdbc:mysql://localhost:3306/CFA103_G4?serverTimezone=Asia/Taipei";
	public static final String USER = "root";
	public static final String PASSWORD = "password";
	

	public static final String INSERT_STMT = 
			"INSERT INTO CS_REC (MEMNO,EMPNO,MSG_CONTEXT,MSG_DIRECT,MSG_TIME,MSG_IMAGE) VALUES(?,?,?,?,?,?)";
	private static final String UPDATE = 
			"UPDATE CS_REC set MEMNO=?,EMPNO=?,MSG_CONTEXT=?,MSG_DIRECT=?,MSG_TIME=?,MSG_IMAGE=? WHERE CSNO=?";
	private static final String DELETE =
			"DELETE FROM CS_REC WHERE CSNO=?";
	private static final String GET_ONE_STMT=
			"SELECT * FROM CS_REC WHERE CSNO=?";
	private static final String GET_ALL_STMT=
			"SELECT * FROM CS_REC ORDER BY CSNO";
	
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CFA103_G4");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}


	public void insert(Cs_RecVO cs_recVO)  {
		Connection con=null;
		PreparedStatement pstmt=null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			
			
			pstmt.setInt(1, cs_recVO.getMemno());
			pstmt.setInt(2, cs_recVO.getEmpno());
			pstmt.setString(3,cs_recVO.getMsg_context() );
			pstmt.setInt(4, cs_recVO.getMsg_direct());
			pstmt.setTimestamp(5,cs_recVO.getMsg_time() );
			pstmt.setBytes(6, cs_recVO.getMsg_image());
			pstmt.executeUpdate();
			
		} catch (SQLException se) {
			se.printStackTrace();
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
	

	@Override
	public void update(Cs_RecVO cs_recVO) {
		Connection con=null;
		PreparedStatement pstmt = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			 
			pstmt.setInt(1, cs_recVO.getMemno());
			pstmt.setInt(2, cs_recVO.getEmpno());
			pstmt.setString(3,cs_recVO.getMsg_context() );
			pstmt.setInt(4, cs_recVO.getMsg_direct());
			pstmt.setTimestamp(5,cs_recVO.getMsg_time() );
			pstmt.setBytes(6, cs_recVO.getMsg_image());
			pstmt.setInt(7, cs_recVO.getCsno());
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
	public void delete(Integer csno) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, csno);

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
	public Cs_RecVO findByPrimaryKey(Integer csno) {
		Cs_RecVO cs_recVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, csno);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				cs_recVO = new Cs_RecVO();
				cs_recVO.setCsno(rs.getInt("CSNO"));
				cs_recVO.setMemno(rs.getInt("MEMNO"));
				cs_recVO.setEmpno(rs.getInt("EMPNO"));
				cs_recVO.setMsg_context(rs.getString("MSG_CONTEXT"));
				cs_recVO.setMsg_direct(rs.getInt("MSG_DIRECT"));
				cs_recVO.setMsg_time(rs.getTimestamp("MSG_TIME"));
				cs_recVO.setMsg_image(rs.getBytes("MSG_IMAGE"));
			
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
		return cs_recVO;
	}

	@Override
	public List<Cs_RecVO> getAll() {
		List<Cs_RecVO> list = new ArrayList<Cs_RecVO>();
		Cs_RecVO cs_recVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				cs_recVO = new Cs_RecVO();
				cs_recVO.setCsno(rs.getInt("CSNO"));
				cs_recVO.setMemno(rs.getInt("MEMNO"));
				cs_recVO.setEmpno(rs.getInt("EMPNO"));
				cs_recVO.setMsg_context(rs.getString("MSG_CONTEXT"));
				cs_recVO.setMsg_direct(rs.getInt("MSG_DIRECT"));
				cs_recVO.setMsg_time(rs.getTimestamp("MSG_TIME"));
				cs_recVO.setMsg_image(rs.getBytes("MSG_IMAGE"));
				
				list.add(cs_recVO); // Store the row in the list
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
	public static byte[] getPictureByteArray(String path) throws IOException {
		FileInputStream fis = new FileInputStream(path);
		byte[] buffer = new byte[fis.available()];
		fis.read(buffer);
		fis.close();
		return buffer;
	}
	
	
}
