/**
 * Copyright (c) 2015-2015 yejunwu126@126.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package org.common.module.entity.query;

import java.util.List;

/**
 * @description 分页查询结果
 * @author ywu@wuxicloud.com
 * 2015年5月13日 下午5:35:25
 */
public class Page {
	/**
	 * @param currentPage 当前页
	 * @param pageSize
	 */
	public Page(int currentPage, int pageSize) {
		setCurrentPage(currentPage);
		setPageSize(pageSize);
	}
	
	private static int DEFAULT_PAGE_SIZE = 10;	//默认每页显示10条记录
	/**
	 * 查询结果
	 */
	private List<?> data;
	/**
	 * 总记录数
	 */
	private long totalRecords;
	/**
	 * 每页显示记录数
	 */
	private int pageSize = DEFAULT_PAGE_SIZE;
	/**
	 * 总页数
	 */
	private int totalPage;
	/**
	 * 当前页
	 */
	private int currentPage = 1;
	
	/**
	 * 获取记录开始索引
	 * @return
	 */
	public int getFirstIndex() {
		return (this.currentPage - 1) * this.pageSize;
	}
	/**
	 * 获取分页数据
	 * @return
	 */
	public List<?> getData() {
		return data;
	}
	/**
	 * 设置分页数据
	 * @param data
	 */
	public void setData(List<?> data) {
		this.data = data;
	}
	/**
	 * 获取总记录数
	 * @return
	 */
	public long getTotalRecords() {
		return totalRecords;
	}
	/**
	 * 设置总记录数
	 * @param totalRecords
	 */
	public void setTotalRecords(long totalRecords) {
		this.totalRecords = totalRecords;
		int totalPage = (int)((totalRecords % this.pageSize == 0) ? (totalRecords / this.pageSize) : (totalRecords / this.pageSize + 1));
		setTotalPage(totalPage);
	}
	/**
	 * 获取每页记录数
	 * @return
	 */
	public int getPageSize() {
		return pageSize;
	}
	/**
	 * 设置每页记录数
	 * @param pageSize
	 */
	public void setPageSize(int pageSize) {
		if (pageSize > 0) {
			this.pageSize = pageSize;
		}
	}
	/**
	 * 获取总页数
	 * @return
	 */
	public int getTotalPage() {
		return totalPage;
	}
	/**
	 * 设置总页数
	 * @param totalPage
	 */
	private void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	/**
	 * 获取当前页码
	 * @return
	 */
	public int getCurrentPage() {
		return currentPage;
	}
	/**
	 * 设置当前页码
	 * @param currentPage
	 */
	public void setCurrentPage(int currentPage) {
		if (currentPage > 0) {
			this.currentPage = currentPage;
		}
	}
	
}
