package com.star.shop.basic.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 树类型实体基类
 *
 * @date 2018年8月10日
 *
 * @author x.zhang
 *
 */
@MappedSuperclass
public abstract class AbstractTreeEntity extends AbstractEntity {
	/**
	 * 上级ID
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	private String pid;
	
	/**
	 * 名称
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	private String name;


	/**
	 * 上级名称
	 */
	@Column(columnDefinition = "varchar(50) default ''")
	private String pname;

	/**
	 * 路径
	 */
	@Column(columnDefinition = "varchar(1024) default ''")
	private String path;

	@Transient
	private List<? super AbstractTreeEntity> children;

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public <T> List<? super AbstractTreeEntity> getChildren() {
		return children;
	}

	public <T> void setChildren(List<? super AbstractTreeEntity> children) {
		this.children = children;
	}

	public <T extends AbstractTreeEntity> void addChildren(T entry) {
		if (children == null) {

			this.children = new ArrayList<>();
		}
		this.children.add(entry);
	}
}
