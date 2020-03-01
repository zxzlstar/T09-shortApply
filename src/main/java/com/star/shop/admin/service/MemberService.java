package com.star.shop.admin.service;

import com.star.shop.admin.entity.Member;
import com.star.shop.admin.repository.MemberRepository;
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 
 * 
 * <p>Title:MemberService</p>
 *
 * <p>Description:</p>
 *
 * <p>Company:</p>
 *
 * @author moxf
 *
 * @date 2018年1月24日
 */
@Service
public class MemberService extends BaseService<Member, String> {
	private @Resource
	MemberRepository memberRepository ;

	@Override
	protected BaseRepository<Member, String> getBaseRepository() {
		return memberRepository;
	}

	public Member findByOpenid(String openid) {
		return memberRepository.findByOpenid(openid) ;
	}
	
	public Member findByInvitation(String invitation) {
		return this.memberRepository.findByInvitation(invitation) ;
	}
	
	//管理端
	public ResultVo listMember(Map<String, Object> params) {
		int pageSize = Integer.parseInt(params.get("pageSize").toString()) ;
		int pageNumber = Integer.parseInt(params.get("pageNumber").toString()) ;
		PageRequest pageable = null;
		if(!StringUtils.isEmpty(params.get("amount")) ) {
			if(params.get("amount").toString().equals("1")) {
				pageable = new PageRequest(pageNumber-1 ,pageSize, new Sort(new Sort.Order(Direction.ASC, "amount"),new Sort.Order(Direction.DESC,"ctime"))) ;
			}else if(params.get("amount").toString().equals("2")) {
				pageable = new PageRequest(pageNumber-1 ,pageSize, new Sort(new Sort.Order(Direction.DESC, "amount"),new Sort.Order(Direction.DESC,"ctime"))) ;
			}
		}else {
			pageable = new PageRequest(pageNumber-1 ,pageSize, Direction.DESC, "ctime") ;
		}
		Specification<Member> spec = new Specification<Member>() {
			@Override
			public Predicate toPredicate(Root<Member> root,CriteriaQuery <?> query, CriteriaBuilder cb) {
				List<Predicate> predicate = new ArrayList<>();
				if(!StringUtils.isEmpty(params.get("nickname"))){
					predicate.add(cb.like(root.get("nickname"),"%"+ params.get("nickname")+"%")) ;
				}
				if(!StringUtils.isEmpty(params.get("sex"))) {
					predicate.add(cb.equal(root.get("sex"), params.get("sex")));
				}
				if(!StringUtils.isEmpty(params.get("ctime"))) {
					String[] date = params.get("ctime").toString().split(" - ");
					predicate.add(cb.between(root.get("ctime"), date[0], date[1]));
				}
				if(!StringUtils.isEmpty(params.get("invitation"))) {
					predicate.add(cb.equal(root.get("invitation"), params.get("invitation"))) ;
				}
                Predicate[] pre = new Predicate[predicate.size()];
                query = query.where(predicate.toArray(pre)) ;
                return query.getRestriction() ;
			}
		};
		
		Page<Member> page = this.memberRepository.findAll(spec, pageable) ;
		ResultVo resultVo = ResultVo.s(page.getContent());
		resultVo.setTotal(page.getTotalElements());
		return resultVo;
	}
	
	/**
	 * 获取所有的会员
	 * @return
	 */
	public ResultVo listAllMember() {
		List<Member> list = memberRepository.findAll();
		return ResultVo.s(list);
	}
	
	/**
	 * 修改消费累计金额
	 * @param member
	 * @return
	 */
	public ResultVo updateAmount(Member member) {
		Member mem = this.getOne(member.getId());
		if(mem != null) {
			mem.setAmount(member.getAmount());
		}
		return ResultVo.s();
	}
	
	public ResultVo infoMember(String id) {
		Member mem = this.getOne(id);
		return ResultVo.s(mem);
	}
	
	//--首页显示最新的前十个会员
	public List<Member> listMainMember(){
		Sort sort = new Sort(Direction.DESC,"ctime");
		PageRequest pageable = new PageRequest(0,10,sort);
		Page<Member> page = this.memberRepository.findAll(pageable) ;
		return page.getContent();
	}
	
	public List<Member> findTopAmount(Integer num){
		PageRequest pageable = new PageRequest(0 ,num, Direction.DESC, "amount") ;//消费金额排名
		Page<Member> page = memberRepository.findAll(pageable);
		List<Member> memList = page.getContent();
		return memList;
	}

	public ResultVo findByReferrer(Map<String,String> params,String mid) {
		int pageSize = Integer.parseInt(params.get("pageSize").toString()) ;
		int pageNumber = Integer.parseInt(params.get("pageNumber").toString()) ;
		PageRequest pageable = new PageRequest(pageNumber-1 ,pageSize, Direction.DESC, "ctime") ;
		
		Specification<Member> spec = new Specification<Member>() {
			@Override
			public Predicate toPredicate(Root<Member> root,CriteriaQuery <?> query, CriteriaBuilder cb) {
				List<Predicate> predicate = new ArrayList<>();
				predicate.add(cb.equal(root.get("referrer"), mid)) ;
                Predicate[] pre = new Predicate[predicate.size()];
                query = query.where(predicate.toArray(pre)) ;
                return query.getRestriction() ;
			}
		};
		
		Page<Member> page = this.memberRepository.findAll(spec, pageable) ;
		ResultVo resultVo = ResultVo.s(page.getContent());
		resultVo.setTotal(page.getTotalElements());
		return resultVo;
	}
	
	/**
	 * 修改账户余额
	 * @param type
	 * @param amount
	 * @return
	 */
	public synchronized ResultVo updateMemberAmount(int type,String mid,BigDecimal amount) {
		Member member = this.getOne(mid);
		if(type == 1) {//收入
			member.setAmount(member.getAmount().add(amount));
		}else { //支出
			if(amount.compareTo(member.getAmount()) == 1) {
				return ResultVo.e(436, "账户余额不足，尝试竞猜商品吧");
			}
			member.setAmount(member.getAmount().subtract(amount));
		}
		this.memberRepository.save(member);
		return ResultVo.s(member.getAmount());
	}

	public Member findById(String id){
		return this.memberRepository.findById(id).get();
	}

	public List<Map<String,Object>> getMemberCountSex(){
		return this.memberRepository.findCountMember();
	}
}
