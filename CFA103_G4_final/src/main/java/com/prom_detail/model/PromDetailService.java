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

//	�q��(Order)�һݡA�ǤJ�\�I�s����^�ǫP�P���椧��k
	
	public Integer checkMPByMn(Integer mealnoFromLO) {
//		���Q�A�Y���h�M�P�P�A�ΡA�ȥi�A�Τ@�M�P�P�A�|��ܮM�ΫP�P�����̧C����פ����O��
//		new �@��promdetail����åH�ǤJ��mealno�d��P�P����
		PromDetailService PDSVC = new PromDetailService();
		List<PromDetailVO> pdlist = PDSVC.FindByMealNo(mealnoFromLO);
//		System.out.println(pdlist.size());
//		new�@��mealSVC����A�èϥ�getOneMeal��k�d��ǤJ�\�I���X���\�I��T�A�æbforeach���C�����N�e���o���\�I����
		MealService MSVC = new MealService();
		MealVO mealvo = MSVC.getOneMeal(mealnoFromLO);
//		new �@�Ӱ}�C�A�ˤU�q�U���N�����X�ä���L�j�p��������A�H��K�i���j�p(ARRAY.sort)
		Integer comparArray[] = new Integer[pdlist.size()];
//		comparArray[0]=1;
//		comparArray[1]=1;
//		System.out.println(comparArray[1]);
		Integer no=0;
		Integer mealPriceReturn=null;
		
//		�P�_�O�_�������P�P�A�Y�L�h�^��null�A�Y���h��102�歡�N���X���
		if(pdlist.size()<=0) {
			return null;
		}else if(pdlist.size()>0) {
//		�Y�������P�P�A�H���N�覡���X�A�P�_prom_state	
		for(PromDetailVO promdetailvo:pdlist) {
			Integer returnmealprice = mealvo.getMeal_price();
			System.out.println("���\�I����="+returnmealprice);
							
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
	
//	�Hmain��k����
	public static void main(String[] args) {
		
		PromDetailService PDSVC = new PromDetailService();
		Integer mealno=3;
		Integer mealPrice= PDSVC.checkMPByMn(mealno);
		System.out.println("�馩���\�I����="+mealPrice);
		
	}
	
	
	
	
	
	
	
	
	
}