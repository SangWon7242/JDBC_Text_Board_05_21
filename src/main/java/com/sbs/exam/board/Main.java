package com.sbs.exam.board;

import com.sbs.exam.board.exception.SQLErrorException;

public class Main {
  public static void main(String[] args) {
    try {
      new App().run();
    }
    catch (SQLErrorException e) {
      System.err.println(e.getMessage());
      e.getOrigin().printStackTrace();
    }
  }
}