package com.star.shop.admin.vo;


import com.star.shop.admin.entity.Merch;

public class MerchAdminVo extends Merch {

	private String picture1;
	
	private String pictureUrl;
	
	private String logoUrl;
	
	private Double distance = 0.0;
	
	private String distanceStr;

	public String getPicture1() {
		return picture1;
	}

	public void setPicture1(String picture1) {
		this.picture1 = picture1;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public String getDistanceStr() {
		return distanceStr;
	}

	public void setDistanceStr(String distanceStr) {
		this.distanceStr = distanceStr;
	}

	
}
