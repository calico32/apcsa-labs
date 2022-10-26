package lab03_substrings;

import java.util.Scanner;

import shared.Console;
import shared.TextBuilder;
import shared.TextHelpers;

public class SubstringGame extends TextHelpers {
  public static final Text[] texts = new Text[] {
    new Text("Bee Movie script", "bee_movie"),
    new Text("Digits of Pi", "digits_of_pi"),
    new Text("FitnessGram Pacer Test", "fitnessgram_pacer_test"),
    new Text("history of the entire world, i guess", "history_of_the_entire_world"),
    new Text("Lorem Ipsum", "lorem_ipsum"),
    new Text("Steamed Hams", "steamed_hams"),
    new Text("The Lego Movie script", "the_lego_movie"),
  };

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    TextBuilder.println(text("Substring game").bold().randomRainbow());
    System.out.println();
    TextBuilder.println(text("Welcome to the substring game!").bold());
    TextBuilder.println(
      text("You will be given a text and a substring. "),
      text(
        "Your goal is to guess the index of the fist occurrence of the substring in the text. "
      ),
      text(
        "Your score out of 5000 is dependent on the distance between your guess and the actual index and the number of hints you use. "
      ),
      text("Good luck!")
    );
    System.out.println();

  game:
    while (true) {

      TextBuilder.println(text("Select a text:").bold());

      int index = 0;

      while (index < texts.length) {
        Text text = texts[index++];
        TextBuilder.println(
          text(index + ". ").bold(),
          text(text.name),
          text(" (").bold(),
          text(text.length + " chars").bold(),
          text(")").bold()
        );
      }

      TextBuilder.println(text(++index + ". ").bold(), text("Random"));
      TextBuilder.println(text(++index + ". ").bold(), text("Supply your own text"));

      System.out.println();

      TextBuilder.print(text("> ").blue().bold());

      int choice;
      while (true) {
        try {
          choice = Integer.parseInt(scanner.nextLine().trim());

          if (choice < 1 || choice > index) {
            throw new NumberFormatException();
          }

          break;
        } catch (NumberFormatException e) {
          TextBuilder.println(
            text("Invalid input: expected a number between 1 and " + index + ".").red()
          );
          TextBuilder.print(text("> ").blue().bold());
        }
      }

      Text text;
      if (choice == index) {
        TextBuilder.println(
          text(
            "Enter your text, then type [endtext] on a new line (or an EOF) to end your text."
          )
            .bold()
        );
        TextBuilder.print(text("> ").blue().bold());

        StringBuilder builder = new StringBuilder();
        while (true) {
          if (!scanner.hasNextLine()) {
            break;
          }
          String line = scanner.nextLine();
          if (line.equals("[endtext]")) {
            break;
          }

          builder.append(line);
          builder.append('\n');
        }

        text = new Text("Custom text", builder.toString());
      } else if (choice == index - 1) {
        text = texts[(int)(Math.random() * texts.length)];
      } else {
        text = texts[choice - 1];
      }

      Console.clear();

      TextBuilder.println(
        text("Text: ").bold(),
        text(text.name).bold().randomRainbow(),

        text(" (").bold(),
        text(text.length + " chars").bold(),
        text(")").bold()
      );

      // calculate substring length, which scales with text length and is capped at 100
      int substringLength = Math.min(100, (int)(text.length * 0.1));

      int substringIndex = (int)(Math.random() * (text.length - substringLength));

      // if the substring has spaces nearby, advance the substring index to the next space
      final int nextSpace = text.content.indexOf(' ', substringIndex);
      if (nextSpace != -1 && nextSpace < substringIndex + substringLength && nextSpace + substringLength < text.length) {
        substringIndex = nextSpace + 1;
      }

      final String substring =
        text.content.substring(substringIndex, substringIndex + substringLength).trim();

      final int correctIndex = text.content.indexOf(substring);

      TextBuilder.println(
        text("...").dim(), text(substring).italic(), text("...").dim()

      );

      System.out.println();

      int response;

      int hints    = 0;
      int hintLow  = 0;
      int hintHigh = text.length;

      while (true) {
        TextBuilder.print(text("What index is the substring at? ").bold());
        TextBuilder.println(
          text("Type ").dim(),
          text("hint").bold().dim(),
          text(" for a hint, or ").dim(),
          text("giveup").bold().dim(),
          text(" to give up.").dim()
        );

        TextBuilder.println(text("Hints taken: ").dim(), text(hints).bold().dim());

        TextBuilder.print(text("> ").blue().bold());

        String input = scanner.nextLine().trim();

        if (input.equals("hint")) {
          // divide the search space in half
          if (hintLow == hintHigh || Math.abs(hintLow - hintHigh) == 1) {
            TextBuilder.println(text("No more hints available.").red());
            continue;
          }

          int hintIndex = (hintLow + hintHigh) / 2;

          if (hintIndex < correctIndex) {
            hintLow = hintIndex + 1;

            TextBuilder.println(
              text("The index of the substring is above ").bold(),
              text(hintIndex).bold().blue(),
              text(".").bold()
            );
          } else {
            hintHigh = hintIndex;

            TextBuilder.println(
              text("The index of the substring is below ").bold(),
              text(hintIndex).bold().blue(),
              text(".").bold()
            );
          }

          hints++;
          continue;
        }

        if (input.equals("giveup")) {
          TextBuilder.println(
            text("The substring was at index ").bold(),
            text(correctIndex).bold().randomRainbow()
          );
          break;
        }

        try {
          response = Integer.parseInt(input);

          if (response < 0 || response >= text.length) {
            throw new NumberFormatException();
          }

          // calculate score
          final double L = text.length;
          final double C = correctIndex;
          final double x = response;
          final double z = 0.2; // difficulty coefficient (higher = easier)
          final double h = hints;

          final int score = (int
          )(5000d * Math.max(
                      0,
                      Math.pow(Math.E, -Math.abs(x - C) / (z * L)) -
                        h / (Math.log(L) / Math.log(2))
                    ));

          TextBuilder.println(
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
          break;
        } catch (NumberFormatException e) {
          TextBuilder.println(
            text("Invalid input: expected a number between 0 and ").red(),
            text(text.length).red().bold(),
            text(".").red()
          );
        }
      }

      System.out.println();

      TextBuilder.println(
        text("Play again? [").bold(),
        text("Y").bold().blue(),
        text("/").bold(),
        text("n").bold().red(),
        text("]").bold()
      );

      TextBuilder.print(text("> ").blue().bold());

      while (true) {
        String input = scanner.nextLine().trim();

        if (input.equals("y") || input.equals("")) {
          Console.clear();
          break;
        } else if (input.equals("n")) {
          break game;
        } else {
          TextBuilder.println(
            text("Invalid input: expected ").red(),
            text("y").red().bold(),
            text(" or ").red(),
            text("n").red().bold(),
            text(".").red()
          );
        }
      }
    }

    scanner.close();
  }
}
