package com.star.shop.admin.pay;

/**
 * 
 * <p>Title:PayConstants</p>
 *
 * <p>Description:</p>
 *
 * <p>Company:</p>
 *
 * @author moxf
 *
 * @date 2017年2月8日
 */
public interface PayConstants {
	/**
	 * 支付渠道统一定义
	 */
	public final static String ALIPAY_PAY_CHANNEL = "alipay";
	
	public final static String WXPAY_PAY_CHANNEL = "wxpay";
	
	/**
	 * 支付宝的APPID
	 */
	//public final static String ALIPAY_APPID =  "2017030906130235" ;
//	public final static String ALIPAY_APPID =  "2016072400106140";

	/**
	 * alipay网关URL
	 */
	public final static String ALIPAY_GATEWAY= "https://openapi.alipay.com/gateway.do";
//	public final static String ALIPAY_GATEWAY= "https://openapi.alipaydev.com/gateway.do";
	
	/**
	 * alipay公共key
	 */
	public final static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAyMU/MAxfHXSIJTiFNlYwObuVGhxmsGy7hvnaXnZGRHfMxtj57vnNGEu60ruSSmxJqQQOOyztEOti1diQlhFUQb6noTPytAW/sNV/M8t89UDQlPJmdBEG8yu0wxHeB4pm5+CXWe5s30EqHk72aDeVOnO+tlJDSLGZVKahidrbdHSW7GrHk0tDc1MEvQd8d3TGGNIXX3vXjUeSVM3qbRQq+XFLrxNC2jqYlgbvfLuAiOmDhEqQuwZOylT1PlyVnHIiXMcNJmpBVMXvBv0miMwpjttVLH/vglPhFvT6L0Bh7mDC5/waZjwxlIL9bcvZk/ahLmXg0rl14nxt317A8ka30wIDAQAB";

