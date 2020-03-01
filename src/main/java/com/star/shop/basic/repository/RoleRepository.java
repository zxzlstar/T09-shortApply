package com.star.shop.basic.repository;

import com.star.shop.basic.entity.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 
 * 
 * <p>Title:RoleRepository</p>
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
public interface RoleRepository extends BaseRepository<Role, String> {
	public Role findByRoleName(String roleName) ;
	
	public Role findByRoleSign(String roleSign) ;
	
	@Query(value = "select id from Role")
	public List<String> findIds();

}
