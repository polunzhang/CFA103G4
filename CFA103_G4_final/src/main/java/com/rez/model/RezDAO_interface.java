package com.rez.model;

import java.util.*;
import com.rez_time.model.*;

public interface RezDAO_interface {
	public void insert(RezVO rezVO);
	public void update(RezVO rezVO);
	public void delete(Integer rezno);
	public RezVO findByRezNO(Integer rezno);
	public List<RezVO> getAll();
}
