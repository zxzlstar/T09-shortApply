package com.star.shop.admin.service;

import com.alibaba.fastjson.JSONObject;
import com.star.shop.admin.entity.Goods;
import com.star.shop.admin.entity.GoodsClassify;
import com.star.shop.admin.entity.GoodsSpecification;
import com.star.shop.admin.eventbus.BusinessEvent;
import com.star.shop.admin.eventbus.EventBusComponent;
import com.star.shop.admin.repository.GoodsClassifyRepository;
import com.star.shop.admin.repository.GoodsRepository;
import com.star.shop.admin.repository.GoodsSpecificationRepository;
import com.star.shop.admin.utils.EventTypeUtils;
import com.star.shop.admin.utils.SortUtils;
import com.star.shop.basic.component.FileComponent;
import com.star.shop.basic.repository.BaseRepository;
import com.star.shop.basic.service.BaseService;
import com.star.shop.basic.vo.ResultVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.*;


@Service
public class GoodsService extends BaseService<Goods, String> {

	private @Resource
	GoodsRepository goodsRepository;
	
	private @Resource
	GoodsClassifyRepository goodsClassifyRepository;
	
	private @Resource
	FileComponent fileComponent;
	
	private @Resource
	EventBusComponent eventBusComponent;

	
	private @Resource
	GoodsSpecificationRepository goodsSpecificationRepository;
	
	@Override
	protected BaseRepository<Goods, String> getBaseRepository() {
		// TODO Auto-generated method stub
		return goodsRepository;
	}

	public ResultVo listGoods(Map<String , Object> params) {
		int pageSize = Integer.parseInt(params.get("pageSize").toString()) ;
		int pageNumber = Integer.parseInt(params.get("pageNumber").toString()) ;
		PageRequest pageable = new PageRequest(pageNumber-1 ,pageSize, Direction.DESC, "ctime") ;
		
		Specification<Goods> spec = new Specification<Goods>() {
			@Override
			public Predicate toPredicate(Root<Goods> root,CriteriaQuery <?> query, CriteriaBuilder cb) {
				List<Predicate> predicate = new ArrayList<>();
				if(!StringUtils.isEmpty(params.get("name"))){
					predicate.add(cb.like(root.get("name"),"%"+ params.get("name")+"%")) ;
				}
				if(!StringUtils.isEmpty(params.get("status"))) {
					predicate.add(cb.equal(root.get("status"), params.get("status")));
				}
				predicate.add(cb.equal(root.get("isDelete"),params.get("isDelete")));
                Predicate[] pre = new Predicate[predicate.size()];
                query = query.where(predicate.toArray(pre)) ;
                return query.getRestriction() ;
			}
		};
		Page<Goods> page = this.goodsRepository.findAll(spec, pageable) ;
		List<Goods> list = page.getContent();
		for(Goods goods : list) {
			
			String[] pictureUrls = goods.getPicture().split(";");
			String pictureUrl = this.fileComponent.getImageUrl(pictureUrls[0]);
			goods.setPictureUrl(pictureUrl);
		}
		ResultVo resultVo = ResultVo.s(list) ;
		resultVo.setTotal(page.getTotalElements());
		return resultVo;
	}
	
