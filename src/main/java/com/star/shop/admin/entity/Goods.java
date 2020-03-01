package com.star.shop.admin.entity;

import com.star.shop.basic.entity.AbstractEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.math.BigDecimal;


/**
 * 
 * 
 * <p>
 * Title:Goods
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
 * @date 2019年9月22日
 */
@Entity
@Table(name = "t_goods")
@DynamicInsert
@DynamicUpdate
public class Goods extends AbstractEntity {

	/**
	 * 商品编号
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	private String number;

	/**
	 * 商品名称
	 */
	@Column(columnDefinition = "varchar(100) default ''")
	private String name;

	/**
	 * 商品分享描述，只用到微信分享，建议不超过50个字
	 */
	@Column(columnDefinition = "varchar(255) default ''")
	private String shareDesc;

	/**
	 * 商品详情,图文并排
	 */
	@Column(columnDefinition = "text")
	private String particulars;

	/**
	 * 商品logo
	 */
	@Column(columnDefinition = "varchar(1024) default ''")
	private String logo;

	/**
	 * 商品图片，可上传多张图片,最多10张，用分隔符;分隔
	 */
	@Column(columnDefinition = "varchar(1024) default ''")
	private String picture;

	/**
	 * 商品分类
	 */
	@Fetch(FetchMode.JOIN)
	@ManyToOne
	@JoinColumn(name = "classify", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
	private GoodsClassify classify;

	/**
	 * 商品价格（商品规格未开启，则使用该价格）
	 */
	@Column(columnDefinition = "decimal(19,2) default 0.00")
	private BigDecimal price;

	/**
	 * 商品原价
	 */
	@Column(columnDefinition = "decimal(19,2) default 0.00")
	private BigDecimal originalPrice;

	/**
	 * 商品状态，1未上架，2已上架
	 */
	@Column(columnDefinition = "int default 1")
	private Integer status;


	/**
	 * 商品规格状态，1未开启，2已开启
	 */
	@Column(columnDefinition = "int default 1")
	private Integer isSpecification;

	/**
	 * 商品价格范围，商品规格状态为2时，才使用
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	private String rangOfPrice;

	/**
	 * 视频
	 */
	@Column(columnDefinition = "varchar(100) default ''")
	private String video;

	/**
	 * 视频前景图
	 */
	@Column(columnDefinition = "varchar(100) default ''")
	private String picbfvd;

	/**
	 * 库存
	 */
	@Column(columnDefinition = "int default 0")
	private Integer stock;

	/**
	 * 销量
	 */
	@Column(columnDefinition = "int default 0")
	private Integer sales;

	/**
	 * 是否置顶，0否，1是
	 */
	@Column(columnDefinition = "int default 0")
	private Integer stick;

	/**
	 * 是否优先推荐， 0否，1是
	 */
	@Column(columnDefinition = "int default 0")
	private Integer recommend;

	/**
	 * 是否新品， 0否，1是
	 */
	@Column(columnDefinition = "int default 0")
	private Integer isNew;


	/**
	 * 是否已经删除,0正常数据，1已删除
	 */
	@Column(columnDefinition = "int default 0")
	private Integer isDelete;

	@Transient
	private String classifyId;

	@Transient
	private String classifyName;

	@Transient
	private String pictureUrl;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShareDesc() {
		return shareDesc;
	}

	public void setShareDesc(String shareDesc) {
		this.shareDesc = shareDesc;
	}

	public String getParticulars() {
		return particulars;
	}

	public void setParticulars(String particulars) {
		this.particulars = particulars;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public GoodsClassify getClassify() {
		return classify;
	}

	public void setClassify(GoodsClassify classify) {
		this.classify = classify;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(BigDecimal originalPrice) {
		this.originalPrice = originalPrice;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getIsSpecification() {
		return isSpecification;
	}

	public void setIsSpecification(Integer isSpecification) {
		this.isSpecification = isSpecification;
	}

	public String getRangOfPrice() {
		return rangOfPrice;
	}

	public void setRangOfPrice(String rangOfPrice) {
		this.rangOfPrice = rangOfPrice;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public String getPicbfvd() {
		return picbfvd;
	}

	public void setPicbfvd(String picbfvd) {
		this.picbfvd = picbfvd;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Integer getSales() {
		return sales;
	}

	public void setSales(Integer sales) {
		this.sales = sales;
	}

	public Integer getStick() {
		return stick;
	}

	public void setStick(Integer stick) {
		this.stick = stick;
	}

	public Integer getRecommend() {
		return recommend;
	}

	public void setRecommend(Integer recommend) {
		this.recommend = recommend;
	}

	public Integer getIsNew() {
		return isNew;
	}

	public void setIsNew(Integer isNew) {
		this.isNew = isNew;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public String getClassifyId() {
		return classifyId;
	}

	public void setClassifyId(String classifyId) {
		this.classifyId = classifyId;
	}

	public String getClassifyName() {
		return classifyName;
	}

	public void setClassifyName(String classifyName) {
		this.classifyName = classifyName;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
}
