package com.star.shop.admin.service;

import com.alibaba.fastjson.JSONArray;
import com.star.shop.admin.entity.SpecificationItem;
import com.star.shop.admin.repository.SpecitemRepository;
import com.star.shop.admin.vo.SpecitemTreeVo;
import com.star.shop.basic.repository.BaseRepository;
import com.star.shop.basic.service.BaseService;
import com.star.shop.basic.vo.ResultVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SpecitemService extends BaseService<SpecificationItem,String> {

    private @Resource
    SpecitemRepository specitemRepository;

    @Override
    protected BaseRepository<SpecificationItem, String> getBaseRepository() {
        return specitemRepository;
    }

    /**
     * 条件查询规格项
     * @param params
     * @return
     */
    public ResultVo listSpecitem(Map<String , Object> params) {
        int pageSize = Integer.parseInt(params.get("pageSize").toString()) ;
        int pageNumber = Integer.parseInt(params.get("pageNumber").toString()) ;
        PageRequest pageable = new PageRequest(pageNumber-1 ,pageSize, Sort.Direction.DESC, "ctime") ;

        Specification<SpecificationItem> spec = new Specification<SpecificationItem>() {
            @Override
            public Predicate toPredicate(Root<SpecificationItem> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicate = new ArrayList<>();
               /* if(!StringUtils.isEmpty(params.get("name"))){
                    predicate.add(cb.like(root.get("name"),"%"+ params.get("name")+"%")) ;
                }
                if(!StringUtils.isEmpty(params.get("position"))) {
                    predicate.add(cb.equal(root.get("position"), params.get("position")));
                }*/
                Predicate[] pre = new Predicate[predicate.size()];
                query = query.where(predicate.toArray(pre)) ;
                return query.getRestriction() ;
            }
        };

        Page<SpecificationItem> page = this.specitemRepository.findAll(spec, pageable) ;
        List<SpecificationItem> list = page.getContent();

        ResultVo resultVo = ResultVo.s(list) ;
        resultVo.setTotal(page.getTotalElements());
        return resultVo;
    }

    /**
     * 修改规格项
     * @param specificationItem
     * @return
     */
    public ResultVo modifySpecitem(SpecificationItem specificationItem){
        System.out.println("------------------------------"+specificationItem.getId());
        SpecificationItem specitem = this.specitemRepository.getOne(specificationItem.getId());
        if(specitem != null) {
            this.specitemRepository.save(specificationItem);
        }else {
            return ResultVo.e(400 , "数据不存在") ;
        }
        return ResultVo.s();
    }

    /**
     * 循环遍历删除规格项
     * @param ids
     * @return
     */
    public ResultVo removeSpecitem(String[] ids){
        for(String id:ids) {
            this.specitemRepository.deleteById(id);
        }
        return ResultVo.s();
    }


    public ResultVo findSpecitemAll(){
        List<SpecificationItem> list = this.specitemRepository.findAll();
        List<SpecitemTreeVo> voList = new ArrayList<>(list.size());
        SpecitemTreeVo vo = null;
        for(int i=0;i<list.size();i++){
            //添加一级目录
            vo = new SpecitemTreeVo();
            vo.setId(list.get(i).getId());
            vo.setPid("-1");
            vo.setName(list.get(i).getParamsName());
            vo.setOpen(false);
            voList.add(vo);
            //添加二级目录
            String paramsValue = list.get(i).getParamsValue();
            JSONArray jsonArray = JSONArray.parseArray(paramsValue);
            for (int j=0;j<jsonArray.size();j++){
                String name = jsonArray.get(j).toString();
                vo = new SpecitemTreeVo();
                vo.setName(name);
                vo.setPid(list.get(i).getId());
                vo.setOpen(false);
                voList.add(vo);
            }
        }
        ResultVo resultVo = ResultVo.s(voList) ;
        return resultVo;
    }
}
