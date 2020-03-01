package com.star.shop.admin.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

public class ImageCpsUtils {
	
	private final static Logger logger = LoggerFactory.getLogger(ImageCpsUtils.class) ;
	
//水印压缩
/*	public static void Compress(File outFile, File waterFile, File newFile) {
		try {
            Thumbnails.of(outFile).
            		scale(1f).
                    watermark(Positions.BOTTOM_RIGHT, ImageIO.read(waterFile),0.8f). //水印位于右下角,半透明
                    outputQuality(0.25f). // 图片压缩80%质量
                    toFile(newFile);
        } catch (IOException e) {
        	logger.info("图片压缩异常: "+e.getMessage());
        }
	}*/
	
	//只是压缩
	public static void Compress(File outFile, File newFile) {
		try {
            Thumbnails.of(outFile).
            		scale(1f).
                    outputQuality(0.25f). // 图片压缩80%质量
                    toFile(newFile);
        } catch (IOException e) {
        	logger.info("图片压缩异常: "+e.getMessage());
        }
	}
	
	//压缩和添加文字水印
	public static void CompressAndaddWord(File outFile, File newFile) {
		try {
            // 读取原图片信息
            Image srcImg = ImageIO.read(outFile);//文件转化为图片
            int srcImgWidth = srcImg.getWidth(null);//获取图片的宽
            int srcImgHeight = srcImg.getHeight(null);//获取图片的高
            // 加水印
            BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufImg.createGraphics();
            Font font = new Font("", Font.PLAIN, 20); 
            Color color=new Color(255,255,255,128);
            String waterMarkContent = "精选酒吧";
            g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);
            g.setColor(color); //根据图片的背景设置水印颜色
            g.setFont(font);              //设置字体

            //设置水印的坐标
            int x = srcImgWidth - getWatermarkLength(waterMarkContent, g);  
            int y = srcImgHeight - 5;  
            g.drawString(waterMarkContent, x, y);  //画出水印
            g.dispose();  
            // 输出图片  
            FileOutputStream outImgStream = new FileOutputStream(newFile);  
            ImageIO.write(bufImg, "jpg", outImgStream);
            System.out.println("添加水印完成");  
            
            outImgStream.flush();  
            outImgStream.close();    
            
            
            //压缩
            Thumbnails.of(newFile).
            		scale(1f).
                    outputQuality(0.25f). // 图片压缩80%质量
                    toFile(newFile);
        } catch (IOException e) {
        	logger.info("图片压缩异常: "+e.getMessage());
        }
	}
	
    public static int getWatermarkLength(String waterMarkContent, Graphics2D g) {  
        return g.getFontMetrics(g.getFont()).charsWidth(waterMarkContent.toCharArray(), 0, waterMarkContent.length());  
    }  
	
	public static void main(String[] args) {
		/*Compress(new File("F:\\1.jpg"),new File("F:\\2.jpg"));*/
	}
}