	/**
	 * alipay商户私钥
	 */
	//public final static String ALIPAY_PRIVATE_KEY = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQDE5d7y+l6l5Iw7NYNAV1JRU0ZIZYPi21q3h2gqEZ06b1bhiM2RzxkMapfi/4W/f1oWPU1P73USPxjWOU3zGAacDTyY+8fMXbLBj9q+Q+vFqzLz/cpxJnmluyodZd68FAWHxfK0PLNdR4yAdzATal7/Osq+iRSC3Bhhws5ncUaxJfgvMPjMV9lMNVlOnUY6Fykgz6LYIV1ni79uYoa0fHy5JnyUu+5AxBqfxvtqfKyEmtMj7T1DkA4buNrOYAkxA1CnPI4+5o2mtBKhRFUh/RZT4vX6IzvLgJCGh1Pxjwrwb9kVmzFS8ehdTx6GTFXe7wfRntju4mMHpqsf5tQJKSzNAgMBAAECggEAE0nBcWP9aZrd8d7oIKFtOvJBbE4lgU+jr17gYV6gXF0T0bwpByOWfJTklCQb4gqDtLIEpNP8T/FGaZXrgOjVnOvfB82CYQ68uCFzeDgHHLIMp8Iw1qzCNe4DziVwkFQVxpspj2Zy26xqoxsJ5p0A+Ve1DL04+Si+arU+qvt5xViayse/mGPuy0prS7LSB+QI1yZZZAg3IRNsC8DKNBL8lFV4wMzFmYVoaT27YvaQehPsuLPntUSZOm8NLuachUmCZju7TRDW8+g9IM2jj3aLZ28GweFOIq31h7X2aR1+tY3hMsWbStr6Ux4P9y+u6XJl2DIXTiWLq0lA+iZrjszMAQKBgQDuQ+CanvfhWKtwpJ0PTRFSwqcnN5R+y+I4SmDxb0taRFO55xx4UrUj6+NiLARSnLnvHzZ50iAngeQ0m0lszlavYT1d9WHHtV46LGMXKY1MjuwoYMcdmv7NHXPT1Ax4pZ02ROPniIK+nJuOTlk5MVp5l6ziC3bwLn9gam0L83/vQQKBgQDTjb6l8omhiE4m8doGVEwl/dn8kacPrtw0ZGlXMJGbYrfC6BliOEQo/+LGrJuflRTGzfrarv4TnnHu2ruO/cnUzXz5YwiFUJcKVsiBPHW8IgQdJ3klhvP1RJIPYmshuNr61Yx1oqC4lSReFdSQRYh2MpxU/BvI8iiOrimkyT7mjQKBgBvMM4IWldjmnWnQ6roERccgCpxasapxYv0qQFEqqOHpItOHkRzAmX2NYcPsWrzAqDirrZFc4H97egJhM5nMIPTpJV226SeBk3K6Bv2GzYNnEUpMsC5WDPAlRYQrMyFIQSU5uFUk6gwO/V8G6QXxKhsRhQwCRmgTJK08/WftirmBAoGAGeUhzwJPInyixFWaUGgFIileg3H4twy6W5dm5YtGZ3S/9ZWfdcxZW4Nm6Na7/d6rumIAMMB2zUP5wjj3+FrMV8YHZCdhJaBlvJg+1sLlzm+DfWYViChquDz6e/OaFXihJU76cHPFpJJs7y9Q44//UiiDxktNw10Q5dYfttIDZwkCgYAiXrV1okif5rjtUmuaB0z5Cp3oq/EkcpjIRRAwODhHIIHpEE94DzNHKNjBff6goWR8mVF1AUg+3Zz2Lj7iQwDwGUwT2K8n5Mz5Ww+yeUogWqpyEy517ovwFFW7XERVi12Wgma4eV2JKLJ9HES190OoM/wg+w47sIKNPAURLJplHw==";
	//测试用的
//	public final static String ALIPAY_PRIVATE_KEY = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCFjGlJHOvuTym7Vmi3Jue/NEsTqbvDXLec1Be71gkJegiyakKGXMg1uBxIH0Lp5Dfm4EojltRVX9nvILP+3thQJeTlZWdFa9OOrlwZfkbHOjYKEoBstswnfWKlHPPAwWCaErLn6/btUwfjX5FuLxLi2i/uLtFaggagXT5U3wCHoclPXvlTtwvB21bCMflVeHtz3J1mj3yvUKyxg93LK+0gtv+urC78MY6HucTjMiZ8MArONBahRjzQ4vYCmCkf4MnPWRO2RBKqj6hABgAvbMrT0HHJOCTGYGItcCaa7FY3mJED2KBdOK2ewNaEU0mOpwWU0KJNtc59Kf4EdTCtEy8RAgMBAAECggEASTI8QqZnaaZZ6SPjIP9h5Zpzy8Mr9lfLC1nz6C/XpCoOujsjWyFfWpIebik0M52yRgiImhkBKAnxGO+DuB3L0aHzYwfBInuG6J0IPbDIekmddMfPO8a0/q/qcTT9JJjYqZm2FQdvv4PSvvySaSHD6DJOMu3WMRUzLJHup/XSBIQH/3lJx3EHPkKdgRuLEti16R83ql/FDoEn53AYgqYV2Gx/g2AG2Ommj7VtrNwiI+IiBKQrwEFCa04vLFji00vuR6Xbm7gqbNdSavRJJnVPakx8H6T5Jk7/yCilgeNDaDaRNAox1AJq9jGLVRi5ovA+7kipIQeQUL+fO55zXCQAAQKBgQDpCKCxvwGgn2TCxNFWz1U7qnnXc1kwPZky5pvR6tZe94+cqnS3DLlKZlZ/xlgXxJsT8dv6i/lP+bWv96zCoiKiazBq4NVhBDSqHJoeRa5gPIywbZxS4Ilv5DBCjl2quw7/m/p7qrln90EuTljI3kTwcWvwhRBTqXS0sGl2d0seEQKBgQCStczsdvvI/MM8urgCjLl61305DZ1F17r3DTcvNNdgVLJywPEJ1OWgM27rd8ddDnF1YxW9okOraT8aEMqt5uNuSwrfyRcS/yxb+EUVJoiNQlc1eBvQcvPAvt6ji94cozyk1FgdaXyoHajs7KCnt/PqReQDtAB3kyq9Gpcx3goBAQKBgE0eBc/MngVBul6aM4y+4D32lIuwKHvGZsRpfHdjd73Fv0jhP+6zzz3H0ChIwy5aadeX2mx3lfHZeT7af9mJPlzxi/xSm0qX0bXZ8UbDWImQgB6Cp5DlrZXRtgbQhj7R2IzOWX5DaUf2kyN32fnfVIEdh9L4tgZ5tZ12I/CIWduBAoGAdcEJ+/n8WfPW2NWdgB5neVugnLx6sWZYIJcw5ejpvd39ZPHMBfIlZJIjw0decyh2lJu1BYJKAjj1dvxnUXFytkHxNnF23b6GYkNBX5qQG6FV0grXeFXro5cPqAFJg37Z3Aq7ts/GSEGWJqQ+xp91XWI/g2FkxpUOxr2jAVmQegECgYB9xMqWV5dlp5oyLqNfjC5o+QeI/yaEj5Yn+aMtKoSOlt0fNEIVfx05B374AGEs0tXipUO9lsmpM6yNPHlPw0gZrjfYpLLOedthZasivKpKbq6qN/R3ko2jt7cFJLDJmpuKiac8GcNK78SOUC5L6T/KzE0FPnWyBah0nE4y596VTw==";
	
