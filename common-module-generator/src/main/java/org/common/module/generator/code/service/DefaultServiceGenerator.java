/**
 * 
 */
package org.common.module.generator.code.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;

import org.common.module.generator.Config;
import org.common.module.generator.code.CodeGenerateUtil;
import org.common.module.generator.code.CodeGenerator;
import org.common.module.generator.db.Table;

/**
 * <p> 默认service生成器
 * @author yejunwu123@gmail.com
 * @since 2016年6月8日 下午3:03:10
 */
public class DefaultServiceGenerator implements CodeGenerator {
	
	private static final String DEFAULT_SERVICE_SUFFIX = "Service";
	private static final String DEFAULT_SERVICE_IMPL_SUFFIX = "Impl";

	@Override
	public void generateBean(Table table, Config config) {
		String serviceSuffix = DEFAULT_SERVICE_SUFFIX;
		if (null != config.getServiceSuffix() && config.getServiceSuffix().trim().length() > 0) {
			serviceSuffix = config.getServiceSuffix().trim();
		}
		generateService(table, serviceSuffix, config);
		String serviceImplSuffix = DEFAULT_SERVICE_IMPL_SUFFIX;
		if (null != config.getServiceImplSuffix() && config.getServiceImplSuffix().trim().length() > 0) {
			serviceImplSuffix = config.getServiceImplSuffix().trim();
		}
		generateServiceImpl(table, serviceSuffix, serviceImplSuffix, config);
	}
	
	/**
	 * service实现类
	 * @param table
	 * @param serviceSuffix
	 * @param serviceImplSuffix
	 * @param config
	 */
	private void generateServiceImpl(Table table, String serviceSuffix, String serviceImplSuffix, Config config) {
		BufferedWriter bw = null;
		try {
			//获取bean名称
			String beanName = table.getEntityName();
			
			//生成bean存放目录
			File beanDir = new File(config.getSaveDir() + File.separator + "service" + File.separator + serviceImplSuffix.toLowerCase());
			if (!beanDir.exists()) {
				beanDir.mkdirs();
			}
			File file = new File(beanDir, beanName + serviceSuffix + serviceImplSuffix + ".java");
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
			
			//版权声明
			CodeGenerateUtil.generateLicence(config, bw);
			
			//包名
			bw.newLine();
			bw.write("package " + config.getServicePackage() + "." + serviceImplSuffix.toLowerCase() + ";");
			bw.newLine();
			//继承类所在包
			if (null != config.getServiceImplExtendClassPackage() && !"".equals(config.getServiceImplExtendClassPackage().trim())) {
				bw.newLine();
				String extendClass = config.getServiceImplExtendClass().substring(0, config.getServiceImplExtendClass().indexOf("<")).trim();
				bw.write("import " + config.getServiceImplExtendClassPackage().trim() + "." + extendClass + ";");
			}
			//泛型类所在包
			boolean generic = CodeGenerateUtil.isGeneric(config.getServiceImplExtendClass());
			if (generic) {
				bw.newLine();
				bw.write("import " + config.getBeanPackage() + "." + table.getEntityName() + ";");
			}
			
			//类注释
			CodeGenerateUtil.generateClassNotes(table, bw);
			
			//类声明
			bw.newLine();
			if (null != config.getServiceImplExtendClass() && config.getServiceImplExtendClass().trim().length() > 0) {
				bw.write("public class " + table.getEntityName() + serviceSuffix + serviceImplSuffix + " extends " + (generic ? MessageFormat.format(config.getServiceImplExtendClass().trim(), table.getEntityName()) : config.getServiceImplExtendClass().trim()) + " {");
			} else {
				bw.write("public class " + table.getEntityName() + serviceSuffix + serviceImplSuffix + " {");
			}
			
			bw.newLine();
			bw.newLine();
			bw.write("}");
			bw.newLine();
			bw.flush();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IOException e) {
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
	 * service接口
	 */
	private void generateService(Table table, String serviceSuffix, Config config) {
		BufferedWriter bw = null;
		try {
			//获取bean名称
			String beanName = table.getEntityName();
			
			//生成bean存放目录
			File beanDir = new File(config.getSaveDir() + File.separator + "service");
			if (!beanDir.exists()) {
				beanDir.mkdirs();
			}
			File file = new File(beanDir, beanName + serviceSuffix + ".java");
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
			
			//版权声明
			CodeGenerateUtil.generateLicence(config, bw);
			
			//包名
			bw.newLine();
			bw.write("package " + config.getServicePackage() + ";");
			bw.newLine();
			//继承接口所在包
			if (null != config.getServiceExtendClassPackage() && !"".equals(config.getServiceExtendClassPackage().trim())) {
				bw.newLine();
				String extendClass = config.getServiceExtendClass().substring(0, config.getServiceExtendClass().indexOf("<")).trim();
				bw.write("import " + config.getServiceExtendClassPackage().trim() + "." + extendClass + ";");
			}
			//泛型类所在包
			boolean generic = CodeGenerateUtil.isGeneric(config.getServiceExtendClass());
			if (generic) {
				bw.newLine();
				bw.write("import " + config.getBeanPackage() + "." + table.getEntityName() + ";");
			}
			
			//类注释
			CodeGenerateUtil.generateClassNotes(table, bw);
			
			//类声明
			bw.newLine();
			if (null != config.getServiceExtendClass() && config.getServiceExtendClass().trim().length() > 0) {
				bw.write("public interface " + table.getEntityName() + serviceSuffix + " extends " + (generic ? MessageFormat.format(config.getServiceExtendClass().trim(), table.getEntityName()) : config.getServiceExtendClass().trim()) + " {");
			} else {
				bw.write("public interface " + table.getEntityName() + serviceSuffix + " {");
			}
			
			bw.newLine();
			bw.newLine();
			bw.write("}");
			bw.newLine();
			bw.flush();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IOException e) {
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
}
