package com.star.shop.admin.vo.express;

import java.util.List;

/**
 * @author Cyan
 * @date 2021年1月27日
 */
public class KdYuanTongSearchResultVo {
	
	private String waybillNo; // 快递单号
	
	private boolean isS; // 是否运输中，是否已收件
	
	private String empCodeS;
	
	private String empNameS;
	
	private String stationCodeS;
	
	private String stationNameS;
	
	private boolean isP;
	
	private String empCodeP;
	
	private String empNameP;
	
	private String stationCodeP;
	
	private String stationNameP;
	
	private boolean isC; // 是否异常
	
	private boolean isQ;  // 是否签收
	
	private boolean isD;
	
	private List<YuanTongLogisticVo> traces;

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public boolean isS() {
		return isS;
	}

	public void setS(boolean isS) {
		this.isS = isS;
	}

	public String getEmpCodeS() {
		return empCodeS;
	}

	public void setEmpCodeS(String empCodeS) {
		this.empCodeS = empCodeS;
	}

	public String getEmpNameS() {
		return empNameS;
	}

	public void setEmpNameS(String empNameS) {
		this.empNameS = empNameS;
	}

	public String getStationCodeS() {
		return stationCodeS;
	}

	public void setStationCodeS(String stationCodeS) {
		this.stationCodeS = stationCodeS;
	}

	public String getStationNameS() {
		return stationNameS;
	}

	public void setStationNameS(String stationNameS) {
		this.stationNameS = stationNameS;
	}

	public boolean isP() {
		return isP;
	}

	public void setP(boolean isP) {
		this.isP = isP;
	}

	public String getEmpCodeP() {
		return empCodeP;
	}

	public void setEmpCodeP(String empCodeP) {
		this.empCodeP = empCodeP;
	}

	public String getEmpNameP() {
		return empNameP;
	}

	public void setEmpNameP(String empNameP) {
		this.empNameP = empNameP;
	}

	public String getStationCodeP() {
		return stationCodeP;
	}

	public void setStationCodeP(String stationCodeP) {
		this.stationCodeP = stationCodeP;
	}

	public String getStationNameP() {
		return stationNameP;
	}

	public void setStationNameP(String stationNameP) {
		this.stationNameP = stationNameP;
	}

	public boolean isC() {
		return isC;
	}

	public void setC(boolean isC) {
		this.isC = isC;
	}

	public boolean isQ() {
		return isQ;
	}

	public void setQ(boolean isQ) {
		this.isQ = isQ;
	}

	public boolean isD() {
		return isD;
	}

	public void setD(boolean isD) {
		this.isD = isD;
	}

	public List<YuanTongLogisticVo> getTraces() {
		return traces;
	}

	public void setTraces(List<YuanTongLogisticVo> traces) {
		this.traces = traces;
	}
	
}