	/**
	 * 结果码,业务处理成功
	 */
	public final static String ALIPAY_RESULT_CODE_10000 = "10000";
	
	/**
	 * 结果码,业务处理成功
	 * 
	 * 具体失败原因参见接口返回的错误码
	 */
	public final static String ALIPAY_RESULT_CODE_40004 = "40004";
	
	/**
	 * 结果码,业务处理成功
	 * 
	 * 该结果码只有在条码支付/声波支付请求API时才返回，代表付款还在进行中，需要调用查询接口查询最终的支付结果
	 */
	public final static String ALIPAY_RESULT_CODE_10003 = "10003";
	
	/**
	 * 结果码,业务出现未知错误或者系统异常
	 * 
	 * 业务出现未知错误或者系统异常，如果支付接口返回，需要调用查询接口确认订单状态或者发起撤销
	 */
	public final static String ALIPAY_RESULT_CODE_20000 = "20000";
	
	/**
	 * WAIT_BUYER_PAY（交易创建，等待买家付款）
	 */
	public final static String ALIPAY_ORDER_STATE_WAIT = "WAIT_BUYER_PAY";
	
	/**
	 * TRADE_CLOSED（未付款交易超时关闭，或支付完成后全额退款）
	 */
	public final static String ALIPAY_ORDER_STATE_CLOSED = "TRADE_CLOSED";
	
	/**
	 * TRADE_SUCCESS（交易支付成功）
	 */
	public final static String ALIPAY_ORDER_STATE_SUCCESS = "TRADE_SUCCESS";
	
	/**
	 * TRADE_FINISHED（交易结束，不可退款）
	 */
	public final static String ALIPAY_ORDER_STATE_FINISHED = "TRADE_FINISHED";
	
	/**
	 * 支付宝异步通知成功标识
	 */
	public final static String ALIPAY_NOTIFY_SUCCESS_TAG = "success"; 
	
	/**
	 * 支付宝支付完成后返回的URL
	 */
//	public final static String ALIPAY_RETURN_URL = "http://bxamjv.natappfree.cc/payment/alipay/return"  ;
	//public final static String ALIPAY_RETURN_URL = "http://m.nuts-edu.com/payment/alipay/return"  ;
//	public final static String ALIPAY_RETURN_URL = "http://112.74.50.206/payment/alipay/return"  ;
	
	/**
	 * 
	 */
//	public final static String ALIPAY_NOTIFY_URL = "http://bxamjv.natappfree.cc/payment/alipay/notify"  ;
	//public final static String ALIPAY_NOTIFY_URL = "http://m.nuts-edu.com/payment/alipay/notify"  ;
//	public final static String ALIPAY_NOTIFY_URL = "http://112.74.50.206/payment/alipay/notify"  ;
	
	
	//微信
	//public final static String WXPAY_APPID = "wx98d977225ce25018";
	
	/**
	 * 商户号
	 */
	//public final static String WXPAY_MCH_ID = "1438710802";
	
	//public final static String WXPAY_APPSECRET = "8b71235928f444bfdd3222453bced392";
	
	/**
	 * 微信支付密钥
	 */
	//public final static String WXPAY_SECRET = "sqRBIWyHsMUHrn8KVm3qghcTjqN0WvWB";
	
	public final static String WXPAY_OAUTH_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s"
				+ "&redirect_uri=%s&response_type=code&scope=snsapi_base&state=m#wechat_redirect" ;
	
	public final static String WXPAY_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
	
	public final static String WXPAY_UNIFIED_ORDER_ADDR = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	
	/**
	 * 微信支付返回码--成功
	 */
	public final static String WXPAY_RESULT_CODE_SUCCESS = "SUCCESS";
	
	/**
	 * 微信支付返回码--失败
	 */
	public final static String WXPAY_RESULT_CODE_FAIL  = "FAIL";
	
	/**
	 * 转账失败状态	
	 */
	public final static String WXPAY_TRANSFERS_INFO_FAILED = "FAILED";
	
	/**
	 * 转账处理中状态	
	 */
	public final static String WXPAY_TRANSFERS_INFO_PROCESSING = "PROCESSING";
	
	/**
	 * 企业向微信用户个人付款 
	 */
	public final static String WXPAY_TRANSFERS_URL = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers" ;
	
	/**
	 * 查询企业付款
	 */
	public final static String WXPAY_GET_TRANSFERS_INFO_URL = "https://api.mch.weixin.qq.com/mmpaymkttransfers/gettransferinfo" ;

}
