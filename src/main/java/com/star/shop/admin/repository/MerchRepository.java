package com.star.shop.admin.repository;

import com.star.shop.admin.entity.Merch;
import com.star.shop.basic.repository.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface MerchRepository extends BaseRepository<Merch, String> {

	public Merch findByNumber(String number);
	
	public Optional<Merch> findById(String id);
	
	public List<Merch> findByType(Integer type);

	public Merch findByMobile(String mobile);
	
	public Merch findByMerchName(String merch);

	public List<Merch> findByCheckStatus(Integer checkStatus);

	@Query("select m from Merch m where status <> :status and type = :type and checkStatus = :checkStatus")
	public List<Merch> findMerchForApi(Integer status, Integer type, Integer checkStatus);
	
	@Query("select id from Merch where merchName like :merchName")
	public List<String> findListIdByMerchName(@Param("merchName") String merchName);
	
	public Merch findByWxOpenid(String wxOpenid) ;
	
	@Modifying
	@Query("update Merch a set a.video = :video  where a.id = :id")
	public void modifyVideo(@Param("id") String id, @Param("video") String video);
	
	@Modifying
	@Query("update Merch a set a.rank = :rank  where a.id = :id")
	public void modifyRank(@Param("id") String id, @Param("rank") Integer rank);
	
	@Modifying
	@Query("update Merch a set a.isPopular = :isPopular  where a.id in :ids")
	public void modifyPopular(@Param("ids") String[] ids, @Param("isPopular") Integer isPopular);
}
