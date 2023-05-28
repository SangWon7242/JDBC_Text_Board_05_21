package com.sbs.exam.board;

import com.sbs.exam.board.container.Container;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
        int actionResult = doAction(rq, conn, sc);

        if (actionResult == -1) {
          continue;
        }

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

  private int doAction(Rq rq, Connection conn, Scanner sc) {
    int articleLastId = 0;
    PreparedStatement pstat = null;
    List<Article> articles = new ArrayList<>();

    if (rq.getUrlPath().equals("/usr/article/write")) {
      System.out.println("== 게시물 등록 ==");
      System.out.printf("제목) ");
      String title = sc.nextLine();
      System.out.printf("내용) ");
      String content = sc.nextLine();
      int id = ++articleLastId;

      try {
        String sql = "INSERT INTO article";
        sql += " SET regDate = NOW()";
        sql += ", updateDate = NOW()";
        sql += ", title = \"" + title + "\"";
        sql += ", content = \"" + content + "\"";

        pstat = conn.prepareStatement(sql);
        pstat.executeUpdate();

      } catch (SQLException e) {
        System.out.println("에러 : " + e);
      } finally {
        try {
          if (pstat != null && !pstat.isClosed()) {
            pstat.close();
          }
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }

      System.out.printf("%d번 게시물이 등록되었습니다.\n", id);
    } else if (rq.getUrlPath().equals("/usr/article/list")) {
      ResultSet rs = null;

      try {
        String sql = "SELECT *";
        sql += " FROM article";
        sql += " ORDER BY id DESC;";

        pstat = conn.prepareStatement(sql);
        rs = pstat.executeQuery(sql);

        while (rs.next()) {
          int id = rs.getInt("id");
          String regDate = rs.getString("regDate");
          String updateDate = rs.getString("updateDate");
          String title = rs.getString("title");
          String content = rs.getString("content");

          Article article = new Article(id, regDate, updateDate, title, content);
          articles.add(article);
        }

      } catch (SQLException e) {
        System.out.println("에러: " + e);
      } finally {
        try {
          if (rs != null && !rs.isClosed()) {
            rs.close();
          }
        } catch (SQLException e) {
          e.printStackTrace();
        }
        try {
          if (pstat != null && !pstat.isClosed()) {
            pstat.close();
          }
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }

      System.out.println("== 게시물 리스트 ==");

      if (articles.isEmpty()) {
        System.out.println("게시물이 존재하지 않습니다.");
        return -1;
      }

      System.out.println("번호 / 제목");

      for (Article article : articles) {
        System.out.printf("%d / %s\n", article.id, article.title);
      }
    } else if (rq.getUrlPath().equals("/usr/article/modify")) {
      int id = rq.getIntParam("id", 0);

      if (id == 0) {
        System.out.println("id를 올바르게 입력해주세요.");
        return -1;
      }

      System.out.printf("새 제목 : ");
      String title = sc.nextLine();
      System.out.printf("새 내용 : ");
      String content = sc.nextLine();

      try {
        String sql = "UPDATE article";
        sql += " SET updateDate = NOW()";
        sql += ", title = \"" + title + "\"";
        sql += ", content = \"" + content + "\"";
        sql += " WHERE id = " + id;

        pstat = conn.prepareStatement(sql);
        pstat.executeUpdate();

        System.out.printf("%d번 게시물이 수정되었습니다.\n", id);

      } catch (SQLException e) {
        System.out.println("에러: " + e);
      } finally {
        try {
          if (pstat != null && !pstat.isClosed()) {
            pstat.close();
          }
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    } else if (rq.getUrlPath().equals("/exit")) {
      System.out.println("프로그램 종료");
      System.exit(0);
    } else {
      System.out.println("명령어를 확인해주세요.");
    }

    return 0;
  }
}
