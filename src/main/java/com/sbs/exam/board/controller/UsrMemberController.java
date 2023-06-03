package com.sbs.exam.board.controller;

import com.sbs.exam.board.container.Container;
import com.sbs.exam.board.util.DBUtil;
import com.sbs.exam.board.util.SecSql;

import java.sql.Connection;
import java.util.Scanner;

public class UsrMemberController {
  private Connection conn;
  private Scanner sc;
  public void setConn(Connection conn) {
    this.conn = conn;
  }
  public void setScanner() {
    this.sc = Container.scanner;
  }
  public void join() {
    String loginId;
    String loginPw;
    String loginPwConfirm;
    String name;

    System.out.println("== 회원 가입 ==");
    // 로그인 아이디 입력 확인
    while (true) {
      System.out.printf("로그인 아이디 : ");
      loginId = sc.nextLine().trim();

      if (loginId.length() == 0) {
        System.out.println("로그인 아이디를 입력해주세요.");
        continue;
      }

      SecSql sql = new SecSql();
      sql.append("SELECT COUNT(*) > 0");
      sql.append("FROM `member`");
      sql.append("WHERE loginId = ?", loginId);

      boolean isLoginIdDup = DBUtil.selectRowBooleanValue(conn, sql);

      if (isLoginIdDup) {
        System.out.printf("%s(은)는 이미 사용중인 로그인 아이디입니다.\n", loginId);
        continue;
      }

      break;
    }

    // 로그인 비밀번호 입력 확인
    while (true) {
      System.out.printf("로그인 비번 : ");
      loginPw = sc.nextLine().trim();

      if (loginPw.length() == 0) {
        System.out.println("로그인 비번을 입력해주세요.");
        continue;
      }

      boolean loginPwConfirmIsSame = true;

      while (true) {
        System.out.printf("로그인 비번 확인 : ");
        loginPwConfirm = sc.nextLine().trim();

        if (loginPwConfirm.length() == 0) {
          System.out.println("로그인 비번 확인을 입력해주세요 : ");
          continue;
        }

        if (loginPw.equals(loginPwConfirm) == false) {
          System.out.println("로그인 비밀번호가 일치하지 않습니다. ");
          loginPwConfirmIsSame = false;
          break;
        }

        break;
      }

      if (loginPwConfirmIsSame) {
        break;
      }
    }

    // 이름 입력 확인
    while (true) {
      System.out.printf("이름 : ");
      name = sc.nextLine().trim();

      if (name.length() == 0) {
        System.out.println("이름을 입력해주세요.");
        continue;
      }
      break;
    }

    SecSql sql = new SecSql();

    sql.append("INSERT INTO `member`");
    sql.append("SET regDate = NOW()");
    sql.append(", updateDate = NOW()");
    sql.append(", loginId = ?", loginId);
    sql.append(", loginPw = ?", loginPw);
    sql.append(", name = ?", name);

    int id = DBUtil.insert(conn, sql);

    System.out.printf("%d번 회원이 등록되었습니다.\n", id);
  }

}
