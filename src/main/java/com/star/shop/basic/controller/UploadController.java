package com.star.shop.basic.controller;

import com.star.shop.admin.utils.ImageCpsUtils;
import com.star.shop.basic.component.FileComponent;
import com.star.shop.basic.entity.Upload;
import com.star.shop.basic.service.UploadService;
import com.star.shop.basic.utils.DateUtils;
import com.star.shop.basic.vo.ResultVo;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 
 * 
 * <p>Title:FileUploadController</p>
 *
 * <p>Description:</p>
 *
 * <p>Company:</p>
 *
 * @author x.zhang
 *
 * @date 2018年8月28日
 */
@Controller
@RequestMapping(value = "/basic/upload")
public class UploadController {
	private final static Logger logger = LoggerFactory.getLogger(UploadController.class) ;
	
	private @Resource
	UploadService uploadService ;
	
	private @Resource
	FileComponent imageComponent;
	
	@Value("${basic.upload.path}")
	private String uploadPath ;	
	
	/**
	 * 
	 * @param multipartFile
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/file", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody
	ResultVo upload(@RequestParam(value = "file") MultipartFile multipartFile, HttpServletRequest req) throws Exception {
		ResultVo vo = new ResultVo() ;
		logger.info("文件长度: " + multipartFile.getSize());
		logger.info("文件类型: " + multipartFile.getContentType());
		logger.info("文件名称: " + multipartFile.getName());
		logger.info("文件原名: " + multipartFile.getOriginalFilename());

		String date = DateUtils.format(new Date(), DateUtils.DATE_PATTERN);
		String realPath = this.uploadPath + File.separator + date;
		String fileName = DateUtils.format(new Date() , "yyyyMMddHHmmssSSS") + System.nanoTime();
		if (multipartFile.getOriginalFilename().lastIndexOf(".") > 0) {
			String strsub = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
			if(strsub.equals(".png")) {
				fileName += ".jpg";
			}else {
				fileName += strsub;
			}
			
			File file = new File(realPath, fileName);
			FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), file);
			//判断是否是图片文件，是图片则压缩
			if(strsub.substring(1).matches("(?i)^[(JPG)|(PNG)|(GIF)]+$")) {
				//ImageCpsUtils.CompressAndaddWord(file, file);
				ImageCpsUtils.Compress(file, file);
			}
		}

		/*String imgU = ResourceUtils.getURL("classpath:").getPath()+"static/assets/img/gpx.png";*///水印图片路径
		/*ImageCpsUtils.Compress(file, new File(imgU), file);*/
		
		
		Upload upload = new Upload() ;
		upload.setPath(realPath + File.separator + fileName);
		upload.setContentType(multipartFile.getContentType());
		upload.setSourceName(multipartFile.getOriginalFilename());
		upload.setSize(multipartFile.getSize()/1024);
		upload = this.uploadService.save(upload) ;
		
		String imageUrl = imageComponent.getImageUrl(upload.getId());
		
		vo.addData("file" ,upload.getId());
		vo.addData("imageUrl", imageUrl);
		return vo;
	}
	
	/**
	 * 
	 * @param multipartFiles
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/files", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody ResultVo multiUpload(@RequestParam(value = "files") MultipartFile[] multipartFiles, HttpServletRequest req) throws Exception {
		ResultVo vo = new ResultVo() ;
		List<String> paths = new ArrayList<>() ;
		String date = DateUtils.format(new Date(), DateUtils.DATE_PATTERN);
		String realPath = this.uploadPath + File.separator + date;
		for(MultipartFile multipartFile : multipartFiles){
			String fileName = DateUtils.format(new Date() , "yyyyMMddHHmmssSSS") + System.nanoTime();
			if (multipartFile.getOriginalFilename().lastIndexOf(".") > 0) {
				String strsub = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
				if(strsub.equals(".png")) {
					fileName += ".jpg";
				}else {
					fileName += strsub;
				}
				
				File file = new File(realPath, fileName);
				FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), file);
				//判断是否是图片文件，是图片则压缩
				if(strsub.substring(1).matches("(?i)^[(JPG)|(PNG)|(GIF)]+$")) {
					//ImageCpsUtils.CompressAndaddWord(file, file);
					ImageCpsUtils.Compress(file, file);
				}
			}
			/*String imgU = ResourceUtils.getURL("classpath:").getPath()+"static/assets/img/gpx.png";*///水印图片路径
			/*ImageCpsUtils.Compress(file, new File(imgU), file);*/
			
			Upload upload = new Upload() ;
			upload.setPath(realPath + File.separator + fileName);
			upload.setContentType(multipartFile.getContentType());
			upload.setSourceName(multipartFile.getOriginalFilename());
			upload.setSize(multipartFile.getSize()/1024);
			upload = this.uploadService.save(upload) ;
			paths.add(upload.getId()) ;
		}
		
		vo.addData("files" ,paths);
		return vo;
	}
	
	/**
	 * 
	 * 查看图片
	 * 
	 * @param id
	 * @param response
	 */
	@RequestMapping(value = "/images/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public void view(@PathVariable String id , HttpServletResponse response){
		Upload upload = this.uploadService.findById(id) ;
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
	
	/**
	 * 
	 * 下载
	 * 
	 * @param id
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/download/{id}" , method = {RequestMethod.GET ,RequestMethod.POST })
	public void download(@PathVariable String id , HttpServletResponse response)throws Exception{
		Upload upload = this.uploadService.findById(id) ;
		if(upload == null || StringUtils.isEmpty(upload.getPath())){
			return  ;
		}
		
		File file = new File(upload.getPath());
		
		if (file.exists()) {
            response.setContentType("application/force-download");// 设置强制下载不打开
            response.addHeader("Accept-Length", String.valueOf(file.length()));
            response.addHeader("Content-Disposition",
                    "attachment;fileName=" + URLEncoder.encode(upload.getSourceName(),"utf-8"));// 设置文件名
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
            } catch (Exception e) {
            	logger.error("download file {} exception:{}" ,upload.getSourceName() , e.getMessage());
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/info/{id}" , method = {RequestMethod.GET ,RequestMethod.POST})
	public @ResponseBody ResultVo info(@PathVariable String id){
		Upload upload = this.uploadService.findById(id) ;
		if(upload == null){
			return  new ResultVo(400 , "文件不存");
		}
		
		ResultVo resultVo = new ResultVo();
		resultVo.addData("contentType", upload.getContentType());
		resultVo.addData("name", upload.getSourceName());
		resultVo.addData("size", upload.getSize());
		
		return resultVo ;
	}
}
