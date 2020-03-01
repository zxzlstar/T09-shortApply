package com.star.shop.admin.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.star.shop.basic.entity.AbstractEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;


/**
 * 
 * 会员
 * 
 * <p>
 * Title:Member
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
 * @date 2017年8月29日
 */
@Entity
@Table(name = "t_member")
@DynamicInsert
@DynamicUpdate
public class Member extends AbstractEntity {

	/**
	 * 手机
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	private String mobile;

	/**
	 * 密码
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	private String password;
	/**
	 * 用户的唯一标识
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	private String openid;

	/**
	 * 用户昵称
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	private String nickname;

	/**
	 * 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
	 */
	@Column(columnDefinition = "int default 0")
	private Integer sex;

	/**
	 * 用户个人资料填写的省份
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	private String province;

	/**
	 * 普通用户个人资料填写的城市
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	private String city;

	/**
	 * 国家，如中国为CN
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	private String country;

	/**
	 * 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
	 */
	@Column(columnDefinition = "varchar(1024) default ''")
	private String headimgurl;

	/**
	 * 邀请码
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	private String invitation;

	/**
	 * 推荐人
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	private String referrer;

	/**
	 * 用户的余额
	 */
	@Column(columnDefinition = "decimal(19,2) default 0.00")
	private BigDecimal amount;

	/**
	 * 用户佣金
	 */
	@Column(columnDefinition = "decimal(19,2) default 0.00")
	private BigDecimal commission;

	/**
	 * 累计佣金
	 */
	@Column(columnDefinition = "decimal(19,2) default 0.00")
	private BigDecimal totalCommission;

	/**
	 * 我的广告id
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	private String advertising;

	/**
	 * 是否店员，0不是，1是
	 */
	@Column(columnDefinition = "int default 0")
	private Integer isClerk;

	/**
	 * 用户名
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	private String name;

	/**
	 * 个人简历
	 */
	@Column(columnDefinition = "varchar(2048) default ''")
	private String resume;

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

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public String getInvitation() {
		return invitation;
	}

	public void setInvitation(String invitation) {
		this.invitation = invitation;
	}

	public String getReferrer() {
		return referrer;
	}

	public void setReferrer(String referrer) {
		this.referrer = referrer;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getCommission() {
		return commission;
	}

	public void setCommission(BigDecimal commission) {
		this.commission = commission;
	}

	public BigDecimal getTotalCommission() {
		return totalCommission;
	}

	public void setTotalCommission(BigDecimal totalCommission) {
		this.totalCommission = totalCommission;
	}

	public Integer getIsClerk() {
		return isClerk;
	}

	public void setIsClerk(Integer isClerk) {
		this.isClerk = isClerk;
	}

	public String getAdvertising() {
		return advertising;
	}

	public void setAdvertising(String advertising) {
		this.advertising = advertising;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getResume() {
		return resume;
	}

	public void setResume(String resume) {
		this.resume = resume;
	}

}
