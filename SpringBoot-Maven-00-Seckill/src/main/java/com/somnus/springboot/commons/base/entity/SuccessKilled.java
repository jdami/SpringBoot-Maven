package com.somnus.springboot.commons.base.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "success_killed")
public class SuccessKilled {
    @Id
    @Column(name = "seckill_id")
    private Long seckillId;

    @Id
    @Column(name = "user_id")
    private Long userId;

    private Byte state;

    @Column(name = "create_time")
    private Date createTime;

    public Long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(Long seckillId) {
        this.seckillId = seckillId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}