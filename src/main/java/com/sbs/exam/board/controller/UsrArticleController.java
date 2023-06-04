package com.sbs.exam.board.controller;

import com.sbs.exam.board.container.Container;
import com.sbs.exam.board.dto.Article;
import com.sbs.exam.board.service.ArticleService;

import java.util.List;

import static com.sbs.exam.board.container.Container.rq;

public class UsrArticleController {
  private ArticleService articleService;

  public UsrArticleController() {
    articleService = Container.articleService;
  }

  public void doWrite() {
    System.out.println("== 게시물 등록 ==");
    System.out.printf("제목 : ");
    String title = Container.scanner.nextLine();
    System.out.printf("내용 : ");
    String content = Container.scanner.nextLine();

    int id = articleService.write(title, content);

    System.out.printf("%d번 게시물이 등록되었습니다.\n", id);
  }

  public void showList() {
    List<Article> articles = articleService.getArticles();

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

    Article article = articleService.getArticleById(id);

    if (article == null) {
      System.out.printf("%d번 게시글은 존재하지 않습니다.\n", id);
      return;
    }

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

    boolean articleIsExists = articleService.articleIsExists(id);

    if (articleIsExists == false) {
      System.out.printf("%d번 게시글은 존재하지 않습니다.\n", id);
      return;
    }

    System.out.printf("새 제목 : ");
    String title = Container.scanner.nextLine();
    System.out.printf("새 내용 : ");
    String content = Container.scanner.nextLine();

    articleService.update(id, title, content);

    System.out.printf("%d번 게시물이 수정되었습니다.\n", id);
  }

  public void doDelete() {
    int id = rq.getIntParam("id", 0);

    if (id == 0) {
      System.out.println("id를 올바르게 입력해주세요.");
      return;
    }

    boolean articleIsExists = articleService.articleIsExists(id);

    if (articleIsExists == false) {
      System.out.printf("%d번 게시글은 존재하지 않습니다.\n", id);
      return;
    }

    articleService.delete(id);

    System.out.printf("%d번 게시물이 삭제되었습니다.\n", id);
  }
}
