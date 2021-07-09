package com.star.shop.admin.vo.express;

import java.util.Date;
import java.util.List;

/**
 * @author Cyan
 * @date 2021年1月11日
 */
public class KdBaiduSearchResultVo {
	
	private Integer status;
	
	private Integer state; //
	
	private String com; // 物流商
	
	private List<LogisticVo> context; // 轨迹信息
	
	private Date send_time;  // 揽收时间
	
	private String latest_progress; // 物流的最新信息
	
	private String _source_com; // 源快递公司
	
	private String _support_from;
	
	private Integer isAbstract;
	
	private String current;  // 当前状态
	
	private Integer currentStatus; // 当前状态编码
	
	private String latest_time; // "进展更新时间"

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

	public String getCom() {
		return com;
	}

	public void setCom(String com) {
		this.com = com;
	}

	public List<LogisticVo> getContext() {
		return context;
	}

	public void setContext(List<LogisticVo> context) {
		this.context = context;
	}

	public Date getSend_time() {
		return send_time;
	}

	public void setSend_time(Date send_time) {
		this.send_time = send_time;
	}

	public String getLatest_progress() {
		return latest_progress;
	}

	public void setLatest_progress(String latest_progress) {
		this.latest_progress = latest_progress;
	}

	public String get_source_com() {
		return _source_com;
	}

	public void set_source_com(String _source_com) {
		this._source_com = _source_com;
	}

	public String get_support_from() {
		return _support_from;
	}

	public void set_support_from(String _support_from) {
		this._support_from = _support_from;
	}

	public Integer getIsAbstract() {
		return isAbstract;
	}

	public void setIsAbstract(Integer isAbstract) {
		this.isAbstract = isAbstract;
	}

	public String getCurrent() {
		return current;
	}

	public void setCurrent(String current) {
		this.current = current;
	}

	public Integer getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(Integer currentStatus) {
		this.currentStatus = currentStatus;
	}

	public String getLatest_time() {
		return latest_time;
	}

	public void setLatest_time(String latest_time) {
		this.latest_time = latest_time;
	}
	
}
