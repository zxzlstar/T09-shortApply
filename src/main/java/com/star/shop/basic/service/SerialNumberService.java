package com.star.shop.basic.service;

import com.star.shop.basic.entity.SerialNumber;
import com.star.shop.basic.repository.BaseRepository;
import com.star.shop.basic.repository.SerialNumberRepository;
import com.star.shop.basic.utils.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;


/**
 * 
 * 
 * <p>
 * Title:SerialNumberService
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
 *
 * @author x.zhang
 *
 * @date 2018年12月10日
 */
@Service
public class SerialNumberService extends BaseService<SerialNumber, String> {

	@Resource
	private SerialNumberRepository SerialNumberRepository;

	@Override
	protected BaseRepository<SerialNumber, String> getBaseRepository() {
		return this.SerialNumberRepository;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public synchronized String getSerialNumber(String serviceCode,boolean isAddTime, int len) {
		String date = DateUtils.format(new Date(), DateUtils.DATE_PATTERN);

		SerialNumber serialNumber = this.SerialNumberRepository.findByServiceCodeAndDate(serviceCode, date);
		if (serialNumber == null) {
			serialNumber = new SerialNumber();
			serialNumber.setCurrNo(1);
			serialNumber.setLen(len);
			serialNumber.setServiceCode(serviceCode);
			serialNumber.setDate(date);

			this.SerialNumberRepository.save(serialNumber);
		} else {
			serialNumber.setCurrNo(serialNumber.getCurrNo() + 1);
			serialNumber.setLen(len);
			this.SerialNumberRepository.save(serialNumber);
		}

		return this.getSerialNumber(serialNumber ,isAddTime);
	}
	
	private String getSerialNumber(SerialNumber serialNumber ,boolean isAddTime) {
		String no = String.valueOf(serialNumber.getCurrNo());
		while (no.length() < serialNumber.getLen()) {
			no = "0" + no;
		}
		if(isAddTime) {
			return serialNumber.getServiceCode() + DateUtils.format(new Date(), "yyyyMMddHHmmss") + no;
		}else {
			return serialNumber.getServiceCode() + DateUtils.format(new Date(), "yyyyMMdd") + no;
		}
	}

}
