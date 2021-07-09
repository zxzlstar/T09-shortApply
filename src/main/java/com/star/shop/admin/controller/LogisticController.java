package com.star.shop.admin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.PostConstruct;

import com.star.shop.admin.entity.express.ExpressCompany;
import com.star.shop.admin.entity.express.ExpressNumber;
import com.star.shop.admin.entity.express.ExpressReturnData;
import com.star.shop.admin.entity.express.ExpressSpiderToken;
import com.star.shop.admin.repository.ExpressNumberRepository;
import com.star.shop.admin.repository.ExpressSpiderTokenRepository;
import com.star.shop.admin.service.express.ExpressCompanyService;
import com.star.shop.admin.service.express.ExpressNumberService;
import com.star.shop.admin.service.express.ExpressReturnDataService;
import com.star.shop.admin.thread.*;
import com.star.shop.basic.vo.ResultVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * 国内物流
 * @author cyan
 *
 */
@Controller
@EnableScheduling // 开启定时任务
@RequestMapping(value = "/shop/logistic")
public class LogisticController {

    private static final Logger logger = LoggerFactory.getLogger(LogisticController.class);

    @Autowired
    private ExpressNumberService expressNumberService;

    @Autowired
    private ExpressReturnDataService expressReturnDataService;

    @Autowired
    private KdBaiduSpiderThread spiderThread;

    @Autowired
    private ExpressNumberRepository expressNumberRepository;

    @Autowired
    private ExpressSpiderTokenRepository expressSpiderTokenRepository;

    @Autowired
    private ExpressCompanyService expressCompanyService;

    @Value("${baidu.spider.expressUrl}")
    private String expressUrl;

    @Value("${shunfeng.telPhone.endNum}")
    private String shunfengTelPhoneEndNum;


    @RequestMapping(value = "/list", method = { RequestMethod.POST })
    @ResponseBody
    public ResultVo listLogistic(@RequestBody Map<String, Object> params) {
        findExpressByBaiduApi(params.get("number"), params.get("phoneEnd"));
        return this.expressReturnDataService.listExpress(params);
    }

    /**
     * 判断是否是顺丰快递
     * @param number
     * @return
     */
    @RequestMapping(value = "/isSFExpress", method = { RequestMethod.GET })
    @ResponseBody
    public ResultVo isSFExpress(@RequestParam String number) {
        if (Objects.nonNull(number) && number != "" && number.startsWith("SF")) {
            return ResultVo.s();
        } else {
            String com = Kuaidi100Util.getCompanyCodeByNumber(number);
            if (com.equals("shunfeng")) {
                return ResultVo.s();
            }
        }
        return ResultVo.e(400);
    }

    // 3.添加定时任务
//	@Scheduled(cron = "${quartz.cron.time}")
//	public void init() {
//		System.out.println("init...");
//		List<String> logisticNos = expressNumberService.findAllNumber(0);
//		logger.info("爬取国内tracknumber数量为：" + (Objects.isNull(logisticNos) ? "0" : logisticNos.size()));
//		if (Objects.nonNull(logisticNos) && logisticNos.size() > 0)
//			this.doExcute(logisticNos);
//	}


//	@PostConstruct
//	public void initKd100() {
//		logger.info("init...");
//		List<String> logisticNos = expressNumberService.findAllNumber(0);
//		if (Objects.nonNull(logisticNos) && logisticNos.size() > 0)
//			this.doKd100Excute(logisticNos);
//	}

    /**
     * 爬取百度快递token,每5分钟执行一次。
     */
//	@PostConstruct
    @Scheduled(cron = "${token.refresh.time}")
    public void spiderExpressToken() {
        logger.info("kdBaiduSpider start...");
        String number = "75431166846904";
        ExpressNumber expressNumber = expressNumberRepository.findFirstByOrderByOperationTimeDesc();
        if (Objects.nonNull(expressNumber)) {
            number = expressNumber.getNumber();
        }
        try {
            String apiUrl = spiderThread.getApiUrl(number);
            if (Objects.nonNull(apiUrl)) {
                String[] strs = apiUrl.split("&tokenV2=");
                if (Objects.nonNull(strs) && strs.length == 2) {
                    String token = strs[1];
                    if (token.contains("&")) {
                        token = token.substring(0, token.indexOf("&"));
                    }
                    ExpressSpiderToken spiderToken = expressSpiderTokenRepository.findFirstByOrderByUpdateTimeDesc();
                    if (Objects.isNull(spiderToken)) {
                        spiderToken = new ExpressSpiderToken();
                        spiderToken.setToken(token);
                        expressSpiderTokenRepository.save(spiderToken);
                    } else {
                        spiderToken.setToken(token);
                        expressSpiderTokenRepository.save(spiderToken);
                    }
                }
            }
            logger.info("apiUrl = {} | kdBaiduSpider end...", apiUrl);
        } finally {
            spiderThread.closeAll();
        }
    }

