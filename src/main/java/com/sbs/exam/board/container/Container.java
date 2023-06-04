package com.sbs.exam.board.container;

import com.sbs.exam.board.dto.Rq;
import com.sbs.exam.board.controller.UsrArticleController;
import com.sbs.exam.board.controller.UsrMemberController;
import com.sbs.exam.board.repository.ArticleRepository;
import com.sbs.exam.board.repository.MemberRepository;
import com.sbs.exam.board.service.ArticleService;
import com.sbs.exam.board.service.MemberService;
import com.sbs.exam.board.session.Session;

import java.sql.Connection;
import java.util.Scanner;

public class Container {

  public static ArticleRepository articleRepository;
  public static MemberRepository memberRepository;

  public static ArticleService articleService;
  public static MemberService memberService;

  public static UsrArticleController usrArticleController;
  public static UsrMemberController usrMemberController;

  public static Scanner scanner;
  public static Session session;
  public static Connection conn;
  public static Rq rq;

  public static void init() {
    articleRepository = new ArticleRepository();
    memberRepository = new MemberRepository();

    articleService = new ArticleService();
    memberService = new MemberService();

    usrArticleController = new UsrArticleController();
    usrMemberController = new UsrMemberController();

    scanner = new Scanner(System.in);
    session = new Session();
  }
}
