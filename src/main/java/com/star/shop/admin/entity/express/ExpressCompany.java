package com.star.shop.admin.entity.express;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "express_company")
@DynamicInsert
@DynamicUpdate
public class ExpressCompany {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    /**
     * 快递公司编码
     */
    @Column(name= "code", columnDefinition = "varchar(50) default ''")
    private String code;

    /**
     * 快递公司名称
     */
    @Column(name= "name", columnDefinition = "varchar(50) default ''")
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
