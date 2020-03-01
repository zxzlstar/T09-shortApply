package com.star.shop.admin.repository;

import com.star.shop.admin.entity.OrderDetail;
import com.star.shop.basic.repository.BaseRepository;

import java.util.List;

public interface OrderDetailRepository extends BaseRepository<OrderDetail, String> {

    public List<OrderDetail> findByOid(String oid);
}
