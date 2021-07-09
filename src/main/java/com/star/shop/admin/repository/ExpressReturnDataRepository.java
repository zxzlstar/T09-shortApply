/**
 * 
 */
package com.star.shop.admin.repository;

import com.star.shop.admin.entity.express.ExpressReturnData;
import com.star.shop.basic.repository.BaseRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;



/**
 * @author cyan
 *
 */
@Repository
@Transactional
public interface ExpressReturnDataRepository extends BaseRepository<ExpressReturnData, String> {

	
	public ExpressReturnData findByNu(String number);
	
}
