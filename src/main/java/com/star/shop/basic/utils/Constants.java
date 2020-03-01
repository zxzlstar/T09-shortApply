package com.star.shop.basic.utils;

/**
 * 
 * 
 * <p>Title:Constants</p>
 *
 * <p>Description:</p>
 *
 * <p>Company:</p>
 *
 * @author x.zhang
 *
 * @date 2018年8月22日
 */
public interface Constants {
	/**
	 * 默认管理员名称
	 */
	public final static String DEFAULT_ADMIN_NAME = "admin" ;
	
	/**
	 * 默认分隔符
	 */
	public final static String DEFAULT_DELIMITER = "," ;
	
	/**
	 * Token过期时间
	 */
	public final static Long TOKEN_EXPIRE_TIME = 3600 * 1000 *24 * 7L ;
	
	/**
	 * 会员ID
	 */
	public final static String MEMBER_ID = "MemberId" ;
	
	/**
	 * 商户 ID
	 */
	public final static String MERCH_ID = "MerchId" ;
	
	/**
	 * 
	 */
	public final static String APP_ID = "AppId";
	
	/**
	 * 系统名称
	 */
	public final static String SYSTEM_NAME = "SystemName" ;
	
	/**
	 * H5端
	 */
	public final static String SYSTEM_WECHAT = "H5";
	
	/**
	 * 商户端
	 */
	public final static String SYSTEM_MERCH = "Merch";
	
	/**
	 * 订单前缀
	 */
	public final static String ORDER_INDEX = "C";
	
	/**
	 * 提现的订单号
	 */
	public final static String WITHDRAW_INDEX = "T";
	
	/**
	 * 订单兑换码前缀
	 */
	public final static String ORDER_CODE_INDEX = "OC";
	
	/**
	 * 竞猜订单兑换码前缀
	 */
	public final static String GUESSORDER_CODE_INDEX = "GC";
	
	/**
	 * 订单号生成的长度
	 */
	public final static int ORDER_LEN = 6;
	
	/**
	 * 支付号码生成长度
	 */
	public final static int PAY_LEN = 6;
	
	/**
	 * 兑换码生成上度
	 */
	public final static int CODE_LEN = 10;
	
	/**
	 * 商户端appid
	 */
	public final static String APPID = "4028819c5de3c938015de3ca929dre01" ;
	
	/**
	 * 微信支付渠道
	 */
	public final static String PAY_CHANNEL_WXPAY = "wxpay";
	
	/**
	 * 会员余额支付渠道
	 */
	public final static String PAY_CHANNEL_BALANCE = "balance";
	
	/**
	 * 阿里云视频约定的key
	 */
	public final static String KEY = "XP2SQ14";
}
