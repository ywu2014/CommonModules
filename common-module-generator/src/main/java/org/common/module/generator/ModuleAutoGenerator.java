package org.common.module.generator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.common.module.generator.code.dao.DefaultDaoGenerator;
import org.common.module.generator.code.entity.DefaultEntityGenerator;
import org.common.module.generator.code.service.DefaultServiceGenerator;
import org.common.module.generator.db.Column;
import org.common.module.generator.db.ConnectionUtil;
import org.common.module.generator.db.Table;
import org.common.module.generator.db.TableInfoExtractor;
import org.common.module.generator.db.mysql.MysqlTableInfoExtractor;
/**
 * <p> 模块生成器
 * @author yejunwu123@gmail.com
 * @since 2016年6月8日 下午4:09:41
 */
public class ModuleAutoGenerator {
	
	public void generate(Config config) {
		TableInfoExtractor extractor = essueTableInfoExtractor(config);
		try {
			//初始化数据库连接
			initConnection(config);
			
			//Table
			List<Table> tables = new ArrayList<Table>();
			
			//获取所有表名
			List<String> tableNames = extractor.getAllTables();
			
			if (null != tableNames && !tableNames.isEmpty()) {
				if (null != config.getIncludeTables() && !config.getIncludeTables().isEmpty()) {
					for (String tableName : tableNames) {
						if (config.getIncludeTables().contains(tableName)) {	//过滤不需要的表
							tables.add(process(tableName, extractor, config));
						}
					}
				} else {
					for (String tableName : tableNames) {
						tables.add(process(tableName, extractor, config));
					}
				}
			}
			
			if (null != tables && !tables.isEmpty()) {
				for (Table table : tables) {
					//生成bean信息
					if (config.isGenerateBean()) {
						if (null != config.getEntityGenerator()) {
							config.getEntityGenerator().generateBean(table, config);
						} else {
							System.out.println("use default entity generator");
							new DefaultEntityGenerator().generateBean(table, config);
						}
					}
					//生成dao信息
					if (config.isGenerateDao()) {
						if (null != config.getDaoGenerator()) {
							config.getDaoGenerator().generateBean(table, config);
						} else {
							System.out.println("use default dao generator");
							new DefaultDaoGenerator().generateBean(table, config);
						}
					}
					//生成service信息
					if (config.isGenerateService()) {
						if (null != config.getServiceGenerator()) {
							config.getServiceGenerator().generateBean(table, config);
						} else {
							System.out.println("use default service generator");
							new DefaultServiceGenerator().generateBean(table, config);
						}
					}
					//其他信息
					generateOtherInfo(table, config);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			//关闭数据库连接
			ConnectionUtil.closeConnection();
		}
	}
	
	//Column
	private Table process(String tableName, TableInfoExtractor extractor, Config config) throws SQLException {
		//表信息
		Table table = extractor.getTableInfo(tableName);
		//列信息
		List<Column> columns = extractor.getColumns(tableName);
		/*if (null != config.getExcludeFields() && !config.getExcludeFields().isEmpty()) {
			List<Column> includeColumns = new ArrayList<Column>();
			for (Column column : columns) {
				if (!config.getExcludeFields().contains(column.getName())) {	//过滤不需要的列
					includeColumns.add(column);
				}
			}
			table.setColumns(includeColumns);
		} else {*/
			table.setColumns(columns);
		//}
		return table;
	}
	
	/**
	 * 初始化数据库连接
	 * @param config
	 */
	private void initConnection(Config config) {
		String driverClass = config.getDriverClass();
		if (null == driverClass || driverClass.trim().isEmpty()) {
			throw new RuntimeException("参数[driverClass]为空.");
		}
		String username = config.getUsername();
		if (null == username || username.trim().isEmpty()) {
			throw new RuntimeException("参数[username]为空.");
		}
		String url = config.getUrl();
		if (null == url || url.trim().isEmpty()) {
			throw new RuntimeException("参数[url]为空.");
		}
		try {
			Class.forName(driverClass.trim());
			Connection connection = DriverManager.getConnection(url.trim(), username.trim(), config.getPassword());
			ConnectionUtil.setConnection(connection);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	//DB类型
	private TableInfoExtractor essueTableInfoExtractor(Config config) {
		TableInfoExtractor extractor = config.getTableInfoExtractor();
		if (null == extractor) {
			String driverClass = config.getDriverClass();
			if (null == driverClass || driverClass.trim().isEmpty()) {
				throw new RuntimeException("参数driverClass为空.");
			}
			if (driverClass.contains("mysql")) {
				extractor = new MysqlTableInfoExtractor();
			} else if (driverClass.contains("oracle")) {
				
			}
			if (null == extractor) {
				throw new RuntimeException("不支持的数据库版本");
			}
			config.setTableInfoExtractor(extractor);
		}
		return extractor;
	}
	
	protected void generateOtherInfo(Table table, Config config) {
		//do nothing
	}
}
