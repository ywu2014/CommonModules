package org.common.module.generator;

import java.util.HashSet;
import java.util.Set;

import org.common.module.generator.code.CodeGenerator;
import org.common.module.generator.db.TableInfoExtractor;

public class Config {
	/***********db相关参数**********/
	/**数据库驱动程序类*/
	private String driverClass;
	/**数据库连接url*/
	private String url;
	/**数据库用户名*/
	private String username;
	/**数据库密码*/
	private String password;
	
	/**表前缀,如T_,PRODUCT_*/
	private String tablePrefix;
	
	/**包含的表名*/
	private Set<String> includeTables;
	/**排除的列名*/
	private Set<String> excludeFields;
	
	/**表信息抽取器*/
	private TableInfoExtractor tableInfoExtractor;
	
	/***********bean相关参数**********/
	/**文件生成基准路径*/
	private String saveDir;
	
	/**是否生成entity*/
	private boolean generateBean = true;
	/**entity生成器,默认为DefaultEntityGenerator*/
	private CodeGenerator entityGenerator;
	/**entity所在包*/
	private String beanPackage;
	/**bean继承的类*/
	private String beanExtends;
	
	/**是否生成dao*/
	private boolean generateDao = false;
	/**dao所在包*/
	private String daoPackage;
	/**dao生成器,默认为DefaultDaoGenerator*/
	private CodeGenerator daoGenerator;
	
	/**是否生成service*/
	private boolean generateService = false;
	/**service所在包*/
	private String servicePackage;
	/**service生成器,默认为DefaultServiceGenerator*/
	private CodeGenerator serviceGenerator;
	
	/**版权声明*/
	private String license;

	/**获取数据库驱动程序类*/
	public String getDriverClass() {
		return driverClass;
	}

	/**设置数据库驱动程序类*/
	public Config setDriverClass(String driverClass) {
		this.driverClass = driverClass;
		return this;
	}

