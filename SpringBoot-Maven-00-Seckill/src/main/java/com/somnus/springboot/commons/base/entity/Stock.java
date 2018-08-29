package com.somnus.springboot.commons.base.entity;

import javax.persistence.*;

public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * ����
     */
    private String name;

    /**
     * ���
     */
    private Integer count;

    /**
     * ����
     */
    private Integer sale;

    /**
     * �ֹ������汾��
     */
    private Integer version;

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
     * ��ȡ����
     *
     * @return name - ����
     */
    public String getName() {
        return name;
    }

    /**
     * ��������
     *
     * @param name ����
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * ��ȡ���
     *
     * @return count - ���
     */
    public Integer getCount() {
        return count;
    }

    /**
     * ���ÿ��
     *
     * @param count ���
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * ��ȡ����
     *
     * @return sale - ����
     */
    public Integer getSale() {
        return sale;
    }

    /**
     * ��������
     *
     * @param sale ����
     */
    public void setSale(Integer sale) {
        this.sale = sale;
    }

    /**
     * ��ȡ�ֹ������汾��
     *
     * @return version - �ֹ������汾��
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * �����ֹ������汾��
     *
     * @param version �ֹ������汾��
     */
    public void setVersion(Integer version) {
        this.version = version;
    }
}