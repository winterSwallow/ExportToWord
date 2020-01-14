package com.winter.swallow;

/**
 * @Author winterSwallow
 * @Date 2020年1月14日 上午10:28:36
 * @Description 数据库驱动
 */
public enum DriverEnum {
	/**
	 * MySql驱动
	 */
	MYSQL("MySql", "com.mysql.cj.jdbc.Driver", "3306"),
	/**
	 * SqlServer驱动
	 */
	SQLSERVER("SqlServer", "com.microsoft.sqlserver.jdbc.SQLServerDriver", "1433");

	private String driverName;
	private String jdbcName;
	private String port;

	private DriverEnum(String driverName, String jdbcName, String port) {
		this.driverName = driverName;
		this.jdbcName = jdbcName;
		this.port = port;
	}

	/**
	 * @param driverName
	 * @return 通过驱动名称获取JDBC连接名称
	 */
	public static DriverEnum getByDriverName(String driverName) {
		for (DriverEnum driverEnum : DriverEnum.values()) {
			if (driverEnum.getDriverName().equalsIgnoreCase(driverName)) {
				return driverEnum;
			}
		}
		return DriverEnum.MYSQL;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getJdbcName() {
		return jdbcName;
	}

	public void setJdbcName(String jdbcName) {
		this.jdbcName = jdbcName;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}
}
