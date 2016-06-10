/**
 * 
 */
package org.common.module.generator.code;

import java.io.BufferedWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.common.module.generator.Config;
import org.common.module.generator.db.Table;

/**
 * <p> 代码生成工具类
 * @author yejunwu123@gmail.com
 * @since 2016年6月10日 下午8:42:42
 */
public class CodeGenerateUtil {
	private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * 生成版权声明
	 * @param config
	 * @param bw
	 * @throws IOException
	 */
	public static void generateLicence(Config config, BufferedWriter bw) throws IOException {
		//版权声明
		if (null != config.getLicense() && config.getLicense().length() > 0) {
			bw.write("/**");
			bw.newLine();
			bw.write(" * " + config.getLicense());
			bw.newLine();
			bw.write(" */");
		}
	}
	/**
	 * 类注释
	 * @param table
	 * @param bw
	 * @throws IOException
	 */
	public static void generateClassNotes(Table table, BufferedWriter bw) throws IOException {
		if (null != table.getComments() && table.getComments().length() > 0) {
			bw.newLine();
			bw.write("/**");
			bw.newLine();
			bw.write(" * <p>" + table.getComments());
			bw.newLine();
			bw.write(" * @author auto generated");
			bw.newLine();
			bw.write(" * @since " + FORMAT.format(new Date()));
			bw.newLine();
			bw.write(" */");
		}
	}
	
	/**
	 * 继承的是否是泛型父接口
	 * @param extendsClass
	 * @return
	 */
	public static boolean isGeneric(String extendsClass) {
		if (null != extendsClass && extendsClass.trim().length() > 0) {
			if (extendsClass.contains("<")) {
				return true;
			}
		}
		return false;
	}
}
