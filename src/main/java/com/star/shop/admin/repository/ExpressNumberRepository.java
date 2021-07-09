/**
 * 
 */
package com.star.shop.admin.repository;

import java.util.List;

import com.star.shop.admin.entity.express.ExpressNumber;
import com.star.shop.basic.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * @author cyan
 *
 */
@Repository
public interface ExpressNumberRepository extends BaseRepository<ExpressNumber, String> {

	@Query(value = "select number from ExpressNumber where status = :status")
	public List<String> findAllNumber(@Param("status") Integer status) ;
	
	@Query(value = "from ExpressNumber where number = :number")
	public ExpressNumber getOneByNumber(@Param("number") String number);
	
	public ExpressNumber findFirstByOrderByOperationTimeDesc();
}
