package com.sbs.exam.board;

import com.sbs.exam.board.container.Container;
import com.sbs.exam.board.controller.UsrArticleController;
import com.sbs.exam.board.controller.UsrMemberController;
import com.sbs.exam.board.dto.Rq;

import java.sql.*;
import java.util.*;

public class App {
  public void run() {
    Container.scanner = new Scanner(System.in);
    Container.init();

    while (true) {
      System.out.printf("명령어) ");
      String cmd = Container.scanner.nextLine();

      Container.rq = new Rq(cmd);

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

        Container.conn = conn;

        // 로직에 실행부분
        action(Container.rq);

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

    Container.scanner.close();
  }

  private void action(Rq rq) {
    if (rq.getUrlPath().equals("/usr/article/write")) {
      Container.usrArticleController.doWrite();
    } else if (rq.getUrlPath().equals("/usr/article/list")) {
      Container.usrArticleController.showList();
    } else if (rq.getUrlPath().equals("/usr/article/detail")) {
      Container.usrArticleController.showDetail();
    } else if (rq.getUrlPath().equals("/usr/article/modify")) {
      Container.usrArticleController.doModify();
    } else if (rq.getUrlPath().equals("/usr/article/delete")) {
      Container.usrArticleController.doDelete();
    } else if (rq.getUrlPath().equals("/usr/member/join")) {
      Container.usrMemberController.join();
    } else if (rq.getUrlPath().equals("/usr/member/login")) {
      Container.usrMemberController.login();
    } else if (rq.getUrlPath().equals("/exit")) {
      System.out.println("프로그램 종료");
      System.exit(0);
    } else {
      System.out.println("명령어를 확인해주세요.");
    }
    return;
  }
}
