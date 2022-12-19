package lab04_leap_years;

import static shared.TextHelpers.*;

import java.util.Date;

import shared.Console;

class DisplayThread extends Thread {
  public final int round;
  public final int time;
  public final int year;
  public Date start;
  public final Runnable onTimeout;

  int elapsedTime() { return (int)(new Date().getTime() - start.getTime()); }

  DisplayThread(int round, int year, Runnable onTimeout) {
    this.round     = round;
    this.time      = GameUtil.timeForRound(round);
    this.year      = year;
    this.start     = null;
    this.onTimeout = onTimeout;
  }

  public void run() {
    Console.clear();

    this.start = new Date();
    display();
    while (elapsedTime() < time) {
      try {
        synchronized (this) { wait(Math.min(100, time - elapsedTime())); }
      } catch (InterruptedException e) {
        return;
      }
      updateTimer();
    }

    onTimeout.run();
  }

  public void display() {
    var now     = new Date();
    var elapsed = (int)(now.getTime() - start.getTime());
    print(
      text("Round " + round + "\n").randomRainbow(),
      text("Time remaining: ").bold(),
      text(Math.round(elapsed / 100.0) * 10 + " seconds\n"),
      text("Is " + year + " a leap year?").bold(),
      text(" (y/n) ").dim()
    );
  }

  public void updateTimer() {
    Console.moveCursor(0, 2);
    print(text("Time remaining: ").bold());
    print(text(Math.round((time - elapsedTime()) / 100.0) / 10d + " seconds\n"));
  }
}
