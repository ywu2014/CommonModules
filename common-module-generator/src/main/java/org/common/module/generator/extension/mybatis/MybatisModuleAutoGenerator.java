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
	/**
	 * 是否生成xml
	 */
	private boolean generateMapperXML = true;
	/**
	 * 是否生成mapper类
	 */
	private boolean generateMapperClass = true;
	/**
	 * mapper类包名
	 */
	private String mapperPackage;
	/**
	 * 默认插入方法名
	 */
	private String insertMethod = "save";
	/**
	 * 默认删除方法名
	 */
	private String deleteMethod = "remove";
	/**
	 * 默认更新方法名
	 */
	private String updateMethod = "update";
	/**
	 * 默认主键查询方法名
	 */
	private String getMethod = "get";
	/**
	 * 默认列表查询方法名
	 */
	private String listMethod = "list";
	
	/**
	 * mapper类后缀,如ProductMapper,默认为Mapper
	 */
	private String mapperClassSuffix;
	/**
	 * mapper继承的接口
	 */
	private String mapperExtendsClass;
	/**
	 * mapper继承接口所在包
	 */
	private String mapperExtendsClassPackage;
	
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
			mapperClassGenerator.setMapperClassSuffix(mapperClassSuffix);
			mapperClassGenerator.setMapperExtendsClass(mapperExtendsClass);
			mapperClassGenerator.setMapperExtendsClassPackage(mapperExtendsClassPackage);
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
	/**
	 * 是否生成xml
	 * @return
	 */
	public boolean isGenerateMapperXML() {
		return generateMapperXML;
	}
	/**
	 * 是否生成xml
	 * @param generateMapperXML
	 */
	public void setGenerateMapperXML(boolean generateMapperXML) {
		this.generateMapperXML = generateMapperXML;
	}
	/**
	 * 是否生成mapper类
	 * @return
	 */
	public boolean isGenerateMapperClass() {
		return generateMapperClass;
	}
	/**
	 * 是否生成mapper类
	 * @param generateMapperClass
	 */
	public void setGenerateMapperClass(boolean generateMapperClass) {
		this.generateMapperClass = generateMapperClass;
	}
	/**
	 * mapper类包名
	 * @return
	 */
	public String getMapperPackage() {
		return mapperPackage;
	}
	/**
	 * mapper类包名
	 * @param mapperPackage
	 */
	public void setMapperPackage(String mapperPackage) {
		this.mapperPackage = mapperPackage;
	}
	/**
	 * 默认插入方法名
	 * @return
	 */
	public String getInsertMethod() {
		return insertMethod;
	}
	/**
	 * 默认插入方法名
	 * @param insertMethod
	 */
	public void setInsertMethod(String insertMethod) {
		this.insertMethod = insertMethod;
	}
	/**
	 * 默认删除方法名
	 * @return
	 */
	public String getDeleteMethod() {
		return deleteMethod;
	}
	/**
	 * 默认删除方法名
	 * @param deleteMethod
	 */
	public void setDeleteMethod(String deleteMethod) {
		this.deleteMethod = deleteMethod;
	}
	/**
	 * 默认更新方法名
	 * @return
	 */
	public String getUpdateMethod() {
		return updateMethod;
	}
	/**
	 * 默认更新方法名
	 * @param updateMethod
	 */
	public void setUpdateMethod(String updateMethod) {
		this.updateMethod = updateMethod;
	}
	/**
	 * 默认主键查询方法名
	 * @return
	 */
	public String getGetMethod() {
		return getMethod;
	}
	/**
	 * 默认主键查询方法名
	 * @param getMethod
	 */
	public void setGetMethod(String getMethod) {
		this.getMethod = getMethod;
	}
	/**
	 * 默认列表查询方法名
	 * @return
	 */
	public String getListMethod() {
		return listMethod;
	}
	/**
	 * 默认列表查询方法名
	 * @param listMethod
	 */
	public void setListMethod(String listMethod) {
		this.listMethod = listMethod;
	}
	/**
	 * mapper类后缀,如ProductMapper,默认为Mapper
	 * @return
	 */
	public String getMapperClassSuffix() {
		return mapperClassSuffix;
	}
	/**
	 * mapper类后缀,如ProductMapper,默认为Mapper
	 * @param mapperClassSuffix
	 */
	public void setMapperClassSuffix(String mapperClassSuffix) {
		this.mapperClassSuffix = mapperClassSuffix;
	}
	/**
	 * mapper继承的接口
	 * @return
	 */
	public String getMapperExtendsClass() {
		return mapperExtendsClass;
	}
	/**
	 * mapper继承的接口
	 * @param mapperExtendsClass
	 */
	public void setMapperExtendsClass(String mapperExtendsClass) {
		this.mapperExtendsClass = mapperExtendsClass;
	}
	/**
	 * mapper继承接口所在包
	 * @return
	 */
	public String getMapperExtendsClassPackage() {
		return mapperExtendsClassPackage;
	}
	/**
	 * mapper继承接口所在包
	 * @param mapperExtendsClassPackage
	 */
	public void setMapperExtendsClassPackage(String mapperExtendsClassPackage) {
		this.mapperExtendsClassPackage = mapperExtendsClassPackage;
	}
	
}
