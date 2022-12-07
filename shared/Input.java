package shared;

import static shared.TextHelpers.print;
import static shared.TextHelpers.println;
import static shared.TextHelpers.text;

import java.util.Scanner;

public class Input {
  public static final Scanner scanner = new Scanner(System.in);

  private static void prompt(TextSegment... segments) {
    if (segments.length != 0) {
      print(segments);
      print(text(" "));
    }
    print(text("> ").blue());
  }

  public static long readLong(long min, long max, TextSegment... prompt) {
    prompt(prompt);

    while (true) {
      try {
        long result = Long.parseLong(scanner.nextLine());
        if (result < min || result > max) {
          throw new NumberFormatException();
        }
        return result;
      } catch (NumberFormatException e) {
        println(text("Invalid input! Enter a number between %d and %d.", min, max).red());
        print(text("> ").blue());
      }
    }
  }

  public static long readLong(TextSegment... prompt) {
    return readLong(Long.MIN_VALUE, Long.MAX_VALUE, prompt);
  }

  public static long readLong(String prompt) {
    return readLong(Long.MIN_VALUE, Long.MAX_VALUE, text(prompt));
  }

  public static long readLong() { return readLong(Long.MIN_VALUE, Long.MAX_VALUE); }

  public static int readInt(int min, int max, TextSegment... prompt) {
    prompt(prompt);

    while (true) {
      try {
        int result = Integer.parseInt(scanner.nextLine());
        if (result < min || result > max) {
          throw new NumberFormatException();
        }
        return result;
      } catch (NumberFormatException e) {
        println(text("Invalid input! Enter a number between %d and %d.", min, max).red());
        print(text("> ").blue());
      }
    }
  }

  public static int readInt(TextSegment... prompt) {
    return readInt(Integer.MIN_VALUE, Integer.MAX_VALUE, prompt);
  }

  public static int readInt(String prompt) {
    return readInt(Integer.MIN_VALUE, Integer.MAX_VALUE, text(prompt));
  }

  public static int readInt() { return readInt(Integer.MIN_VALUE, Integer.MAX_VALUE); }

  public static float readFloat(float min, float max, TextSegment... prompt) {
    prompt(prompt);

    while (true) {
      try {
        float result = Float.parseFloat(scanner.nextLine());
        if (result < min || result > max) {
          throw new NumberFormatException();
        }
        return result;
      } catch (NumberFormatException e) {
        println(text("Invalid input! Enter a number between %f and %f.", min, max).red());
        print(text("> ").blue());
      }
    }
  }

  public static float readFloat(TextSegment... prompt) {
    return readFloat(Float.MIN_VALUE, Float.MAX_VALUE, prompt);
  }

  public static float readFloat(String prompt) {
    return readFloat(Float.MIN_VALUE, Float.MAX_VALUE, text(prompt));
  }

  public static float readFloat() { return readFloat(Float.MIN_VALUE, Float.MAX_VALUE); }

  public static double readDouble(double min, double max, TextSegment... prompt) {
    prompt(prompt);

    while (true) {
      try {
        double result = Double.parseDouble(scanner.nextLine());
        if (result < min || result > max) {
          throw new NumberFormatException();
        }
        return result;
      } catch (NumberFormatException e) {
        println(text("Invalid input! Enter a number between %f and %f.", min, max).red());
        print(text("> ").blue());
      }
    }
  }

  public static double readDouble(TextSegment... prompt) {
    return readDouble(Double.MIN_VALUE, Double.MAX_VALUE, prompt);
  }

  public static double readDouble(String prompt) {
    return readDouble(Double.MIN_VALUE, Double.MAX_VALUE, text(prompt));
  }

  public static double readDouble() {
    return readDouble(Double.MIN_VALUE, Double.MAX_VALUE);
  }

  public static boolean readYesNo(boolean defaultChoice, TextSegment... prompt) {
    if (prompt.length != 0) {
      print(prompt);
      print(text(" "));
    }

    if (defaultChoice) {
      print(text("[Y/n] ").blue());
    } else {
      print(text("[y/N] ").blue());
    }

    while (true) {
      String input = scanner.nextLine().toLowerCase();
      if (input.equals("y") || input.equals("yes")) {
        return true;
      } else if (input.equals("n") || input.equals("no")) {
        return false;
      } else if (input.equals("")) {
        return defaultChoice;
      } else {
        println(text("Invalid input! Enter 'y' or 'n'.").red());
        print(text("> ").blue());
      }
    }
  }
}
