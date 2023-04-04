package lab06_loops;

import shared.Console;
import shared.Input;
import shared.TextSegment;

import java.util.Scanner;

import static shared.TextHelpers.*;

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
        var scanner = new Scanner(System.in);

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

        gameLoop:
        while (true) {
            println();
            println(text("Select a difficulty:").white());

            var index = 0;
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
            println();

            long min, max;

            var difficultyIndex = Input.readInt(1, index + 1) - 1;
            if (difficultyIndex < Difficulty.values().length) {
                var difficulty = Difficulty.values()[difficultyIndex];

                min = difficulty.min;
                max = difficulty.max;
            } else {
                min = Input.readLong(text("Minimum value").white());
                max = Input.readLong(min, Long.MAX_VALUE, text("Maximum value").white());
            }

            Console.clear();

            var numbers = new long[5];
            for (int i = 0; i < numbers.length; i++) {
                numbers[i] = (long) (Math.random() * (max - min + 1)) + min;
            }

            var guessed = new boolean[numbers.length];
            var startTime = System.currentTimeMillis();
            var guesses = 0;
            Long lastGuess = null;

            println(
                text("Range: ").bold(),
                text(min).bold().white(),
                text(" - ").bold(),
                text(max).bold().white()
            );
            println(text("Start guessing!").bold().white());
            println(
                text("Type ").dim(),
                text("giveup").dim().bold(),
                text(" to reveal the numbers, or ").dim(),
                text("quit").dim().bold(),
                text(" to exit.").dim()
            );
            println();

            guessLoop:
            while (true) {
                printNumbers(numbers, guessed, lastGuess);

                print(text(" "), text(guesses).dim(), text(" > ").yellow());

                var input = scanner.nextLine();

                if (input.equalsIgnoreCase("quit")) {
                    break gameLoop;
                } else if (input.equalsIgnoreCase("giveup")) {
                    println();
                    println(text("The numbers were:").bold().white());
                    for (long number : numbers) {
                        print(text(number).bold().white(), text(" ").bold());
                    }

                    println();

                    break guessLoop;
                }

                long guess;

                try {
                    guess = Long.parseLong(input);
                } catch (NumberFormatException e) {
                    continue;
                }

                guesses++;

                for (int i = 0; i < numbers.length; i++) {
                    if (numbers[i] == guess && !guessed[i]) {
                        guessed[i] = true;
                    }
                }

                lastGuess = guess;

                var allGuessed = true;
                for (boolean correct : guessed) {
                    if (!correct) {
                        allGuessed = false;
                        break;
                    }
                }

                if (allGuessed) {
                    printNumbers(numbers, guessed, lastGuess);
                    print(text(" "), text(guesses).dim());
                    println();

                    var endTime = System.currentTimeMillis();

                    print();

                    println(
                        text("You guessed all ").green(),
                        text(numbers.length).bold().white(),
                        text(" numbers in ").green(),
                        text(guesses).bold().white(),
                        text(" guesses!").green()
                    );

                    // print time in seconds
                    println(
                        text("It took you ").green(),
                        text((endTime - startTime) / 1000.0).bold().white(),
                        text(" seconds.").green()
                    );

                    break guessLoop;
                }
            }

            println();

            var playAgain = Input.readYesNo(true, text("Play again?").white());
            if (!playAgain) {
                break gameLoop;
            }
        }

        scanner.close();
    }

    public static void printNumbers(long[] numbers, boolean[] guessed, Long guess) {

        for (int i = 0; i < numbers.length; i++) {
            var n = numbers[i];
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
