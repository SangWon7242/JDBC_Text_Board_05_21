package com.sbs.exam.board.controller;

import com.sbs.exam.board.Rq;

import java.sql.Connection;
import java.util.Scanner;
public abstract class Controller {
  protected Connection conn;
  protected Scanner sc;
  protected Rq rq;

  public void setConn(Connection conn) {
    this.conn = conn;
  }
  public void setScanner(Scanner scanner) {
    this.sc = scanner;
  }
  public void setRq(Rq rq) {
    this.rq = rq;
  }
}
