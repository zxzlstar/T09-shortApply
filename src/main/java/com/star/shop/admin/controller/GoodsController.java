package com.star.shop.admin.controller;


import com.star.shop.admin.service.GoodsService;
import com.star.shop.basic.controller.BaseController;
import com.star.shop.basic.service.UploadService;
import com.star.shop.basic.vo.ResultVo;
import com.star.shop.basic.annotation.Log;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;


@RestController
@RequestMapping(value = "/shop/goods")
public class GoodsController extends BaseController {

	private @Resource
	GoodsService goodsService;

	private @Resource
	UploadService uploadService;

	@RequestMapping(value = "/list", method = { RequestMethod.POST })
	public ResultVo listGoods(@RequestBody Map<String, Object> params) {
		params.put("isDelete", 0);
		return this.goodsService.listGoods(params);
	}

	@Log("新增商品")
	@RequestMapping(value = "save", method = { RequestMethod.POST })
	public ResultVo saveGoods(@RequestBody Map<String,Object> params) {
		return this.goodsService.saveGoods(params);
	}

	@RequestMapping(value = "info", method = { RequestMethod.GET, RequestMethod.POST })
	public ResultVo infoGoods(@RequestBody String id) {
		return this.goodsService.infoGoods(id);
	}

	@Log("商品编辑")
	@RequestMapping(value = "/modify", method = { RequestMethod.POST })
	public ResultVo modifyGoods(@RequestBody Map<String,Object> params) {
		return this.goodsService.modifyGoods(params);
	}

	@Log("删除商品")
	@RequestMapping(value = "/remove", method = { RequestMethod.POST })
	public ResultVo removeGoods(@RequestBody String[] ids) {
		return this.goodsService.removeGoods(ids);
	}

	@Log("商品上架")
	@RequestMapping(value = "/sellIn", method = { RequestMethod.POST })
	public ResultVo sellInGoods(@RequestBody String[] ids) {
		return this.goodsService.sellInGoods(ids);
	}

	@Log("商品下架")
	@RequestMapping(value = "/sellOut", method = { RequestMethod.POST })
	public ResultVo sellOutGoods(@RequestBody String[] ids) {
		return this.goodsService.sellOutGoods(ids);
	}

	@Log("商品置顶")
	@RequestMapping(value = "/stick", method = { RequestMethod.POST })
	public ResultVo stickGoods(@RequestBody String[] ids) {
		return this.goodsService.stickGoods(ids);
	}

	@Log("商品取消置顶")
	@RequestMapping(value = "/ccelStick", method = { RequestMethod.POST })
	public ResultVo ccelStickGoods(@RequestBody String[] ids) {
		return this.goodsService.ccelStickGoods(ids);
	}

	@Log("商品推荐")
	@RequestMapping(value = "/recommend", method = { RequestMethod.POST })
	public ResultVo recommendGoods(@RequestBody String[] ids) {
		return this.goodsService.recommendGoods(ids);
	}

	@Log("商品取消推荐")
	@RequestMapping(value = "/ccelRecommend", method = { RequestMethod.POST })
	public ResultVo ccelRecommendGoods(@RequestBody String[] ids) {
		return this.goodsService.ccelRecommendGoods(ids);
	}

	@Log("获取有商品规格的商品列表")
	@RequestMapping(value = "/isSpecGoods", method = { RequestMethod.POST })
	public ResultVo isSpecGoodsList(@RequestParam String classifyId) {
		return this.goodsService.listGoodsByClassifyAndIsSpecification(classifyId);
	}
}
