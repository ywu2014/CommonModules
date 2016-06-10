package org.common.module.generator.db;

import java.sql.SQLException;
import java.util.List;

/**
 * <p> 表信息抽取
 * @author ywu@wuxicloud.com
 * @since 2016年5月30日 下午5:40:22
 */
public interface TableInfoExtractor {
	/**
	 * 获取所有表信息
	 * @return
	 */
	public List<String> getAllTables() throws SQLException;
	/**
	 * 获取表
	 * @param includeTables
	 * @return
	 */
	public Table getTableInfo(String table) throws SQLException;
	/**
	 * 获取列信息
	 * @param table
	 * @return
	 */
	public List<Column> getColumns(String table) throws SQLException;
	/**
	 * 数据库列类型与java field类型映射
	 * @param columnType
	 * @return
	 */
	public String getFieldMapping(String columnType);
}
