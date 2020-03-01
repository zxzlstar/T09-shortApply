package com.star.shop.basic.utils;

import java.util.Collection;

import org.springframework.data.jpa.domain.Specification;

/**
 * 
 * 
 * <p>Title:SpecificationFactory</p>
 *
 * <p>Description:</p>
 *
 * <p>Company:</p>
 *
 * @author x.zhang
 *
 * @date 2018年9月1日
 */
public final class SpecificationFactory {
    /**
     * 模糊查询，匹配对应字段
     * @param attribute
     * @param value
     * @return
     */
    public static <T> Specification<T> containsLike(String attribute, String value) {
        return (root, query, cb) -> cb.like(root.get(attribute), "%" + value + "%");
    }
    /**
     * 某字段的值等于value的查询条件
     * @param attribute
     * @param value
     * @return
     */
    public static <T> Specification<T> equal(String attribute, Object value) {
        return (root, query, cb) -> cb.equal(root.get(attribute),value);
    }
    /**
     * 获取对应属性的值所在区间
     * @param attribute
     * @param min
     * @param max
     * @return
     */
    public static <T> Specification<T> isBetween(String attribute, int min, int max) {
        return (root, query, cb) -> cb.between(root.get(attribute), min, max);
    }
    
    /**
     * 
     * @param attribute
     * @param min
     * @param max
     * @return
     */
    public static <T> Specification<T> isBetween(String attribute, double min, double max) {
        return (root, query, cb) -> cb.between(root.get(attribute), min, max);
    }
    /**
     * 通过属性名和集合实现in查询
     * @param attribute
     * @param c
     * @return
     */
    public static <T> Specification<T> in(String attribute, Collection<?> c) {
        return (root, query, cb) -> root.get(attribute).in(c);
    }
}