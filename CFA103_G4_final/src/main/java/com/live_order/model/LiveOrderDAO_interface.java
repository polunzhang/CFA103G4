package com.live_order.model;

import java.util.List;
import java.util.Map;

import com.live_detail.model.LiveDetailVO;

public interface LiveOrderDAO_interface {
    public void insert(LiveOrderVO LiveOrderVO);
    public void update(LiveOrderVO LiveOrderVO);
    //���έq��A�@��u�|�d��@�������b�q��
    public LiveOrderVO findByTableNO(Integer tableno , Integer pay_status);
    public LiveOrderVO findByTableNO(Integer tableno);
    public List<LiveOrderVO> findByPayStatus(Integer pay_status);
    public List<LiveOrderVO> getAll();
    public Integer insertWithDetails(LiveOrderVO liveOrderVO , List<LiveDetailVO> list);
    public LiveOrderVO findByPrimaryKey(Integer liveno);

    //�U�νƦX�d��(�ǤJ�Ѽƫ��AMap)(�^�� List)
    public List<LiveOrderVO> getAll(Map<String, String[]> map); 

}
