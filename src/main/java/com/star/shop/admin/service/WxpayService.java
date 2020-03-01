package com.star.shop.admin.service;

import com.star.shop.admin.pay.PayConstants;
import com.star.shop.admin.pay.utils.WxSignature;
import com.star.shop.admin.pay.utils.XmlMarshaller;
import com.star.shop.admin.pay.wxpay.WxUnifiedOrderRequest;
import com.star.shop.admin.pay.wxpay.WxUnifiedOrderResponse;
import com.star.shop.basic.http.HttpRequest;
import com.star.shop.basic.repository.UploadRepository;
import com.star.shop.basic.utils.MD5Utils;
import com.star.shop.basic.utils.Utils;
import com.star.shop.basic.vo.ResultVo;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import java.math.BigDecimal;


/**
 * 
 * 微信支付
 * 
 * <p>Title:WxpayService</p>
 *
 * <p>Description:</p>
 *
 * <p>Company:</p>
 *
 * @author x.zhang
 *
 * @date 2018年1月30日
 */
@Service
public class WxpayService {
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(WxpayService.class) ;
	
	@Value("${shop.wxpay.appid}")
	private String appid ;
	
	@Value("${shop.wxpay.mchid}")
	private String mchid ;
	
	@Value("${shop.wxpay.secret}")
	private String secret ;
	
	@Value("${shop.wxpay.notify.url}")
	private String notifyUrl ;

	
	private @Resource
	UploadRepository uploadRepository ;
	/**
	 * 
	 * @param payNo
	 * @param body
	 * @param detail
	 * @param amount
	 * @param openid
	 * @throws Exception
	 */
	public ResultVo wapPay(String payNo , String body , String detail , BigDecimal amount , String openid) throws Exception{
		// 构造请求参数
		WxUnifiedOrderRequest requestData = new WxUnifiedOrderRequest();
		requestData.setAppid(appid);
		requestData.setMch_id(mchid);
		requestData.setNonce_str(Utils.getRandomString(16));

		requestData.setBody(body); // 商品描述
		requestData.setDetail(detail); // 商品详情
		requestData.setOut_trade_no(payNo); // 交易系统订单号
		requestData.setTotal_fee(getTotalFee(amount));
		requestData.setFee_type("CNY");
		String ip = Utils.getIpAddr(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
		if(ip.contains(",")){
			ip = ip.substring(0 ,  ip.indexOf(","));
		}
		requestData.setSpbill_create_ip(ip); // 先默认一个IP
		requestData.setTrade_type("JSAPI");
		requestData.setNotify_url(notifyUrl);
		requestData.setSign_type("MD5");
		requestData.setOpenid(openid);

		String sign = "";
		try {
			sign = WxSignature.getSign(requestData, secret);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} // 加签

		requestData.setSign(sign);
		
		ResultVo resultVo = new ResultVo() ;
		
		String xmlData = XmlMarshaller.marshal(requestData); // 生成请求参数
		logger.info("微信支付下单请求参数：{}" , xmlData);
		String result = HttpRequest.getInstance().postSendHttp(PayConstants.WXPAY_UNIFIED_ORDER_ADDR, xmlData);
		logger.info("微信支付下单返回结果：{}" , result);
		WxUnifiedOrderResponse unifiederResponse = XmlMarshaller.unmarshal(result, WxUnifiedOrderResponse.class);
		if (PayConstants.WXPAY_RESULT_CODE_SUCCESS.equals(unifiederResponse.getReturn_code())
				&& PayConstants.WXPAY_RESULT_CODE_SUCCESS.equals(unifiederResponse.getResult_code())
				&& WxSignature.checkIsSignValidFromResponseString(result,secret)) {
			
			String nonceStr = Utils.getRandomString(20) ;
			String timeStamp = String.valueOf(System.currentTimeMillis()/1000) ;
			String packageVal = "prepay_id="+unifiederResponse.getPrepay_id() ;
			resultVo.addData("appId", appid);
			resultVo.addData("timeStamp",timeStamp );
			resultVo.addData("nonceStr", nonceStr);
			resultVo.addData("package", packageVal);
			resultVo.addData("signType", "MD5");
			
			
			String signFormat = "appId=%s&nonceStr=%s&package=%s&signType=MD5&timeStamp=%s&key=%s";
			sign = String.format(signFormat, appid, nonceStr, packageVal, timeStamp,secret);
			sign = MD5Utils.encode(sign).toUpperCase() ;
			resultVo.addData("paySign", sign);

		}else{
			resultVo.setCode(400);
		}
		
		return resultVo ;
	}
	
	public static String getTotalFee(BigDecimal amount) {
		amount = amount.multiply(new BigDecimal(100));
		amount = amount.setScale(0, BigDecimal.ROUND_FLOOR);
		return amount.toString();
	}
	private String getIp() {
		String ip = "121.32.12.133";
		try{
			ip = Utils.getIpAddr(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
			if(ip.contains(",")){
				ip = ip.substring(0 ,  ip.indexOf(","));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return ip ;
	}

	


}
