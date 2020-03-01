package com.star.shop.admin.repository;

import com.star.shop.admin.entity.GoodsSpecification;
import com.star.shop.basic.repository.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface GoodsSpecificationRepository extends BaseRepository<GoodsSpecification, String> {
	
	@Query("from GoodsSpecification a where a.goodsId = :goodsId and a.status = :status")
	public List<GoodsSpecification> findByGoodsIdAndStatus(@Param("goodsId") String goodsId, @Param("status") Integer status);
	
	public Optional<GoodsSpecification> findById(String id);
	
	@Modifying
	@Query("update GoodsSpecification a set a.stock = :stock where a.id = :id")
	public void updateStock(@Param("id") String id, @Param("stock") Integer stock);

	@Modifying
	public void deleteByGoodsId(String goodsId);
}
