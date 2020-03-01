package com.star.shop.basic.controller;

import com.star.shop.basic.utils.HttpContextUtils;
import org.apache.commons.io.FileUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * 
 * 
 * <p>
 * Title:BaseController
 * </p>
 *
 * <p>
 * Description:
 * </p>
 *
 * <p>
 * Company:
 * </p>
 *
 * @author x.zhang
 *
 * @date 2018年8月17日
 */
public class BaseController {
	/**
	 * 写出数据
	 * 
	 * @param res
	 *            输出的字符串
	 * @throws Exception
	 */
	protected void write(String res) throws Exception {
		HttpServletResponse response = HttpContextUtils.getHttpServletResponse() ;
		Writer writer = null;
		try {
			res = (null == res ? "" : res);
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-type", "text/html;charset=UTF-8");
			writer = response.getWriter();
			// logger.debug("输出JSON字符串："+res);
			writer.write(res);
		} catch (IOException e) {
			// logger.error("输出JSON字符串异常");
			throw new Exception("write json string error");
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					// logger.error("关闭输出流异常,无法关闭会导致内存溢出");
				}
			}
		}
	}

	protected void fileDownload(final HttpServletResponse response, File file, String fileName) throws Exception {
		byte[] data = FileUtils.readFileToByteArray(file);
		fileName = URLEncoder.encode(fileName, "UTF-8");
		response.reset();
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		response.addHeader("Content-Length", "" + data.length);
		response.setContentType("application/octet-stream;charset=UTF-8");
		OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
		outputStream.write(data);
		outputStream.flush();
		outputStream.close();
	}
}
