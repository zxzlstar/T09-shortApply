/**
 * 
 */
package com.star.shop.admin.service.express;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * @author cyan
 *
 */
public interface BaseService<T , ID extends Serializable> {
		
	public List<T> findAll();
	
	public List<T> findAll(Sort sort);
	
	public List<T> findAll(Example<T> example);

	public void flush();
	
	public <S extends T> S saveAndFlush(S entity);
	
	public void deleteInBatch(Iterable<T> entities);
	
	public void deleteAllInBatch();
	
	public T getOne(ID id);
	
	public Page<T> findAll(Pageable pageable);
	
	public <S extends T> S save(S entity);
	
	public Optional<T> findOne(Example<T> example);
	
	public boolean exists(Example<T> example);
	
	public long count();
	
	public void delete(ID id);
	
	public void delete(T entity);
	
	public void deleteAll();
	
	public String createId(String prefix);
}
