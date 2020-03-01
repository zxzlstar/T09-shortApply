package com.star.shop.basic.repository;

import com.star.shop.basic.entity.User;
import org.springframework.stereotype.Repository;


/**
 * 
 * 
 * <p>Title:UserRepository</p>
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
public interface UserRepository extends BaseRepository<User, String> {
	public User findByUsername(String username) ;
}
