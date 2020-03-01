package com.star.shop.admin.service;

import com.star.shop.admin.entity.GoodsClassify;
import com.star.shop.admin.repository.GoodsClassifyRepository;
import com.star.shop.admin.vo.GoodsClassifyVo;
import com.star.shop.basic.component.FileComponent;
import com.star.shop.basic.entity.Upload;
import com.star.shop.basic.repository.BaseRepository;
import com.star.shop.basic.repository.UploadRepository;
import com.star.shop.basic.service.BaseService;
import com.star.shop.basic.vo.ResultVo;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GoodsClassifyService extends BaseService<GoodsClassify, String> {

	private @Resource
	GoodsClassifyRepository goodsClassifyRepository;
	
	private @Resource
	UploadRepository uploadRepository;
	
	private @Resource
	FileComponent fileComponent;
	
	@Override
	protected BaseRepository<GoodsClassify, String> getBaseRepository() {
		// TODO Auto-generated method stub
		return goodsClassifyRepository;
	}
	
	public ResultVo listGoodscf(String treeType, String id) {
		List<GoodsClassify> list = new ArrayList<GoodsClassify>();
		if("1".equals(treeType))
			list = this.goodsClassifyRepository.findAll();
		else if("2".equals(treeType)) {
			list = this.goodsClassifyRepository.findByPathNotLike("%"+id+"%");
		}
		
		List<GoodsClassifyVo> listVo = new ArrayList<GoodsClassifyVo>();
		for(GoodsClassify goodscf: list) {
			GoodsClassifyVo goodscfVo = new GoodsClassifyVo();
			try {
				BeanUtils.copyProperties(goodscfVo, goodscf);
			}catch (Exception e) {
				e.printStackTrace();
			}
			goodscfVo.setImageUrl(fileComponent.getImageUrl(goodscf.getImage()));
			listVo.add(goodscfVo);
		}
		return ResultVo.s(listVo);
	}
	
	public ResultVo saveGoodscf(GoodsClassify goodscf) {
		
		//添加之前要判断该添加的goodscf是否是属于一级或者二级的商品类型
		if(StringUtils.isNotBlank(goodscf.getPid())) {
			//二级商品类型
			GoodsClassify gcf = this.findById(goodscf.getPid());
			if(StringUtils.isNotBlank(gcf.getPid())) {
				return ResultVo.e(400, "不可以添加三级商品类型");
			}
	
		}
		this.goodsClassifyRepository.save(goodscf);
		goodscf.setPath(this.getPath(goodscf));
		this.goodsClassifyRepository.save(goodscf);
		return ResultVo.s();
	}
	
	public ResultVo infoGoodscf(String id) {
		GoodsClassify goodscf = this.findById(id);
		if(goodscf == null) {
			return ResultVo.e(400, "没有查询到数据");
		}else {
			Upload upload = new Upload();
			upload.setId(goodscf.getImage());
			upload = this.uploadRepository.findById(upload.getId()).get() ;
			
			if(upload !=null && StringUtils.isNotEmpty(upload.getPath())){
				GoodsClassifyVo goodscfVo = new GoodsClassifyVo();
				try {
					BeanUtils.copyProperties(goodscfVo, goodscf);
				}catch (Exception e) {
					e.printStackTrace();
				}
				goodscfVo.setImageUrl(fileComponent.getImageUrl(goodscf.getImage()));
				return ResultVo.s(goodscfVo);
			}
			
			return ResultVo.e(400); 	
		}
	}
	
	public ResultVo modifyGoodscf(GoodsClassify goodscf) {
		//判断要修改的goodscf原本是一级的还是二级的
		GoodsClassify gcf = this.findById(goodscf.getId());
		GoodsClassify gpidCl = this.findById(goodscf.getPid());
		List<GoodsClassify> list = this.goodsClassifyRepository.findByPid(goodscf.getId());
		if(gcf != null && StringUtils.isBlank(gcf.getPid())) { //一级的
			//判断将要修改的pid是一级的还是二级的
			if(gpidCl != null) {
				if(StringUtils.isBlank(gpidCl.getPid())) {
					
					if(list.size() > 0) {
						return ResultVo.e(400, "该一级商品类型存在二级商品类型，不能将它迁移到其他商品类型");
					}else if(goodscf.getId().equals(goodscf.getPid())){
						return ResultVo.e(400, "不能选择自身类别，请选择上级类别");
					}else {
						this.goodsClassifyRepository.save(goodscf);
						//更新下级
						this.updateChildrenPath(goodscf);
					}
				}else {
					return ResultVo.e(400, "任何的商品类型不能迁移到任何的二级商品类型中");
				}
			}else {
				this.goodsClassifyRepository.save(goodscf);
				//更新下级
				this.updateChildrenPath(goodscf);
			}
		}else { //二级的
			//判断将要修改的pid是一级的还是二级的
			if(gpidCl != null && StringUtils.isBlank(gpidCl.getPid())) {
				this.goodsClassifyRepository.save(goodscf);
				//更新下级
				this.updateChildrenPath(goodscf);
			}else if(gpidCl == null){
				this.goodsClassifyRepository.save(goodscf);
				//更新下级
				this.updateChildrenPath(goodscf);
			}else {
				return ResultVo.e(400, "该二级商品类型不能将它迁移到其他二级商品类型");
			}
		}	
		return ResultVo.s();
	}
	
	public ResultVo removeGoodscf(String[] ids) {
		for(String id : ids) {
			if(this.goodsClassifyRepository.findByPid(id).size() > 0) {
				return ResultVo.e(400, "存在子商品类别不能删除");
			}
			GoodsClassify goodscf = this.findById(id);
			//判断该二级商品类别是否有商品
			//List<Goods> list = this.goodsRepository.findByClassify(goodscf);
			/*if(list != null && list.size()>0) {
				return ResultVo.e(400, "该商品类别中存在商品，不可删除");
			}*/
			this.goodsClassifyRepository.deleteById(id);
		}
		
		return ResultVo.s();
	}
	
	
	/**
	 * 根据pid查询商品类别
	 * @param pid
	 * @return
	 */
	public List<GoodsClassify> listGoodscfByPid(String pid) {
		List<GoodsClassify> list = new ArrayList<>();
		list = this.goodsClassifyRepository.findByPidOrderByRankDesc(pid);
		return list;
	}
	 
	public List<GoodsClassify> listallGoodscf(){
		List<GoodsClassify> list = this.goodsClassifyRepository.findAll();
		return list;
	}

	public GoodsClassify findById(String id){
		Optional<GoodsClassify> goodsClassify =  this.goodsClassifyRepository.findById(id);
		if(goodsClassify.isPresent()){
			return goodsClassify.get();
		}else{
			return null;
		}
	}
}
