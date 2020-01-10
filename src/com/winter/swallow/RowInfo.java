package com.winter.swallow;

/**
 * @Author winterSwallow
 * @Date 2020-01-09 16:41
 * @Description 输出表信息定义
 */
public class RowInfo {

    /**
     * 表名
     */
    public String tableName;
    /**
     * 表注释
     */
    public String tableComment;
    /**
     * 列名
     */
    public String columnName;
    /**
     * 列类型
     */
    public String columnType;
    /**
     * 是否可为空
     */
    public String isNullEnable;
    /**
     * 是否主键
     */
    public String isPrimaryKey;
    /**
     * 默认值
     */
    public String defaultValue;
    /**
     * 描述
     */
    public String description;
    /**
     * 其他
     */
    public String other;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableComment() {
        return tableComment;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }


    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getIsNullEnable() {
        return isNullEnable;
    }

    public void setIsNullEnable(String isNullEnable) {
        this.isNullEnable = isNullEnable;
    }

    public String getIsPrimaryKey() {
        return isPrimaryKey;
    }

    public void setIsPrimaryKey(String isPrimaryKey) {
        this.isPrimaryKey = isPrimaryKey;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }
}
