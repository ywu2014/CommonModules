/**
 * 
 */
package org.common.module.generator;

import org.common.module.generator.extension.mybatis.MybatisModuleAutoGenerator;
import org.junit.Test;

/**
 * <p> mybatis生成器测试
 * @author yejunwu123@gmail.com
 * @since 2016年6月10日 下午1:37:42
 */
public class MybatisModuleAutoGeneratorTest {
	@Test
	public void testGenerate() {
		Config config = new Config();
		config.setDriverClass("com.mysql.jdbc.Driver")
			.setUsername("root")
			.setPassword("passw0rd")
			.setUrl("jdbc:mysql://127.0.0.1:3306/easy_shop")
			.setLicense("Apache License 2.0")
			.setGenerateBean(true)
			.setBeanPackage("com.jiangnan.bean")
			//.setIncludeTables("test1_person")
			.setExcludeFields("DELETED,VERSION")
			.setSaveDir("d:/test")
			.setGenerateService(true)
			.setServicePackage("com.jiangnan.product.service")
			.setServiceExtendClass("BaseService<{0}>")
			.setServiceExtendClassPackage("org.common.module.mybatis.service")
			.setServiceImplExtendClass("BaseServiceImpl<{0}>")
			.setServiceImplExtendClassPackage("org.common.module.mybatis.service.impl")
			//.setExcludeFields("id")
			;
		MybatisModuleAutoGenerator generator = new MybatisModuleAutoGenerator("com.eashshop.order.mapper");
		generator.setMapperExtendsClass("BaseMapper<{0}>");
		generator.setMapperExtendsClassPackage("org.common.module.mybatis.mapper");
		generator.generate(config);
	}
}
