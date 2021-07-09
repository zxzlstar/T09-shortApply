/**
 * 
 */
package com.star.shop.admin.service.express.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.star.shop.admin.service.express.BaseService;
import com.star.shop.basic.repository.BaseRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;




/**
 * @author cyan
 * @param <T>
 *
 */
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
public abstract class BaseServiceImpl<T, ID extends Serializable> implements BaseService<T , ID> {
	
	protected @PersistenceContext EntityManager entityManager;

	protected abstract BaseRepository<T, ID> getBaseRepository();

	/**
	 * Returns all instances of the type.
	 * 
	 * @return all entities
	 */
	@Override
	public List<T> findAll() {
		return this.getBaseRepository().findAll();
	}

	/**
	 * Returns all entities sorted by the given options.
	 * 
	 * @param sort
	 * @return all entities sorted by the given options
	 */
	@Override
	public List<T> findAll(Sort sort) {
		return this.getBaseRepository().findAll(sort);
	}

	/**
	 * Returns all instances of the type with the given IDs.
	 * 
	 * @param example
	 * @return
	 */
	@Override
	public List<T> findAll(Example<T> example) {
		return this.getBaseRepository().findAll(example);
	}


	/**
	 * Flushes all pending changes to the database.
	 */
	@Override
	public void flush() {
		this.getBaseRepository().flush();
	}

	/**
	 * Saves an entity and flushes changes instantly.
	 * 
	 * @param entity
	 * @return the saved entity
	 */
	@Override
	public <S extends T> S saveAndFlush(S entity) {
		return this.getBaseRepository().saveAndFlush(entity);
	}

	/**
	 * Deletes the given entities in a batch which means it will create a single
	 * {@link EntityManager} after the call.
	 * 
	 * @param entities
	 */
	@Override
	public void deleteInBatch(Iterable<T> entities) {
		this.getBaseRepository().deleteInBatch(entities);
	}

	/**
	 * Deletes all entites in a batch call.
	 */
	@Override
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
	@Override
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
	@Override
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
	@Override
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
	@SuppressWarnings("unchecked")
	@Override
	public Optional<T> findOne(Example<T> example) {
		return (Optional<T>) this.getBaseRepository().findOne(example);
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
	@Override
	public boolean exists(Example<T> example) {
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
	@Override
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
	
	public String createId(String prefix) {
//		DateUtils.fromDateStringToLong(DateUtils.DATE_TIME_PATTERN_FULL, inVal);
		long dateStr = System.currentTimeMillis();
		Random random = new Random();
		int num = random.nextInt(1000);
		return prefix
				+ (dateStr == 0 ? "-NODATE-" : dateStr)
				+ String.format("%03d", num);
	}
}
