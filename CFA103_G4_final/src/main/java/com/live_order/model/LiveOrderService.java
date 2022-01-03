package com.live_order.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.live_detail.model.LiveDetailService;
import com.live_detail.model.LiveDetailVO;

public class LiveOrderService {

	private LiveOrderDAO_interface dao;

	public LiveOrderService() {
		dao = new LiveOrderDAO();
	}

	public Integer addOrderWithDetail(Integer empno, Integer tableno, Integer pay_status, Integer total,
			Integer pay_way, List<LiveDetailVO> list) {
		LiveOrderVO LiveOrderVO = new LiveOrderVO();
		LiveOrderVO.setEmpno(empno);
		LiveOrderVO.setTableno(tableno);
		LiveOrderVO.setPay_status(pay_status);
		LiveOrderVO.setCreate_time(new Timestamp(System.currentTimeMillis()));
		LiveOrderVO.setTotal(total);
		LiveOrderVO.setPay_way(pay_way);
		Integer liveno = dao.insertWithDetails(LiveOrderVO, list);
		return liveno;
	}

	public LiveOrderVO updateOrder(Integer liveno, Integer empno, Integer tableno, Integer pay_status,
			Timestamp create_time, Integer total, Integer pay_way) {

		LiveOrderVO LiveOrderVO = new LiveOrderVO();

		LiveOrderVO.setLiveno(liveno);
		LiveOrderVO.setEmpno(empno);
		LiveOrderVO.setTableno(tableno);
		LiveOrderVO.setPay_status(pay_status);
		LiveOrderVO.setCreate_time(create_time);
		LiveOrderVO.setTotal(total);
		LiveOrderVO.setPay_way(pay_way);
		dao.insert(LiveOrderVO);
		return LiveOrderVO;
	}
	
	public LiveOrderVO updateOrder(LiveOrderVO liveorderVO) {
		
		dao.update(liveorderVO);
		return liveorderVO;
	}

	public LiveOrderVO getOneByTableNO(Integer tableno, Integer pay_status) {
		return dao.findByTableNO(tableno, pay_status);
	}

	public LiveOrderVO getOneByTableNO(Integer tableno) {
		return dao.findByTableNO(tableno);
	}
	
	public List<LiveOrderVO> getAll() {
		return dao.getAll();
	}

	public List<LiveOrderVO> getAll(String columName, String columValue) {
		// 配合 req.getParameterMap()方法 回傳
		// java.util.Map<java.lang.String,java.lang.String[]> 之測試
		Map<String, String[]> map = new TreeMap<String, String[]>();
		map.put(columName, new String[] { columValue });
		return dao.getAll(map);
	}

	public List<LiveOrderVO> getAll(Map<String, String[]> map) {
		return dao.getAll(map);
	}

	public List<LiveOrderVO> getAllUncheck() {
		Map<String, String[]> map = new TreeMap<String, String[]>();
		map.put("pay_status", new String[] { "0" });
		return dao.getAll(map);
	}

	public List<LiveOrderVO> getOneByPayStatus(Integer pay_status) {
		return dao.findByPayStatus(pay_status);
	}

	public LiveOrderVO getOneByLiveNO(Integer liveno) {
		return dao.findByPrimaryKey(liveno);
	}

	public List<LiveOrderVO> getAllUncooked() {
		Set<Integer> set = new LinkedHashSet<>();
		LiveDetailService LDSrv = new LiveDetailService();
		List<LiveDetailVO> liveDetailVOs = LDSrv.getAllUncooked();

		for (LiveDetailVO liveDetailVO : liveDetailVOs) {
			set.add(liveDetailVO.getLiveno());
		}

		List<LiveOrderVO> list = new ArrayList<>();
		for (Integer data : set) {
			list.add(dao.findByPrimaryKey(data));
		}
		return list;
	}

	public List<LiveOrderVO> getAllUnserved() {
		Set<Integer> set = new LinkedHashSet<>();
		LiveDetailService LDSrv = new LiveDetailService();
		List<LiveDetailVO> liveDetailVOs = LDSrv.getAllUnserved();
		List<LiveDetailVO> liveDetailVOs2 = LDSrv.getAllUncooked();

		for (LiveDetailVO liveDetailVO : liveDetailVOs) {
			set.add(liveDetailVO.getLiveno());
		}
		
		for (LiveDetailVO liveDetailVO : liveDetailVOs2) {
			set.add(liveDetailVO.getLiveno());
		}

		List<LiveOrderVO> list = new ArrayList<>();
		for (Integer data : set) {
			list.add(dao.findByPrimaryKey(data));
		}
		return list;
	}
}
