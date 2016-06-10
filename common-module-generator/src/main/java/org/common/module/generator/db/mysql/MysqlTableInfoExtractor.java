package org.common.module.generator.db.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.common.module.generator.db.Column;
import org.common.module.generator.db.ConnectionUtil;
import org.common.module.generator.db.Table;
import org.common.module.generator.db.TableInfoExtractor;

public class MysqlTableInfoExtractor implements TableInfoExtractor {
	
	
	private Map<String, String> maps = new HashMap<String, String>();
	
	public List<String> getAllTables() throws SQLException {
		List<String> tables = new ArrayList<String>();
		PreparedStatement pstate = ConnectionUtil.getConnection().prepareStatement("show tables");
		ResultSet results = pstate.executeQuery();
		while (results.next()) {
			String tableName = results.getString(1);
			tables.add(tableName);
		}
		pstate = ConnectionUtil.getConnection().prepareStatement("show table status");
		results = pstate.executeQuery();
		while (results.next()) {
			maps.put(results.getString("NAME"), results.getString("COMMENT"));
		}
		results.close();
		pstate.close();
		return tables;
	}

	public Table getTableInfo(String table) {
		Table t = new Table();
		t.setName(table);
		t.setComments(maps.get(table));
		return t;
	}

	public List<Column> getColumns(String table) throws SQLException {
		PreparedStatement prepareStatement = ConnectionUtil.getConnection().prepareStatement("show full fields from `" + table + "`");
		//prepareStatement.setString(1, table);
		ResultSet results = prepareStatement.executeQuery();
		List<Column> columns = new ArrayList<Column>();
		while (results.next()) {
			Column column = new Column();
			column.setName(results.getString("FIELD"));
			column.setComment(results.getString("COMMENT"));
			column.setType(results.getString("TYPE"));
			String key = results.getString("KEY");
			if ("PRI".equals(key)) {
				column.setPrimaryKey(true);
			}
			columns.add(column);
		}
		if (null != results) {
			results.close();
		}
		if (null != prepareStatement) {
			prepareStatement.close();
		}
		return columns;
	}

	/**
	 * 数据库类型与实体类型映射
	 */
	public String getFieldMapping(String columnType) {
		if (columnType.indexOf("char") > -1) {
			return "String";
		} else if (columnType.indexOf("bigint") > -1) {
			return "Long";
		} else if (columnType.indexOf("int") > -1) {
			return "Integer";
		} else if (columnType.indexOf("date") > -1 || columnType.indexOf("timestamp") > -1) {
			return "Date";
		} else if (columnType.indexOf("text") > -1) {
			return "String";
		} else if (columnType.indexOf("bit") > -1) {
			return "Boolean";
		} else if (columnType.indexOf("decimal") > -1) {
			return "BigDecimal";
		} else if (columnType.indexOf("blob") > -1) {
			return "byte[]";
		} else if (columnType.indexOf("float") > -1) {
			return "Float";
		} else if (columnType.indexOf("double") > -1) {
			return "Double";
		}
		return null;
	}

}
