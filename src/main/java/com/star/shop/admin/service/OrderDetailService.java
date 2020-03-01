package com.star.shop.admin.service;

import com.star.shop.admin.entity.OrderDetail;
import com.star.shop.admin.repository.OrderDetailRepository;
import com.star.shop.basic.repository.BaseRepository;
import com.star.shop.basic.service.BaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrderDetailService extends BaseService<OrderDetail, String> {

    private @Resource
    OrderDetailRepository orderDetailRepository;

    @Override
    protected BaseRepository<OrderDetail, String> getBaseRepository() {
        return orderDetailRepository;
    }

    public List<OrderDetail> findByOid(String oid){
        return orderDetailRepository.findByOid(oid);
    }
}