    private void doKdShunFengExcute(List<String> list) throws InterruptedException {
        logger.info("kdShunFengSearch start...");
        KuaidiShunFengUtil sfUtil = null;
//		List<ExpressReturnData> epressReturnDataList = new ArrayList<>();
        for (String logisticNo : list) {
            sfUtil = new KuaidiShunFengUtil(shunfengTelPhoneEndNum);
            ExpressReturnData data = sfUtil.call(logisticNo);
            if (Objects.nonNull(data)) {
//				epressReturnDataList.add(data);
                expressReturnDataService.saveDataAndInfo(data);
            }
        }

        logger.info("kdShunFengSearch end...");
    }

    public Map<String, List<String>> splitExpressNumberListBySF(List<String> list) {
        Map<String, List<String>> map = new HashMap<>();
        List<String> sfList = new ArrayList<>();
        List<String> ytList = new ArrayList<>();
        List<String> otherList = new ArrayList<>();
        for (String number : list) {
            if (Objects.nonNull(number)) {
                if (number.startsWith("SF")) {
                    sfList.add(number);
                } else if (number.startsWith("YT")) {
                    ytList.add(number);
                } else {
                    otherList.add(number);
                }
            }
        }
        map.put("sf", sfList);
        map.put("yt", ytList);
        map.put("other", otherList);
        return map;
    }

