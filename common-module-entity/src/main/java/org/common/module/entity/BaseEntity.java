/**
 * 
 */
package org.common.module.entity;

import java.io.Serializable;

/**
 * <p> 基础实体
 * @author yejunwu123@gmail.com
 * @since 2016年6月7日 下午1:57:54
 */
public class BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7592222312700624885L;
	
	/**
	 * 版本号,有些持久化框架用于乐观锁定
	 */
	protected int version;
	/**
	 * 是否删除标记
	 */
	protected Integer deleted = 0;
	
	/**
	 * 版本号,有些持久化框架用于乐观锁定
	 */
	public int getVersion() {
		return version;
	}
	
	/**
	 * 是否删除标记,0:删除,1:未删除
	 */
	public Integer getDeleted() {
		return deleted;
	}
	/**
	 * 是否删除标记,0:删除,1:未删除
	 */
	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}
	
}
