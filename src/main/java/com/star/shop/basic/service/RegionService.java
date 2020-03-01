package com.star.shop.basic.service;

import com.star.shop.basic.entity.Region;
import com.star.shop.basic.repository.BaseRepository;
import com.star.shop.basic.repository.RegionRepository;
import com.star.shop.basic.vo.ResultVo;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * <p>Title:RegionService</p>
 *
 * <p>Description:</p>
 *
 * <p>Company:</p>
 *
 * @author x.zhang
 *
 * @date 2018年8月23日
 */
@Service
public class RegionService extends BaseService<Region, String> {
	private @Resource
	RegionRepository regionRepository ;

	@Override
	protected BaseRepository<Region, String> getBaseRepository() {
		return regionRepository;
	}

	public ResultVo listRegion(Map<String,Object> params) {
		Specification<Region> spec = new Specification<Region>() {
			@Override
			public Predicate toPredicate(Root<Region> root,CriteriaQuery <?> query, CriteriaBuilder cb) {
				List<Predicate> predicate = new ArrayList<>();
				if(!StringUtils.isEmpty(params.get("name"))){
					predicate.add(cb.like(root.get("name"),"%"+ params.get("name")+"%")) ;
				}
				if(!StringUtils.isEmpty(params.get("pcode"))){
					predicate.add(cb.equal(root.get("pcode"), ObjectUtils.nullSafeToString(params.get("pcode")))) ;
				}
				
                Predicate[] pre = new Predicate[predicate.size()];
                query = query.where(predicate.toArray(pre)) ;
                return query.getRestriction() ;
			}
		};
		
		List<Region> list = this.regionRepository.findAll(spec) ;
		return ResultVo.s(list);
	}

	public ResultVo selectRegion(String code) {
		List<Region> list = this.regionRepository.findByPcode(code) ;
		return ResultVo.s(list);
	}

	public ResultVo infoRegion(String id) {
		Region region = this.regionRepository.findById(id).get() ;
		if(region == null){
			return ResultVo.e(400 , "没有查询到数据") ;
		}
		return ResultVo.s(region);
	}

	public ResultVo saveRegion(Region region) {
		Region tmp = this.regionRepository.findByCode(region.getCode()) ;
		if(tmp != null){
			return ResultVo.e(400 , "区域编号已经存在");
		}
		this.regionRepository.save(region) ;
		return ResultVo.s();
	}

	public ResultVo modifyRegion(Region region) {
		Region tmp = this.regionRepository.findByCode(region.getCode()) ;
		if(tmp != null && !region.getId().equals(tmp.getId())){
			return ResultVo.e(400 , "区域编号已经存在");
		}
		this.regionRepository.save(region) ;
		return ResultVo.s();
	}

	public ResultVo removeRegion(String[] ids) {
		for(String id : ids){
			Region region = this.regionRepository.findById(id).get() ;
			if(this.regionRepository.findByPcode(region.getCode()).size() > 0){
				return ResultVo.e(400 , "有子区域的不能删除") ;
			}
			this.regionRepository.deleteById(id);
		}
		return ResultVo.s();
	}

//	public ResultVo init() {
////		List<Object[]> data = this.regionRepository.area() ;
////		for(Object[] obj : data){
////			Region region = new Region() ;
////			region.setCode(ObjectUtils.nullSafeToString(obj[1]));
////			region.setPcode(ObjectUtils.nullSafeToString(obj[2]));
////			region.setName(ObjectUtils.nullSafeToString(obj[3]));
////			
////			region.setLayer(Integer.parseInt(ObjectUtils.nullSafeToString(obj[4])));
////			region.setOrderNum(Integer.parseInt(ObjectUtils.nullSafeToString(obj[5])));
////			region.setStatus(Integer.parseInt(ObjectUtils.nullSafeToString(obj[6])));
////			region.setRemark(ObjectUtils.nullSafeToString(obj[7]));
////			
////			this.regionRepository.save(region) ;
////		}
//		
//		List<Region> list = this.regionRepository.findAll() ;
//		for(Region region : list){
//			if("0".equals(region.getPcode())){
//				region.setPath(region.getId());
//			}else{
//				Region parent = this.regionRepository.findByCode(region.getPcode()) ;
//				region.setPath(region.getPath() + Constants.DEFAULT_DELIMITER + region.getId());
//				region.setPid(parent.getId());
//				region.setPname(parent.getName());
//			}
//			regionRepository.save(region) ;
//		}
//		
//		
//		return ResultVo.s();
//	}


}
