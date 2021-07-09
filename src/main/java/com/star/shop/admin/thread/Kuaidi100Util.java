package com.star.shop.admin.thread;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Objects;

import com.star.shop.admin.entity.express.ExpressReturnData;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 快递查询工具类
 *
 * @author cyan
 */
public class Kuaidi100Util {

	private static final Logger logger = LoggerFactory.getLogger(Kuaidi100Util.class);
	
//	private static String templateNo = "0.860779312047020";

	/**
	 * 根据 快递单号 自动获取快递公司的 URL
	 */
	private static final String AUTO_SEARCH_COMPANY_URL = "https://www.kuaidi100.com/autonumber/autoComNum";

	/**
	 * 根据 快递公司编码 和 快递单号 查询物流信息 的 URL
	 */
	private static final String SEARCH_EXPRESS_INFO_URL = "https://www.kuaidi100.com/query";

	public Kuaidi100Util() {
		super();
	}

	/**
	 * 根据 【快递单号】 获取 【快递公司】 (90% 的正确率) (由于接口返回的快递公司有时候会有多个, 第一个概率是最高的, 就直接返回第一家公司的)
	 *
	 * @param number
	 *            快递单号
	 * @return 快递公司
	 */
	public static String getCompanyCodeByNumber(String number) {
		if (StringUtils.isBlank(number)) {
			return null;
		}
		try {
			HttpPost httpPost = new HttpPost(AUTO_SEARCH_COMPANY_URL);
			// 必须携带 User-Agent 请求头信息 (参数是直接从浏览器请求中 copy 的)
			httpPost.setHeader(HttpHeaders.USER_AGENT,
					"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36");
			// 请求参数
			ArrayList<BasicNameValuePair> parameters = new ArrayList<>();
			// parameters.add(new BasicNameValuePair("resultv2", "1"));
			parameters.add(new BasicNameValuePair("text", number));
			HttpEntity httpEntity = new UrlEncodedFormEntity(parameters);
			httpPost.setEntity(httpEntity);

			HttpClient httpClient = HttpClients.createDefault();
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if (null == httpResponse) {
				return null;
			}
			if (null == httpResponse.getEntity()) {
				return null;
			}
			String responseText = EntityUtils.toString(httpResponse.getEntity(), Charset.forName("utf-8"));
			if (StringUtils.isBlank(responseText)) {
				return null;
			}
			JSONObject resultObject = JSONObject.parseObject(responseText);
			if (null == resultObject) {
				return null;
			}
			// 请求无效, 被阻拦了
			if ("301".equals(resultObject.get("returnCode"))) {
				logger.info("请求被拦截, number = [{}]", number);
				return null;
			}
			// 没有查询到相关的快递公司, 该情况为 快递单号 无效
			if ("[]".equals(String.valueOf(resultObject.get("auto")))) {
				logger.info("快递单号无效, number = [{}]", number);
				return null;
			}
			// 获取快递公司列表
			JSONArray jsonArray = JSONArray.parseArray(String.valueOf(resultObject.get("auto")));
			// 获取第一个
			JSONObject jsonObject = JSONObject.parseObject(String.valueOf(jsonArray.get(0)));
			return jsonObject.getString("comCode");
		} catch (Exception e) {
			logger.info("查询快递公司错误, number = {}, errorMsg: {} ", number, e);
		}
		return null;
	}

	/**
	 * 根据 快递公司编码 和 快递单号 查询物流信息
	 *
	 * @param code
	 *            快递公司编码
	 * @param number
	 *            快递单号
	 * @return 快递物流信息
	 */
	public static ExpressReturnData getExpressInfoByCodeAndNumber(String code, String number) {
		if (StringUtils.isBlank(code) || StringUtils.isBlank(number)) {
			return null;
		}
		try {
			HttpPost httpPost = new HttpPost(SEARCH_EXPRESS_INFO_URL);

			// 请求头信息
			httpPost.setHeader(HttpHeaders.USER_AGENT,
					"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36");
			httpPost.setHeader(HttpHeaders.COOKIE,
					"csrftoken=rq0RJ1s5Me6iLVBgEKr1z6zYXURo3QyiTth9vVgq; WWWID=WWW9AA328B0FD91F0096AEAD0A533959B31; Hm_lvt_22ea01af58ba2be0fec7c11b25e88e6c=1578103600,1578104194,1578105292,1578105357; Hm_lpvt_22ea01af58ba2be0fec7c11b25e88e6c=1578106770");
			httpPost.setHeader(HttpHeaders.HOST, "www.kuaidi100.com");
			httpPost.setHeader(HttpHeaders.ORIGIN, "https://www.kuaidi100.com");
			httpPost.setHeader(HttpHeaders.REFERER, "https://www.kuaidi100.com/result.jsp?nu=" + number);

			// 请求参数
			ArrayList<BasicNameValuePair> parameters = new ArrayList<>();
			// parameters.add(new BasicNameValuePair("id", "1"));
			parameters.add(new BasicNameValuePair("type", code));
			parameters.add(new BasicNameValuePair("postid", number));
			// parameters.add(new BasicNameValuePair("platform", "MWWW"));
//			parameters.add(new BasicNameValuePair("temp", "0.860779312047018"));
			parameters.add(new BasicNameValuePair("temp", "0.9812371336915044"));
			HttpEntity httpEntity = new UrlEncodedFormEntity(parameters);
			httpPost.setEntity(httpEntity);

			// 执行请求
			HttpClient httpClient = HttpClients.createDefault();
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if (null == httpResponse) {
				return null;
			}
			if (null == httpResponse.getEntity()) {
				return null;
			}
			String responseText = EntityUtils.toString(httpResponse.getEntity(), Charset.forName("utf-8"));
			if (StringUtils.isBlank(responseText)) {
				return null;
			}
			try {
				return JSONObject.parseObject(responseText, ExpressReturnData.class);
			} catch (Exception e) {
				logger.info("查询错误！{}", e);
				return null;
			}
		} catch (Exception e) {
			logger.info("查询快递信息错误, code = {}, number ={}, errorMsg: {} ", code, number, e);
		}
		return null;
	}

	public ExpressReturnData call(String kuaidiNo) {
		if (Objects.nonNull(kuaidiNo)) {
			ExpressReturnData data = getExpressInfoByCodeAndNumber(getCompanyCodeByNumber(kuaidiNo),
					kuaidiNo);
			return data;
		}
		return null;
	}

}
