package cm.ithema.dao.base.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import cm.ithema.dao.base.IbaseDao;
import cm.ithema.domian.Staff;

public class IbaseDaoImpl<T> extends HibernateDaoSupport implements IbaseDao<T>{
	
	private Class<T> entityClass;
	//根据类型注入spring工厂中的会话工厂对象sessionFactory
	@Resource
	public void setMySessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	

	// 在父类的构造方法中动态活动entityclass
	public IbaseDaoImpl() {
		ParameterizedType param = (ParameterizedType) this.getClass().getGenericSuperclass();
		Type[] entitys = param.getActualTypeArguments();
		entityClass = (Class<T>) entitys[0];
	}

	@Override
	public void save(T entity) {
		this.getHibernateTemplate().save(entity);
		
	}

	@Override
	public void delete(T entity) {
		this.getHibernateTemplate().delete(entity);
		
	}

	@Override
	public void update(T entity) {
		this.getHibernateTemplate().update(entity);
		
	}

	@Override
	public T findById(Serializable id) {
		
		return this.getHibernateTemplate().get(entityClass, id);
	}

	@Override
	public List<T> findAll() {
		String sql = "FROM " + entityClass.getSimpleName();
		
		return (List<T>) this.getHibernateTemplate().find(sql);
	}

	@Override
	public List<T> findAllByPages(final String sql,final Integer startcount, final Integer endtcount) {
		// 分页查询
		
		//this.getSessionFactory().getCurrentSession().createCriteria(arg0);
		
		Query query = this.getSessionFactory().getCurrentSession().createQuery(sql);
		
		query.setFirstResult(startcount);
		query.setMaxResults(endtcount);
	/*	List<T> staffs = (List<T>) this.getHibernateTemplate().execute(new HibernateCallback(){

			@Override
			public List doInHibernate(Session session) throws HibernateException {
				// 分页查询
				Query query = session.createQuery(sql);
				query.setFirstResult(startcount);
				query.setMaxResults(endtcount);
				
				return  query.list();
			}
			
		});*/
		List<T> list = query.list();
		System.out.println("list:"+list.size());
		for(T lis : list) {
			System.out.println("t:"+lis.toString());
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public void delete(final String[] id,final String sql) {
	/*	String sql = "delete from Staff where ";
		SessionFactory session = this.getSessionFactory();
		
		
		for(int i = 0 ; i < id.length;i++) {
			if(i == 0) {
				sql += "id = "+ "+id[i]+";
			}else {
				sql = sql + " or id = " + id[i];
			}
		}
		System.out.println(sql);
		Session se = session.openSession();
		Transaction tx = se.beginTransaction();
		Query query = se.createQuery(sql);
		query.executeUpdate();
		tx.commit();
		se.close();*/
		//final String sql = "delete Staff where id in (:ids)";
		this.getHibernateTemplate().execute(new HibernateCallback() {

			
			 public Object doInHibernate(Session session) throws HibernateException {
				Query query = session.createQuery(sql);
				query.setParameterList("ids", id);
				int i = query.executeUpdate();
				if(i<=0) {
					try {
						throw new Exception("删除失败！");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				}
				return i;
			}
			
		});
	}


	@Override
	public void saveOrUpdate(T entity) {
		this.getHibernateTemplate().saveOrUpdate(entity);
		
	}
}