	/**获取数据库连接url*/
	public String getUrl() {
		return url;
	}
	/**设置数据库连接url*/
	public Config setUrl(String url) {
		this.url = url;
		return this;
	}
	/**获取数据库用户名*/
	public String getUsername() {
		return username;
	}
	/**设置数据库用户名*/
	public Config setUsername(String username) {
		this.username = username;
		return this;
	}
	/**获取数据库密码*/
	public String getPassword() {
		return password;
	}
	/**设置数据库密码*/
	public Config setPassword(String password) {
		this.password = password;
		return this;
	}
	/**获取表前缀,如T_,PRODUCT_*/
	public String getTablePrefix() {
		return tablePrefix;
	}
	/**设置表前缀,如T_,PRODUCT_*/
	public Config setTablePrefix(String tablePrefix) {
		this.tablePrefix = tablePrefix;
		return this;
	}
	/**获取包含的表名*/
	public Set<String> getIncludeTables() {
		return includeTables;
	}
	/**设置包含的表名*/
	public Config setIncludeTables(Set<String> includeTables) {
		this.includeTables = includeTables;
		return this;
	}
	/**设置包含的表名*/
	public Config setIncludeTables(String includeTables) {
		if (null != includeTables && includeTables.length() > 0) {
			includeTables = includeTables.trim();
			String[] includeTableArray = includeTables.split(",");
			if (null == this.includeTables) {
				this.includeTables = new HashSet<String>(includeTableArray.length);
			}
			for (int i = 0; i < includeTableArray.length; i++) {
				this.includeTables.add(includeTableArray[i].trim());
			}
		}
		return this;
	}
	/**添加包含的表名*/
	public Config addIncludeTable(String includeTable) {
		if (null == this.includeTables) {
			this.includeTables = new HashSet<String>();
		}
		this.includeTables.add(includeTable);
		return this;
	}
	/**是否生成entity*/
	public boolean isGenerateBean() {
		return generateBean;
	}
	/**设置是否生成entity*/
	public Config setGenerateBean(boolean generateBean) {
		this.generateBean = generateBean;
		return this;
	}
	/**是否生成dao*/
	public boolean isGenerateDao() {
		return generateDao;
	}
	/**设置是否生成dao*/
	public Config setGenerateDao(boolean generateDao) {
		this.generateDao = generateDao;
		return this;
	}
	/**获取是否生成service*/
	public boolean isGenerateService() {
		return generateService;
	}
	/**设置是否生成service*/
	public Config setGenerateService(boolean generateService) {
		this.generateService = generateService;
		return this;
	}
	/**获取entity所在包*/
	public String getBeanPackage() {
		return beanPackage;
	}
	/**设置entity所在包*/
	public Config setBeanPackage(String beanPackage) {
		this.beanPackage = beanPackage;
		return this;
	}
	/**获取dao所在包*/
	public String getDaoPackage() {
		return daoPackage;
	}
	/**设置dao所在包*/
	public Config setDaoPackage(String daoPackage) {
		this.daoPackage = daoPackage;
		return this;
	}
	/**获取service所在包*/
	public String getServicePackage() {
		return servicePackage;
	}
	/**设置service所在包*/
	public Config setServicePackage(String servicePackage) {
		this.servicePackage = servicePackage;
		return this;
	}
	/**获取文件生成基准路径*/
	public String getSaveDir() {
		return saveDir;
	}
	/**设置文件生成基准路径*/
	public Config setSaveDir(String saveDir) {
		this.saveDir = saveDir;
		return this;
	}
	/**获取版权声明*/
	public String getLicense() {
		return license;
	}
	/**设置版权声明*/
	public Config setLicense(String license) {
		this.license = license;
		return this;
	}
	/**获取表信息抽取器*/
	public TableInfoExtractor getTableInfoExtractor() {
		return tableInfoExtractor;
	}
	/**设置表信息抽取器*/
	public Config setTableInfoExtractor(TableInfoExtractor tableInfoExtractor) {
		this.tableInfoExtractor = tableInfoExtractor;
		return this;
	}
	/**获取排除的列名*/
	public Set<String> getExcludeFields() {
		return excludeFields;
	}
	/**设置排除的列名*/
	public Config setExcludeFields(Set<String> excludeFields) {
		this.excludeFields = excludeFields;
		return this;
	}
	/**设置排除的列名*/
	public Config setExcludeFields(String excludeFields) {
		if (null != excludeFields && excludeFields.length() > 0) {
			if (null == this.excludeFields) {
				this.excludeFields = new HashSet<String>();
			}
			String[] excludeFieldArray = excludeFields.split(",");
			for (String excludeField : excludeFieldArray) {
				this.excludeFields.add(excludeField.trim());
			}
		}
		return this;
	}
	/**添加排除的列名*/
	public Config addExcludeFields(String excludeField) {
		if (null == this.excludeFields) {
			this.excludeFields = new HashSet<String>();
		}
		this.excludeFields.add(excludeField);
		return this;
	}
	/**获取bean继承的类*/
	public String getBeanExtends() {
		return beanExtends;
	}
	/**设置bean继承的类*/
	public Config setBeanExtends(String beanExtends) {
		this.beanExtends = beanExtends;
		return this;
	}
	/**获取entity生成器,默认为DefaultEntityGenerator*/
	public CodeGenerator getEntityGenerator() {
		return entityGenerator;
	}
	/**设置entity生成器,默认为DefaultEntityGenerator*/
	public Config setEntityGenerator(CodeGenerator entityGenerator) {
		this.entityGenerator = entityGenerator;
		return this;
	}
	/**获取dao生成器,默认为DefaultDaoGenerator*/
	public CodeGenerator getDaoGenerator() {
		return daoGenerator;
	}
	/**设置dao生成器,默认为DefaultDaoGenerator*/
	public Config setDaoGenerator(CodeGenerator daoGenerator) {
		this.daoGenerator = daoGenerator;
		return this;
	}
	/**获取service生成器,默认为DefaultServiceGenerator*/
	public CodeGenerator getServiceGenerator() {
		return serviceGenerator;
	}
	/**设置service生成器,默认为DefaultServiceGenerator*/
	public Config setServiceGenerator(CodeGenerator serviceGenerator) {
		this.serviceGenerator = serviceGenerator;
		return this;
	}
	
}
