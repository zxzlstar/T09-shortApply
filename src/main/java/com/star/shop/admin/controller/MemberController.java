package com.star.shop.admin.controller;

import com.star.shop.admin.entity.Member;
import com.star.shop.admin.repository.MemberRepository;
import com.star.shop.admin.service.MemberService;
import com.star.shop.basic.annotation.Log;
import com.star.shop.basic.component.FileComponent;
import com.star.shop.basic.controller.BaseController;
import com.star.shop.basic.repository.UploadRepository;
import com.star.shop.basic.vo.ResultVo;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping(value = "/shop/member")
public class MemberController extends BaseController {
	
	private @Resource
	MemberService memberService;

	private @Resource WxMpService wxMpService;
	
	private @Resource
	UploadRepository uploadRepository;
	
	private @Resource
	MemberRepository memberRepository;
	
	private @Resource
	FileComponent fileComponent;

	
	@RequestMapping(value = "/list" , method = {RequestMethod.GET, RequestMethod.POST})
	public ResultVo listMember(@RequestBody Map<String, Object> params) {
		return this.memberService.listMember(params);
	}
	
	
	@Log("修改消费累计金额")
	@RequestMapping(value = "/updateAmount" , method= {RequestMethod.GET, RequestMethod.POST})
	public ResultVo updateAmount(@RequestBody Member member) {
		return this.memberService.updateAmount(member);
	}
	
	@Log("修改消费累计金额")
	@RequestMapping(value = "/info", method = {RequestMethod.GET, RequestMethod.POST })
	public ResultVo infoMember(@RequestBody String id) {
		return this.memberService.infoMember(id);
	}
	
	@Log("获取所有会员")
	@RequestMapping(value = "/listall" , method = {RequestMethod.GET, RequestMethod.POST})
	public ResultVo listAllMember(@RequestBody Map<String, String> params) {
		return this.memberService.listAllMember();
	}
	
