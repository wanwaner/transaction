/**
 * Copyright (c) 2011-2020, hubin (jobob@qq.com). <p> Licensed under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with the License. You may obtain
 * a copy of the License at <p> http://www.apache.org/licenses/LICENSE-2.0 <p> Unless required by
 * applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See
 * the License for the specific language governing permissions and limitations under the License.
 */
package com.camelot.transaction.common.idworker;

import java.sql.Timestamp;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class SystemClock {

  private final long period;
  private final AtomicLong now;

  private SystemClock(long period) {
    this.period = period;
    this.now = new AtomicLong(System.currentTimeMillis());
    scheduleClockUpdating();
  }

  private static SystemClock instance() {
    return InstanceHolder.INSTANCE;
  }

  public static long now() {
    return instance().currentTimeMillis();
  }

  public static String nowDate() {
    return new Timestamp(instance().currentTimeMillis()).toString();
  }

  private void scheduleClockUpdating() {
    ScheduledExecutorService scheduler = Executors
        .newSingleThreadScheduledExecutor(new ThreadFactory() {
          public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable, "System Clock");
            thread.setDaemon(true);
            return thread;
          }
        });
    scheduler.scheduleAtFixedRate(new Runnable() {
      public void run() {
        now.set(System.currentTimeMillis());
      }
    }, period, period, TimeUnit.MILLISECONDS);
  }

  private long currentTimeMillis() {
    return now.get();
  }

  private static class InstanceHolder {

    public static final SystemClock INSTANCE = new SystemClock(1);
  }

}
