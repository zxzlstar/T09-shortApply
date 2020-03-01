package com.star.shop.basic.repository;

import com.star.shop.basic.entity.SerialNumber;
import org.springframework.stereotype.Repository;


/**
 * 
 * 
 * <p>Title:SerialNumberRepository</p>
 *
 * <p>Description:</p>
 *
 * <p>Company:</p>
 *
 * @author x.zhang
 *
 * @date 2018年1月29日
 */
@Repository
public interface SerialNumberRepository extends BaseRepository<SerialNumber, String> {
	public SerialNumber findByServiceCodeAndDate(String serviceCode, String date) ;
}
