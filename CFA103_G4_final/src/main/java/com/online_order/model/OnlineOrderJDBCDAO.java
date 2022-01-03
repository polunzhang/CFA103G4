package com.online_order.model;

import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.online_order.model.OnlineOrderJDBCDAO;

import jdbc.util.CompositeQuery.jdbcUtil_CompositeQuery_LiveOrder;

import com.online_detail.model.OnlineDetailJDBCDAO;
import com.online_detail.model.OnlineDetailVO;
import com.online_order.model.OnlineOrderDAO_interface;

import java.sql.*;



public class OnlineOrderJDBCDAO implements OnlineOrderDAO_interface{
	
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CFA103_G4");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/CFA103_G4?serverTimezone=Asia/Taipei";
	String userid = "David";
	String passwd = "123456";
	
	private static final String INSERT_STMT = 
			"INSERT INTO online_order (empno, memno, pay_status, address, set_time, create_time, total, pay_way) VALUES (?,?,?,?,?,?,?,?)";
	private static final String GET_ALL_STMT = 
			"SELECT olno, empno, memno, pay_status, address, set_time, create_time, total, pay_way FROM online_order order by set_time";
	private static final String GET_ONE_BY_OLNO = 
			"SELECT olno, empno, memno, pay_status, address, set_time, create_time, total, pay_way FROM online_order where olno = ?";
	private static final String GET_ONE_BY_PAY_STATUS = 
			"SELECT olno, empno, memno, pay_status, address, set_time, create_time, total, pay_way FROM online_order where  pay_status = ?";
	private static final String UPDATE = 
			"UPDATE online_order set empno = ?, memno = ?, pay_status= ?, address = ?, set_time = ?, create_time = ?, total = ?, pay_way = ? where olno = ?";
	private static final String DELETE = 
			"DELETE FROM online_order where olno = ?";
	private static final String GET_ONE_BY_ADDRESS = 
			"SELECT olno, empno, memno, pay_status, address, set_time, create_time, total, pay_way FROM online_order WHERE address is not null AND address !='' order by set_time";
	private static final String GET_ONE_BY_ADDRESS2 = 
			"SELECT olno, empno, memno, pay_status, address, set_time, create_time, total, pay_way FROM online_order WHERE address is null OR address='' order by set_time";
	private static final String GET_ALL_BY_MEMNO = 
			"SELECT olno, empno, memno, pay_status, address, set_time, create_time, total, pay_way FROM online_order WHERE memno=? order by set_time DESC";
	                                                            
	
	@Override
	public List<OnlineOrderVO> getAll(Map<String, String[]> map) {
		List<OnlineOrderVO> list = new ArrayList<OnlineOrderVO>();
		OnlineOrderVO onlineorderVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			String finalSQL = "select * from online_order " + jdbcUtil_CompositeQuery_LiveOrder.get_WhereCondition(map)
			+ "order by olno";
			pstmt = con.prepareStatement(finalSQL);
			System.out.println("●●finalSQL(by DAO) = " + finalSQL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				onlineorderVO = new OnlineOrderVO();
				onlineorderVO.setOlno(rs.getInt("olno"));
				onlineorderVO.setEmpno(rs.getInt("empno"));
				onlineorderVO.setMemno(rs.getInt("memno"));
				onlineorderVO.setPay_status(rs.getInt("pay_status"));
				onlineorderVO.setAddress(rs.getString("address"));
				onlineorderVO.setSet_time(rs.getTimestamp("set_time"));
				onlineorderVO.setCreate_time(rs.getTimestamp("create_time"));
				onlineorderVO.setTotal(rs.getInt("total"));
				onlineorderVO.setPay_way(rs.getInt("pay_way"));
				list.add(onlineorderVO); // Store the row in the list
			}

			// Handle any SQL errors
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
	public void insert(OnlineOrderVO onlineOrderVO) {
		
		Connection con  = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver); //載入驅動
			con = DriverManager.getConnection(url, userid, passwd);//建立連線
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setInt(1, onlineOrderVO.getEmpno());//送出sql指令
			pstmt.setInt(2, onlineOrderVO.getMemno());
			pstmt.setInt(3, onlineOrderVO.getPay_status());
			pstmt.setString(4, onlineOrderVO.getAddress());
			pstmt.setTimestamp(5,onlineOrderVO.getSet_time());
			pstmt.setTimestamp(6, onlineOrderVO.getCreate_time());
			pstmt.setInt(7, onlineOrderVO.getTotal());
			pstmt.setInt(8, onlineOrderVO.getPay_way());
			
			pstmt.executeUpdate();
			
			// Handle any driver errors
		}catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		}finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if(con != null) {
				try {
					con.close();
				}catch(Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		
	}
	
	@Override
	public void update(OnlineOrderVO onlineOrderVO) {
		Connection con  = null;
		PreparedStatement pstmt = null;
		
		try {
			
			Class.forName(driver); //載入驅動
			con = DriverManager.getConnection(url, userid, passwd);//建立連線
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setInt(1, onlineOrderVO.getEmpno());
			pstmt.setInt(2, onlineOrderVO.getMemno());
			pstmt.setInt(3, onlineOrderVO.getPay_status());
			pstmt.setString(4, onlineOrderVO.getAddress());
			pstmt.setTimestamp(5, onlineOrderVO.getSet_time());
			pstmt.setTimestamp(6, onlineOrderVO.getCreate_time());
			pstmt.setInt(7, onlineOrderVO.getTotal());
			pstmt.setInt(8, onlineOrderVO.getPay_way());
			pstmt.setInt(9, onlineOrderVO.getOlno());
			pstmt.executeUpdate();
			
			
		}catch(SQLException se) {
			throw new RuntimeException("A database error occured." + se.getMessage());
			
		}catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		}finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if(con != null) {
				try {
					con.close();
				}catch(Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}
	
	@Override
	public void delete(Integer olno) {
		
		Connection con  = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver); //載入驅動
			con = DriverManager.getConnection(url, userid, passwd);//建立連線
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setInt(1, olno);
			pstmt.executeUpdate();
			
		}catch(SQLException se) {
			throw new RuntimeException("A database error occured." + se.getMessage());
		
		}catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		}finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if(con != null) {
				try {
					con.close();
				}catch(Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}
	
	@Override
	public List<OnlineOrderVO> getAll() {
		List<OnlineOrderVO> list = new ArrayList<OnlineOrderVO>();
		OnlineOrderVO onlineOrderVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver); //載入驅動
			con = DriverManager.getConnection(url, userid, passwd);//建立連線
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				//onlineOrderVO 也稱為 Domain objects
				onlineOrderVO = new OnlineOrderVO();
				onlineOrderVO.setOlno(rs.getInt("olno"));
				onlineOrderVO.setEmpno(rs.getInt("empno"));
				onlineOrderVO.setMemno(rs.getInt("memno"));
				onlineOrderVO.setPay_status(rs.getInt("pay_status"));
				onlineOrderVO.setAddress(rs.getString("address"));
				onlineOrderVO.setSet_time(rs.getTimestamp("set_time"));
				onlineOrderVO.setCreate_time(rs.getTimestamp("create_time"));
				onlineOrderVO.setTotal(rs.getInt("total"));
				onlineOrderVO.setPay_way(rs.getInt("pay_way"));
				list.add(onlineOrderVO); // Store the row in the list
			}
			
		}catch(SQLException se) {
			throw new RuntimeException("A database error occured." +se.getMessage());
		}catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		}finally {
			if(rs != null) {
				try {
					rs.close();
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			
			if(pstmt != null) {
				try {
					pstmt.close();
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if(con != null) {
				try {
					con.close();
				}catch(Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}
	
	@Override
	public OnlineOrderVO findByOlno(Integer olno) {
		OnlineOrderVO onlineOrderVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver); //載入驅動
			con = DriverManager.getConnection(url, userid, passwd);//建立連線
			pstmt = con.prepareStatement(GET_ONE_BY_OLNO);
			pstmt.setInt(1, olno);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				//onlineOrderVO 也稱為 Domain objects
				onlineOrderVO = new OnlineOrderVO();
				onlineOrderVO.setOlno(rs.getInt("olno"));
				onlineOrderVO.setEmpno(rs.getInt("empno"));
				onlineOrderVO.setMemno(rs.getInt("memno"));
				onlineOrderVO.setPay_status(rs.getInt("pay_status"));
				onlineOrderVO.setAddress(rs.getString("address"));
				onlineOrderVO.setSet_time(rs.getTimestamp("set_time"));
				onlineOrderVO.setCreate_time(rs.getTimestamp("create_time"));
				onlineOrderVO.setTotal(rs.getInt("total"));
				onlineOrderVO.setPay_way(rs.getInt("pay_way"));
			}
			
		}catch(SQLException se) {
			throw new RuntimeException("A database error occured." +se.getMessage());
		}catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		}finally {
			if(rs != null) {
				try {
					rs.close();
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			
			if(pstmt != null) {
				try {
					pstmt.close();
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if(con != null) {
				try {
					con.close();
				}catch(Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return onlineOrderVO;
	}
		
	@Override
	public List<OnlineOrderVO> findByPayStatus(Integer pay_status){
		List<OnlineOrderVO> list = new ArrayList<OnlineOrderVO>();
		OnlineOrderVO onlineOrderVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver); //載入驅動
			con = DriverManager.getConnection(url, userid, passwd);//建立連線
			pstmt = con.prepareStatement(GET_ONE_BY_PAY_STATUS);
			pstmt.setInt(1, pay_status);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				//onlineOrderVO 也稱為 Domain objects
				onlineOrderVO = new OnlineOrderVO();
				onlineOrderVO.setOlno(rs.getInt("olno"));
				onlineOrderVO.setEmpno(rs.getInt("empno"));
				onlineOrderVO.setMemno(rs.getInt("memno"));
				onlineOrderVO.setPay_status(rs.getInt("pay_status"));
				onlineOrderVO.setAddress(rs.getString("address"));
				onlineOrderVO.setSet_time(rs.getTimestamp("set_time"));
				onlineOrderVO.setCreate_time(rs.getTimestamp("create_time"));
				onlineOrderVO.setTotal(rs.getInt("total"));
				onlineOrderVO.setPay_way(rs.getInt("pay_way"));
				list.add(onlineOrderVO); // Store the row in the list
			}
			
		}catch(SQLException se) {
			throw new RuntimeException("A database error occured." +se.getMessage());
		}catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		}finally {
			if(rs != null) {
				try {
					rs.close();
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			
			if(pstmt != null) {
				try {
					pstmt.close();
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if(con != null) {
				try {
					con.close();
				}catch(Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}
	
	
	@Override
	public List<OnlineOrderVO> findByMemno(Integer memno){
		List<OnlineOrderVO> list = new ArrayList<OnlineOrderVO>();
		OnlineOrderVO onlineOrderVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver); //載入驅動
			con = DriverManager.getConnection(url, userid, passwd);//建立連線
			pstmt = con.prepareStatement(GET_ALL_BY_MEMNO);
			pstmt.setInt(1, memno);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				//onlineOrderVO 也稱為 Domain objects
				onlineOrderVO = new OnlineOrderVO();
				onlineOrderVO.setOlno(rs.getInt("olno"));
				onlineOrderVO.setEmpno(rs.getInt("empno"));
				onlineOrderVO.setMemno(rs.getInt("memno"));
				onlineOrderVO.setPay_status(rs.getInt("pay_status"));
				onlineOrderVO.setAddress(rs.getString("address"));
				onlineOrderVO.setSet_time(rs.getTimestamp("set_time"));
				onlineOrderVO.setCreate_time(rs.getTimestamp("create_time"));
				onlineOrderVO.setTotal(rs.getInt("total"));
				onlineOrderVO.setPay_way(rs.getInt("pay_way"));
				list.add(onlineOrderVO); // Store the row in the list
			}
			
		}catch(SQLException se) {
			throw new RuntimeException("A database error occured." +se.getMessage());
		}catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		}finally {
			if(rs != null) {
				try {
					rs.close();
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			
			if(pstmt != null) {
				try {
					pstmt.close();
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if(con != null) {
				try {
					con.close();
				}catch(Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}
	
	
	@Override
	public Integer insertWithDetails(OnlineOrderVO onlineOrderVO, List<OnlineDetailVO> list) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		Integer olno = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			
			// 1●設定於 pstm.executeUpdate()之前
			con.setAutoCommit(false);
			
			// 先新增訂單主檔
			String cols[] = {"OLNO"};
			pstmt = con.prepareStatement(INSERT_STMT, cols);
			pstmt.setInt(1, onlineOrderVO.getEmpno());
			pstmt.setInt(2, onlineOrderVO.getMemno());
			pstmt.setInt(3, onlineOrderVO.getPay_status());
			pstmt.setString(4, onlineOrderVO.getAddress());
			pstmt.setTimestamp(5,onlineOrderVO.getSet_time());
			pstmt.setTimestamp(6, onlineOrderVO.getCreate_time());
			pstmt.setInt(7, onlineOrderVO.getTotal());
			pstmt.setInt(8, onlineOrderVO.getPay_way());
			
			pstmt.executeUpdate();
			
			// 掘取對應的自增主鍵值
			String next_olno = null;
			ResultSet rs = pstmt.getGeneratedKeys();
			if(rs.next()) {
				next_olno = rs.getString(1);
				System.out.println("自增主鍵值= " + next_olno + "(剛新增成功的訂單編號)");
			} else {
				System.out.println("未取得自增主鍵值");
			}
			rs.close();
			// 再同時新增訂單明細
			OnlineDetailJDBCDAO dao = new OnlineDetailJDBCDAO();
			System.out.println("list.size()-A=" + list.size());
			for(OnlineDetailVO data : list) {
				data.setOlno(new Integer(next_olno));
				dao.insert2(data, con);
			}
			
			// 2●設定於 pstm.executeUpdate()之後
			con.commit();
			con.setAutoCommit(true);
			System.out.println("list.size()-B=" + list.size());
			System.out.println("新增訂單編號" + next_olno + "時,共有訂單明細" + list.size() + "筆同時被新增");
			olno = new Integer(next_olno);
			
			// Handle any driver errors
		} catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch(SQLException se) {
			if(con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					System.err.print("Transaction is being ");
					System.err.println("rolled back-由-dept");
					con.rollback();
				} catch(SQLException excep) {
					throw new RuntimeException("rollback error occured. "
							+ excep.getMessage());
				}
			}
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch(Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return olno;
	}
	
	@Override
	public List<OnlineOrderVO> findByAddress() {
		List<OnlineOrderVO> list = new ArrayList<OnlineOrderVO>();
		OnlineOrderVO onlineOrderVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver); //載入驅動
			con = DriverManager.getConnection(url, userid, passwd);//建立連線
			pstmt = con.prepareStatement(GET_ONE_BY_ADDRESS);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				//onlineOrderVO 也稱為 Domain objects
				onlineOrderVO = new OnlineOrderVO();
				onlineOrderVO.setOlno(rs.getInt("olno"));
				onlineOrderVO.setEmpno(rs.getInt("empno"));
				onlineOrderVO.setMemno(rs.getInt("memno"));
				onlineOrderVO.setPay_status(rs.getInt("pay_status"));
				onlineOrderVO.setAddress(rs.getString("address"));
				onlineOrderVO.setSet_time(rs.getTimestamp("set_time"));
				onlineOrderVO.setCreate_time(rs.getTimestamp("create_time"));
				onlineOrderVO.setTotal(rs.getInt("total"));
				onlineOrderVO.setPay_way(rs.getInt("pay_way"));
				list.add(onlineOrderVO); // Store the row in the list
			}
			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured." +se.getMessage());
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
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
	public List<OnlineOrderVO> findByAddress2() {
		List<OnlineOrderVO> list = new ArrayList<OnlineOrderVO>();
		OnlineOrderVO onlineOrderVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver); //載入驅動
			con = DriverManager.getConnection(url, userid, passwd);//建立連線
			pstmt = con.prepareStatement(GET_ONE_BY_ADDRESS2);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				//onlineOrderVO 也稱為 Domain objects
				onlineOrderVO = new OnlineOrderVO();
				onlineOrderVO.setOlno(rs.getInt("olno"));
				onlineOrderVO.setEmpno(rs.getInt("empno"));
				onlineOrderVO.setMemno(rs.getInt("memno"));
				onlineOrderVO.setPay_status(rs.getInt("pay_status"));
				onlineOrderVO.setAddress(rs.getString("address"));
				onlineOrderVO.setSet_time(rs.getTimestamp("set_time"));
				onlineOrderVO.setCreate_time(rs.getTimestamp("create_time"));
				onlineOrderVO.setTotal(rs.getInt("total"));
				onlineOrderVO.setPay_way(rs.getInt("pay_way"));
				list.add(onlineOrderVO); // Store the row in the list
			}
			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured." +se.getMessage());
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
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
	
	public static void main(String[] args) {
		
		OnlineOrderJDBCDAO dao = new OnlineOrderJDBCDAO();
		
		//新增
//		OnlineOrderVO onlineOrderVO = new OnlineOrderVO();
//		onlineOrderVO.setEmpno(1);
//		onlineOrderVO.setMemno(3);
//		onlineOrderVO.setPay_status(0);
//		onlineOrderVO.setAddress("320桃園市中壢區復興路46號9樓");
//		onlineOrderVO.setSet_time(java.sql.Timestamp.valueOf("2021-12-22 19:20:00"));
//		onlineOrderVO.setCreate_time(java.sql.Timestamp.valueOf("2021-12-22 16:20:00"));
//		onlineOrderVO.setTotal(455);
//		onlineOrderVO.setPay_way(0);
//		dao.insert(onlineOrderVO);
		
		//修改
//		OnlineOrderVO onlineOrderVO2 = new OnlineOrderVO();
//		onlineOrderVO2.setOlno(1);
//		onlineOrderVO2.setEmpno(1);
//		onlineOrderVO2.setMemno(2);
//		onlineOrderVO2.setPay_status(1);
//		onlineOrderVO2.setAddress("桃園市桃園區中正路231號");
//		onlineOrderVO2.setSet_time(java.sql.Timestamp.valueOf("2021-12-22 19:20:00"));
//		onlineOrderVO2.setCreate_time(java.sql.Timestamp.valueOf("2021-12-22 19:20:00"));
//		onlineOrderVO2.setTotal(2000);
//		onlineOrderVO2.setPay_way(1);
//		dao.update(onlineOrderVO2);
		
		//刪除
//		dao.delete(3);
		
		//查全部
//		List<OnlineOrderVO> list = dao.getAll();
//		for(OnlineOrderVO onlineOrderVO3:list) {
//			System.out.print(onlineOrderVO3.getOlno() + ",");
//			System.out.print(onlineOrderVO3.getEmpno() + ",");
//			System.out.print(onlineOrderVO3.getMemno() + ",");
//			System.out.print(onlineOrderVO3.getPay_status() + ",");
//			System.out.print(onlineOrderVO3.getAddress() + ",");
//			System.out.print(onlineOrderVO3.getSet_time() + ",");
//			System.out.print(onlineOrderVO3.getCreate_time() + ",");
//			System.out.print(onlineOrderVO3.getTotal() + ",");
//			System.out.print(onlineOrderVO3.getPay_way());
//			System.out.println();
//		}
		
		//用付款狀態( )查詢
//		List<OnlineOrderVO> list2 = dao.findByPayStatus(0);
//		for(OnlineOrderVO onlineOrderVO4:list2) {
//			System.out.print(onlineOrderVO4.getOlno() + ",");
//			System.out.print(onlineOrderVO4.getEmpno() + ",");
//			System.out.print(onlineOrderVO4.getMemno() + ",");
//			System.out.print(onlineOrderVO4.getPay_status() + ",");
//			System.out.print(onlineOrderVO4.getAddress() + ",");
//			System.out.print(onlineOrderVO4.getSet_time() + ",");
//			System.out.print(onlineOrderVO4.getCreate_time() + ",");
//			System.out.print(onlineOrderVO4.getTotal() + ",");
//			System.out.print(onlineOrderVO4.getPay_way());
//			System.out.println();
//		}
		
		//用會員編號查詢
//		OnlineOrderVO onlineOrderVO5 = dao.findByMemno(5);
//		System.out.print(onlineOrderVO5.getOlno() + ",");
//		System.out.print(onlineOrderVO5.getEmpno() + ",");
//		System.out.print(onlineOrderVO5.getMemno() + ",");
//		System.out.print(onlineOrderVO5.getPay_status() + ",");
//		System.out.print(onlineOrderVO5.getAddress() + ",");
//		System.out.print(onlineOrderVO5.getSet_time() + ",");
//		System.out.print(onlineOrderVO5.getCreate_time() + ",");
//		System.out.print(onlineOrderVO5.getTotal() + ",");
//		System.out.print(onlineOrderVO5.getPay_way());

		//用地址查詢外送訂單(地址不為空字串)
//		List<OnlineOrderVO> list3 = dao.findByAddress();
//		for(OnlineOrderVO onlineOrderVO6:list3) {
//			System.out.print(onlineOrderVO6.getOlno() + ",");
//			System.out.print(onlineOrderVO6.getEmpno() + ",");
//			System.out.print(onlineOrderVO6.getMemno() + ",");
//			System.out.print(onlineOrderVO6.getPay_status() + ",");
//			System.out.print(onlineOrderVO6.getAddress() + ",");
//			System.out.print(onlineOrderVO6.getSet_time() + ",");
//			System.out.print(onlineOrderVO6.getCreate_time() + ",");
//			System.out.print(onlineOrderVO6.getTotal() + ",");
//			System.out.print(onlineOrderVO6.getPay_way());
//			System.out.println();
//		}
		
		//用地址查詢外帶訂單(地址為空字串或為空值)
//		List<OnlineOrderVO> list4 = dao.findByAddress2();
//		for(OnlineOrderVO onlineOrderVO7:list4) {
//			System.out.print(onlineOrderVO7.getOlno() + ",");
//			System.out.print(onlineOrderVO7.getEmpno() + ",");
//			System.out.print(onlineOrderVO7.getMemno() + ",");
//			System.out.print(onlineOrderVO7.getPay_status() + ",");
//			System.out.print(onlineOrderVO7.getAddress() + ",");
//			System.out.print(onlineOrderVO7.getSet_time() + ",");
//			System.out.print(onlineOrderVO7.getCreate_time() + ",");
//			System.out.print(onlineOrderVO7.getTotal() + ",");
//			System.out.print(onlineOrderVO7.getPay_way());
//			System.out.println();
//		}
//		=====================================
//		OnlineOrderDAO_interface dao = new OnlineOrderJDBCDAO();
		//新增
//		OnlineOrderVO onlineOrderVO = new OnlineOrderVO();
//		OnlineDetailJDBCDAO dao2 = new OnlineDetailJDBCDAO();
//		List<OnlineDetailVO> list = dao2.findByOlno(3);
//		onlineOrderVO.setEmpno(3);
//		onlineOrderVO.setMemno(3);
//		onlineOrderVO.setPay_status(0);
//		onlineOrderVO.setAddress("桃園市桃園區中正路104號");
//		onlineOrderVO.setSet_time(java.sql.Timestamp.valueOf("2021-12-25 18:00:00"));
//		onlineOrderVO.setCreate_time(new Timestamp(System.currentTimeMillis()));
//		onlineOrderVO.setTotal(1760);
//		onlineOrderVO.setPay_way(0);
//		dao.insertWithDetails(onlineOrderVO, list);
//		
		
		//用會員編號查詢
		List<OnlineOrderVO> list5 = dao.findByMemno(5);
		for(OnlineOrderVO onlineOrderVO8:list5) {
			System.out.print(onlineOrderVO8.getOlno() + ",");
			System.out.print(onlineOrderVO8.getEmpno() + ",");
			System.out.print(onlineOrderVO8.getMemno() + ",");
			System.out.print(onlineOrderVO8.getPay_status() + ",");
			System.out.print(onlineOrderVO8.getAddress() + ",");
			System.out.print(onlineOrderVO8.getSet_time() + ",");
			System.out.print(onlineOrderVO8.getCreate_time() + ",");
			System.out.print(onlineOrderVO8.getTotal() + ",");
			System.out.print(onlineOrderVO8.getPay_way());
			System.out.println();
		}
		
	}
}

