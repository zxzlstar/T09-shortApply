package com.star.shop.admin.controller.api;

import com.star.shop.admin.entity.Member;
import com.star.shop.admin.service.MemberService;
import com.star.shop.admin.utils.BarcodeUtils;
import com.star.shop.admin.utils.ImageCpsUtils;
import com.star.shop.basic.annotation.RequestMember;
import com.star.shop.basic.component.FileComponent;
import com.star.shop.basic.entity.Token;
import com.star.shop.basic.entity.Upload;
import com.star.shop.basic.service.TokenService;
import com.star.shop.basic.service.UploadService;
import com.star.shop.basic.utils.*;
import com.star.shop.basic.vo.ResultVo;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;


/**
 * 
 * 
 * <p>Title:ApiCommonController</p>
 *
 * <p>Description:</p>
 *
 * <p>Company:</p>
 *
 * @author x.zhang
 *
 * @date 2018年1月24日
 */
@RestController
@RequestMapping(value = "/api")
public class ApiCommonController{
	private final static Logger logger = LoggerFactory.getLogger(ApiCommonController.class) ;
	
	private @Resource
	MemberService memberService ;
	
	private @Resource
	TokenService tokenService ;
	
	private @Resource
	UploadService uploadService ;
	
	private @Resource
	FileComponent fileComponent;
	
	private @Resource WxMpService wxMpService;

	@Autowired
	private RedisUtil redisUtil;
	
	@Value("${basic.file.service.path}")
	private String fileUrl ;
	
	@Value("${basic.upload.path}")
	private String uploadPath ;
	
	/**
	 * 获取系统时间
	 * @return
	 */
	@RequestMapping(value = "/getsystemtime" , method = {RequestMethod.GET,RequestMethod.POST})
	public ResultVo getSystemTime() {
		ResultVo resultVo = new ResultVo() ;
		resultVo.addData("time", System.currentTimeMillis());
		return resultVo ;
	}
	
