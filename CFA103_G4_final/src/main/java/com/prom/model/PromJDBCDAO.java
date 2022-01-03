package com.prom.model;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.meal.model.MealDAO_interface;
import com.meal.model.MealJDBCDAO;
import com.meal.model.MealVO;
import com.meal_pic.model.MealPicVO;


public class PromJDBCDAO implements PromDAO_interface {
	//�ŧi�s�u�һݱ`��
	public static final String driver = "com.mysql.cj.jdbc.Driver";
	public static final String url = "jdbc:mysql://localhost:3306/CFA103_G4?serverTimezone=Asia/Taipei";
	public static final String userid = "David";
	public static final String passwd = "123456";
	//�]�w�W�R��d��mysql���O
	private static final String INSERT_STMT = "INSERT INTO PROM (empno,prom_title,prom_content,prom_image) VALUES (?, ?, ?, ?)";
	private static final String DELETE = "DELETE FROM PROM where promno = ?";
	private static final String DELETEDT = "DELETE FROM PROM_detail where promno = ?";
	private static final String UPDATE = "UPDATE `CFA103_G4`.`PROM` SET `EMPNO` =?, `PROM_TITLE` = ?, `PROM_CONTENT` =?, `PROM_IMAGE` = ? WHERE (`PROMNO` =?);";
	private static final String GET_ONE_STMT = "SELECT * FROM PROM where promno = ?";
	private static final String GET_ALL_STMT = "SELECT * FROM PROM order by promno";
	private static final String GET_ONE_EMP_STMT = "SELECT * FROM PROM where empno = ?";
	private static final String GET_ONE_PIC = "SELECT PROM_IMAGE FROM PROM where promno = ?";

	
	static {
		try {
			Class.forName(driver);
		}  catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public PromVO insert(PromVO PromVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		

		try {
			con=DriverManager.getConnection(url,userid,passwd);
			String cols[] = {"PROMNO"};
			pstmt = con.prepareStatement(INSERT_STMT , cols);
			pstmt.setInt(1, PromVO.getEmpno());
			pstmt.setString(2, PromVO.getProm_title());
			pstmt.setString(3, PromVO.getProm_content());
			pstmt.setBytes(4, PromVO.getProm_image());
			
			pstmt.executeUpdate();
			
			String next_promno = null;
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				next_promno = rs.getString(1);
				System.out.println("�ۼW�D���= " + next_promno +"(��s�W���\���P�P�s��)");
			PromVO.setPromno(Integer.parseInt(next_promno));
//			System.out.println(PromVO.getPromno());
			}
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
		return PromVO;
	}

	@Override
	public void update(PromVO PromVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con=DriverManager.getConnection(url,userid,passwd);
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setInt(1, PromVO.getEmpno());
			pstmt.setString(2, PromVO.getProm_title());
			pstmt.setString(3, PromVO.getProm_content());
			pstmt.setBytes(4, PromVO.getProm_image());
			pstmt.setInt(5, PromVO.getPromno());
			
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
	public void delete(Integer Promno) {
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETEDT);
			pstmt.setInt(1, Promno);
			pstmt.executeUpdate();
			
			pstmt1 = con.prepareStatement(DELETE);
			pstmt1.setInt(1, Promno);
			pstmt1.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		finally {
			if(pstmt !=null) {
					try {
						pstmt.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}		
			}
			if(pstmt1 !=null) {
				try {
					pstmt1.close();
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
	public PromVO findByPrimaryKey(Integer Promno) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		PromVO Promvo = null;
		
	
			try {
				con=DriverManager.getConnection(url,userid,passwd);
				pstmt = con.prepareStatement(GET_ONE_STMT);
				pstmt.setInt(1, Promno);
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					Promvo = new PromVO();
					Promvo.setPromno(Promno);
					Promvo.setEmpno(rs.getInt("EMPNO"));
					Promvo.setProm_title(rs.getString("PROM_TITLE"));
					Promvo.setProm_content(rs.getString("PROM_CONTENT"));
					Promvo.setProm_image(rs.getBytes("PROM_IMAGE"));
				}		
			} catch (SQLException e) {
				e.printStackTrace();
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
		return Promvo;
	}

	@Override
	public List<PromVO> findByEmpno(Integer Empno) {
		List<PromVO> list = new ArrayList<PromVO>();
		PromVO Promvo = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_EMP_STMT);
			pstmt.setInt(1, Empno);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				Promvo = new PromVO();
				Promvo.setPromno(rs.getInt("Promno"));
				Promvo.setEmpno(Empno);
				Promvo.setProm_title(rs.getString("PROM_TITLE"));
				Promvo.setProm_content(rs.getString("PROM_CONTENT"));
				Promvo.setProm_image(rs.getBytes("PROM_IMAGE"));
				list.add(Promvo); 
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

	@Override
	public byte[] findonepic(Integer promno) {
		byte[] Prompic = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
	
			
			try {
				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(GET_ONE_PIC);
				pstmt.setInt(1, promno);
				rs = pstmt.executeQuery();
				rs.next();
				Prompic = rs.getBytes(1);
				
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
			
			
		
		return Prompic;
	}

	@Override
	public List<PromVO> getAll() {
		List<PromVO> list = new ArrayList<PromVO>();
		PromVO Promvo = null;

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
				Promvo = new PromVO();
				Promvo.setPromno(rs.getInt("Promno"));
				Promvo.setEmpno(rs.getInt("EMPNO"));
				Promvo.setProm_title(rs.getString("PROM_TITLE"));
				Promvo.setProm_content(rs.getString("PROM_CONTENT"));
				Promvo.setProm_image(rs.getBytes("PROM_IMAGE"));
				list.add(Promvo); 
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
		public static void main(String[] args) {


			PromJDBCDAO dao = new PromJDBCDAO();
			
//			===�s�W���===
//			PromVO Promvo1 = new PromVO();
//			Promvo1.setEmpno(1);
//			Promvo1.setProm_title("�����K�O");
//			Promvo1.setProm_content("������ө���");
//			dao.insert(Promvo1);
			
			
//			===�ק���===
//			PromVO Promvo2 = new PromVO();
//			Promvo2.setEmpno(2);
//			Promvo2.setProm_title("����100");
//			Promvo2.setProm_content("������ө���");
//			Promvo2.setProm_image(null);
//			Promvo2.setPromno(5);
//			dao.update(Promvo2);
			
//			===�R��===
//			dao.delete(5);

			
//			===�d�߳浧���===
//			PromVO Promvo3 = dao.findByPrimaryKey(1);
//			�L�X�d�ߵ��G
//			System.out.println("PROMNO ="+Promvo3.getPromno());
//			System.out.println("EMPNO ="+Promvo3.getEmpno());
//			System.out.println("PROM_TITLE ="+Promvo3.getProm_title());
//			System.out.println("PROM_CONTENT ="+Promvo3.getProm_content());
//			System.out.println("PROM_IMAGE ="+Promvo3.getProm_image());
//			System.out.println("=========");

			
//			===�d�ߺ޲z���s�W�P�P���O���==
//			List<PromVO> findByEmpnoList = dao.findByEmpno(1);
//			for(PromVO Promvo4 : findByEmpnoList) {	
//			�d�߸�Ƥ����C��
//				System.out.println("PROMNO ="+Promvo4.getPromno());
//				System.out.println("EMPNO ="+Promvo4.getEmpno());
//				System.out.println("PROM_TITLE ="+Promvo4.getProm_title());
//				System.out.println("PROM_CONTENT ="+Promvo4.getProm_content());
//				System.out.println("PROM_IMAGE ="+Promvo4.getProm_image());
//				System.out.println("==========");
//			}
		
			
			
//			===�d�߾㵧���===
//			List<PromVO> PromvoList = dao.getAll();
//			for(PromVO Promvo4 : PromvoList) {
//			
//			1�B�d�߸�ƥH�r��C��(�]MealVO���gtoString��k)
//				System.out.println(mealvo);
			
//			2�B�d�߸�Ƥ����C��
//				System.out.println("PROMNO ="+Promvo4.getPromno());
//				System.out.println("EMPNO ="+Promvo4.getEmpno());
//				System.out.println("PROM_TITLE ="+Promvo4.getProm_title());
//				System.out.println("PROM_CONTENT ="+Promvo4.getProm_content());
//				System.out.println("PROM_IMAGE ="+Promvo4.getProm_image());
//				System.out.println("==========");
//
//			}
			
//			3�B�d�߸�ƥH�r��C��(���i�k)
//			mealvoList.forEach(System.out::println);
			
		}	
}

	
	
	
	
	
	
	

