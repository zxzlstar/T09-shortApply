package com.star.shop.basic.interceptor;

import com.alibaba.fastjson.JSON;
import com.star.shop.basic.entity.Token;
import com.star.shop.basic.service.TokenService;
import com.star.shop.basic.utils.Constants;
import com.star.shop.basic.utils.RedisUtil;
import com.star.shop.basic.utils.ThreadLocalUtil;
import com.star.shop.basic.vo.ResultVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * <p>
 * Title:ApiInterceptor
 * </p>
 *
 * <p>
 * Description:api接口的拦截器
 * </p>
 *
 * <p>
 * Company:
 * </p>
 *
 *
 * @author x.zhang
 *
 * @date 2018年12月10日
 */
@Component
public class ApiInterceptor implements HandlerInterceptor {
	private final static Logger logger = LoggerFactory.getLogger(ApiInterceptor.class);

	private @Resource
	TokenService tokenService;

	@Autowired
	private RedisUtil redisUtil;
	/**
	 * 不需要登录的URL列表
	 */
	private List<String> notLoginUrlList;

	private List<String> noCheckUrlList ;
	
	private Map<String, String> appidMap;
	
	public ApiInterceptor() {
		appidMap = new HashMap<>() ;
		appidMap.put("34bb23a830193824fef6e8e2cae0c65c", Constants.SYSTEM_WECHAT) ;//h5端
		appidMap.put("4028819c5de3c938015de3ca929dre01", Constants.SYSTEM_MERCH) ; //商户端
		notLoginUrlList = new ArrayList<>() ;
		notLoginUrlList.add("/api/wxlogin") ;
		notLoginUrlList.add("/api/merch/login") ;
		notLoginUrlList.add("/api/merch/register");
		notLoginUrlList.add("/api/scancode/*");
		notLoginUrlList.add("/api/merch/infoBywxoi");
		
		noCheckUrlList =  new ArrayList<>() ;
		noCheckUrlList.add("/api/barcode") ;
		noCheckUrlList.add("/api/uploadimgs") ; //文件上传，不需要检验，直接跨域上传
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String url = request.getRequestURI();
		// 对获取时间接口不校验
		if (url.equals("/api/getsystemtime") || url.equals("/api/wxpay/notify")) {
			return true;
		}
		//测试
//		if(StringUtils.hasText(url)){
//			return true ;
//		}

		if(!this.exclusive(url, noCheckUrlList)) {
			return true;
		}
		
		String appid = request.getHeader("appid");
		String time = request.getHeader("time");
		String token = request.getHeader("token");

		logger.info("请求URL：{},appid={},time={},token={}" , url,appid,time,token);
		ThreadLocalUtil.remove();

		if (StringUtils.isEmpty(appid) || StringUtils.isEmpty(time)) {
			this.writeError(response, new ResultVo(401, "非法请求"));
			return false;
		}

		// 请求时间不能超过24小时
		if (Long.parseLong(time) < System.currentTimeMillis() - 24 * 3600 * 1000
				|| Long.parseLong(time) > System.currentTimeMillis() + 24 * 3600 * 1000) {
			this.writeError(response,  new ResultVo(401, "非法请求"));
			return false;
		}

		// 还需要判断APPID是否存在
		if (!appidMap.keySet().contains(appid)) {
			this.writeError(response,  new ResultVo(401, "非法请求"));
			return false;
		}

		// 如果是需要登录则检验token是否存在
		if (this.exclusive(url)) {
			if (StringUtils.isEmpty(token)) {
				this.writeError(response,  new ResultVo(402, "token为空或不存在"));
				return false;
			} else {
				// 查找token
				Token entry = (Token)redisUtil.get(token);
				if (entry == null) {
					this.writeError(response,  new ResultVo(402, "token为空或不存在"));
					return false;
				}
				/*Token entry = this.tokenService.findByToken(token);
				if (entry == null) {
					this.writeError(response,  new ResultVo(402, "token为空或不存在"));
					return false;
				} else {
					if (entry.getState() == 0) {
						if (entry.getExpireTime() < System.currentTimeMillis()) {
							// 已经过期
							this.tokenService.overdue(token);
							this.writeError(response,  new ResultVo(403, "Token过期"));
							return false;
						}
					} else {
						// 已经过期
						this.writeError(response, new ResultVo(403, "Token过期"));
						return false;
					}
				}*/
				if(Constants.SYSTEM_WECHAT.equals(appidMap.get(entry.getAppid()))) {
					// 会员ID
					ThreadLocalUtil.set(Constants.MEMBER_ID, entry.getUserId());
				}else if(Constants.SYSTEM_MERCH.equals(appidMap.get(entry.getAppid()))) {
					// 商户ID
					ThreadLocalUtil.set(Constants.MERCH_ID, entry.getUserId());
				}
			}
		}
		ThreadLocalUtil.set(Constants.APP_ID , appid);
		ThreadLocalUtil.set(Constants.SYSTEM_NAME ,appidMap.get(appid));
		return true;
	}

	protected void writeError(HttpServletResponse response, ResultVo resultVo) throws Exception {
		String res = JSON.toJSONString(resultVo);

		Writer writer = null;
		try {
			res = (null == res ? "" : res);
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-type", "text/html;charset=UTF-8");
			writer = response.getWriter();
			// logger.debug("输出JSON字符串："+res);
			writer.write(res);
		} catch (IOException e) {
			// logger.error("输出JSON字符串异常");
			throw new Exception("write json string error");
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					logger.error("关闭输出流异常,无法关闭会导致内存溢出");
				}
			}
		}
	}

	private boolean exclusive(String url) {
		for (String value : notLoginUrlList) {
			if (value.endsWith("*")) {
				if (url.startsWith(value.substring(0, value.length() - 1))) {
					return false;
				}
			} else if (value.equals(url)) {
				return false;
			}
		}
		return true;
	}
	
	private boolean exclusive(String url , List<String> list) {
		for (String value : list) {
			if (value.endsWith("*")) {
				if (url.startsWith(value.substring(0, value.length() - 1))) {
					return false;
				}
			} else if (value.equals(url)) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

	public List<String> getNotLoginUrlList() {
		return notLoginUrlList;
	}

	public void setNotLoginUrlList(List<String> notLoginUrlList) {
		this.notLoginUrlList = notLoginUrlList;
	}

	public TokenService getTokenService() {
		return tokenService;
	}

	public void setTokenService(TokenService tokenService) {
		this.tokenService = tokenService;
	}

	public Map<String, String> getAppidMap() {
		return appidMap;
	}

	public void setAppidMap(Map<String, String> appidMap) {
		this.appidMap = appidMap;
	}

}
