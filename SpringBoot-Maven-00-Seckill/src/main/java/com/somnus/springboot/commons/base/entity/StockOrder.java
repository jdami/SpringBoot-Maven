package com.somnus.springboot.commons.base.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "stock_order")
public class StockOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * ���ID
     */
    private Integer sid;

    /**
     * ��Ʒ����
     */
    private String name;

    /**
     * ����ʱ��
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * ��ȡ���ID
     *
     * @return sid - ���ID
     */
    public Integer getSid() {
        return sid;
    }

    /**
     * ���ÿ��ID
     *
     * @param sid ���ID
     */
    public void setSid(Integer sid) {
        this.sid = sid;
    }

    /**
     * ��ȡ��Ʒ����
     *
     * @return name - ��Ʒ����
     */
    public String getName() {
        return name;
    }

    /**
     * ������Ʒ����
     *
     * @param name ��Ʒ����
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * ��ȡ����ʱ��
     *
     * @return create_time - ����ʱ��
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * ���ô���ʱ��
     *
     * @param createTime ����ʱ��
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}