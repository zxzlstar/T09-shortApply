package com.star.shop.admin.thread;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author Cyan
 * @date 2021年1月13日
 */
@Service
public class KdBaiduSpiderThread {
	
	@Value("${spider.config.file}")
	private String spiderConfigFile;
	
	private List<WebDriver> webDriverList = Collections.synchronizedList(new ArrayList<WebDriver>());

	public String getApiUrl(String number) {
		System.getProperties().setProperty("webdriver.chrome.driver", spiderConfigFile);
		ChromeOptions options = new ChromeOptions();
        options.addArguments("--user-data-dir=C:\\Users\\Administrator\\AppData\\Local\\Google\\Chrome\\User Data");
        //  关闭使用 ChromeDriver 打开浏览器时上部提示语 "Chrome正在受到自动软件的控制"
		options.addArguments("disable-infobars");
		options.addArguments("--headless");
        ChromeDriver webDriver = new ChromeDriver(options);
		webDriver.get("https://www.baidu.com/s?wd="+number);

		WebElement element = webDriver.findElement(By.xpath("/html"));
		String htmlStr = element.getAttribute("outerHTML");
		Pattern pattern = Pattern.compile("apiUrl: '(.*)'");
		Matcher matcher = pattern.matcher(htmlStr);
		String apiUrl = null;
		if (matcher.find()) {
			apiUrl = matcher.group(1);
		}
		webDriver.close();
		webDriverList.add(webDriver);
		return apiUrl;
	}
	
	public void closeAll() {
		for (WebDriver webDriver : webDriverList) {
			webDriver.quit();
			webDriver = null;
		}
	}
}