	/**
	 * 微信登录
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "/wxlogin" , method = {RequestMethod.POST})
	public ResultVo wxLogin(@RequestBody Map<String, String> params) {
		logger.info("用户登录[/api/wxlogin]请求参数：{}",params);
		String openid = params.get("openid") ;
		String nickname = params.get("nickname") ;
		String sex = params.get("sex") ;
		String province = params.get("province") ;
		String city = params.get("city") ;
		String country = params.get("country") ;
		String headimgurl = params.get("headimgurl") ;
		String invitation = params.get("invitation") ;
		
		if(StringUtils.isEmpty(openid)) {
			return new ResultVo(499 , "字段不正确");
		}
		Member member = memberService.findByOpenid(openid) ;
		if(member != null) {
//			//更新用户信息
//			member.setNickname(nickname);
//			if(StringUtils.hasText(sex)) {
//				member.setSex(Integer.parseInt(sex));
//			}
//			if(StringUtils.hasText(province)) {
//				member.setProvince(province);
//			}
//			
//			if(StringUtils.hasText(city)) {
//				member.setCity(city);
//			}
//			
//			if(StringUtils.hasText(country)) {
//				member.setCountry(country);
//			}
//			
//			if(StringUtils.hasText(headimgurl)) {
//				member.setHeadimgurl(headimgurl);
//			}
//			this.memberService.save(member) ;
			
		}else {
			if(StringUtils.isEmpty(nickname)) {
				return new ResultVo(404, "用户不存在") ;
			}
			member = new Member() ;
			member.setOpenid(openid);
			member.setNickname(nickname);
			member.setSex(StringUtils.isEmpty(sex)?0:Integer.parseInt(sex));
			member.setProvince(StringUtils.isEmpty(province)?"":province);
			member.setCity(StringUtils.isEmpty(city)?"":city);
			member.setCountry(StringUtils.isEmpty(country)?"":country);
			member.setHeadimgurl(StringUtils.isEmpty(headimgurl)?"":headimgurl);
			String tmp = Utils.getRandomString(8) ;
			while(this.memberService.findByInvitation(tmp)!= null) {
				tmp = Utils.getRandomString(8) ;
			}
			member.setInvitation(tmp);
			if(StringUtils.hasText(invitation)) {
				Member memberTemp = this.memberService.findByInvitation(invitation) ;
				if(memberTemp != null) {
					member.setReferrer(memberTemp.getId());
				}
			}
			
			this.memberService.save(member) ;
		}
		
		Token token = this.tokenService.createToken(member.getId() , (String) ThreadLocalUtil.get(Constants.APP_ID)) ;
		ResultVo resultVo = new ResultVo() ;
		resultVo.addData("token", token.getToken());
		redisUtil.set(token.getToken(),token,token.getExpireTime());
		//resultVo.addData("invitation", member.getInvitation());
		return resultVo ;
	}
	
	@RequestMapping(value = "/getuserinfo" , method = {RequestMethod.GET , RequestMethod.POST})
	public ResultVo getUserInfo(@RequestMember Member member) {
		logger.info("获取用户信息[/api/getuserinfo]请求参数：mid={}",ThreadLocalUtil.get(Constants.MEMBER_ID));
		
		//String mid = (String)ThreadLocalUtil.get(Constants.MEMBER_ID) ;
		ResultVo resultVo = new ResultVo() ;
		//Member member = this.memberService.findOne(mid) ;
		
		try {
			WxMpUser user = wxMpService.getUserService().userInfo(member.getOpenid(), "zh_CN");
			if(user.getSubscribe()) {
				resultVo.addData("isSubscribe", 1);
			}else {
				resultVo.addData("isSubscribe", 0);
			}
		} catch (WxErrorException e) {
			e.printStackTrace();
			resultVo.addData("isSubscribe", -1);
		}
		
		resultVo.addData("openid", member.getOpenid());
		resultVo.addData("nickname", member.getNickname());
		resultVo.addData("sex", member.getSex());
		resultVo.addData("province", member.getProvince());
		resultVo.addData("city", member.getCity());
		resultVo.addData("country", member.getCountry());
		resultVo.addData("headimgurl", member.getHeadimgurl());
		resultVo.addData("invitation", member.getInvitation());
		resultVo.addData("amount", member.getAmount());
		resultVo.addData("commission", member.getCommission());
		resultVo.addData("totalCommission", member.getTotalCommission());
		resultVo.addData("advertising", StringUtils.isEmpty(member.getAdvertising())?"":fileComponent.getImageUrl(member.getAdvertising()));
		resultVo.addData("name", member.getName());
		resultVo.addData("resume", member.getResume());
		resultVo.addData("mobile", member.getMobile());
		return resultVo ;
	}
	
	/**
	 * 获取排名前30的消费会员
	 * @param params
	 */
	@RequestMapping(value = "/gettopamount" , method = {RequestMethod.GET , RequestMethod.POST})
	public ResultVo getTopAmount(@RequestBody Map<String, String> params) {
		String numStr = params.get("num");
		Integer num = 30; //默认30个
		if(StringUtils.hasText(numStr)) num = Integer.parseInt(numStr); 
		List<Member> data = this.memberService.findTopAmount(num);		
		ResultVo resultVo = new ResultVo();
		resultVo.setData(data);
		return resultVo;
	}
	
	/**
	 * 上传图片
	 * @param multipartFiles
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/uploadimgs", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody ResultVo multiUpload(@RequestParam(value = "files") MultipartFile[] multipartFiles, HttpServletRequest req) throws Exception {
		logger.info("图片上传[/api/uploadimgs]请求图片数量："+ multipartFiles.length);
		List<Map<String,String>> paths = new ArrayList<>() ;
		String date = DateUtils.format(new Date(), DateUtils.DATE_PATTERN);
		String realPath = this.uploadPath + File.separator + date;
		for(MultipartFile multipartFile : multipartFiles){
			Map<String,String> map = new HashMap<String,String>();
			String fileName = DateUtils.format(new Date(), "yyyyMMddHHmmssSSS") + System.nanoTime();
			if (multipartFile.getOriginalFilename().lastIndexOf(".") > 0) {
				fileName += ".jpg";
			}
			File file = new File(realPath, fileName);
			FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), file);
			//图片压缩
			ImageCpsUtils.Compress(file, file);
			
			Upload upload = new Upload() ;
			upload.setPath(realPath + File.separator + fileName);
			System.out.println(upload.getPath());
			upload.setContentType(multipartFile.getContentType());
			upload.setSourceName(multipartFile.getOriginalFilename());
			upload.setSize(multipartFile.getSize()/1024);
			upload = this.uploadService.save(upload) ;
			map.put("id", upload.getId());
			map.put("path", this.fileComponent.getImageUrl(upload.getId()));
			paths.add(map);
		}
		/*vo.addData("files" ,paths);*/
		
