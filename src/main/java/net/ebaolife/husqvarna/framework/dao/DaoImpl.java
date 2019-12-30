package net.ebaolife.husqvarna.framework.dao;

import net.ebaolife.husqvarna.framework.bean.PageInfo;
import net.ebaolife.husqvarna.framework.exception.DaoException;
import net.ebaolife.husqvarna.framework.utils.BeanUtils;
import net.ebaolife.husqvarna.framework.utils.CommonUtils;
import net.ebaolife.husqvarna.framework.utils.DataObjectUtils;
import net.ebaolife.husqvarna.framework.utils.ProjectUtils;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.internal.SessionImpl;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.metamodel.internal.EntityTypeImpl;
import org.hibernate.persister.entity.EntityPersister;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.persistence.Table;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@SuppressWarnings({ "unchecked", "deprecation", "rawtypes" })

public class DaoImpl extends HibernateDaoSupport implements Dao {

	@Resource
	private SessionFactory sessionFactory;


	private Session session;



	/*public static MySQL5SqlFunction sf;


	static {
		sf = JdbcAdapterFactory.getJdbcAdapter(ProjectUtils.getInitParameter("jdbc.dbType"));
	}*/



	public DaoImpl() {
	}

	public DaoImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
		super.setSessionFactory(sessionFactory);
	}

	@PostConstruct
	public void InjectedSessionFactory() {
		super.setSessionFactory(sessionFactory);
	}

	public Session getCurrentSession() {
		return session == null ? sessionFactory.getCurrentSession() : session;
	}

	public Connection getConnection() {
		SessionImpl session = (SessionImpl) getCurrentSession();
		return session.getJdbcCoordinator().getLogicalConnection().getPhysicalConnection();
	}

	public Session openSession() {
		return sessionFactory.openSession();
	}

	public Dao openDao() {
		DaoImpl dao = new DaoImpl();
		dao.setSessionFactory(sessionFactory);
		dao.setSession(sessionFactory.openSession());
		return dao;
	}

	@Override
	public void flush() {
		getCurrentSession().flush();
	}

	@Override
	public void clear() {
		getCurrentSession().clear();
	}

	@Override
	public void close() {
		getCurrentSession().close();
	}

	@Override
	public Transaction beginTransaction() {
		Transaction transaction = getCurrentSession().getTransaction();
		if (transaction == null || !transaction.isActive()) {
			transaction = getCurrentSession().beginTransaction();
		}
		return transaction;
	}

	@Override
	public void commit() {
		beginTransaction().commit();
	}

	@Override
	public void rollback() {
		beginTransaction().rollback();
	}

	@Override
	public <T> void refresh(T t) {
		getHibernateTemplate().refresh(t);
	}

	@Override
	public <T> void evict(T t) {
		getHibernateTemplate().evict(t);
	}

	@Override
	public <T> Serializable save(T t) {

		Serializable id = getHibernateTemplate().save(t);
		flush();
		return id;
	}

	@Override
	public <T> List<Serializable> bulkSave(List<T> ts) {
		List<Serializable> list = new ArrayList<Serializable>();
		for (T t : ts) {
			list.add(save(t));
		}
		flush();
		return list;
	}

	public int bulkUpdate(final String queryString, final Object... values) {
		int count = getHibernateTemplate().bulkUpdate(queryString, values);
		flush();
		return count;
	}

	@Override
	public <T> T update(T t) {
		return update(t, false);
	}

	@Override
	public <T> T update(T t, String... includeFields) {
		return update(t, true, includeFields);
	}

	@Override
	public <T> T update(Class<T> clazz, Map<String, Object> map) {
		Serializable keyName = getIdentifierPropertyName(clazz);
		Serializable KeyValue = (Serializable) map.get(keyName);
		T object = (T) findById(clazz, KeyValue);
		try {
			for (String key : map.keySet()) {
				String[] keys = key.split("[.]");
				Object value = map.get(key);
				if (keys.length == 1) {
					Field field = clazz.getDeclaredField(key);
					field.setAccessible(true);
					field.set(object, value);
				} else if (keys.length == 2) {
					String entity = keys[0];
					String name = keys[1];
					Field field = clazz.getDeclaredField(entity);
					field.setAccessible(true);
					EntityTypeImpl e = ProjectUtils.getEntityMap(this).get(entity.toLowerCase());
					Serializable keyname = getIdentifierPropertyName(e.getJavaType());
					if (keyname.equals(name)) {
						if (CommonUtils.isEmpty(value)) {
							field.set(object, null);
						} else {
							Object obj = findById(e.getJavaType(), value.toString());
							field.set(object, obj);
						}
					} else {
						Field field1 = e.getClass().getDeclaredField(name);
						field1.setAccessible(true);
						field1.set(e, value);
					}
				}
			}
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			throw new DaoException(e);
		}
		flush();
		return object;
	}

	@Override
	public <T> T update(T t, boolean ignoredNull, String... includeFields) {

		if (ignoredNull) {
			try {
				Serializable id = getIdentifier(t);
				T object = (T) findById(t.getClass(), id);
				BeanUtils.copyProperties(object, t, includeFields);
				t = object;
			} catch (Exception e) {
				throw new DaoException(e);
			}
		} else {
			getHibernateTemplate().update(t);
		}
		flush();
		return t;
	}

	@Override
	public <T> void delete(T t) {
		Serializable id = getIdentifier(t);
		getHibernateTemplate().delete(findById(t.getClass(), id));
		flush();
	}

	@Override
	public int executeUpdate(String hql, Object... params) {
		int count = getQuery(hql, params, null).executeUpdate();
		flush();
		return count;
	}

	@Override
	public <T> T saveOrUpdate(T t) {
		return saveOrUpdate(t, true);
	}

	@Override
	public <T> T saveOrUpdate(T t, String... includeFields) {
		return saveOrUpdate(t, true, includeFields);
	}

	@Override
	public <T> T saveOrUpdate(T t, boolean ignoredNull, String... includeFields) {
		Serializable id = getIdentifier(t);
		if (CommonUtils.isEmpty(id)) {
			save(t);
		} else {
			t = update(t, ignoredNull, includeFields);
		}
		flush();
		return t;
	}

	@Override
	public <T, ID extends Serializable> T findById(Class<T> c, ID id) {
		return getHibernateTemplate().get(c, id);
	}

	@Override
	public <ID extends Serializable> Object findById(String entityName, ID id) {
		return getHibernateTemplate().get(entityName, id);
	}

	@Override
	public <T> List<T> findAll(Class<T> c) {
		return getHibernateTemplate().loadAll(c);
	}

	@Override
	public <T> T findByPropertyFirst(Class<T> t, Object... obj) {
		List<T> list = findByProperty(t, obj);
		return list.size() == 0 ? null : list.get(0);
	}

	@Override
	public <T> List<T> findByProperty(Class<T> t, Object... obj) {
		if (obj != null && obj.length % 2 != 0)
			throw new DaoException("findByPropertyFirst方法中参数错误，obj必须是键值对存在，所以参数为双数。");
		StringBuffer hql = new StringBuffer("from " + t.getName() + " ");
		if (!CommonUtils.isEmpty(obj)) {
			List<Object> params = new ArrayList<Object>();
			for (int i = 0; i < obj.length; i += 2) {
				hql.append(i == 0 ? " where " : " and ");
				String key = String.valueOf(obj[i]);
				Object value = obj[i + 1];
				if (value == null) {
					hql.append(key + " is null ");
				} else {
					hql.append(key + " = ? ");
					params.add(obj[i + 1]);
				}
			}

			return executeQuery(hql.toString(), params.toArray());
		}
		return executeQuery(hql.toString());
	}

	@Override
	public <T> List<T> findByProperty(Class<T> t, Map<String, Object> map) {
		StringBuffer hql = new StringBuffer("from " + t.getName() + " ");
		if (map.size() > 0)
			hql.append(" where ");
		List<Object> params = new ArrayList<Object>();
		for (String key : map.keySet()) {
			Object value = map.get(key);
			if (params.size() == 0)
				hql.append(" where ");
			if (value == null) {
				hql.append(key + " is null ");
			} else {
				hql.append(key + " = ? ");
				params.add(value);
			}
		}
		return executeQuery(hql.toString(), params);
	}

	@Override
	public <T> List<T> findByProperty(Class<T> t, T obj) {
		StringBuffer hql = new StringBuffer("from " + t.getName() + " ");
		Field[] fields = obj.getClass().getDeclaredFields();
		List<Object> params = new ArrayList<Object>();
		for (int i = 0; i < fields.length; i++) {
			try {
				Field field = fields[i];
				field.setAccessible(true);
				Object value = fields[i].get(obj);
				if (!CommonUtils.isEmpty(value))
					continue;
				if (params.size() == 0)
					hql.append(" where ");
				hql.append(field.getName() + " = ?");
				params.add(value);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return executeQuery(hql.toString());
	}

	@Override
	public <T> List<T> executeQuery(String hql, Object... params) {
		return getQuery(hql, params, null).getResultList();
	}

	@Override
	public <T> PageInfo<T> executeQuery(String hql, int start, int limit, Object... params) {
		return executeQuery(hql, params, null, start, limit);
	}

	@Override
	public int executeSQLUpdate(String sql, Object... params) {
		int count = getSQLQuery(sql, Map.class, params, null).executeUpdate();
		flush();
		return count;
	}

	@Override
	public List<Map<String, Object>> executeSQLQuery(String sql, Object... params) {
		NativeQuery query = getSQLQuery(sql, Map.class, params, null);
		return query.getResultList();
	}

	@Override
	public List<Map<String, Object>> executeSQLQuery(String sql, String[] fields, Object... params) {
		NativeQuery query = getSQLQuery(sql, null, params, null);
		return arrayToMap(fields, query.getResultList());
	}

	@Override
	public Map<String, Object> executeSQLQueryFirst(String sql, Object... params) {
		List<Map<String, Object>> list = executeSQLQuery(sql, params);
		return list.size() == 0 ? null : list.get(0);
	}

	public Map<String, Object> executeSQLQueryFirst(String sql, String[] fields, Object... params) {
		NativeQuery query = getSQLQuery(sql, null, params, null);
		List<Map<String, Object>> list = arrayToMap(fields, query.getResultList());
		return list.size() == 0 ? null : list.get(0);
	}

	@Override
	public <T extends Serializable> List<T> executeSQLQuery(String sql, Class<T> c, Object... params) {
		NativeQuery query = getSQLQuery(sql, Map.class, params, null);
		return convertMapToList(c, query.getResultList());
	}

	public static <T> List<T> convertMapToList(Class<T> type, List<Map<String, Object>> maps) {
		List<T> result = new ArrayList<T>();
		for (Map<String, Object> map : maps) {
			try {
				result.add(convertMapToBean(type, map));
			} catch (IllegalAccessException | InstantiationException | InvocationTargetException
					| IntrospectionException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static <T> T convertMapToBean(Class<T> type, Map<String, Object> map)
			throws IntrospectionException, IllegalAccessException, InstantiationException, InvocationTargetException {
		BeanInfo beanInfo = Introspector.getBeanInfo(type);
		T obj = type.newInstance();

		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (String key : map.keySet()) {
			String beanfieldlike = key.replaceAll("_", "").toLowerCase();
			for (int i = 0; i < propertyDescriptors.length; i++) {
				PropertyDescriptor descriptor = propertyDescriptors[i];
				String propertyName = descriptor.getName();
				if (propertyName.toLowerCase().equals(beanfieldlike)) {
					Object value = map.get(key);
					Object[] args = new Object[1];
					args[0] = value;
					if (descriptor.getPropertyType() == Boolean.class) {
						if (value != null && value.getClass() != Boolean.class) {
							Boolean b = Boolean.valueOf(value.toString());
							if (value instanceof Number)
								b = Integer.parseInt(value.toString()) != 0;
							args[0] = b;
						}
					}
					if (descriptor.getPropertyType() == BigInteger.class) {
						if (value != null && value.getClass() != BigInteger.class) {
							BigInteger b = new BigInteger(value.toString());
							args[0] = b;
						}
					}
					if (descriptor.getPropertyType() == Integer.class) {
						if (value != null) {
							Integer b = Integer.parseInt(value.toString());
							args[0] = b;
						}
					}
					descriptor.getWriteMethod().invoke(obj, args);
				}
			}
		}
		return obj;
	}

	@Override
	public <T extends Serializable> T executeSQLQueryFirst(String sql, Class<T> c, Object... params) {
		List<T> list = executeSQLQuery(sql, c, params);
		return list.size() == 0 ? null : list.get(0);
	}

	@Override
	public PageInfo<Map<String, Object>> executeSQLQueryPage(String sql, int start, int limit, Object... params) {
//		return (PageInfo<Map<String, Object>>) executeSQLQueryPage(sql, new HashMap<String, Object>().getClass(), null,
//				params, null, start, limit, 0);
		return null;
	}

	@Override
	public PageInfo<Map<String, Object>> executeSQLQueryPage(String sql, int start, int limit, int total,
			Object... params) {
		return null;
//		return (PageInfo<Map<String, Object>>) executeSQLQueryPage(sql, new HashMap<String, Object>().getClass(), null,
//				params, null, start, limit, total);
	}

	@Override
	public PageInfo<Map<String, Object>> executeSQLQueryPage(String sql, String[] fields, int start, int limit,
			Object... params) {
		return executeSQLQueryPage(sql, null, fields, params, null, start, limit, 0);
	}

	@Override
	public PageInfo<Map<String, Object>> executeSQLQueryPage(String sql, String[] fields, int start, int limit,
			int total, Object... params) {
		return executeSQLQueryPage(sql, null, fields, params, null, start, limit, total);
	}

	@Override
	public <T extends Serializable> PageInfo<T> executeSQLQueryPage(String sql, Class<T> c, int start, int limit,
			Object... params) {
		return executeSQLQueryPage(sql, c, null, params, null, start, limit, 0);
	}

	@Override
	public <T extends Serializable> PageInfo<T> executeSQLQueryPage(String sql, Class<T> c, int start, int limit,
			int total, Object... params) {
		return executeSQLQueryPage(sql, c, null, params, null, start, limit, total);
	}

	@Override
	public int selectCount(String hql, Object... params) {
		return selectCount(hql, params, null);
	}

	@Override
	public int selectPageCount(String hql, Object... params) {
		return selectPageCount(hql, params, null);
	}

	@Override
	public int selectSQLCount(String sql, Object... params) {
		return selectSQLCount(sql, params, null);
	}

	@Override
	public int selectPageSQLCount(String sql, Object... params) {
		return selectPageSQLCount(sql, params, null);
	}

	public <T> Serializable getIdentifierPropertyName(T t) {
		ClassMetadata cm = getClassMetadata(t);
		return cm.getIdentifierPropertyName();
	}

	public <T> Serializable getIdentifier(T t) {
		SessionImpl session = (SessionImpl) getCurrentSession();
		EntityPersister ep = session.getEntityPersister(t.getClass().getName(), t);
		return ep.getIdentifier(t, session);
	}

	public <T> ClassMetadata getClassMetadata(T t) {
		SessionImpl session = (SessionImpl) getCurrentSession();
		Class c = t instanceof Class ? (Class<? extends Object>) t : t.getClass();
		EntityPersister ep = session.getEntityPersister(c.getName(), t);
		return ep.getClassMetadata();
	}

	private <T> PageInfo<T> executeQuery(String hql, Object[] params1, Map<String, Object> params2, int start,
			int limit) {
		Query<T> query = getQuery(hql, params1, params2);
		ScrollableResults sr = query.scroll();
		sr.last();
		int totalRows = sr.getRowNumber();
		query.setFirstResult(start);
		query.setMaxResults(limit);
		return new PageInfo<T>(start, limit, totalRows, query.getResultList());
	}

	private <T> PageInfo<T> executeSQLQueryPage(String sql, Class<T> c, String[] fields, Object[] params1,
			Map<String, Object> params2, int start, int limit, int total) {
		Query<T> query = getSQLQuery(sql, c, params1, params2);
		int totalRows = total == 0 ? selectPageSQLCount(sql, params1, params2) : total;
		query.setFirstResult(start);
		query.setMaxResults(limit);
		List resultList = query.getResultList();
		PageInfo info = new PageInfo<T>(start, limit, totalRows, null);
		if (c == null && !CommonUtils.isEmpty(fields)) {
			info.setData(arrayToMap(fields, resultList));
		} else {
			info.setData(resultList);
		}
		return info;
	}

	private List<Map<String, Object>> arrayToMap(String[] fields, List<Object[]> resultList) {
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < resultList.size(); i++) {
			Object obj = resultList.get(i);
			Map<String, Object> dataMap = new HashMap<String, Object>();
			if (obj instanceof Object[]) {
				Object[] datas = (Object[]) obj;
				for (int j = 0; j < fields.length; j++) {
					dataMap.put(fields[j], j < datas.length ? datas[j] : null);
				}
			} else {
				dataMap.put(fields[0], obj);
			}
			dataList.add(dataMap);
		}
		return dataList;
	}

	private int selectPageCount(String hql, Object[] params1, Map<String, Object> params2) {
		String totalsql = "select count(1) " + hql.substring(hql.indexOf(" from "));
		int orderbyIndex = totalsql.lastIndexOf(" order ");
		if (orderbyIndex != -1)
			totalsql = totalsql.substring(0, orderbyIndex);
		return selectCount(totalsql, params1, params2);
	}

	private int selectCount(String hql, Object[] params1, Map<String, Object> params2) {
		Query query = getQuery(hql, params1, params2);
		Object result = query.getSingleResult();
		if (result instanceof Number) {
			return ((Number) result).intValue();
		} else {
			return Integer.valueOf(result.toString());
		}
	}

	private int selectSQLCount(String sql, Object[] params1, Map<String, Object> params2) {
		Query query = getCurrentSession().createNativeQuery(sql);

		Map<String, Object> param = DataObjectUtils.getSqlParameter();
		if (param != null) {
			for (String key : param.keySet()) {
				if (sql.indexOf(":" + key) != -1)
					query.setParameter(key, param.get(key));
			}
		}
		if (params1 != null) {
			for (int i = 0; i < params1.length; i++) {
				query.setParameter(i, params1[i]);
			}
		}
		if (params2 != null) {
			for (Map.Entry map : params2.entrySet()) {
				query.setParameter((String) map.getKey(), map.getValue());
			}
		}
		Object result = query.getSingleResult();
		if (result instanceof Number) {
			return ((Number) result).intValue();
		} else {
			return Integer.valueOf(result.toString());
		}
	}

	private Query getQuery(String hql, Object[] params1, Map<String, Object> params2) {
		Query query = getCurrentSession().createQuery(hql);
		if (params1 != null) {
			for (int i = 0; i < params1.length; i++) {
				query.setParameter(i, params1[i]);
			}
		}
		if (params2 != null) {
			for (Map.Entry map : params2.entrySet()) {
				query.setParameter((String) map.getKey(), map.getValue());
			}
		}
		return query;
	}

	private <T> NativeQuery getSQLQuery(String sql, Class<T> c, Object[] params1, Map<String, Object> params2) {
		NativeQueryImpl query = (NativeQueryImpl) getCurrentSession().createNativeQuery(sql);

		Map<String, Object> param = DataObjectUtils.getSqlParameter();
		if (param != null) {
			for (String key : param.keySet()) {
				if (sql.indexOf(":" + key) != -1)
					query.setParameter(key, param.get(key));
			}
		}
		if (c == null) {
		} else if (c == Map.class) {
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		} else {
			query.setResultTransformer(Transformers.aliasToBean(c));

		}
		//query.setZeroBasedParametersIndex(true);
		//query.setCacheable()
		if (params1 != null) {
			for (int i = 0; i < params1.length; i++) {
				query.setParameter(i, params1[i]);
			}
		}
		if (params2 != null) {
			for (Map.Entry map : params2.entrySet()) {
				query.setParameter((String) map.getKey(), map.getValue());
			}
		}
		return query;
	}

	@SuppressWarnings("unused")
	private <T> void setParentBeanNull11(T t, String... includeFields) {
		Field[] fields = t.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			try {
				Field field = fields[i];
				Table table = field.getType().getAnnotation(Table.class);
				if (table != null) {
					field.setAccessible(true);
					Object obj = field.get(t);
					if (obj == null)
						continue;
					Object keyValue = getIdentifier(obj);
					if (keyValue == null || keyValue.toString().length() == 0) {
						field.set(t, null);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (includeFields != null) {
			for (int j = 0; j < includeFields.length; j++) {
				String includefield = includeFields[j];
				String[] incs = includefield.split("[.]");
				if (incs.length > 1) {
					includeFields[j] = incs[0];
				}
			}
		}

	}

	public void setSession(Session session) {
		this.session = session;
	}
}
