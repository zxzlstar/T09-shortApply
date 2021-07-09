/**
 * 
 */
package com.star.shop.admin.service.express;

import com.star.shop.admin.entity.express.ExpressNumber;

import java.util.List;


/**
 * @author cyan
 *
 */
public interface ExpressNumberService extends BaseService<ExpressNumber, String> {
	
	public List<String> findAllNumber(Integer status);
	
}
