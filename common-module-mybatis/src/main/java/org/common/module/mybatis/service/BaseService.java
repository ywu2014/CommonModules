/**
 * 
 */
package org.common.module.mybatis.service;

import java.io.Serializable;
import java.util.List;

import org.common.module.entity.query.Page;
import org.common.module.entity.query.Query;

/**
 * <p> 基础业务层接口
 * @author yejunwu123@gmail.com
 * @since 2016年6月7日 下午3:22:57
 */
public interface BaseService<T> {
	/**
	 * 根据标识属性获取实体
	 * @param id
	 * @return
	 */
	T get(Serializable id);
	/**
	 * 将实体持久化到数据库
	 * @param entity
	 * @return
	 */
	int save(T entity);
	/**
	 * 更新实体
	 * @param entity
	 * @return
	 */
	int update(T entity);
	/**
	 * @see OptimisticLockException
	 * 使用乐观锁更新实体,如果更新失败,抛出OptimisticLockException异常
	 * @param entity
	 * @return
	 */
	int updateWithOptimisticLock(T entity);
	/**
	 * 删除实体(逻辑删除,将表中DELETED字段置为1)
	 * @param entity
	 * @return
	 */
	int remove(T entity);
	/**
	 * 删除实体(物理删除)
	 * @param id
	 * @return
	 */
	int delete(Serializable id);
	/**
	 * 实体是否存在
	 * @param id
	 * @return
	 */
	boolean exist(Serializable id);
	/**
	 * 分页查询
	 * 默认从PageContext中获取当前页和每页显示记录数这两个参数
	 * 如果从PageContext中未获取到,则从query中获取
	 * @param query 查询参数对象
	 * @return
	 */
	Page find(Query<?> query);
	/**
	 * 列表查询(无分页)
	 * @param query 查询参数对象
	 * @return
	 */
	<E> List<E> list(Query<?> query);
}
