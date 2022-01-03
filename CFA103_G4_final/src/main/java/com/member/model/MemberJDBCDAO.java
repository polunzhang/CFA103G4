package com.member.model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MemberJDBCDAO implements MemberDAO_interface {

	public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	public static final String URL = "jdbc:mysql://localhost:3306/CFA103_G4?serverTimezone=Asia/Taipei";
	public static final String USER = "root";
	public static final String PASSWORD = "123456";

	public static final String INSERT_STMT = "INSERT INTO MEMBER(MNAME, GENDER, BDAY, ADDRESS, PHONE, MACCOUNT, MPASSWORD, MEM_EMAIL, MEM_STATE, MEM_VERIFY) values(?,?,?,?,?,?,?,?,?,?)";
	public static final String UPDATE_STMT = "UPDATE CS_REC SET MNAME=?, GENDER=?, BDAY=?, ADDRESS=?, PHONE=?, MACCOUNT=?, MPASSWORD=?, MEM_EMAIL=?, MEM_STATE=?, MEM_VERIFY=? where MEMNO=?";
	public static final String DELETE_STMT = "DELETE FROM MEMBER where MEMNO=?";
	public static final String GETONE = "SELECT * FROM MEMBER where MEMNO=?";
	public static final String GETALL = "SELECT * FROM MEMBER ORDER BY MEMNO";
	public static final String GETALL2 = "SELECT * FROM CFA103_G4.member where mem_state=1 and MEM_VERIFY=1";

	static {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void insert(MemberVO membervo) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = DriverManager.getConnection(URL, USER, PASSWORD);
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
			con = DriverManager.getConnection(URL, USER, PASSWORD);
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
			pstmt.setInt(11, membervo.getMemno());
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
			con = DriverManager.getConnection(URL, USER, PASSWORD);
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

			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(GETONE);

			pstmt.setInt(1, memno);

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

			con = DriverManager.getConnection(URL, USER, PASSWORD);
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

	public static void main(String[] args) throws IOException {

		MemberDAO_interface dao = new MemberJDBCDAO();

		List<MemberVO> list = dao.getAll();
		for (MemberVO membervo : list) {
			System.out.println(membervo.getMaccount());
			
		}
	}
	
	@Override
		public List<MemberVO> getAll2() {

			List<MemberVO> list = new ArrayList<MemberVO>();
			MemberVO membervo = null;
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {

				con = DriverManager.getConnection(URL, USER, PASSWORD);
				pstmt = con.prepareStatement(GETALL2);

				rs = pstmt.executeQuery();

				while (rs.next()) {
					membervo = new MemberVO();
					membervo.setMname(rs.getString("MNAME"));
					membervo.setMem_email(rs.getString("MEM_EMAIL"));

					
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
	public MemberVO findByName(String mname) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MemberVO login(String maccount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MemberVO forgetpassword(String mem_email) {
		// TODO Auto-generated method stub
		return null;
	}
}
