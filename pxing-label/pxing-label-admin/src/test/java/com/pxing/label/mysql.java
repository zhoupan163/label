package com.pxing.label;

import java.sql.Connection;

import java.sql.DriverManager;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class mysql {
	// 数据库地址
	private static String dbUrl = "jdbc:mysql://10.66.66.121:3307/pxing_label?"
			+ "useSSL=false&characterEncoding=utf-8&serverTimezone=GMT";
	// 用户名
	private static String dbUserName = "root";
	// 密码
	private static String dbPassword = "123456";
	// 驱动名称
	private static String jdbcName = "com.mysql.cj.jdbc.Driver";
	public static void main(String[] args) {
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
			System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
			Class.forName(jdbcName);
			System.out.println("加载驱动成功！");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("加载驱动失败！");
		}
		Connection con = null;
		try {
			// 获取数据库连接
			con = DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
			System.out.println("获取数据库连接成功！");
			System.out.println("进行数据库操作！");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("获取数据库连接失败！");
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
