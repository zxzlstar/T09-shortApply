package com.star.shop.admin.pay.wxpay;

/**
 * 
 * 业务
 *
 * @date 2016年3月22日
 *
 * @author mo.xf
 *
 */
public abstract class WxBusinessResponse extends WxProtocolResponse{
	/**
	 * 公众账号IDString(32)	wx8888888888888888	调用接口提交的公众账号ID
	 */
	private String appid;
	
	/**
	 * 商户号String(32)	1900000109	调用接口提交的商户号
	 */
	private String mch_id;	
	
	/**
	 * 设备号String(32)	013467007045764	调用接口提交的终端设备号，
	 */
	private String device_info;	
	
	/**
	 * 随机字符串String(32)	5K8264ILTKCH16CQ2502SI8ZNMTM67VS	微信返回的随机字符串
	 */
	private String nonce_str;	
		
	/**
	 * 签名String(32)	C380BEC2BFD727A4B6845133519F3AD6	微信返回的签名，详见签名生成算法
	 */
	private String sign;	
	
	/**
	 * 业务结果	String(16)	SUCCESS	SUCCESS/FAIL
	 */
	private String result_code;	
	
	/**
	 * 错误代码	String(32)	SYSTEMERROR	详细参见错误列表
	 */
	private String err_code;
	
	/**
	 * 错误代码描述	String(128)	系统错误	错误返回的信息描述
	 */
	private String err_code_des	;	
	
	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getMch_id() {
		return mch_id;
	}

	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}

	public String getDevice_info() {
		return device_info;
	}

	public void setDevice_info(String device_info) {
		this.device_info = device_info;
	}

	public String getNonce_str() {
		return nonce_str;
	}

	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getResult_code() {
		return result_code;
	}

	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}

	public String getErr_code() {
		return err_code;
	}

	public void setErr_code(String err_code) {
		this.err_code = err_code;
	}

	public String getErr_code_des() {
		return err_code_des;
	}

	public void setErr_code_des(String err_code_des) {
		this.err_code_des = err_code_des;
	}
}
