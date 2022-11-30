package lab06_loops;

import static shared.TextHelpers.print;
import static shared.TextHelpers.println;
import static shared.TextHelpers.text;

import java.util.Scanner;

import shared.Console;
import shared.Input;
import shared.TextSegment;

enum Difficulty {
  EASY(0, 10),
  MEDIUM(0, 100),
  HARD(0, 1000),
  IMPOSSIBLE(Integer.MIN_VALUE, Integer.MAX_VALUE);

  public final long min;
  public final long max;

  public String displayName() {
    return name().charAt(0) + name().substring(1).toLowerCase();
  }

  Difficulty(long min, long max) {
    this.min = min;
    this.max = max;
  }
}

public class HighLowGame {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    println(text("High low game").bold().randomRainbow());

    println(
      text("Welcome to the high low game! "),
      text("You will be given 5 numbers between two bounds. "),
      text(
        "Your goal is to guess all 5 numbers with as few guesses as possible, as quickly as possible. "
      ),
      text("You will be given hints on whether your guess is too high or too low. \n\n"),
      text(" ↑ ").black().bgBlue(),
      text(" - Number is higher than your guess\n"),
      text(" ↓ ").black().bgRed(),
      text(" - Number is lower than your guess\n"),
      text(" ✓ ").black().bgGreen(),
      text(" - Number is correct\n")
    );

    println(text("Good luck!"));

    println();

    println(text("Select a difficulty:").white());

    int index = 0;
    for (Difficulty difficulty : Difficulty.values()) {
      println(
        text(index++ + 1).bold().blue(),
        text(". ").bold(),
        text(difficulty.displayName()).bold().white(),
        text(" (").bold(),
        text(difficulty.min).bold().white(),
        text(" - ").bold(),
        text(difficulty.max).bold().white(),
        text(")").bold()
      );
    }
    println(
      text(index + 1).bold().blue(), text(". ").bold(), text("Custom").bold().white()
    );

    long min;
    long max;

    int difficultyIndex = Input.readInt(1, index + 1) - 1;
    if (difficultyIndex < Difficulty.values().length) {
      Difficulty difficulty = Difficulty.values()[difficultyIndex];

      min = difficulty.min;
      max = difficulty.max;
    } else {
      min = Input.readLong(text("Minimum value").white());
      max = Input.readLong(min, Long.MAX_VALUE, text("Maximum value").white());
    }

    Console.clear();

    long[] numbers = new long[5];
    for (int i = 0; i < numbers.length; i++) {
      numbers[i] = (long)(Math.random() * (max - min + 1)) + min;
    }

    boolean[] guessed = new boolean[numbers.length];

    long startTime = System.currentTimeMillis();
    int guesses    = 0;

    Long lastGuess = null;

    while (true) {
      boolean allGuessed = true;
      for (boolean guess : guessed) {
        if (!guess) {
          allGuessed = false;
          break;
        }
      }

      if (allGuessed) {
        break;
      }

      printNumbers(numbers, guessed, lastGuess);

      print(text(" "), text(guesses).dim(), text(" > ").yellow());

      String input = scanner.nextLine();
      long guess;

      try {
        guess = Long.parseLong(input);
      } catch (NumberFormatException e) {
        continue;
      }

      guesses++;

      boolean newCorrectGuess = false;
      for (int i = 0; i < numbers.length; i++) {
        if (numbers[i] == guess && !guessed[i]) {
          guessed[i]      = true;
          newCorrectGuess = true;
        }
      }

      if (newCorrectGuess) {
        int correct = 0;
        for (boolean guessedValue : guessed) {
          if (guessedValue) {
            correct++;
          }
        }
        // println(
        //   text("Guessed ").bold().green(),
        //   text(guess).bold().white(),
        //   text(" correctly!").bold().green(),
        //   text(" (").bold().green(),
        //   text(correct).bold().white(),
        //   text("/").bold().green(),
        //   text(numbers.length).bold().white(),
        //   text(")").bold().green()
        // );
      }

      lastGuess = guess;
    }

    printNumbers(numbers, guessed, lastGuess);
    print(text(" "), text(guesses).dim());
    println();

    long endTime = System.currentTimeMillis();

    println(
      text("You guessed all ").bold().green(),
      text(numbers.length).bold().white(),
      text(" numbers in ").bold().green(),
      text(guesses).bold().white(),
      text(" guesses!").bold().green()
    );

    // print time in seconds
    println(
      text("It took you ").bold().green(),
      text((endTime - startTime) / 1000.0).bold().white(),
      text(" seconds!").bold().green()
    );

    scanner.close();
  }

  public static void printNumbers(long[] numbers, boolean[] guessed, Long guess) {

    for (int i = 0; i < numbers.length; i++) {
      long n = numbers[i];
      TextSegment segment;
      if (guess == null) {
        segment = text(" · ").black().bgWhite();
      } else if (guess == n || guessed[i]) {
        segment = text(" ✓ ").black().bgGreen();
      } else if (guess < n) {
        segment = text(" ↑ ").black().bgBlue();
      } else {
        segment = text(" ↓ ").black().bgRed();
      }

      print(segment);
    }
  }
}
