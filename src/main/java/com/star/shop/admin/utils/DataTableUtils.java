package com.star.shop.admin.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DataTableUtils {
	
	//--数据库----
	private static String DRIVER = "com.mysql.jdbc.Driver"; //驱动
	private static String URL = "jdbc:mysql://120.78.90.56:3306/jingxuandbdev?useUnicode=true&characterEncoding=UTF-8";
	private static String USER = "develop"; //用户名
	private static String PASSWORD = "#Develop2018"; //密码
	
	
	private static String OLD_YUMING = "teststatic.lemankang.com";  //旧域名
	private static String NEW_YUMING = "teststatic.jingxuanjiuba.com"; //新域名
	
	private String table ;
	private String column;
	
	public DataTableUtils(String table,String column){
		this.table = table;
		this.column = column;
	}
	
	private ResultSet getDataTableResult(Connection conn,String likeColumn) throws SQLException {
		String sql = "select id,"+ column + " from " + table +" where " + column +" like ?";
		PreparedStatement pst=conn.prepareStatement(sql);
		pst.setString(1, "%"+likeColumn+"%");
		ResultSet rs=pst.executeQuery();
		return rs;
	}
	
	private void setDataTableResult(Connection conn,String replaceStr,String id) throws SQLException {
		String sql = "update "+ table + " set " + column +" =? where id = ?";
		PreparedStatement pst=conn.prepareStatement(sql);
		pst.setString(1, replaceStr);
		pst.setString(2, id);
		pst.execute();
		pst.close();
	}
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		//1.加载驱动程序
        Class.forName(DRIVER);
        //2.获得数据库链接
        Connection conn=DriverManager.getConnection(URL, USER, PASSWORD);
        //3.查询数据表字段的值中存在域名的数据并且修改
        
        //3.1----(商品表中的particulars字段的数据中新旧域名之间的替换)
        DataTableUtils dtu = new DataTableUtils("t_goods","particulars");
        ResultSet set = dtu.getDataTableResult(conn,OLD_YUMING);
        
        String id = "";
        String particulars = "";
        while(set.next()) {
        	id=set.getString("id");
        	particulars = set.getString("particulars").replaceAll(OLD_YUMING, NEW_YUMING);
        	dtu.setDataTableResult(conn, particulars, id);
        }
        
        //3.2 ----(商户表中的intro字段数据中新旧域名之间的替换)
        dtu = new DataTableUtils("t_merch","intro");
        set = dtu.getDataTableResult(conn,OLD_YUMING);
        String intro = "";
        while(set.next()) {
        	id=set.getString("id");
        	intro = set.getString("intro").replaceAll(OLD_YUMING, NEW_YUMING);
        	dtu.setDataTableResult(conn, intro, id);
        }
        
        System.out.println("操作完成！！！");
        //3.3 ----(新闻表中的content字段数据中新旧域名之间的替换)----只存在于”顾评鲜“平台
/*        dtu = new DataTableUtils("t_news","content");
        set = dtu.getDataTableResult(conn,OLD_YUMING);
        String content = "";
        while(set.next()) {
        	id=set.getString("id");
        	content = set.getString("content").replaceAll(OLD_YUMING, NEW_YUMING);
        	dtu.setDataTableResult(conn, content, id);
        }*/
        
	}
}
