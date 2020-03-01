package com.star.shop.basic.component;

import com.star.shop.basic.entity.Upload;
import com.star.shop.basic.repository.UploadRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
/**
 *
 * <p>Title:FileComponent</p>
 *
 * <p>Description:</p>
 *
 * <p>Company:</p>
 *
 * @author x.zhang
 *
 * @date 2018年9月11日
 */

@Component
public class FileComponent {

	@Value("${basic.upload.path}")
	private String uploadPath ;
	
	@Value("${basic.file.service.path}")
	private String fileServicePath ;
	
	@Value("${spring.profiles.active}")
	private String proActive;
	
	private @Resource
	UploadRepository uploadRepository;
	
	//图片路径拼接
	public String getImageUrl(String id) {
		String imageUrl = "";
		if(proActive.equals("dev")) {
			imageUrl = fileServicePath+"/basic/upload/images/"+id;
		}else if(proActive.equals("prod")){
			Upload upload = this.uploadRepository.findById(id).get() ;
			if(upload !=null && StringUtils.isNotEmpty(upload.getPath())){
				imageUrl = upload.getPath().replace(uploadPath, fileServicePath).replace("\\", "/");
			}
		}else if(proActive.equals("test")) {
			Upload upload = this.uploadRepository.findById(id).get() ;
			if(upload !=null && StringUtils.isNotEmpty(upload.getPath())){
				imageUrl = upload.getPath().replace(uploadPath, fileServicePath).replace("\\", "/");
			}
		}
		return imageUrl;
	}
}
