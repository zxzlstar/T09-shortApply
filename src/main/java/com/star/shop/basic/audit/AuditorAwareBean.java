package com.star.shop.basic.audit;

import com.star.shop.basic.entity.User;
import com.star.shop.basic.shiro.ShiroUtils;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;


/**
 * 
 * <p>Title:AuditorAwareBean</p>
 *
 * <p>Description:</p>
 *
 * <p>Company:</p>
 *
 * @author x.zhang
 *
 * @date 2018年9月11日
 */
@Component("auditorAwareBean")
public class AuditorAwareBean  implements AuditorAware<String>{

	@Override
	public Optional<String> getCurrentAuditor() {
		User user = ShiroUtils.getUser() ;
		if(user != null){
			return Optional.of(user.getId()) ;
		}
		
		return Optional.of("");
	}

}
