package com.member.model;

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

public class MemberDAO implements MemberDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CFA103_G4");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	public static final String INSERT_STMT = "INSERT INTO MEMBER(MNAME, GENDER, BDAY, ADDRESS, PHONE, MACCOUNT, MPASSWORD, MEM_EMAIL, MEM_STATE, MEM_VERIFY, MEM_ID) values(?,?,?,?,?,?,?,?,?,?,?)";
	public static final String UPDATE_STMT = "UPDATE MEMBER SET MNAME=?, GENDER=?, BDAY=?, ADDRESS=?, PHONE=?, MACCOUNT=?, MPASSWORD=?, MEM_EMAIL=?, MEM_STATE=?, MEM_VERIFY=?, MEM_ID=? where MEMNO=?";
	public static final String DELETE_STMT = "DELETE FROM MEMBER where MEMNO=?";
	public static final String GETONE = "SELECT * FROM MEMBER where MEMNO=?";
	public static final String GETALL = "SELECT * FROM MEMBER ORDER BY MEMNO";
	public static final String GETONEBYNAME = "SELECT * FROM MEMBER where MNAME=?";
	public static final String LOGIN = "SELECT * FROM MEMBER where maccount = ?";
	public static final String FORGETPASSWORD = "SELECT * FROM MEMBER where MEM_EMAIL = ?";

	@Override
	public void insert(MemberVO membervo) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, membervo.getMname());
			pstmt.setString(2, membervo.getGender());
			pstmt.setDate(3, membervo.getBday());
			pstmt.setString(4, membervo.getAddress());
			pstmt.setString(5, membervo.getPhone());
			pstmt.setString(6, membervo.getMaccount());
			pstmt.setString(7, membervo.getMpassword());
			pstmt.setString(8, membervo.getMem_email());
			pstmt.setInt(9, membervo.getMem_state());
			pstmt.setInt(10, membervo.getMem_verify());
			pstmt.setString(11, membervo.getMem_id());

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
	public void update(MemberVO membervo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_STMT);

			pstmt.setString(1, membervo.getMname());
			pstmt.setString(2, membervo.getGender());
			pstmt.setDate(3, membervo.getBday());
			pstmt.setString(4, membervo.getAddress());
			pstmt.setString(5, membervo.getPhone());
			pstmt.setString(6, membervo.getMaccount());
			pstmt.setString(7, membervo.getMpassword());
			pstmt.setString(8, membervo.getMem_email());
			pstmt.setInt(9, membervo.getMem_state());
			pstmt.setInt(10, membervo.getMem_verify());
			pstmt.setString(11, membervo.getMem_id());
			pstmt.setInt(12, membervo.getMemno());

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
	public void delete(Integer memno) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_STMT);
			pstmt.setInt(1, memno);
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
	public MemberVO findByPrimaryKey(Integer memno) {

		MemberVO membervo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GETONE);

			pstmt.setInt(1, memno);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				membervo = new MemberVO();
				membervo.setMemno(rs.getInt("MEMNO"));
				membervo.setMname(rs.getString("" + "MNAME"));
				membervo.setGender(rs.getString("GENDER"));
				membervo.setBday(rs.getDate("BDAY"));
				membervo.setAddress(rs.getString("ADDRESS"));
				membervo.setPhone(rs.getString("Phone"));
				membervo.setMaccount(rs.getString("MACCOUNT"));
				membervo.setMpassword(rs.getString("MPASSWORD"));
				membervo.setMem_email(rs.getString("MEM_EMAIL"));
				membervo.setMem_state(rs.getInt("MEM_STATE"));
				membervo.setMem_verify(rs.getInt("MEM_VERIFY"));
				membervo.setMem_id(rs.getString("MEM_ID"));
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
		return membervo;
	}


	@Override
	public MemberVO login(String maccount) {

		MemberVO membervo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(LOGIN);

			pstmt.setString(1, maccount);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				membervo = new MemberVO();
				membervo.setMemno(rs.getInt("MEMNO"));
				membervo.setMname(rs.getString("MNAME"));
				membervo.setGender(rs.getString("GENDER"));
				membervo.setBday(rs.getDate("BDAY"));
				membervo.setAddress(rs.getString("ADDRESS"));
				membervo.setPhone(rs.getString("Phone"));
				membervo.setMaccount(rs.getString("MACCOUNT"));
				membervo.setMpassword(rs.getString("MPASSWORD"));
				membervo.setMem_email(rs.getString("MEM_EMAIL"));
				membervo.setMem_state(rs.getInt("MEM_STATE"));
				membervo.setMem_verify(rs.getInt("MEM_VERIFY"));
				membervo.setMem_id(rs.getString("MEM_ID"));

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
		return membervo;
	}

	@Override
	public MemberVO findByName(String mname) {

		MemberVO membervo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GETONEBYNAME);

			pstmt.setString(1, mname);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				membervo = new MemberVO();
				membervo.setMemno(rs.getInt("MEMNO"));
				membervo.setMname(rs.getString("MNAME"));
				membervo.setGender(rs.getString("GENDER"));
				membervo.setBday(rs.getDate("BDAY"));
				membervo.setAddress(rs.getString("ADDRESS"));
				membervo.setPhone(rs.getString("Phone"));
				membervo.setMaccount(rs.getString("MACCOUNT"));
				membervo.setMpassword(rs.getString("MPASSWORD"));
				membervo.setMem_email(rs.getString("MEM_EMAIL"));
				membervo.setMem_state(rs.getInt("MEM_STATE"));
				membervo.setMem_verify(rs.getInt("MEM_VERIFY"));
				membervo.setMem_id(rs.getString("MEM_ID"));
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
		return membervo;
	}

	@Override
	public MemberVO forgetpassword(String mem_email) {

		MemberVO membervo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(FORGETPASSWORD);

			pstmt.setString(1, mem_email);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				membervo = new MemberVO();
				membervo.setMemno(rs.getInt("MEMNO"));
				membervo.setMname(rs.getString("MNAME"));
				membervo.setGender(rs.getString("GENDER"));
				membervo.setBday(rs.getDate("BDAY"));
				membervo.setAddress(rs.getString("ADDRESS"));
				membervo.setPhone(rs.getString("Phone"));
				membervo.setMaccount(rs.getString("MACCOUNT"));
				membervo.setMpassword(rs.getString("MPASSWORD"));
				membervo.setMem_email(rs.getString("MEM_EMAIL"));
				membervo.setMem_state(rs.getInt("MEM_STATE"));
				membervo.setMem_verify(rs.getInt("MEM_VERIFY"));
				membervo.setMem_id(rs.getString("MEM_ID"));
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
		return membervo;
	}

	@Override
	public List<MemberVO> getAll() {

		List<MemberVO> list = new ArrayList<MemberVO>();
		MemberVO membervo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GETALL);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				membervo = new MemberVO();
				membervo.setMemno(rs.getInt("MEMNO"));
				membervo.setMname(rs.getString("MNAME"));
				membervo.setGender(rs.getString("GENDER"));
				membervo.setBday(rs.getDate("BDAY"));
				membervo.setAddress(rs.getString("ADDRESS"));
				membervo.setPhone(rs.getString("Phone"));
				membervo.setMaccount(rs.getString("MACCOUNT"));
				membervo.setMpassword(rs.getString("MPASSWORD"));
				membervo.setMem_email(rs.getString("MEM_EMAIL"));
				membervo.setMem_state(rs.getInt("MEM_STATE"));
				membervo.setMem_verify(rs.getInt("MEM_VERIFY"));
				membervo.setMem_id(rs.getString("MEM_ID"));

				list.add(membervo);
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
	public List<MemberVO> getAll2() {
		// TODO Auto-generated method stub
		return null;
	}

}
