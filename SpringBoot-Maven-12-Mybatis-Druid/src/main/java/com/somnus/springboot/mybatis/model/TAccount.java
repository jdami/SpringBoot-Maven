package com.somnus.springboot.mybatis.model;

import javax.persistence.*;

@Table(name = "t_account")
public class TAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String address;

    private String nonce;

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
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return nonce
     */
    public String getNonce() {
        return nonce;
    }

    /**
     * @param nonce
     */
    public void setNonce(String nonce) {
        this.nonce = nonce;
    }
}