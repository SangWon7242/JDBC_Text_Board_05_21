package com.sbs.exam.board.service;

import com.sbs.exam.board.repository.MemberRepository;
import com.sbs.exam.board.util.DBUtil;
import com.sbs.exam.board.util.SecSql;

import java.sql.Connection;

public class MemberService {
  private MemberRepository memberRepository;
  public MemberService(Connection conn) {
    memberRepository = new MemberRepository(conn);
  }
  public boolean isLoginIdDup(String loginId) {
    return memberRepository.isLoginIdDup(loginId);
  }

  public int join(String loginId, String loginPw, String name) {
    return memberRepository.join(loginId, loginPw, name);
  }
}
