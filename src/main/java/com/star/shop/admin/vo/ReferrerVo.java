package com.star.shop.admin.vo;

import java.math.BigDecimal;

public class ReferrerVo {

	private String headimgurl;
	
	private BigDecimal amount;
	
	private String nickname;
	
	private String ctime;

	private String oid;
	
	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getCtime() {
		return ctime;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public void setCtime(String ctime) {
		this.ctime = ctime;
	}
	
	
}
