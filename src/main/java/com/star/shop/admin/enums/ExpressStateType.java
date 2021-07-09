package com.star.shop.admin.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 快递状态： 0在途，1揽收，2疑难，3签收，4退签，5派件，6退回
 * @author cyan
 */
public enum ExpressStateType{
	
	/**
	 * 未设置
	 */
	NOSET(-1, "未设置"),
	
	/**
	 * 揽收
	 */
	COLLECTING(1, "已揽收"),

	/**
	 * 在途
	 */
	WAITING(0, "运输中"),
	
	/**
	 * 派送中
	 */
	SENDING(5, "派送中"),

	/**
	 * 签收
	 */
	RECEIVING(3, "已签收"),
	
	/**
	 * 遇难
	 */
	DISASTER(2, "疑难"),

	/**
	 * 退签
	 */
	REJECT(4, "退签"),

	
	/**
	 * 退回
	 */
	REFUND(6, "退回");
	
	/**
	 * code
	 */
	private Integer code;

	/**
	 * msg
	 */
	private String msg;

	private ExpressStateType(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}


	public Integer getCode() {
		return code;
	}


	public String getMsg() {
		return msg;
	}

	public static ExpressStateType[] useableValues() {
		List<ExpressStateType> ret = new ArrayList<ExpressStateType>();
		for (ExpressStateType c : ExpressStateType.values()) {
			if (c.code == -1)
				continue;
			ret.add(c);
		}
		return ret.toArray(new ExpressStateType[ret.size()]);
	}

	public static ExpressStateType fromCode(Integer code) {
		for (ExpressStateType c : ExpressStateType.values()) {
			if (c.code.equals(code)) {
				return c;
			}
		}
		throw new IllegalArgumentException(code.toString());
	}
	
	public static ExpressStateType fromValue(String value) {
		for (ExpressStateType c : ExpressStateType.values()) {
			if (c.toString().equals(value)) {
				return c;
			}
		}
		throw new IllegalArgumentException(value.toString());
	}

}
