package com.star.shop.admin.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.star.shop.basic.entity.AbstractEntity;
import com.star.shop.basic.utils.PathSerializer;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 
 * 商户表
 * 
 * <p>
 * Title:Merch
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
 * @date 2018年3月14日
 */
@Entity
@Table(name = "t_merch")
@DynamicInsert
@DynamicUpdate
public class Merch extends AbstractEntity {
	/**
	 * 商家联系人称呼
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	private String contacts;

	/**
	 * 商家手机号码，用于商家后台登录
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	private String mobile;

	/**
	 * 登录密码
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	private String password;

	/**
	 * 商家编号，后台自动生成一个唯一纯数字的6位商家编号
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	private String number;

	/**
	 * 商家店铺名称
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	private String merchName;

	/**
	 * 商家logo
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	@JSONField(serializeUsing = PathSerializer.class)
	private String logo;

	/**
	 * 商家标签,最多5个,用分隔符;分隔
	 */
	@Column(columnDefinition = "varchar(1024) default ''")
	private String tags;

	/**
	 * 商家地址
	 */
	@Column(columnDefinition = "varchar(1024) default ''")
	private String address;

	/**
	 * 第一级地址（省）
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	private String provinceName;
	/**
	 * 第二级地址（市）
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	private String cityName;

	/**
	 * 第三级地址（区）
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	private String districtName;

	/**
	 * 地理位置纬度
	 */
	@Column(columnDefinition = "decimal(19,6) default 0.00")
	private String latitude;

	/**
	 * 地理位置经度
	 */
	@Column(columnDefinition = "decimal(19,6) default 0.00")
	private String longitude;

	/**
	 * 商家电话
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	private String tel;

	/**
	 * 视频
	 */
	@Column(columnDefinition = "varchar(100) default ''")
	private String video;

	/**
	 * 商家图片，可上传多张图片,最多5张，用分隔符;分隔
	 */
	@Column(columnDefinition = "varchar(1024) default ''")
	private String picture;

	/**
	 * 开始营业时间，如8:00
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	private String startOpeningHours;

	/**
	 * 结束营业时间，如23：00
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	private String endOpeningHours;

	/**
	 * 商家类型：1酒吧,2KTV,3沐足
	 */
	@Column(columnDefinition = "int default 1")
	private Integer type;

	/**
	 * 商家简介
	 */
	@Column(columnDefinition = "text")
	private String intro;

	/**
	 * 商家状态，1新增，2正在使用，3已经下线
	 */
	@Column(columnDefinition = "int default 1")
	private Integer status;

	/**
	 * 商户的余额
	 */
	@Column(columnDefinition = "decimal(19,2) default 0.00")
	private BigDecimal amount;

	/**
	 * 审核状态：1待审核，2审核通过，3审核不通过
	 */
	@Column(columnDefinition = "int default 1")
	private Integer checkStatus;

	/**
	 * 备注
	 */
	@Column(columnDefinition = "varchar(1024) default ''")
	private String remark;

	/**
	 * 微信openId
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	private String wxOpenid;

	/**
	 * 昵称
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	private String wxNickname;

	/**
	 * 微信头像
	 */
	@Column(columnDefinition = "varchar(1024) default ''")
	private String wxHeadimgurl;
	
	/**
	 * 排序
	 */
	@Column(columnDefinition = "int default 0")
	private Integer rank;
	
	/**
	 * 是否热门（1否，2是）
	 */
	@Column(columnDefinition = "int default 1")
	private Integer isPopular;

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getMerchName() {
		return merchName;
	}

	public void setMerchName(String merchName) {
		this.merchName = merchName;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getStartOpeningHours() {
		return startOpeningHours;
	}

	public void setStartOpeningHours(String startOpeningHours) {
		this.startOpeningHours = startOpeningHours;
	}

	public String getEndOpeningHours() {
		return endOpeningHours;
	}

	public void setEndOpeningHours(String endOpeningHours) {
		this.endOpeningHours = endOpeningHours;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(Integer checkStatus) {
		this.checkStatus = checkStatus;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getWxOpenid() {
		return wxOpenid;
	}

	public void setWxOpenid(String wxOpenid) {
		this.wxOpenid = wxOpenid;
	}

	public String getWxNickname() {
		return wxNickname;
	}

	public void setWxNickname(String wxNickname) {
		this.wxNickname = wxNickname;
	}

	public String getWxHeadimgurl() {
		return wxHeadimgurl;
	}

	public void setWxHeadimgurl(String wxHeadimgurl) {
		this.wxHeadimgurl = wxHeadimgurl;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public Integer getIsPopular() {
		return isPopular;
	}

	public void setIsPopular(Integer isPopular) {
		this.isPopular = isPopular;
	}

	
}
