package com.star.shop.admin.repository;

import com.star.shop.admin.entity.Order;
import com.star.shop.basic.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends BaseRepository<Order, String> {

    @Query(value = "select o from Order o where mid = :mid order by ctime desc")
    public List<Order> findByMid(@Param("mid")String mid);


}
