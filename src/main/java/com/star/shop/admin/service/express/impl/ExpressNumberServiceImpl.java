/**
 * 
 */
package com.star.shop.admin.service.express.impl;

import com.star.shop.admin.entity.express.ExpressNumber;
import com.star.shop.admin.repository.ExpressNumberRepository;
import com.star.shop.admin.service.express.ExpressNumberService;
import com.star.shop.basic.repository.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;



/**
 * @author cyan
 *
 */
@Service
@Transactional
public class ExpressNumberServiceImpl extends BaseServiceImpl<ExpressNumber, String> implements ExpressNumberService {
	
	@Autowired
	private ExpressNumberRepository expressNumberRepository;

	@Override
	protected BaseRepository<ExpressNumber, String> getBaseRepository() {
		// TODO Auto-generated method stub
		return this.expressNumberRepository;
	}

	@Override
	public List<String> findAllNumber(Integer status) {
		// TODO Auto-generated method stub
		return expressNumberRepository.findAllNumber(status);
	}


	
}
