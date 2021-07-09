package com.star.shop.admin.service.express;

import com.star.shop.admin.entity.express.ExpressCompany;

import java.util.List;

public interface ExpressCompanyService extends BaseService<ExpressCompany, Integer> {
    List<ExpressCompany> getExpressCompanyList();
}
