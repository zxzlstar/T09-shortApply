/**
 * 
 */
package com.star.shop.admin.service.express.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.star.shop.admin.entity.Member;
import com.star.shop.admin.entity.express.ExpressCompany;
import com.star.shop.admin.entity.express.ExpressInfo;
import com.star.shop.admin.entity.express.ExpressNumber;
import com.star.shop.admin.entity.express.ExpressReturnData;
import com.star.shop.admin.repository.ExpressCompanyRepository;
import com.star.shop.admin.repository.ExpressInfoRepository;
import com.star.shop.admin.repository.ExpressNumberRepository;
import com.star.shop.admin.repository.ExpressReturnDataRepository;
import com.star.shop.admin.service.express.ExpressReturnDataService;
import com.star.shop.admin.utils.express.PrefixIdUtils;
import com.star.shop.basic.repository.BaseRepository;
import com.star.shop.basic.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


/**
 * @author cyan
 *
 */
@Service
@Transactional
public class ExpressReturnDataServiceImpl extends BaseServiceImpl<ExpressReturnData, String> implements ExpressReturnDataService {

	@Autowired
	private ExpressReturnDataRepository expressReturnDataRepository;
	
	@Autowired
	private ExpressInfoRepository expressInfoRepository;
	
	@Autowired
	private ExpressNumberRepository expressNumberRepository;

	@Autowired
	private ExpressCompanyRepository expressCompanyRepository;

	private Map<String, String> comMap = null;
		
	@Override
	protected BaseRepository<ExpressReturnData, String> getBaseRepository() {
		return this.expressReturnDataRepository;
	}

	private String[] citys = {"广东", "广州", "深圳", "珠海", "汕头", "佛山", "韶关", "湛江", "肇庆", "云浮",
			"江门", "茂名", "惠州", "梅州", "汕尾", "河源", "阳江", "清远", "东莞", "中山", "潮州", "揭阳"};

	@Override
	public String saveDataAndInfo(ExpressReturnData data) {
		// 已经保存物流信息的物流单号
		if (Objects.nonNull(data)) {
			if (data.getStatus() != 200 && !"ok".equals(data.getMessage()))
				return null;
			String context = null; // 派件节点信息
			data.setId(createId(PrefixIdUtils.EXPRESS_RETURN_DATA));
			if (Objects.nonNull(data.getData()) && data.getData().size() > 0) {
				if (data.getData().size() == 1 && "查无结果".equals(data.getData().get(0).getContext()))
					return null;
				// 查询出已经存在的物流信息，并且删除。
				String tempPid = null;
				ExpressReturnData findByNumber = expressReturnDataRepository.findByNu(data.getNu());
				if (Objects.nonNull(findByNumber)) {
					tempPid = findByNumber.getId();
					expressReturnDataRepository.delete(findByNumber);
				}
	//				ExpressReturnData deleteByNumber = expressReturnDataRepository.deleteByNumber(data.getNu());
				if (tempPid != null) {
					expressInfoRepository.deleteByPid(tempPid);
				}
				
				System.out.println(data.getNu());
				for (ExpressInfo info : data.getData()) {
					info.setPid(data.getId());
					info.setId(createId(PrefixIdUtils.EXPRESS_INFO));
					if (info.getContext().contains("派件") || info.getContext().contains("派送")) {
						context = info.getContext();
					}
				}
				expressInfoRepository.saveAll(data.getData());
				if ((data.getState() == 3 || data.getStatus() == 6)) {
					ExpressNumber expressNumber = expressNumberRepository.getOneByNumber(data.getNu());
					if (Objects.isNull(expressNumber)) {
						expressNumber = new ExpressNumber();
						expressNumber.setStatus(1);
						expressNumber.setOperation("sys");
						expressNumber.setNumber(data.getNu());
						expressNumberRepository.save(expressNumber);
					} else {
						expressNumber.setStatus(1);
					}
				}
				// 第一条轨迹
				ExpressInfo firstLogistic = data.getData().get(data.getData().size() -1);
				data.setIsProvince(0);
				for (String city : citys) {
					if (firstLogistic.getContext().contains(city)) {
						data.setIsProvince(1);
					break;
					}
				}
				
				if (Objects.nonNull(context)) {
					// 获取context节点中的派件人电话
					data.setSenderPhone(getPhoneNum(context));
				}
				expressReturnDataRepository.save(data);
				return data.getNu();
			}
		}
		return null;
	}

	@Override
	public ResultVo listExpress(Map<String, Object> params) {
		int pageSize = Integer.parseInt(params.get("pageSize").toString()) ;
		int pageNumber = Integer.parseInt(params.get("pageNumber").toString()) ;
		PageRequest pageable = null;
		pageable = new PageRequest(pageNumber-1 ,pageSize, new Sort(new Sort.Order(Sort.Direction.DESC, "com"))) ;
		Specification<ExpressReturnData> spec = new Specification<ExpressReturnData>() {
			@Override
			public Predicate toPredicate(Root<ExpressReturnData> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicate = new ArrayList<>();
				if(!StringUtils.isEmpty(params.get("company"))){
					predicate.add(cb.like(root.get("com"),"%"+ params.get("company")+"%")) ;
				}
				if(!StringUtils.isEmpty(params.get("number"))) {
					predicate.add(cb.equal(root.get("nu"), params.get("number")));
				}

				if(!StringUtils.isEmpty(params.get("senderPhone"))) {
					predicate.add(cb.equal(root.get("senderPhone"), params.get("senderPhone"))) ;
				}
				Predicate[] pre = new Predicate[predicate.size()];
				query = query.where(predicate.toArray(pre)) ;
				return query.getRestriction() ;
			}
		};

		Page<ExpressReturnData> page = this.expressReturnDataRepository.findAll(spec, pageable) ;
		List<ExpressReturnData> list = page.getContent();
		companyTranStr(list);
		ResultVo resultVo = ResultVo.s(list);
		resultVo.setTotal(page.getTotalElements());
		return resultVo;
	}

	private void companyTranStr(List<ExpressReturnData> list) {
		if (Objects.isNull(comMap)) {
			List<ExpressCompany> compList = expressCompanyRepository.findAll();
			comMap = compList.stream().collect(Collectors.toMap(ExpressCompany::getCode, ExpressCompany::getName));
		}
		if (Objects.nonNull(comMap)) {
			for (ExpressReturnData data : list) {
				Specification<ExpressInfo> spec = new Specification<ExpressInfo>() {
					@Override
					public Predicate toPredicate(Root<ExpressInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
						List<Predicate> predicate = new ArrayList<>();
						predicate.add(cb.equal(root.get("pid"), data.getId())) ;

						Predicate[] pre = new Predicate[predicate.size()];
						query = query.where(predicate.toArray(pre)) ;
						return query.getRestriction() ;
					}
				};
				List<ExpressInfo> infoList = expressInfoRepository.findAll(spec, new Sort(new Sort.Order(Sort.Direction.DESC, "time")));
				data.setData(infoList);
				data.setCompanyName(comMap.get(data.getCom()).trim());
			}
		}
	}

	//提取文本手机号
    public static String getPhoneNum(String text){
        String regex="[\\d]{11}";
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(text);
        String result = null;
        while (matcher.find()){
            result = matcher.group();
            break;
        }
        return result;
    }
}
