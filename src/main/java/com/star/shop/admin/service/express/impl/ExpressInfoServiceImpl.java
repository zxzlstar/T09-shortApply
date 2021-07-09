/**
 * 
 */
package com.star.shop.admin.service.express.impl;

import com.star.shop.admin.entity.express.ExpressInfo;
import com.star.shop.admin.repository.ExpressInfoRepository;
import com.star.shop.admin.service.express.ExpressInfoService;
import com.star.shop.basic.repository.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author cyan
 *
 */
@Service
@Transactional
public class ExpressInfoServiceImpl extends BaseServiceImpl<ExpressInfo, String> implements ExpressInfoService {

	@Autowired
	private ExpressInfoRepository expressInfoRepository;
	
	@Override
	protected BaseRepository<ExpressInfo, String> getBaseRepository() {
		return this.expressInfoRepository;
	}


}
