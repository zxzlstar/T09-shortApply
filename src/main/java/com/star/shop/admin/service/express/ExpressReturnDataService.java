/**
 * 
 */
package com.star.shop.admin.service.express;


import com.star.shop.admin.entity.express.ExpressReturnData;
import com.star.shop.basic.vo.ResultVo;

import java.util.Map;

/**
 * @author cyan
 *
 */
public interface ExpressReturnDataService extends BaseService<ExpressReturnData, String> {
	
	/**
	 * 保存物流的主体和明细信息
	 * @return 返回已经爬取的单号
	 */
	public String saveDataAndInfo(ExpressReturnData data);

	/**
	 * 查询所有已经爬取到的物流信息
	 * @param params
	 * @return
	 */
    ResultVo listExpress(Map<String, Object> params);
}
