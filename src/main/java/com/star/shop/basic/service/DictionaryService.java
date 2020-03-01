package com.star.shop.basic.service;

import com.star.shop.basic.entity.Dictionary;
import com.star.shop.basic.repository.BaseRepository;
import com.star.shop.basic.repository.DictionaryRepository;
import com.star.shop.basic.vo.ResultVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 
 * 
 * <p>Title:DictionaryService</p>
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
public class DictionaryService extends BaseService<Dictionary, String> {
	private @Resource
	DictionaryRepository dictionaryRepository ;

	@Override
	protected BaseRepository<Dictionary, String> getBaseRepository() {
		return dictionaryRepository ;
	}
	
	@Override
	public List<Dictionary> findByPid(String pid) {
		return this.dictionaryRepository.findByPid(pid) ;
	}

	public ResultVo listDict() {
		return ResultVo.s(dictionaryRepository.findAll());
	}
	
	public ResultVo selectDict() {
		return ResultVo.s(dictionaryRepository.findByType(0));
	}

	public ResultVo infoDict(String id) {
		Dictionary dict = this.dictionaryRepository.findById(id).get() ;
		if(dict == null){
			return ResultVo.e(400 , "没有查询到数据") ;
		}
		return ResultVo.s(dict);
	}

	public ResultVo saveDict(Dictionary dict) {
		this.dictionaryRepository.save(dict) ;
		dict.setPath(this.getPath(dict));
		this.dictionaryRepository.save(dict) ;
		return ResultVo.s();
	}

	public ResultVo modifyDict(Dictionary dict) {
		String path = this.getPath(dict) ;
		dict.setPath(path);
		this.dictionaryRepository.save(dict) ;
		
		//更新下级路径
		this.updateChildrenPath(dict);
		
		return ResultVo.s();
	}

	public ResultVo removeDict(String[] ids) {
		for(String id : ids){
			if(this.dictionaryRepository.findByPid(id).size() > 0){
				return ResultVo.e(400, "存在下级不能删除");
			}
			this.dictionaryRepository.deleteById(id); ;
		}
		return ResultVo.s();
	}

	

}
