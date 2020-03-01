package com.star.shop.admin.repository;

import com.star.shop.admin.entity.Goods;
import com.star.shop.admin.entity.GoodsClassify;
import com.star.shop.basic.repository.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface GoodsRepository extends BaseRepository<Goods, String> {

	@Modifying
	@Query("update Goods a set a.isDelete = :isDelete  where a.id in :ids")
	public void removeGoods(@Param("isDelete") Integer isDelete, @Param("ids") String[] ids);
	
	@Modifying
	@Query("update Goods a set a.status = :status  where a.id in :ids")
	public void sellInOrOutGoods(@Param("status") String status, @Param("ids") String[] ids);
	
	@Modifying
	@Query("update Goods a set a.stick = :stick  where a.id in :ids")
	public void stickGoods(@Param("stick") Integer stick, @Param("ids") String[] ids);
	
	@Modifying
	@Query("update Goods a set a.recommend = :recommend  where a.id in :ids")
	public void recommendGoods(@Param("recommend") Integer recommend, @Param("ids") String[] ids);
	

	@Query("from Goods where id in :ids")
	public List<Goods> findByIds(@Param("ids") String[] ids);

	
	@Modifying
	@Query("update Goods a set a.stock = :stock where a.id = :id")
	public void updateStock(@Param("id") String id, @Param("stock") Integer stock);
	
	public List<Goods> findByClassify(@Param("classify") GoodsClassify goodscf);
	
	public Optional<Goods> findById(String id);

	public List<Goods> findByClassifyAndIsSpecificationAndIsDelete(@Param("classify") GoodsClassify goodscf, @Param("isSpecification") Integer isSpecification, @Param("isDelete") Integer isDelete);
}
