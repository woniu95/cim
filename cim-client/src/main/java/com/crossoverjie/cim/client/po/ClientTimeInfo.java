package com.crossoverjie.cim.client.po;

import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @program: cim
 * @description:
 * @author: 王强
 * @create: 2020-12-07 18:59
 */
@Component
public class ClientTimeInfo {

    private boolean alive;

    private volatile Date lastOperateTime;

    private volatile Date lastReceiveMsgTime;

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public Date getLastOperateTime() {
        return lastOperateTime;
    }

    public void setLastOperateTime(Date lastOperateTime) {
        this.lastOperateTime = lastOperateTime;
    }

    public Date getLastReceiveMsgTime() {
        return lastReceiveMsgTime;
    }

    public void setLastReceiveMsgTime(Date lastReceiveMsgTime) {
        this.lastReceiveMsgTime = lastReceiveMsgTime;
    }

    @Override
    public String toString() {
        return "ClientTimeInfo{" +
                "alive=" + alive +
                ", lastOperateTime=" + lastOperateTime +
                ", lastReceiveMsgTime=" + lastReceiveMsgTime +
                '}';
    }
}
