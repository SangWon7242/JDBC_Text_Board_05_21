package com.sbs.exam.board.repository;

import com.sbs.exam.board.container.Container;
import com.sbs.exam.board.dto.Article;
import com.sbs.exam.board.util.DBUtil;
import com.sbs.exam.board.util.SecSql;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ArticleRepository {

  public int write(String title, String content) {
    SecSql sql = new SecSql();

    sql.append("INSERT INTO article");
    sql.append("SET regDate = NOW()");
    sql.append(", updateDate = NOW()");
    sql.append(", title = ?", title);
    sql.append(", content = ?", content);

    int id = DBUtil.insert(Container.conn, sql);

    return id;
  }

  public boolean articleIsExists(int id) {
    SecSql sql = new SecSql();

    sql.append("SELECT COUNT(*) AS cnt");
    sql.append("FROM article");
    sql.append("WHERE id = ?", id);

    return DBUtil.selectRowBooleanValue(Container.conn, sql);
  }

  public void delete(int id) {
    SecSql sql = new SecSql();

    sql.append("DELETE FROM article");
    sql.append("WHERE id = ?", id);

    DBUtil.delete(Container.conn, sql);
  }

  public Article getArticleById(int id) {
    SecSql sql = new SecSql();

    sql.append("SELECT *");
    sql.append("FROM article");
    sql.append("WHERE id = ?", id);

    Map<String, Object> articleMap = DBUtil.selectRow(Container.conn, sql);

    if(articleMap.isEmpty()) {
      return null;
    }

    return new Article(articleMap);
  }

  public void update(int id, String title, String content) {
    SecSql sql = new SecSql();

    sql.append("UPDATE article");
    sql.append("SET updateDate = NOW()");
    sql.append(", title = ?", title);
    sql.append(", content = ?", content);
    sql.append("WHERE id = ?", id);

    DBUtil.update(Container.conn, sql);
  }

  public List<Article> getArticles() {
    SecSql sql = new SecSql();

    sql.append("SELECT *");
    sql.append("FROM article");
    sql.append("ORDER BY id DESC");

    List<Map<String, Object>> articleListMap = DBUtil.selectRows(Container.conn, sql);

    List<Article> articles = new ArrayList<>();

    for (Map<String, Object> articleMap : articleListMap) {
      articles.add(new Article(articleMap));
    }

    return articles;
  }

}
