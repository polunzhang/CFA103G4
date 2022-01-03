package com.online_order.model;

import java.util.List;
import java.util.Map;

import com.online_detail.model.OnlineDetailVO;

public interface OnlineOrderDAO_interface {
	public void insert(OnlineOrderVO onlineOrderVO);
	public void update(OnlineOrderVO onlineOrderVO);
	public void delete(Integer olno);
	public OnlineOrderVO findByOlno(Integer olno);//�̷ӭq��s���d��
	public List<OnlineOrderVO> findByPayStatus(Integer pay_status);//�ȤH���\�ɬd�ߵ��b���A�C
	public List<OnlineOrderVO> getAll();
	public List<OnlineOrderVO> findByAddress();
	public List<OnlineOrderVO> findByAddress2();
	public List<OnlineOrderVO> findByMemno(Integer memno);
	
	
	
	//�P�ɷs�W�q��D�ɩM�q�����(�ۼW�D�����)
	public Integer insertWithDetails(OnlineOrderVO onlineOrderVO, List<OnlineDetailVO> list);
	
	//�U�νƦX�d��(�ǤJ�Ѽƫ��AMap)(�^�� List)
    public List<OnlineOrderVO> getAll(Map<String, String[]> map); 
}
