/**
 * 
 */
package org.common.module.generator.extension.mybatis;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.MessageFormat;

import org.common.module.generator.Config;
import org.common.module.generator.code.CodeGenerateUtil;
import org.common.module.generator.code.CodeGenerator;
import org.common.module.generator.db.Table;

/**
 * <p> mapper类生成器
 * @author yejunwu123@gmail.com
 * @since 2016年6月10日 上午11:05:19
 */
public class DefaultMapperClassGenerator implements CodeGenerator {
	
	/**
	 * mapper类所在包,eg:com.jiangnan.mapper
	 */
	private String mapperPackage;
	/**
	 * mapper类后缀,如ProductMapper,默认为Mapper
	 */
	private String mapperClassSuffix = "Mapper";
	/**
	 * mapper继承的接口
	 */
	private String mapperExtendsClass;
	/**
	 * mapper继承接口所在包
	 */
	private String mapperExtendsClassPackage;
	
	public DefaultMapperClassGenerator(String mapperPackage) {
		this.mapperPackage = mapperPackage;
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
			File file = new File(beanDir, beanName + this.mapperClassSuffix + ".java");
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
			
			//版权声明
			CodeGenerateUtil.generateLicence(config, bw);
			
			//包名
			bw.newLine();
			bw.write("package " + this.mapperPackage + ";");
			bw.newLine();
			//继承接口所在包
			if (null != this.mapperExtendsClassPackage && !"".equals(this.mapperExtendsClassPackage.trim())) {
				bw.newLine();
				String extendClass = this.mapperExtendsClass.substring(0, this.mapperExtendsClass.indexOf("<")).trim();
				bw.write("import " + this.mapperExtendsClassPackage.trim() + "." + extendClass + ";");
			}
			//泛型类所在包
			boolean generic = CodeGenerateUtil.isGeneric(this.mapperExtendsClass);
			if (generic) {
				bw.newLine();
				bw.write("import " + config.getBeanPackage() + "." + table.getEntityName() + ";");
			}
			
			//类注释
			CodeGenerateUtil.generateClassNotes(table, bw);
			
			//类声明
			bw.newLine();
			if (null != this.mapperExtendsClass && this.mapperExtendsClass.trim().length() > 0) {
				bw.write("public interface " + getMapperClassSimpleName(table) + " extends " + (generic ? MessageFormat.format(this.mapperExtendsClass.trim(), table.getEntityName()) : this.mapperExtendsClass.trim()) + " {");
			} else {
				bw.write("public interface " + getMapperClassSimpleName(table) + " {");
			}
			bw.newLine();
			bw.newLine();
			bw.write("}");
			bw.newLine();
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
	 * 获取mapper类简单名称
	 * @param table
	 * @return
	 */
	public String getMapperClassSimpleName(Table table) {
		return table.getEntityName() + this.mapperClassSuffix;
	}
	/**
	 * 获取mapper类全限定名
	 * @param table
	 * @return
	 */
	public String getMapperClassQualifiedName(Table table) {
		return this.mapperPackage + "." + table.getEntityName() + this.mapperClassSuffix;
	}
	/**
	 * 获取mapper类所在包,eg:com.jiangnan.mapper
	 * @return
	 */
	public String getMapperPackage() {
		return mapperPackage;
	}
	/**
	 * 设置mapper类所在包,eg:com.jiangnan.mapper
	 * @param mapperPackage
	 */
	public void setMapperPackage(String mapperPackage) {
		this.mapperPackage = mapperPackage;
	}
	/**
	 * 获取mapper类后缀,如ProductMapper,默认为Mapper
	 * @return
	 */
	public String getMapperClassSuffix() {
		return mapperClassSuffix;
	}
	/**
	 * 设置mapper类后缀,如ProductMapper,默认为Mapper
	 * @param mapperClassSuffix
	 */
	public void setMapperClassSuffix(String mapperClassSuffix) {
		if (null != mapperClassSuffix && mapperClassSuffix.trim().length() > 0) {
			this.mapperClassSuffix = mapperClassSuffix.trim();
		}
	}
	/**
	 * 获取mapper继承的接口
	 * @return
	 */
	public String getMapperExtendsClass() {
		return mapperExtendsClass;
	}
	/**
	 * 设置mapper继承的接口
	 * @param mapperExtendsClass
	 */
	public void setMapperExtendsClass(String mapperExtendsClass) {
		this.mapperExtendsClass = mapperExtendsClass;
	}
	/**
	 * 获取mapper继承接口所在包
	 * @return
	 */
	public String getMapperExtendsClassPackage() {
		return mapperExtendsClassPackage;
	}
	/**
	 * 设置mapper继承接口所在包
	 * @param mapperExtendsClassPackage
	 */
	public void setMapperExtendsClassPackage(String mapperExtendsClassPackage) {
		this.mapperExtendsClassPackage = mapperExtendsClassPackage;
	}
	
}
