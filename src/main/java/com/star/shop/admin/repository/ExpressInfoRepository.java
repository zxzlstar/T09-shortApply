/**
 * 
 */
package com.star.shop.admin.repository;

import com.star.shop.admin.entity.express.ExpressInfo;
import com.star.shop.basic.repository.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



/**
 * @author Cyan
 *
 */
@Repository
public interface ExpressInfoRepository extends BaseRepository<ExpressInfo, String> {

	@Modifying
	public void deleteByPid(@Param("pid") String pid);
}
