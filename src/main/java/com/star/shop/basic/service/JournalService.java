package com.star.shop.basic.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.star.shop.basic.entity.Journal;
import com.star.shop.basic.repository.BaseRepository;
import com.star.shop.basic.repository.JournalRepository;
import com.star.shop.basic.vo.ResultVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


/**
 * 
 * 
 * <p>Title:JournalService</p>
 *
 * <p>Description:</p>
 *
 * <p>Company:</p>
 *
 * @author x.zhang
 *
 * @date 2018年8月22日
 */
@Service
public class JournalService extends BaseService<Journal, String> {
	private @Resource
	JournalRepository JournalRepository ;
	@Override
	protected BaseRepository<Journal, String> getBaseRepository() {
		return JournalRepository;
	}
	public ResultVo listJournal(Map<String, Object> params) {
		int pageSize = (int)params.get("pageSize") ;
		int pageNumber = (int)params.get("pageNumber") ;
		
		PageRequest pageable = new PageRequest(pageNumber-1 ,pageSize, Direction.DESC, "ctime") ;
		
		Specification<Journal> spec = new Specification<Journal>() {
			@Override
			public Predicate toPredicate(Root<Journal> root,CriteriaQuery <?> query, CriteriaBuilder cb) {
				List<Predicate> predicate = new ArrayList<>();
				if(!StringUtils.isEmpty(params.get("username"))){
					predicate.add(cb.like(root.get("username"),"%"+ params.get("username")+"%")) ;
				}
				
                Predicate[] pre = new Predicate[predicate.size()];
                query = query.where(predicate.toArray(pre)) ;
                return query.getRestriction() ;
			}
		};
		
		Page<Journal> page = this.JournalRepository.findAll(spec, pageable) ;
		
		ResultVo resultVo = ResultVo.s(page.getContent()) ;
		resultVo.setTotal(page.getTotalElements());
		return resultVo ;
	}
	public ResultVo removeJournal(String[] ids) {
		for(String id : ids){
			this.JournalRepository.deleteById(id);
		}
		return ResultVo.s();
	}
	public ResultVo clearJournal() {
		this.JournalRepository.deleteAll(); 
		return ResultVo.s();
	}

}
