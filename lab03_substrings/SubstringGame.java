package lab03_substrings;

import static shared.TextHelpers.*;

import java.util.Scanner;

import shared.Console;
import shared.Text;

public class SubstringGame {
  public static void main(String[] args) {
    var scanner = new Scanner(System.in);

    println(text("Substring game").bold().randomRainbow());
    println();
    println(text("Welcome to the substring game!").bold());
    println(
      text("You will be given a text and a substring. "),
      text(
        "Your goal is to guess the index of the first occurrence of the substring in the text. "
      ),
      text(
        "Your score out of 5000 is dependent on the distance between your guess and the actual index and the number of hints you use. "
      ),
      text("Good luck!")
    );
    println();

    while (true) {
      var text = promptText(scanner);
      Console.clear();
      playGame(scanner, text);

      println();

      println(
        text("Play again? [").bold(),
        text("Y").bold().blue(),
        text("/").bold(),
        text("n").bold().red(),
        text("]").bold()
      );

      print(text("> ").blue().bold());

      var input = scanner.nextLine().trim();

      if (!input.equals("y") && !input.equals("")) {
        break;
      }

      Console.clear();
    }

    scanner.close();
  }

  public static void playGame(Scanner scanner, Text text) {
    println(
      text("Text: ").bold(),
      text(text.name).bold().randomRainbow(),

      text(" (").bold(),
      text(text.length + " chars").bold(),
      text(")").bold()
    );

    // calculate substring length, which scales with text length and is capped at 100
    var substringLength = Math.min(100, (int)(text.length * 0.1));

    var substringIndex = (int)(Math.random() * (text.length - substringLength));

    // if the substring has spaces nearby, advance the substring index to the next space
    final var nextSpace = text.content.indexOf(' ', substringIndex);
    if (nextSpace != -1 && nextSpace < substringIndex + substringLength && nextSpace + substringLength < text.length) {
      substringIndex = nextSpace + 1;
    }

    final var substring =
      text.content.substring(substringIndex, substringIndex + substringLength).trim();

    final var correctIndex = text.content.indexOf(substring);

    println(text("...").dim(), text(substring).italic(), text("...").dim());

    println();

    int response;
    var hints       = 0;
    var hintLow     = 0;
    var hintHigh    = text.length;
    var answerGiven = false;

    while (true) {
      print(text("What index is the substring at? ").bold());
      println(
        text("Type ").dim(),
        text("hint").bold().dim(),
        text(" for a hint, or ").dim(),
        text("giveup").bold().dim(),
        text(" to give up.").dim()
      );

      println(text("Hints taken: ").dim(), text(hints).bold().dim());

      print(text("> ").blue().bold());

      var input = scanner.nextLine().trim();

      if (input.equals("giveup")) {
        println(
          text("The substring was at index ").bold(),
          text(correctIndex).bold().randomRainbow()
        );
        return;
      }

      if (input.equals("hint")) {
        // divide the search space in half
        if (hintLow == hintHigh || Math.abs(hintLow - hintHigh) == 1) {
          answerGiven = true;
          println(text("No more hints available.").red());
          continue;
        }

        var hintIndex = (hintLow + hintHigh) / 2;

        if (hintIndex < correctIndex) {
          hintLow = hintIndex + 1;

          println(
            text("The index of the substring is above ").bold(),
            text(hintIndex).bold().blue(),
            text(".").bold()
          );
        } else if (hintIndex == correctIndex) {
          answerGiven = true;
          println(
            text("The index of the substring is ").bold(),
            text(hintIndex).bold().blue(),
            text(".").bold()
          );
        } else {
          hintHigh = hintIndex;

          println(
            text("The index of the substring is below ").bold(),
            text(hintIndex).bold().blue(),
            text(".").bold()
          );
        }

        hints++;
        continue;
      }

      try {
        response = Integer.parseInt(input);

        if (response < 0 || response >= text.length) {
          throw new NumberFormatException();
        }

        var score =
          answerGiven ? 0 : getScore(response, correctIndex, text.length, hints);

        println(
          text("Your guess was ").bold(),
          text(Math.abs(response - correctIndex)).bold().randomRainbow(),
          text(" away from the correct index of ").bold(),
          text(correctIndex).bold().randomRainbow(),
          text(". With ").bold(),
          text(hints).bold().blue(),
          text(" hints taken, your score for this guess is ").bold(),
          text(score).bold().randomRainbow(),
          text(".").bold()
        );
        return;
      } catch (NumberFormatException e) {
        println(
          text("Invalid input: expected a number between 0 and ").red(),
          text(text.length).red().bold(),
          text(".").red()
        );
      }
    }
  }

  public static Text promptText(Scanner scanner) {
    println(text("Select a text:").bold());

    var index = 0;
    while (index < Text.examples.length) {
      var text = Text.examples[index++];
      println(
        text(index + ". ").bold(),
        text(text.name),
        text(" (").bold(),
        text(text.length + " chars").bold(),
        text(")").bold()
      );
    }

    println(text(++index + ". ").bold(), text("Random"));
    println(text(++index + ". ").bold(), text("Supply your own text"));

    println();

    print(text("> ").blue().bold());

    int choice;
    while (true) {
      try {
        choice = Integer.parseInt(scanner.nextLine().trim());

        if (choice < 1 || choice > index) {
          throw new NumberFormatException();
        }

        break;
      } catch (NumberFormatException e) {
        println(
          text("Invalid input: expected a number between 1 and " + index + ".").red()
        );
        print(text("> ").blue().bold());
      }
    }

    Text text;
    if (choice == index) {
      println(text("Enter your text, then type [endtext] on a new line to end your text.")
                .bold());
      print(text("> ").blue().bold());

      var builder = new StringBuilder();
      while (true) {
        if (!scanner.hasNextLine()) {
          break;
        }
        var line = scanner.nextLine();
        if (line.equals("[endtext]")) {
          break;
        }

        builder.append(line);
        builder.append('\n');
      }

      text = new Text("Custom text", builder.toString().trim());
    } else if (choice == index - 1) {
      text = Text.examples[(int)(Math.random() * Text.examples.length)];
    } else {
      text = Text.examples[choice - 1];
    }

    return text;
  }

  public static int getScore(int response, int actual, int length, int hints) {
    // difficulty coefficient (higher = easier)
    var z = 0.2;

    var distance   = (double)Math.abs(response - actual);
    var log2Length = Math.log(length) / Math.log(2);
    var score =
      Math.max(0, Math.pow(Math.E, -distance / (z * length)) - (hints / log2Length));

    return (int)(score * 5000);
  }
}
