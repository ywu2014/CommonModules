/**
 * 
 */
package org.common.module.generator;

import org.junit.Test;

/**
 * <p> 模块生成器测试
 * @author yejunwu123@gmail.com
 * @since 2016年6月10日 下午9:32:40
 */
public class ModuleAutoGeneratorTest {
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
			.setIncludeTables("test1_person")
			.setSaveDir("d:/test")
			.setGenerateService(true)
			.setServicePackage("com.jiangnan.product.service")
			.setServiceExtendClass("BaseService<{0}>")
			.setServiceExtendClassPackage("org.common.module.mybatis.service")
			.setServiceImplExtendClass("BaseServiceImpl<{0}>")
			.setServiceImplExtendClassPackage("org.common.module.mybatis.service.impl")
			//.setExcludeFields("id")
			;
		ModuleAutoGenerator generator = new ModuleAutoGenerator();
		generator.generate(config);
	}
}
