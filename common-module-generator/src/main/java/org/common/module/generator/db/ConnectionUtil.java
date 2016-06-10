/**
 * 
 */
package org.common.module.generator.db;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * <p> 数据库连接工具类
 * @author yejunwu123@gmail.com
 * @since 2016年6月8日 下午3:26:52
 */
public class ConnectionUtil {
	private static final ThreadLocal<Connection> CONNECTION = new ThreadLocal<Connection>();
	
	public static void setConnection(Connection conn) {
		CONNECTION.set(conn);
	}
	/**
	 * 获取连接
	 * @return
	 */
	public static Connection getConnection() {
		return CONNECTION.get();
	}
	/**
	 * 关闭连接
	 */
	public static void closeConnection() {
		try {
			Connection conn = CONNECTION.get();
			if (null != conn) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
		} finally {
			CONNECTION.remove();
		}
	}
}
