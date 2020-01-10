package com.winter.swallow;

/**
 * @Author winterSwallow
 * @Date 2020-01-09 14:12
 * @Description 常量定义
 */
public class CommonConst {

	/**
	 * 输出文件路径
	 */
	public static String FILE_PATH = "D:\\temp.doc";
	/**
	 * 数据库名称
	 */
	public static String DB_NAME = "temp";
	/**
	 * 数据库驱动名称
	 */
	public static String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
	/**
	 * 数据库url
	 */
	public static String URL = "jdbc:mysql://localhost:3306/" + DB_NAME	+ "?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8";
	/**
	 * 数据库用户名
	 */
	public static String USER = "root";
	/**
	 * 数据库用户密码
	 */
	public static String PASSWORD = "123456";
	/**
	 * 输出列名定义
	 */
	public static String[] COLUMN_NAME_ARRAY = {"英文名","中文名","列名","列类型","可为空","主键","默认值","列注释","其他"};
	/**
	 * 查询语句
	 */
	public static String QUERY_SQL_STATEMENT = "SELECT" + "	T.TABLE_NAME AS '%s'," + "	T.TABLE_COMMENT AS '%s',"
			+ "	C.COLUMN_NAME AS '%s'," + "	C.COLUMN_TYPE AS '%s',"
			+ "	( CASE WHEN C.IS_NULLABLE = 'NO' THEN '' ELSE '是' END ) AS '%s',"
			+ "	( CASE WHEN C.COLUMN_KEY = 'PRI' THEN '是' ELSE '' END ) AS '%s'," + "	C.COLUMN_DEFAULT AS '%s',"
			+ "	C.COLUMN_COMMENT AS '%s'," + "	C.EXTRA AS '%s' " + "FROM" + "	information_schema.`TABLES` T"
			+ "	LEFT JOIN information_schema.`COLUMNS` C ON T.TABLE_NAME = C.TABLE_NAME "
			+ "	AND T.TABLE_SCHEMA = C.TABLE_SCHEMA " + "WHERE" + "	T.table_schema =  '" + DB_NAME + "' ORDER BY"
			+ "	C.TABLE_NAME," + "	C.ORDINAL_POSITION";
	/**
	 * 表格宽度
	 */
	public static String tableWidth = "9000";

	/**
	 * 表格单元格背景色
	 */
	public static String tableCellBgColor = "D9EDF7";
	/**
	 * 表格单元格宽度
	 */
	public static String tableCellWidth = "1300";
	/**
	 * 表格单元格高度
	 */
	public static String tableCellHeight = "360";
}
