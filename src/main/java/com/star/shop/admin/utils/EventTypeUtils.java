package com.star.shop.admin.utils;
/**
 * 业务类型静态常量
 * @author Administrator
 *
 */
public class EventTypeUtils {

	/**
	 * 商品上架
	 */
	public static final String GOODS_SELLIN = "sellInGoods" ; 
	
	/**
	 * 商品下架
	 */
	public static final String GOODS_SELLOUT = "sellOutGoods"; 
	
	/**
	 * 订单完成后处理
	 */
	public static final String ORDER_SUCCESS= "orderSuccess";
	
	/**
	 * 支付成功后处理
	 */
	public static final String PAY_SUCCESS = "paySuccess";

	/**
	 * 商品已发货通知
	 */
	public static final String Transport_Start = "transportStart";
	
	/**
	 * 赠送订单成功
	 */
	public static final String SENDORD_SUCCESS = "sendOrdSuccess";
	
	/**
	 * 商品规格失效
	 */
	public static final String REMOVE_SPECIFICATION = "removeSpecification";
	
	/**
	 * 商品删除
	 */
	public static final String REMOVE_GOODS = "removeGoods";
	
	/**
	 * 竞猜开奖
	 */
	public static final String GUESS_OPEN = "guessOpen";
	
	/**
	 * 竞猜订单佣金
	 */
	public static final String GUESS_COMMISSION = "guessCommission";
	
	/**
	 * 兑换成功
	 */
	public static final String REDEEM_SUCCESS = "redeemSuccess";
	
}
