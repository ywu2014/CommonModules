/**
 * 
 */
package org.common.module.mybatis.mapper;

import java.io.Serializable;
import java.util.List;

import org.common.module.entity.query.Query;

/**
 * <p> 基础mapper
 * @author yejunwu123@gmail.com
 * @since 2016年6月7日 下午3:10:23
 */
public interface BaseMapper<T> {
	/**
	 * 将实体持久化到数据库
	 * @param entity
	 * @return
	 */
	int save(T entity);
	/**
	 * 删除实体
	 * @param id
	 */
	int remove(Serializable id);
	/**
	 * 实体是否存在
	 * @param id
	 * @return
	 */
	boolean exist(Serializable id);
	/**
	 * 根据标识属性获取实体
	 * @param id
	 * @return
	 */
	T get(Serializable id);
	/**
	 * 更新实体
	 * @param entity
	 * @return
	 */
	int update(T entity);
	/**
	 * 获取列表
	 * @param params
	 * @return
	 */
	<E> List<E> list(Query<?> query);
}
