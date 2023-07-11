package com.yr.springbootshiro.entity;

import org.springframework.stereotype.Component;

@Component
public class UUser {

  private Integer id;
  private String nickname;
  private String email;
  private String pswd;
  private java.sql.Timestamp createTime;
  private java.sql.Timestamp lastLoginTime;
  private Integer status;


  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }


  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }


  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }


  public String getPswd() {
    return pswd;
  }

  public void setPswd(String pswd) {
    this.pswd = pswd;
  }


  public java.sql.Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(java.sql.Timestamp createTime) {
    this.createTime = createTime;
  }


  public java.sql.Timestamp getLastLoginTime() {
    return lastLoginTime;
  }

  public void setLastLoginTime(java.sql.Timestamp lastLoginTime) {
    this.lastLoginTime = lastLoginTime;
  }


  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  @Override
  public String toString() {
    return "UUser{" +
            "id=" + id +
            ", nickname='" + nickname + '\'' +
            ", email='" + email + '\'' +
            ", pswd='" + pswd + '\'' +
            ", createTime=" + createTime +
            ", lastLoginTime=" + lastLoginTime +
            ", status=" + status +
            '}';
  }
}
