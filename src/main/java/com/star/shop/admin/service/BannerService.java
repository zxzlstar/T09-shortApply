package com.star.shop.admin.service;

import com.star.shop.admin.entity.Banner;
import com.star.shop.admin.repository.BannerRepository;
import com.star.shop.basic.component.FileComponent;
import com.star.shop.basic.repository.BaseRepository;
import com.star.shop.basic.repository.UploadRepository;
import com.star.shop.basic.service.BaseService;
import com.star.shop.basic.vo.ResultVo;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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


@Service
public class BannerService extends BaseService<Banner, String> {

	private @Resource
	BannerRepository bannerRepository;
	
	private @Resource
	UploadRepository uploadRepository;
	
	private @Resource
	FileComponent fileComponent;

	@Override
	protected BaseRepository<Banner, String> getBaseRepository() {
		// TODO Auto-generated method stub
		return this.bannerRepository;
	}
	
	public ResultVo listBanner(Map<String , Object> params) {
		int pageSize = Integer.parseInt(params.get("pageSize").toString()) ;
		int pageNumber = Integer.parseInt(params.get("pageNumber").toString()) ;
		PageRequest pageable = new PageRequest(pageNumber-1 ,pageSize, Direction.DESC, "ctime") ;
		
		Specification<Banner> spec = new Specification<Banner>() {
			@Override
			public Predicate toPredicate(Root<Banner> root,CriteriaQuery <?> query, CriteriaBuilder cb) {
				List<Predicate> predicate = new ArrayList<>();
				if(!StringUtils.isEmpty(params.get("name"))){
					predicate.add(cb.like(root.get("name"),"%"+ params.get("name")+"%")) ;
				}
				if(!StringUtils.isEmpty(params.get("position"))) {
					predicate.add(cb.equal(root.get("position"), params.get("position")));
				}
                Predicate[] pre = new Predicate[predicate.size()];
                query = query.where(predicate.toArray(pre)) ;
                return query.getRestriction() ;
			}
		};
		
		Page<Banner> page = this.bannerRepository.findAll(spec, pageable) ;
		List<Banner> list = page.getContent();
		for(Banner banner: list) {
			String imageUrl = this.fileComponent.getImageUrl(banner.getImage());
			banner.setImageUrl(imageUrl);
		}
		
		ResultVo resultVo = ResultVo.s(list) ;
		resultVo.setTotal(page.getTotalElements());
		return resultVo;
	}
	
	public ResultVo saveBanner(Banner banner) {
		this.bannerRepository.save(banner);
		return ResultVo.s();
	}
	
	public ResultVo infoBanner(String id) {
		Banner banner = new Banner();
		banner.setId(id);
		banner = this.bannerRepository.findOne(Example.of(banner)).get() ;
		if(banner == null){
			return ResultVo.e(400 , "没有查询到数据") ;
		}		
		String imageUrl = this.fileComponent.getImageUrl(banner.getImage());
		banner.setImageUrl(imageUrl);
		return ResultVo.s(banner) ;
	}
	
	public ResultVo modifyBanner(Banner banner) {
		Banner ban = this.bannerRepository.findById(banner.getId()).get();
		if(ban != null) {
			this.bannerRepository.save(banner);
		}else {
			return ResultVo.e(400 , "数据不存在") ;
		}
		return ResultVo.s();
	}
	
	public ResultVo removeBanner(String[] ids) {
		for(String id:ids) {
			this.bannerRepository.deleteById(id);
		}
		return ResultVo.s();
	}

	public List<Banner> findByPosition(Integer position) {
		List<Banner> list = this.bannerRepository.findByPositionOrderByRankDesc(position);
		return list;
	}
}
