package com.emper.model;

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

public class EmperJNDIDAO implements EmperDAO_interface{

	
	// �@�����ε{����,�w��@�Ӹ�Ʈw ,�@�Τ@��DataSource�Y�i
			private static DataSource ds = null;
			static {
				try {
					Context ctx = new InitialContext();
					ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB2");
				} catch (NamingException e) {
					e.printStackTrace();
				}
			}
	
	private static final String INSERT_STMT = "INSERT INTO `cfa103_g4`.`emper` (`EMPER_NAME`) VALUES (?)";
	private static final String GET_ALL_STMT = "SELECT * FROM `cfa103_g4`.`emper` order by emperno";
	private static final String GET_ONE_STMT = "SELECT * FROM `cfa103_g4`.`emper` where emperno = ?";
	private static final String DELETE_DETAIL = "DELETE FROM `cfa103_g4`.`emper_detail` where emperno = ?";
	private static final String DELETE_EMPER = "DELETE FROM `cfa103_g4`.`emper` where emperno = ?";
	private static final String UPDATE = "UPDATE `cfa103_g4`.`emper` set emper_name=? where emperno=?";

	@Override
	public void insert(EmperVO emperVO) {
	
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
	
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
	
			pstmt.setString(1, emperVO.getEmper_name());
	
			pstmt.executeUpdate();
			
			// Handle any SQL errors
		}catch (SQLException se) {
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
	public void update(EmperVO emperVO) {
	
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
	
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
	
			pstmt.setString(1, emperVO.getEmper_name());
			pstmt.setInt(2, emperVO.getEmperno());
	
			pstmt.executeUpdate();
	
			// Handle any SQL errors
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
	public void delete(Integer emperno) {
		int updateCount_DETAILs = 0;
	
		Connection con = null;
		PreparedStatement pstmt = null;
	
		try {
	
			
			con = ds.getConnection();
	
			// 1���]�w�� pstm.executeUpdate()���e
			con.setAutoCommit(false);
			// ���R������
			pstmt = con.prepareStatement(DELETE_DETAIL);
			pstmt.setInt(1, emperno);
			updateCount_DETAILs = pstmt.executeUpdate();
			// �A�R���v��
			pstmt = con.prepareStatement(DELETE_EMPER);
			pstmt.setInt(1, emperno);
			pstmt.executeUpdate();
	
			// 2���]�w�� pstm.executeUpdate()����
			con.commit();
			con.setAutoCommit(true);
			System.out.println("�R���v��" + emperno + "��,�@��" + updateCount_DETAILs + "�����ӦP�ɳQ�R��");
	
			
			// Handle any SQL errors
		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3���]�w���exception�o�ͮɤ�catch�϶���
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. " + excep.getMessage());
				}
			}
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
	public EmperVO findByPrimaryKey(Integer emperno) {
	
		EmperVO emperVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
		try {
	

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
	
			pstmt.setInt(1, emperno);
	
			rs = pstmt.executeQuery();
	
			while (rs.next()) {
				// emperVo �]�٬� Domain objects
				emperVO = new EmperVO();
				emperVO.setEmperno(rs.getInt("emperno"));
				emperVO.setEmper_name(rs.getString("emper_name"));
	
			}
	
			
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
		return emperVO;
	}

	@Override
	public List<EmperVO> getAll() {
		List<EmperVO> list = new ArrayList<EmperVO>();
		EmperVO emperVO = null;
	
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
		try {
	

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
	
			while (rs.next()) {
				// empVO �]�٬� Domain objects
				emperVO = new EmperVO();
				emperVO.setEmperno(rs.getInt("emperno"));
				emperVO.setEmper_name(rs.getString("emper_name"));
				list.add(emperVO); // Store the row in the list
			}
	
			
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
		return list;
	}

}
