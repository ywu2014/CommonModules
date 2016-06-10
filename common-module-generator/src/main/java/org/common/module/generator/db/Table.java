package org.common.module.generator.db;

import java.util.ArrayList;
import java.util.List;

/**
 * <p> 数据库表元数据
 * @author ywu@wuxicloud.com
 * @since 2016年5月30日 下午5:31:45
 */
public class Table {
	/**
	 * 表名
	 */
	private String name;
	/**
	 * 实体名称
	 */
	private String entityName;
	/**
	 * 注释
	 */
	private String comments;
	/**
	 * 列
	 */
	private List<Column> columns = new ArrayList<Column>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public List<Column> getColumns() {
		return columns;
	}
	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	
}