	public ResultVo saveGoods(Map<String,Object> params) {
		Goods goods =JSONObject.parseObject(params.get("goods").toString(), Goods.class);
		//判断图片是否还没上传
		if(org.apache.commons.lang3.StringUtils.isBlank(goods.getPicture())) {
			return ResultVo.e(400, "图片还没上传");
		}
		//先获取商品类型
		GoodsClassify goodscf = this.goodsClassifyRepository.findById(goods.getClassifyId()).get();
		if(org.apache.commons.lang3.StringUtils.isBlank(goodscf.getPid())) {
			return ResultVo.e(400, "请选择二级商品类型");
		}
		goods.setClassify(goodscf);
		//设置库存
		
		this.goodsRepository.save(goods);
		if(goods.getIsSpecification() == 2) {
			int stock = 0; //定义库存（存在商品规格的话，就叠加所有商品规格库存存储在商品表中）
			if(!StringUtils.isEmpty(params.get("spec"))) {
				List<BigDecimal> listPirc = new ArrayList<BigDecimal>();
				List<BigDecimal> listOrignalPirc = new ArrayList<BigDecimal>();
				List<GoodsSpecification> listSpec = JSONObject.parseArray(params.get("spec").toString(),GoodsSpecification.class);
				//添加商品规格数据
				for (GoodsSpecification goodsSpecification : listSpec) {
					listPirc.add(goodsSpecification.getPrice());
					listOrignalPirc.add(goodsSpecification.getPrice());
					stock += goodsSpecification.getStock();
					goodsSpecification.setGoodsId(goods.getId());
					goodsSpecification.setGoodsName(goods.getName());
					this.goodsSpecificationRepository.save(goodsSpecification);
				}
				
				//价格升序以及获取第一个放在goods中
				Collections.sort(listPirc);
				Collections.sort(listOrignalPirc);
				goods.setPrice(listPirc.get(0));
				goods.setOriginalPrice(listOrignalPirc.get(0));
				
				//拼接最低到最高的价格组成一个范围值
				StringBuilder sb = new StringBuilder();
				sb.append(listPirc.get(0).setScale(2, BigDecimal.ROUND_DOWN));
				sb.append("-");
				sb.append(listPirc.get(listPirc.size()-1).setScale(2, BigDecimal.ROUND_DOWN));
				if(listPirc.get(0).equals(listPirc.get(listPirc.size()-1)))
					goods.setRangOfPrice(listPirc.get(0).setScale(2, BigDecimal.ROUND_DOWN).toString());
				else
					goods.setRangOfPrice(sb.toString());
			}
			goods.setStock(stock);

			
		}
		return ResultVo.s();
	}
	
	public ResultVo infoGoods(String id) {
		
		Goods goods = this.goodsRepository.findById(id).get();
		if(goods == null){
			return ResultVo.e(400 , "没有查询到数据") ;
		}
		goods.setClassifyId(goods.getClassify().getId());
		goods.setClassifyName(goods.getClassify().getName());


		String[] pictureUrls = goods.getPicture().split(";");
		List<String> listPic = new ArrayList<String>();
		for(String pic: pictureUrls) {
			String pictureUrl = this.fileComponent.getImageUrl(pic);
			listPic.add(pictureUrl);
		}
		String pictureUrl = org.apache.commons.lang3.StringUtils.join(listPic, ";");
		goods.setPictureUrl(pictureUrl);
		//获取商品对应的所有商品规格
		List<GoodsSpecification> listSpec = this.goodsSpecificationRepository.findByGoodsIdAndStatus(id,1);
		Map<String,Set<Object>> MapSp = new HashMap<String,Set<Object>>();
		for (GoodsSpecification goodsSpecification : listSpec) {
			Map<String,Object> mapSpec = JSONObject.parseObject(goodsSpecification.getParamsValue());
			for (String key : mapSpec.keySet()) {
				if(MapSp.get(key) != null) {
					Set<Object> set = MapSp.get(key);
					set.add(mapSpec.get(key));
				}else {
					Set<Object> set = new HashSet<Object>();
					set.add(mapSpec.get(key));
					MapSp.put(key, set);
				}
			}
		}
		//创建一个Map对象把上面的goods、listSpec、MapSp三组数据封装传递到前台
		Map<String,Object> allMap = new HashMap<String,Object>();
		allMap.put("goods", goods);
		allMap.put("listSpec", listSpec);
		allMap.put("selectSpec", MapSp);
		return ResultVo.s(allMap);
	}
	
