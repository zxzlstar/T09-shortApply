package com.star.shop.admin.controller;

import com.star.shop.admin.entity.SpecificationItem;
import com.star.shop.admin.service.SpecitemService;
import com.star.shop.basic.annotation.Log;
import com.star.shop.basic.controller.BaseController;
import com.star.shop.basic.vo.ResultVo;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping(value = "/shop/specitem")
public class SpecitemController extends BaseController {

    private @Resource
    SpecitemService specitemService;

    /**
     * 获取specitem列表
     * @return
     */
    @RequestMapping(value = "/list" , method = {RequestMethod.GET ,RequestMethod.POST})
    public ResultVo listSpecitem(@RequestBody Map<String , Object> params) {
        return this.specitemService.listSpecitem(params);

    }

    /**
     * 新增规格项
     * @param specificationItem
     * @return
     */
    @Log("规格项新增")
    @RequestMapping(value = "/save" , method = {RequestMethod.POST})
    public ResultVo saveSpecitem(@RequestBody SpecificationItem specificationItem) {
        this.specitemService.save(specificationItem);
        return ResultVo.s();
    }

    /**
     * 查看规格项
     * @param id
     * @return
     */
    @Log("规格项信息")
    @RequestMapping(value = "/info" ,  method = {RequestMethod.GET ,RequestMethod.POST})
    public ResultVo infoSpecitem(@RequestBody String id) {
        SpecificationItem specificationItem = new SpecificationItem();
        specificationItem.setId(id);
        try {
            specificationItem = this.specitemService.findOne(Example.of(specificationItem)).get();
        }catch(Exception e){
            return ResultVo.e(400 , "没有查询到数据") ;
        }
        return ResultVo.s(specificationItem);
    }

    @Log("规格项编辑")
    @RequestMapping(value = "/modify" , method = {RequestMethod.POST})
    public ResultVo modifySpecitem(@RequestBody SpecificationItem specificationItem) {
        return this.specitemService.modifySpecitem(specificationItem);
    }

    @Log("规格项删除")
    @RequestMapping(value = "/remove" , method = {RequestMethod.POST})
    public ResultVo removeBanner(@RequestBody String[] ids) {
        return this.specitemService.removeSpecitem(ids);
    }


    @Log("所有规格项列表")
    @RequestMapping(value = "/listSpeciTree" , method = {RequestMethod.GET ,RequestMethod.POST})
    public ResultVo listSpecitemTree(){
        return this.specitemService.findSpecitemAll();
    }

}
