package com.star.shop.admin.controller;

import com.star.shop.admin.entity.GoodsClassify;
import com.star.shop.admin.service.GoodsClassifyService;
import com.star.shop.basic.annotation.Log;
import com.star.shop.basic.controller.BaseController;
import com.star.shop.basic.vo.ResultVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;



@RestController
@RequestMapping(value = "/shop/goodscf")
public class GoodsClassifyController extends BaseController {

	private @Resource
	GoodsClassifyService goodsClassifyService;
	
	@RequestMapping(value = "/list" , method = {RequestMethod.GET ,RequestMethod.POST})
	public ResultVo listGooscf(@RequestParam(value="treeType",required=false,defaultValue="1") String treeType, @RequestParam(value="id",required=false,defaultValue="") String id){
		return this.goodsClassifyService.listGoodscf(treeType,id);
	}
	
	@Log("商品类别新增")
	@RequestMapping(value = "/save" ,method = {RequestMethod.POST})
	public ResultVo saveGoodscf(@RequestBody GoodsClassify goodscf) {
		return this.goodsClassifyService.saveGoodscf(goodscf);
	}
	
	/**
	 * 根据id查询商品类别
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/info" ,method = {RequestMethod.POST})
	public ResultVo infoGoodscf(@RequestBody String id) {
		return this.goodsClassifyService.infoGoodscf(id);
	}
	
	/**
	 * 
	 * @param goodscf
	 * @return
	 */
	@Log("菜单修改")
	@RequestMapping(value = "/modify" ,method = {RequestMethod.POST})
	public ResultVo modifyGoodscf(@RequestBody GoodsClassify goodscf) {
		return this.goodsClassifyService.modifyGoodscf(goodscf);
	}
	
	/**
	 * 
	 * @param ids
	 * @return
	 */
	@Log("菜单删除")
	@RequestMapping(value = "/remove" ,method = {RequestMethod.POST})
	public ResultVo removeGoodscf(@RequestBody String[] ids) {
		return this.goodsClassifyService.removeGoodscf(ids);
	}
}
