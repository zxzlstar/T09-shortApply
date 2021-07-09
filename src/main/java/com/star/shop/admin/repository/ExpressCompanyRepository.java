/**
 * 
 */
package com.star.shop.admin.repository;

import com.star.shop.admin.entity.express.ExpressCompany;
import com.star.shop.admin.entity.express.ExpressNumber;
import com.star.shop.basic.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author cyan
 *
 */
@Repository
public interface ExpressCompanyRepository extends BaseRepository<ExpressCompany, Integer> {

}
