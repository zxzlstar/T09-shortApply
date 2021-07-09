/**
 * 
 */
package com.star.shop.admin.entity.express;

import java.text.DateFormat;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.star.shop.admin.utils.express.DateUtils;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author cyan
 * 国内快递的物流主体信息
 *
 */
@Entity
@Table(name = "express_hundred_info")
@DynamicInsert
@DynamicUpdate
public class ExpressInfo extends AbstractEntity {
    /**
     * 时间，原始格式
     */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8") 
    private Date time;

    /**
     * 内容
     */
	@Column(columnDefinition = "varchar(1024) default ''")
    private String context;

    /**
     * 格式化后时间
     */
    @Transient
    @JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    private Date ftime;

	@Transient
	private String TimeCn;

    /**
     * 这个字段好像文档没给描述, 但是接口返回了
     */
    @Column(columnDefinition = "varchar(200) default ''")
    private String location;
    
    @Column(columnDefinition = "varchar(50) default ''")
    private String pid;

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public Date getFtime() {
		return ftime;
	}

	public void setFtime(Date ftime) {
		this.ftime = ftime;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	

	public String getPid() {
		return pid;
	}


	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getTimeCn() {
		if (Objects.nonNull(this.time)) {
			return DateUtils.format(this.time, DateUtils.DATE_TIME_PATTERN);
		}
		return null;
	}
}