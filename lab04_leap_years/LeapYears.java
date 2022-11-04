package lab04_leap_years;

import shared.Console;
import shared.TextBuilder;
import shared.TextHelpers;
import shared.TextSegment;

class GameUtil {
  static int timeForRound(int round) {
    return (int)(1000 * (Math.pow(Math.E, -0.08 * round + 2.6742) + 0.5));
  }

  static int randomYear(int round) {
    int maxYear = (int)(0.5 * Math.pow(round, 3) + 1582 + 500);
    int minYear = 1582;
    return (int)(Math.random() * (maxYear - minYear) + minYear);
  }

  static boolean isLeapYear(int year) {
    return (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
  }
}

public class LeapYears extends TextHelpers {

  public static void main(String[] args) {
    try {
      Console.init();
      Console.hideCursor();
    } catch (Exception e) {
      TextBuilder.println(
        text("Error initializing the console window: "), text(e.getMessage())
      );
      return;
    }

    TextBuilder.println(
      text("Is it a leap year? - The game\n\n").bold().randomRainbow(),
      text(
        "You will be given a year. Answer if it is a leap year before the timer runs out!\n\n"
      ),
      text("Press any key to begin.")
    );

    int round = 1;

    DisplayThread display = null;

    try {
      Console.next();

    roundLoop:
      while (true) {
        Console.clear();
        display =
          new DisplayThread(round, GameUtil.randomYear(round), () -> Console.skipNext());
        display.start();

      inputLoop:
        while (true) {
          int[] input = Console.next();

          if (input.length == 0) {
            TextBuilder.println(text("\n\nYou ran out of time!").bold().red());
            break roundLoop;
          }

          int key = input[0];
          if (key != 'n' && key != 'y') {
            continue inputLoop;
          }

          display.interrupt();
          boolean isLeapYear = GameUtil.isLeapYear(display.year);
          boolean isCorrect  = (key == 'y') == isLeapYear;

          TextSegment[] resultString = new TextSegment[] {
            text("The year "),
            text(display.year).bold().randomRainbow(),
            text(isLeapYear ? " is " : " is not ")
              .style(isCorrect ? TextSegment.GREEN : TextSegment.RED)
              .bold(),
            text("a leap year."),
          };

          if (isCorrect) {
            TextBuilder.print(text("Correct! ").bold().green());
            TextBuilder.println(resultString);
            TextBuilder.println(text("\nPress any key to continue to the next round."));
            Console.next();
            round++;
            break inputLoop;
          } else {
            TextBuilder.print(text("Incorrect! ").bold().red());
            TextBuilder.println(resultString);
            TextBuilder.println(text("\nPress any key to exit."));
            Console.next();
            break roundLoop;
          }
        }
      }

    } catch (Exception e) {
      TextBuilder.println(text("Error getting input: "), text(e.getMessage()));
    }
  }
}
