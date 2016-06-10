/**
 * 
 */
package org.common.module.generator.code.entity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.common.module.generator.Config;
import org.common.module.generator.code.CodeGenerator;
import org.common.module.generator.db.Column;
import org.common.module.generator.db.Table;

/**
 * <p> 默认实体生成器
 * @author yejunwu123@gmail.com
 * @since 2016年6月8日 下午3:00:58
 */
public class DefaultEntityGenerator implements CodeGenerator {

	@Override
	public void generateBean(Table table, Config config) {
		BufferedWriter bw = null;
		try {
			//获取bean名称
			String beanName = getBeanName(table, config);
			table.setEntityName(beanName);
			
			//生成bean存放目录
			File beanDir = new File(config.getSaveDir() + File.separator + "bean");
			if (!beanDir.exists()) {
				beanDir.mkdirs();
			}
			File file = new File(beanDir, beanName + ".java");
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
			
			//版权声明
			if (null != config.getLicense() && config.getLicense().length() > 0) {
				bw.write("/**");
				bw.newLine();
				bw.write(" * " + config.getLicense());
				bw.newLine();
				bw.write(" */");
			}
			
			//包名
			bw.newLine();
			bw.write("package " + config.getBeanPackage() + ";");
			bw.newLine();
			bw.newLine();
			if (null == config.getBeanExtends() || "".equals(config.getBeanExtends().trim())) {
				bw.write("import java.io.Serializable;");
				bw.newLine();
			}
			
			//列
			boolean includeDate = false;
			List<Column> columns = table.getColumns();
			List<String> fieldTypes = new ArrayList<String>(columns.size());
			for (int i = 0; i < columns.size(); i++) {
				Column column = columns.get(i);
				String fieldType = config.getTableInfoExtractor().getFieldMapping(column.getType());
				fieldTypes.add(fieldType);
				if ("Date".equals(fieldType)) {
					includeDate = true;
				}
			}
			
			//判断是否需要导入日期
			if (includeDate) {
				bw.newLine();
				bw.write("import java.util.Date;");
				bw.newLine();
			}
			
			//类注释
			if (null != table.getComments() && table.getComments().length() > 0) {
				bw.newLine();
				bw.write("/**");
				bw.newLine();
				bw.write(" * " + table.getComments());
				bw.newLine();
				bw.write(" */");
			}
			
			//类声明
			bw.newLine();
			if (null != config.getBeanExtends() && config.getBeanExtends().length() > 0) {
				bw.write("public class " + beanName + " extends " + config.getBeanExtends() + " {");
			} else {
				bw.write("public class " + beanName + " implements Serializable {");
				bw.newLine();
				bw.write("\tprivate static final long serialVersionUID = 1L;");
			}
			
			//field
			for (int i = 0; i < columns.size(); i++) {
				Column column = columns.get(i);
				String fieldName = getFieldName(column);
				column.setFieldName(fieldName);
				String fieldType = fieldTypes.get(i);
				String comments = column.getComment();
				bw.newLine();
				bw.write("\t/**");
				bw.newLine();
				bw.write("\t * " + comments);
				bw.newLine();
				bw.write("\t */");
				bw.newLine();
				bw.write("\tprivate " + fieldType + " " + fieldName + ";");
			}
			bw.newLine();
			
			//get set方法
			for (int i = 0; i < columns.size(); i++) {
				String tempType = fieldTypes.get(i);
				String tempField = getFieldName(columns.get(i));
				String field = tempField.substring(0, 1).toUpperCase() + tempField.substring(1);
				bw.newLine();
				bw.write("\tpublic " + tempType + " get" + field + "() {");
				bw.newLine();
				bw.write("\t\treturn this." + tempField + ";");
				bw.newLine();
				bw.write("\t}");
				bw.newLine();
				bw.newLine();
				bw.write("\tpublic void set" + field + "(" + tempType + " " + tempField + ") {");
				bw.newLine();
				bw.write("\t\tthis." + tempField + " = " + tempField + ";");
				bw.newLine();
				bw.write("\t}");
				bw.newLine();
			}
			
			bw.newLine();
			bw.write("}");
			bw.newLine();
			bw.flush();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			try {
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
	}

	private String getFieldName(Column column) {
		String columnName = column.getName();
		StringBuilder finalColumnName = new StringBuilder();
		if (columnName.contains("_")) {
			String[] columnNameSplits = columnName.split("_");
			int index = 0;
			for (String columnNameSplit : columnNameSplits) {
				String firstChar = columnNameSplit.substring(0, 1);
				finalColumnName.append(index == 0 ? firstChar.toLowerCase() : firstChar.toUpperCase());
				finalColumnName.append(columnNameSplit.substring(1).toLowerCase());
				index++;
			}
		} else {
			finalColumnName.append(columnName.toLowerCase());
		}
		return finalColumnName.toString();
	}
	
	/**
	 * 获取bean名称(驼峰法)
	 * @param table
	 * @param config
	 * @return
	 */
	private String getBeanName(Table table, Config config) {
		String tableName = table.getName();
		String tablePrefix = config.getTablePrefix();
		String beanName = tableName;
		if (null != tablePrefix && tablePrefix.length() > 0) {
			beanName = beanName.substring(beanName.indexOf(tablePrefix));
		}
		StringBuilder finalBeanName = new StringBuilder();
		if (beanName.contains("_")) {
			String[] beanNameSplits = beanName.split("_");
			for (String beanNameSplit : beanNameSplits) {
				finalBeanName.append(beanNameSplit.substring(0, 1).toUpperCase());
				finalBeanName.append(beanNameSplit.substring(1).toLowerCase());
			}
		} else {
			finalBeanName.append(beanName.substring(0, 1).toUpperCase());
			finalBeanName.append(beanName.substring(1).toLowerCase());
		}
		return finalBeanName.toString();
	}
}
