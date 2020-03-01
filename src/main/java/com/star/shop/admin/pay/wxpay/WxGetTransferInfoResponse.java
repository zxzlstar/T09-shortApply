package com.star.shop.admin.pay.wxpay;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * 
 * 
 * <p>
 * Title:WxGetTransferInfoResponse
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
 * @author moxf
 *
 * @date 2018年6月7日
 */
@XmlRootElement(name = "xml")
public class WxGetTransferInfoResponse extends WxProtocolResponse {
	/**
	 * 业务结果
	 */
	private String result_code;

	/**
	 * 错误代码
	 */
	private String err_code;

	/**
	 * 错误代码描述
	 */
	private String err_code_des;

	/**
	 * 商户单号
	 */
	private String partner_trade_no;

	/**
	 * 商户号
	 */
	private String mch_id;

	/**
	 * 付款单号
	 */
	private String detail_id;
	/**
	 * 转账状态 SUCCESS:转账成功 FAILED:转账失败 PROCESSING:处理中
	 */
	private String status;

	/**
	 * 失败原因
	 */
	private String reason;
	/**
	 * 收款用户
	 */
	private String openid;

	/**
	 * 收款用户姓名
	 */
	private String transfer_name;

	/**
	 * 付款金额
	 */
	private String payment_amount;

	/**
	 * 转账时间
	 */
	private String transfer_time;

	/**
	 * 付款描述
	 */
	private String desc;

	public String getResult_code() {
		return result_code;
	}

	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}

	public String getErr_code() {
		return err_code;
	}

	public void setErr_code(String err_code) {
		this.err_code = err_code;
	}

	public String getErr_code_des() {
		return err_code_des;
	}

	public void setErr_code_des(String err_code_des) {
		this.err_code_des = err_code_des;
	}

	public String getPartner_trade_no() {
		return partner_trade_no;
	}

	public void setPartner_trade_no(String partner_trade_no) {
		this.partner_trade_no = partner_trade_no;
	}

	public String getMch_id() {
		return mch_id;
	}

	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}

	public String getDetail_id() {
		return detail_id;
	}

	public void setDetail_id(String detail_id) {
		this.detail_id = detail_id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getTransfer_name() {
		return transfer_name;
	}

	public void setTransfer_name(String transfer_name) {
		this.transfer_name = transfer_name;
	}

	public String getPayment_amount() {
		return payment_amount;
	}

	public void setPayment_amount(String payment_amount) {
		this.payment_amount = payment_amount;
	}

	public String getTransfer_time() {
		return transfer_time;
	}

	public void setTransfer_time(String transfer_time) {
		this.transfer_time = transfer_time;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
