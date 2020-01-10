package com.winter.swallow;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author winterSwallow
 * @Date 2020-01-09 14:12
 */
public class Startup {

	public static void main(String[] args) {
		try {
			System.out.println("数据写入开始");
			Class.forName(CommonConst.DRIVER_NAME);
			Connection connection = DriverManager.getConnection(CommonConst.URL, CommonConst.USER,
					CommonConst.PASSWORD);
			Statement statement = connection.createStatement();
			String querySql = String.format(CommonConst.QUERY_SQL_STATEMENT, CommonConst.COLUMN_NAME_ARRAY);
			ResultSet resultSet = statement.executeQuery(querySql);
			List<List<Map<Integer, String>>> tableList = new ArrayList<List<Map<Integer, String>>>();
			String tableName = "";
			List<Map<Integer, String>> rowInfoList = new ArrayList<Map<Integer, String>>();
			while (resultSet.next()) {
				Map<Integer, String> rowInfo = new HashMap<Integer, String>();
				if (!tableName.equals("") && !tableName.equals(resultSet.getString(1))) {
					tableList.add(rowInfoList);
					rowInfoList = new ArrayList<>();
				}
				tableName = resultSet.getString(1);
				for (int i = 0; i < CommonConst.COLUMN_NAME_ARRAY.length; i++) {
					rowInfo.put(i, resultSet.getString(i + 1));
				}
				rowInfoList.add(rowInfo);
				if (resultSet.isLast()) {
					tableList.add(rowInfoList);
				}
			}
			connection.close();
			DocHelper docHelper = new DocHelper();
			docHelper.writeToWord(tableList);
			System.out.println("数据写入完成");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
