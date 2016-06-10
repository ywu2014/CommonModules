/**
 * 
 */
package org.common.module.generator.extension.mybatis;

import org.common.module.generator.Config;
import org.common.module.generator.ModuleAutoGenerator;
import org.common.module.generator.db.Table;

/**
 * <p> mybatis模块生成器
 * @author yejunwu123@gmail.com
 * @since 2016年6月8日 下午4:10:13
 */
public class MybatisModuleAutoGenerator extends ModuleAutoGenerator {
	
	private boolean generateMapperXML = true;
	private boolean generateMapperClass = true;
	/**
	 * mapper类包名
	 */
	private String mapperPackage;
	
	private String insertMethod = "save";
	private String deleteMethod = "remove";
	private String updateMethod = "update";
	private String getMethod = "get";
	private String listMethod = "list";
	
	public MybatisModuleAutoGenerator(String mapperPackage) {
		this.mapperPackage = mapperPackage;
	}
	
	@Override
	public void generate(Config config) {
		config.setGenerateDao(false);
		super.generate(config);
	}
	
	/**
	 * 生成mapper信息
	 */
	@Override
	protected void generateOtherInfo(Table table, Config config) {
		DefaultMapperClassGenerator mapperClassGenerator = new DefaultMapperClassGenerator(mapperPackage);
		if (this.generateMapperClass) {
			mapperClassGenerator.generateBean(table, config);
		}
		if (this.generateMapperXML) {
			DefaultMapperXMLGenerator mapperXMLGenerator = new DefaultMapperXMLGenerator(mapperClassGenerator.getMapperClassQualifiedName(table));
			mapperXMLGenerator.setDeleteMethod(deleteMethod);
			mapperXMLGenerator.setGetMethod(getMethod);
			mapperXMLGenerator.setInsertMethod(insertMethod);
			mapperXMLGenerator.setListMethod(listMethod);
			mapperXMLGenerator.setUpdateMethod(updateMethod);
			mapperXMLGenerator.generateBean(table, config);
		}
	}

	public boolean isGenerateMapperXML() {
		return generateMapperXML;
	}

	public void setGenerateMapperXML(boolean generateMapperXML) {
		this.generateMapperXML = generateMapperXML;
	}

	public boolean isGenerateMapperClass() {
		return generateMapperClass;
	}

	public void setGenerateMapperClass(boolean generateMapperClass) {
		this.generateMapperClass = generateMapperClass;
	}

	public String getMapperPackage() {
		return mapperPackage;
	}

	public void setMapperPackage(String mapperPackage) {
		this.mapperPackage = mapperPackage;
	}

	public String getInsertMethod() {
		return insertMethod;
	}

	public void setInsertMethod(String insertMethod) {
		this.insertMethod = insertMethod;
	}

	public String getDeleteMethod() {
		return deleteMethod;
	}

	public void setDeleteMethod(String deleteMethod) {
		this.deleteMethod = deleteMethod;
	}

	public String getUpdateMethod() {
		return updateMethod;
	}

	public void setUpdateMethod(String updateMethod) {
		this.updateMethod = updateMethod;
	}

	public String getGetMethod() {
		return getMethod;
	}

	public void setGetMethod(String getMethod) {
		this.getMethod = getMethod;
	}

	public String getListMethod() {
		return listMethod;
	}

	public void setListMethod(String listMethod) {
		this.listMethod = listMethod;
	}
	
}
