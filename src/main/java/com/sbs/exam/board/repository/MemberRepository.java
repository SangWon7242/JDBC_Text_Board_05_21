package com.sbs.exam.board.repository;

import com.sbs.exam.board.Article;
import com.sbs.exam.board.Member;
import com.sbs.exam.board.util.DBUtil;
import com.sbs.exam.board.util.SecSql;

import java.sql.Connection;
import java.util.Map;

public class MemberRepository {
  private Connection conn;
  public MemberRepository(Connection conn) {
    this.conn = conn;
  }

  public boolean isLoginIdDup(String loginId) {
    SecSql sql = new SecSql();
    sql.append("SELECT COUNT(*) > 0");
    sql.append("FROM `member`");
    sql.append("WHERE loginId = ?", loginId);

    return DBUtil.selectRowBooleanValue(conn, sql);
  }

  public int join(String loginId, String loginPw, String name) {
    SecSql sql = new SecSql();

    sql.append("INSERT INTO `member`");
    sql.append("SET regDate = NOW()");
    sql.append(", updateDate = NOW()");
    sql.append(", loginId = ?", loginId);
    sql.append(", loginPw = ?", loginPw);
    sql.append(", name = ?", name);

    return DBUtil.insert(conn, sql);
  }

  public Member getMemberByLoginId(String loginId) {
    SecSql sql = new SecSql();

    sql.append("SELECT *");
    sql.append("FROM `member`");
    sql.append("WHERE loginId = ?", loginId);

    Map<String, Object> memberMap = DBUtil.selectRow(conn, sql);

    if(memberMap.isEmpty()) {
      return null;
    }

    return new Member(memberMap);
  }
}
