/**
 * Copyright (c) 2015-2015 yejunwu126@126.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package org.common.module.mybatis.plugin;

import java.util.HashMap;
import java.util.Map;

import org.common.module.mybatis.Constants;

/**
 * @description 封装分页查询参数
 * @author ywu@wuxicloud.com
 * 2015年5月14日 下午2:26:39
 */
public final class PageContext {
	
	private PageContext() {
		
	}

	private static ThreadLocal<Map<String, Integer>> PAGE = new ThreadLocal<Map<String,Integer>>(){
		protected java.util.Map<String,Integer> initialValue() {
			return new HashMap<String, Integer>(2);
		};
	};
	
	/**
	 * 设置当前页码
	 * @param currentPage
	 */
	public static void setCurrentPage(Integer currentPage) {
		PAGE.get().put(Constants.CURRENT_PAGE, currentPage);
	}
	/**
	 * 获取当前页码
	 * @return
	 */
	public static Integer getCurrentPage() {
		Integer currentPage = PAGE.get().get(Constants.CURRENT_PAGE);
		if (null == currentPage) {
			currentPage = 1;
		}
		return currentPage;
	}
	/**
	 * 设置每页显示记录数
	 * @param pageSize
	 */
	public static void setPageSize(Integer pageSize) {
		PAGE.get().put(Constants.PAGE_SIZE, pageSize);
	}
	/**
	 * 获取每页显示记录数
	 * @return
	 */
	public static Integer getPageSize() {
		return PAGE.get().get(Constants.PAGE_SIZE);
	}
	
	/**
	 * 清除分页数据
	 */
	public static void clear() {
		PAGE.remove();
	}
}
