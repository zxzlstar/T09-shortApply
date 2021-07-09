package com.star.shop.admin.thread;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.star.shop.admin.entity.express.ExpressInfo;
import com.star.shop.admin.entity.express.ExpressReturnData;
import com.star.shop.admin.vo.express.KdBaiduSearchResultVo;
import com.star.shop.admin.vo.express.LogisticVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

import com.alibaba.fastjson.JSONObject;


/**
 * @author Cyan
 * @date 2021年1月11日
 * 通过百度查询物流接口获取快递物流信息
 */
public class KdBaiduSearchUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(KdBaiduSearchUtil.class);
	

	/**
	 * 根据 快递公司编码 和 快递单号 查询物流信息 的 URL
	 */
	private String apiUrl;
	
	public KdBaiduSearchUtil(String apiUrl) {
		super();
		this.apiUrl = apiUrl;
	}

	public ExpressReturnData call(String kuaidiNo) {
		String url = this.apiUrl + "&nu=" + kuaidiNo;
		if (Objects.nonNull(kuaidiNo)) {
			String result = getExpressInfoByCodeAndNumber(url);
			JSONObject parseObject = JSONObject.parseObject(result);
			 if (Objects.nonNull(parseObject) && parseObject.getInteger("status") == 0) {
				 JSONObject dataObj = parseObject.getJSONObject("data");
				 if (Objects.nonNull(dataObj)) {
					 KdBaiduSearchResultVo vo = dataObj.getObject("info", KdBaiduSearchResultVo.class);
					 if (vo.getStatus() == 1) {
						 // vo 转 epress格式
						 ExpressReturnData data = KdBaiduSearchVoTranferData(vo, kuaidiNo);
						 return data;
					 }
				 }
			 }
		}
		return null;
	}
	
	private ExpressReturnData KdBaiduSearchVoTranferData(KdBaiduSearchResultVo vo, String kuaidiNo) {
		ExpressReturnData data = new ExpressReturnData();
		data.setCom(vo.getCom());
		if (vo.getState() == 3)
			data.setIscheck(1);
		data.setMessage("ok");
		data.setNu(kuaidiNo);
		data.setState(vo.getState());
		data.setStatus(200);
		List<ExpressInfo> infoList = new ArrayList<>();
		ExpressInfo info = null;
		if (Objects.nonNull(vo.getContext())) {
			for (LogisticVo lo : vo.getContext()) {
				info = new ExpressInfo();
				info.setContext(lo.getDesc());
				info.setTime(new Date(lo.getTime() * 1000));
				infoList.add(info);
			}
		}
		data.setData(infoList);
		return data;
	}


	private String getExpressInfoByCodeAndNumber(String url) {
		if (StringUtils.isBlank(url)) {
			return null;
		}
		try {
			// 请求url参数
			HttpGet httpGet = new HttpGet(url);
			
			httpGet.setHeader(HttpHeaders.USER_AGENT,
					"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36");
			httpGet.setHeader(HttpHeaders.COOKIE,
					"BAIDUID=19ECBF84E3549F3188AB96D4CBC8E8AB:FG=1; BIDUPSID=41A1F491FE10CB245482647314B3E028; PSTM=1609292780; __yjs_duid=1_ee262b2205309df4a6c98cc010818ca51609292836325; BDORZ=B490B5EBF6F3CD402E515D22BCDA1598; BDSFRCVID_BFESS=TKKOJeC62wn92KbrUFU_hb6XfTX5HnJTH6aoSUKYc6ODoHqtaZG8EG0PjM8g0KAbVwkKogKK3gOTH4DF_2uxOjjg8UtVJeC6EG0Ptf8g0M5; H_BDCLCKID_SF_BFESS=tR-j_K-2tKD3fP36q4Rfh4F_hgT22-usJC5t2hcH0KLKfD5Mj5O-KlQX0pj35fJK3N8q2nRNJfb1MRjvMxvlqj0pMMJ2bMJvKjTWbq5TtUJoeCnTDMRh-lKWMPRyKMnitKv9-pP23pQrh459XP68bTkA5bjZKxtq3mkjbPbDfn02eCKuDjA2jT33DNLs-bbfHD3bWJTqa5rjDnCry5AMXUI8LNDH-6QTJnPeQf5ILJ6sHCQyblrD0b0IjRO7ttoy2eJPsCbF2n3jqpOy-RoO0xL1Db3yKjvMtg3t3fQ52hvoepvoDPJc3Mv3Q-jdJJQOBKQB0KnGbUQkeq8CQft20b0EeMtjKjLEtR-j_K-2tKD3jnbpK-u_MP0HMfQyetJyaR3nahvvWJ5TMCo6h6uKyPDX3tjPy-vaJHbh-UPaWnrkShPC-tn63fP4-Nj90PR7-6nKhq0y3l02VbcEe-t2yU_V3lJWB-RMW20e0h7mWIbUsxA45J7cM4IseboJLfT-0bc4KKJxbnLWeIJEjj6jK4JKDGLDJ5vP; H_PS_PSSID=33425_33442_31660_32970_33287_33343_33414_33389_33370; BA_HECTOR=aga00g0k2h040001t61fvnsmt0q; BAIDUID_BFESS=19ECBF84E3549F3188AB96D4CBC8E8AB:FG=1; delPer=0; PSINO=7");

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
			logger.info("查询快递信息错误, url = {}, errorMsg: {} ", url, e);
		}
		return null;
	}
	
}
