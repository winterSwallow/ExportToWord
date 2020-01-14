package com.winter.swallow;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author winterSwallow
 * @Date 2020年1月14日 上午10:46:27
 * @Description 数据库操作工具类
 */
public class DBHelper {

	private String url = "";
	private DriverEnum driverEnum;
	private String host = "";
	private String port = "";
	private String dbName = "";
	private String userName = "root";
	private String password = "123456";

	public DBHelper(String host, String port, String dbName, String driverName, String userName, String password) {
		this.host = host;
		this.dbName = dbName;
		this.driverEnum = DriverEnum.getByDriverName(driverName);
		if (port != null && !port.isEmpty()) {
			this.port = port;
		} else {
			this.port = driverEnum.getPort();
		}
		this.userName = userName;
		this.password = password;
		switch (driverEnum) {
		case MYSQL:
			this.url = "jdbc:mysql://" + this.host + ":" + this.port + "/" + this.dbName
					+ "?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8";
			break;

		case SQLSERVER:
			this.url = "jdbc:sqlserver://" + this.host + ":" + this.port + ";DatabaseName=" + this.dbName;
			break;

		default:
			break;
		}
	}

	/**
	 * @param sql
	 * @return 查询结果集
	 */
	public List<List<RowInfo>> queryResult(String sql) {
		List<List<RowInfo>> tableList = new ArrayList<>();
		try {
			Class.forName(driverEnum.getJdbcName());
			Connection connection = DriverManager.getConnection(url, userName, password);
			Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet resultSet = statement.executeQuery(sql);
			String lastTableName = "";
			List<RowInfo> rowInfoList = new ArrayList<RowInfo>();
			while (resultSet.next()) {
				RowInfo rowInfo = new RowInfo();
				if (!lastTableName.equals("") && !lastTableName.equals(resultSet.getString(1))) {
					tableList.add(rowInfoList);
					rowInfoList = new ArrayList<>();
				}
				lastTableName = resultSet.getString(1);

				rowInfo.setTableName(resultSet.getString(1));
				rowInfo.setTableComment(resultSet.getString(2));
				rowInfo.setColumnName(resultSet.getString(3));
				rowInfo.setColumnType(resultSet.getString(4));
				rowInfo.setIsNullEnable(resultSet.getString(5));
				rowInfo.setIsPrimaryKey(resultSet.getString(6));
				rowInfo.setDefaultValue(resultSet.getString(7));
				rowInfo.setDescription(resultSet.getString(8));
				rowInfo.setOther(resultSet.getString(9));
				
				rowInfoList.add(rowInfo);
				if (resultSet.isLast()) {
					tableList.add(rowInfoList);
				}
			}
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tableList;
	}
}
