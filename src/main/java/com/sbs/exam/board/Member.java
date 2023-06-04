package com.sbs.exam.board;

import java.util.Map;

public class Member {
  public int id;
  public String regDate;
  public String updateDate;
  public String loginId;
  public String loginPw;
  public String name;

  public String getLoginId() {
    return loginId;
  }

  public String getLoginPw() {
    return loginPw;
  }

  public String getName() {
    return name;
  }

  public Member(Map<String, Object> articleMap) {
    this.id = (int) articleMap.get("id");
    this.regDate = (String) articleMap.get("regDate");
    this.updateDate = (String) articleMap.get("updateDate");
    this.loginId = (String) articleMap.get("loginId");
    this.loginPw = (String) articleMap.get("loginPw");
    this.name = (String) articleMap.get("name");
  }

  @Override
  public String toString() {
    return "Member{" +
        "id=" + id +
        ", regDate='" + regDate + '\'' +
        ", updateDate='" + updateDate + '\'' +
        ", loginId='" + loginId + '\'' +
        ", loginPw='" + loginPw + '\'' +
        '}';
  }
}
