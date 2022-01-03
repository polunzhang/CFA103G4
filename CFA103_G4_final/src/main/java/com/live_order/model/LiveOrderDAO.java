package com.live_order.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.live_detail.model.LiveDetailDAOJDBC;
import com.live_detail.model.LiveDetailVO;

import jdbc.util.CompositeQuery.jdbcUtil_CompositeQuery_LiveOrder;

public class LiveOrderDAO implements LiveOrderDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CFA103_G4");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO `cfa103_g4`.`live_order` "
			+ "(`EMPNO`, `TABLENO`, `PAY_STATUS`, `CREATE_TIME`, `PAY_WAY`, `TOTAL`) VALUES (?,?,?,?,?,?);";
	private static final String GET_ONE_BY_PRIMARYKEY = "SELECT LIVENO,EMPNO,pay_status,create_time,total,tableno,pay_way FROM `cfa103_g4`.`live_order` where LIVENO = ?";
	private static final String GET_ALL_STMT = "SELECT `LIVENO`,`EMPNO`, `TABLENO`, `PAY_STATUS`, `CREATE_TIME`, `TOTAL`, `PAY_WAY` FROM LIVE_ORDER ORDER BY create_time desc";
	private static final String GET_ONE_BY_TABLENO = "SELECT LIVENO,EMPNO,pay_status,create_time,total,tableno,pay_way FROM LIVE_ORDER where TABLENO = ? and PAY_STATUS = ? ORDER BY create_time";
	private static final String GET_ONE_BY_PAY_STATUS = "SELECT LIVENO,EMPNO,pay_status,create_time,total,tableno,pay_way FROM LIVE_ORDER where PAY_STATUS = ?";
	private static final String UPDATE = "UPDATE LIVE_ORDER set total=? ,pay_status=? ,pay_way = ? where LIVENO = ?";
	private static final String GET_UNCHECK_BY_TABLENO = "SELECT LIVENO,EMPNO,pay_status,create_time,total,tableno,pay_way FROM LIVE_ORDER where TABLENO = ? and PAY_STATUS = 0 ORDER BY create_time";
	@Override
	public void insert(LiveOrderVO LiveOrderVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setInt(1, LiveOrderVO.getEmpno());
			pstmt.setInt(2, LiveOrderVO.getTableno());
			pstmt.setInt(3, LiveOrderVO.getPay_status());
			pstmt.setTimestamp(4, LiveOrderVO.getCreate_time());
			pstmt.setInt(5, LiveOrderVO.getPay_way());
			pstmt.setInt(6, LiveOrderVO.getTotal());

//pstmt.executeUpdate("set auto_increment_offset=10;");
//pstmt.executeUpdate("set auto_increment_increment=10;");
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
	public void update(LiveOrderVO LiveOrderVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setInt(1, LiveOrderVO.getTotal());
			pstmt.setInt(2, LiveOrderVO.getPay_status());
			pstmt.setInt(3, LiveOrderVO.getPay_way());
			pstmt.setInt(4, LiveOrderVO.getLiveno());
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
	public List<LiveOrderVO> getAll() {

		List<LiveOrderVO> list = new ArrayList<LiveOrderVO>();
		LiveOrderVO LiveOrderVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// LiveOrderVO 也稱為 Domain objects
				LiveOrderVO = new LiveOrderVO();
				LiveOrderVO.setLiveno(rs.getInt("liveno"));
				LiveOrderVO.setEmpno(rs.getInt("empno"));
				LiveOrderVO.setTableno(rs.getInt("tableno"));
				LiveOrderVO.setPay_status(rs.getInt("pay_status"));
				LiveOrderVO.setCreate_time(rs.getTimestamp("create_time"));
				LiveOrderVO.setTotal(rs.getInt("total"));
				LiveOrderVO.setPay_way(rs.getInt("pay_way"));
				list.add(LiveOrderVO); // Store the row in the list
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

	public LiveOrderVO findByTableNO(Integer tableno) {
		  
		  LiveOrderVO LiveOrderVO = null;
		  Connection con = null;
		  PreparedStatement pstmt = null;
		  ResultSet rs = null;
		  
		  try {
		   
		   con = ds.getConnection();
		   pstmt = con.prepareStatement(GET_UNCHECK_BY_TABLENO);
		   pstmt.setInt(1, tableno);   
		   rs = pstmt.executeQuery();
		   
		   while (rs.next()) {
		    // LiveOrderVO 也稱為 Domain objects
		    LiveOrderVO = new LiveOrderVO();
		    LiveOrderVO.setLiveno(rs.getInt("liveno"));
		    LiveOrderVO.setEmpno(rs.getInt("empno"));
		    LiveOrderVO.setTableno(rs.getInt("tableno"));
		    LiveOrderVO.setPay_status(rs.getInt("pay_status"));
		    LiveOrderVO.setCreate_time(rs.getTimestamp("create_time"));
		    LiveOrderVO.setTotal(rs.getInt("total"));
		    LiveOrderVO.setPay_way(rs.getInt("pay_way"));
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
		  return LiveOrderVO;
		 }
	
	
	@Override
	public LiveOrderVO findByTableNO(Integer tableno, Integer pay_status) {

		LiveOrderVO LiveOrderVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_BY_TABLENO);
			pstmt.setInt(1, tableno);
			pstmt.setInt(2, pay_status);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// LiveOrderVO 也稱為 Domain objects
				LiveOrderVO = new LiveOrderVO();
				LiveOrderVO.setLiveno(rs.getInt("liveno"));
				LiveOrderVO.setEmpno(rs.getInt("empno"));
				LiveOrderVO.setTableno(rs.getInt("tableno"));
				LiveOrderVO.setPay_status(rs.getInt("pay_status"));
				LiveOrderVO.setCreate_time(rs.getTimestamp("create_time"));
				LiveOrderVO.setTotal(rs.getInt("total"));
				LiveOrderVO.setPay_way(rs.getInt("pay_way"));
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
		return LiveOrderVO;
	}

	@Override
	public List<LiveOrderVO> findByPayStatus(Integer pay_status) {
		List<LiveOrderVO> list = new ArrayList<LiveOrderVO>();
		LiveOrderVO LiveOrderVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_BY_PAY_STATUS);
			pstmt.setInt(1, pay_status);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// LiveOrderVO 也稱為 Domain objects
				LiveOrderVO = new LiveOrderVO();
				LiveOrderVO.setLiveno(rs.getInt("liveno"));
				LiveOrderVO.setEmpno(rs.getInt("empno"));
				LiveOrderVO.setTableno(rs.getInt("tableno"));
				LiveOrderVO.setPay_status(rs.getInt("pay_status"));
				LiveOrderVO.setCreate_time(rs.getTimestamp("create_time"));
				LiveOrderVO.setTotal(rs.getInt("total"));
				LiveOrderVO.setPay_way(rs.getInt("pay_way"));
				list.add(LiveOrderVO);
			}

			// Handle any driver errors
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
	public Integer insertWithDetails(LiveOrderVO liveOrderVO, List<LiveDetailVO> list) {
		Connection con = null;
		PreparedStatement pstmt = null;
		Integer liveno = null;

		try {

			con = ds.getConnection();

			// 1●設定於 pstm.executeUpdate()之前
			con.setAutoCommit(false);

			// 先新增訂單
			String cols[] = { "LIVENO" };
			pstmt = con.prepareStatement(INSERT_STMT, cols);
			pstmt.setInt(1, liveOrderVO.getEmpno());
			if (!(liveOrderVO.getTableno() == null)) {
				pstmt.setInt(2, liveOrderVO.getTableno());
			} else {
				pstmt.setNull(2, java.sql.Types.INTEGER);
			}
			pstmt.setInt(3, liveOrderVO.getPay_status());
			pstmt.setTimestamp(4, liveOrderVO.getCreate_time());
			pstmt.setInt(5, liveOrderVO.getPay_way());
			pstmt.setInt(6, liveOrderVO.getTotal());

//pstmt.executeUpdate("set auto_increment_offset=10;");
//pstmt.executeUpdate("set auto_increment_increment=10;");
			pstmt.executeUpdate();

			// 掘取對應的自增主鍵值
			String next_liveno = null;
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				next_liveno = rs.getString(1);
				System.out.println("自增主鍵值= " + next_liveno + "(剛新增成功的部門編號)");
			} else {
				System.out.println("未取得自增主鍵值");
			}
			rs.close();
			// 再同時新增員工
			LiveDetailDAOJDBC dao = new LiveDetailDAOJDBC();
			System.out.println("list.size()-A=" + list.size());
			for (LiveDetailVO data : list) {
				data.setLiveno(new Integer(next_liveno));
				dao.insert2(data, con);
			}

			// 2●設定於 pstm.executeUpdate()之後
			con.commit();
			con.setAutoCommit(true);
			System.out.println("list.size()-B=" + list.size());
			System.out.println("新增部門編號" + next_liveno + "時,共有員工" + list.size() + "人同時被新增");
			liveno = new Integer(next_liveno);

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
		return liveno;
	}

	public LiveOrderVO findByPrimaryKey(Integer liveno) {

		LiveOrderVO LiveOrderVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_BY_PRIMARYKEY);
			pstmt.setInt(1, liveno);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// LiveOrderVO 也稱為 Domain objects
				LiveOrderVO = new LiveOrderVO();
				LiveOrderVO.setLiveno(rs.getInt("liveno"));
				LiveOrderVO.setEmpno(rs.getInt("empno"));
				LiveOrderVO.setTableno(rs.getInt("tableno"));
				LiveOrderVO.setPay_status(rs.getInt("pay_status"));
				LiveOrderVO.setCreate_time(rs.getTimestamp("create_time"));
				LiveOrderVO.setTotal(rs.getInt("total"));
				LiveOrderVO.setPay_way(rs.getInt("pay_way"));
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
		return LiveOrderVO;
	}

	@Override
	public List<LiveOrderVO> getAll(Map<String, String[]> map) {

		List<LiveOrderVO> list = new ArrayList<LiveOrderVO>();
		LiveOrderVO LiveOrderVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			String finalSQL = "select * from live_order " + jdbcUtil_CompositeQuery_LiveOrder.get_WhereCondition(map)
					+ "order by liveno";
			pstmt = con.prepareStatement(finalSQL);
			System.out.println("●●finalSQL(by DAO) = " + finalSQL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				LiveOrderVO = new LiveOrderVO();
				LiveOrderVO.setLiveno(rs.getInt("liveno"));
				LiveOrderVO.setEmpno(rs.getInt("empno"));
				LiveOrderVO.setTableno(rs.getInt("tableno"));
				LiveOrderVO.setPay_status(rs.getInt("pay_status"));
				LiveOrderVO.setCreate_time(rs.getTimestamp("create_time"));
				LiveOrderVO.setTotal(rs.getInt("total"));
				LiveOrderVO.setPay_way(rs.getInt("pay_way"));
				list.add(LiveOrderVO); // Store the row in the list
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

}
