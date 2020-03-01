package com.star.shop.basic.repository;

import com.star.shop.basic.entity.Token;
import org.springframework.stereotype.Repository;



/**
 * 
 * <p>Title:TokenRepository</p>
 *
 * <p>Description:</p>
 *
 * <p>Company:</p>
 *
 * @author x.zhang
 *
 * @date 2018年2月6日
 */
@Repository
public interface TokenRepository extends BaseRepository<Token, String> {
	public Token findByToken(String token) ;
}
