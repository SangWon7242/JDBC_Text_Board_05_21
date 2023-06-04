package com.sbs.exam.board;

import com.sbs.exam.board.container.Container;
import com.sbs.exam.board.controller.UsrArticleController;
import com.sbs.exam.board.controller.UsrMemberController;
import com.sbs.exam.board.util.DBUtil;
import com.sbs.exam.board.util.SecSql;

import java.sql.*;
import java.util.*;

public class App {
  public void run() {
    Scanner sc = Container.scanner;

    while (true) {
      System.out.printf("명령어) ");
      String cmd = sc.nextLine();

      Rq rq = new Rq(cmd);

      // DB 연결 시작
      Connection conn = null;

      try {
        Class.forName("com.mysql.jdbc.Driver");
      } catch (ClassNotFoundException e) {
        System.out.println("예외 : MySQL 드라이버 로딩 실패");
        System.out.println("프로그램을 종료합니다.");
        break;
      }

      String url = "jdbc:mysql://127.0.0.1:3306/text_board?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

      try {
        conn = DriverManager.getConnection(url, "sbsst", "sbs123414");

        // 로직에 실행부분
        action(rq, conn, sc);

      } catch (SQLException e) {
        System.out.println("예외 : MySQL 드라이버 로딩 실패");
        System.out.println("프로그램을 종료합니다.");
        break;
      } finally {
        try {
          if (conn != null && !conn.isClosed()) {
            conn.close();
          }
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
      // DB 연결 끝
    }

    sc.close();
  }

  private void action(Rq rq, Connection conn, Scanner sc) {
    UsrArticleController usrArticleController = new UsrArticleController(conn, sc, rq);
    UsrMemberController usrMemberController = new UsrMemberController(conn, sc, rq);

    if (rq.getUrlPath().equals("/usr/article/write")) {
      usrArticleController.doWrite();
    } else if (rq.getUrlPath().equals("/usr/article/list")) {
      usrArticleController.showList();
    } else if (rq.getUrlPath().equals("/usr/article/detail")) {
      usrArticleController.showDetail();
    } else if (rq.getUrlPath().equals("/usr/article/modify")) {
      usrArticleController.doModify();
    } else if (rq.getUrlPath().equals("/usr/article/delete")) {
      usrArticleController.doDelete();
    } else if (rq.getUrlPath().equals("/usr/member/join")) {
      usrMemberController.join();
    } else if (rq.getUrlPath().equals("/exit")) {
      System.out.println("프로그램 종료");
      System.exit(0);
    } else {
      System.out.println("명령어를 확인해주세요.");
    }
    return;
  }
}