	@Log("判断用户是否关注了公众号")
	@RequestMapping(value = "/subscribe", method = {RequestMethod.POST })
	public ResultVo subscribe(String id) {

		Member member = memberRepository.findById(id).get();
		if(member != null) {
			String lang = "zh_CN"; //语言
			try {
				WxMpUser user = wxMpService.getUserService().userInfo(member.getOpenid(),lang);
				if(user.getSubscribe()) {
					return ResultVo.s("yes");
				}else {
					return ResultVo.s("no");
				}
			} catch (WxErrorException e) {
				e.printStackTrace();
				return ResultVo.e(404, "无效的openid");
			}
		}
		return null;
	}
	
/*	@Log("生成会员个人广告图的参数配置")
	@RequestMapping(value = "/advertising/make", method = {RequestMethod.POST })
	public ResultVo makeAdvertising(@RequestBody Map<String,String> params) {
		//查询获取广告背景图
		ParameterConfig config = this.parameterConfigRepository.findByName(ParameterConstants.MEMBER_PERSONAL_ADVERTISE);
		String advertise = params.get("advertise");
		String remark = params.get("remark");

		if(config == null) {
			if(StringUtils.isEmpty(advertise)) {
				return ResultVo.e(423, "请先配置广告背景图");
			}else {
				config = new ParameterConfig();
				config.setName(ParameterConstants.MEMBER_PERSONAL_ADVERTISE);
				config.setRemark(remark);
				config.setVal(advertise);
				this.parameterConfigRepository.save(config);
			}
		}else {
			config.setVal(advertise);
			config.setRemark(remark);
			this.parameterConfigRepository.save(config);
			//删除会员存在的个人广告图信息
			uploadRepository.deleteAdvertise();
			//把所有的会员的广告字段清空
			this.memberRepository.emptyAdvertising();
		}
		//获取广告图的实际像素大小
		String url = this.fileComponent.getImageUrl(config.getVal());
		Map<String,Object> bgpic = ImageUtils.getImageInfoByUrl(url);
		String bgrealpic_width = bgpic.get("width").toString();
		//获取管理端设置的大小
		String bgpic_width = params.get("bgpicture_width");
		String qrcode_width = params.get("qrcode_width");
		String qrcode_height = params.get("qrcode_height");
		String qrcode_positionX = params.get("qrcode_positionX");
		String qrcode_positionY = params.get("qrcode_positionY");
		String touxiang_width_height = params.get("touxiang_width_height");
		String touxaing_positionX = params.get("touxaing_positionX");
		String touxaing_positionY = params.get("touxaing_positionY");
		String istouxiang_in_qrcode = params.get("istouxiang_in_qrcode");
		
		List<ParameterConfig> list = new ArrayList<ParameterConfig>();
		
		ParameterConfig  touinqr = new ParameterConfig();
		touinqr.setName(ParameterConstants.QRCODE_INCLUDE_WXTOUXIANG);
		touinqr.setVal(istouxiang_in_qrcode);
		list.add(touinqr);
		
		//保存管理端二维码设置的位置以及像素大小
		ParameterConfig  pqc1 = new ParameterConfig();
		pqc1.setName(ParameterConstants.QRCODE_WIDTH_HEIGHT);
		pqc1.setVal(qrcode_width+"-"+qrcode_height);
		list.add(pqc1);
		
		ParameterConfig  pqc2 = new ParameterConfig();
		pqc2.setName(ParameterConstants.QRCODE_POSITION_XY);
		pqc2.setVal(qrcode_positionX+"-"+qrcode_positionY);
		list.add(pqc2);
		
		//保存管理端二维码设置的位置以及像素大小
		ParameterConfig txc1 = new ParameterConfig();
		txc1.setName(ParameterConstants.TOUXIANG_WIDTH_HEIGHT);
		txc1.setVal(touxiang_width_height);
		list.add(txc1);
		
		ParameterConfig txc2 = new ParameterConfig();
		txc2.setName(ParameterConstants.TOUXIANG_POSITION_XY);
		txc2.setVal(touxaing_positionX+"-"+touxaing_positionY);
		list.add(txc2);
		
		//计算二维码的比例
		
		BigDecimal rate = new BigDecimal(bgrealpic_width).divide(new BigDecimal(bgpic_width), 2, RoundingMode.HALF_UP) ;
		
		//计算真实的二维码像素大小
		BigDecimal qrcodereal_width = rate.multiply(new BigDecimal(qrcode_width)) ;
		BigDecimal qrcodereal_height = rate.multiply(new BigDecimal(qrcode_height));
		//计算真实的二维码位置
		BigDecimal qrcodereal_positionX = rate.multiply(new BigDecimal(qrcode_positionX));
		BigDecimal qrcodereal_positionY = rate.multiply(new BigDecimal(qrcode_positionY));
		//计算真实的头像像素大小
		BigDecimal touxiangreal_width_height = rate.multiply(new BigDecimal(touxiang_width_height));
		//计算真是的头像位置
		BigDecimal touxiangreal_positionX = rate.multiply(new BigDecimal(touxaing_positionX));
		BigDecimal touxiangreal_positionY = rate.multiply(new BigDecimal(touxaing_positionY));

		ParameterConfig  p1 = new ParameterConfig();
		p1.setName(ParameterConstants.QRCODEREAL_WIDTH);
		p1.setVal(qrcodereal_width.toString());
		list.add(p1);
		ParameterConfig  p2 = new ParameterConfig();
		p2.setName(ParameterConstants.QRCODEREAL_HEIGHT);
		p2.setVal(qrcodereal_height.toString());
		list.add(p2);
		ParameterConfig  p3 = new ParameterConfig();
		p3.setName(ParameterConstants.QRCODEREAL_POSITIONX);
		p3.setVal(qrcodereal_positionX.toString());
		list.add(p3);
		ParameterConfig  p4 = new ParameterConfig();
		p4.setName(ParameterConstants.QRCODEREAL_POSITIONY);
		p4.setVal(qrcodereal_positionY.toString());
		list.add(p4);
		ParameterConfig  p5 = new ParameterConfig();
		p5.setName(ParameterConstants.TOUXIANGREAL_WIDTH_HEIGHT);
		p5.setVal(touxiangreal_width_height.toString());
		list.add(p5);
		ParameterConfig  p6 = new ParameterConfig();
		p6.setName(ParameterConstants.TOUXIANGREAL_POSITIONX);
		p6.setVal(touxiangreal_positionX.toString());
		list.add(p6);
		ParameterConfig  p7 = new ParameterConfig();
		p7.setName(ParameterConstants.TOUXIANGREAL_POSITIONY);
		p7.setVal(touxiangreal_positionY.toString());
		list.add(p7);
		//保存下来
		return this.parameterConfigService.saveParameterConfig(list);
	}*/
/*	@Log("用户关注和取消关注")
	@RequestMapping(value = "/member/changesub", method = {RequestMethod.GET, RequestMethod.POST })
	public void test(HttpServletRequest request) {
		try {
			WxMpXmlMessage fromXml = WxMpXmlMessage.fromXml(request.getInputStream());
			String event = fromXml.getEvent();
			Date date = new Date(fromXml.getCreateTime());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/
}
