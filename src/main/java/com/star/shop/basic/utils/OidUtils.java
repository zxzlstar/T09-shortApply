package com.star.shop.basic.utils;

import java.util.Date;
import java.util.Random;

/**
 * 生成oid
 * @author 蔡晓鹏
 *
 */
public class OidUtils {

	/**
	 * 生成订单Id
	 * @return
	 */
	public static String createOid() {
		String date = DateUtils.format(new Date(), "ddMMyyyyHHmmss");
		Random random = new Random();
		int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数
		String oid = date+rannum;
		
		return oid;
	}
	
	/**
	 * 生成详细的订单Id
	 * orderDetailId
	 * @param oid
	 * @return
	 */
	public static String createOrderDetailId(String oid) {		
		Random random = new Random();
		int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数
		
		String orderDetailId = oid + rannum;
		return orderDetailId;
	}
}
