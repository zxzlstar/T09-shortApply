package com.star.shop.admin.repository;

import java.util.List;

import com.star.shop.admin.entity.Banner;
import com.star.shop.basic.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface BannerRepository extends BaseRepository<Banner, String> {

	@Query(value = "select image from Banner where id = :id")
	public String findImageById(@Param("id") String id) ;
	
	public List<Banner> findByPositionOrderByRankDesc(@Param("position") Integer position);
}
