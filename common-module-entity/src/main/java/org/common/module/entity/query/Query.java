/**
 * 
 */
package org.common.module.entity.query;

import java.util.ArrayList;
import java.util.List;

/**
 * <p> 查询参数对象
 * @author yejunwu123@gmail.com
 * @since 2016年6月7日 下午2:34:20
 */
public class Query<T> {
	/**
	 * 当前页
	 */
	private Integer currentPage = 1;
	/**
	 * 每页显示记录数
	 */
	private Integer pageSize = 10;
	/**
	 * 查询字段
	 */
	private List<String> fields;
	/**
	 * 参数对象,可以是实体或Map
	 */
	private T params;
	
	/**
	 * 获取查询字段
	 * @return
	 */
	public List<String> getFields() {
		return fields;
	}
	/**
	 * 设置查询字段
	 * @param fields
	 */
	public Query<T> setFields(List<String> fields) {
		this.fields = fields;
		return this;
	}
	/**
	 * 添加查询字段
	 * @param field
	 * @return
	 */
	public Query<T> addField(String field) {
		if (null != field && !(field.trim().isEmpty())) {
			if (null == this.fields) {
				this.fields = new ArrayList<String>(10);
			}
			this.fields.add(field.trim());
		}
		return this;
	}
	/**
	 * 设置查询字段,逗号(,)分割
	 * @param fields
	 * @return
	 */
	public Query<T> setFields(String fields) {
		if (null != fields && !(fields.trim().isEmpty())) {
			String[] fieldArray = fields.split(",");
			if (fieldArray.length > 0) {
				if (null == this.fields) {
					this.fields = new ArrayList<String>(10);
				}
				for (String field : fieldArray) {
					String s = field.trim();
					if (!s.isEmpty()) {
						this.fields.add(s);
					}
				}
			}
		}
		return this;
	}
	/**
	 * 获取参数
	 * @return
	 */
	public T getParams() {
		return params;
	}
	/**
	 * 设置参数
	 * @param params
	 */
	public Query<T> setParams(T params) {
		this.params = params;
		return this;
	}
	/**
	 * 获取当前页
	 * @return
	 */
	public Integer getCurrentPage() {
		return currentPage;
	}
	/**
	 * 设置当前页
	 * @param currentPage
	 */
	public Query<T> setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
		return this;
	}
	/**
	 * 获取每页显示记录数
	 * @return
	 */
	public Integer getPageSize() {
		return pageSize;
	}
	/**
	 * 设置每页显示记录数
	 * @param pageSize
	 */
	public Query<T> setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
		return this;
	}
	
}
