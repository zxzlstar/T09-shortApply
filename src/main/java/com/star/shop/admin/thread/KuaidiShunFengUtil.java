package com.star.shop.admin.thread;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.star.shop.admin.entity.express.ExpressInfo;
import com.star.shop.admin.entity.express.ExpressReturnData;
import com.star.shop.admin.vo.express.KdShunFengSearchResultVo;
import com.star.shop.admin.vo.express.ShunFengLogisticVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 顺丰官网获取物流信息
 * @author Cyan
 * @date 2021年1月19日
 */
public class KuaidiShunFengUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(KuaidiShunFengUtil.class);
	
	private final String SHUNFENG_URL = "https://www.sf-express.com/sf-service-core-web/service/bills/SF1324287794272/routesForInput?lang=sc&region=cn&subMobile=";
	
		
	private String endNum; // 手机尾号
	
	public KuaidiShunFengUtil(String endNum) {
		super();
		this.endNum = endNum;
	}

	public ExpressReturnData call(String kuaidiNo) {
		String apiUrl = SHUNFENG_URL.concat(endNum).replace("SF1324287794272", kuaidiNo);
		if (Objects.nonNull(kuaidiNo) && kuaidiNo.startsWith("SF")) {
			String result = getSFExpressInfoByApiUrl(apiUrl);
			JSONObject parseObject = JSONObject.parseObject(result);
			if (Objects.nonNull(parseObject) && parseObject.getInteger("code") == 0) {
				JSONObject resultObj = parseObject.getJSONObject("result");
				if (Objects.nonNull(resultObj)) {
					JSONArray routeArray = resultObj.getJSONArray("routes");
					if (Objects.nonNull(routeArray) && routeArray.size() > 0) {
						KdShunFengSearchResultVo vo = routeArray.getObject(0, KdShunFengSearchResultVo.class);
						ExpressReturnData data = KdShunfengSearchVoTranferData(vo);
						return data;
					}
				}
			}
		}
		return null;
	}

	private ExpressReturnData KdShunfengSearchVoTranferData(KdShunFengSearchResultVo vo) {
		ExpressReturnData data = new ExpressReturnData();
		data.setCom("shunfeng");
		if ("1".equals(vo.getReceiveBillFlg()))
			data.setIscheck(1);
		data.setMessage("ok");
		data.setNu(vo.getId());
		data.setStatus(200);
		if (Objects.nonNull(vo.getRoutes()) && vo.getRoutes().size() > 0) {
			int lastIndex = vo.getRoutes().size() -1;
			String opCode = vo.getRoutes().get(lastIndex).getOpCode();
			if (Objects.nonNull(opCode)) {
				if (opCode.startsWith("5")) { // 已揽收
					data.setState(1);
				} else if (opCode.startsWith("3")) { // 运输中
					data.setState(0);
				} else if (opCode.startsWith("2")) { // 派送中
					data.setState(5);
				} else if (opCode.startsWith("8")) { // 已签收
					data.setState(3);
				}
			}
			
			List<ExpressInfo> infoList = new ArrayList<>();
			ExpressInfo info = null;
			for (ShunFengLogisticVo lo : vo.getRoutes()) {
				info = new ExpressInfo();
				info.setContext(lo.getRemark());
				info.setTime(lo.getScanDateTime());
				infoList.add(info);
			}
			data.setData(infoList);
		}
		return data;
	}

	private String getSFExpressInfoByApiUrl(String apiUrl) {
		try {
			// 请求url参数
			HttpGet httpGet = new HttpGet(apiUrl);
			// 执行请求
			HttpClient httpClient = HttpClients.createDefault();
			HttpResponse httpResponse = httpClient.execute(httpGet);
			httpResponse.setHeader(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8");
			if (Objects.isNull(httpResponse) && Objects.isNull(httpResponse.getEntity())) {
				return null;
			}
			String responseText = EntityUtils.toString(httpResponse.getEntity(), Charset.forName("utf-8"));
			if (StringUtils.isBlank(responseText)) {
				return null;
			}
			return responseText;
		} catch (Exception e) {
			logger.info("查询顺丰快递信息错误, apiUrl = {}, errorMsg: {} ", apiUrl, e);
		}
		return null;
	}

}
