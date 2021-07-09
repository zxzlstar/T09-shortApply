package com.star.shop.admin.thread;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.net.ssl.SSLContext;

import com.star.shop.admin.entity.express.ExpressInfo;
import com.star.shop.admin.entity.express.ExpressReturnData;
import com.star.shop.admin.vo.express.KdYuanTongSearchResultVo;
import com.star.shop.admin.vo.express.YuanTongLogisticVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


/**
 * 圆通官网获取信息
 * @author Cyan
 * @date 2021年1月27日
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class KuaidiYuanTongUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(KuaidiYuanTongUtil.class);
	
	private final String YUANTONG_URL = "https://www.yto.net.cn/api/trace/waybill";
	
	final static PoolingHttpClientConnectionManager CONN_MGR;
    final static org.apache.http.client.HttpClient CLIENT;
 
    static {
        SSLConnectionSocketFactory sf = null;
 
        try {
            //信任所有
            SSLContext sslc = new SSLContextBuilder().loadTrustMaterial(null, (chain, authType) -> true).build();
            sf = new SSLConnectionSocketFactory(sslc);
        } catch (Exception e) {
            logger.error("Create socket factory failed.", e);
        }
 
 
        final Registry sfr = RegistryBuilder.create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", null != sf ? sf : SSLConnectionSocketFactory.getSocketFactory())
                .build();
 
        CONN_MGR = new PoolingHttpClientConnectionManager(sfr);
        CONN_MGR.setDefaultMaxPerRoute(1024);
        CONN_MGR.setMaxTotal(512);
        CONN_MGR.setValidateAfterInactivity(1000);
 
        CLIENT = HttpClientBuilder.create()
                // 这里主要用来避免使用http链接请求实际为https的地址
                .setSSLSocketFactory(sf)
                .setConnectionManager(CONN_MGR)
                .build();
    }
	
	public ExpressReturnData call(String kuaidiNo) {
		if (Objects.nonNull(kuaidiNo)) {
			String result = getYTExpressInfoByApiUrl(kuaidiNo);
			JSONObject parseObject = JSONObject.parseObject(result);
			if (Objects.nonNull(parseObject) && "success".equals(parseObject.getString("code"))) {
				JSONArray routeArray = parseObject.getJSONArray("data");
				if (Objects.nonNull(routeArray) && routeArray.size() > 0) {
					KdYuanTongSearchResultVo vo = routeArray.getObject(0, KdYuanTongSearchResultVo.class);
					ExpressReturnData data = KdYuanTongSearchVoTranferData(vo);
					return data;
				}
			}
		}
		return null;
	}
	
	private ExpressReturnData KdYuanTongSearchVoTranferData(KdYuanTongSearchResultVo vo) {
		ExpressReturnData data = new ExpressReturnData();
		data.setCom("yuantong");
		if (vo.isQ() == true)
			data.setIscheck(1);
		data.setMessage("ok");
		data.setNu(vo.getWaybillNo());
		data.setStatus(200);
		if (Objects.nonNull(vo.getTraces()) && vo.getTraces().size() > 0) {
			if (vo.getTraces().size() == 1 && vo.getTraces().get(0).getInfo().contains("已收件")) {
				data.setState(1); // 已揽收
			} else if (vo.isQ() == false && vo.isS() == true) {
				data.setState(0); // 运输中
			} else if ("派送".equals(vo.getTraces().get(0).getType())) {
				data.setState(5); // 派送中
			} else if (vo.isQ()) {
				data.setState(3); // 已签收
			}
			
			List<ExpressInfo> infoList = new ArrayList<>();
			ExpressInfo info = null;
			for (YuanTongLogisticVo lo : vo.getTraces()) {
				info = new ExpressInfo();
				info.setContext(lo.getInfo());
				info.setTime(new Date(lo.getTime()));
				infoList.add(info);
			}
			data.setData(infoList);
		}
		return data;
	}

	private String getYTExpressInfoByApiUrl(String kuaidiNo) {
		try {
			// 请求url参数
			HttpPost httpPost = new HttpPost(YUANTONG_URL);
			// 请求参数
			ArrayList<BasicNameValuePair> parameters = new ArrayList<>();
			parameters.add(new BasicNameValuePair("waybillNo", kuaidiNo));
			HttpEntity httpEntity = new UrlEncodedFormEntity(parameters);
			httpPost.setEntity(httpEntity);
			// 执行请求
//			HttpClient httpClient = HttpClients.createDefault();
			HttpResponse httpResponse = CLIENT.execute(httpPost);
			if (Objects.isNull(httpResponse) && Objects.isNull(httpResponse.getEntity())) {
				return null;
			}
			String responseText = EntityUtils.toString(httpResponse.getEntity(), Charset.forName("utf-8"));
			if (StringUtils.isBlank(responseText)) {
				return null;
			}
			return responseText;
		} catch (Exception e) {
			logger.info("查询圆通快递信息错误, apiUrl = {}, kuaidiNo = {}, errorMsg: {} ", YUANTONG_URL, kuaidiNo, e);
		}
		return null;
	}
}
