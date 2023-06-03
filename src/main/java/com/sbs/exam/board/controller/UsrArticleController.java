package com.sbs.exam.board.controller;

import com.sbs.exam.board.Article;
import com.sbs.exam.board.util.DBUtil;
import com.sbs.exam.board.util.SecSql;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UsrArticleController extends Controller {
  List<Article> articles = new ArrayList<>();
  public void doWrite() {
    System.out.println("== 게시물 등록 ==");
    System.out.printf("제목 : ");
    String title = sc.nextLine();
    System.out.printf("내용 : ");
    String content = sc.nextLine();

    SecSql sql = new SecSql();

    sql.append("INSERT INTO article");
    sql.append("SET regDate = NOW()");
    sql.append(", updateDate = NOW()");
    sql.append(", title = ?", title);
    sql.append(", content = ?", content);

    int id = DBUtil.insert(conn, sql);

    System.out.printf("%d번 게시물이 등록되었습니다.\n", id);
  }

  public void showList() {
    SecSql sql = new SecSql();

    sql.append("SELECT *");
    sql.append("FROM article");
    sql.append("ORDER BY id DESC");

    List<Map<String, Object>> articleListMap = DBUtil.selectRows(conn, sql);

    for (Map<String, Object> articleMap : articleListMap) {
      articles.add(new Article(articleMap));
    }

    System.out.println("== 게시물 리스트 ==");

    if (articles.isEmpty()) {
      System.out.println("게시물이 존재하지 않습니다.");
      return;
    }

    System.out.println("번호 / 제목");

    for (Article article : articles) {
      System.out.printf("%d / %s\n", article.id, article.title);
    }
  }

  public void showDetail() {
    int id = rq.getIntParam("id", 0);

    if (id == 0) {
      System.out.println("id를 올바르게 입력해주세요.");
      return;
    }

    SecSql sql = new SecSql();

    sql.append("SELECT *");
    sql.append("FROM article");
    sql.append("WHERE id = ?", id);

    Map<String, Object> articleMap = DBUtil.selectRow(conn, sql);

    if (articleMap.isEmpty()) {
      System.out.printf("%d번 게시글은 존재하지 않습니다.\n", id);
      return;
    }

    Article article = new Article(articleMap);

    System.out.printf("== %d번 게시물 상세보기 ==\n", article.id);
    System.out.printf("번호 : %d\n", article.id);
    System.out.printf("작성날짜 : %s\n", article.regDate);
    System.out.printf("수정날짜 : %s\n", article.updateDate);
    System.out.printf("제목 : %s\n", article.title);
    System.out.printf("내용 : %s\n", article.content);
  }

  public void doModify() {
    int id = rq.getIntParam("id", 0);

    if (id == 0) {
      System.out.println("id를 올바르게 입력해주세요.");
      return;
    }

    SecSql sql = new SecSql();

    sql.append("SELECT COUNT(*) AS cnt");
    sql.append("FROM article");
    sql.append("WHERE id = ?", id);

    boolean articleIsExists = DBUtil.selectRowBooleanValue(conn, sql);

    if (articleIsExists == false) {
      System.out.printf("%d번 게시글은 존재하지 않습니다.\n", id);
      return;
    }

    System.out.printf("새 제목 : ");
    String title = sc.nextLine();
    System.out.printf("새 내용 : ");
    String content = sc.nextLine();

    sql = new SecSql();

    sql.append("UPDATE article");
    sql.append("SET updateDate = NOW()");
    sql.append(", title = ?", title);
    sql.append(", content = ?", content);
    sql.append("WHERE id = ?", id);

    DBUtil.update(conn, sql);

    System.out.printf("%d번 게시물이 수정되었습니다.\n", id);
  }

  public void doDelete() {
    int id = rq.getIntParam("id", 0);

    if (id == 0) {
      System.out.println("id를 올바르게 입력해주세요.");
      return;
    }

    SecSql sql = new SecSql();

    sql.append("SELECT COUNT(*) AS cnt");
    sql.append("FROM article");
    sql.append("WHERE id = ?", id);

    boolean articleIsExists = DBUtil.selectRowIntValue(conn, sql) == 1;

    if (articleIsExists == false) {
      System.out.printf("%d번 게시글은 존재하지 않습니다.\n", id);
      return;
    }

    sql = new SecSql();

    sql.append("DELETE FROM article");
    sql.append("WHERE id = ?", id);

    DBUtil.delete(conn, sql);

    System.out.printf("%d번 게시물이 삭제되었습니다.\n", id);

  }
}
