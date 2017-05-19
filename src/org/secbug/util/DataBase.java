package org.secbug.util;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

public class DataBase extends QueryRunner {

	private static String driverClassName = "com.mysql.jdbc.Driver";
	private static String url = "jdbc:mysql://127.0.0.1:3306/probeace?useUnicode=true&characterEncoding=UTF-8";
	private static String username = "root";
	private static String password = "root";

	/***************** DataSource *********************/

	private static DataSource ds = DataBase.initDS();

	public DataBase() {
		super(DataBase.ds);
	}

	private static DataSource initDS() {
		if (DataBase.ds != null) {
			return DataBase.ds;
		}
		BasicDataSource dbs = new BasicDataSource();
		dbs.setDriverClassName(driverClassName);
		dbs.setUsername(username);
		dbs.setPassword(password);
		dbs.setUrl(url);
		dbs.setInitialSize(80); // 初始的连接数；
		dbs.setMaxTotal(70); // 最大活动连接数
		dbs.setMaxIdle(70); // 最大连接数
		dbs.setMinIdle(0); // 最小连接数
		dbs.setMaxWaitMillis(200); // 获得连接的最大等待毫秒数
		return dbs;
	}

	/***************** Connection *********************/
	public static Connection initConn() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static void endDS() {
		try {
			Connection conn = ds.getConnection();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ds = null;
		// System.out.println("Run The End!!!");
		System.exit(-1);
	}

	/**
	 * 按照ID查询
	 * 
	 * @param 表名
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public <T> T findByID(Class<T> clazz, int id) {

		String tableName = clazz.getSimpleName();

		String sql = "select * from " + tableName + " where id = ?";
		T t = null;
		try {
			t = super.query(sql, new BeanHandler<T>(clazz), id);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return t;
	}

	public <T> int deleteByID(Class<T> clazz, int id) {
		String tableName = clazz.getSimpleName();
		String sql = "delete  from " + tableName + " where id = ?";

		try {
			return this.update(sql, id);
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return -1;
	}

	public <T> List<T> findByList(Class<T> clazz) {

		return findByList(clazz, null);
	}

	public <T> List<T> findByList(Class<T> clazz, String where) {

		List<T> arr = null;
		String tableName = clazz.getSimpleName();
		String sql = "";
		if (where == null || "".equals(where)) {
			sql = "select * from " + tableName;
		} else {
			sql = "select * from " + tableName + " where " + where;
		}
		try {
			arr = this.query(sql, new BeanListHandler<T>(clazz));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return arr;
	}

	public <T> int insert(T t) {

		String sql = "";
		try {
			sql = objToSQL(t);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			this.insert(sql, new ArrayHandler());
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			Object[] o = this.query("select @@IDENTITY", new ArrayHandler());
			return Integer.parseInt(o[0].toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return -1;
	}

	public <T> int update(Class<T> clazz, String set) {

		String tablename = clazz.getSimpleName();
		String sql = "";
		if (set == null || "".equals(set))
			return -1;
		else
			sql = "update " + tablename + " set " + set;

		try {
			int num = this.update(sql);
			return num;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	private String objToSQL(Object o) throws Exception {
		Class<?> clazz = o.getClass();
		Field[] fs = clazz.getDeclaredFields();
		StringBuffer sb = new StringBuffer("INSERT INTO " + clazz.getSimpleName() + " (");
		StringBuffer par = new StringBuffer();
		for (int i = 0; i < fs.length; i++) {
			Field f = fs[i];
			f.setAccessible(true); // 设置些属性是可以访问的
			// Object val = f.get(o);//得到此属性的值
			if (f.getName().toLowerCase().equals("id")) {
				continue;
			}

			sb.append(f.getName());
			par.append("\"" + f.get(o) + "\"");
			if (i != fs.length - 1) {
				sb.append(",");
				par.append(",");
			}
			// System.out.println("name:"+f.getName()+"\t value = "+val);
		}
		sb.append(") VALUES(");
		sb.append(par.toString());
		sb.append(");");
		// System.out.println(sb);
		return sb.toString();
	}

}
