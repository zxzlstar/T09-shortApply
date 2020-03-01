package com.star.shop.admin.utils;

/**
 * 
 * 
 * <p>Title:ParameterConstants</p>
 *
 * <p>Description:</p>
 *
 * <p>Company:</p>
 *
 * @author moxf
 *
 * @date 2018年4月24日
 */
public interface ParameterConstants {
	/**
	 * 商家收入提成点数，类型：float,单位%，数据在0~100之间
	 */
	public final static String PERCENTAGE_POINTS  = "PERCENTAGE_POINTS" ;
	
	/**
	 * 打赏提成点数，类型：float,单位%，数据在0~100之间
	 */
	public final static String REWARD_POINTS  = "REWARD_POINTS" ;
	
	
	/**
	 * 会员推广的佣金点数，类型：float,单位%，数据在0~100之间
	 */
	public final static String COMMISSION_POINTS  = "COMMISSION_POINTS" ;
	
	/**
	 * 微信appid
	 */
	public final static String WX_APPID = "WX_APPID";
	/**
	 * 微信app secret
	 */
	public final static String WX_SECRET = "WX_SECRET";
	
	/**
	 * 微信支付appid
	 */
	public final static String WXPAY__APPID = "WXPAY__APPID";
	
	/**
	 * 微信支付商户号,获取方法：微信支付商户后台 > 账户中心 > 账户设置 > 商户信息 > 微信支付商户号
	 */
	public final static String WXPAY__MCHID = "WXPAY__MCHID";
	
	/**
	 * 微信支付secret,获取方法：微信支付商户后台 > 账户中心 > 账户设置 > API 安全 > API 秘钥
	 */
	public final static String WXPAT_SECRET = "WXPAT_SECRET";
	
	/**
	 * 微信支付证书，类型：文件，需要上传,获取方法：微信支付商户后台 > 账户中心 > 账户设置 > API安全 > 下载证书。得到的 apiclient_cert.p12 文件后，点击右侧的上传按钮进行上传即可。
	 */
	public final static String WXPAY_CERT = "WXPAY_CERT";
	
	/**
	 * 商家商品自动审核:0否，1是
	 */
	public final static String MERCH_GOODS_AUTO_CHECK = "MERCH_GOODS_AUTO_CHECK" ;
	
	/**
	 *  会员提现自动审核：0否，1是
	 */
	public final static String MEMBER_WITHDRAW_AUTO_CHECK = "MEMBER_WITHDRAW_AUTO_CHECK";
	
	/**
	 *  商家提现自动审核：0否，1是
	 */
	public final static String MERCH_WITHDRAW_AUTO_CHECK = "MERCH_WITHDRAW_AUTO_CHECK";
	
	/**
	 *  店员提现自动审核：0否，1是
	 */
	public final static String CLERK_WITHDRAW_AUTO_CHECK = "CLERK_WITHDRAW_AUTO_CHECK";
	
	/**
	 * 招聘职位信息自动审核：0否，1是
	 */
	public final static String POSITION_AUTO_CHECK = "POSITION_AUTO_CHECK";
	
	/**
	 * 社区主题信息自动审核：0否，1是
	 */
	public final static String SUBJECT_AUTO_CHECK = "SUBJECT_AUTO_CHECK";
	
	/**
	 * 是否在二维码中添加头像：0否，1是
	 */
	public final static String QRCODE_INCLUDE_WXTOUXIANG = "QRCODE_INCLUDE_WXTOUXIANG";
	
	/**
	 * 竞猜商品过期时间，单位：天，0为不限时间
	 */
	public final static String GUESS_GOODS_OVERDUE = "GUESS_GOODS_OVERDUE";
	
	/**
	 * 会员个人的广告页
	 */
	public final static String MEMBER_PERSONAL_ADVERTISE = "MEMBER_PERSONAL_ADVERTISE";
	
	
	/**
	 * 商户端显示广告页的二维码长和宽
	 */
	public final static String QRCODE_WIDTH_HEIGHT = "QRCODE_WIDTH_HEIGHT";
	
	/**
	 * 商户端显示广告页的二维码位置
	 */
	public final static String QRCODE_POSITION_XY = "QRCODE_POSITION_XY";
	
	/**
	 * 商户端显示广告页的会员头像的长和宽
	 */
	public final static String TOUXIANG_WIDTH_HEIGHT = "TOUXIANG_WIDTH_HEIGHT";
	
	/**
	 * 商户端显示广告页的会员头像的位置
	 */
	public final static String TOUXIANG_POSITION_XY = "TOUXIANG_POSITION_XY";
	
	/**
	 * 二维码真实的长
	 */
	public final static String QRCODEREAL_WIDTH = "QRCODEREAL_WIDTH";
	
	/**
	 * 二维码真实的宽
	 */
	public final static String QRCODEREAL_HEIGHT = "QRCODEREAL_HEIGHT";
	
	/**
	 * 二维码真实的水平位置
	 */
	public final static String QRCODEREAL_POSITIONX = "QRCODEREAL_POSITIONX";
	
	/**
	 * 二维码真实的垂直位置
	 */
	public final static String QRCODEREAL_POSITIONY = "QRCODEREAL_POSITIONY";
	
	/**
	 * 头像的真实的像素
	 */
	public final static String TOUXIANGREAL_WIDTH_HEIGHT ="TOUXIANGREAL_WIDTH_HEIGHT";
	
	/**
	 * 头像的真实的水平位置
	 */
	public final static String TOUXIANGREAL_POSITIONX = "TOUXIANGREAL_POSITIONX";
	
	/**
	 * 头像的真实的垂直位置
	 */
	public final static String TOUXIANGREAL_POSITIONY = "TOUXIANGREAL_POSITIONY";
	

}
