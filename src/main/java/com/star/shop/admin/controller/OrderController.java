package com.star.shop.admin.controller;

import com.star.shop.admin.service.OrderService;
import com.star.shop.basic.controller.BaseController;
import com.star.shop.basic.vo.ResultVo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/shop/order")
public class OrderController extends BaseController {

    private @Resource
    OrderService orderService;

    @RequestMapping(value = "/list" , method= {RequestMethod.GET,RequestMethod.POST})
    public ResultVo listOrder(@RequestBody Map<String, Object> params) {
        return this.orderService.listOrder(params);
    }
}
