package com.star.shop.basic.service;

import com.star.shop.basic.entity.Upload;
import com.star.shop.basic.repository.BaseRepository;
import com.star.shop.basic.repository.UploadRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * 
 * 
 * <p>Title:UploadService</p>
 *
 * <p>Description:</p>
 *
 * <p>Company:</p>
 *
 * @author x.zhang
 *
 * @date 2018年8月28日
 */
@Service
public class UploadService extends BaseService<Upload, String> {
	private @Resource
	UploadRepository uploadRepository ;

	@Override
	protected BaseRepository<Upload, String> getBaseRepository() {
		return uploadRepository;
	}

	public Upload findById(String id){
		return this.uploadRepository.findById(id).get();
	}

}
