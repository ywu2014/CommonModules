/**
 * 
 */
package org.common.module.generator.code;

import org.common.module.generator.Config;
import org.common.module.generator.db.Table;

/**
 * <p> 代码生成
 * @author yejunwu123@gmail.com
 * @since 2016年6月8日 下午2:57:19
 */
public interface CodeGenerator {
	/**
	 * 代码生成
	 * @param table 表信息
	 * @param config 配置参数
	 */
	public void generateBean(Table table, Config config);
}
