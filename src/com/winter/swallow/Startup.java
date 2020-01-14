package com.winter.swallow;

import java.util.List;

/**
 * @Author winterSwallow
 * @Date 2020-01-09 14:12
 */
public class Startup {

	/**
	 * 定义MySql查询语句
	 */
	public static String MYSQL_QUERY_STATEMENT = "SELECT" + "	T.TABLE_NAME AS '英文名'," + "	T.TABLE_COMMENT AS '中文名',"
			+ "	C.COLUMN_NAME AS '列名'," + "	C.COLUMN_TYPE AS '列类型',"
			+ "	( CASE WHEN C.IS_NULLABLE = 'NO' THEN '' ELSE '是' END ) AS '可为空',"
			+ "	( CASE WHEN C.COLUMN_KEY = 'PRI' THEN '是' ELSE '' END ) AS '主键'," + "	C.COLUMN_DEFAULT AS '默认值',"
			+ "	C.COLUMN_COMMENT AS '列注释'," + "	C.EXTRA AS '其他' " + "FROM" + "	information_schema.`TABLES` T"
			+ "	LEFT JOIN information_schema.`COLUMNS` C ON T.TABLE_NAME = C.TABLE_NAME "
			+ "	AND T.TABLE_SCHEMA = C.TABLE_SCHEMA " + "WHERE" + "	T.table_schema =  '%s' ORDER BY"
			+ "	C.TABLE_NAME," + "	C.ORDINAL_POSITION";
	/**
	 * 定义SqlServer查询语句
	 */
	public static String SQLSERVER_QUERY_STATEMENT = "SELECT\r\n" + "	d.name '英文名',\r\n" + "	f.value '中文名',\r\n"
			+ "	a.name '列名',\r\n"
			+ "	b.name + '(' + CAST ( COLUMNPROPERTY( a.id, a.name, 'PRECISION' ) AS VARCHAR ) + ')' '列类型',\r\n"
			+ "	( CASE WHEN a.isnullable= 1 THEN '是' ELSE '' END ) '可为空',\r\n" + "	(\r\n" + "	CASE\r\n"
			+ "			\r\n" + "			WHEN EXISTS (\r\n" + "			SELECT\r\n" + "				1 \r\n"
			+ "			FROM\r\n" + "				sysobjects \r\n" + "			WHERE\r\n"
			+ "				xtype = 'PK' \r\n" + "				AND parent_obj = a.id \r\n"
			+ "				AND name IN (\r\n" + "				SELECT\r\n" + "					name \r\n"
			+ "				FROM\r\n" + "					sysindexes \r\n" + "				WHERE\r\n"
			+ "				indid IN ( SELECT indid FROM sysindexkeys WHERE id = a.id AND colid = a.colid ))) THEN\r\n"
			+ "				'是' ELSE '' \r\n" + "			END \r\n" + "			) '主键',\r\n"
			+ "			isnull( e.text, '' ) '默认值',\r\n" + "			CONVERT (\r\n"
			+ "				VARCHAR ( 50 ),\r\n" + "			ISNULL( g.[value], '' )) AS '列注释',\r\n"
			+ "			(case when COLUMNPROPERTY( a.id,a.name,'IsIdentity')=1 then '自增'else '' end) AS '其他' \r\n" + "		FROM\r\n" + "			syscolumns a\r\n"
			+ "			LEFT JOIN systypes b ON a.xtype= b.xusertype\r\n"
			+ "			INNER JOIN sysobjects d ON a.id= d.id \r\n" + "			AND d.xtype= 'U' \r\n"
			+ "			AND d.name<> 'dtproperties'\r\n" + "			LEFT JOIN syscomments e ON a.cdefault= e.id\r\n"
			+ "			LEFT JOIN sys.extended_properties g ON a.id= g.major_id \r\n"
			+ "			AND a.colid= g.minor_id\r\n"
			+ "			LEFT JOIN sys.extended_properties f ON d.id= f.major_id \r\n" + "			AND f.minor_id= 0 \r\n"
			+ "		WHERE\r\n" + "			b.name IS NOT NULL \r\n" + "		ORDER BY\r\n" + "		a.id,\r\n"
			+ "	a.colorder";

	public static void main(String[] args) {
		System.out.println("数据写入开始");
		exportFromMySql();
		exportFromSqlServer();
		System.out.println("数据写入完成");
	}

	/**
	 * 导出MySql库信息
	 */
	public static void exportFromMySql() {
		String sql = String.format(MYSQL_QUERY_STATEMENT, "temp");
		DBHelper dbHelper = new DBHelper("localhost", "3306", "temp", "MySql", "root", "123456");
		List<List<RowInfo>> tableList = dbHelper.queryResult(sql);
		DocHelper docHelper = new DocHelper();
		docHelper.writeToWord(tableList, "D:\\mysql.doc");
	}

	/**
	 * 导出SqlServer库信息
	 */
	public static void exportFromSqlServer() {
		DBHelper dbHelper = new DBHelper("localhost", "1433", "zddw", "SqlServer", "sa", "123456");
		List<List<RowInfo>> tableList = dbHelper.queryResult(SQLSERVER_QUERY_STATEMENT);
		DocHelper docHelper = new DocHelper();
		docHelper.writeToWord(tableList, "D:\\sqlServer.doc");
	}

}
