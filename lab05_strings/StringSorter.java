package lab05_strings;

import java.util.Scanner;

import shared.Text;
import shared.TextBuilder;
import shared.TextHelpers;

public class StringSorter extends TextHelpers {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    TextBuilder.println(text("String sorter").randomRainbow());

    int index = 0;
    while (index < Text.examples.length) {
      Text text = Text.examples[index++];
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
        String input = scanner.nextLine().trim();
        if (input.isBlank()) {
          TextBuilder.print(text("> ").blue().bold());
          continue;
        }
        if (input.equals("q")) {
          break outer;
        }

        try {
          int choice = Integer.parseInt(input);

          if (choice < 1 || choice > index) {
            TextBuilder.println(
              text("Invalid input: expected a number between 1 and ").red(),
              text(index).red().bold(),
              text(".").red()
            );
            TextBuilder.print(text("> ").blue().bold());
            continue;
          }

          Text preset = Text.examples[choice - 1];

          // calculate substring length, which scales with text length and is capped at
          // 100
          int substringLength = Math.min(100, (int)(preset.length * 0.1));

          int substringIndex = (int)(Math.random() * (preset.length - substringLength));

          // if the substring has spaces nearby, advance the substring index to the next
          // space
          final int nextSpace = preset.content.indexOf(' ', substringIndex);
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

      String[] words = text.split("\s+");

      QuickSort<String> wordSort = new QuickSort<>(words);
      wordSort.sort();

      TextBuilder.print(text("Word sort:   ").bold());
      for (String word : wordSort.items) {
        System.out.print(word);
        System.out.print(" ");
      }
      System.out.println();

      Character[] letters = new Character[text.length()];
      for (int i = 0; i < text.length(); i++) {
        letters[i] = text.charAt(i);
      }
      QuickSort<Character> letterSort = new QuickSort<>(letters);
      letterSort.sort();

      TextBuilder.print(text("Letter sort: ").bold());
      for (Character letter : letterSort.items) {
        System.out.print(letter);
      }

      System.out.println();
    }

    scanner.close();
  }
}
