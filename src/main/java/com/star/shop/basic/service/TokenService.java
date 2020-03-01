package com.star.shop.basic.service;

import com.star.shop.basic.entity.Token;
import com.star.shop.basic.repository.BaseRepository;
import com.star.shop.basic.repository.TokenRepository;
import com.star.shop.basic.utils.Constants;
import com.star.shop.basic.utils.MD5Utils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * 
 * <p>Title:TokenService</p>
 *
 * <p>Description:</p>
 *
 * <p>Company:</p>
 *
 * @author x.zhang
 *
 * @date 2018年2月6日
 */
@Service
public class TokenService extends BaseService<Token, String> {
	private @Resource
	TokenRepository tokenRepository ;
	
	@Override
	protected BaseRepository<Token, String> getBaseRepository() {
		return this.tokenRepository ;
	}

	public Token createToken(String userId , String appid){
		Token token = new Token() ;
		token.setUserId(userId);
		token.setToken(MD5Utils.encode(userId + System.currentTimeMillis()));
		token.setAppid(appid);
		token.setExpireTime(System.currentTimeMillis() + Constants.TOKEN_EXPIRE_TIME);
		token.setState(0);
		token.setRemark("");
		
		return this.tokenRepository.save(token) ;
	}
	
	public Token findByToken(String token){
		return this.tokenRepository.findByToken(token) ;
	}
	
	public void overdue(String token) {
		Token tmp = this.tokenRepository.findByToken(token) ;
		tmp.setState(1);
		this.tokenRepository.save(tmp) ;
	}

}