		return ResultVo.s(paths);
	}
	/**
	 * 查看图片
	 * @param id
	 * @param response
	 */
	@RequestMapping(value = "/images/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public void view(@PathVariable String id , HttpServletResponse response){
		Upload upload = this.uploadService.getOne(id) ;
		if(upload == null || StringUtils.isEmpty(upload.getPath())){
			return  ;
		}
		String path = upload.getPath() ; 
		response.setContentType("application/octet-stream".equals(upload.getContentType())?"image/png":upload.getContentType());
		response.setDateHeader("expries", -1);  
        response.setHeader("Cache-Control", "no-cache");  
        response.setHeader("Pragma", "no-cache");  
		try {
			InputStream imageIn = new FileInputStream(new File(path));
			//文件流        
	        BufferedInputStream bis=new BufferedInputStream(imageIn);
	        //输入缓冲流   
	        BufferedOutputStream bos=new BufferedOutputStream(response.getOutputStream());
	        //输出缓冲流   
	        byte data[]=new byte[4096];
	        //缓冲字节数   
	        int size=0;    
	        size=bis.read(data);   
	        while (size!=-1){      
	            bos.write(data,0,size);           
	            size=bis.read(data);   
	        }   
	        bis.close();   
	        bos.flush();
	        //清空输出缓冲流        
	        bos.close(); 
		} catch (Exception e) {
			logger.error("image view exception:{}" , e.getMessage());
		}
	}
	
	@RequestMapping(value = "/barcode", method = { RequestMethod.GET, RequestMethod.POST })
	public void barcode(String code, HttpServletResponse response) {
		logger.info("生成条码[/api/barcode]请求参数code：{}", code);
		byte data[] = BarcodeUtils.generate(code);

		response.setContentType("image/png");
		response.setDateHeader("expries", -1);
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		
		try {
			//输入缓冲流   
	        BufferedOutputStream bos=new BufferedOutputStream(response.getOutputStream());
	        bos.write(data);
	        bos.flush();
	        //清空输出缓冲流        
	        bos.close(); 
			
		} catch (Exception e) {
			logger.error("generate barcode exception:{}" , e.getMessage());
		}
	}
	
