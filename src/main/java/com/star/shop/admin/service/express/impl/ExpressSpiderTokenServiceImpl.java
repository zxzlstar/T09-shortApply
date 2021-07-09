package com.star.shop.admin.service.express.impl;

import com.star.shop.admin.entity.express.ExpressSpiderToken;
import com.star.shop.admin.repository.ExpressSpiderTokenRepository;
import com.star.shop.admin.service.express.ExpressSpiderTokenService;
import com.star.shop.basic.repository.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author Cyan
 * @date 2021年1月19日
 */
public class ExpressSpiderTokenServiceImpl extends BaseServiceImpl<ExpressSpiderToken, Integer>
		implements ExpressSpiderTokenService {
	
	@Autowired
	private ExpressSpiderTokenRepository expressSpiderTokenRepository;

	@Override
	protected BaseRepository<ExpressSpiderToken, Integer> getBaseRepository() {
		// TODO Auto-generated method stub
		return this.expressSpiderTokenRepository;
	}



}
