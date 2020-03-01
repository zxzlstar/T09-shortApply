package com.star.shop.admin.vo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class ResponseVo {
	
	private Integer code;
	
	private Integer total;
	
	private Object data;
	
	public ResponseVo(Integer code,Integer total,Object data) {
		this.code = code;
		this.total = total;
		this.data = data;
	}
	
	public ResponseVo(Object data) {
		this.data = data;
	}
	
	public static ResponseVo s(String response) {
		JSONObject object = (JSONObject) JSON.parse(response);
		int code = Integer.parseInt(object.get("code").toString());
		int total = Integer.parseInt(object.get("total").toString());
		Object responseData = object.get("data");
		return new ResponseVo(code,total,responseData);
	}
	
	public static ResponseVo ss(String response) {
		JSONObject object = (JSONObject) JSON.parse(response);
		Object responseData = object.get("data");
		return new ResponseVo(responseData);
	}
	
	
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
