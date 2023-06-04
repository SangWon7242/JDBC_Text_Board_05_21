package com.sbs.exam.board.service;

import com.sbs.exam.board.container.Container;
import com.sbs.exam.board.dto.Member;
import com.sbs.exam.board.repository.MemberRepository;

import java.sql.Connection;

public class MemberService {
  private MemberRepository memberRepository;
  public MemberService() {
    memberRepository = Container.memberRepository;
  }
  public boolean isLoginIdDup(String loginId) {
    return memberRepository.isLoginIdDup(loginId);
  }

  public int join(String loginId, String loginPw, String name) {
    return memberRepository.join(loginId, loginPw, name);
  }

  public Member getMemberByLoginId(String loginId) {
    return memberRepository.getMemberByLoginId(loginId);
  }
}