	public ResultVo modifyGoods( Map<String,Object> params) {
		//判断图片和视频是否还没上传
		Goods goods =JSONObject.parseObject(params.get("goods").toString(), Goods.class);
		if(org.apache.commons.lang3.StringUtils.isBlank(goods.getPicture())) {
			return ResultVo.e(400, "图片还没上传");
		}
		GoodsClassify goodscf = this.goodsClassifyRepository.findById(goods.getClassifyId()).get();
		if(org.apache.commons.lang3.StringUtils.isBlank(goodscf.getPid())) {
			return ResultVo.e(400, "请选择一个二级商品类型");
		}
		Goods good = this.goodsRepository.findById(goods.getId()).get();
		
		if(good != null) {
			goods.setClassify(goodscf);
			if(goods.getIsSpecification() == 2) {

				//删除原来的规格项目
				this.goodsSpecificationRepository.deleteByGoodsId(goods.getId());

				int stock = 0; //定义库存（存在商品规格的话，就叠加所有商品规格库存存储在商品表中）
				if(!StringUtils.isEmpty(params.get("spec"))) {
					//获取前台传递过来的该商品的规格数据
					List<GoodsSpecification> listSpec = JSONObject.parseArray(params.get("spec").toString(),GoodsSpecification.class);
					for(GoodsSpecification gs:listSpec){
						gs.setGoodsId(goods.getId());
						gs.setGoodsName(goods.getName());
						gs.setStatus(1);
						gs.setOriginalPrice(gs.getPrice());
						this.goodsSpecificationRepository.save(gs);
					}

					List<BigDecimal> listPirc = new ArrayList<BigDecimal>();  //保存所有规格商品的价格
					List<BigDecimal> listOrignalPirc = new ArrayList<BigDecimal>();//保存所有规格商品的原价
					
					//添加商品规格数据
					for (GoodsSpecification goodsSpecification : listSpec) {
						listPirc.add(goodsSpecification.getPrice());
						listOrignalPirc.add(goodsSpecification.getOriginalPrice());
						stock += goodsSpecification.getStock();
						goodsSpecification.setGoodsId(goods.getId());
						goodsSpecification.setGoodsName(goods.getName());
						goodsSpecification.setStatus(1);
						this.goodsSpecificationRepository.save(goodsSpecification);
					}
					//价格升序以及获取第一个放在goods中
					Collections.sort(listPirc);
					Collections.sort(listOrignalPirc);
					goods.setPrice(listPirc.get(0));
					goods.setOriginalPrice(listOrignalPirc.get(0));
					//拼接最低到最高的价格组成一个范围值
					StringBuilder sb = new StringBuilder();
					sb.append(listPirc.get(0).setScale(2, BigDecimal.ROUND_DOWN));
					sb.append("-");
					sb.append(listPirc.get(listPirc.size()-1).setScale(2, BigDecimal.ROUND_DOWN));
					if(listPirc.get(0).equals(listPirc.get(listPirc.size()-1)))
						goods.setRangOfPrice(listPirc.get(0).setScale(2, BigDecimal.ROUND_DOWN).toString());
					else
						goods.setRangOfPrice(sb.toString());
				}
				goods.setStock(stock);
			}
			this.goodsRepository.save(goods);
		}else {
			return ResultVo.e(400, "数据不存在！！！");
		}
		return ResultVo.s();
	}
	
	public ResultVo removeGoods(String[] ids) {
		this.goodsRepository.removeGoods(1,ids);
		eventBusComponent.post(new BusinessEvent(EventTypeUtils.REMOVE_GOODS, ids));
		return ResultVo.s();
	}
	
	public ResultVo sellInGoods(String[] ids) {
		this.goodsRepository.sellInOrOutGoods("2", ids);
		eventBusComponent.post(new BusinessEvent(EventTypeUtils.GOODS_SELLIN, ids));
		return ResultVo.s();
	}
	
	public ResultVo sellOutGoods(String[] ids) {
		this.goodsRepository.sellInOrOutGoods("1", ids);
		eventBusComponent.post(new BusinessEvent(EventTypeUtils.GOODS_SELLOUT, ids));
		return ResultVo.s();
	}
	
	//置顶
	public ResultVo stickGoods(String[] ids) {
		this.goodsRepository.stickGoods(1, ids);
		return ResultVo.s();
	}
	
	//取消置顶
	public ResultVo ccelStickGoods(String[] ids) {
		this.goodsRepository.stickGoods(0, ids);
		return ResultVo.s();
	}
	
	//推荐
	public ResultVo recommendGoods(String[] ids) {
		this.goodsRepository.recommendGoods(1, ids);
		return ResultVo.s();
	}
	//取消推荐
	public ResultVo ccelRecommendGoods(String[] ids) {
		this.goodsRepository.recommendGoods(0, ids);
		return ResultVo.s();
	}
	
