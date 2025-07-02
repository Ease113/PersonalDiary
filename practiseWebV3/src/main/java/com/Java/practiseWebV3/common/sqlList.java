package com.Java.practiseWebV3.common;

public class sqlList {
	
	public static String SQLNB001 = "SELECT COUNT(*) FROM user_info WHERE user_Id = '{0}' AND passWord = '{1}'";
	public static String SQLNB002 = "INSERT INTO user_info (user_Id, passWord, user_name, user_email, REG_SYS_TIME, UPD_SYS_TIME, DEL_FLG) VALUES ('{0}', '{1}', '{2}', '{3}', NOW(), NOw(), 0)";
	public static String SQLNB003 = "SELECT REG_Id, user_Id, passWord, user_name, user_email FROM user_info WHERE user_Id = '{0}' OR user_name = '{1}' OR user_email = '{2}'";
	public static String SQLNB004 = "SELECT C.REG_Id, C.category, (SELECT COUNT(*) FROM ARTICLE_INFO AS A WHERE C.USER_NAME = A.AUTHOR AND C.CATEGORY = A.CATEGORY) AS COUNT FROM (CATEGORY_INFO AS C LEFT JOIN USER_INFO AS U ON C.USER_NAME = U.USER_NAME)\r\n"
									+ "WHERE C.USER_NAME = '{0}' AND C.DEL_FLG = 0 AND U.DEL_FLG = 0 GROUP BY C.REG_ID ORDER BY C.REG_ID ASC";
	//public static String SQLNB004 = "SELECT C.REG_Id, C.category, C.COUNT(*) FROM CATEGORY_INFO AS C LEFT JOIN USER_INFO AS U ON C.USER_NAME = U.USER_NAME WHERE DEL_FLG = 0 ORDER BY REG_ID ASC";
	public static String SQLNB005 = "SELECT S.REG_Id, S.series, (SELECT COUNT(*) FROM ARTICLE_INFO AS A WHERE S.USER_NAME = A.AUTHOR AND S.SERIES = A.SERIES) AS COUNT FROM (SERIES_INFO AS S LEFT JOIN USER_INFO AS U ON S.USER_NAME = U.USER_NAME)\r\n"
									+ "WHERE S.USER_NAME = '{0}' AND S.DEL_FLG = 0 AND U.DEL_FLG = 0 GROUP BY S.REG_ID ORDER BY S.REG_ID ASC";
	//public static String SQLNB005 = "SELECT REG_Id, series FROM SERIES_INFO WHERE DEL_FLG = 0 ORDER BY REG_ID ASC";
	public static String SQLNB006 = "INSERT INTO CATEGORY_INFO (CATEGORY, USER_NAME, DEL_FLG) VALUES ('{0}', '{1}', 0) ON DUPLICATE KEY UPDATE CATEGORY = '{0}', USER_NAME = '{1}', DEL_FLG = {2}";
	public static String SQLNB007 = "INSERT INTO SERIES_INFO (SERIES, USER_NAME, DEL_FLG) VALUES ('{0}', '{1}', 0) ON DUPLICATE KEY UPDATE SERIES = '{0}', USER_NAME = '{1}', DEL_FLG = {2}";
	public static String SQLNB008 = "UPDATE CATEGORY_INFO SET DEL_FLG = '1' WHERE REG_ID = '{0}'";
	public static String SQLNB009 = "UPDATE SERIES_INFO SET DEL_FLG = '1' WHERE REG_ID = '{0}'";
	public static String SQLNB010 = "SELECT A.REG_ID, A.TITLE, A.ARTICLE, A.AUTHOR, A.TAG, A.CATEGORY, A.SERIES, A.UPD_SYS_TIME, A.REG_SYS_TIME, A.DEL_FLG FROM article_info AS A LEFT JOIN user_info AS U ON A.author = U.user_name WHERE A.author = '{0}' AND A.DEL_FLG = 0 AND U.DEL_FLG = 0";
	
	public static String SQLNB011 = "INSERT INTO ARTICLE_INFO (TITLE, ARTICLE, AUTHOR, TAG, CATEGORY, SERIES, REG_SYS_TIME, UPD_SYS_TIME, DEL_FLG) VALUES ('{0}', '{1}', '{2}', '{3}', '{4}', '{5}', NOW(), NOW(), 0)";
	public static String SQLNB012 = "SELECT A.REG_ID, A.TITLE, A.ARTICLE, A.AUTHOR, A.TAG, A.CATEGORY, A.SERIES, A.UPD_SYS_TIME, A.REG_SYS_TIME, A.DEL_FLG FROM article_info AS A LEFT JOIN user_info AS U ON A.author = U.user_name WHERE A.author = '{0}' AND A.REG_ID = {1} AND A.DEL_FLG = 0 AND U.DEL_FLG = 0";
	public static String SQLNB013 = "SELECT REG_ID, TITLE FROM ARTICLE_INFO WHERE SERIES = '{0}' AND AUTHOR = '{1}' AND DEL_FLG = 0";
	
	public static String SQLNB014 = "DELETE FROM ARTICLE_INFO WHERE REG_ID = {0} AND AUTHOR = '{1}'";
	
	public static String GETUSERNAME = "SELECT USER_NAME FROM USER_INFO WHERE USER_ID = '{0}'";
	public static String RESETAUTOINCREMENTCATEGORY = "ALTER TABLE category_info AUTO_INCREMENT = 1";
	public static String RESETAUTOINCREMENTSERIES= "ALTER TABLE series_info AUTO_INCREMENT = 1";
	
}