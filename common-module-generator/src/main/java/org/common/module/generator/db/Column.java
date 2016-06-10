package org.common.module.generator.db;
/**
 * <p> 数据库列元数据
 * @author ywu@wuxicloud.com
 * @since 2016年5月30日 下午5:33:44
 */
public class Column {
	/**
	 * 列名称
	 */
	private String name;
	/**
	 * 实体字段名称
	 */
	private String fieldName;
	/**
	 * 列注释
	 */
	private String comment;
	/**
	 * 列类型
	 */
	private String type;
	/**
	 * 是否是主键列
	 */
	private boolean primaryKey = false;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean isPrimaryKey() {
		return primaryKey;
	}
	public void setPrimaryKey(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
}
