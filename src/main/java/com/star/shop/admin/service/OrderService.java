package com.star.shop.admin.service;

import com.star.shop.admin.entity.Order;
import com.star.shop.admin.repository.OrderRepository;
import com.star.shop.basic.repository.BaseRepository;
import com.star.shop.basic.service.BaseService;
import com.star.shop.basic.vo.ResultVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

;

@Service
public class OrderService extends BaseService<Order, String> {

    private @Resource
    OrderRepository orderRepository;

    @Override
    protected BaseRepository<Order, String> getBaseRepository() {
        return orderRepository;
    }


    public ResultVo listOrder(Map<String, Object> params) {
        int pageSize = Integer.parseInt(params.get("pageSize").toString()) ;
        int pageNumber = Integer.parseInt(params.get("pageNumber").toString()) ;

        PageRequest pageable = new PageRequest(pageNumber-1 ,pageSize, new Sort(new Sort.Order(Direction.DESC, "date"), new Sort.Order(Direction.DESC, "time"))) ;
        Specification<Order> spec = new Specification<Order>() {
            @Override
            public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicate = new ArrayList<>();
                if(!StringUtils.isEmpty(params.get("oid"))){
                    predicate.add(cb.like(root.get("oid"),"%"+ params.get("oid")+"%")) ;
                }
                if(!StringUtils.isEmpty(params.get("transportNo"))) {
                    predicate.add(cb.like(root.get("transportNo"),"%"+ params.get("transportNo")+"%"));
                }
                if(!StringUtils.isEmpty(params.get("dateStr"))) {
                    String[] date = params.get("dateStr").toString().split(" - ");
                    predicate.add(cb.between(root.get("date"), date[0], date[1]));
                }
                if(!StringUtils.isEmpty(params.get("status"))) {
                    predicate.add(cb.equal(root.get("status"), params.get("status")));
                }
                Predicate[] pre = new Predicate[predicate.size()];
                query = query.where(predicate.toArray(pre)) ;
                return query.getRestriction() ;
            }
        };

        Page<Order> page = this.orderRepository.findAll(spec, pageable) ;
        ResultVo resultVo = ResultVo.s(page.getContent());
        resultVo.setTotal(page.getTotalElements());
        return resultVo;
    }
}
