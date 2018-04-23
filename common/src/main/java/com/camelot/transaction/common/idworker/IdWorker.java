package com.camelot.transaction.common.idworker;

public class IdWorker {

  private static final Sequence worker = new Sequence();

  public static long getId() {
    return worker.nextId();
  }
}