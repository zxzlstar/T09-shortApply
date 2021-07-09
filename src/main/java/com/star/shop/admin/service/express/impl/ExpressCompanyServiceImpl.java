/**
 * 
 */
package com.star.shop.admin.service.express.impl;

import com.star.shop.admin.entity.express.ExpressCompany;
import com.star.shop.admin.entity.express.ExpressNumber;
import com.star.shop.admin.repository.ExpressCompanyRepository;
import com.star.shop.admin.repository.ExpressNumberRepository;
import com.star.shop.admin.service.express.ExpressCompanyService;
import com.star.shop.admin.service.express.ExpressNumberService;
import com.star.shop.basic.repository.BaseRepository;
import com.star.shop.basic.utils.RedisUtil;
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
public class ExpressCompanyServiceImpl extends BaseServiceImpl<ExpressCompany, Integer> implements ExpressCompanyService {
	
	@Autowired
	private ExpressCompanyRepository expressCompanyRepository;

	@Override
	protected BaseRepository<ExpressCompany, Integer> getBaseRepository() {
		// TODO Auto-generated method stub
		return this.expressCompanyRepository;
	}

	@Override
	public List<ExpressCompany> getExpressCompanyList() {
		List<ExpressCompany> list = expressCompanyRepository.findAll();
		return list;
	}
}
