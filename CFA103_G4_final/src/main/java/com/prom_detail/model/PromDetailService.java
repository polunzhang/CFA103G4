package com.prom_detail.model;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import com.meal.model.MealService;
import com.meal.model.MealVO;
import com.prom.model.PromDAO_interface;
import com.prom.model.PromJDBCDAO;
import com.prom.model.PromVO;

public class PromDetailService {
	private PromDetailDAO_interface dao;

	public PromDetailService() {
		dao = new PromDetailJDBCDAO();
	}

	public PromDetailVO addPromDetail(Integer promno, Integer mealno, Double prom_value,
			Date prom_time_start,Date prom_time_end,Integer prom_state) {

		PromDetailVO promdetailvo = new PromDetailVO();

		promdetailvo.setPromno(promno);
		promdetailvo.setMealno(mealno);
		promdetailvo.setProm_value(prom_value);
		promdetailvo.setProm_time_start(prom_time_start);
		promdetailvo.setProm_time_end(prom_time_end);
		promdetailvo.setProm_state(prom_state);
		dao.insert(promdetailvo);

		return promdetailvo;
	}

	public PromDetailVO updatePromDetail(Integer mealno, Double prom_value,
			Date prom_time_start,Date prom_time_end,Integer prom_state,Integer promno,Integer mealno2) {

		PromDetailVO promdetailvo = new PromDetailVO();

		promdetailvo.setPromno(promno);
		promdetailvo.setMealno(mealno);
		promdetailvo.setProm_value(prom_value);
		promdetailvo.setProm_time_start(prom_time_start);
		promdetailvo.setProm_time_end(prom_time_end);
		promdetailvo.setProm_state(prom_state);
		dao.update(promdetailvo);

		return promdetailvo;
	}

	public void deletePromDetail(Integer promno,Integer mealno) {
		PromDetailVO promdetailvo = new PromDetailVO();
		promdetailvo.setPromno(promno);
		promdetailvo.setMealno(mealno);
		dao.delete(promdetailvo);
	}

	public List<PromDetailVO> getOnepromDetail(Integer promno) {
		PromDetailVO promdetailvo = new PromDetailVO();
		promdetailvo.setPromno(promno);
		
		
		return dao.findByPrimaryKey(promno);
	}
	
	public List<PromDetailVO> FindByMealNo(Integer mealno) {
		
		PromDetailVO promdetailvo = new PromDetailVO();
		promdetailvo.setMealno(mealno);
		dao.findByMealNo(mealno);
		
		return dao.findByMealNo(mealno);
	}
	
	

	public List<PromDetailVO> getAll() {
		return dao.getAll();
	}

//	訂單(Order)所需，傳入餐點編號後回傳促銷價格之方法
	
	public Integer checkMPByMn(Integer mealnoFromLO) {
//		假想，若有多套促銷適用，僅可適用一套促銷，會選擇套用促銷後價格最低之方案予消費者
//		new 一個promdetail物件並以傳入之mealno查找促銷活動
		PromDetailService PDSVC = new PromDetailService();
		List<PromDetailVO> pdlist = PDSVC.FindByMealNo(mealnoFromLO);
//		System.out.println(pdlist.size());
//		new一個mealSVC物件，並使用getOneMeal方法查找傳入餐點號碼之餐點資訊，並在foreach內每次迭代前取得原餐點價格
		MealService MSVC = new MealService();
		MealVO mealvo = MSVC.getOneMeal(mealnoFromLO);
//		new 一個陣列，裝下從各迭代內取出並比較過大小的折後價格，以方便進行比大小(ARRAY.sort)
		Integer comparArray[] = new Integer[pdlist.size()];
//		comparArray[0]=1;
//		comparArray[1]=1;
//		System.out.println(comparArray[1]);
		Integer no=0;
		Integer mealPriceReturn=null;
		
//		判斷是否有相關促銷，若無則回傳null，若有則續102行迭代取出資料
		if(pdlist.size()<=0) {
			return null;
		}else if(pdlist.size()>0) {
//		若有相關促銷，以迭代方式取出，判斷prom_state	
		for(PromDetailVO promdetailvo:pdlist) {
			Integer returnmealprice = mealvo.getMeal_price();
			System.out.println("原餐點價格="+returnmealprice);
							
			if(promdetailvo.getProm_state()==0) {
				returnmealprice=(int) (returnmealprice-(promdetailvo.getProm_value()));
				comparArray[no]=returnmealprice;
				
			}else if(promdetailvo.getProm_state()==1) {
				promdetailvo.getProm_value();
				returnmealprice=(int) (returnmealprice*(promdetailvo.getProm_value()));
				comparArray[no]=returnmealprice;
				
			}else if(promdetailvo.getProm_state()<0) {
				comparArray[no]=0;
				return null;
			}
			no++;
			}
		Arrays.sort(comparArray);
		mealPriceReturn=comparArray[0];
		}
		return mealPriceReturn;
	}
	
//	以main方法測試
	public static void main(String[] args) {
		
		PromDetailService PDSVC = new PromDetailService();
		Integer mealno=3;
		Integer mealPrice= PDSVC.checkMPByMn(mealno);
		System.out.println("折扣後餐點價格="+mealPrice);
		
	}
	
	
	
	
	
	
	
	
	
}