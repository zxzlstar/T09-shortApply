package com.star.shop.basic.service;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.star.shop.basic.entity.AbstractTreeEntity;
import com.star.shop.basic.repository.BaseRepository;
import com.star.shop.basic.utils.Constants;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
public abstract class BaseService <T , ID extends Serializable> {
	protected @PersistenceContext EntityManager entityManager;

	protected abstract BaseRepository<T, ID> getBaseRepository();

	/**
	 * Returns all instances of the type.
	 * 
	 * @return all entities
	 */
	public List<T> findAll() {
		return this.getBaseRepository().findAll();
	}

	/**
	 * Returns all entities sorted by the given options.
	 * 
	 * @param sort
	 * @return all entities sorted by the given options
	 */
	public List<T> findAll(Sort sort) {
		return this.getBaseRepository().findAll(sort);
	}

	/**
	 * Returns all instances of the type with the given IDs.
	 * 
	 * @param example
	 * @return
	 */
	public List<T> findAll(Example example) {
		return this.getBaseRepository().findAll(example);
	}


	/**
	 * Flushes all pending changes to the database.
	 */
	public void flush() {
		this.getBaseRepository().flush();
	}

	/**
	 * Saves an entity and flushes changes instantly.
	 * 
	 * @param entity
	 * @return the saved entity
	 */
	public <S extends T> S saveAndFlush(S entity) {
		return this.getBaseRepository().saveAndFlush(entity);
	}

	/**
	 * Deletes the given entities in a batch which means it will create a single
	 * {@link Query}. Assume that we will clear the
	 * {@link EntityManager} after the call.
	 * 
	 * @param entities
	 */
	public void deleteInBatch(Iterable<T> entities) {
		this.getBaseRepository().deleteInBatch(entities);
	}

	/**
	 * Deletes all entites in a batch call.
	 */
	public void deleteAllInBatch() {
		this.getBaseRepository().deleteAllInBatch();
	}

	/**
	 * Returns a reference to the entity with the given identifier.
	 * 
	 * @param id
	 *            must not be {@literal null}.
	 * @return a reference to the entity with the given identifier.
	 * @see EntityManager#getReference(Class, Object)
	 */
	public T getOne(ID id) {
		return this.getBaseRepository().getOne(id);
	}

	/**
	 * Returns a {@link Page} of entities meeting the paging restriction
	 * provided in the {@code Pageable} object.
	 * 
	 * @param pageable
	 * @return a page of entities
	 */
	public Page<T> findAll(Pageable pageable) {
		return this.getBaseRepository().findAll(pageable);
	}

	/**
	 * Saves a given entity. Use the returned instance for further operations as
	 * the save operation might have changed the entity instance completely.
	 * 
	 * @param entity
	 * @return the saved entity
	 */
	public <S extends T> S save(S entity) {
		return this.getBaseRepository().save(entity);
	}

	/**
	 * Retrieves an entity by its id.
	 * 
	 * @param example
	 *            must not be {@literal null}.
	 * @return the entity with the given id or {@literal null} if none found
	 * @throws IllegalArgumentException
	 *             if {@code id} is {@literal null}
	 */
	public Optional<T> findOne(Example example) {
		return this.getBaseRepository().findOne(example);
	}

	/**
	 * Returns whether an entity with the given id exists.
	 * 
	 * @param example
	 *            must not be {@literal null}.
	 * @return true if an entity with the given id exists, {@literal false}
	 *         otherwise
	 * @throws IllegalArgumentException
	 *             if {@code id} is {@literal null}
	 */
	public boolean exists(Example example) {
		return this.getBaseRepository().exists(example);
	}

	/**
	 * Returns the number of entities available.
	 * 
	 * @return the number of entities
	 */
	public long count() {
		return this.getBaseRepository().count();
	}

	/**
	 * Deletes the entity with the given id.
	 * 
	 * @param id
	 *            must not be {@literal null}.
	 * @throws IllegalArgumentException
	 *             in case the given {@code id} is {@literal null}
	 */
	public void delete(ID id) {
		this.getBaseRepository().deleteById(id);
	}

	/**
	 * Deletes a given entity.
	 * 
	 * @param entity
	 * @throws IllegalArgumentException
	 *             in case the given entity is {@literal null}.
	 */
	public void delete(T entity) {
		this.getBaseRepository().delete(entity);
	}


	/**
	 * Deletes all entities managed by the repository.
	 */
	public void deleteAll() {
		this.getBaseRepository().deleteAll();
	}
	
	/**
	 * 获取路径
	 * @param entity
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getPath(T entity){
		String path = ""; 
		if(entity instanceof AbstractTreeEntity){
			AbstractTreeEntity tree = (AbstractTreeEntity)entity ;
			path = tree.getId() ;
			if(StringUtils.hasText(tree.getPid())){
				T parent = this.findOne(Example.of(tree)).get() ;
				//path = this.getPath(parent) + "," + path ;
				AbstractTreeEntity p = (AbstractTreeEntity)parent ;
				path = p.getPath() + Constants.DEFAULT_DELIMITER + path ;
			}
			
			
		}
		return path ;
	}
	
	/**
	 * 更新路径
	 * @param entity
	 */
	public void updateChildrenPath(T entity){
		if(entity instanceof AbstractTreeEntity){
			AbstractTreeEntity tree = (AbstractTreeEntity)entity ;
			List<T> children = this.findByPid(tree.getId()) ;
			for(T t : children){
				AbstractTreeEntity child = (AbstractTreeEntity)t ;
				child.setPath(tree.getPath() + Constants.DEFAULT_DELIMITER + child.getId());
				child.setPname(tree.getName()); //更新上级名称
				this.getBaseRepository().save(t) ;
				
				this.updateChildrenPath(t);
			}
		}
	}
	
	/**
	 * 
	 * 根据上级ID查询下级
	 * 
	 * @param pid
	 * @return
	 */
	public List<T> findByPid(String pid){
		return Arrays.asList() ;
	}
}
