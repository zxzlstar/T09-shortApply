package com.star.shop.basic.repository;

import com.star.shop.basic.entity.Dictionary;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 
 * 
 * <p>Title:DictionaryRepository</p>
 *
 * <p>Description:</p>
 *
 * <p>Company:</p>
 *
 * @author x.zhang
 *
 * @date 2018年8月22日
 */
@Repository
public interface DictionaryRepository extends BaseRepository<Dictionary, String> {
	public List<Dictionary> findByType(Integer type) ;
	
	public List<Dictionary> findByPid(String pid) ;

}
