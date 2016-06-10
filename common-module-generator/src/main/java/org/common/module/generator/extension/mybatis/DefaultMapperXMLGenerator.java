/**
 * 
 */
package org.common.module.generator.extension.mybatis;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.common.module.generator.Config;
import org.common.module.generator.code.CodeGenerator;
import org.common.module.generator.db.Column;
import org.common.module.generator.db.Table;

/**
 * <p> mapper xml生成器
 * @author yejunwu123@gmail.com
 * @since 2016年6月10日 上午11:04:43
 */
public class DefaultMapperXMLGenerator implements CodeGenerator {
	
	private static final String XML_DECLARATION = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
	private static final String XML_DOCTYPE = "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">";

	/**
	 * dao类名,作为根元素明明空间
	 */
	private String daoClazzName;
	
	private String insertMethod = "save";
	private String deleteMethod = "remove";
	private String updateMethod = "update";
	private String getMethod = "get";
	private String listMethod = "list";
	
	public DefaultMapperXMLGenerator(String daoClazzName) {
		this.daoClazzName = daoClazzName;
	}
	
	@Override
	public void generateBean(Table table, Config config) {
		BufferedWriter bw = null;
		String beanName = table.getEntityName();
		try {
			//生成bean存放目录
			File beanDir = new File(config.getSaveDir() + File.separator + "mapper");
			if (!beanDir.exists()) {
				beanDir.mkdirs();
			}
			File file = new File(beanDir, beanName + ".xml");
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
			
			//xml声明
			bw.write(XML_DECLARATION);
			bw.newLine();
			bw.write(XML_DOCTYPE);
			bw.newLine();
			
			//根元素
			bw.write("<mapper namespace=\"" + daoClazzName + "\">");
			//结果集映射
			generateResultMap(table, bw);
			//插入
			generateInsert(table, bw);
			//删除
			generateDelete(table, bw);
			//修改
			generateUpdate(table, bw);
			//查询
			generateQuery(table, bw);
			//结束元素
			bw.newLine();
			bw.write("</mapper>");
			bw.flush();
		} catch (Exception e) {
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
	
	/**
	 * 查询
	 * @param table
	 * @param bw
	 * @throws IOException
	 */
	private void generateQuery(Table table, BufferedWriter bw) throws IOException {
		String selectFieldId = getSelectFieldId(table);
		String whereId = getWhereId(table);
		String orderById = getOrderById(table);
		//查询字段
		generateQuerySelectFields(table, selectFieldId, bw);
		//where条件
		generateQueryWhere(table, whereId, bw);
		//排序
		generateQueryOrderBy(table, orderById, bw);
		//根据主键查询
		generateQueryById(table, selectFieldId, bw);
		//列表查询
		generateQueryByList(table, selectFieldId, whereId, orderById, bw);
	}
	
	/**
	 * 排序字段
	 * @param table
	 * @param orderById
	 * @param bw
	 * @throws IOException
	 */
	private void generateQueryOrderBy(Table table, String orderById, BufferedWriter bw) throws IOException {
		bw.newLine();
		bw.newLine();
		bw.write("\t<!-- 排序字段 -->");
		
		bw.newLine();
		bw.write("\t<sql id=\"" + orderById + "\">");
		bw.newLine();
		bw.write("\t\t<if test=\"orderBy != null and orderBy.size >0\">");
		
		bw.newLine();
		bw.write("\t\t\tORDER BY ");
		
		bw.newLine();
		bw.write("\t\t\t<foreach collection=\"orderBy\" index=\"key\" item=\"val\" separator=\",\">");
		bw.newLine();
		bw.write("\t\t\t\t${key} ${val}");
		bw.newLine();
		bw.write("\t\t\t</foreach>");
		
		bw.newLine();
		bw.write("\t\t</if>");
		bw.newLine();
		bw.write("\t</sql>");
	}
	
	/**
	 * where条件
	 * @param table
	 * @param bw
	 * @throws IOException
	 */
	private void generateQueryWhere(Table table, String whereId, BufferedWriter bw) throws IOException {
		bw.newLine();
		bw.newLine();
		bw.write("\t<!-- where条件 -->");
		
		bw.newLine();
		bw.write("\t<sql id=\"" + whereId + "\">");
		bw.newLine();
		bw.write("\t\t<where>");
			for (Column column : table.getColumns()) {
				bw.newLine();
				bw.write("\t\t\t<if test=\"" + column.getFieldName() + " != null\">AND " + column.getName() + " = #{" + column.getFieldName() + "}</if>");
			}
		bw.newLine();
		bw.write("\t\t</where>");
		bw.newLine();
		bw.write("\t</sql>");
	}
	
	/**
	 * 查询字段
	 * @param table
	 * @param selectFieldId
	 * @param bw
	 * @throws IOException
	 */
	private void generateQuerySelectFields(Table table, String selectFieldId, BufferedWriter bw) throws IOException {
		bw.newLine();
		bw.newLine();
		bw.write("\t<!-- 查询字段 -->");
		
		bw.newLine();
		bw.write("\t<sql id=\"" + selectFieldId + "\">");
		
		bw.newLine();
		bw.write("\t\tSELECT");
		
		//指定field
		bw.newLine();
		bw.write("\t\t\t<if test=\"fields != null and fields.size() > 0\">");
		bw.newLine();
		bw.write("\t\t\t\t<foreach collection=\"fields\" separator=\",\" item=\"field\">");
		bw.newLine();
		bw.write("\t\t\t\t\t${field}");
		bw.newLine();
		bw.write("\t\t\t\t</foreach>");
		bw.newLine();
		bw.write("\t\t\t</if>");
		
		//默认查询所有field
		bw.newLine();
		bw.write("\t\t\t<if test=\"fields == null or fields.size() == 0\">");
		StringBuilder fields = new StringBuilder();
		for (Column column : table.getColumns()) {
			fields.append(column.getName()).append(", ");
		}
		fields.deleteCharAt(fields.lastIndexOf(", "));
		bw.newLine();
		bw.write("\t\t\t\t" + fields.toString());
		bw.newLine();
		bw.write("\t\t\t</if>");
		
		//表名
		bw.newLine();
		bw.write("\t\tFROM " + table.getName());
		
		bw.newLine();
		bw.write("\t</sql>");
	}
	
	/**
	 * 列表查询
	 * @param table
	 * @param selectFieldId
	 * @param bw
	 * @throws IOException
	 */
	private void generateQueryByList(Table table, String selectFieldId, String whereId, String orderById, BufferedWriter bw) throws IOException {
		bw.newLine();
		bw.newLine();
		bw.write("\t<!-- 列表查询 -->");
		
		bw.newLine();
		String resultMapId = getResultMapId(table);
		bw.write("\t<select id=\"" + this.listMethod + "\" resultMap=\"" + resultMapId + "\">");
		bw.newLine();
		bw.write("\t\t<include refid=\"" + selectFieldId + "\"/>");
		bw.newLine();
		bw.write("\t\t<include refid=\"" + whereId + "\"/>");
		bw.newLine();
		bw.write("\t\t<include refid=\"" + orderById + "\"/>");
		
		bw.newLine();
		bw.write("\t</select>");
	}
	
	/**
	 * 根据主键查询
	 * @param table
	 * @param bw
	 * @throws IOException
	 */
	private void generateQueryById(Table table, String selectFieldId, BufferedWriter bw) throws IOException {
		bw.newLine();
		bw.newLine();
		bw.write("\t<!-- 主键查询 -->");
		
		bw.newLine();
		String resultMapId = getResultMapId(table);
		bw.write("\t<select id=\"" + this.getMethod + "\" parameterType=\"long\" resultMap=\"" + resultMapId + "\">");
		bw.newLine();
		bw.write("\t\t<include refid=\"" + selectFieldId + "\"/>");
		bw.newLine();
		String[] idColumnField = getIdColumnAndField(table);
		bw.write("\t\tWHERE " + idColumnField[0] + " = #{" + idColumnField[1] + "}");
		
		bw.newLine();
		bw.write("\t</select>");
	}
	
	/**
	 * 更新
	 * @param table
	 * @param bw
	 * @throws IOException
	 */
	private void generateUpdate(Table table, BufferedWriter bw) throws IOException {
		bw.newLine();
		bw.newLine();
		bw.write("\t<!-- 更新 -->");
		
		bw.newLine();
		bw.write("\t<update id=\"" + this.updateMethod + "\" parameterType=\"" + table.getEntityName() + "\">");
		
		bw.newLine();
		bw.write("\t\tUPDATE " + table.getName());
		bw.newLine();
		
		//更新字段
		bw.write("\t\t<set>");
		bw.newLine();
		bw.write("\t\t\t<trim suffixOverrides=\",\">");
		for (Column column : table.getColumns()) {
			bw.newLine();
			bw.write("\t\t\t\t<if test=\"" + column.getFieldName() + " != null\">" + column.getName() + " = #{" + column.getFieldName() + "},</if>");
		}
		bw.newLine();
		bw.write("\t\t\t</trim>");
		bw.newLine();
		bw.write("\t\t</set>");
		
		//where条件
		String[] idColumnField = getIdColumnAndField(table);
		bw.newLine();
		bw.write("\t\tWHERE " + idColumnField[0] + " = #{" + idColumnField[1] + "}");
		
		bw.newLine();
		bw.write("\t</update>");
	}
	
	/**
	 * 删除
	 * @param table
	 * @param bw
	 * @throws IOException
	 */
	private void generateDelete(Table table, BufferedWriter bw) throws IOException {
		bw.newLine();
		bw.newLine();
		bw.write("\t<!-- 删除 -->");
		
		bw.newLine();
		bw.write("\t<delete id=\"" + this.deleteMethod + "\" parameterType=\"long\">");
		bw.newLine();
		String[] idColumnField = getIdColumnAndField(table);
		bw.write("\t\tDELETE FROM " + table.getName() + " WHERE " + idColumnField[0] + " = #{" + idColumnField[1] + "}");
		bw.newLine();
		bw.write("\t</delete>");
	}
	
	private String[] getIdColumnAndField(Table table) {
		String[] idColumnField = {"ID", "id"};
		for (Column column : table.getColumns()) {
			if (column.isPrimaryKey()) {
				idColumnField[0] = column.getName();
				idColumnField[1] = column.getFieldName();
				break;
			}
		}
		return idColumnField;
	}
	
	/**
	 * 保存
	 * @param table
	 * @param bw
	 * @throws IOException
	 */
	private void generateInsert(Table table, BufferedWriter bw) throws IOException {
		bw.newLine();
		bw.newLine();
		bw.write("\t<!-- 保存 -->");
		
		bw.newLine();
		bw.write("\t<insert id=\"" + this.insertMethod + "\" parameterType=\"" + table.getEntityName() + "\" useGeneratedKeys=\"true\" keyProperty=\"id\">");
		
		bw.newLine();
		bw.write("\t\tINSERT INTO " + table.getName());
	
		bw.newLine();
		bw.write("\t\t<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");
		//插入字段
		for (Column column : table.getColumns()) {
			bw.newLine();
			bw.write("\t\t\t<if test=\"" + column.getName() + " != null\">id,</if>");
		}
		bw.newLine();
		bw.write("\t\t</trim>");
		
		bw.newLine();
		bw.write("\t\tVALUES");
		
		bw.newLine();
		bw.write("\t\t<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");
		//字段值
		for (Column column : table.getColumns()) {
			bw.newLine();
			bw.write("\t\t\t<if test=\"" + column.getFieldName() + " != null\">#{" + column.getFieldName() + "},</if>");
		}
		bw.newLine();
		bw.write("\t\t</trim>");
		
		bw.newLine();
		bw.write("\t</insert>");
	}

	/**
	 * 生成结果集映射
	 * @param table
	 * @throws IOException 
	 */
	private void generateResultMap(Table table, BufferedWriter bw) throws IOException {
		bw.newLine();
		bw.write("\t<!-- 结果映射器 -->");
		String entityName = table.getEntityName();
		String resultMapId = getResultMapId(table);
		bw.newLine();
		bw.write("\t<resultMap id=\"" + resultMapId + "\" type=\"" + entityName + "\">");
		for (Column column : table.getColumns()) {
			bw.newLine();
			bw.write("\t\t<result property=\"" + column.getFieldName() + "\" column=\"" + column.getName() + "\"/>");
		}
		bw.newLine();
		bw.write("\t</resultMap>");
	}
	
	private String getResultMapId(Table table) {
		String entityName = table.getEntityName();
		String resultMapId = entityName.substring(0, 1).toLowerCase() + entityName.substring(1);
		return resultMapId;
	}
	
	/**
	 * 查询字段id
	 * @param table
	 * @return
	 */
	private String getSelectFieldId(Table table) {
		return table.getEntityName() + "Selector";
	}
	/**
	 * where条件id
	 * @param table
	 * @return
	 */
	private String getWhereId(Table table) {
		return table.getEntityName() + "Where";
	}
	/**
	 * 排序id
	 * @param table
	 * @return
	 */
	private String getOrderById(Table table) {
		return table.getEntityName() + "OrderBy";
	}

	public String getInsertMethod() {
		return insertMethod;
	}

	public void setInsertMethod(String insertMethod) {
		if (null != insertMethod && insertMethod.trim().length() > 0) {
			this.insertMethod = insertMethod;
		}
	}

	public String getDeleteMethod() {
		return deleteMethod;
	}

	public void setDeleteMethod(String deleteMethod) {
		if (null != deleteMethod && deleteMethod.trim().length() > 0) {
			this.deleteMethod = deleteMethod.trim();
		}
	}

	public String getUpdateMethod() {
		return updateMethod;
	}

	public void setUpdateMethod(String updateMethod) {
		if (null != updateMethod && updateMethod.trim().length() > 0) {
			this.updateMethod = updateMethod.trim();
		}
	}

	public String getGetMethod() {
		return getMethod;
	}

	public void setGetMethod(String getMethod) {
		if (null != getMethod && getMethod.trim().length() > 0) {
			this.getMethod = getMethod.trim();
		}
	}

	public String getListMethod() {
		return listMethod;
	}

	public void setListMethod(String listMethod) {
		if (null != listMethod && listMethod.trim().length() > 0) {
			this.listMethod = listMethod.trim();
		}
	}
	
}
