package com.star.shop.admin.repository;

import com.star.shop.admin.entity.GoodsClassify;
import com.star.shop.basic.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface GoodsClassifyRepository extends BaseRepository<GoodsClassify, String> {

	public List<GoodsClassify> findByPid(String pid) ;
	
	public List<GoodsClassify> findByPidOrderByRankDesc(String pid);
	
	public List<GoodsClassify> findByPathNotLike(String path);

	public Optional<GoodsClassify> findById(String id);
}
