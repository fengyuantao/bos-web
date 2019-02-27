package cm.ithema.dao.base;

import java.io.Serializable;
import java.util.List;

import cm.ithema.domian.Region;

public interface IbaseDao<T> {

	void save(T entity);
	void delete(T entity);
	void saveOrUpdate(T entity);
	void delete(String[] id,String sql);
	void update(T entity);
	T findById(Serializable id);
	List<T> findAll();
	List<T> findAllByPages(String sql, Integer startcount, Integer endtcount);
}
