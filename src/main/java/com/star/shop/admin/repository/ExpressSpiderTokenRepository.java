package com.star.shop.admin.repository;


import com.star.shop.admin.entity.express.ExpressSpiderToken;
import com.star.shop.basic.repository.BaseRepository;

/**
 * @author Cyan
 * @date 2021年1月19日
 */
public interface ExpressSpiderTokenRepository extends BaseRepository<ExpressSpiderToken, Integer> {
	
	public ExpressSpiderToken findFirstByOrderByUpdateTimeDesc();
}
