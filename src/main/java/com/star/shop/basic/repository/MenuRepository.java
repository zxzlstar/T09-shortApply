package com.star.shop.basic.repository;

import com.star.shop.basic.entity.Menu;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


/**
 * 
 * 
 * <p>Title:MenuRepository</p>
 *
 * <p>Description:</p>
 *
 * <p>Company:</p>
 *
 * @author x.zhang
 *
 * @date 2018年8月17日
 */
@Repository
public interface MenuRepository extends BaseRepository<Menu, String> {
	public List<Menu> findByType(Integer type) ;
	
	public List<Menu> findByTypeAndIdIn(Integer type, List<String> id) ;
	
	public List<Menu> findByPid(String pid) ;
	
	public List<Menu> findByPidAndIdIn(String pid, List<String> id) ;
	
	@Query(value = "select perms from Menu")
	public Set<String> findAllPerms() ;
	
	/**
	 * 
	 * @param ids
	 * @return
	 */
	@Query(value = "select perms from Menu where id in (:ids)")
	public Set<String> findByIdIn(@Param("ids") List<String> ids);

}
