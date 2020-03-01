package com.star.shop.admin.controller.api;

import com.star.shop.admin.entity.Member;
import com.star.shop.admin.repository.MemberRepository;
import com.star.shop.admin.service.MemberService;
import com.star.shop.basic.utils.Constants;
import com.star.shop.basic.utils.ThreadLocalUtil;
import com.star.shop.basic.vo.ResultVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/api")
public class ApiMemberController {
	private static final Logger logger = LoggerFactory.getLogger(ApiMemberController.class) ;

	private @Resource
	MemberRepository memberRepository;
	
	private @Resource
	MemberService memberService;
	
	@RequestMapping(value="/member/referrer" , method = {RequestMethod.POST})
	public ResultVo getreferrer(@RequestBody Map<String,String> params) {
		logger.info("获取用户推荐的人列表[/api/member/referrer]请求参数:{}",params);
		
		String mid = (String)ThreadLocalUtil.get(Constants.MEMBER_ID);
		
		ResultVo vo  = this.memberService.findByReferrer(params,mid);
		List<Member> list = (List<Member>) vo.getData();
		List<Map<String,Object>> data = new ArrayList<>();
		for (Member member : list) {
			Map<String,Object> map = new HashMap<>();
			map.put("nickname", member.getNickname());
			map.put("headimgurl", member.getHeadimgurl());
			map.put("sex", member.getSex());
			map.put("id", member.getId());
			data.add(map); 
		}
		ResultVo result = new ResultVo();
		result.setData(data);
		result.setTotal(vo.getTotal());
		
		return result;
		
	}

}
