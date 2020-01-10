package com.winter.swallow;

/**
 * @Author winterSwallow
 * @Date 2020-01-09 14:12
 * @Description ��������
 */
public class CommonConst {

	/**
	 * ����ļ�·��
	 */
	public static String FILE_PATH = "D:\\temp.doc";
	/**
	 * ���ݿ�����
	 */
	public static String DB_NAME = "temp";
	/**
	 * ���ݿ���������
	 */
	public static String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
	/**
	 * ���ݿ�url
	 */
	public static String URL = "jdbc:mysql://localhost:3306/" + DB_NAME	+ "?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8";
	/**
	 * ���ݿ��û���
	 */
	public static String USER = "root";
	/**
	 * ���ݿ��û�����
	 */
	public static String PASSWORD = "123456";
	/**
	 * �����������
	 */
	public static String[] COLUMN_NAME_ARRAY = {"Ӣ����","������","����","������","��Ϊ��","����","Ĭ��ֵ","��ע��","����"};
	/**
	 * ��ѯ���
	 */
	public static String QUERY_SQL_STATEMENT = "SELECT" + "	T.TABLE_NAME AS '%s'," + "	T.TABLE_COMMENT AS '%s',"
			+ "	C.COLUMN_NAME AS '%s'," + "	C.COLUMN_TYPE AS '%s',"
			+ "	( CASE WHEN C.IS_NULLABLE = 'NO' THEN '' ELSE '��' END ) AS '%s',"
			+ "	( CASE WHEN C.COLUMN_KEY = 'PRI' THEN '��' ELSE '' END ) AS '%s'," + "	C.COLUMN_DEFAULT AS '%s',"
			+ "	C.COLUMN_COMMENT AS '%s'," + "	C.EXTRA AS '%s' " + "FROM" + "	information_schema.`TABLES` T"
			+ "	LEFT JOIN information_schema.`COLUMNS` C ON T.TABLE_NAME = C.TABLE_NAME "
			+ "	AND T.TABLE_SCHEMA = C.TABLE_SCHEMA " + "WHERE" + "	T.table_schema =  '" + DB_NAME + "' ORDER BY"
			+ "	C.TABLE_NAME," + "	C.ORDINAL_POSITION";
	/**
	 * �����
	 */
	public static String tableWidth = "9000";

	/**
	 * ���Ԫ�񱳾�ɫ
	 */
	public static String tableCellBgColor = "D9EDF7";
	/**
	 * ���Ԫ����
	 */
	public static String tableCellWidth = "1300";
	/**
	 * ���Ԫ��߶�
	 */
	public static String tableCellHeight = "360";
}
