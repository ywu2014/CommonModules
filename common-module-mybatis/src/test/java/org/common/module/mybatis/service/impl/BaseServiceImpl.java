/**
 * 
 */
package org.common.module.mybatis.service.impl;

import java.io.Serializable;
import java.util.List;

import org.common.module.entity.BaseEntity;
import org.common.module.entity.query.Page;
import org.common.module.entity.query.Query;
import org.common.module.mybatis.exception.OptimisticLockException;
import org.common.module.mybatis.mapper.BaseMapper;
import org.common.module.mybatis.plugin.PageHelper;
import org.common.module.mybatis.service.BaseService;

/**
 * <p> 基础业务层实现
 * @author yejunwu123@gmail.com
 * @since 2016年6月7日 下午3:46:05
 */
public abstract class BaseServiceImpl<T extends BaseEntity> implements BaseService<T> {

	public T get(Serializable id) {
		return getMapper().get(id);
	}

	public int save(T entity) {
		entity.setDeleted(0);
		return getMapper().save(entity);
	}

	public int update(T entity) {
		return getMapper().update(entity);
	}

	public int updateWithOptimisticLock(T entity) {
		int count = getMapper().update(entity);
		if (count == 0) {
			throw new OptimisticLockException();
		}
		return count;
	}

	public int remove(T entity) {
		//设置删除标记
		entity.setDeleted(1);
		return update(entity);
	}

	public int delete(Serializable id) {
		return getMapper().remove(id);
	}

	public boolean exist(Serializable id) {
		return false;
	}

	public Page find(Query<?> query) {
		boolean success = PageHelper.startPage();
		if (!success) {
			PageHelper.startPage(query.getCurrentPage(), query.getPageSize());
		}
		return PageHelper.getPageResult(list(query));
	}

	public <E> List<E> list(Query<?> query) {
		return getMapper().list(query);
	}

	/**
	 * 获取实际的mapper
	 * @return
	 */
	protected abstract BaseMapper<T> getMapper();
}
