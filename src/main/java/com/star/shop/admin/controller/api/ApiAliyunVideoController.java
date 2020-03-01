package com.star.shop.admin.controller.api;

import com.star.shop.admin.vo.ResponseVo;
import com.star.shop.basic.http.HttpRequest;
import com.star.shop.basic.utils.Constants;
import com.star.shop.basic.utils.MD5Utils;
import com.star.shop.basic.vo.ResultVo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@Controller
@RequestMapping(value="/api/aliyun")
public class ApiAliyunVideoController {

	@Value("${aliyun.server.url}")
	private String baseUrl;
	
	/**
	 * 播放阿里云视频
	 * @param params
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/play", method= {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ResultVo play(@RequestParam Map<String, String> params, HttpServletRequest request) {
		String url = baseUrl+"/api/aliyun/play?videoId="+params.get("videoId");
		String time = String.valueOf(System.currentTimeMillis());
		String token = MD5Utils.encode(Constants.APPID+ Constants.KEY + time);
		String response = HttpRequest.getInstance().getSendHttp(url , token, time);
		ResponseVo s = ResponseVo.s(response);
		if(s.getCode() == 200) {
			return ResultVo.e(200, "成功", s.getData());
		}
		return ResultVo.e(500, "后台错误") ;
	}
	
	/**
	 * 播放阿里云视频
	 * @param params
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/playonmobile", method= {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ResultVo playV(@RequestParam Map<String, String> params, HttpServletRequest request) {
		String url = baseUrl+"/api/aliyun/playonmobile?videoId="+params.get("videoId");
		String time = String.valueOf(System.currentTimeMillis());
		String token = MD5Utils.encode(Constants.APPID+ Constants.KEY + time);
		String response = HttpRequest.getInstance().getSendHttp(url , token, time);
		ResponseVo s = ResponseVo.ss(response);
		if(s!=null) {
			return ResultVo.e(200, "成功", s.getData());
		}
		return ResultVo.e(500, "后台错误") ;
	}
	
	/**
	 * 上传阿里云视频
	 * @param params
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/upload", method= {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ResultVo upload(@RequestParam Map<String, String> params, HttpServletRequest request) {
		String fileName = params.get("fileName");
		String title = params.get("title");
		String url = baseUrl+"/api/aliyun/upload?title=" +title + "&fileName=" + fileName;
		String time = String.valueOf(System.currentTimeMillis());
		String token = MD5Utils.encode(Constants.APPID+ Constants.KEY + time);
		String response = HttpRequest.getInstance().getSendHttp(url , token, time);
		
		ResponseVo s = ResponseVo.s(response);
		if(s.getCode() == 200) {
			return ResultVo.e(200, "成功", s.getData());
		}
		return ResultVo.e(500, "后台错误") ;
	}
}