	public List<Goods> listGoodsByParams(Map<String, String> params){
		
		String classify = StringUtils.isEmpty(params.get("classify"))?"":params.get("classify").toString();
		String type = StringUtils.isEmpty(params.get("type"))?"":params.get("type").toString();
		GoodsClassify goodscf = this.goodsClassifyRepository.findById(classify).get();
		int pageSize = Integer.parseInt(params.get("pageSize").toString()) ;
		int pageNumber = Integer.parseInt(params.get("pageNumber").toString()) ;
		if(pageNumber <= 0) {
			pageNumber = 1; 
		}
		if(pageSize <= 0) {
			pageSize = 10;
		}
		String sortStr = StringUtils.isEmpty(params.get("sort"))?"1":params.get("sort").toString();
		int sort = Integer.parseInt(sortStr);
		PageRequest pageable = null;
		if(sort == SortUtils.ALL_CODE) {
			pageable = new PageRequest(pageNumber-1 ,pageSize, new Sort(
					new Order(Direction.DESC, SortUtils.STICK_CONTENT),
					new Order(Direction.DESC, SortUtils.RECOMMEND_CONTENT),
					new Order(Direction.DESC, SortUtils.CTIME_CONTENT),
					new Order(Direction.ASC, SortUtils.PRICE_CONTENT),
					new Order(Direction.DESC, SortUtils.SALES_CONTENT))) ;
		}else if(sort == SortUtils.CTIME_CODE) {
			pageable = new PageRequest(pageNumber-1 ,pageSize, new Sort(
					new Order(Direction.DESC, SortUtils.STICK_CONTENT),
					new Order(Direction.DESC, SortUtils.RECOMMEND_CONTENT),
					new Order(Direction.DESC,SortUtils.CTIME_CONTENT)
					));
		}else if(sort == SortUtils.PRICE_ASC_CODE) {
			pageable = new PageRequest(pageNumber-1 ,pageSize, new Sort(
					new Order(Direction.DESC, SortUtils.STICK_CONTENT),
					new Order(Direction.DESC, SortUtils.RECOMMEND_CONTENT),
					new Order(Direction.ASC,SortUtils.PRICE_CONTENT)
					));
		}else if(sort == SortUtils.PRICE_DESC_CODE) {
			pageable = new PageRequest(pageNumber-1 ,pageSize, new Sort(
					new Order(Direction.DESC, SortUtils.STICK_CONTENT),
					new Order(Direction.DESC, SortUtils.RECOMMEND_CONTENT),
					new Order(Direction.DESC,SortUtils.PRICE_CONTENT)
					));
		}else if(sort == SortUtils.SALES_ASC_CODE) {
			pageable = new PageRequest(pageNumber-1 ,pageSize, new Sort(
					new Order(Direction.DESC, SortUtils.STICK_CONTENT),
					new Order(Direction.DESC, SortUtils.RECOMMEND_CONTENT),
					new Order(Direction.ASC,SortUtils.SALES_CONTENT)
					));
		}else if(sort == SortUtils.SALES_DESC_CODE) {
			pageable = new PageRequest(pageNumber-1 ,pageSize, new Sort(
					new Order(Direction.DESC, SortUtils.STICK_CONTENT),
					new Order(Direction.DESC, SortUtils.RECOMMEND_CONTENT),
					new Order(Direction.DESC,SortUtils.SALES_CONTENT)
					));
		}
		Specification<Goods> spec = new Specification<Goods>() {
			@Override
			public Predicate toPredicate(Root<Goods> root,CriteriaQuery <?> query, CriteriaBuilder cb) {
				List<Predicate> predicate = new ArrayList<>();
				if(goodscf != null && org.apache.commons.lang3.StringUtils.isNotBlank(goodscf.getId())){
					predicate.add(cb.equal(root.get("classify"), goodscf)) ;
				}
				if(org.apache.commons.lang3.StringUtils.isNotBlank(type)) {
					predicate.add(cb.equal(root.get("type"), type));
				}
				predicate.add(cb.equal(root.get("status"),"2"));
				predicate.add(cb.equal(root.get("isDelete"),"0"));
                Predicate[] pre = new Predicate[predicate.size()];
                query = query.where(predicate.toArray(pre)) ;
                return query.getRestriction() ;
			}
		};
		
		Page<Goods> page = this.goodsRepository.findAll(spec, pageable) ;
		List<Goods> list = page.getContent();
		return list;
	}
	
	public Goods infoGoodsById(String id) {
		Goods goods = this.goodsRepository.findById(id).get();
		return goods;
	}
	
	public Goods findById(String id) {
		return this.goodsRepository.findById(id).get();
	}
	
	
	public ResultVo listGoodsByClassifyAndIsSpecification(String classifyId){
		GoodsClassify classify = this.goodsClassifyRepository.findById(classifyId).get();
		if(classify != null) {
			List<Goods> goodsList = this.goodsRepository.findByClassifyAndIsSpecificationAndIsDelete(classify, 2, 0);
			return ResultVo.s(goodsList);
		}
		return ResultVo.e(400,"没有找到该商品类别");
	}
}
