/**
 * 
 */
package org.common.module.generator.extension.mybatis;

import org.common.module.generator.Config;
import org.common.module.generator.code.CodeGenerator;
import org.common.module.generator.db.Table;

/**
 * <p> mapper类生成器
 * @author yejunwu123@gmail.com
 * @since 2016年6月10日 上午11:05:19
 */
public class DefaultMapperClassGenerator implements CodeGenerator {
	
	private String mapperPackage;
	private String mapperClassSuffix = "Mapper";
	
	public DefaultMapperClassGenerator(String mapperPackage) {
		this.mapperPackage = mapperPackage;
	}

	@Override
	public void generateBean(Table table, Config config) {
		
	}

	public String getMapperClassQualifiedName(Table table) {
		return this.mapperPackage + "." + table.getEntityName() + this.mapperClassSuffix;
	}
}
