package com.star.shop.basic.repository;

import com.star.shop.basic.entity.Upload;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


/**
 * 
 * 
 * <p>Title:UploadRepository</p>
 *
 * <p>Description:</p>
 *
 * <p>Company:</p>
 *
 * @author x.zhang
 *
 * @date 2018年8月28日
 */
@Repository
public interface UploadRepository extends BaseRepository<Upload, String> {

	@Modifying
	@Transactional
	@Query(value="DELETE FROM t_upload WHERE id IN (SELECT advertising FROM t_member WHERE advertising != '')", nativeQuery = true)
	public void deleteAdvertise();
	
}
