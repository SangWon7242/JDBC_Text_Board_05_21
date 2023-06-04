package com.sbs.exam.board.controller;

import com.sbs.exam.board.Rq;

import java.sql.Connection;
import java.util.Scanner;
public abstract class Controller {
  protected Scanner sc;
  protected Rq rq;

  public Controller(Scanner sc, Rq rq) {
    this.sc = sc;
    this.rq = rq;
  }
}
