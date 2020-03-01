package com.star.shop.basic.vo;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 
 * <p>
 * Title:ResultVo
 * </p>
 *
 * <p>
 * Description:
 * </p>
 *
 * <p>
 * Company:
 * </p>
 *
 * @author x.zhang
 *
 * @date 2018年8月17日
 */
public class ResultVo {

	private Integer code;

	private String message;

	private Object data;

	private long total;

	public ResultVo() {
		this.code = 200;
	}

	public ResultVo(Integer code) {
		this.code = code;
	}

	public ResultVo(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public ResultVo(Integer code, String message, Object data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public static ResultVo s() {
		return new ResultVo();
	}

	public static ResultVo s(String message) {
		return new ResultVo(200, message);
	}

	public static ResultVo s(String message, Object data) {
		return new ResultVo(200, message, data);
	}

	public static ResultVo s(Object data) {
		return new ResultVo(200, "", data);
	}

	public static ResultVo e(Integer code) {
		return new ResultVo();
	}

	public static ResultVo e(Integer code, String message) {
		return new ResultVo(code, message);
	}

	public static ResultVo e(Integer code, String message, Object data) {
		return new ResultVo(code, message, data);
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void addData(String key, Object value) {
		if (data == null) {
			data = new HashMap<String, Object>();
		}

		if (data instanceof Map) {
			((Map) data).put(key, value);
		}
	}
}
