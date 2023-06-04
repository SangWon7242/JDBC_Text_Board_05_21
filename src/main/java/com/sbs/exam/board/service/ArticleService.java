package com.sbs.exam.board.service;

import com.sbs.exam.board.Article;
import com.sbs.exam.board.repository.ArticleRepository;
import com.sbs.exam.board.repository.MemberRepository;

import java.sql.Connection;
import java.util.List;

public class ArticleService {
  private ArticleRepository articleRepository;
  public ArticleService(Connection conn) {
    articleRepository = new ArticleRepository(conn);
  }

  public int write(String title, String content) {
    return articleRepository.write(title, content);
  }

  public boolean articleIsExists(int id) {
    return articleRepository.articleIsExists(id);
  }

  public void delete(int id) {
    articleRepository.delete(id);
  }

  public Article getArticleById(int id) {
    return articleRepository.getArticleById(id);
  }

  public void update(int id, String title, String content) {
    articleRepository.update(id, title, content);
  }

  public List<Article> getArticles() {
    return articleRepository.getArticles();
  }
}
