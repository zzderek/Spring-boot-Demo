package com.y2t.akeso.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.y2t.akeso.common.sql.Invisible;

public class User {

    private Long id;

    private String userName;

    private String userId;

    private String phone;

    private String status;

    /** 创建时间 */
    @Invisible
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private String  createTime;
    /** 更新时间 */
    @Invisible
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private String updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public User( String userId,String userName, String phone, String status) {
        this.userName = userName;
        this.userId = userId;
        this.phone = phone;
        this.status = status;
    }

    public User() {

    }
}
