package lab05_strings;

import static shared.TextHelpers.*;

import java.util.Scanner;

import shared.QuickSort;
import shared.Text;
import shared.TextBuilder;

public class StringSorter {
  public static void main(String[] args) {
    var scanner = new Scanner(System.in);

    TextBuilder.println(text("String sorter").randomRainbow());

    var index = 0;
    while (index < Text.examples.length) {
      var text = Text.examples[index++];
      TextBuilder.println(text(index + ". ").bold(), text(text.name));
    }

    TextBuilder.println(text(
      "Enter some text to sort, or type a number to select a random substring of a preset. Type q to quit."
    ));

  outer:
    while (true) {
      System.out.println();

      TextBuilder.print(text("> ").blue().bold());

      String text;
      while (true) {
        var input = scanner.nextLine().trim();
        if (input.isBlank()) {
          TextBuilder.print(text("> ").blue().bold());
          continue;
        }
        if (input.equals("q")) {
          break outer;
        }

        try {
          var choice = Integer.parseInt(input);

          if (choice < 1 || choice > index) {
            TextBuilder.println(
              text("Invalid input: expected a number between 1 and ").red(),
              text(index).red().bold(),
              text(".").red()
            );
            TextBuilder.print(text("> ").blue().bold());
            continue;
          }

          var preset = Text.examples[choice - 1];

          // calculate substring length, which scales with text length and is capped at
          // 100
          var substringLength = Math.min(100, (int)(preset.length * 0.1));

          var substringIndex = (int)(Math.random() * (preset.length - substringLength));

          // if the substring has spaces nearby, advance the substring index to the next
          // space
          final var nextSpace = preset.content.indexOf(' ', substringIndex);
          if (nextSpace != -1 && nextSpace < substringIndex + substringLength && nextSpace + substringLength < preset.length) {
            substringIndex = nextSpace + 1;
          }

          text =
            preset.content.substring(substringIndex, substringIndex + substringLength)
              .trim();

          break;
        } catch (NumberFormatException e) {
          // not a number, treat as text
          text = input;
          break;
        }
      }

      var words = text.split("\s+");

      var wordSort = new QuickSort<>(words);

      print(text("Word sort:   ").bold());
      for (String word : wordSort.sort()) {
        print(word);
        print(" ");
      }
      println();

      var letters = new Character[text.length()];
      for (int i = 0; i < text.length(); i++) {
        letters[i] = text.charAt(i);
      }
      var letterSort = new QuickSort<>(letters);

      print(text("Letter sort: ").bold());
      for (Character letter : letterSort.sort()) {
        System.out.print(letter);
      }

      System.out.println();
    }

    scanner.close();
  }
}
