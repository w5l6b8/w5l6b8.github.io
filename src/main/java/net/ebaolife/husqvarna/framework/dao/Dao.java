package net.ebaolife.husqvarna.framework.dao;

import net.ebaolife.husqvarna.framework.bean.PageInfo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.orm.hibernate5.HibernateTemplate;

import java.io.Serializable;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

public interface Dao {

	public SessionFactory getSessionFactory();

	public Session getCurrentSession();

	public Connection getConnection();

	public Session openSession();

	public Dao openDao();

	public HibernateTemplate getHibernateTemplate();

	public void flush();

	public void clear();

	public void close();

	public Transaction beginTransaction();

	public void commit();

	public void rollback();

	public <T> void refresh(T t);

	public <T> void evict(T t);

	public <T> Serializable save(T t);

	public <T> List<Serializable> bulkSave(List<T> ts);

	public int bulkUpdate(final String queryString, final Object... values);

	public <T> T update(T t);

	public <T> T update(Class<T> clazz, Map<String, Object> data);

	public <T> T update(T t, String... includeFields);

	public <T> T update(T t, boolean ignoredNull, String... includeFields);

	public <T> void delete(T c);

	public int executeUpdate(String hql, Object... params);

	public <T> T saveOrUpdate(T t);

	public <T> T saveOrUpdate(T t, String... includeFields);

	public <T> T saveOrUpdate(T t, boolean ignoredNull, String... includeFields);

	public <T, ID extends Serializable> T findById(Class<T> c, ID id);

	public <ID extends Serializable> Object findById(String entityName, ID id);

	public <T> List<T> findAll(Class<T> t);

	public <T> List<T> findByProperty(Class<T> t, Object... obj);

	public <T> T findByPropertyFirst(Class<T> t, Object... obj);

	public <T> List<T> findByProperty(Class<T> t, T obj);

	public <T> List<T> findByProperty(Class<T> t, Map<String, Object> map);

	public <T> List<T> executeQuery(String hql, Object... params);

	public <T> PageInfo<T> executeQuery(String hql, int start, int limit, Object... params);

	public int selectCount(String hql, Object... params);

	public int selectPageCount(String hql, Object... params);

	public int executeSQLUpdate(String sql, Object... params);

	public List<Map<String, Object>> executeSQLQuery(String sql, Object... params);

	public List<Map<String, Object>> executeSQLQuery(String sql, String[] fields, Object... params);

	public Map<String, Object> executeSQLQueryFirst(String sql, Object... params);

	public Map<String, Object> executeSQLQueryFirst(String sql, String[] fields, Object... params);

	public <T extends Serializable> List<T> executeSQLQuery(String sql, Class<T> c, Object... params);

	public <T extends Serializable> T executeSQLQueryFirst(String sql, Class<T> c, Object... params);

	public PageInfo<Map<String, Object>> executeSQLQueryPage(String sql, int start, int limit, Object... params);

	public PageInfo<Map<String, Object>> executeSQLQueryPage(String sql, int start, int limit, int total,
                                                             Object... params);

	public PageInfo<Map<String, Object>> executeSQLQueryPage(String sql, String[] fields, int start, int limit,
                                                             Object... params);

	public PageInfo<Map<String, Object>> executeSQLQueryPage(String sql, String[] fields, int start, int limit,
                                                             int total, Object... params);

	public <T extends Serializable> PageInfo<T> executeSQLQueryPage(String sql, Class<T> c, int start, int limit,
                                                                    Object... params);

	public <T extends Serializable> PageInfo<T> executeSQLQueryPage(String sql, Class<T> c, int start, int limit,
                                                                    int total, Object... params);

	public int selectSQLCount(String sql, Object... params);

	public int selectPageSQLCount(String sql, Object... params);

	public <T> Serializable getIdentifierPropertyName(T t);

	public <T> Serializable getIdentifier(T t);

	public <T> ClassMetadata getClassMetadata(T t);
}
