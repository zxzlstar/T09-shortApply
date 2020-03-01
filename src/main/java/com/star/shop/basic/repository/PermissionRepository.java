package com.star.shop.basic.repository;

import com.star.shop.basic.entity.Permission;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 
 * 
 * <p>Title:PermissionRepository</p>
 *
 * <p>Description:</p>
 *
 * <p>Company:</p>
 *
 * @author x.zhang
 *
 * @date 2018年8月21日
 */
@Repository
public interface PermissionRepository extends BaseRepository<Permission, String> {
	public List<Permission> findByRoleId(String roleId) ;
	
	@Query(value = "select menuId from Permission where roleId = :roleId")
	public List<String> findMenuIdByRoleId(@Param("roleId") String roleId) ;
	
	public int deleteByRoleId(String roleId) ;
	
	public int deleteByMenuId(String menuId);
	
	@Query(value = "select menuId from Permission where roleId in (:roleIds)")
	public List<String> findMenuIdByRoleIdIn(@Param("roleIds") List<String> roleIds);
}
