package com.star.shop.basic.repository;

import com.star.shop.basic.entity.Region;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 
 * 
 * <p>Title:RegionRepository</p>
 *
 * <p>Description:</p>
 *
 * <p>Company:</p>
 *
 * @author x.zhang
 *
 * @date 2018年8月23日
 */
@Repository
public interface RegionRepository extends BaseRepository<Region, String> {
	public List<Region> findByPcode(String pcode) ;
	
	public Region findByCode(String code);

}
