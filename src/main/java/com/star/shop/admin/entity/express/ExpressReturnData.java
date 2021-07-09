package com.star.shop.admin.entity.express;

import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.star.shop.admin.enums.ExpressStateType;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 国内快递的物流明细信息
 *
 * @author cyan
 */
@Entity
@Table(name = "express_hundred_detail_info")
@DynamicInsert
@DynamicUpdate
public class ExpressReturnData extends AbstractEntity {

    /**
     * 消息体
     */
	@Column(columnDefinition = "varchar(1024) default ''")
    private String message;

    /**
     * 单号
     */
	@Column(name = "number", columnDefinition = "varchar(100) default ''")
    private String nu;

    /**
     * 是否签收标记，请忽略，明细状态请参考state字段
     */
	@Column(name = "isCheck", columnDefinition = "int default 0")
    private Integer ischeck;

    /**
     * 快递单明细状态标记，暂未实现，请忽略
     */
	@Column(name = "sign", columnDefinition = "varchar(100) default ''")
    private String condition;

    /**
     * 快递公司编码,一律用小写字母
     */
	@Column(name= "company", columnDefinition = "varchar(100) default ''")
    private String com;

    /**
     * 通讯状态, 请忽略
     */
	@Column(columnDefinition = "int default 0")
    private Integer status;

    /**
     * 快递单当前状态，
     * 包括0在途，1揽收，2疑难，3签收，4退签，5派件，6退回等7个状态
     */
    @Column(columnDefinition = "int default 0")
    private Integer state;
    
    /**
     * 派送电话
     */
    @Column(name= "sender_phone", columnDefinition = "varchar(15) default ''")
    private String senderPhone;
    
    /**
     * 是否省内，0否，1是
     */
    @Column(columnDefinition = "int default 0")
    private Integer isProvince;

    /**
     * 最新查询结果，数组，包含多项，全量，倒序（即时间最新的在最前）
     */
    @Transient
    private List<ExpressInfo> data;

    @Transient
    private String companyName;

    @Transient
    private String stateCn;

    // Get / Set

    public String getMessage() {
		return message;
	}



	public void setMessage(String message) {
		this.message = message;
	}



	public String getNu() {
		return nu;
	}



	public void setNu(String nu) {
		this.nu = nu;
	}



	public Integer getIscheck() {
		return ischeck;
	}


	public void setIscheck(Integer ischeck) {
		this.ischeck = ischeck;
	}



	public String getCondition() {
		return condition;
	}



	public void setCondition(String condition) {
		this.condition = condition;
	}



	public String getCom() {
		return com;
	}



	public void setCom(String com) {
		this.com = com;
	}



	public Integer getStatus() {
		return status;
	}



	public void setStatus(Integer status) {
		this.status = status;
	}



	public Integer getState() {
		return state;
	}



	public void setState(Integer state) {
		this.state = state;
	}


	public List<ExpressInfo> getData() {
		return data;
	}


	public void setData(List<ExpressInfo> data) {
		this.data = data;
	}


	public String getSenderPhone() {
		return senderPhone;
	}


	public void setSenderPhone(String senderPhone) {
		this.senderPhone = senderPhone;
	}


	public Integer getIsProvince() {
		return isProvince;
	}


	public void setIsProvince(Integer isProvince) {
		this.isProvince = isProvince;
	}

	public String getStateCn() {
    	if (Objects.nonNull(this.state))
			this.stateCn = ExpressStateType.fromCode(this.state).getMsg();
		return stateCn;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
}