    public void findExpressByBaiduApi(Object obj, Object phoneEnd ) {
        if (Objects.isNull(obj)) {
            return;
        }
        String number = (String) obj;
        try {
            // 获取快递的物流商
            String com = Kuaidi100Util.getCompanyCodeByNumber(number);
            if (number.startsWith("SF") || (Objects.nonNull(com) && com.equals("shunfeng"))) {
                if (Objects.isNull(phoneEnd)) {
                    return;
                }
                KuaidiShunFengUtil sfUtil = new KuaidiShunFengUtil(phoneEnd.toString());
                ExpressReturnData data = sfUtil.call(number);
                if (Objects.isNull(data)) {
//                    retMap.put("code", "402");
//                    retMap.put("msg", "暂时顺丰官网上查询不到【"+ number +"】物流信息！");
                } else {
//					List<ExpressReturnData> list = new ArrayList<>();
//					list.add(data);
                    String nu = expressReturnDataService.saveDataAndInfo(data);
//                    if (Objects.nonNull(nu)) {
//                        retMap.put("code", "200");
//                        retMap.put("msg", "顺丰快递中查询成功！");
//                    } else {
//                        retMap.put("code", "402");
//                        retMap.put("msg", "暂时顺丰快递中查询不到【"+ number +"】物流信息！");
//                    }
                }
            } else if (number.startsWith("YT") || (Objects.nonNull(com) && com.equals("yuantong"))) {
                KuaidiYuanTongUtil ytUtil = new KuaidiYuanTongUtil();
                ExpressReturnData data = ytUtil.call(number);
                if (Objects.isNull(data)) {

                } else {
                    String nu = expressReturnDataService.saveDataAndInfo(data);
                }
            } else {
                // 获取从百度爬取需要的token
                ExpressSpiderToken spiderToken = expressSpiderTokenRepository.findFirstByOrderByUpdateTimeDesc();
                String apiUrl = expressUrl.concat("?tokenV2=").concat(spiderToken.getToken());
                KdBaiduSearchUtil baiduUtil= new KdBaiduSearchUtil(apiUrl);
                ExpressReturnData data = baiduUtil.call(number);
                if (Objects.isNull(data)) {
//                    Kuaidi100Util kd100Util = new Kuaidi100Util();
//                    data = (ExpressReturnData) kd100Util.call(number);
//                    if (Objects.isNull(data)) {
//                        retMap.put("code", "402");
//                        retMap.put("msg", "暂时百度上查询不到【"+ number +"】物流信息！");
//                    } else {
////						List<ExpressReturnData> list = new ArrayList<>();
////						list.add(data);
//                        String nu = expressReturnDataService.saveDataAndInfo(data);
//                        if (Objects.nonNull(nu)) {
//                            retMap.put("code", "200");
//                            retMap.put("msg", "快递100中查询成功！");
//                        } else {
//                            retMap.put("code", "402");
//                            retMap.put("msg", "暂时快递100中查询不到【"+ number +"】物流信息！");
//                        }
//                    }
                } else {
//					List<ExpressReturnData> list = new ArrayList<>();
//					list.add(data);
                    String nu = expressReturnDataService.saveDataAndInfo(data);
//                    if (Objects.nonNull(nu)) {
//                        retMap.put("code", "200");
//                        retMap.put("msg", "百度上查询成功！");
//                    } else {
//                        retMap.put("code", "402");
//                        retMap.put("msg", "暂时百度上查询不到【"+ number +"】物流信息！");
//                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 定时任务去爬取物流信息，每20分钟执行一次。
     * @throws InterruptedException
     */
//    @PostConstruct
//    @Scheduled(cron = "${quartz.cron.time}")
    public void initKdBaiduSearch() throws InterruptedException {
        List<String> logisticNos = expressNumberService.findAllNumber(0);
        if (Objects.nonNull(logisticNos) && logisticNos.size() > 0) {
            Map<String, List<String>> map = splitExpressNumberListBySF(logisticNos);
            try {
                // 处理顺丰的
                doKdShunFengExcute(map.get("sf"));

                // 处理非顺丰的
                doKdBaiduExcute(map.get("notSf"));
            } catch (Exception e) {
                logger.info("定时爬取国内物流报错：{}", e);
            }
        }
    }

    /**
     * 百度爬取快递信息
     * @param list
     * @throws InterruptedException
     */
    public void doKdBaiduExcute(List<String> list) throws InterruptedException {

        // 获取从百度爬取需要的token
        ExpressSpiderToken spiderToken = expressSpiderTokenRepository.findFirstByOrderByUpdateTimeDesc();
        if (Objects.isNull(spiderToken)) {
            // 从快递100中爬取。
            if (Objects.nonNull(list) && list.size() > 0)
                this.doKd100Excute(list);
            return;
        }
        String apiUrl = expressUrl.concat("?tokenV2=").concat(spiderToken.getToken());
        logger.info("从百度上获取物流信息的apiUrl = {}", apiUrl);

        logger.info("kdBaiduSearch start...");
        KdBaiduSearchUtil util = null;
//		List<ExpressReturnData> epressReturnDataList = new ArrayList<>();
        Map<String, Object> info = new HashMap<>();
        for (String logisticNo : list) {
            util = new KdBaiduSearchUtil(apiUrl);
            ExpressReturnData data = util.call(logisticNo);
            if (Objects.nonNull(data)) {
//				epressReturnDataList.add(data);
                String nu = expressReturnDataService.saveDataAndInfo(data);
                if (Objects.nonNull(nu)) {
                    info.put(nu, null);
                }
            }
        }
        logger.info("kdBaiduSearch end...");
        // 获取在百度上没有爬取到的物流信息的快递号
        List<String> expressNumList = getNotCatchExpressNumInBaiduSpider(info);
        // 再从快递100中爬取。
        if (Objects.nonNull(expressNumList) && expressNumList.size() > 0)
            this.doKd100Excute(expressNumList);
    }

    private List<String> getNotCatchExpressNumInBaiduSpider(Map<String, Object> info) {
        List<String> logisticNos = expressNumberService.findAllNumber(0);
        Iterator<String> it = logisticNos.iterator();
        while (it.hasNext()) {
            String next = it.next();
            if (info.containsKey(next)) {
                it.remove();
            }
        }
        return logisticNos;
    }

    /**
     * 爬取快递100
     * @param list
     * @throws InterruptedException
     */
    public void doKd100Excute(List<String> list) throws InterruptedException {
        logger.info("kd100 start...");
        Kuaidi100Util util = null;
//		List<ExpressReturnData> epressReturnDataList = new ArrayList<>();
        for (String logisticNo : list) {
            util = new Kuaidi100Util();
            ExpressReturnData data = util.call(logisticNo);
            if (Objects.nonNull(data)) {
//				epressReturnDataList.add(data);
                expressReturnDataService.saveDataAndInfo(data);
            }
        }
//		expressReturnDataService.saveDataAndInfo(epressReturnDataList);
        logger.info("kd100 end...");
    }


    @RequestMapping(value = "/loadExpressCompany", method = { RequestMethod.POST })
    @ResponseBody
    public ResultVo loadExpressCompany() {
        List<ExpressCompany> companyList = expressCompanyService.getExpressCompanyList();
        List<Object> ret = new ArrayList<Object>();
        Map<String, String> map = new HashMap<>();
        map.put("id", "");
        map.put("text", "全部");
        ret.add(map);
        for (ExpressCompany o : companyList) {
            map = new HashMap<String, String>();
            map.put("id", o.getCode());
            map.put("text", o.getName());
            ret.add(map);
        }
        ResultVo resultVo = ResultVo.s(ret);
        return resultVo;
    }

}