/*	@RequestMapping(value = "/advertising/generate", method = { RequestMethod.GET, RequestMethod.POST })
	public ResultVo generateAdvertising(@RequestParam(required = true) String qrcode) {
		logger.info("生成我的广告[/api/advertising/generate]请求参数：mid={},qrcode={}", ThreadLocalUtil.get(Constants.MEMBER_ID),qrcode);
		String mid = (String) ThreadLocalUtil.get(Constants.MEMBER_ID);
		Member member = this.memberService.getOne(mid);
		//获取二维码的大小
		ParameterConfig qrwitdh_config = this.parameterConfigRepository.findByName(ParameterConstants.QRCODEREAL_WIDTH);
		ParameterConfig qrheight_config = this.parameterConfigRepository.findByName(ParameterConstants.QRCODEREAL_HEIGHT);
		ParameterConfig qrx_config = this.parameterConfigRepository.findByName(ParameterConstants.QRCODEREAL_POSITIONX);
		ParameterConfig qry_config = this.parameterConfigRepository.findByName(ParameterConstants.QRCODEREAL_POSITIONY);
		ParameterConfig bgPicture = this.parameterConfigRepository.findByName(ParameterConstants.MEMBER_PERSONAL_ADVERTISE);
		ParameterConfig touxiang_width_height_config = this.parameterConfigRepository.findByName(ParameterConstants.TOUXIANGREAL_WIDTH_HEIGHT);
		ParameterConfig touxiangx_config = this.parameterConfigRepository.findByName(ParameterConstants.TOUXIANGREAL_POSITIONX);
		ParameterConfig touxiangy_config = this.parameterConfigRepository.findByName(ParameterConstants.TOUXIANGREAL_POSITIONY);
		ParameterConfig qrcode_in_touxiang = this.parameterConfigRepository.findByName(ParameterConstants.QRCODE_INCLUDE_WXTOUXIANG);
		if(qrwitdh_config == null || qrheight_config == null || qrx_config == null || qry_config == null || bgPicture == null) {
			return ResultVo.e(423);
		}
		String date = DateUtils.format(new Date(), DateUtils.DATE_PATTERN);
		String realPath = this.uploadPath + File.separator + date;
		
		File parent = new File(realPath) ;
		if(!parent.exists()) {
			parent.mkdirs() ;
		}
		
		File qrcodeFile = new File(realPath, member.getOpenid() + ".png");
		String qrcodeUrl = "http://mobile.qq.com/qrcode?url=" + qrcode;
		//将头像显示在二维码中间，并以1/4的比例
		Map<String,Object> qrcodeXY = ImageUtils.getImageInfoByUrl(qrcodeUrl);
		if(qrcode_in_touxiang != null && qrcode_in_touxiang.getVal().equals("1")) {
			int wxtxWidth = Float.valueOf(Float.parseFloat(qrcodeXY.get("width").toString())*0.16f).intValue();
			int wxtxHeight = Float.valueOf(Float.parseFloat(qrcodeXY.get("height").toString())*0.16f).intValue();
			int wxtxPositionX = Float.valueOf((Float.parseFloat(qrcodeXY.get("width").toString())-wxtxWidth)/2).intValue();
			int wxtxPositionY = Float.valueOf((Float.parseFloat(qrcodeXY.get("height").toString())-wxtxHeight)/2).intValue();
			String headImageurl = member.getHeadimgurl();
			ImageUtils.resize(qrcodeFile, headImageurl, wxtxHeight, wxtxWidth, true);
			ImageUtils.pressImage(qrcodeFile,qrcodeUrl, qrcodeFile.getPath(), wxtxPositionX, wxtxPositionY, 1f);		
		}else {
			ImageUtils.resize(qrcodeFile, qrcodeUrl, Float.valueOf(Float.parseFloat(qrcodeXY.get("height").toString())).intValue(), Float.valueOf(Float.parseFloat(qrcodeXY.get("width").toString())).intValue(), true);
		}
		
		Upload ud = this.uploadService.getOne(bgPicture.getVal());
		String advertisingUrl = ud.getPath();
		File advertisingFile = new File(realPath, member.getId() + ".jpg");
		ImageUtils.resize(qrcodeFile, qrcodeFile.getPath(), (int)Float.parseFloat(qrheight_config.getVal().toString()), (int)Float.parseFloat(qrwitdh_config.getVal().toString()), true);
		ImageUtils.pressImage(advertisingFile,advertisingUrl, qrcodeFile.getPath(), (int)Float.parseFloat(qrx_config.getVal().toString()), (int)Float.parseFloat(qry_config.getVal().toString()), 1f);
		
		//计算二维码中微信头像的大小以及位置
*//*		int wxtxWidth = Float.valueOf(Float.parseFloat(qrwitdh_config.getVal().toString())*0.25f).intValue();
		int wxtxHeight = Float.valueOf(Float.parseFloat(qrheight_config.getVal().toString())*0.25f).intValue();
		int wxtxPositionX = Float.valueOf(qrx_config.getVal().toString()).intValue()+ Float.valueOf((Float.parseFloat(qrwitdh_config.getVal())-wxtxWidth)/2).intValue();
		int wxtxPositionY = Float.valueOf(qry_config.getVal().toString()).intValue()+ Float.valueOf((Float.parseFloat(qrheight_config.getVal())-wxtxHeight)/2).intValue();
		
		File wxcodeFile = new File(realPath, member.getOpenid() + ".png");
		String wxcodeUrl = member.getHeadimgurl();
		ImageUtils.resize(wxcodeFile, wxcodeUrl, wxtxHeight, wxtxWidth, true);	
		ImageUtils.pressImage(advertisingFile,advertisingFile.getPath(), wxcodeFile.getPath(), wxtxPositionX, wxtxPositionY, 1f);
		*//*
		//获取背景图中微信头像的大小及位置
		String wxcodeUrl = member.getHeadimgurl();
		File touxiangcodeFile = new File(realPath, member.getOpenid() + ".png");
		ImageUtils.resize(touxiangcodeFile, wxcodeUrl, (int)Float.parseFloat(touxiang_width_height_config.getVal().toString()), (int)Float.parseFloat(touxiang_width_height_config.getVal().toString()), true);
		ImageUtils.pressImage(advertisingFile,advertisingFile.getPath(), touxiangcodeFile.getPath(),  (int)Float.parseFloat(touxiangx_config.getVal().toString()), (int)Float.parseFloat(touxiangy_config.getVal().toString()), 1f);
		
		Upload upload = new Upload();
		upload.setPath(advertisingFile.getPath());
		upload.setContentType("image/jpg");
		upload.setSourceName(advertisingFile.getName());
		upload.setSize(0);
		upload = this.uploadService.save(upload);
		
		member.setAdvertising(upload.getId());
		memberService.save(member) ;
		
		ResultVo resultVo = new ResultVo() ;
		resultVo.addData("id", fileComponent.getImageUrl(upload.getId()));
		return resultVo ;
	}*/
	
	public static void main(String[] args) {
		System.out.println(System.currentTimeMillis());
	}
}
