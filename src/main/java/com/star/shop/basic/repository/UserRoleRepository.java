package com.star.shop.basic.repository;

import com.star.shop.basic.entity.UserRole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 
 * 
 * <p>Title:UserRoleRepository</p>
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
public interface UserRoleRepository extends BaseRepository<UserRole, String> {
	@Query(value = "select roleId from UserRole where userId = :userId")
	public List<String> findRoleIdByUserId(@Param("userId") String userId) ;
	
	public List<UserRole> findByUserId(String userId) ;
	
	public int deleteByUserId(String userId) ;
	
	public int deleteByRoleId(String roleId);
}
